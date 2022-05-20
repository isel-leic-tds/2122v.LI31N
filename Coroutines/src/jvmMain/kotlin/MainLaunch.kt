import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        println("Start")
        val job1 = launch {
            println("Before")
            delay(3000)
            println("After")
        }
        val job2 =launch {
            println("Hello")
        }
        job2.join()
        println("End")
        //job1.cancel()
    }
}