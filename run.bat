@echo off
cd /d "%~dp0"

REM Prefer a bundled JDK if available, otherwise use system Java.
if exist "jdk\jdk11\bin\java.exe" (
    set "JAVA_HOME=%~dp0jdk\jdk11"
    set "PATH=%JAVA_HOME%\bin;%PATH%"
    echo Using bundled JDK...
) else if defined JAVA_HOME if exist "%JAVA_HOME%\bin\java.exe" (
    echo Using JAVA_HOME: %JAVA_HOME%
) else (
    where java >nul 2>&1
    if errorlevel 1 (
        echo Java executable not found in PATH or JAVA_HOME.
        echo Please install Java 11 or newer and ensure java.exe is on PATH.
        pause
        exit /b 1
    ) else (
        echo Using system Java from PATH...
    )
)

echo Compiling Java...
javac -cp "lib/*" -d out src\Main.java

if errorlevel 1 (
    echo Compilation failed.
    pause
    exit /b 1
)

echo.
echo Starting server on localhost:4567...
start "" java -cp "out;lib/*" Main

echo.
echo Waiting for server to start...
timeout /t 5 /nobreak

echo.
echo Opening browser...
start http://localhost:4567/index.html

echo.
echo Server is running. Close this window to stop the server.
pause