package controllers

import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler
import models.Parcel

class GetUserParcel: Handler {

    override fun handle(context: Context) {
        val userId = context.pathParam("id", String::class.java).get()
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs").whereEqualTo("userId", userId).get()
        val userParcel = docRef.get()
        if(!userParcel.isEmpty){
            context.result(userParcel.toObjects(Parcel::class.java).toString())
        } else {
            context.res.status = 404
        }

    }
}