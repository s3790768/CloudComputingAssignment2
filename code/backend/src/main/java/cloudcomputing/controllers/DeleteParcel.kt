package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import cloudcomputing.models.Parcel
import com.google.firebase.cloud.FirestoreClient
import com.google.firebase.cloud.StorageClient
import com.google.gson.GsonBuilder
import io.javalin.http.Context
import io.javalin.http.Handler

class DeleteParcel: Handler {

    override fun handle(context: Context) {
        val parcelId = context.pathParam("id", String::class.java).get()
        val userId = context.formParam("userId", String::class.java).get()
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs")
        val parcelDocument = docRef.document(parcelId).get().get()
        if(parcelDocument.exists()) {
            val parcel = parcelDocument.toObject(Parcel::class.java)
            if(parcel?.userId != userId){
                context.result(
                    GsonBuilder()
                        .create()
                        .toJson(HttpResponse(401, "Unauthorized user")))
            } else {
                if(parcel.isDelivered || parcel.isAccepted){
                    context.result(
                        GsonBuilder()
                            .create()
                            .toJson(HttpResponse(200, "Parcel is already delivered or is being delivered")))
                } else {
                    docRef.document(parcelId).delete()
                    StorageClient.getInstance().bucket().get(parcelId).delete()
                    context.result(
                        GsonBuilder()
                            .create()
                            .toJson(HttpResponse(200, "Parcel deleted")))
                }
            }
        } else {
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(404, "Parcel not found")))
        }
    }
}