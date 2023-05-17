# Race Application Service

REST API with small event driven CQRS microservice architecture.

The API enables the following operations:
- Create
- Patch
- Delete
- Get (one)
- Get (all)

Each application is defined by the following attributes:
- ID (uuid) required
- First name (255 characters) required
- Last name (255 characters) required
- Club (255 characters) optional
- Distance (5k | 10k | HalfMarathon | Marathon) required

### Two modules:

#### race_application_command_service: 
Spring boot microservice implementing the mutating API endpoints (create, patch, delete). The service produces an event upon receiving an API request and publishes it to a Kafka messaging system.

##### race_application_query_service: 
Spring boot microservice, listening to a queue/topic/stream on the messaging system. When a new event appears, it updates its PostgreSQL database. It should also implement the query endpoints (get one and get all).


### Running in development

Race Application Service runs as two microservices
(`com.race.app.race_application_command_service` and
`com.race.app.race_application_query_service`) that coordinate via
[Apache Kafka](http://kafka.apache.org/) and a JDBC-compliant database
like [PostgreSQL](https://www.postgresql.org/).  In order to run the
Race Application Service, you'll first need to run a Kafka Cluster and a
database.

### Supporting Services

There is a `Makefile` for running and interacting with the
supporting services and/or the example application.  This `Makefile`
uses `docker` and `docker-compose` under the hood to orchestrate the
various services.

Running the supporting services (Kafka, ZooKeeper, PostgreSQL) via the
`Makefile` is the easiest way to get started.  However, if you want to
run the supporting services manually, you can use a package manager to
install the services.

#### Running Manually

The following instructions provide an example for those running Windows to install
the supporting services.

#### Install

##### Kafka and ZooKeeper

##### Downloading, Installation and Running
Apache Kafka can be downloaded from its official site [kafka.apache.org](https://kafka.apache.org/downloads).
Kafka uses [Apache ZooKeeper](https://zookeeper.apache.org/) to maintain
runtime state and configuration consistently across the cluster.
You'll need to install both Kafka and ZooKeeper.

Step 1: Go to the Downloads folder and select the downloaded Binary file.

Step 2: Extract the file and move the extracted folder to the directory where you wish to keep the files.

Step 3: Copy the path of the Kafka folder. Now go to config inside kafka folder and open zookeeper.properties file. Copy the path against the field dataDir and add /zookeeper-data to the path.

``` sh
dataDir=c:/kafka/zookeeper-data
```

Step 4: Now in the same folder config open server.properties and scroll down to log.dirs and paste the path. To the path add /kafka-logs

Step 5: This completes the configuration of zookeeper and kafka server. Now open command prompt and change the directory to the kafka folder. First start zookeeper using the command given below:

``` sh
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```

Step 6: Now open another command prompt and change the directory to the kafka folder. Run kafka server using the command:

``` sh
.\bin\windows\kafka-server-start.bat .\config\server.properties
```


##### PostgresQL

To install postgres:

1. Download PostgreSQL installer for Windows
2. Install PostgreSQL
3. Verify the installation

Download PostgreSQL installer for Windows

Go to the download page of [PostgreSQL installers on the EnterpriseDB](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads) and click the download link.

Install PostgreSQL

Click on the installer file and follow installation steps.
- set data directory
- set password
- define port 5432

Verify the installation

Click the psql application to launch it.           
Enter all the necessary information such as the server, database, port, username, and password. To accept the default, you can press Enter.  Note that you should provide the password that you entered during installing the PostgreSQL.



##### Microservices
Start microservices using preferable idea, generated jar file or docker.