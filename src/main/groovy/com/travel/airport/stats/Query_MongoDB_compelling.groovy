package com.travel.airport.stats

import org.bson.Document


import com.mongodb.client.MongoClients

import static com.mongodb.client.model.Filters.*
import static com.mongodb.client.model.Projections.*
import static com.mongodb.client.model.Sorts.*
import static com.mongodb.client.model.Accumulators.*

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import static com.mongodb.client.model.Aggregates.*

// load credentials from src/main/resources/mongodb.properties
// this file should contain 
//		USN=yourUsername
//		PWD=yourPassword
//		DATABASE=yourDatabaseName 
def properties = new Properties()
def propertiesFile = new File('src/main/resources/mongodb.properties')
propertiesFile.withInputStream {
	properties.load(it)
}

// create connection and upload contents
def mongoClient = MongoClients.create("mongodb+srv://${properties.USN}:${properties.PWD}@${properties.CLUSTER}.${properties.HOST}.mongodb.net/${properties.DB}?retryWrites=true&w=majority");
def db = mongoClient.getDatabase(properties.DB);
def col = db.getCollection("travel-airport-stats")


/**
 * Example of query that requires using MongoDb Java Driver in more depth.
 *
 * Select actors that star more than 1 movie with at least a 9 imdb rating
 */
def resultList = col.aggregate([
	unwind('$it.Time'),
	match(gt('Year',2010))
	/*project(fields(include('title', 'year'),excludeId())),
	sort(ascending('year'))*/
])

println()
println()
resultList.each { println(it) }
println()
println()
