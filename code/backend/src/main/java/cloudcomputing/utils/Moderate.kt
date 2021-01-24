package cloudcomputing.utils

import cloudcomputing.Constants
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.ContentModeratorManager
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.AzureRegionBaseUrl
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.EvaluateFileInputOptionalParameter
import java.io.FileInputStream

class Moderate {

    fun image(imageToModerate: FileInputStream): Boolean{
        val client = ContentModeratorManager.authenticate(
            AzureRegionBaseUrl.fromString(Constants.MICROSOFT_ENDPOINT), Constants.MICROSOFT_API_KEY)
        val evaluate = client.imageModerations().evaluateFileInput(imageToModerate.readAllBytes(),
            EvaluateFileInputOptionalParameter().withCacheImage(true))
        return evaluate.isImageRacyClassified
    }
}