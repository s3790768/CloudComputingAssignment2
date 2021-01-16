package cloudcomputing.controllers

import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler
import cloudcomputing.models.Parcel
import com.google.firebase.cloud.StorageClient
import java.util.concurrent.TimeUnit


class GetParcel: Handler {

    override fun handle(context: Context) {
        val parcelId = context.pathParam("id", String::class.java).get()
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs")
        val parcelDocument = docRef.document(parcelId).get().get()
        if(parcelDocument.exists()){
            val parcel = parcelDocument.toObject(Parcel::class.java)
            val url = StorageClient.getInstance().bucket().get(parcelId).signUrl(5, TimeUnit.MINUTES).toURI()
            val parcelModel = Parcel(parcel?.userId ?: "", parcel?.pickUpLocation ?: "",
                parcel?.dropOffLocation ?: "", parcel?.time ?: "",
                parcel?.description ?: "", parcel?.isAccepted ?: false,
                parcel?.driverId ?: "", parcel?.isDelivered ?: false, url.toString())
            context.result(parcelModel.toString())
        } else {
            context.res.status = 404
        }
    }
}