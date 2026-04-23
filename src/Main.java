import java.nio.file.Paths;

import com.google.gson.Gson;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.util.Arrays;

public class Main {
    private static Gson gson = new Gson();
    private static String currentRoom = "start";
    private static String [] inventory = new String[9];
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
        if (input.equals("inventory")) {
            if (inventory[0] == null) {
                inventory[0] = "Really cool air";
            }
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i] == null) {
                    inventory[i] = "More air";
                }
            }
            response = "You are carrying:\n " + String.join(",\n ", inventory);
        }
        if (input.equals("help")) {
            response = "Welcome to the text-adventure game!\nWhen you play, you will receive a prompt. Based on this prompt, you can enter a response in the box, and the game will continue based on what you entered. For example:\n\nYou are in a dark room with a green door and a red door. What do you do?\n1) Open the green door\n2) Open the red door\n3) Cry\n4) Check your inventory - this is always available\n\nIf you enter \"1\", the game will continue with you going through the green door. You can always enter \"inventory\" to check your inventory, \"Help\", which takes you to this page, or \"Hard-Reset\", which will reset the game. Don't do that.";
        }
        if (input.equals("hard-reset")) {
            resetGame();
            response = "Game reset. You are back at the start.";
        }

        



        return response;
    }

    // Game Helper classes
    private static void resetGame() {
        currentRoom = "start";
        inventory = new String[9];
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
            response += "\n" + (i + 1) + ") " + opts[i];
        }
        return response;
    }


    // Helper classes for JSON serialization
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
