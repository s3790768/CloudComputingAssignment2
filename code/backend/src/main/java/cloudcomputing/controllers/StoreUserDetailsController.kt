package cloudcomputing.controllers

import cloudcomputing.models.HttpResponse
import cloudcomputing.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import com.google.gson.GsonBuilder
import com.stripe.model.Customer
import com.stripe.model.PaymentMethod
import com.stripe.model.SetupIntent
import com.stripe.param.CustomerCreateParams
import com.stripe.param.PaymentMethodListParams
import io.javalin.http.Context
import io.javalin.http.Handler
import java.util.*
import kotlin.collections.HashMap

class StoreUserDetailsController: Handler {

    override fun handle(context: Context) {
        val userId = context.pathParam("id")
        val stripeId = context.formParam("stripeId")
        try {
            val firebaseAuth = FirebaseAuth.getInstance()
            val userRecords = firebaseAuth.getUser(userId)
            val db = FirestoreClient.getFirestore()
            val docRef = db.collection("user")
            val customerParams =
                CustomerCreateParams.builder()
                    .setEmail(userRecords.email)
                    .setName(userRecords.displayName)
                    .build()
            val customer = Customer.create(customerParams)
            val setupIntentHash = HashMap<String, Any>()
            setupIntentHash["customer"] = customer.id
            val setupIntent = SetupIntent.create(setupIntentHash)
            val params =
                PaymentMethodListParams.builder()
                    .setCustomer(customer.id)
                    .setType(PaymentMethodListParams.Type.CARD)
                    .build()

            docRef.document(userRecords.uid).create(User(customer.id ?: "", setupIntent.clientSecret ?: ""))
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(200, "")))
        } catch (exception: Exception){
            exception.printStackTrace()
            context.result(
                GsonBuilder()
                    .create()
                    .toJson(HttpResponse(401, "Invalid user")))
        }
    }
}