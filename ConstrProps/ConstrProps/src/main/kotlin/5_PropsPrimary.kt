
class Student5(val number: Int=0, val name:String="") {
    init { print("Init -> ") }
    fun println() = println("$number - ($name)")
}

fun main() {
    Student5().println()
    Student5(48000,"Pedro Pereira").println()
    Student5(47000).println()
    Student5(name="Ana Paula").println()
}