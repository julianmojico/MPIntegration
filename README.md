# MPIntegration
Custom Web Checkout integration using MercadoPago APIs.
Static frontend + Java Spark framework, which uses Jetty container.
Checkout types: Redirect, widget, iframe, custom checkout

### Instructions
1. Import as regular gradle project
2. Run Main.class as Java Application
3. Navigate to http://localhost/

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
#### Files description
- **pref1.html** =  static html used for custom checkout
- **payflow.jade** = template used for landing which shows multiple products and checkouts. Here you can setup the preferenceId.
- **responserenderer.jade** = template used to show Payments API response after payment is done.
- **main.js** = contains js functions to process custom checkout payment (card tokenization, sdkhandling in general). This also contains MercadoPago's API key.

#### Routes
RoutesHandler.java = Defines controller's routes

- **POST** */api/preference* = Preference creation
- **POST** */api/payment* = General Payment processing
- **POST** */front/payment* = Frontend Payment processing
