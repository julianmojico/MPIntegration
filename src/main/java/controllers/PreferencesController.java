package controllers;

import com.google.gson.Gson;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPValidationException;
import com.mercadopago.resources.Preference;
import models.ApiError;
import models.ApiMessage;
import models.ApiResponse;
import org.apache.http.HttpStatus;
import spark.Request;
import spark.Response;


public class PreferencesController {

    private static final Gson gson = new Gson();


    /**
     * Provides the state of the migration: whether it's active or not and the
     * percent of migrated users from configured total.
     *
     * @param req      Request received
     * @param response Response to be built
     * @return a Preference.
     */
    public String createPref(Request req, Response response) throws MPException {

        ApiResponse apiResponse = null;
        response.type("application/json");
        Preference preference = gson.fromJson(req.body(), Preference.class);
        if (preference == null) {
            apiResponse = new ApiError(HttpStatus.SC_BAD_REQUEST, "Check JSON syntax and preferences attributes", "Could not parse request body");
        }
        try {
            Preference savedPref = preference.save();
            int status = savedPref.getLastApiResponse().getStatusCode();
            response.status(status);

            if (status != 201) {
                apiResponse = new ApiError(status, savedPref.getLastApiResponse().getReasonPhrase(), savedPref.getLastApiResponse().getStringResponse());
            }
            apiResponse = new ApiMessage((short) status, savedPref.getLastApiResponse().getJsonElementResponse(), savedPref.getClass().getTypeName());

        } catch (MPValidationException mpe) {
            apiResponse = new ApiError().buildValidationError(response, mpe);
        } catch (Exception e) {
            apiResponse = new ApiError().buildGeneralError(response, e);

        } finally {
            //TODO: DISCUSION, ME CONVIENE PASAR A JSON CON EL SINGLETON O CON EL METODO DE APIRESPONSE?
//           return JsonUtils.objectToJson(apiResponse);
            return apiResponse.render();
        }
    }

}