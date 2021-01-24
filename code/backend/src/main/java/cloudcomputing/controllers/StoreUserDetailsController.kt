package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import cloudcomputing.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import com.google.gson.GsonBuilder
import io.javalin.http.Context
import io.javalin.http.Handler

class StoreUserDetailsController: Handler {

    override fun handle(context: Context) {
        val params = HashMap<String, Any>()
        val userId = context.pathParam("id")
        val stripeId = context.formParam("stripeId")
        try {
            val firebaseAuth = FirebaseAuth.getInstance()
            val userRecords = firebaseAuth.getUser(userId)
            params["email"] = userRecords.email
            params["name"] = userRecords.displayName
            val db = FirestoreClient.getFirestore()
            val docRef = db.collection("user")
            docRef.document(userRecords.uid).create(User(stripeId ?: ""))
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(200, "")))
        } catch (exception: Exception){
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(401, "Invalid user")))
        }
    }
}