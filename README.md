# Organisation-API

## Author
Michael Mbaya Gikunda.

### Description
This a REST API for querying and retrieving scoped news and information in an organisation.

## Features

As a user of the application you will be able to:

1. Add department and view departments. 
2. Add a user and view users.
3. Adds news and view news.

## Prerequisites & setup
### Prerequisites
* 1.Git.
* 2.Java SDK and Environment
* 3.Java IDE preferably IntelliJIDEA Community or Ultimate Edition.
* 4.Gradle.
* 5.PostgreSQL

##### Run

* fork the repository, clone your forked repository in your local machine and gradle run the project on your IntelliJ app.

## How to use (Behavior)
## User Guide

####View Landing page
* Method: GET
* Route: /

#### Create general News: 
* Method: POST:
* Route: /news/new
* Sample Data: 
*     {
		"newsTitle":"IT-Mbaya",
		"newsContent":"Geeks for Comps"
	    }
#### Create department News: 
* Method: POST:
* Route: /news/new
* Sample Data: 
*       {
        "newsTitle":"Annual Marketing Retreat",
        "newsContent":"This year's annual retreat will be lit! Turn up people!",
        "departmentId":"2"
        }
#### View all news: 
* Method: GET:
* Route: /news
#### View individual news: 
* Method: GET:
* Route: /news/:id
* where :id is a number 1,2,3 onwards
#### Create New Department: 
* Method: POST:
* Route: /department/new
* Sample Data: 
*     { 
  	"departmentName":"ICT",
  	"departmentDescription":"Geeks for Comps",
  	"departmentEmployeesNumber":"2"
      }
#### View all departments: 
* Method: GET:
* Route: /departments
#### View individual department: 
* Method: GET:
* Route: /department/:id
* where :id is a number 1,2,3 onwards
#### View news of a particular department: 
* Method: GET:
* Route: /department/:id/news
* where :id is a number 1,2,3 onwards
#### View users of a particular department: 
* Method: GET:
* Route: /department/:id/users
* where :id is a number 1,2,3 onwards
#### Create New User: 
* Method: POST:
* Route: /user/new
* Sample Data: 
*       {
        "userName":"One and only Mbaya",
        "userCompanyPosition":"ICT Manager",
        "userCompanyRole":"Run company’s ICT services",
        "departmentId":"3"
        }
#### View all users:
* Method: GET: 
* Route: /users
#### View individual user: 
* Method: GET:
* Route: /user/:id
* where :id is a number 1,2,3 onwards


## TECHNNOLOGIES USED

* Java
* Java Spark
* postgreSQL
* Postman

## BUGS

* Having to remember departments by their id is cumbersome. working on ways to change this.
* If you come across any bugs, please reach out to the contact below

## CONTACTS

* For any enquiries and comments, reach out at: 
* gikundamike@gmail.com

## LICENSE

* MIT License

Copyright (c) 2020, Michael-Mbaya, #Organisation-API

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.