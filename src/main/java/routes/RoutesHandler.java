package routes;

import controllers.PaymentController;
import controllers.PreferencesController;
import controllers.ViewController;
import spark.RouteGroup;
import spark.Spark;
import spark.template.jade.JadeTemplateEngine;

import static spark.Spark.path;

public class RoutesHandler implements RouteGroup {

    PreferencesController preferencesController = new PreferencesController();
    ViewController viewController = ViewController.getInstance();
    PaymentController paymentController = PaymentController.getInstance();

    @Override
    public void addRoutes() {


        Spark.get("/", viewController::payflow, new JadeTemplateEngine());

        path("/front", () -> {
            Spark.post("/payment", viewController::doPayment, new JadeTemplateEngine());

        });

        path("/api", () -> {
            Spark.post("/preference", "application/json", preferencesController::createPref);
            Spark.post("/payment", "application/json", paymentController::processPayment);

        });

    }
}

