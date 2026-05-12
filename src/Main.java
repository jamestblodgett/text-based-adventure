import java.nio.file.Paths;
import java.util.Arrays;

import com.google.gson.Gson;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

public class Main {
    private static Gson gson = new Gson();
    private static String currentRoom = "start";
    private static String subRoom = "";
    private static String [] inventory = new String[9];
    private static String [] foundItems = new String[30];
    private static String [] opts = new String[5];

    // ---------------------------------------------
    // Input/Output Handling. Main function.       |
    // ---------------------------------------------
    public static void main(String[] args) {
        String publicDir = Paths.get("").toAbsolutePath().resolve("public").toString();
        staticFiles.externalLocation(publicDir);

        // Game endpoint that handles user input
        post("/game", (req, res) -> {
            res.type("application/json");

            try {
                // Parse the JSON input
                GameInput gameInput = gson.fromJson(req.body(), GameInput.class);
                if (gameInput == null || gameInput.input == null) {
                    return gson.toJson(new GameResponse("No input received."));
                }
                String userInput = gameInput.input.toLowerCase().trim();

                // Process the input through the game logic
                String response = runGame(userInput);

                // Return the game response
                return gson.toJson(new GameResponse(response));
            } catch (Exception e) {
                return gson.toJson(new GameResponse("Error processing input: " + e.getMessage()));
            }
        });

        // Keep the old endpoints for compatibility
        get("/hello", (req, res) -> {
            res.type("application/json");
            return "{\"message\": \"Hello from Java!\"}";
        });

        post("/echo", (req, res) -> {
            res.type("application/json");
            return "{\"you_sent\": \"" + req.body() + "\"}";
        });
    }


