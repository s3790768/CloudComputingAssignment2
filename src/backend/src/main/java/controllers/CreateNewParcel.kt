package controllers

import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler

class CreateNewParcel: Handler {

    override fun handle(context: Context) {
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs")
        val data = HashMap<String, Any>()
        val pickupAddress = context.queryParam("pickupAddress")
        val dropOffAddress = context.queryParam("dropOffAddress")
        val time = context.queryParam("time")
        val uid = context.queryParam("userId")
        if(pickupAddress != null && dropOffAddress != null && time != null && uid != null){
            data["dropOff"] = dropOffAddress
            data["pickup"] = pickupAddress
            data["time"] = time
            data["userId"] = uid
            docRef.document().set(data)
            context.res.status = 200
        } else {
            context.res.status = 500
        }
    }
}