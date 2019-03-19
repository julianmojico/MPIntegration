package controllers;

import com.google.gson.Gson;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPValidationException;
import com.mercadopago.resources.Preference;
import models.ApiError;
import models.ApiMessage;
import org.apache.http.HttpStatus;
import spark.Request;
import spark.Response;
import models.ApiResponse;


public class PreferencesController {

    private static Gson gson = new Gson();


    /**
     * Provides the state of the migration: whether it's active or not and the
     * percent of migrated users from configured total.
     *
     * @param req      Request received
     * @param response Response to be built
     * @return a Preference.
     */
    public String createPref(Request req, Response response) throws MPException {


        response.type("application/json");
        Preference preference = gson.fromJson(req.body(), Preference.class);
        if (preference == null) {
            return new ApiError(HttpStatus.SC_BAD_REQUEST, "Check JSON syntax and preferences attributes", "Could not parse request body").render();
        }
        try {
            Preference savedPref = preference.save();
            int status = savedPref.getLastApiResponse().getStatusCode();
            response.status(status);

            if (status != 201){
                return new ApiError(status,savedPref.getLastApiResponse().getReasonPhrase(), savedPref.getLastApiResponse().getStringResponse()).render();
            }
            return new ApiMessage((short) status, savedPref.getLastApiResponse().getJsonElementResponse() , savedPref.getClass().getTypeName()).render();

        } catch (MPValidationException mpe) {
            return new ApiError().buildValidationError(response, mpe).render();
        } catch (Exception e) {
            return new ApiError().buildGeneralError(response, e).render();

        }
    }

}