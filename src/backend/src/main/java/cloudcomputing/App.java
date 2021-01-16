package cloudcomputing;

import cloudcomputing.controllers.*;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import cloudcomputing.utils.Firebase;

public class App {

    public static void main(String[] args) {
        Javalin app = Javalin.create(JavalinConfig::enableDevLogging).start(8080);
        try {
            new Firebase().init();
        } catch(Exception exception){
            System.out.println("Firebase Config not found!");
        }
        configureRoutes(app);
    }

    public static void configureRoutes(Javalin app) {
        app.get("/id/:id", new GetParcel());
        app.post("/id/:id", new CollectParcel());
        app.get("/user/:id", new GetUserParcel());
        app.post("/create", new CreateNewParcel());
    }
}