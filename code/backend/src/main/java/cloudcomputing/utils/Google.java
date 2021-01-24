package cloudcomputing.utils;

import cloudcomputing.Constants;
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
                .setStorageBucket(Constants.STORAGE_BUCKET_URL)
                .build();
        FirebaseApp.initializeApp(options);
    }

    public static GeoApiContext initGMap(){
        return new GeoApiContext.Builder()
                .apiKey(Constants.GOOGLE_MAPS_KEY)
                .build();
    }

}
