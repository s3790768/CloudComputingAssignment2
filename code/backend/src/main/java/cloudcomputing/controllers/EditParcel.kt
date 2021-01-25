package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import cloudcomputing.models.Parcel
import com.google.firebase.cloud.FirestoreClient
import com.google.gson.GsonBuilder
import io.javalin.http.Context
import io.javalin.http.Handler
import kotlin.collections.HashMap

class EditParcel: Handler {

    override fun handle(context: Context) {
        val parcelId = context.pathParam("id", String::class.java).get()
        val userId = context.formParam("userId", String::class.java).get()
        val db = FirestoreClient.getFirestore()
        val parcelDocument = db.document(parcelId).get().get()
        val parcel = parcelDocument.toObject(Parcel::class.java)
        if(parcel?.userId == userId){
            if(parcel.isDelivered || parcel.isAccepted){
                context.result(
                    GsonBuilder()
                        .create()
                        .toJson(HttpResponse(200, "Parcel is already delivered or is being delivered")))
            } else {
                val bodyData = GsonBuilder().create().fromJson(context.body(), Parcel::class.java)
                val hashMap: HashMap<String, Any?> = hashMapOf("pickupAddress" to bodyData.pickupAddress,
                    "dropOffAddress" to bodyData.dropOffAddress, "time" to bodyData.time, "description" to bodyData.description)
                db.document(parcelId).update(hashMap)
                context.result(
                    GsonBuilder()
                        .create()
                        .toJson(HttpResponse(200, "Updated")))
            }

        } else {
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(401, "Unauthorized user")))
        }
    }
}