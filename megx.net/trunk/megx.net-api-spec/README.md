Micro B3 Information System Integration test
=====

## Overview

This maven project is about testing an existing MB3-IS installation.

it makes use of

* RESTAssured
* JUnit >= 4.11
 * Categories
 * and Rules
* Maven profiles


RestAssured is used cause it simplifies testing of REST web services and has support for simple web page tests. It supports XML and JSON parsing and several authentication mechanisms.

JUnit Categories are used to 'tag' tests, so that the can be used in one or more test scenarios. 

Currently, we have the following test categories:

* Availability (check if e.g. a web page is available by e.g. just sending an HTTP Head request)
* Integration 
* RESTService
* ThirdParty
* WebPage

They are organized in package `net.megx.test.categories`

The actual tests are organized in several packages: 

* `net.megx.test.webpage` for testing web pages
* `net.megx.test.ws` for testing web services

TODO names sub pages by symbolic module/component name

The test scenarios are a combination of which MB3-IS instance/environment should be tested for what?

This is organized by maven profiles.

There are profiles configuring the MB3-Instance and profiles for the kind of test run, which should be performed.

Profiles are

* Instance/environment
 * `production-test-env` (for testing the live server)
 * If no profile is given all test will be executed against http://localhost:8080/megx.net
* Kind of tests
 * `avail-tests` 
 * `web-page-tests`



