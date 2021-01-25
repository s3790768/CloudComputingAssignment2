package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler
import cloudcomputing.models.Parcel
import cloudcomputing.utils.Moderate
import com.google.firebase.cloud.StorageClient
import com.google.gson.GsonBuilder
import io.javalin.core.util.FileUtil
import java.io.FileInputStream
import java.lang.Exception

class CreateNewParcel: Handler {

    override fun handle(context: Context) {
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs")
        val pickupAddress = context.formParam("pickupAddress")
        val dropOffAddress = context.formParam("dropOffAddress")
        val time = context.formParam("time")
        val uid = context.formParam("userId")
        val description = context.formParam("description")
        val fileUpload = context.uploadedFile("file")
        if(pickupAddress != null && dropOffAddress != null
            && time != null && uid != null && description != null && fileUpload != null){
            val firebaseAuth = FirebaseAuth.getInstance()
            try {
                firebaseAuth.getUser(uid)
                FileUtil.streamToFile(fileUpload.content, "upload/" + fileUpload.filename)
                val moderate = Moderate()
                if(moderate.image(FileInputStream("upload/" + fileUpload.filename))){
                    context.result(
                        GsonBuilder()
                            .create()
                            .toJson(HttpResponse(401, "Your uploaded image maybe an racy image!")))
                } else {
                    val parcelRef = docRef.document()
                    parcelRef.create(Parcel(uid, pickupAddress, dropOffAddress,
                        time, description, false))
                    StorageClient.getInstance().bucket().create(parcelRef.id,
                        FileInputStream("upload/" + fileUpload.filename))
                    context.result(
                        GsonBuilder()
                            .create()
                            .toJson(HttpResponse(200, "Success")))
                }
            } catch (exception: Exception){
                context.result(
                    GsonBuilder()
                        .create()
                        .toJson(HttpResponse(401, "Unauthorized user")))
            }
        } else {
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(400, "Invalid data")))
        }
    }
}