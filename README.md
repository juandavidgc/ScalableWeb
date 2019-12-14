# ScalableWeb

[![Build Status](https://travis-ci.com/juandavidgc/ScalableWeb.svg?branch=master)](https://travis-ci.com/juandavidgc/ScalableWeb)

## Table of Contents

**[Architecture](#Architecture)**
  * [Components](#Components)
  * [Deployment](#Deployment)

**[DevOps](#DevOps)**

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

[![Play With Docker](https://github.com/play-with-docker/stacks/raw/cff22438cb4195ace27f9b15784bbb497047afa7/assets/images/button.png)](https://labs.play-with-docker.com/?stack=https://raw.githubusercontent.com/juandavidgc/ScalableWeb/master/stack.yml)

## DevOps
![Build Pipeline](docs/build-pipeline.jpg)