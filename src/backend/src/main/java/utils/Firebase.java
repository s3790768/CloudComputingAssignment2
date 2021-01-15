package utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

public class Firebase {

    public static void init() throws Exception{
        FileInputStream fileInputStream = new FileInputStream("service.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(fileInputStream);
        FirebaseOptions options = FirebaseOptions.builder().setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
    }

}
