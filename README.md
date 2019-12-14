# ScalableWeb

[![Build Status](https://travis-ci.com/juandavidgc/ScalableWeb.svg?branch=master)](https://travis-ci.com/juandavidgc/ScalableWeb)

## Table of Contents

**[The API](#heading--1)**
  * [Store strings](#heading--1-1)
  * [Calculate differences](#heading--1-2)

**[Architecture](#Architecture)**
  * [Components](#Components)
  * [Deployment](#Deployment)

**[DevOps](#DevOps)**

<div id="heading--1"/>

## The API

<div id="heading--1-1"/>

### Store Strings
```
<host>/v1/diff/<ID>/left & <host>/v1/diff/<ID>/right
```
Each of these endpoints receive a JSON structure with the base64 encoded string to be compared.

```json
{
  "base64": "{encoded string}"
}
```

#### Responses
| HTTP Code             | Explanation             |
| -------------         |:-----------------------------------------------:|
| 400 (OK)              | The String was accepted |
| 406 (Not Acceptable)  | Empty String, Invalid JSON or Bad encoding      |

<div id="heading--1-2"/>

### Calculate differences
```
<host>/v1/diff/<ID>
```
This endpoint returns the result of the comparisson between the previous received encoded strings.

If the JSON structures are equals:
```json
{
  "status": "EQUAL"
}
```

If the JSON structures are of not of equal size:
```json
{
  "status": "NOT_EQUAL"
}
```

If the JSON structures are of the same size but are different:
```json
{
  "status": "SAME_SIZE_BUT_DIFFERENT",
  "differences": [
    {
      "position": 4,
      "length": 2
    },
    {
       "position": 23,
       "length": 2
    }
  ]
}
```
The position is where the difference start (starts in 0), and the length is the amount of characters that are different.

#### Responses
| HTTP Code                 | Explanation             |
| -------------             |:-----------------------------------------------:|
| 400 (OK)                  | Success calculation |
| 417 (Expectation failed)  | Not enough parts (right, left or both)      |
| 406 (Not acceptable)      | Empty ID      |


## Architecture

### Components
![Components](docs/components.jpg)

The solution is composed of the following components:
 * Core. Main component that contains business logic. Is composed of the following low level components:
    * entities. This component has all domain classes, exceptions and interfaces (without implementations).
    * usecases. This component has implementations of the business logic exposed through interfaces located in entities.
 * API. This component has the modules used to expose the API of the solution.
    * rest-api. API exposed through a series of REST endpoints.
 * Datastore. This component has the modules used to store the state of the solution.
    * memory-datastore. Implementation that stores the state in memory.

### Deployment
![Deployment](docs/deployment.jpg)

The solutions is delivered through a docker instance pushed to docker hub [juandavidgc/scalable-web](https://hub.docker.com/repository/docker/juandavidgc/scalable-web).
This instance has all deployable units within.

If you want to try this docker instance out, you can use this link:

[![Play With Docker](https://github.com/play-with-docker/stacks/raw/cff22438cb4195ace27f9b15784bbb497047afa7/assets/images/button.png)](https://labs.play-with-docker.com/?stack=https://raw.githubusercontent.com/juandavidgc/ScalableWeb/master/stack.yml)

## DevOps
DevOps is managed using travis-ci. With this, maven stages are executed, the docker images is built and push to
docker hub registry.