package br.com.javaapirestful.controller;

import br.com.javaapirestful.conn.MongoDB;
import br.com.javaapirestful.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

public class UserController {

    public static void insert(User user) throws Exception {

        MongoCollection<User> collection = MongoDB.getInstance().getDatabase()
                .getCollection(User.COLLECTION, User.class);

        collection.insertOne(user);

    }

    public static void remove(Bson bson) throws Exception {

        MongoCollection<User> collection = MongoDB.getInstance().getDatabase()
                .getCollection(User.COLLECTION, User.class);

        collection.deleteOne(bson);

    }

    public static void update(Bson bson, User user) throws Exception {

        MongoCollection<User> collection = MongoDB.getInstance()
                .getDatabase().getCollection(User.COLLECTION, User.class);

        collection.updateOne(bson, new Document("$set", user));

    }

    public static User search(Bson bson) throws Exception {

        MongoCollection<User> collection = MongoDB.getInstance()
                .getDatabase().getCollection(User.COLLECTION, User.class);

        return collection.find(bson).first();

    }

    public static List<User> all() {

        MongoCollection<User> collection = MongoDB.getInstance()
                .getDatabase().getCollection(User.COLLECTION, User.class);

        List<User> list = new ArrayList<>();

        try (MongoCursor<User> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                list.add(cursor.next());
            }
        }

        return list;

    }

}
