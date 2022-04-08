package isel.leic.tds.mongoDB


import com.mongodb.*
import com.mongodb.client.*
import org.litote.kmongo.*
import java.io.Closeable

private const val ENV_CONNECTION = "MONGO_CONNECTION"
private const val ENV_DB_NAME = "MONGO_DB_NAME"

/**
 * Represents the MongoDB driver. Must be closed at the end.
 * The connection string of the environment variable is used to connect to the remote database,
 * if the variable is not defined a local instance is used.
 * The database name is defined in the following order:
 * 1- constructor parameter; 2 - environment variable; 3 - in the connection string.
 * @param nameDb Database name (override environment variable and connection string)
 */
class MongoDriver(nameDb: String? =null) : Closeable {
    /**
     * Reference to database.
     * Receiver of database functions to call.
     */
    val db: MongoDatabase

    private val client: MongoClient
    init {
        val envConnection = System.getenv(ENV_CONNECTION)
        val envDbName = System.getenv(ENV_DB_NAME)
        var dbName = nameDb
        if (dbName==null && envDbName!=null) dbName = envDbName
        if (dbName==null && envConnection!=null) dbName = ConnectionString(envConnection).database
        require(dbName!=null) { "$ENV_DB_NAME environment variable is required" }
        client =
            if (envConnection == null) KMongo.createClient()
            else KMongo.createClient(envConnection)
        db = client.getDatabase(dbName)
    }

    /**
     * Close the drive.
     * Must be called at the end of use.
     */
    override fun close() = client.close()

    /**
     * Gets one collection from the database.
     * @param id Collection identification.
     * @return The collection of documents of type T.
     */
    inline fun <reified T : Any> getCollection(id: String): MongoCollection<T> =
        db.getCollection(id, T::class.java)

    /**
     * Gets all collections from the database.
     * @return The list of document collections of type T.
     */
    inline fun <reified T : Any> getAllCollections(): Iterable<MongoCollection<T>> =
        db.listCollectionNames().map { getCollection<T>(it) }
}

/**
 * Get all documents from the collection.
 * @return The list of all documents (of type T) in the collection.
 */
fun <T> MongoCollection<T>.getAllDocuments(): Iterable<T> = this.find()

/**
 * Get a single document from the collection.
 * @param id Identification of document (_id field)
 * @return The document or null
 */
fun <T> MongoCollection<T>.getDocument(id: String): T? = this.findOneById(id)

/**
 * Insert a new document (of type T) in the collection.
 * @param doc Document to insert.
 * @return true if writing was acknowledged.
 */
fun <T> MongoCollection<T>.insertDocument(doc: T): Boolean = this.insertOne(doc).wasAcknowledged()

/**
 * Updates the document with new content. Assumes the document has an _id field.
 * @param doc Document to update.
 * @return true if writing was acknowledged.
 */
inline fun <reified T: Any> MongoCollection<T>.replaceDocument(doc: T): Boolean = this.replaceOne(doc).wasAcknowledged()

/**
 * Deletes the document with the identifier.
 * @param id Identification of document (_id field)
 * @return true if deleting was acknowledged.
 */
fun <T> MongoCollection<T>.deleteDocument(id: String): Boolean = this.deleteOneById(id).wasAcknowledged()
