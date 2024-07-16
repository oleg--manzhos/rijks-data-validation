# Rijks open data validation project

## Description
Rijksmuseum data services provide access to object metadata, bibliographic data and user generated content.
These pages comprise the technical documentation of RijksData, also available are a general introduction, as well as the open data policy of the museum. Contact us if you have any questions or want to report issues.
## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Testing](#testing)
- [CI/CD Integration](#cicd-integration)
- [Documentation](#documentation)
- [Findings](#findings)

## Installation
### Prerequisites
- Java 17
- Maven 3.X
### Commands
```mvn clean install```

## Usage
### How to Run Tests
All tests for the whole project can be run by:
```bash
# The command to run all the tests
mvn clean test
```
There are 3 test suits, designed to execute the groups of tests:
- `AllTestsSuite` allows to run all available tests (like `mvn test`)
- `ContractSuite` executes contract related suit
- `HappyPathFunctionalSuite` allows to execute the functional tests only

```bash
# The command to run a test suite
mvn test -Dsurefire.suiteXmlFiles={test_suite_name}.xml
```

### Testing

### CI/CD Integration
Configurable command line parameter `api_key` is sent as `-Dapi_key` parameter. The test suites are also available to be called from the CI/CD.
Jenkins file (template) allows to execute tests on the CI/CD pipeline

### Documentation
Javadocs and this README file are the source of the documentation

### Findings
- Userset response contains BOM char, that prevents JSON from being parsed
- Special chars in the pagination parameters are not treated incorrectly
- Filter by artist (both ascending and descending) doesn't work correctly