# MPIntegration
Custom Web Checkout integration using MercadoPago APIs

### Instructions
1. Import as regular gradle project
2. Run Main.class as Java Application
3. Navigate to http://localhost:8080/

### Routes
/ -> Landing site where different custom payments can be found

/api/preference -> JSON request, creates a preference. 

/api/payment  -> JSON request, creates a payment. 

### Sample POST request to Payments API
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
