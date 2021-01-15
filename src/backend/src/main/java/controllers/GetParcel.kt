package controllers

import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler
import models.Parcel


class GetParcel: Handler {

    override fun handle(context: Context) {
        val parcelId = context.pathParam("id", String::class.java).get()
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs")
        val parcelDocument = docRef.document(parcelId).get().get()
        if(parcelDocument.exists()){
            context.result(parcelDocument.toObject(Parcel::class.java).toString())
        } else {
            context.res.status = 404
        }
    }
}