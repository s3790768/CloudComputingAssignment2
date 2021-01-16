package cloudcomputing.controllers

import cloudcomputing.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import com.stripe.model.Customer
import io.javalin.http.Context
import io.javalin.http.Handler

class StoreUserDetailsController: Handler {

    override fun handle(context: Context) {
        val params = HashMap<String, Any>()
        val userId = context.pathParam("id")
        try {
            val firebaseAuth = FirebaseAuth.getInstance()
            val userRecords = firebaseAuth.getUser(userId)
            params["email"] = userRecords.email
            params["name"] = userRecords.displayName
            val customer = Customer.create(params)
            val db = FirestoreClient.getFirestore()
            val docRef = db.collection("user")
            docRef.document(userRecords.uid).create(User(customer.id))
            context.res.status = 200
        } catch (exception: Exception){
            exception.printStackTrace()
            context.result("Invalid User")
        }
    }
}