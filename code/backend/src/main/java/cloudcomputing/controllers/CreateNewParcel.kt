package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler
import cloudcomputing.models.Parcel
import com.google.gson.GsonBuilder
import java.lang.Exception

class CreateNewParcel: Handler {

    override fun handle(context: Context) {
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs")
        val bodyData = GsonBuilder().create().fromJson(context.body(), Parcel::class.java)
        val firebaseAuth = FirebaseAuth.getInstance()
        try {
            firebaseAuth.getUser(bodyData.userId)
            if(bodyData.pickupAddress.isNotEmpty() && bodyData.dropOffAddress.isNotEmpty()
                && bodyData.time.isNotEmpty() && bodyData.userId.isNotEmpty() && bodyData.description.isNotEmpty()){
                val parcelRef = docRef.document()
                parcelRef.create(Parcel(bodyData.userId, bodyData.pickupAddress, bodyData.dropOffAddress,
                    bodyData.time, bodyData.description, false))
                context.result(
                    GsonBuilder()
                        .create()
                        .toJson(HttpResponse(200, "Success")))
            } else {
                context.result(
                    GsonBuilder()
                        .create()
                        .toJson(HttpResponse(401, "There are some missing data")))
            }
        } catch (exception: Exception){
            exception.printStackTrace()
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(401, "Unauthorized user")))
        }
    }
}