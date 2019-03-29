package models;

import com.google.gson.JsonSyntaxException;

public class PaymentDTO {

    private String token;
    private float amount;
    private String paymentMethodId;
    private String description;
    private Integer installments;
    private String email;
    private PaymentTypeId paymentTypeId;
    public enum PaymentTypeId {
        account_money,
        ticket,
        bank_transfer,
        atm,
        credit_card,
        debit_card,
        prepaid_card
    }
    private String issuerId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }

    public boolean isValidPayment() throws JsonSyntaxException {

        if (email instanceof String && !email.isEmpty() && description instanceof String && amount>0.0f
                    && !description.isEmpty()
            ) {
                return true;
            }
         else {
            throw new JsonSyntaxException("Payment fields missing or wrong. Amount must be positive, payer email and description not null.");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PaymentTypeId getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(PaymentTypeId paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }
}
