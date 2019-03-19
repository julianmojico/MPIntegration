package controllers;

import static spark.Spark.internalServerError;
import static spark.Spark.notFound;

public class DefaultHandlers {

    public static void setupRoutes(){

        internalServerError((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Custom 500 handling\"}";
        });

        notFound((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Custom 404\"}";
        });
    }
}
