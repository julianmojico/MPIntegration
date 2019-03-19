package models;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public abstract class ApiResponse implements ResponseTransformer {

    private static final Gson gson = new Gson();


    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

    public String render(){
        return gson.toJson(this);
    }
}
