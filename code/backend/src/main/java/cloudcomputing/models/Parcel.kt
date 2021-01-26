package cloudcomputing.models

data class Parcel(
    val userId: String = "",
    val pickupAddress: String = "",
    val dropOffAddress: String = "",
    val time: String = "",
    val description: String = "",
    val isAccepted: Boolean = false,
    val driverId: String = "",
    val isDelivered: Boolean = false,
    val parcelId: String = "",
    val receiverName: String = "",
    val senderName: String = "",
    val isReported: Boolean = false
)