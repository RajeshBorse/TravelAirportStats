package com.travel.airport.stats

import groovy.console.ui.ObjectBrowser
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import static com.mongodb.client.model.Sorts.*;


// from JSON file to Groovy map
def jsonSlurper = new JsonSlurper()
def file = new File('src/main/resources/airport-stats.json')
def statList = jsonSlurper.parseText(file.text)


/**
 * Example of simple query:
 * 
 * Select movies with more than a 9 rating and project title and year.
 * Then the resulting movies are sorted by year, in ascending order. 
 */

/**
 * Selecting Total Number of delays occurred in year 2010, which were greater than 1000
 * as a minimum threshold value.
 */

def projection = {
	[
		year: it.Time.Year,
		statsTotalDelayed : it.Statistics.Flights.Delayed,
		statsCarrierDelayed : it.Statistics.NumberOfDelays.Carrier,
		statsLateAircraftDelayed : it.Statistics.NumberOfDelays.LateAircraft,
		statsNationalAviationSystemDelayed : it.Statistics.NumberOfDelays.NationalAviationSystem,
		statsSecurityDelayed : it.Statistics.NumberOfDelays.Security,
		statsWeatherDelayed : it.Statistics.NumberOfDelays.Weather,
		month: it.Time.Month,
		totalFlights: it.Statistics.Flights.Total
	]
}

def selectedResultList = statList
		.findAll{it.Time.Year == 2010}
		.sort{it.Statistics.Flights.Total}
		.collect{projection(it)}.groupBy({it.month})
		
		println "**************"
		println selectedResultList
		println "**************"
		
//def groupedObj = selectedResultList.groupBy{(it.Time.Month)}	

def collectedMap = new LinkedHashMap()
//for(int i in 1..12) {
	def int totalDelays, totalFlights = 0
	for (obj in selectedResultList) {
//		if(i == obj.month) {
//			totalDelays += obj.statsTotalDelayed
//			totalFlights += obj.totalFlights
//		}
	println("Year : " + obj.year +"\t"
		  + "Month : "+ obj.month +"\t"
		  + "Flights : " + obj.totalFlights +"\t"
		  + "Delays : " + obj.statsTotalDelayed + "\t"
		  + "Carrier : " + obj.statsCarrierDelayed + "\t"
		  + "Late Aircraft : " + obj.statsLateAircraftDelayed + "\t"
		  + "National Aviation System :" + obj.statsNationalAviationSystemDelayed + "\t"
		  + "Weather :" + obj.statsWeatherDelayed + "\t"
		  + "Security :" + obj.statsSecurityDelayed + "\t"
		  + "%Rate: " + obj.statsTotalDelayed*100/obj.totalFlights + "\t")
	
	// Add to Map
	
	collectedMap.put("", obj)
}
	
//}



