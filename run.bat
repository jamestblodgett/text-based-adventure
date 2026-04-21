@echo off
cd /d "%~dp0"

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