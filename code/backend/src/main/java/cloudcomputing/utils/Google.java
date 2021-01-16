package cloudcomputing.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.maps.GeoApiContext;

import java.io.InputStream;

public class Google {

    public void initFireBase() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("service.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .setStorageBucket("cloudcomputinga2-74277.appspot.com")
                .build();
        FirebaseApp.initializeApp(options);
    }

    public static GeoApiContext initGMap(){
        return new GeoApiContext.Builder()
                .apiKey("AIzaSyD8PN2_Kv-lRqf0Gy4A9EJuJ9sl5vAXeDw")
                .build();
    }

}
