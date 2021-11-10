package com.travel.airport.stats

import groovy.console.ui.ObjectBrowser
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import static com.mongodb.client.model.Sorts.*;


// from JSON file to Groovy map
def jsonSlurper = new JsonSlurper()
def file = new File('src/main/resources/new-airport-stat.json')
def statList = jsonSlurper.parseText(file.text)

def projection = {
	[
		year : it.Year,
		label : it.Label,
		month : it.Month,
		monthName : it.MonthName,
		delayedCarrierName : it.DelayedCarrierName,
		delayedLateAircraft : it.DelayedLateAircraft,
		delayedNationalAviationSystem : it.DelayedNationalAviationSystem,
		delayedSecurity : it.DelayedSecurity,
		delayedWeather : it.DelayedWeather,
		carriersNames : it.CarriersNames,
		carriersTotal : it.CarriersTotal,
		flightsCancelled : it.FlightsCancelled,
		flightsDelayed : it.FlightsDelayed,
		flightsDiverted : it.FlightsDiverted,
		flightsArrivedOnTime : it.FlightsArrivedOnTime,
		flightsTotal : it.FlightsTotal,
		rate : it.FlightsDelayed*100 / it.FlightsTotal
	]
}

def selectedResultList = statList
		.findAll{it.Year == 2010}
		.sort{it.rate}
		.collect{projection(it)}.groupBy({it.label})

//println selectedResultList

for (Map.Entry<String, List<String>> obj in selectedResultList.entrySet()) {
	def sumDelayedFlightsCarrier, sumDelayedLateAircraft, sumDelayedNationalAviationSystem, sumDelayedSecurity, sumDelayedWeather = 0
	def year

	for (statsItem in obj.getValue()) {
		year = statsItem.getValue("year")
		sumDelayedFlightsCarrier += statsItem.getValue("delayedCarrierName")
		sumDelayedLateAircraft += statsItem.getValue("delayedLateAircraft")
		sumDelayedNationalAviationSystem += statsItem.getValue("delayedNationalAviationSystem")
		sumDelayedSecurity += statsItem.getValue("delayedSecurity")
		sumDelayedWeather += statsItem.getValue("delayedWeather")
	}

	println("Year :" + year +"\t"+
			"Sum of Carrier Delays :" + sumDelayedFlightsCarrier +"\t"+
			"Sum of Late Aircraft :" + sumDelayedFlightsCarrier +"\t"+
			"Sum of National Aviation System :" + sumDelayedFlightsCarrier +"\t"+
			"Sum of Security :" + sumDelayedFlightsCarrier +"\t"+
			"Sum of Weather :" + sumDelayedFlightsCarrier +"\t")

}




