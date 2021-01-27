package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import cloudcomputing.models.Parcel
import com.google.firebase.cloud.FirestoreClient
import com.google.gson.GsonBuilder
import io.javalin.http.Context
import io.javalin.http.Handler

class GetAllParcel: Handler {

    override fun handle(context: Context) {
        val db = FirestoreClient.getFirestore()
        val allParcel = arrayListOf<Parcel>()
        db.collection("jobs").listDocuments().forEach { docReference ->
            val parcel = docReference.get().get().toObject(Parcel::class.java)
            if(parcel?.hasAccepted != true || !parcel.hasDelivered){
                allParcel.add(Parcel(parcel?.userId ?: "", parcel?.pickupAddress ?: "",
                    parcel?.dropOffAddress ?: "", parcel?.time ?: "", parcel?.description ?: "",
                    parcelId = docReference.get().get().id))
            }
        }
        context.result(
            GsonBuilder()
                .create()
                .toJson(HttpResponse(200, allParcel)))
    }
}