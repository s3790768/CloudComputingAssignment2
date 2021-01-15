package models

data class Parcel(
    val userId: String = "",
    val pickUpLocation: String = "",
    val dropOffLocation: String = "",
    val time: String = "",
    val isAccepted: Boolean = false
)