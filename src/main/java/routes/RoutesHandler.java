package routes;

import configurations.JsonTransformer;
import controllers.PaymentController;
import controllers.PreferencesController;
import controllers.ViewController;
import spark.RouteGroup;
import spark.Spark;
import spark.template.jade.JadeTemplateEngine;

public class RoutesHandler implements RouteGroup {

    JsonTransformer jsonTransformer = new JsonTransformer();
    //TODO: Make singleton
    PreferencesController preferencesController = new PreferencesController();
    ViewController viewController = ViewController.getInstance();
    PaymentController paymentController = PaymentController.getInstance();

    @Override
    public void addRoutes() {

        Spark.post("/preference", "application/json", preferencesController::createPref);
        Spark.get("/", viewController::handle , new JadeTemplateEngine());
        Spark.post("/payment", paymentController::processPayment, jsonTransformer);
//        Spark.exception();
    }
}
