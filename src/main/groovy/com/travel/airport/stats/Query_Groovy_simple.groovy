package com.travel.airport.stats

import groovy.json.JsonSlurper

def jsonSlurper = new JsonSlurper()
def file = new File('src/main/resources/new-airport-stat.json')
def statList = jsonSlurper.parseText(file.text)

def year,month,delayPercent

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
		delayPercent : it.FlightsDelayed*100 / it.FlightsTotal
	]
}

def selectedResultList = statList
		.findAll{it.Year == 2010}
//		.findAll{it.Year <= 2016} // Scalability on Larger Dataset
		.sort{it.rate}
		.collect{projection(it)}.groupBy({it.label})

for (obj in selectedResultList.entrySet()) {
	def sumDelayedFlightsCarrier = 0
	def sumDelayedLateAircraft = 0
	def sumDelayedNationalAviationSystem = 0
	def sumDelayedSecurity = 0
	def sumDelayedWeather = 0

	for (statsItem in obj.getValue()) {
		year = statsItem.get("year")
		month = statsItem.get("month")
		delayPercent = statsItem.get("delayPercent")
		sumDelayedFlightsCarrier += statsItem.get("delayedCarrierName")
		sumDelayedLateAircraft += statsItem.get("delayedLateAircraft")
		sumDelayedNationalAviationSystem += statsItem.get("delayedNationalAviationSystem")
		sumDelayedSecurity += statsItem.get("delayedSecurity")
		sumDelayedWeather += statsItem.get("delayedWeather")
	}
	println("Year :" + year +"\t"+
			"Month :" + month +"\t"+
			"Sum of Carrier Delays :" + sumDelayedFlightsCarrier +"\t"+
			"Sum of Late Aircraft :" + sumDelayedLateAircraft +"\t"+
			"Sum of National Aviation System :" + sumDelayedNationalAviationSystem +"\t"+
			"Sum of Security :" + sumDelayedSecurity +"\t"+
			"Sum of Weather :" + sumDelayedWeather +"\t"+
			"Delay Percentage :" + String.format("%.2f", delayPercent) +"\t")
}
