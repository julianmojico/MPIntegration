# MPIntegration
Custom Web Checkout integration using MercadoPago APIs

### Routes
/ -> Landing site where different custom payments can be found

/api/preference -> JSON request, creates a preference. 

/api/payment  -> JSON request, creates a payment. 

### Sample request
```
{
	"email":"john@domain.com",
	"amount":100,
	"installments":1,
	"paymentMethodId":"master",
	"token":"b5ecde7e36fa605c3e1a1b3cf524197d",
	"paymentTypeId":"credit_card"
}
```


#### Navigate to webapp
http://localhost:8080/
