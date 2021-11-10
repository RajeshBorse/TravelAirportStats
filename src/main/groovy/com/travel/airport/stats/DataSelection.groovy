package com.travel.airport.stats

import org.bson.Document
import com.mongodb.BasicDBObject
import com.mongodb.client.MongoClients
import groovy.json.JsonOutput
import static com.mongodb.client.model.Filters.*
import static com.mongodb.client.model.Sorts.*;


// Picking the properties from local resources 'mongodb.properties' file
def properties = new Properties()
def propertiesFile = new File('src/main/resources/mongodb.properties')
propertiesFile.withInputStream {
	properties.load(it)
}

// Creating the MongoDB Connection to Fetch the contents
def mongoClient = MongoClients.create("mongodb+srv://${properties.USN}:${properties.PWD}@${properties.CLUSTER}.${properties.HOST}.mongodb.net/${properties.DB}?retryWrites=true&w=majority");
def dbInstance = mongoClient.getDatabase(properties.DB);
def myCollection = dbInstance.getCollection("new-travel-airport-stats")

// Selecting all the values form the MongoDB collection "travel-airport-stats"
def resultList = myCollection.find()

for(item in resultList) {
	println(JsonOutput.prettyPrint(JsonOutput.toJson(item)))
}

