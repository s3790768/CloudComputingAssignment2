<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout</title>
    <script src="https://js.stripe.com/v3/"></script>
    <?php require_once('includes/head.inc.php'); ?>
    <script src="scripts/cookies.js"></script>
    <link href="styles/checkout.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-sm-offset-3">
                <h2>Checkout - Parcelize</h2>
                <b id="price"></b>
                <form method="post" id="payment-form">
                    <div class="form-group">
                        <label for="cardNumber">Credit or Debit card Number</label>
                        <div id="cardNumber">
                            <!-- A Stripe Element will be inserted here. -->
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label for="cardCvc">CVC</label>
                                <div id="cardCvc">
                                    <!-- A Stripe Element will be inserted here. -->
                                </div>
                            </div>

                            <div class="col-md-6">
                                <label for="cardExpiry">Credit or debit card Expiry</label>

                                <div id="cardExpiry">
                                    <!-- A Stripe Element will be inserted here. -->
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Used to display form errors. -->
                    <div id="card-errors" role="alert"></div>

                    <div class="form-group">
                        <button class="form=contron btn btn-success">Submit Payment</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script src="scripts/checkout.js"></script>
    <script>
        var url_string = window.location.href
        var url = new URL(url_string);
        var price = url.searchParams.get("price");
        document.getElementById("price").textContent = 'Price: $' + price
    </script>
</body>
</html>