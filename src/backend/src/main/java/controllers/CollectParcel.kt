package controllers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler
import java.lang.Exception

class CollectParcel: Handler {

    override fun handle(context: Context) {
        val parcelId = context.pathParam("id", String::class.java).get()
        val driverId = context.queryParam("userId")
        val firebaseAuth = FirebaseAuth.getInstance()
        if(driverId != null) {
            try {
                firebaseAuth.getUser(driverId)
                val db = FirestoreClient.getFirestore()
                val docRef = db.collection("jobs")
                val hashMap: Map<String, Any> = hashMapOf("driverId" to driverId, "isAccepted" to true)
                docRef.document(parcelId).update(hashMap)
            } catch (exception: Exception){
                context.res.status = 401
                context.result("Driver not found!")
            }
        }
    }
}