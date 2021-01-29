package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler
import cloudcomputing.models.Parcel
import cloudcomputing.models.PaymentData
import cloudcomputing.utils.Distance
import com.google.gson.GsonBuilder
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import java.lang.Exception

class CreateNewParcel: Handler {

    override fun handle(context: Context) {
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs")
        val bodyData = GsonBuilder().create().fromJson(context.body(), Parcel::class.java)
        val firebaseAuth = FirebaseAuth.getInstance()
        try {
            val user = firebaseAuth.getUser(bodyData.userId)
            if(bodyData.pickupAddress.isNotEmpty() && bodyData.dropOffAddress.isNotEmpty()
                && bodyData.time.isNotEmpty() && bodyData.userId.isNotEmpty() && bodyData.description.isNotEmpty()){
                val parcelRef = docRef.document()
                parcelRef.create(Parcel(bodyData.userId,
                    bodyData.pickupAddress, bodyData.dropOffAddress,
                    bodyData.time, bodyData.description,
                    hasAccepted = false, receiverName = bodyData.receiverName, parcelId = parcelRef.get().get().id))
                chargeUser(context, bodyData.pickupAddress, bodyData.dropOffAddress,
                    parcelRef.get().get().id, user.email)
            } else {
                context.result(
                    GsonBuilder()
                        .create()
                        .toJson(HttpResponse(401, "There are some missing data")))
            }
        } catch (exception: Exception){
            exception.printStackTrace()
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(401, "Unauthorized user")))
        }
    }

    private fun chargeUser(context: Context, firstAddress: String,
                           secondAddress: String, parcelId: String, email: String) {
        try {
            val distance = Distance().calculate(firstAddress, secondAddress)
            val price = if(distance == 0L){
                100
            } else {
                distance / 100
            }
            val params =
                PaymentIntentCreateParams.builder()
                    .setCurrency("aud")
                    .setAmount(price)
                    .setReceiptEmail(email)
                    .putMetadata("integration_check", "accept_a_payment")
                    .build()
            val intent = PaymentIntent.create(params)
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(200, PaymentData(price.toString(), parcelId, intent.clientSecret))))
        } catch (exception: Exception){
            exception.printStackTrace()
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(300, "It appears that your address is invalid")))
        }
    }
}