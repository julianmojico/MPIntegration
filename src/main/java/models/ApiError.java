package models;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mercadopago.exceptions.MPValidationException;
import org.apache.http.HttpStatus;
import spark.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiError extends ApiResponse {

    private static final Gson gson = new Gson();
    private int status;
    private JsonElement message;
    private List<String> errors = new ArrayList<String>();

    public ApiError(int status, JsonElement message, String error) {
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

    public ApiError(int status, String message, String error) {
        this.status = status;
        this.message = gson.toJsonTree("{\"message\":\""+ message +"\"}");
        errors = Arrays.asList(error);
    }

    public ApiError() {

    }

    /*Custom Exception case for Bad request */
    public ApiError buildValidationError(Response response, MPValidationException mpe) {

        this.status =  HttpStatus.SC_BAD_REQUEST;
        mpe.getColViolations().forEach( violation -> this.errors.add(violation.toString()));
        this.message = gson.toJsonTree("Validation exception; malformed body");
        response.status(this.status);
        String bodyResponse;
        try {
            bodyResponse = render(this);
        } catch (Exception e){
            e.printStackTrace();
            bodyResponse = "Exception caught while mapping ApiError to json";
        }
        response.body(bodyResponse);
        return this;
    }

    /*Custom Exception case when catched Exception has nullmessage */
    public ApiError buildGeneralError(Response response, Exception e) {

        this.status =  HttpStatus.SC_INTERNAL_SERVER_ERROR;
        if (e == null || e.getMessage() == null) {
            this.errors = Arrays.asList("Exception message is null");
        }
        this.message = gson.toJsonTree("The createPref cannot be processed due to errors");
        response.status(this.status);
        String bodyResponse;
        try {
            bodyResponse = render(this);
        } catch (Exception ex){
            ex.printStackTrace();
            bodyResponse = "Exception caught while mapping ApiError to json";
        }
        response.body(bodyResponse);
        return this;
    }
}