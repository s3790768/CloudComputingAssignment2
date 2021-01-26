package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler
import cloudcomputing.models.Parcel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder

class GetParcel: Handler {

    override fun handle(context: Context) {
        val parcelId = context.pathParam("id", String::class.java).get()
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs")
        val parcelDocument = docRef.document(parcelId).get().get()
        if(parcelDocument.exists()){
            val parcel = parcelDocument.toObject(Parcel::class.java)
            val senderName = FirebaseAuth.getInstance().getUser(parcel?.userId).displayName
            val parcelModel = Parcel(parcel?.userId ?: "", parcel?.pickupAddress ?: "",
                parcel?.dropOffAddress ?: "", parcel?.time ?: "",
                parcel?.description ?: "", parcel?.isAccepted ?: false,
                parcel?.driverId ?: "", parcel?.isDelivered ?: false,
                receiverName = parcel?.receiverName ?: "", senderName = senderName)
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(200, parcelModel)))
        } else {
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(404, "Parcel not found")))
        }
    }
}