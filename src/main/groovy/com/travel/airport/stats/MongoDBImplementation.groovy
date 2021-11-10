package com.travel.airport.stats

import static com.mongodb.client.model.Accumulators.*
import static com.mongodb.client.model.Aggregates.*
import static com.mongodb.client.model.Filters.*
import static com.mongodb.client.model.Projections.*
import static com.mongodb.client.model.Sorts.*

import com.mongodb.client.MongoClients

import groovy.time.*

def timeStart = new Date()

// Referring to local 'mongodb.properties'
def properties = new Properties()
def propertiesFile = new File('src/main/resources/mongodb.properties')
propertiesFile.withInputStream {
	properties.load(it)
}

// Creating MongoDB connection to Upload dataset
def mongoClient = MongoClients.create("mongodb+srv://${properties.USN}:${properties.PWD}@${properties.CLUSTER}.${properties.HOST}.mongodb.net/${properties.DB}?retryWrites=true&w=majority");
def db = mongoClient.getDatabase(properties.DB);
def myCollection = db.getCollection("new-travel-airport-stats")


println ()
println "========================================================================================"
println "Monthly Statistics of all 30 Airports in US -->" +"\t"+ "(Output via MongoDB Query)"
println "========================================================================================"
println ()
println ()
println "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"

// MongoDB Query to Filter,Collect,Sort and GroupBy referring our mongoDB collection
def selectedResultList = myCollection.aggregate([
	match(eq('Year',2010)),
//	match(lte('Year',2016)), // Uncomment to test the Scalability on Larger Dataset
	project(fields(include('Label', 'DelayedCarrierName', 'DelayedLateAircraft', 'DelayedNationalAviationSystem','DelayedSecurity','DelayedWeather',
		'FlightsCancelled','FlightsDelayed','FlightsDiverted','FlightsArrivedOnTime'),excludeId())),
	group('$Label', sum('Sum of Carrier Delays', '$DelayedCarrierName'),
		sum('SumLate Aircraft Delays', '$DelayedLateAircraft'),
		sum('SumNational Aviation System', '$DelayedLateAircraft'),
		sum('SumSecurity', '$DelayedLateAircraft'),
		sum('SumWeather', '$DelayedWeather'),
		sum('FlightsCancelled', '$FlightsCancelled'),
		sum('FlightsDelayed', '$FlightsDelayed'),
		sum('FlightsDiverted', '$FlightsDiverted'),
		sum('FlightsArrivedOnTime', '$FlightsArrivedOnTime')),
	sort(ascending('_id'))
])

// Traversing the obtained resultSet to print the output in console
for(obj in selectedResultList) {
	println obj
	println "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
}

// Calculate the execution time difference between the start and end time
def timeStop = new Date()
TimeDuration executionTimeSpent = TimeCategory.minus(timeStop, timeStart)

println()
println()
println "=================================================================="
println "Total Execution Time Using MongoDB Query:\t" + executionTimeSpent
println "=================================================================="

