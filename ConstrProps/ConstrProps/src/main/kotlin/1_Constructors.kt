
val x = 10

class Student1 {
    val number: Int
    val name: String

    init { print("Init -> ") }
    constructor(n: Int, id:String) {
        print("C(n,id) -> ")
        number = n
        name = id
    }
    constructor() {
        print("C() -> ")
        number = 0
        name =""
    }
    fun println() = println("$number - ($name)")
}

fun main() {
    val y: Int
    Student1().println()
    y = 10
    println(y)
    Student1(48000,"Pedro Pereira").println()
}