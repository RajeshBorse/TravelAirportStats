package com.travel.airport.stats

import static com.mongodb.client.model.Accumulators.*
import static com.mongodb.client.model.Aggregates.*
import static com.mongodb.client.model.Filters.*
import static com.mongodb.client.model.Projections.*
import static com.mongodb.client.model.Sorts.*

import com.mongodb.client.MongoClients

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
def myCollection = db.getCollection("new-travel-airport-stats")


def selectedResultList = myCollection.aggregate([
	match(lte('Year',2016)),
	project(fields(include('Label', 'DelayedCarrierName', 'DelayedLateAircraft', 'DelayedNationalAviationSystem','DelayedSecurity','DelayedWeather'),excludeId())),
	group('$Label', sum('Sum of Carrier Delays', '$DelayedCarrierName'),
		sum('Sum of Late Aircraft Delays', '$DelayedLateAircraft'),
		sum('Sum of National Aviation System', '$DelayedLateAircraft'),
		sum('Sum of Security', '$DelayedLateAircraft'),
		sum('Sum of Weather', '$DelayedWeather')),
	sort(ascending('_id'))
])

println()
println()

for(abc in selectedResultList) {
	println abc
}

//selectedResultList.each{println(it)}
//println()
//println()
