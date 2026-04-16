package main.java.app;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        // Serve static files from the "public" folder
        staticFiles.externalLocation("public");

        // Example API endpoint
        get("/hello", (req, res) -> {
            res.type("application/json");
            return "{\"message\": \"Hello from Java!\"}";
        });

        // Example POST endpoint
        post("/echo", (req, res) -> {
            res.type("application/json");
            return "{\"you_sent\": \"" + req.body() + "\"}";
        });
    }
}
