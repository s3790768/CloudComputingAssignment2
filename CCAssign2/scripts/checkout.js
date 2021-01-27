// Create a Stripe client.
var stripe = Stripe('pk_test_51IAAvPEhoYdXR4dITZEpLuvn35zKClUWVbZN74yjrceC9dZGwocoLdzClk1nJbVLtCLdGFC8nR7exsvgi091vwRo00UtuXxjUO');

// Create an instance of Elements.
var elements = stripe.elements();

// Create an instance of the card Element.
var card = elements.create('cardNumber', {
    classes : {
        base : "form-control",
        invalid : "error"
    }
});

var cvc = elements.create('cardCvc', {
    classes : {
        base : "form-control",
        invalid : "error"
    }
});

var exp = elements.create('cardExpiry', {
    classes : {
        base : "form-control",
        invalid : "error"
    }
});

// Add an instance of the card Element into the `card-element` <div>.
card.mount('#cardNumber');
cvc.mount('#cardCvc');
exp.mount('#cardExpiry');

// Handle real-time validation errors from the card Element.
card.on('change', function(event) {
    var displayError = document.getElementById('card-errors');
    if (event.error) {
        displayError.textContent = event.error.message;
    } else {
        displayError.textContent = '';
    }
});

// Handle form submission.
var form = document.getElementById('payment-form');
form.addEventListener('submit', function(event) {
    event.preventDefault();
    var url_string = window.location.href
    var url = new URL(url_string);
    var secret = url.searchParams.get("secret");
    var parcelId = url.searchParams.get("parcelId");
    stripe.confirmCardPayment(secret, {
            payment_method: {
                card: card
            }
        })
        .then(function(result) {
            if (result.error) {
                alert(result.error.message);
            } else {
                const jsonObject =  JSON.parse(JSON.stringify(result));
                // TODO: Replace this url
                const backendUrl = "https://cloudcomputinga2.ts.r.appspot.com/parce/paid"
                const data = 'parcelId=' +  parcelId + '&paymentIntent=' + jsonObject.paymentIntent.id;
                xmlhttp = new XMLHttpRequest();
                xmlhttp.open('POST', backendUrl, true);
                xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xmlhttp.send(data);
                window.location.replace("viewOrder.php");
            }
        });
});