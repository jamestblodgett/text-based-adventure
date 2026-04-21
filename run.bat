@echo off
echo Compiling Java...
mkdir out 2>nul
timeout /t 2 >nul
javac -cp "lib/*" -d out src\Main.java

echo Starting server...
timeout /t 2 >nul
start "" cmd /k "java -cp \"out;lib/*\" Main"

echo Waiting for server to start.

echo Opening browser...
timeout /t 2 >nul
start http://localhost:4567/index.html