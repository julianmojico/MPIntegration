function fillForm() {

    $('#email').val("default+348496119@mercadolibre.com");
    $('#cardNumber').val("371594625346344");
    $('#securityCode').val("1234");
    $('#cardExpirationMonth').val("12");
    $('#cardExpirationYear').val("20");
    $('#cardholderName').val("APROnombre");
    $('#docNumber').val("12323345");
    $('#transactionAmount').val("100");
    $('#installments').val("1");


};

function setPaymentMethodInfo(status, response) {

    var paymentMethod = document.querySelector("input[name=paymentMethodId]");

    if (status == 200) {
        var form = document.getElementById("pay");
        var bin = $('#cardNumber').val();
        var amount = $('#amount').val();

        paymentMethod.setAttribute('value', response[0].id);
        console.log("Payment method set to:" + paymentMethod.value)
        form.appendChild(paymentMethod);

        Mercadopago.getInstallments({
            "bin": bin,
            "amount": amount
        }, setInstallmentInfo);

    } else {
        paymentMethod.value = response[0].id;
        console.log("Payment method set to:" + paymentMethod.value)
    }
}


doSubmit = false;

function doPay(event) {
    event.preventDefault();
    if (!doSubmit) {
        var $form = document.querySelector('#pay');
        ;
        Mercadopago.createToken($form, sdkResponseHandler);
        return false;
    }
};

function sdkResponseHandler(status, response) {
    if (status != 200 && status != 201) {
        alert("verify filled data");
    } else {
        var form = document.querySelector('#pay');
        var card = document.createElement('input');
        card.setAttribute('name', 'token');
        card.setAttribute('type', 'hidden');
        card.setAttribute('value', response.id);
        console.log("Card Token: " + response.id);
        form.appendChild(card);
        doSubmit = true;
        form.submit();
    }
};
var payloadDiv = document.querySelector('#requestPayload');

function preview() {
    payloadDiv.innerText = JSON.stringify($('#pay').serializeArray());
    payloadDiv.innerText += JSON.stringify($('#pay[type=hidden]').serializeArray());
}


function guessingPaymentMethod(event) {
    var bin = $('#cardNumber').val();
    if (bin.length >= 6) {
        Mercadopago.getPaymentMethod({
            "bin": bin
        }, setPaymentMethodInfo);
    }
}

function setInstallmentInfo(status, response) {

    if (response && response.length>0){

        var input = document.getElementById('issuerId');
        input.value = response[0].issuer.id;
        //TODO: Crear elementos del DOM para las options de Installments
    }

}

$(document).ready(function () {

    Mercadopago.setPublishableKey("TEST-f4dae8c9-bfe8-49f1-b8c8-f0e8c6da5a28");
    Mercadopago.getIdentificationTypes();
    fillForm();
    $('#cardNumber').change(guessingPaymentMethod);
    document.querySelector('#pay').addEventListener('submit', doPay, false);
    $('input').change(preview);
    $('input[type=hidden]').change(preview);
});

