fun main() {
    val stk = MutableStack<String>()
    stk.push("ISEL")
    stk.push("TDS")
    println(stk.top())
    while( !stk.isEmpty() ) {
        println( stk.pop() )
    }

    var si = Stack<String>()
    si = si.push("ISEL")
    si = si.push("TDS")
    println(si.top())
    while ( !si.isEmpty() ) {
        println(si.top())
        si = si.pop()
    }
}