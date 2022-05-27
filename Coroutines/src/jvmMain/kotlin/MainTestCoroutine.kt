import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    var count = 10
    runBlocking {
        repeat(count) {
            launch {
                delay(3000L + it * 10)
                val x = count
                println("end $it count=$x")
                count = x - 1
            }
        }
        while (count>0) {
            delay(50)
            print("($count)")
        }
        println("End.")
    }
}