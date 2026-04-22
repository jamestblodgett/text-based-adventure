# Text-Based Adventure Game

## Overview
This is my text-based adventure game project! It's a functional choose-your-own-adventure style game that runs in a web browser, such as google or edge, and is run with Java and Spark Java servers.

The game explains a scenario and a few options, and the player inputs their decisions to progress through the story. It features puzzles, riddles, inventory management, memory games, and more!

## Features
- Interactive text-based gameplay
- Web-based program
- Inventory system
- Help and reset commands (as well as more commands that I won't tell you :D)
- No other installation required.

## Requirements
- **Windows** (Only works on windows, no chrome-os, linux, or other programs)
- **A decent computer** You need a computer that can run java, html, and other code simultaneously.
- **Half a brai-** Nevermind, editor said to leave that part out.
- **A GOOD ATTITUDE!!!**

## How to Run the Game

### 1. Download and Extract
- Download the project as a ZIP from the repository
    - Click the Green `Code` button at the top right above the folders
    - Click `Download ZIP`
- Extract to a folder
    - Find the file in your computer
    - Right click and select the "Unzip" option
    - Select the folder where you want the game's folder to go
### 2. Run the Game
- Go into the folder
- **Double-click `run.bat`** or right-click and select "Open"
- A command window will appear showing cool stuff about the server startup
- Your favorite web browser will automatically open to `http://localhost:4567`, which contains the game.

### 3. Play the Game
- Read the instructions available to learn how to play.

### 4. Stop the Game
- Close the command window to stop the server (MUST do this)
- You can also close the browser, but the server continues until the command window is closed
- If you try to re-open the game, but the command window is still open, then the game will *not* work. I spent 4 hours trying to troubleshoot this thinking it was a problem only to facepalm myself into the wall because it already worked.

## Game Commands
- **Numbers**: Select options in scenarios
- **inventory**: Display current inventory
- **help**: Show game instructions
- **hard-reset**: Reset game to start (use carefully)
- **Others**: ...which I will conveniently not tell you.

## Troubleshooting
Try all of these solutions in order if it isn't working. If you're still having problems, then you're out of luck.
1. **CLOSE THE COMMAND WINDOW**, then close the browser tab, and then re-open the project.
2. **Make sure all the files are in the same folder**, and that no other files were added in or modified.
3. **Manually go to `http://localhost:4567`**

## Project Structure
- `src/Main.java` - Main game server code
- `public/index.html` - Web interface
- `public/css/styles.css` - Styling
- `lib/` - Java dependencies (Spark, Gson, etc.)
- `jdk/` - Bundled JDK 11 (no external Java needed)
- `run.bat` - Windows startup script
- `pom.xml` - Maven build configuration

## Development
I'm not telling you how to change the game you cheats.

## Conclusion
#### Of course, I hope that each and every one of you playing will be able to have a blast playing this game. If you have any concerns about the game, have anything you would like to contribute, or just want to give feedback about the game, email me at: jamestblodgett@gmail.com
#### Have a nice day, and feel free to check out my other projects and my profile at https://github.com/jamestblodgett.