package cloudcomputing.controllers

import cloudcomputing.models.Payment
import cloudcomputing.utils.Distance
import io.javalin.http.Context
import io.javalin.http.Handler


class DistanceCalculator: Handler {

    override fun handle(context: Context) {
        val firstAddress = context.formParam("pickUpAddress") ?: ""
        val secondAddress = context.formParam("dropOffAddress") ?: ""
        if(firstAddress.isBlank() && secondAddress.isBlank()){
            context.result("Missing address")
        } else {
            // $1 for every 100 meters
            val distance = Distance().calculate(firstAddress, secondAddress)
            val price = distance / 100
            context.result(Payment(distance, "$" + price).toString())
        }
    }
}