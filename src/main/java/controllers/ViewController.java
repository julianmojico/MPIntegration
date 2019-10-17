package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            map.put("response",body);

        return new ModelAndView(map,"responserender");
    }

    public ModelAndView payflow(Request request, Response response){
        Map<String, String> map = new HashMap<>();
        return new ModelAndView(map, "payflow");
    }

    public String renderMarketplace(Request request, Response response){
        try {
            URL url = getClass().getResource("/public/marketplace.html");
            Path path = Paths.get(url.toURI());
            return new String(Files.readAllBytes(path), Charset.defaultCharset());
        } catch (IOException | URISyntaxException e) {
            // Add your own exception handlers here.
        }
        return null;
    }
}
