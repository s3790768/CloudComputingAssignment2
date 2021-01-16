package cloudcomputing.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.InputStream;

public class Firebase {

    public void init() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("service.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        FirebaseOptions options = FirebaseOptions.builder().setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
    }

}
