import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * Delegated properties:
 * val/var <property name>: <Type> by <expression>
 */

var global = 0 // using for delegate

class D1 {
    var prop: String by MyDelegate()

    val number: Int by lazy {  // Standard delegate
        println("First read...")
        (0..99).random()
    }

    val sameNumber: Int by this::number
    var member: Int by ::global

    var observated: Int by Delegates.observable(0) { prop, old, new ->
        println("Value of ${prop.name} changed from $old to $new")
    }

    var later: Int by Delegates.notNull()

    private val map = mutableMapOf<String,Any>(
        "ISEL" to 2020, "TDS" to 2022
    )
    val TDS by map
    var xpto: String by map
}

class MyDelegate {
    operator fun getValue(thisRef: D1, property: KProperty<*>): String {
        println("Reading of '${property.name}' in $thisRef")
        return "xpto"
    }
    operator fun setValue(thisRef: D1, property: KProperty<*>, value: String) {
        println("Writing $value to '${property.name}' in $thisRef.")
    }
}

fun main() {
    val d1 = D1()
    d1.prop = "test"
    println(d1.prop)

    println(d1.number)
    println(d1.number)

    val x by lazy{ (1..10).random() }
    println( x )
    println( x )

    global = 10
    println(d1.member)
    println(d1.sameNumber)

    d1.observated = 10

    //println(d1.later)
    d1.later =10
    println(d1.later)

    println(d1.TDS)
    //println(d1.xpto)
    d1.xpto = "Hello"
    println(d1.xpto)
}