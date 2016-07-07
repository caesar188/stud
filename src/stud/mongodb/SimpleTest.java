package stud.mongodb;

import com.mongodb.*;
import com.mongodb.client.*;

import java.util.Date;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.junit.Test;

/**
 * Created by pc on 2016/7/6.
 */
public class SimpleTest {

    public static final int port = 27017;
    public static final String host = "192.168.1.100";

    /**
     * 获取数据库对象
     *
     * @return
     */
    private MongoDatabase getDb() {
        // Old version, uses Mongo
        //Mongo mongo = new Mongo("localhost", 27017);
        // Since 2.10.0, uses MongoClient
        MongoClient mongo = new MongoClient(host, port);
        MongoDatabase db = mongo.getDatabase("new_db");
        return db;
    }

    /**
     * 获取表（集合）
     *
     * @return
     */
    private MongoCollection getCollection() {
        MongoDatabase db = getDb();
        MongoCollection<Document> table = db.getCollection("user");
        return table;
    }

    @Test
    public void displayDb()
    {
        MongoClient mongo = new MongoClient(host, port);
        MongoIterable<String> dbs= mongo.listDatabaseNames();

        for (String name:dbs)
        {
            System.out.println(name);
        }
    }

    @Test
    public void dropDatabase(){
        MongoClient mongo = new MongoClient(host, port);
        mongo.dropDatabase("local");
    }

    @Test
    public void update() {
        MongoCollection table = getCollection();
        Document query = new Document();
        query.put("name", "gu hao");
        BasicDBObject newDoc = new BasicDBObject();
        newDoc.put("name", "zicheng.net自成e家");
        newDoc.put("age", 28);
        UpdateOptions options = new UpdateOptions();
        //如果这里是true，当查不到结果的时候会添加一条newDoc,默认为false
        options.upsert(true);
        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDoc);
        UpdateResult result = table.updateMany(query, updateObj, options);
        assert result.getModifiedCount() > 0;
    }

    @Test
    public void find() {
        MongoCollection table = getCollection();
        Document document = new Document();
        document.put("age", 20);
        FindIterable iterable = table.find(document);
        MongoCursor cursor = iterable.iterator();
        while (cursor.hasNext()) {
            Object o = cursor.tryNext();
            System.out.println(o);
        }
    }

    @Test
    public void insert() {
        MongoCollection table = getCollection();
        Document document = new Document();
        document.put("name", "dst");
        document.put("age", 18);
        document.put("createdDate", new Date());
        table.insertOne(document);
    }

    @Test
    public void delete() {
        MongoCollection table = getCollection();
        Document document = new Document();
        document.put("name", "zicheng.net自成e家");
        DeleteResult result = table.deleteMany(document);
        assert (result.getDeletedCount() > 0);
    }

}
