import isel.leic.tds.mongoDB.MongoDriver
import isel.leic.tds.mongoDB.getDocument
import isel.leic.tds.mongoDB.insertDocument
import kotlin.test.*

class MongoTest {
    data class Doc(val _id: String, val field:Int)
    @Test
    fun `test insert Document`() {
        MongoDriver().use { drv ->
            val collection = drv.getCollection<Doc>("test")
            collection.insertDocument(Doc("test_id", 10))
            val doc = collection.getDocument("test_id")
            assertNotNull(doc)
            assertEquals(10, doc.field)
        }
    }
}