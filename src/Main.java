import static spark.Spark.*;
import java.nio.file.Paths;
import com.google.gson.Gson;

public class Main {
    private static Gson gson = new Gson();
    private static String currentRoom = "start";

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

    public static String runGame(String input) {
        String response = "";

        switch (currentRoom) {
            case "start":
                if (input.equals("start")) {
                    currentRoom = "entrance";
                    response = "You find yourself at the entrance of an ancient castle.\n" +
                              "The massive wooden doors creak open before you.\n" +
                              "You see a grand hallway with doors on either side.\n\n" +
                              "What would you like to do?\n" +
                              "1) Go left through the green door\n" +
                              "2) Go right through the red door\n" +
                              "3) Look around the entrance";
                } else {
                    response = "Welcome to the adventure! Type 'start' to begin your journey.";
                }
                break;

            case "entrance":
                switch (input) {
                    case "1":
                    case "go left":
                    case "green":
                        currentRoom = "green_room";
                        response = "You enter the green room. It's filled with lush plants and a mysterious glow.\n" +
                                  "In the center, you see a pedestal with a shiny key on it.\n\n" +
                                  "What would you like to do?\n" +
                                  "1) Take the key\n" +
                                  "2) Examine the plants\n" +
                                  "3) Go back to the entrance";
                        break;
                    case "2":
                    case "go right":
                    case "red":
                        currentRoom = "red_room";
                        response = "You enter the red room. The walls are adorned with ancient tapestries.\n" +
                                  "A suit of armor stands in the corner, and there's a locked chest on the floor.\n\n" +
                                  "What would you like to do?\n" +
                                  "1) Examine the armor\n" +
                                  "2) Try to open the chest\n" +
                                  "3) Go back to the entrance";
                        break;
                    case "3":
                    case "look":
                        response = "The entrance hall is grand and imposing. Torchlight flickers on the stone walls.\n" +
                                  "You notice intricate carvings depicting heroic deeds.\n\n" +
                                  "What would you like to do?\n" +
                                  "1) Go left through the green door\n" +
                                  "2) Go right through the red door\n" +
                                  "3) Look around the entrance";
                        break;
                    default:
                        response = "I don't understand that command. Please choose:\n" +
                                  "1) Go left through the green door\n" +
                                  "2) Go right through the red door\n" +
                                  "3) Look around the entrance";
                }
                break;

            case "green_room":
                switch (input) {
                    case "1":
                    case "take":
                    case "key":
                        response = "You take the shiny key! It feels warm in your hand.\n" +
                                  "This might be useful later.\n\n" +
                                  "What would you like to do?\n" +
                                  "1) Examine the plants\n" +
                                  "2) Go back to the entrance";
                        break;
                    case "2":
                    case "examine":
                    case "plants":
                        response = "The plants seem to glow with an inner light. They appear healthy and well-tended.\n" +
                                  "You notice they form a pattern that looks like a door.\n\n" +
                                  "What would you like to do?\n" +
                                  "1) Take the key\n" +
                                  "2) Examine the plants\n" +
                                  "3) Go back to the entrance";
                        break;
                    case "3":
                    case "back":
                        currentRoom = "entrance";
                        response = "You return to the entrance hall.\n\n" +
                                  "What would you like to do?\n" +
                                  "1) Go left through the green door\n" +
                                  "2) Go right through the red door\n" +
                                  "3) Look around the entrance";
                        break;
                    default:
                        response = "I don't understand that command. Please choose:\n" +
                                  "1) Take the key\n" +
                                  "2) Examine the plants\n" +
                                  "3) Go back to the entrance";
                }
                break;

            case "red_room":
                switch (input) {
                    case "1":
                    case "examine":
                    case "armor":
                        response = "The suit of armor is old but well-maintained. It looks like it could spring to life at any moment!\n" +
                                  "You notice a small inscription on the helmet.\n\n" +
                                  "What would you like to do?\n" +
                                  "1) Examine the armor\n" +
                                  "2) Try to open the chest\n" +
                                  "3) Go back to the entrance";
                        break;
                    case "2":
                    case "open":
                    case "chest":
                        response = "The chest is locked! You need a key to open it.\n" +
                                  "Maybe there's one somewhere else in the castle.\n\n" +
                                  "What would you like to do?\n" +
                                  "1) Examine the armor\n" +
                                  "2) Try to open the chest\n" +
                                  "3) Go back to the entrance";
                        break;
                    case "3":
                    case "back":
                        currentRoom = "entrance";
                        response = "You return to the entrance hall.\n\n" +
                                  "What would you like to do?\n" +
                                  "1) Go left through the green door\n" +
                                  "2) Go right through the red door\n" +
                                  "3) Look around the entrance";
                        break;
                    default:
                        response = "I don't understand that command. Please choose:\n" +
                                  "1) Examine the armor\n" +
                                  "2) Try to open the chest\n" +
                                  "3) Go back to the entrance";
                }
                break;

            default:
                response = "You find yourself in an unknown location. Something went wrong!";
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
