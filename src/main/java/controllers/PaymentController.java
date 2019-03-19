package controllers;

import com.google.gson.Gson;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import services.MPService;
import spark.Request;
import spark.Response;

public class PaymentController {

    private static PaymentController ourInstance = new PaymentController();
    private static MPService mpService = MPService.getInstance();
    private Gson gson = new Gson();

    private PaymentController() {
    }

    public static PaymentController getInstance() {
        return ourInstance;
    }

    public Object processPayment(Request req, Response res) {

        Payment payment = gson.fromJson(req.body(), Payment.class);

        try {
            mpService.submitPayment(payment);
        } catch (MPException e) {
            e.printStackTrace();
        }
        //        mpService.createPayment();

        return payment.getStatus();
    }
}
