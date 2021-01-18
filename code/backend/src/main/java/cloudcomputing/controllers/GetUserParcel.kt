package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler
import cloudcomputing.models.Parcel
import com.google.gson.GsonBuilder

class GetUserParcel: Handler {

    override fun handle(context: Context) {
        val userId = context.pathParam("id", String::class.java).get()
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs").whereEqualTo("userId", userId).get()
        val userParcel = docRef.get()
        context.result(
            GsonBuilder()
                .create()
                .toJson(HttpResponse(200, userParcel.toObjects(Parcel::class.java))))
    }
}