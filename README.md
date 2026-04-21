# Overview
This is my Text-based Adventure Game project (still unnamed after working on it since january 2026). It is now *finnaly* functional! It loads up the code, opens it up in the box, and runs Java inside an HTML page (thanks a lot to Copilot and Claude Haiku 4.5).

The general basis of this game is this: The player will enter the text into the input box, containing the answer to the program's prompt. For example:
```
Do you open the green door or the red door?
1) Pick Green
2) Pick Red
3) Go back
4) Cry
```
The user could then respond with something like:
```
1
```
And the program would continue with them having picked the green door.

# Running the Game
This is important, as anyone who wants to play the game needs to know how to actually *run* the game.

## Requirements
- **Java 11 or higher** installed on your computer
- **Windows** (the batch script is Windows-specific)

## Step-by-Step Instructions

### 1. Download and Extract the Game
- Download the game from the repository, by clicking here.
- Extract/unzip the folder to a location on your computer (e.g., `C:\Games\text-based-adventure`)
    - To do this, right click the folder, click "extract", and then select the folder you want to extract it to.

### 2. Open the Game Folder
- Navigate to the game folder where you extracted it
- You should see a `run.bat` file in the main folder

### 3. Run the Game
- **Double-click `run.bat`** or right-click and select "Open"
- A command window will appear showing the compilation and server startup messages
- Your web browser will automatically open to `http://localhost:4567`

### 4. Play the Game
- Click the "Run Game" button, and the text will appear on screen in the black box. Type your response below the text and the game will continue. More instructions will be provided in-game.

### 5. Stop the Game
- To stop the game, close the command window that appeared when you ran `run.bat`
- You can also close your browser, but the server will still be running until you close the command window

## Troubleshooting
- **"Java is not recognized"**: Make sure Java is installed correctly on your computer. I intend to find some way around this, but for now you'll just need to install it yourself.
- **Port 4567 already in use**: Close any other instances of the game or programs using that port
- **Browser doesn't open**: Manually open your browser and navigate to `http://localhost:4567`