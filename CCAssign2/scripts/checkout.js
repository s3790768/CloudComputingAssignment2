// Create a Stripe client.
var stripe = Stripe('pk_test_51IAAvPEhoYdXR4dITZEpLuvn35zKClUWVbZN74yjrceC9dZGwocoLdzClk1nJbVLtCLdGFC8nR7exsvgi091vwRo00UtuXxjUO');

// Create an instance of Elements.
var elements = stripe.elements();

// Custom styling can be passed to options when creating an Element.
// (Note that this demo uses a wider set of styles than the guide below.)
var style = {
    base: {
        color: '#32325d',
        fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
        fontSmoothing: 'antialiased',
        fontSize: '16px',
        '::placeholder': {
            color: '#aab7c4'
        }
    },
    invalid: {
        color: '#fa755a',
        iconColor: '#fa755a'
    }
};

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

    stripe.createToken(card).then(function(result) {
        if (result.error) {
            // Inform the user if there was an error.
            var errorElement = document.getElementById('card-errors');
            errorElement.textContent = result.error.message;
        } else {
            // Send the token to your server.
            stripeTokenHandler(result.token);
        }
    });
});

// Submit the form with the token ID.
function stripeTokenHandler(token) {
    // Insert the token ID into the form so it gets submitted to the server
    var form = document.getElementById('payment-form');
    var hiddenInput = document.createElement('input');
    hiddenInput.setAttribute('type', 'hidden');
    hiddenInput.setAttribute('name', 'stripeToken');
    hiddenInput.setAttribute('value', token.id);
    form.appendChild(hiddenInput);
    console.log("token: "+ token.id)
    // Submit the form
    var userId = getCookie("userId")
    var details = {
        'stripeId': token.id
    };
    var formBody = [];
    for (var property in details) {
        var encodedKey = encodeURIComponent(property);
        var encodedValue = encodeURIComponent(details[property]);
        formBody.push(encodedKey + "=" + encodedValue);
    }
    formBody = formBody.join("&");

    fetch('http://localhost:8080/user/' + userId, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        body: formBody
    })


    form.submit();
}