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

###Two modules:

####race_application_command_service: 
Spring boot microservice implementing the mutating API endpoints (create, patch, delete). The service produces an event upon receiving an API request and publishes it to a Kafka messaging system.

#####race_application_query_service: 
Spring boot microservice, listening to a queue/topic/stream on the messaging system. When a new event appears, it updates its PostgreSQL database. It should also implement the query endpoints (get one and get all).