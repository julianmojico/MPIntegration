package routes;

import configurations.JsonTransformer;
import controllers.PaymentController;
import controllers.PreferencesController;
import controllers.ViewController;
import spark.RouteGroup;
import spark.Spark;
import spark.template.jade.JadeTemplateEngine;

import static spark.Spark.path;

public class RoutesHandler implements RouteGroup {

    JsonTransformer jsonTransformer = new JsonTransformer();
    //TODO: Make singleton
    PreferencesController preferencesController = new PreferencesController();
    ViewController viewController = ViewController.getInstance();
    PaymentController paymentController = PaymentController.getInstance();

    @Override
    public void addRoutes() {

        path("/front", () -> {
            Spark.get("/mpflow", viewController::handle, new JadeTemplateEngine());
            Spark.get("/payflow", viewController::payflow, new JadeTemplateEngine());
            Spark.post("/payment", viewController::doPayment, new JadeTemplateEngine());
//            Spark.get("/mpflow", viewController::handle);
//            Spark.get("/payflow", viewController::payflow);

        });

        path("/api", () -> {
            Spark.post("/preference", "application/json", preferencesController::createPref);
            Spark.post("/payment", "application/json", paymentController::processPayment);

        });

    }
}

