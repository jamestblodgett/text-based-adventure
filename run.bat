@echo off
cd /d "%~dp0"

REM Check if bundled JDK exists
if exist "jdk\jdk11\bin\java.exe" (
    set JAVA_HOME=%~dp0jdk\jdk11
    set PATH=%JAVA_HOME%\bin;%PATH%
    echo Using bundled JDK...
) else (
    echo Bundled JDK not found. Please ensure Java 11+ is installed.
    pause
    exit /b 1
)

echo Compiling Java...
javac -cp "lib/*" -d out src\Main.java

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