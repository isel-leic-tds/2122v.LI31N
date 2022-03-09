fun main() {
    val values = readValues("Insira valores")
    val avg = values.average()
    println("Média = $avg")
    values.filter{ it>avg }.forEach( ::println )
}

fun readValues(message:String?=null): List<Int> {
    message?.apply{ println(this) }
    //return readln().split(' ').mapNotNull { it.toIntOrNull() }
    val values = mutableListOf<Int>()
    while (true) {
        values += (readln().toIntOrNull() ?: return values)
    }
}

