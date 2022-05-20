import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val r = async {
            delay(3000L)
            (10..100).random()
        }
        println("Waiting...")
        println("Value = ${r.await()}")
    }
}