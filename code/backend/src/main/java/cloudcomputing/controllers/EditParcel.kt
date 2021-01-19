package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import cloudcomputing.models.Parcel
import cloudcomputing.utils.Moderate
import com.google.firebase.cloud.FirestoreClient
import com.google.firebase.cloud.StorageClient
import com.google.gson.GsonBuilder
import io.javalin.http.Context
import io.javalin.http.Handler
import java.io.FileInputStream

class EditParcel: Handler {

    override fun handle(context: Context) {
        val parcelId = context.pathParam("id", String::class.java).get()
        val userId = context.formParam("userId", String::class.java).get()
        val db = FirestoreClient.getFirestore()
        val parcelDocument = db.document(parcelId).get().get()
        val parcel = parcelDocument.toObject(Parcel::class.java)
        if(parcel?.userId == userId){
            if(parcel.isDelivered || parcel.isAccepted){
                context.result(
                    GsonBuilder()
                        .create()
                        .toJson(HttpResponse(200, "Parcel is already delivered or is being delivered")))
            } else {
                val pickupAddress = context.queryParam("pickupAddress")
                val dropOffAddress = context.queryParam("dropOffAddress")
                val time = context.queryParam("time")
                val description = context.queryParam("description")
                val fileUpload = context.uploadedFile("file")
                StorageClient.getInstance().bucket().create(parcelId,
                    FileInputStream("upload/" + fileUpload?.filename))
                val moderate = Moderate()
                if(moderate.image(FileInputStream("upload/" + fileUpload?.filename))){
                    context.result(
                        GsonBuilder()
                            .create()
                            .toJson(HttpResponse(401, "Your uploaded image maybe an racy image!")))
                } else {
                    val hashMap: HashMap<String, Any?> = hashMapOf("pickupAddress" to pickupAddress,
                        "dropOffAddress" to dropOffAddress, "time" to time, "description" to description)
                    db.document(parcelId).update(hashMap)
                    context.result(
                        GsonBuilder()
                            .create()
                            .toJson(HttpResponse(200, "Updated")))
                }
            }

        } else {
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(401, "Unauthorized user")))
        }
    }
}