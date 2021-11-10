package com.travel.airport.stats

import org.bson.Document
import com.mongodb.client.MongoClients
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
def myCollection = dbInstance.getCollection("new-travel-airport-stats")

//def Map<String, List<String>> map = LinkedHashMap<String, List<String>>

def LinkedHashMap<String,String> airportMap = new LinkedHashMap<String,String>();

// Uploading the Parsed JsonObjects as an Documents to MongoDB
for (item in list) {
	for (Map.Entry<String, List<String>> entry : item.entrySet()) {
		String key = entry.getKey();
		if(key == "Airport") {
			for(item1 in entry.getValue()) {
				airportMap.put(item1.getKey(), item1.getValue())
			}
		}

		if(key == "Time") {
			for(item1 in entry.getValue()) {
				airportMap.put(item1.getKey(), item1.getValue())
			}
		}

		if(key == "Statistics") {
			for (Map.Entry<String, List<String>> statsItem : entry.getValue()) {
				String statsKey = statsItem.getKey();
				if(statsKey == "NumberOfDelays") {
					for(item1 in statsItem.getValue()) {
						airportMap.put(item1.getKey(), item1.getValue())
					}
				}
				if(statsKey == "Carriers") {
					for(item1 in statsItem.getValue()) {
						airportMap.put(item1.getKey()=='Total' ? 'CarriersTotal' : item1.getKey() , item1.getValue())
					}
				}
				if(statsKey == "Flights") {
					for(item1 in statsItem.getValue()) {
						airportMap.put(item1.getKey()=='Total' ? 'FlightsTotal' : item1.getKey() , item1.getValue())
					}
				}
			}
		}
	}

	// Inserting the collection of documents in MongoDB
	def doc = Document.parse(JsonOutput.toJson(airportMap))
	myCollection.insertOne(doc)
}