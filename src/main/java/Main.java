import com.mercadopago.exceptions.MPException;
import routes.RoutesHandler;
import services.MPService;
import spark.Spark;

import static spark.Spark.port;

public class Main {


    public static void main(String[] args) {

        port(80);
        Spark.staticFiles.location("/public");

        try {
            //MPService.configBasicCheckout();
            MPService.configCustomCheckout();
        } catch (MPException e) {
            e.printStackTrace();
        }

        RoutesHandler routesHandler = new RoutesHandler();
        routesHandler.addRoutes();


        //DefaultHandlers.setupRoutes();



        //redirect.get("/", "https://www.mercadopago.com/mla/checkout/start?pref_id=77725206-100eecea-91b3-4692-b505-c13111e96f49");

    }
}