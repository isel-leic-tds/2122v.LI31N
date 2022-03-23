package tennis.tests


data class A(val x: Int)  {
    /*
    override fun equals(other: Any?): Boolean {
        println("equals $other")
        return (other is A) && x==other.x
    }
    override fun hashCode() = x
    override fun toString() = "A($x)
     */
}

fun main() {
    val obj1 = A(10)
    val obj2 = A(10)
    val obj3 = obj1
    println(obj1==obj2)   // Comparação de valor
    println(obj1===obj2)  // Comparação de identidade
    println(obj1===obj3)
    val str = obj1.toString()
    val hash = obj1.hashCode()
    println("$str hash=$hash")
}