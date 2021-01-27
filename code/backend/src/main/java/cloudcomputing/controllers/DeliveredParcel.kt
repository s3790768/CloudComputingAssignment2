package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import cloudcomputing.models.Parcel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import com.google.gson.GsonBuilder
import com.sendgrid.*
import io.javalin.http.Context
import io.javalin.http.Handler
import java.io.IOException

class DeliveredParcel: Handler {


    override fun handle(context: Context) {
        val parcelId = context.pathParam("parcelId", String::class.java).get()
        val hashMap: Map<String, Any> = hashMapOf("hasDelivered" to true)
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs")
        docRef.document(parcelId).update(hashMap)
        val parcel = docRef.document(parcelId).get().get().toObject(Parcel::class.java)
        val firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.getUser(parcel?.userId)
        sendMail(context, user.email)
    }

    private fun sendMail(context: Context, userEmail: String){
        val subject = "Your driver has delivered your parcel"
        val from = Email("danielquah.cloud.computing@gmail.com")
        val to = Email(userEmail)
        val content = Content("text/plain", "Your parcel has been delivered. Thank you for using our service!")
        val mail = Mail(from, subject, to, content)
        val sendGrid = SendGrid("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")
        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()
            sendGrid.api(request)
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(200, "")))
        } catch (e: IOException) {
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(500, "There was an error contacting your user")))
        }
    }
}