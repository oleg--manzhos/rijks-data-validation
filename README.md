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
- [Contributing](#contributing)
- [Documentation](#documentation)
- [Support](#support)
- [License](#license)
- [Acknowledgements](#acknowledgements)
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
# Example command to run tests
mvn clean test
```
### Findings
- Userset response contains BOM char, that prevents JSON from being parsed
- Special chars in the pagination parameters are not treated incorrectly
- Filter by artist (both ascending and descending) doesn't work correctly