    // ---------------------------------------------
    // Actual Game Logic                           |
    // ---------------------------------------------
    public static String runGame(String input) {
        String response = "";
        String actionMessage = "";

        inventory = inventoryDebug(inventory);
        foundItems = inventoryDebug(foundItems);
        
        // Handle global commands first
        if (true) {
            if (input.startsWith("skipto:")) {
                String targetRoom = input.substring(input.indexOf(":") + 1).trim();
                if (!targetRoom.isEmpty()) {
                    currentRoom = targetRoom;
                    subRoom = "";
                    response = "Skipping to " + targetRoom + ".";
                } else {
                    response = "Skipto command requires a room name, e.g. 'skipto: room1'.";
                }
            } else if (input.startsWith("additem:")) {
                String item = input.substring(input.indexOf(":") + 1).trim();
                if (!item.isEmpty()) {
                    inventory = addItem(item, inventory, foundItems);
                    response = "Item added to inventory: " + item;
                } else {
                    response = "additem command requires an item name, e.g. 'addItem: sword'.";
                }
            } else if (input.equals("inventory") || input.equals("i") || input.equals("check inventory")) {
                inventory = inventoryDebug(inventory);
                response = "You are carrying:\n " + String.join(",\n ", inventory);
            } else if (input.equals("foundItems") ||  input.equals("f")) {
                foundItems = inventoryDebug(foundItems);
                response = "Items you've found:\n " + String.join(",\n ", foundItems);
            } else if (input.equals("help") || input.equals("h") || input.equals("?")) {
                response = "Welcome to the text-adventure game!\nWhen you play, you will receive a prompt. Based on this prompt, you can enter a response in the box, and the game will continue based on what you entered. For example:\n\nYou are in a dark room with a green door and a red door. What do you do?\n1) Open the green door\n2) Open the red door\n3) Cry\n4) Check your inventory - this is always available\n\nIf you enter \"1\", the game will continue with you going through the green door. You can always enter \"inventory\" to check your inventory, \"foundItems\" to check your total found items, \"Help\", which takes you to this page, or \"Hard-Reset\", which will reset the game. Don't do that.";
            } else if (input.equals("hard-reset")) {
                resetGame();
                response = "Game reset. You are back at the start.";
            }
        }

        // Process input based on current room
        switch(currentRoom){
            case "start":
                if (input.equals("1")) {
                    actionMessage = "\nYou go through the door.";
                    currentRoom = "room1";
                }
                break;
            case "room1":
                if (subRoom.equals("room1Plant")) {
                    if (containsVal("crowbar", inventory, foundItems) && !containsVal("key", inventory, foundItems)) {
                        switch (input) {
                            case "1":
                                subRoom = "";
                                break;
                            case "2":
                                actionMessage = "\nThe vase seems to be glued to the table.";
                                break;
                            case "3":
                                actionMessage = "\nYou find a key in the broken shards of the vase.";
                                inventory = addItem("key", inventory, foundItems);
                                break;
                        }
                    } else {
                        switch (input) {
                            case "1":
                                subRoom = "";
                                break;
                            case "2":
                                actionMessage = "\nThe vase seems to be glued to the table.";
                                break;
                        }
                    }
                } else if (subRoom.equals("window")) {
                    if (containsVal("string", inventory, foundItems)) {
                        switch(input) {
                            case "1":
                                actionMessage = "\nYou go back.";
                                subRoom = "";
                                break;
                            case "2":
                                actionMessage = "\nYou use the string to pull the crowbar to yourself. The string breaks, but you grab the crowbar in time.";
                                inventory = removeItem("string", inventory);
                                inventory = addItem("crowbar", inventory, foundItems);
                                subRoom = "";
                                break;
                        }
                    } else {
                        if (input.equals("1")) {
                            actionMessage = "\nYou go back.";
                            subRoom = "";
                        }
                    }
                } else {
                    switch (input) {
                        case "1":
                            actionMessage = "\nYou go back to the start.";
                            currentRoom = "start";
                            subRoom = "";
                            break;
                        case "2":
                            subRoom = "room1Plant";
                            break;
                        case "3":
                            if (containsVal("key", inventory, foundItems)) {
                                actionMessage = "\nYou unlock the door and pass through.";
                                inventory = removeItem("key", inventory);
                                currentRoom = "chute";
                                subRoom = "";
                            } else {
                                actionMessage = "\nThe door seems to be locked, you'll need to find a key.";
                            }
                            break;
                        case "4":
                            actionMessage = "\nYou look out the window.";
                            subRoom = "window";
                            break;
                        case "5":
                            if (containsVal("string", inventory, foundItems)) {
                                actionMessage = "\nThere is nothing left under the table.";
                            } else {
                                actionMessage = "\nYou look under the table and find a small length of string.";
                                inventory = addItem("string", inventory, foundItems);
                            }
                            break;
                    }
                }
                break;
            case "chute":
                switch(input) {
                    case "1":
                        actionMessage = "\nYou go back to the previous room.";
                        currentRoom = "room1";
                        break;
                    case "2":
                        actionMessage = "\nYou jump down the chute";
                        currentRoom = "room2";
                        break;
                }
                break;
            case "room2":
                switch(input) {
                    case "1":
                        actionMessage = "\nYou leave the room";
                        currentRoom = "forest1";
                        break;
                    case "2":
                        actionMessage = "\nYou look out the window and see that you're now in a thick, green forest. How?!?";
                        break;
                    case "3":
                        actionMessage = "\nThe paper has writing on it: \"Find the cone\". What's that supposed to mean?";
                        break;
                    case "4":
                        if (containsVal("room2-underTable-Coin", foundItems, foundItems)) {
                            actionMessage = "\nThere is nothing left under the table.";
                        } else {
                            actionMessage = "\nYou look under the table and find an iron coin.";
                            inventory = addItem("iron coin", inventory, foundItems);
                            foundItems = addItem("room2-underTable-Coin", foundItems, foundItems);
                        }
                        break;
                    case "5":
                        if (containsVal("room2-dirt-Coin", foundItems, foundItems)) {
                            actionMessage = "\nYou look in the dirt but don't find anything else of interest.";
                        } else {
                            actionMessage = "\nYou look in the dirt and find a small Iron Coin.";
                            inventory = addItem("Iron Coin", inventory, foundItems);
                            foundItems = addItem("room2-dirt-Coin", foundItems, foundItems);
                        }
                        break;
                    }
                    break;
                case "forest1":
                    if (subRoom.equals("stump")) {
                    } else if (subRoom.equals("river")) {
                        if (containsVal("bucket", inventory, foundItems)) {
                            switch (input) {
                            case "1":
                                actionMessage = "\nYou go back to the clearing.";
                                subRoom = "";
                                break;
                            case "2":
                                actionMessage = "\nYou pick up a smooth stone from the riverbed.";
                                inventory = addItem("smooth stone", inventory, foundItems);
                                break;
                            case "3":
                                actionMessage = "\nYou follow the river and find nothing of interest, eventually coming to a waterfall. You turn back.";
                                break;
                            case "4":
                                actionMessage = "\nYou use the bucket to collect some water from the river.";
                                inventory = removeItem("bucket", inventory);
                                inventory = addItem("water bucket", inventory, foundItems);
                        }
                        }
                        
                    } else {
                        switch (input) {
                        case "1":
                            actionMessage = "\nYou go back inside the room.";
                            currentRoom = "room2";
                            break;
                        case "2":
                            subRoom = "river";
                            actionMessage = "\nYou head to the sound of the water.";
                            break;
                        case "3":
                            actionMessage = "\nYou climb a tree and don't see much, the sky is far too cloudy and you can see fog in the distance obscuring your view. You climb back down.";
                            break;
                        case "4":
                            subRoom = "stump";
                            actionMessage = "\nYou go to the stump.";
                            break;
                        }
                    }
                    
                    break;

        }

        // Now generate response based on current state
        response += actionMessage;
        switch(currentRoom){
            case "start":
                opts = setOpts(opts, "Go through the door");
                response += "\nYou find yourself in a white, cube-shaped room. It is well-lit, but you don't have any idea where the light is coming from.\r\n" + //
                    "Actually, come to think of it, you don't even know how you got here. Looking around the room, you only see a single door.\r\n";
                response = addOpts(response, opts);
                break;
            case "room1":
                if (subRoom.equals("room1Plant")) {
                    if (containsVal("crowbar", inventory, foundItems) && !containsVal("key", inventory, foundItems)) {
                        opts = setOpts(opts, "Go Back", "Push over the vase", "Break the vase (crowbar)");
                    } else {
                        opts = setOpts(opts, "Go Back", "Push over the vase");
                    }
                    
                    if (containsVal("key", inventory, foundItems)) {
                        response += "\nThe plant was in a green ceramic vase before you broke it, and seems to be an unblossomed desert rose.";
                    } else {
                        response += "\nThe plant is in a green ceramic vase, and seems to be an unblossomed desert rose.";
                    }
                    response = addOpts(response, opts);
                } else if (subRoom.equals("window")) {
                    response += "\nOutside the window you see a desert, with small dry bushes and nothing else remotely alive. There is a crowbar on the ground, just out of reach.";
                    if (containsVal("string", inventory, foundItems) && !containsVal("crowbar", inventory, foundItems)) {
                        opts = setOpts(opts, "Go back", "Grab the crowbar (String)");
                    } else {
                        opts = setOpts(opts, "Go back");
                    }
                    response = addOpts(response, opts);
                } else {
                    opts = setOpts(opts, "Go back to the start", "Check out the plant","Go through the next door","Look out the window","Look under the table");
                    response += "\nThe next room has a single door, a small window, and a table with a potted plant on it. What a bizzare room.";
                    response = addOpts(response, opts);
                }
                break;
            case "chute":
                opts = setOpts(opts, "Go back","Jump down");
                response += "\nThe door reveals a drop, with the same plaster-white walls, directly down into darkness. You suppose the only way is down, if you're ready.";
                response = addOpts(response, opts);
                break;
            case "room2":
                opts = setOpts(opts, "Leave the room","Look out the window","Look at the paper","Look under the table", "Look in the dirt");
                response += "\nYou land in a room, but instead of white walls, the walls are now made from wood, and the floor appears to be made from dirt. There is a door, a window, and a table with a small piece of paper left on it. The window has no glass.";
                response = addOpts(response, opts);
                break;
            case "forest1":
                if (subRoom.equals("stump")) {
                } else if (subRoom.equals("river")) {
                    if (containsVal("water bucket", inventory, inventory)) {
                        opts = setOpts(opts, "Go back to your clearing", "Grab a stone", "Follow the river");
                        response += "\nYou see a thin, yet fast, stream. The water is clear enough to see smooth stones at the bottom. At least you've grabbed some water now.";
                        response = addOpts(response, opts);
                    } else if (containsVal("bucket", inventory, inventory) && !containsVal("water bucket", inventory, inventory)) {
                        opts = setOpts(opts, "Go back to your clearing", "Grab a stone", "Follow the river", "Use the bucket to grab water");
                        response += "\nYou see a thin, yet fast, stream. The water is clear enough to see smooth stones at the bottom. You could probably use the bucket to grab some of the water.";
                        response = addOpts(response, opts);
                    } else {
                        opts = setOpts(opts, "Go back to your clearing", "Grab a stone", "Follow the river");
                        response += "\nYou find  and see a thin, yet fast, stream. The water is clear enough to see smooth stones at the bottom. If only you had something to grab the water with...";
                        response = addOpts(response, opts);
                    }

                } else {
                    opts = setOpts(opts, "Go inside", "Head to the water", "Climb a tree", "Go to the stump");
                    response += "\nYou step outside into a forest. The trees are all thin and spaced, the ground coated in moss and rocks. You head water flowing in the distance.\nThe clearing around the small hut has a small stump, a handful of trees, and nothing else.";
                    response = addOpts(response, opts);
                    break;
                }
            case "river":
                
                break;
        }

        return response;
    }

