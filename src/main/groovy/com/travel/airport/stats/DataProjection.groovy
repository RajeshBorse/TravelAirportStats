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
def myCollection = dbInstance.getCollection("travel-airport-stats")


// Selecting all the values form the MongoDB collection "travel-airport-stats"
def resultList = myCollection.find().sort(ascending('Airport.Code')).toList()

// Need to implement Projections here, below is just a Test display output

// FYI - Our dataset is collected between 2003/06 to 2016/01 - Monthly for Each Airport

for(item in resultList) {
	println("Airport Code : " + item.Airport.Code + "\t"
		+ "In Year/Month of : " + item.Time.Label  + "\t"
		+ "Number of Flights Delayed : " + item.Statistics.Flights.Delayed)
}