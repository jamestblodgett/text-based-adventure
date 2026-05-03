# Text-Based Adventure Game

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Requirements](#requirements)
- [How to Run the Game](#how-to-run-the-game)
- [Game Commands](#game-commands)
- [Troubleshooting](#troubleshooting)
- [Differences for the Demo version](#differences-for-the-demo-version)
- [Project Structure](#project-structure)
- [Development](#development)
- [Conclusion](#conclusion)
- [Installing JAVA](#installing-java)

## Overview
This is my text-based adventure game project! It's a functional choose-your-own-adventure style game that runs in any web browser in windows, such as google or firefox, and is run with Java and Spark Java servers.

The game starts with an initial introduction:
```
Welcome to the text-adventure game!

This game is a fun, puzzle-based choose your own adventure game, where each 
    choice you make will lead you down a different path. There will be various challenges,
    such as puzzles, riddles, memory-based games, and more. To play, simply type "Let it begin"
    into the box above, then either click the "Enter" key, or press the button next to the box.
  
When you play, you will receive a prompt. Based on this prompt, you can enter a response
    in the box, and the game will continue based on what you entered. For example:
  
You are in a room with a green door and a red door. What do you do?
    1) Open the green door
    2) Open the red door
    3) Cry
    4) Check your inventory - this action is always available.
  
You could then enter "1" in the box, which would have the program continue with you going
    through the green door. You can Always enter "Inventory" to check your inventory, 
    "Help", which will take you back to this page, or "Hard-Reset", which will reset the game.
    Don't do that.
  
Now, keep your hands, feet, reproductive organs, and ticket to the afterlife in the vehicle
    at all times, and enjoy the game!
```
it explains a scenario and a few options, and the player inputs their decisions to progress through the story. It features puzzles, riddles, inventory management, memory games, and more!

### Features
- *Interactive text-based gameplay*
- *Web-based program*
- *Inventory system*
- *Help and reset commands (as well as more commands that I won't tell you :D)*
- *No other installation required.*

### Requirements
- **Java JDK setup** *Instructions are listed [below](#installing-java)*
- **Windows** *(Only works on windows, no chrome-os, linux, or other programs)*
- **A decent computer** *You need a computer that can run java, html, and other code simultaneously.*
- **Half a brai-** *Nevermind, editor said to leave that part out.*
- **A GOOD ATTITUDE!!!**

### How to Run the Game

#### 1. Download and Extract
- Download the project as a ZIP from the repository
    - Click the Green `Code` button at the top right above the folders
    - Click `Download ZIP`
- Extract to a folder
    - Find the file in your computer
    - Right click and select the "Unzip" option
    - Select the folder where you want the game's folder to go
#### 2. Run the Game
- Go into the folder
- **Double-click `run.bat`** or right-click and select "Open"
- A command window will appear showing cool stuff about the server startup
- Your preffered web browser will automatically open to `http://localhost:4567`, which contains the game.

#### 3. Play the Game
- Read the instructions available to learn how to play.

#### 4. Stop the Game
- Close the command window (which opened when you started the game) to stop the server (MUST do this)
- You can also close the browser, but the server continues until the command window is closed
- If you try to re-open the game, but the command window is still open, then the game will *not* work. I spent 4 hours trying to troubleshoot this thinking it was a problem only to facepalm myself into the wall because it already worked.

### Game Commands
- **Numbers**: Select options in scenarios
- **inventory**: Display current inventory
- **help**: Show game instructions
- **hard-reset**: Reset game to start (use carefully)
- **Others**: ...which I will conveniently not tell you.

### Troubleshooting
Try all of these solutions in order if it isn't working. If you're still having problems, then you're out of luck.
1. **CLOSE THE COMMAND WINDOW**, then close the browser tab, and then re-open the project.
2. **Make sure all the files are in the same folder**, and that no other files were added in or modified.
3. **Manually go to `http://localhost:4567`**

## Differences for the Demo version
This version is extremely short, has no "hidden" commands, and doesn't have any fancy features or characters. It only has 2 rooms, unlike the final game, which will have dozens. Everything else is identical, however.

## Project Structure
- `src/Main.java` - Main game server code
- `public/index.html` - Web interface
- `public/css/styles.css` - Styling
- `lib/` - Java dependencies (Spark, Gson, etc.)
- `jdk/` - Bundled JDK 11 (no external Java needed)
- `run.bat` - Windows startup script
- `pom.xml` - Maven build configuration

### Development
I'm not telling you how to change the game you cheats.

### Conclusion
##### Of course, I hope that each and every one of you playing will be able to have a blast playing this game. If you have any concerns about the game, have anything you would like to contribute, or just want to give feedback about the game, email me at: jamestblodgett@gmail.com
#### Have a nice day, and feel free to check out my other projects and my profile at https://github.com/jamestblodgett.

## Installing JAVA
If you don't already have java installed on your device, then you will need to install it in order to play the game. I'm working on a way around this at the moment, but we'll have to see.
- You may attempt this link, although it may not work.
    - https://download.oracle.com/java/26/latest/jdk-26_windows-x64_bin.exe
- **Download the JDK** If the link didn't work, it can be downloaded from Oracle at https://www.oracle.com/java/technologies/downloads.
- The page provides intallment links for several options. Select JDK 26 or JDK 25, then select windows. Click the `Download` link listed below. Click the one that ends with `.exe`, which should match the one up above.
- If you want to verify it was installed, open the "terminal" app in your device and type in `java -version` and it should ouput something showing the version you installed. Of course, if it doesn't work, there will be a bunch of red text saying that 'java' isn't recognized.
