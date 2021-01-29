package cloudcomputing.models

data class PaymentData(
    val price: String,
    val parcelId: String,
    val paymentIntent: String
)