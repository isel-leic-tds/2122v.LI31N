
open class A1 {
    open val prop: Int =0
}

class A2 : A1() {
    override val prop
        get() = (1..10).random()
}

class A3 : A1() {
    override var prop = 10  // var ???
}

fun printProp(obj: A1) = println(obj.prop)

fun main() {
    printProp( A1() )
    printProp( A2() )
    val obj = A3()
    obj.prop = 15
    printProp(obj)
}