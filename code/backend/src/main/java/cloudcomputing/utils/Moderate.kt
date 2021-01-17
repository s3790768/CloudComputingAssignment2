package cloudcomputing.utils

import com.microsoft.azure.cognitiveservices.vision.contentmoderator.ContentModeratorManager
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.AzureRegionBaseUrl
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.EvaluateFileInputOptionalParameter
import java.io.FileInputStream

class Moderate {

    private val apiKey = "5989c312fe00460ba3bc8054a9c0509a"
    private val endPoint = "https://cloudcomputinga2.cognitiveservices.azure.com/"

    fun image(imageToModerate: FileInputStream): Boolean{
        val client = ContentModeratorManager.authenticate(
            AzureRegionBaseUrl.fromString(endPoint), apiKey)
        val evaluate = client.imageModerations().evaluateFileInput(imageToModerate.readAllBytes(),
            EvaluateFileInputOptionalParameter().withCacheImage(true))
        return evaluate.isImageRacyClassified
    }
}