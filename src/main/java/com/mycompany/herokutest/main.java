package com.mycompany.herokutest;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClient.*;
import com.mongodb.MongoClientURI;
import com.mongodb.client.*;
import java.util.ArrayList;
import static spark.Spark.*;

public class main {
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
//        get("/collection", (req, res) -> "27mm");

        String databaseName = "gocrush";
        MongoClientURI uri = new MongoClientURI("mongodb://gocrushapp:admin@ds123182.mlab.com:23182/gocrush");
        MongoClient mongoClient = new MongoClient(uri);
        DB db = mongoClient.getDB(databaseName);
        System.out.println("Connect to database successfully");
        DBCollection coll = db.getCollection("crush");
        DBCursor cursor = coll.find();
        ArrayList arr = new ArrayList();
        while (cursor.hasNext()) {
                arr.add(cursor.next());
        }
        get("/", (req, res) -> {
            return arr;
        });
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    } 
}
