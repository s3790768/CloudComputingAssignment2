package cloudcomputing.utils

import cloudcomputing.Constants
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseOptions
import com.google.firebase.FirebaseApp
import com.google.maps.GeoApiContext
import java.lang.Exception

class Google {

    @Throws(Exception::class)
    fun initFireBase() {
        val classLoader = javaClass.classLoader
        val inputStream = classLoader.getResourceAsStream("service.json")
        val credentials = GoogleCredentials.fromStream(inputStream)
        val options = FirebaseOptions.builder()
            .setCredentials(credentials)
            .build()
        FirebaseApp.initializeApp(options)
    }

    companion object {
        fun initGMap(): GeoApiContext {
            return GeoApiContext.Builder()
                .apiKey(Constants.GOOGLE_MAPS_KEY)
                .build()
        }
    }
}