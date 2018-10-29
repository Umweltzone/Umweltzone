package de.avpptr.umweltzone.zones

sealed class ChildZonesCount(val value: Int) {

    object ONE : ChildZonesCount(1)
    object TWO : ChildZonesCount(2)

}
