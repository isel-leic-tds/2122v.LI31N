data class Node<T>(val elem: T, val next: Node<T>? = null)

class MutableStack<T> {
    private var top: Node<T>? = null
    private val topNotNull
        get() = top ?: throw NoSuchElementException()
    fun push(e: T) { top = Node(e,top) }
    fun pop() = top().also { top= topNotNull.next }
    fun top() = topNotNull.elem
    fun isEmpty() = top==null
}

class Stack<T>(private val top: Node<T>? = null) {
    private val topNotNull by lazy { top ?: throw NoSuchElementException() }
    fun push(e: T) = Stack( Node(e,top) )
    fun pop() = Stack( topNotNull.next )
    fun top() = topNotNull.elem
    fun isEmpty() = top==null
}