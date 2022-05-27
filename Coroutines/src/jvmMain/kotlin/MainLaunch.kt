import kotlinx.coroutines.*

fun main() {
    runBlocking {
        println(coroutineContext)
        val job1 = launch(Dispatchers.Main) {
            println(coroutineContext)
            val res = task1()
            log("After $res")
        }
        val job2 =launch(Dispatchers.Default) {
            delay(2000)
            log("Hello")
        }
        log("Before join")
        job2.join()
        log("End")
        //job1.cancel()
    }
    log("The end.")
}

suspend fun task1(): String {
    withContext(Dispatchers.IO) {
        log("Before")
        delay(3000)
    }
    return "After 3sec"
}