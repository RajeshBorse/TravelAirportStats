<link rel='stylesheet' href='web/swiss.css'/>

# Analysis of Delayed Flights - United States

This project is based on the project template of the worksheet for week 13 and it provides a couple of examples of acceptable queries for the mini project using the IMDB movie dataset used in the programming exercises:

## Briefly describe the application domain that you are going to analyse (e.g. motivation for using that topic, what the topic is about, and main concepts involved)

The motivation for selecting this topic i.e. Monthly Airline Delays by Airport, 2003-2016
is that using the provided dataset we can do the in-depth analysis of the past performances of the Airports and the Carrier flights monthly and yearly from year 2003/01 till 2016/06.

Nobody wants to spend their time on Airports and have any-kinds of delay during their journeys. We will be able to use this analysis data and statistics to improve the overall services for customer to enable them making their right choices of their destination or transit Airports and Carriers and also give them the background about the factors causing the delays from our past surveys.

On the technical stack we intend to us MongoDB, Groovy, Gradle and GitHub for the mini-project deliverables. As MongoDB Atlas is a secure multi-cloud service, being it also provides as with its enhanced features as we can scale horizontally, consistency on the data part and its easy to maintain. It is designed to increase developers productivity reason being we can upload our dataset's.

We will be uploading the selected dataset using Groovy based project and use the MongoDB drivers to upload the dataset. We are planing to extract queries in the possible ways and make use of the entire dataset to pull valuable information, moreover we are also ensuring that we keep the performance of the queries to the mark, which helps us for having high availability of our data.


## Briefly describe the query that you are going to implement

Here after the discussion with the team and analysis of the complete dataset, we have observed that the dataset has several attributes using which we can extract some meaningful information using Groovy and MongoDB API's.

For Example :- 

1) Total number of flights which were delayed between year 2013 to 2016.
2) Airports with most number of delayed carrier flights.
3) Identify the most and the least number of flight delays Airport wise.
4) Statistical data extract for factors affecting the delays i.e. (Carrier, Late Aircraft, National Aviation System, Security and Weather)
5) Airport having most success ratio and flights that were arrived on time.

Using this queries it will help us to build result set which we will be using to generate the reports and add them in our presentations. We are going to use the Mongo Charts to generate these reports which will give us a pictorial/graphical view of our data extracts.


Thanks


## Team - Group 22 (Contributers)
* Al Janapy, Abeer S.J.
* Al-Algawy, Yousif A.A.
* Shaikh, Sana Atif
* Sadanala, Yogeswari
* Kadir Abdallah, Fathi
* Borse, Rajesh S
