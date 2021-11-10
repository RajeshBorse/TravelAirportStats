package com.travel.airport.stats

import groovy.json.JsonOutput

def csvFile = 'src/main/resources/Flights2015.csv'
//Input file which needs to be parsed
String fileToParse = csvFile;
BufferedReader fileReader = null;
final String DELIMITER = ",";
def list = []
//Create the file reader
fileReader = new BufferedReader(new FileReader(fileToParse));
def lines = fileReader.readLines()
def keys = lines[0].split(',')
//pass values to the keys
def rows = lines[1..-1].collect { row ->
	def i = 0, vals = row.split(',')
	keys.inject([:]) { map, key -> map << ["$key": vals[i++]] }
}
//Print json output
def json = new groovy.json.JsonBuilder()
json Flights: rows
println groovy.json.JsonOutput.prettyPrint(json.toString())


