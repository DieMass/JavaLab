package die.mass.mongo.driver;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class DriverMain {

	public static void main(String[] args) {
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("devices");
		MongoCollection<Document> documents = database.getCollection("cpu");
		Document documentToSave = new Document("series", "cores").append("series", "testSeries").append("core", 238);
		documents.insertOne(documentToSave);
		documents.updateOne(eq("series", "testSeries"), new Document("$set", new Document("series", "newSeries")));
		documents.deleteOne(eq("series", "newSeries"));
	}
}
