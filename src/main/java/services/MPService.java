package services;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.Payer;
import controllers.PaymentController;
import org.slf4j.spi.SLF4JServiceProvider;

public class MPService {
    private static MPService ourInstance = new MPService();

    public static MPService getInstance() {
        return ourInstance;
    }

    private MPService() {
    }

    public Payment createPayment(String body){

        Payment payment = new Payment()
                .setTransactionAmount(100f)
                .setToken("your_cardtoken")
                .setDescription("description")
                .setInstallments(1)
                .setPaymentMethodId("visa")
                .setPayer(new Payer().setEmail("dummy_email"));
        return payment;

    }

    public Payment submitPayment(Payment payment) throws MPException {

            payment.save();
            return payment;

    }

    public static void configBasicCheckout() throws MPException {
        try {
            MercadoPago.SDK.setClientSecret("Cfylu95q3uerjGC7eXk3dhYS36QvFY0y");
            MercadoPago.SDK.setClientId("5595646364578069");
        } catch (MPException e) {
            e.printStackTrace();
        }

    }

    public static void configCustomCheckout() throws MPException  {
        MercadoPago.SDK.setAccessToken("ACCESS_TOKEN");
    }

}
