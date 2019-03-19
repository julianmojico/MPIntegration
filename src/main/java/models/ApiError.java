package models;

import com.google.gson.JsonElement;
import com.mercadopago.exceptions.MPValidationException;
import org.apache.http.HttpStatus;
import spark.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ApiError extends ApiResponse {

    private int status;
    private String message = "";
    private List<String> errors = new ArrayList<String>();

    public ApiError(int status, String message, String error) {
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

    public ApiError() {

    }

    public ApiResponse buildValidationError(Response response, MPValidationException mpe) {

        //todo: revisar acoplamiento del object Response propio de Spark con un eventual cambio de framework
        this.status =  HttpStatus.SC_BAD_REQUEST;
        mpe.getColViolations().forEach( violation -> this.errors.add(violation.toString()));
        this.message = "Validation exception; malformed body";
        response.status(this.status);
        response.body(render(this));
        return this;
    }

    public ApiResponse buildGeneralError(Response response, Exception e) {

        this.status =  HttpStatus.SC_INTERNAL_SERVER_ERROR;
        if (e == null || e.getMessage() == null) {
            this.errors = Arrays.asList("Exception message is null");
        }
        this.message = "The createPref cannot be processed due to errors";
        response.status(this.status);
        response.body(render(this));
        return this;
    }
}