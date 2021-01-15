package controllers

import com.google.cloud.firestore.CollectionReference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import com.google.firebase.cloud.FirestoreClient
import com.sendgrid.*
import io.javalin.http.Context
import io.javalin.http.Handler
import models.Parcel
import java.lang.Exception
import java.io.IOException

class CollectParcel: Handler {

    private lateinit var driver: UserRecord
    private lateinit var docRef: CollectionReference

    override fun handle(context: Context) {
        val parcelId = context.pathParam("id", String::class.java).get()
        val driverId = context.queryParam("userId")
        val firebaseAuth = FirebaseAuth.getInstance()
        if(driverId != null) {
            try {
                driver = firebaseAuth.getUser(driverId)
                val db = FirestoreClient.getFirestore()
                docRef = db.collection("jobs")
                val hashMap: Map<String, Any> = hashMapOf("driverId" to driverId, "isAccepted" to true)
                docRef.document(parcelId).update(hashMap)
                val parcelDetails = docRef.document(parcelId).get().get().toObject(Parcel::class.java)
                val userId = parcelDetails?.userId
                val user = firebaseAuth.getUser(userId)
                val subject = "Your driver is on the way"
                val from = Email("danielquah.cloud.computing@gmail.com")
                val to = Email(user.email)
                val content = Content("text/plain", "Your parcel is ready to be picked up by: " + driver.email)
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
                    e.printStackTrace()
                    context.res.status = 500
                }
            } catch (exception: Exception){
                context.res.status = 401
                context.result("Driver not found!")
            }
        }
    }
}