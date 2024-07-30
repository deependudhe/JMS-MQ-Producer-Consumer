# JMS MQ Integration and Performance Testing

This repository contains code for integrating with IBM MQ using JMS, as well as performance testing the JMS operations. The project includes producer and consumer examples, configuration management, unit tests, and performance benchmarks.

### Explanation

1. **Introduction**: Provides a brief overview of the project.
2. **Prerequisites**: Lists the required tools and dependencies.
3. **Installation**: Steps to clone and build the project.
4. **Configuration**: Explains how to configure the JMS connection.
5. **Usage**: Instructions for using the producer and consumer classes.
6. **Testing**: Details on running unit, integration, and performance tests.
7. **Contributing**: Guidelines for contributing to the project.
8. **License**: Information about the project’s license.

This structure ensures that users can easily understand how to set up, configure, use, and test the project.

## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
    - [Producer](#producer)
    - [Consumer](#consumer)
- [Testing](#testing)
    - [Unit Tests](#unit-tests)
    - [Integration Tests](#integration-tests)
    - [Performance Tests](#performance-tests)
- [Docker Setup](#docker-setup)
- [Contributing](#contributing)
- [License](#license)

## Introduction

This project demonstrates how to use JMS with IBM MQ. It includes examples of sending and receiving messages, as well as unit tests and performance tests to ensure the reliability and efficiency of the JMS operations.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven
- Docker
- IBM MQ installation (or access to a running IBM MQ instance)
- Access to a Git repository (GitHub, GitLab, etc.)

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/deependudhe/JMS-MQ-Producer-Consumer.git
    cd JMS-MQ-Integration
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

## Configuration

The configuration for connecting to the IBM MQ is specified in the `JmsConfig.java` file. Update the configuration parameters as needed:

```java
public class JmsConfig {
    private static final String HOST = "localhost";
    private static final int PORT = 1414;
    private static final String CHANNEL = "DEV.APP.SVRCONN";
    private static final String QMGR = "QM1";
    private static final String APP_USER = "app";
    private static final String APP_PASSWORD = "";
    private static final String QUEUE_NAME = "DEV.QUEUE.1";

    // Getter methods for the above properties
}
```
## Usage
### Producer

To send a message to the queue, use the `Producer` class:

```java
public class Producer {
    public void sendMessage() {
    // Implementation for sending a message
    }
}
```
### Consumer

To receive a message from the queue, use the `Consumer` class:
```java
public class Consumer {
    public void receiveMessage() {
        // Implementation for receiving a message
    }
}
```

## Testing
### Unit Tests

Unit tests are provided for the producer and consumer classes using JUnit and Mockito. 
To run the unit tests, use:
```shell
mvn test
```
### Integration Tests

Integration tests verify the functionality against a real JMS broker. Ensure the JMS broker is running and accessible. Run the integration tests with:

```shell
mvn verify -P integration-tests
```

### Performance Tests

Performance tests are implemented using JMH. To run the performance tests, execute the JmhRunner class:

```shell
java -cp target/benchmarks.jar JmhRunner
```

## Docker Setup

You can use Docker to run an IBM MQ container for testing purposes. Ensure you have Docker installed on your machine.
### Running IBM MQ in Docker

1. Pull the IBM MQ Docker image and run the container:
    ```shell
    docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --volume qm1data:/mnt/mqm --publish 1414:1414 --publish 9443:9443 --detach --env MQ_APP_PASSWORD=somePassword --env MQ_ADMIN_PASSWORD=somePassword --name QM1 icr.io/ibm-messaging/mq:latest
    ```
2. This command will:
   * Accept the license agreement.
   * Set the queue manager name to QM1.
   * Mount a Docker volume qm1data to persist data.
   * Publish ports 1414 (for MQ) and 9443 (for the web console).
   * Set the application and admin passwords to passw0rd.
   * Name the container QM1.
   * Use the latest IBM MQ image from the IBM Cloud Container Registry.
3. Ensure your `JmsConfig` settings match the Docker container configuration:
```java
public class JmsConfig {
    private static final String HOST = "localhost";
    private static final int PORT = 1414;
    private static final String CHANNEL = "DEV.APP.SVRCONN";
    private static final String QMGR = "QM1";
    private static final String APP_USER = "app";
    private static final String APP_PASSWORD = "";
    private static final String QUEUE_NAME = "DEV.QUEUE.1";

    // Getter methods for the above properties
}
```

## Contributing

Contributions are welcome! Please follow these steps to contribute:

    Fork the repository.
    Create a new branch: git checkout -b feature-name.
    Make your changes and commit them: git commit -m 'Add some feature'.
    Push to the branch: git push origin feature-name.
    Create a pull request.

License

This project is licensed under the MIT License - see the `LICENSE` file for details.