package com.example.EVeteranBot.bd;

import com.example.EVeteranBot.service.User;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Data;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Random;



public class MongoDBClient {

    MongoClient mongoClient = MongoClients.create("mongodb+srv://Vlad:LLPDEPF2GFPFxeBI@cluster0.neqrfbq.mongodb.net/");
    MongoDatabase database = mongoClient.getDatabase("test");
    MongoCollection<Document> collection = database.getCollection("users");

    public MongoDBClient() {
    }

    public MongoDBClient(User user) {
        IDgenerator iDgenerator = new IDgenerator();
        String id = iDgenerator.getID();
        Document document = new Document("_id", id)
                .append("birthday", user.getBirthDate())
                .append("idRegion", user.getRegion())
                .append("sureName", user.getSureName())
                .append("chatId", user.getChatID())
                .append("firstName", user.getName())
                .append("lastName", user.getSurname())
                .append("phoneNumber", user.getPhoneNumber());


        collection.insertOne(document);
    }


}
