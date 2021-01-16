package cloudcomputing.utils

import com.google.maps.DistanceMatrixApi
import com.google.maps.model.TravelMode
import com.google.maps.model.Unit

class Distance {

    fun calculate(firstAddress: String, secondAddress: String): Long{
        val distanceMatrixApi =
            DistanceMatrixApi.getDistanceMatrix(Google.initGMap(),
                arrayOf(firstAddress), arrayOf(secondAddress))
        val rows = distanceMatrixApi.mode(TravelMode.DRIVING).units(Unit.METRIC).await().rows
        val distanceArray = arrayListOf<Long>()
        rows.forEach { distanceMatrixRow ->
            distanceMatrixRow.elements.forEach {  distanceMatrixElement ->
                distanceArray.add(distanceMatrixElement.distance.inMeters)
            }
        }
        return distanceArray.min() ?: 0
    }
}