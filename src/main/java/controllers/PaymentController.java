package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import models.ApiError;
import models.ApiMessage;
import models.ApiResponse;
import models.PaymentDTO;
import org.apache.http.HttpStatus;
import services.MPService;
import spark.Request;
import spark.Response;
import util.JsonUtils;

import java.util.HashMap;

public class PaymentController {

    private static final Gson gson = new Gson();
    private static PaymentController ourInstance = new PaymentController();
    private static MPService mpService = MPService.getInstance();

    private PaymentController() {
    }

    public static PaymentController getInstance() {
        return ourInstance;
    }

    public String processPayment(Request request, Response response) {

        ApiResponse body = null;

        try {
            PaymentDTO paymentDTO = parsePayment(request);
            paymentDTO.isValidPayment();
            Payment payment = mpService.createCardPayment(paymentDTO);
            mpService.submitPayment(payment);
            int status = payment.getLastApiResponse().getStatusCode();
            response.status(status);

            if (status != 200 && status != 201) {
                body = new ApiError(status, payment.getLastApiResponse().getJsonElementResponse(), payment.getLastApiResponse().getReasonPhrase());
            }

            //success

            body = new ApiMessage(status, gson.toJsonTree(payment), Payment.class.getTypeName());

        } catch (MPException mpe) {
            System.out.println(mpe.getStackTrace());
            body = new ApiError(HttpStatus.SC_BAD_GATEWAY, "MP API failed to process the request", mpe.toString());
        } catch (JsonSyntaxException jse) {
            jse.printStackTrace();
            body = new ApiError(HttpStatus.SC_BAD_REQUEST, "Input payment data is wrong or missing", jse.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            body = new ApiError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Internal Server error processing the request", e.getClass().toString());
        } finally {
            response.body(JsonUtils.objectToJson(body));
            return body.render();
        }
    }

    /*Serialize Payment object from both JSON or Form encoding*/
    public PaymentDTO parsePayment(Request request) {

        PaymentDTO paymentDTO;

        if (request.contentType().equals("application/json")) {
            paymentDTO = gson.fromJson(request.body(), PaymentDTO.class);
        } else {
            request.contentType().equals("application/x-www-form-urlencoded");
            HashMap<String, String> map = JsonUtils.formPayloadToMap(request.body());
            String jsonDto = JsonUtils.objectToJson(map);
            paymentDTO = gson.fromJson(jsonDto, PaymentDTO.class);
        }
        return paymentDTO;
    }

}
