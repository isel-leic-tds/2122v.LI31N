import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    println("Start")
    runBlocking {
        delay(3000L)
        println("Hello")
    }
    println("End")
}