package controllers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler
import models.Parcel
import java.lang.Exception

class CreateNewParcel: Handler {

    override fun handle(context: Context) {
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs")
        val pickupAddress = context.queryParam("pickupAddress")
        val dropOffAddress = context.queryParam("dropOffAddress")
        val time = context.queryParam("time")
        val uid = context.queryParam("userId")
        if(pickupAddress != null && dropOffAddress != null && time != null && uid != null){
            val firebaseAuth = FirebaseAuth.getInstance()
            try {
                firebaseAuth.getUser(uid)
                docRef.document().create(Parcel(uid, pickupAddress, dropOffAddress, time))
                context.res.status = 200
            } catch (exception: Exception){
                context.res.status = 401
                context.result("Unauthorized user")
            }

        } else {
            context.result("Invalid data")
        }
    }
}