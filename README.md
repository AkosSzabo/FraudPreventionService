# FraudPreventionService

## Building and running the application
### Building the artifact
*mnv clean package* from the project folder
### Running integration tests
*mvn clean verify -P int-test* from the project folder
### Running the service locally
- After building the artifact
- *java -jar -Dspring.profiles.active=swagger target/FraudPreventionService-0.0.1-SNAPSHOT.war* 
### Using the service
Local URI: *http://localhost:5000/api/prevention/check*
Local swagger UI: *http://localhost:5000/swagger-ui.html*
AWS URI: *http://fraudpreventionserviceapp2-dev.eba-yfjuzmbr.eu-west-2.elasticbeanstalk.com/api/prevention/check*
AWS swagger UI: *http://fraudpreventionserviceapp2-dev.eba-yfjuzmbr.eu-west-2.elasticbeanstalk.com/swagger-ui.html*

Operation: POST
Relative path: /api/prevention/check
Payload examples: 

no issues:
{
	"dateTime": "2020-06-10 12:11:23",
	"userAccountNumber": "account1",
	"payeeAccountNumber": "external1",
	"dollarAmount": 9000.2
}
frequency issue:
{
	"dateTime": "2010-06-20 12:11:23",
	"userAccountNumber": "account1",
	"payeeAccountNumber": "external1",
	"dollarAmount": 9000.2
}
amount and frequency:
{
	"dateTime": "2020-07-30 12:11:23",
	"userAccountNumber": "account1",
	"payeeAccountNumber": "external1",
	"dollarAmount": 21000.2
}
amount issue:      
{
	"dateTime": "2020-06-10 12:11:23",
	"userAccountNumber": "account1",
	"payeeAccountNumber": "external1",
	"dollarAmount": 21000.2
}
unknown payee issue:      
{
	"dateTime": "2010-06-20 12:11:23",
	"userAccountNumber": "account1",
	"payeeAccountNumber": "external3",
	"dollarAmount": 21000.2
} 
bad requests:
{
	"dateTime": "-",
	"userAccountNumber": "account1",
	"payeeAccountNumber": "external3",
	"dollarAmount": 21000.2
}
{
	"dateTime": "2010-06-20 12:11:23",
	"userAccountNumber": "account1",
	"payeeAccountNumber": "external3",
	"dollarAmount": -1
}

see transactions historical data for reference:
*https://github.com/AkosSzabo/FraudPreventionService/blob/master/src/main/resources/import.sql*
## Design decisions
   /// max int in days is more than 5 million years, dont really need long
## Possible improvements
