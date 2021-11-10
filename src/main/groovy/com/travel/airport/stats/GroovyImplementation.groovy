package com.travel.airport.stats

import groovy.json.JsonSlurper
import groovy.time.*

// Declaring the Variables
def timeStart = new Date()
def jsonSlurper = new JsonSlurper()
def file = new File('src/main/resources/new-airport-stat.json')
def statList = jsonSlurper.parseText(file.text)
def year, month, monthName

// Defining the Projection
def projection = {[
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
	]}

println ()
println "========================================================================================"
println "Monthly Statistics of all 30 Airports in US -->" +"\t"+ "(Output via Groovy Parsing)"
println "========================================================================================"
println ()
def startTimestamp = new Date()
println ()
println "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"

// Groovy Query to Filter,Collect,Sort and GroupBy referring our local .json file
def selectedResultList = statList
		.findAll{it.Year == 2010}
//		.findAll{it.Year <= 2016} // Uncomment to test the Scalability on Larger Dataset
		.sort{it.rate}
		.collect{projection(it)}.groupBy({it.label})

	// Traversing the obtained resultSet to do calculations and then print the output in console
for (obj in selectedResultList.entrySet()) {
	def sumDelayedFlightsCarrier = 0
	def sumDelayedLateAircraft = 0
	def sumDelayedNationalAviationSystem = 0
	def sumDelayedSecurity = 0
	def sumDelayedWeather = 0
	def sumTotalFlights = 0
	def delayPercent = 0
	def sumFlightsDelayed = 0
	def sumFlightsCancelled = 0
	def sumFlightsDiverted = 0
	def sumFlightsArrivedOnTime = 0
	
	for (statsItem in obj.getValue()) {
		year = statsItem.get("year")
		month = statsItem.get("month")
		monthName = statsItem.get("monthName")
		sumTotalFlights += statsItem.get("flightsTotal")
		delayPercent += statsItem.get("delayPercent")/10

		// Reasons for delay of Flights
		sumDelayedFlightsCarrier += statsItem.get("delayedCarrierName")
		sumDelayedLateAircraft += statsItem.get("delayedLateAircraft")
		sumDelayedNationalAviationSystem += statsItem.get("delayedNationalAviationSystem")
		sumDelayedSecurity += statsItem.get("delayedSecurity")
		sumDelayedWeather += statsItem.get("delayedWeather")
		
		// Other additional statistics of Flights 
		sumFlightsDelayed += statsItem.get("flightsDelayed")
		sumFlightsCancelled += statsItem.get("flightsCancelled")
		sumFlightsDiverted += statsItem.get("flightsDiverted")
		sumFlightsArrivedOnTime += statsItem.get("flightsArrivedOnTime")
		
	}
	// Printing the values out to console
	println("Year: " + year.toString().padRight(7)+
			"Month: " + month.toString().padRight(5)+
			"MonthName: " + monthName.toString().padRight(12)+
			"TotalFlights: " + sumTotalFlights.toString().padRight(9)+
			"---> "+
			"SumCarrierDelays: " + sumDelayedFlightsCarrier.toString().padRight(9)+
			"SumLateAircraft: " + sumDelayedLateAircraft.toString().padRight(9)+
			"SumNationalAviationSystem: " + sumDelayedNationalAviationSystem.toString().padRight(9)+
			"SumSecurity: " + sumDelayedSecurity.toString().padRight(6)+
			"SumWeather: " + sumDelayedWeather.toString().padRight(7)+
			"DelayedPercentage: " + String.format("%.2f", delayPercent)+" %" +"\t"+
			"---> "+
			"FlightsCancelled: " + sumFlightsCancelled.toString().padRight(9)+
			"FlightsDelayed: " + sumFlightsDelayed.toString().padRight(9)+
			"FlightsDiverted: " + sumFlightsDiverted.toString().padRight(9)+
			"FlightsArrivedOnTime: " + sumFlightsArrivedOnTime.toString().padRight(9))
println "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"}

// Calculate the execution time difference between the start and end time
def timeStop = new Date()
TimeDuration executionTimeSpent = TimeCategory.minus(timeStop, timeStart)

println()
println()
println "=================================================================="
println "Total Execution Time Using Groovy Query:\t" + executionTimeSpent
println "=================================================================="
