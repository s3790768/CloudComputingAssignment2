package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import cloudcomputing.models.Parcel
import cloudcomputing.models.PaymentData
import com.google.firebase.cloud.FirestoreClient
import com.google.gson.GsonBuilder
import io.javalin.http.Context
import io.javalin.http.Handler

class ParcelPaid: Handler {

    override fun handle(context: Context) {
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs")
        val parcelDocument = docRef.document(context.formParam("parcelId") ?: "").get().get()
        val parcel = parcelDocument.toObject(Parcel::class.java)
        val parcelModel = Parcel(parcel?.userId ?: "", parcel?.pickupAddress ?: "",
            parcel?.dropOffAddress ?: "", parcel?.time ?: "",
            parcel?.description ?: "", parcel?.isAccepted ?: false,
            parcel?.driverId ?: "", parcel?.isDelivered ?: false,
            receiverName = parcel?.receiverName ?: "", senderName = parcel?.senderName ?: "",
            paymentIntent =  context.formParam("paymentIntent") ?: "")
        docRef.document(context.formParam("parcelId") ?: "").set(parcelModel)
        context.result(GsonBuilder()
            .create()
            .toJson(HttpResponse(200, "")))
    }
}