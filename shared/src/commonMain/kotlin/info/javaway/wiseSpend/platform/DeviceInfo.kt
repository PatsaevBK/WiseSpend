package info.javaway.wiseSpend.platform

expect class DeviceInfo() {
    val osName: String
    val osVersion: String
    val model: String
    val cpu: String
    val screenWidth: String
    val screenHeight: String
    val screenDestiny: String
    
    fun getSummary(): String
}