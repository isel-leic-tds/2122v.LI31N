
private class Student3(n: Int, id:String) {
    val name: String = id
    init {
        print("Init -> ")
    }
    val number: Int = n.also{ print("n ") }
    init {
        println("Other")
    }
    constructor() : this(0,"") {
        print("C() -> ")
    }
    fun println() = println("$number - ($name)")
}

fun main() {
    Student3().println()
    Student3(48000,"Pedro Pereira").println()
}