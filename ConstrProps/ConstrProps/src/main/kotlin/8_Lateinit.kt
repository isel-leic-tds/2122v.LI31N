import kotlin.properties.Delegates

class Z {
    lateinit var later : String
    fun check() = this::later.isInitialized
}

fun main() {
    val z = Z()
    //println(z.later)
    println(z.check())
    z.later = "Ok"
    println(z.later)
    println(z.check())
}