    // Game Helper classes
    private static void resetGame() {
        currentRoom = "start";
        subRoom = "";
        inventory = new String[9];
        foundItems = new String[30];
        inventory = inventoryDebug(inventory);
        foundItems = inventoryDebug(foundItems);
    }

    public static String [] setOpts (String [] array, String ... opts){
        array = new String[array.length];
        Arrays.fill(array, "");

        for (int i = 0; i < opts.length; i++){
            array[i] = opts[i];
        }
        return array;
    }

    public static String addOpts (String response, String [] opts){
        for (int i = 0; i < opts.length; i++) {
            if (!opts[i].equals("")){
                response += "\n" + (i + 1) + ") " + opts[i];
            } else {
                break;
            }
        }
        return response;
    }

    public static boolean containsVal (String val, String [] array, String [] foundItems){
        array = inventoryDebug(array);
        foundItems = inventoryDebug(foundItems);
        for (int i = 0; i < array.length; i++){
            if (array[i].equals(val)){
                return true;
            }
        }
        for (int i = 0; i < foundItems.length; i++){
            if (foundItems[i].equals(val)){
                return true;
            }
        }
        return false;
    }

    public static String [] inventoryDebug (String[]inventory){
        // Replace *null* and *""* with *-*
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null || inventory[i].equals("")) {
                inventory[i] = "-";
            }
        }

        //Move objects down
        int index = 0;

        for(int i = 0; i < inventory.length; i++) {
            if (!inventory[i].equals("-")){
                inventory[index] = inventory [i];
                index++;
            }
        }

        while (index < inventory.length) {
            inventory[index] = "-";
            index++;
        }
        
        return inventory;
    }

    public static String [] removeItem(String item, String [] array){
        for (int i = 0; i < array.length; i++){
            if (array[i].equals(item)){
                array[i] = "-";
                return array;
            }
        }
        return array;
    }

    public static String[] addItem(String item, String[] inventory, String[] foundItems) {
        // Check if item has already been collected (prevent re-collection)
        for (int i = 0; i < foundItems.length; i++) {
            if (foundItems[i] != null && foundItems[i].equals(item)) {
                return inventory;  // Item already collected, do nothing
            }
        }
        
        // Find an empty slot in inventory and add the item
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i].equals("-")) {
                inventory[i] = item;
                break;
            }
        }
        
        // Mark the item as collected in foundItems (add to first available slot)
        for (int i = 0; i < foundItems.length; i++) {
            if (foundItems[i] == null || foundItems[i].equals("-")) {
                foundItems[i] = item;
                break;
            }
        }
        
        // Optional: Clean up arrays (ensure consistency)
        inventory = inventoryDebug(inventory);
        foundItems = inventoryDebug(foundItems);
        
        return inventory;
    }

    private static class GameInput {
        String input;
    }

    private static class GameResponse {
        String response;

        GameResponse(String response) {
            this.response = response;
        }
    }
}
