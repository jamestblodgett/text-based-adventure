

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        staticFiles.externalLocation("public");

        get("/hello", (req, res) -> {
            res.type("application/json");
            return "{\"message\": \"Hello from Java!\"}";
        });

        post("/echo", (req, res) -> {
            res.type("application/json");
            return "{\"you_sent\": \"" + req.body() + "\"}";
        });
    }
}
