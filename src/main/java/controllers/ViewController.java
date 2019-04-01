package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

public class ViewController implements TemplateViewRoute  {
    private static ViewController ourInstance = new ViewController();

    public static ViewController getInstance() {
        return ourInstance;
    }

    public static final PaymentController paymentController = PaymentController.getInstance();

    private ViewController() {
    }

    @Override
    public ModelAndView handle(spark.Request request, Response response) throws Exception {

        response.type("text/html");
        Map<String, String> map = new HashMap<>();
        return new ModelAndView(map,"mpflow");
    }

    public ModelAndView doPayment(Request request, Response response){

        String body = paymentController.processPayment(request,response);
        HashMap<String,Object> map = new HashMap<>();
            //map = JsonUtils.mapper.readValue(body,HashMap.class);ç
            map.put("response",body);

        return new ModelAndView(map,"responserender");
    }

    public ModelAndView payflow(Request request, Response response){
        Map<String, String> map = new HashMap<>();
        return new ModelAndView(map, "payflow");
    }
}
