package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import cloudcomputing.models.Parcel
import com.google.firebase.cloud.FirestoreClient
import com.google.gson.GsonBuilder
import io.javalin.http.Context
import io.javalin.http.Handler

class ToBeDelivered: Handler {

    override fun handle(context: Context) {
        val userId = context.pathParam("userId", String::class.java).get()
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs").whereEqualTo("driverId", userId)
        val parcelList = arrayListOf<Parcel>()
        docRef.get().get().documents.forEach {  snapShot ->
            val snap = snapShot.toObject(Parcel::class.java)
            if(!snap.hasDelivered){
                parcelList.add(
                    Parcel(snap.userId, snap.pickupAddress,
                        snap.dropOffAddress, snap.time, snap.description, snap.hasAccepted,
                        snap.driverId, snap.hasDelivered, snapShot.id,
                        receiverName = snap.receiverName, senderName = snap.senderName)
                )
            }
        }

        context.result(
            GsonBuilder()
                .create()
                .toJson(HttpResponse(200, parcelList)))
    }
}