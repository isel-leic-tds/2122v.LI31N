
/**
 * val <propertyName>[: <PropertyType>] [= <property_initializer>]
 *      [<getter>]
 */

class X {
    val first = 3
    val second: Int?
    init { second = 2 }
    val third: Int
        get() = first + (second?:0)
    val fourth get() = 4
    private val fifth
        get() = fourth + first
    fun useFifth() {
        println(fifth)
    }
}

// Global properties
var size = 3
val isEmpty get() = size==0

//Extension properties
val String.size get() = length

fun main() {
    val x = X()
    println( x.first )      // java: x.getFirst()
    println( x.second )     // java: x.getSecond()
    println( x.third )      // java: x.getThird()
    println( x.fourth )     // java: x.getFourth()
    x.useFifth()
    //val prop get() = 27     // No local properties
    println(isEmpty)
    println("abc".size)
}