package cloudcomputing.controllers

import cloudcomputing.models.Parcel
import cloudcomputing.models.User
import cloudcomputing.utils.Distance
import com.google.cloud.firestore.CollectionReference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import com.sendgrid.*
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import io.javalin.http.Context
import io.javalin.http.Handler
import java.lang.Exception
import java.io.IOException


class CollectParcel: Handler {

    private lateinit var docRef: CollectionReference

    override fun handle(context: Context) {
        val parcelId = context.pathParam("id", String::class.java).get()
        val driverId = context.formParam("driverId")
        val firebaseAuth = FirebaseAuth.getInstance()
        if(driverId != null) {
            try {
                val driver = firebaseAuth.getUser(driverId)
                val db = FirestoreClient.getFirestore()
                docRef = db.collection("jobs")
                val hashMap: Map<String, Any> = hashMapOf("driverId" to driverId, "isAccepted" to true)
                docRef.document(parcelId).update(hashMap)
                val parcelDetails = docRef.document(parcelId).get().get().toObject(Parcel::class.java)
                val userId = parcelDetails?.userId
                val user = firebaseAuth.getUser(userId)
                val paymentMethodId = context.formParam("paymentMethodId")
                if(paymentMethodId.isNullOrBlank()){
                    context.result("Payment Method ID not found!")
                } else {
                    chargeUser(context, userId ?: "", paymentMethodId, parcelDetails?.pickUpLocation ?: "", parcelDetails?.dropOffLocation ?: "")
                    sendMail(context, user.email, driver.displayName)
                }
            } catch (exception: Exception){
                exception.printStackTrace()
                context.res.status = 401
                context.result("Driver ID is invalid")
            }
        } else {
            context.res.status = 401
            context.result("Please provide driver ID")
        }
    }

    private fun sendMail(context: Context, userEmail: String, driverName: String){
        val subject = "Your driver is on the way"
        val from = Email("danielquah.cloud.computing@gmail.com")
        val to = Email(userEmail)
        val content = Content("text/plain", "Your parcel is ready to be picked up by: $driverName")
        val mail = Mail(from, subject, to, content)
        val sendGrid = SendGrid("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")
        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()
            sendGrid.api(request)
            context.res.status = 200
        } catch (e: IOException) {
            context.res.status = 500
        }
    }

    private fun chargeUser(context: Context, userId: String,
                           paymentMethodId: String, firstAddress: String, secondAddress: String){
        val db = FirestoreClient.getFirestore()
        docRef = db.collection("user")
        val userDetails = docRef.document(userId).get().get().toObject(User::class.java)
        if(userDetails?.stripeId != null){
            val distance = Distance().calculate(firstAddress, secondAddress)
            val price = distance / 100
            val createParams = PaymentIntentCreateParams.builder()
                .setAmount(price)
                .setCurrency("aud")
                .setConfirm(true)
                .setPaymentMethod(paymentMethodId)
                .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.AUTOMATIC)
                .build()
            PaymentIntent.create(createParams)
        } else {
            context.result("User credit card details not found!")
        }
    }
}