package cloudcomputing;

import cloudcomputing.controllers.*;
import cloudcomputing.utils.Stripe;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import cloudcomputing.utils.Firebase;

public class App {

    public static void main(String[] args) {
        Javalin app = Javalin.create(JavalinConfig::enableDevLogging).start(8080);
        try {
            Stripe.Companion.init();
            new Firebase().init();
        } catch(Exception exception){
            System.out.println("Firebase Config not found!");
        }
        configureRoutes(app);
    }

    public static void configureRoutes(Javalin app) {
        // Parcel
        app.get("/parcel/:id", new GetParcel());
        app.post("/parcel/:id", new CollectParcel());
        app.post("/parcel", new CreateNewParcel());

        // User
        app.get("/user/parcel/:id", new GetUserParcel());
        app.post("/user/:id", new StoreUserDetailsController());

    }
}
