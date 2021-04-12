# Technology Used
* Java 11
* SpringBoot 2.4.4
* Spring Rest Web Service 
* MongoDB
* Spring Data Mongo
* Spring RestTemplate (Http client)
* JUnit Test (For integration test)
* Docker-compose (For MongoDB where service connects to at run time)

# How to run
The service and it's db are dockerized, so first you need to do the following:
* Build the service using maven => mvn clean install
* Build the docker image => docker build -t ultimate-ai-service .
* Start up docker-compose => docker-compose up

# How to test
There are two tests:
* Integration test: where the real endpoint is being called and I am using embedded MongoDB for storage.
* Unit test: sample unit test for testing main logic.

# Service Structure
* When the service starts up, a method in MongoCollectionInitializer event listener is being triggered.
  This method will read the intents (provided in the task) which are stored in file "db.json" on the 
  class path, and then will store them in MongoDB. On the startup, we always clear this DB before this process
* The service has a POST API which accepts message and botId.
* The API forwards the request to to MessageReplyService which is responsible for returning
  final result.
* The service is doing following checks:
    1. First it will use MessageIntentSuggestionService to get back list of suggested intents for the message.
    2. It will sort this list in descending order to get the highest confidence intent.
    3. For this highest intent name, we get from the DB the best matching reply.
    4. If it's found, we return it, otherwise we return 404.

# What can be enhanced
* We may need a way to cache list of returned intents for some time instead of always calling
  the API, like that we may save lots of requests to the API and it's BE resources.  
* Adding more unit and integration tests (Maybe with different framework)
* Enhancing project and package structure!