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
        val docRef = db.collection("jobs").whereEqualTo("userId", userId)
        val parcelList = arrayListOf<Parcel>()
        docRef.get().get().documents.forEach {  snapShot ->
            val snap = snapShot.toObject(Parcel::class.java)
            parcelList.add(Parcel(snap.userId, snap.pickupAddress,
                snap.dropOffAddress, snap.time, snap.description, snap.isAccepted,
                snap.driverId, snap.isDelivered, snapShot.id,
                receiverName = snap.receiverName, senderName = snap.senderName))
        }

        context.result(
            GsonBuilder()
                .create()
                .toJson(HttpResponse(200, parcelList)))
    }
}