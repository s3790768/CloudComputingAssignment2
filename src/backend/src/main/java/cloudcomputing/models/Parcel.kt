package cloudcomputing.models

data class Parcel(
    val userId: String = "",
    val pickUpLocation: String = "",
    val dropOffLocation: String = "",
    val time: String = "",
    val description: String = "",
    val isAccepted: Boolean = false,
    val driverId: String = "",
    val isDelivered: Boolean = false
)