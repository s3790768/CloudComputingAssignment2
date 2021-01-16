package cloudcomputing.controllers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler
import cloudcomputing.models.Parcel
import com.google.cloud.storage.Bucket
import com.google.firebase.cloud.StorageClient
import io.javalin.core.util.FileUtil
import java.io.FileInputStream
import java.lang.Exception

class CreateNewParcel: Handler {

    override fun handle(context: Context) {
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("jobs")
        val pickupAddress = context.queryParam("pickupAddress")
        val dropOffAddress = context.queryParam("dropOffAddress")
        val time = context.queryParam("time")
        val uid = context.queryParam("userId")
        val description = context.queryParam("description")
        val fileUpload = context.uploadedFile("file")
        if(pickupAddress != null && dropOffAddress != null
            && time != null && uid != null && description != null && fileUpload != null){
            val firebaseAuth = FirebaseAuth.getInstance()
            try {
                firebaseAuth.getUser(uid)
                val parcelRef = docRef.document()
                parcelRef.create(Parcel(uid, pickupAddress, dropOffAddress,
                    time, description, false))
                FileUtil.streamToFile(fileUpload.content, "upload/" + fileUpload.filename)
                StorageClient.getInstance().bucket().create(parcelRef.id,
                    FileInputStream("upload/" + fileUpload.filename))
                context.res.status = 200
            } catch (exception: Exception){
                context.res.status = 401
                context.result("Unauthorized user")
            }

        } else {
            context.result("Invalid data")
        }
    }
}