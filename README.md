# Appointment API
The app uses PostgreSQL 10.6, Spring Boot 2.2.10  
For the first run, you have to uncomment 3 last lines in application.properties and comment them back after the first run. Swagger 2 API documentation is located at "localhost:8080/swagger-ui.html"

###  **appointment-controller**  
##### GET  
/appointment  
Returns a list of appointments that are not in Declined status.  
Parameters (Query string):  
status(optional), acceptable values - excludeDeclined(by default), all, approved, declined, negotiation, open  
  
  
##### POST  
/appointment  
Creates a new appointment  
Parameters (JSON):  
teacher_data_id(Object, required) that contains only id;  
  
Example JSON request body:  
{  
	"teacher_data_id":{  	
	    "id": 0  
	}  
}
  
  
/appointment/{id}/reservation  
Making a reservation in a selected appointment.  
  
  
/appointment/{id}/approve  
Approving selected appointment  
  
  
/appointment/{id}/decline  
Declining selected appointment  
  
  
  
###  **teacher-data-controller**  
##### GET  
/teacher/data/me  
Returns all data for a current teacher  
  
  
/teacher/data  
Returns all data for all teacher  
  
  
##### POST  
/teacher/data  
Creates a new data for a current teacher  
Parameters (JSON):  
workfrom (time in format HH:mm:ss, required)  
workto (time in format HH:mm:ss, required)  
currency (three-letter ISO code, required)  
price (money/hour, required)  
date (date in yyyy-MM-dd format, required)  
  
 Example JSON request body:    
 {  
  	"workfrom": "13:00:00",  
  	"workto": "17:00:00",  
  	"currency": "UAH",  
  	"price": "100/h",  
  	"date": "2021-08-10"  
 }  
  
  ### **user-controller**  
  ##### GET  
  /users  
  Returns a list of all registered users  
  Parameters (Query string):  
  role(optional) acceptable values - student, teacher, all(by default)  
    
    
  /users/{id}  
  Returns a selected user  
  
  
  ##### POST  
  /register  
  Register a new user  
  Parameters (JSON):  
  firstName (First name, required)  
  lastName (Last name, required)  
  role_id (Object, required), that contains a role id. 1 - student, 2 - student  
  email (User email, required)  
  password (User password, required)  
  
  Example JSON request body:  
  {  
  	"firstName": "Fname",  
  	"lastName": "Lname",  
  	"role_id": {  
  		"id": 1  
  	},  
  	"email": "example@mail.box",  
  	"password": "pass"  
  }
  
