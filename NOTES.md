### How to run

Subprojects are dockerized, but unfortunately they require the whole project to be built first with maven.
Inside `development` directory there's docker-compose that includes all system applications.

1. Build the root project with `maven package`
2. Run `docker-compose up -d --build` to build and run the whole system right away 
3. To run the thermometer simulators, execute `./run-simulators.sh config.json` 
    from inside the `developement/simulation` directory. You can configure simulation parameters by modifying `config.json` 

### Notes

#### Few things are missing due to lack of time

- **anomaly-storage** lacks integration test that would check the flow of consuming the message 
  and saving anomaly to database
- **thermometer-storage** lacks integration test that would check the whole application flow including scheduler
- **anomaly-detector** lacks second anomaly detection algorithm and corresponding tests as well as 
  TemperatureAnomalyProcessor unit tests
- ideally it would be great to have automated e2e tests that would launch the whole system in docker containers 
  and test the flow using simulators and rest api client


#### When it comes to the future

In general, I would eventually modify the architecture a little bit. Thermometers should not fire to kafka directly 
but rather there should be proxy service that would receive the measurements 
using appropriate protocol (receive directly, read from mqtt broker etc.) and pass them to kafka.
It could manage the registration of thermometers as well, providing some additional data.

I would add additional storage connector that would store the raw measurements in mongo as they are valuable data themselves

If we wanted to enable functionality for assigning thermometers to rooms, browse measurements data etc. we could add  
second api service that would handle such operations and could be used by e.g mobile app for visualization and operations

This api service could read data directly from mongo but all modification operations would be published to kafka, read
by storage connector and saved to mongo

Each service should have its own mongo user with configured access restrictions to adequate collections 
