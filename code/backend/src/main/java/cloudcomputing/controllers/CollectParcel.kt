package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import cloudcomputing.models.Parcel
import cloudcomputing.utils.Distance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import com.google.gson.GsonBuilder
import com.sendgrid.*
import com.stripe.model.*
import com.stripe.param.*
import io.javalin.http.Context
import io.javalin.http.Handler
import java.lang.Exception
import java.io.IOException


class CollectParcel: Handler {

    override fun handle(context: Context) {
        val parcelId = context.pathParam("id", String::class.java).get()
        val bodyParam = GsonBuilder().create().fromJson(context.body(), Parcel::class.java)
        val driverId = bodyParam.driverId
        val firebaseAuth = FirebaseAuth.getInstance()
        if(driverId.isNotEmpty()) {
            try {
                val driver = firebaseAuth.getUser(driverId)
                val db = FirestoreClient.getFirestore()
                val docRef = db.collection("jobs")
                val hashMap: Map<String, Any> = hashMapOf("driverId" to driverId, "hasAccepted" to true)
                docRef.document(parcelId).update(hashMap)
                val parcelDetails = docRef.document(parcelId).get().get().toObject(Parcel::class.java)
                val userId = parcelDetails?.userId
                val user = firebaseAuth.getUser(userId)
                sendMail(context, user.email, driver.displayName)
            } catch (exception: Exception){
                exception.printStackTrace()
                context.result(
                    GsonBuilder()
                        .create()
                        .toJson(HttpResponse(401, "Driver ID is invalid")))
            }
        } else {
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(401, "Please provide driver ID")))
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
        } catch (e: IOException) {
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(500, "There was an error contacting your driver")))
        }
    }
}