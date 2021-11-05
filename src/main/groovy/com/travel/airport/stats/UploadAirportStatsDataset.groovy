package com.travel.airport.stats

import org.bson.Document
import com.mongodb.client.MongoClients
import static com.mongodb.client.model.Filters.*;
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

// Picking the properties from local resources 'mongodb.properties' file
def properties = new Properties()
def propertiesFile = new File('src/main/resources/mongodb.properties')
propertiesFile.withInputStream {
	properties.load(it)
}

// Parsing the local 'airport-stats.json' file to Upload the contents to MongoDB Server
def jsonFile = new File('src/main/resources/airport-stats.json')
def jsonSlurper = new JsonSlurper()
def list = jsonSlurper.parseText(jsonFile.text)

// Creating the MongoDB Connection and Uploading the contents
def mongoClient = MongoClients.create("mongodb+srv://${properties.USN}:${properties.PWD}@${properties.CLUSTER}.${properties.HOST}.mongodb.net/${properties.DB}?retryWrites=true&w=majority");
def dbInstance = mongoClient.getDatabase(properties.DB);
def myCollection = dbInstance.getCollection("travel-airport-stats")

// Uploading the Parsed JsonObjects as an Documents to MongoDB
for (item in list) {
	def doc = Document.parse(JsonOutput.toJson(item))
	myCollection.insertOne(doc)
}