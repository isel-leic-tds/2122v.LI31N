import kotlin.concurrent.thread


fun main() {
    var count = 10
    repeat(count) {
        thread {
            Thread.sleep(3000L+it*20)
            val x = count
            println("end $it count=$x")
            count = x-1
        }
    }
    while (count>0) {
        Thread.sleep(50)
        print("($count)")
    }
    println("End.")
}