package com.udacity.jdnd.course3.critter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Launches the Spring application. Unmodified from starter code.
 */
@SpringBootApplication
public class CritterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CritterApplication.class, args);
	}

}

//To avoid MySql foreign key issue when creating a pet for a customer, ensure ownerId is added in Postman, as below
//{
//		"type": "CAT",
//		"name": "Kilo",
//		"ownerId": 1,
//		"birthDate": "2019-12-16T04:43:57.995Z",
//		"notes": "HI KILO"
//		}

//To avoid not found in Postman, for employee days available set to http://localhost:8082/user/employee/3  (i.e. value 3 not 1)

//In POSTMAN for "PUT Create Schedule" test, I had to change PUT to POST and use http://localhost:8082/schedule instead
//of http://localhost:8082/user/employee/1  and use the test data below for the BODY test data.
//{
//		"employeeIds": [1],
//		"petIds": [1],
//		"date": "2019-12-17",
//		"activities": ["PETTING", "FEEDING"]
//		}

//Udacity and external web resources used to investigate and resolve issues:
//      https://knowledge.udacity.com/questions/529358
//      https://knowledge.udacity.com/questions/328548
//		https://knowledge.udacity.com/questions/540673
//		https://knowledge.udacity.com/questions/289395
//		https://meri-stuff.blogspot.com/2012/03/jpa-tutorial.html#RelationshipsBidirectionalOneToManyManyToOneConsistency
//      https://stackoverflow.com/questions/15998824/mapping-setenum-using-elementcollection
//		https://stackoverflow.com/questions/12639791/what-is-the-reason-for-java-lang-illegalargumentexception-no-enum-const-class-e
