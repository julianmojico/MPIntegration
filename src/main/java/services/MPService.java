package services;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.Payer;
import models.PaymentDTO;

public class MPService {
    private static MPService ourInstance = new MPService();

    public static MPService getInstance() {
        return ourInstance;
    }

    private MPService() {
    }

    public Payment createCardPayment(PaymentDTO paymentDto){

        Payment payment = new Payment()
                .setTransactionAmount(paymentDto.getAmount())
                .setToken(paymentDto.getToken())
                .setIssuerId(paymentDto.getIssuerId())
                .setDescription(paymentDto.getDescription())
                .setInstallments(paymentDto.getInstallments())
                .setPaymentMethodId(paymentDto.getPaymentMethodId())
                .setPayer(new Payer().setEmail(paymentDto.getEmail()));
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
        MercadoPago.SDK.setAccessToken("TEST-5595646364578069-040415-c03000222bc5d69bf6d4200375936fa6-77725206");
    }

}
