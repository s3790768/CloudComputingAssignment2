package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import cloudcomputing.models.Parcel
import com.google.firebase.cloud.FirestoreClient
import com.google.gson.GsonBuilder
import com.stripe.model.Refund
import com.stripe.param.RefundCreateParams
import io.javalin.http.Context
import io.javalin.http.Handler

class RefundParcel: Handler {

    override fun handle(context: Context) {
        val parcelId = context.pathParam("id")
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs")
        val parcelDocument = docRef.document(parcelId ?: "")
            .get().get().toObject(Parcel::class.java)
        docRef.document(parcelId ?: "").delete()
        Refund.create(
            RefundCreateParams.builder()
            .setPaymentIntent(parcelDocument?.paymentIntent)
            .build())
        context.result(GsonBuilder().create().toJson(HttpResponse(200, "Parcel refunded!")))
    }
}