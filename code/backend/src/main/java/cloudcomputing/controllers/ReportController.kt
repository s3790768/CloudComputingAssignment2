package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import cloudcomputing.models.Parcel
import com.google.firebase.cloud.FirestoreClient
import com.google.gson.GsonBuilder
import io.javalin.http.Context
import io.javalin.http.Handler

class ReportController: Handler {

    override fun handle(context: Context) {
        val parcelId = context.pathParam("id", String::class.java).get()
        val db = FirestoreClient.getFirestore()
        val dbRef = db.collection("jobs").document(parcelId)
        val parcel = dbRef.get().get().toObject(Parcel::class.java)
        db.document(parcelId).set( Parcel(
            parcel?.userId ?: "", parcel?.pickupAddress ?: "",
            parcel?.dropOffAddress ?: "", parcel?.time ?: "",
            parcel?.description ?: "", parcel?.isAccepted ?: false,
            parcel?.driverId ?: "", parcel?.isDelivered ?: false,
            parcelId, parcel?.receiverName ?: "", parcel?.senderName ?: "",
            true
        ))


        context.result(
            GsonBuilder()
                .create()
                .toJson(HttpResponse(200, "Listing has been reported")))
    }
}