package controllers;

import spark.ModelAndView;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

public class ViewController implements TemplateViewRoute  {
    private static ViewController ourInstance = new ViewController();

    public static ViewController getInstance() {
        return ourInstance;
    }

    private ViewController() {
    }

    @Override
    public ModelAndView handle(spark.Request request, Response response) throws Exception {
        Map<String, String> map = new HashMap<>();
        return new ModelAndView(map,"start");
    }
}
