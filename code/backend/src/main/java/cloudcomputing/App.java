package cloudcomputing;

import cloudcomputing.controllers.*;
import cloudcomputing.utils.Stripe;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import cloudcomputing.utils.Google;

public class App {

    public static void main(String[] args) {
        Javalin app = Javalin.create(JavalinConfig::enableDevLogging).start(8080);
        try {
            Stripe.Companion.init();
            Google.initGMap();
            new Google().initFireBase();
        } catch(Exception exception){
            exception.printStackTrace();
            System.out.println("Firebase Config not found!");
        }
        configureRoutes(app);
    }

    public static void configureRoutes(Javalin app) {
        // Parcel
        app.get("/parcel/:id", new GetParcel());
        app.post("/parcel/:id", new CollectParcel());
        app.post("/parcel", new CreateNewParcel());
        app.get("/parcel", new GetAllParcel());
        // Intentional misspelling
        app.post("/parce/paid", new ParcelPaid());

        app.get("/toDeliver/:userId", new ToBeDelivered());
        app.get("/delivered/:parcelId", new DeliveredParcel());

        app.post("/refund/:id", new RefundParcel());
        // User
        app.get("/user/parcel/:userId", new GetUserParcel());
        app.post("/user/parcel/report/:id", new ReportController());

    }
}
