package cloudcomputing

import kotlin.jvm.JvmStatic
import io.javalin.Javalin
import io.javalin.core.JavalinConfig
import com.stripe.Stripe
import cloudcomputing.utils.Google
import cloudcomputing.controllers.GetParcel
import cloudcomputing.controllers.CollectParcel
import cloudcomputing.controllers.CreateNewParcel
import cloudcomputing.controllers.GetAllParcel
import cloudcomputing.controllers.ParcelPaid
import cloudcomputing.controllers.ToBeDelivered
import cloudcomputing.controllers.DeliveredParcel
import cloudcomputing.controllers.RefundParcel
import cloudcomputing.controllers.GetUserParcel
import cloudcomputing.controllers.ReportController
import java.lang.Exception

object App {
    @JvmStatic
    fun main(args: Array<String>) {
        val app = Javalin.create { obj: JavalinConfig -> obj.enableDevLogging() }.start(8080)
        try {
            Stripe.apiKey = Constants.STRIPE_KEY
            Google.initGMap()
            Google().initFireBase()
        } catch (exception: Exception) {
            exception.printStackTrace()
            println("Firebase Config not found!")
        }
        configureRoutes(app)
    }

    private fun configureRoutes(app: Javalin) {
        // Parcel
        app.get("/parcel/:id", GetParcel())
        app.post("/parcel/:id", CollectParcel())
        app.post("/parcel", CreateNewParcel())
        app.get("/parcel", GetAllParcel())
        // Intentional misspelling
        app.post("/parce/paid", ParcelPaid())
        app.get("/toDeliver/:userId", ToBeDelivered())
        app.get("/delivered/:parcelId", DeliveredParcel())
        app.get("/parcel-refund/:id", RefundParcel())
        // User
        app.get("/user/parcel/:userId", GetUserParcel())
        app.post("/user/parcel/report/:id", ReportController())
    }
}