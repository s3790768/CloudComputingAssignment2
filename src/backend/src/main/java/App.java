import controllers.CreateNewParcel;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import utils.Firebase;


public class App {

    public static void main(String[] args) {
        Javalin app = Javalin.create(JavalinConfig::enableDevLogging).start(8080);
        try {
            Firebase.init();
        } catch(Exception exception){
            System.out.println("Firebase Config not found!");
        }
        configureRoutes(app);
    }

    public static void configureRoutes(Javalin app) {
        app.post("/create", new CreateNewParcel());
    }
}
