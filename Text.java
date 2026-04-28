import java.util.Scanner;
import java.util.Arrays;

public class Text {
    private static String currentRoom = "start";
    private static String subRoom = "";
    private static String [] inventory = new String[9];
    private static String [] opts = new String[5];

    // ---------------------------------------------
    // Input/Output Handling. Main function.       |
    // ---------------------------------------------
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        
        //DEBUG
        inventory[3] = "crowbar";
        //DEBUG

        System.out.println("Welcome to the text-adventure game!\r\n" + //
                        "When you play, you will receive a prompt. Based on this prompt, you can enter a response in the box, and the game will continue based on what you entered. For example:\r\n" + //
                        "\r\n" + //
                        "You are in a dark room with a green door and a red door. What do you do?\r\n" + //
                        "1) Open the green door\r\n" + //
                        "2) Open the red door\r\n" + //
                        "3) Cry\r\n" + //
                        "4) Check your inventory - this is always available\r\n" + //
                        "\r\n" + //
                        "If you enter \"1\", the game will continue with you going through the green door. You can always enter \"inventory\" to check your inventory, \"Help\", which takes you to this page, or \"Hard-Reset\", which will reset the game. Don't do that.\n\n");
        while (true){
            String input = scanner.nextLine();
            System.out.println(runGame(input));
        }
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

        switch(currentRoom){
            case "start":
                opts = setOpts(opts, "Go through the door");
                response += "\nYou find yourself in a white, cube-shaped room. It is well-lit, but you don't have any idea where the light is coming from.\r\n" + //
                    "Actually, come to think of it, you don't even know how you got here. Looking around the room, you only see a single door.\r\n";
                response = addOpts(response, opts);
                switch (input) {
                    case "1":
                        response += "\nYou go through the door.";
                        currentRoom = "room1";
                        break;
                }
                break;
            case "room1":
                if (subRoom.equals("room1Plant")){
                    
                    if (containsVal("crowbar",inventory)){
                        opts = setOpts(opts, "Go Back", "Push over the vase", "Break the vase (crowbar)");
                    } else {
                        opts = setOpts(opts, "Go Back", "Push over the vase");
                    } 
                    response += response += "\nThe plant is in a green ceramic vase, and seems to be an unblossomed desert rose.";
                    response = addOpts(response, opts);
                    if (containsVal("crowbar", inventory)){
                        switch (input){
                        case "1":
                            subRoom = "";
                            break;
                        case "2":
                            response += "\nThe vase seems to be glued to the table.";
                            subRoom = "room1Plant";
                            break;
                        }
                    } else {
                        switch (input){
                            case "1":
                                subRoom = "";
                                break;
                            case "2":
                                response += "\nThe vase seems to be glued to the table.";
                                subRoom = "room1Plant";
                                break;
                        }
                    }
                } else {
                    opts = setOpts(opts, "Go back to the start", "Check out the plant","Go through the next door","Look out the window","Look under the table");
                    response += "\nThe next room has a single door, a small window, and a table with a potted plant on it. What a bizzare room.";
                    response = addOpts(response, opts);
                    switch (input){
                        case "1":
                            response += "\n You go back to the start";
                            currentRoom = "start";
                        case "2":
                            subRoom = "room1Plant";

                    }
                }
                break;
        }

        return response;
    }
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
            if (!opts[i].equals("")){
                response += "\n" + (i + 1) + ") " + opts[i];
            } else {
                break;
            }
        }
        return response;
    }

    public static boolean containsVal (String val, String [] array){
        for (int i = 0; i < array.length; i++){
            if (array[i].equals(val)){
                return true;
            }
        }
        return false;
    }
}
