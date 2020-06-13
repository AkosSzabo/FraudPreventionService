# FraudPreventionService

## Building and running the application
### Building the artifact
*mnv clean package* from the project folder
### Running integration tests
*mvn clean verify -P int-test* from the project folder
### Running the service locally
- After building the artifact
- *java -jar -Dspring.profiles.active=swagger target/FraudPreventionService-0.0.1-SNAPSHOT.jar* 
### Using the service
LOCAL URI:
LOCAL swagger UI: 
AWS URI:
AWS swagger UI:

Operation: POST
Relative path: /api/prevention/check
Payload examples: 

{"dateTime" : "2010-06-20 12:11:23",
 "userAccountNumber"  : "account1",
 "payeeAccountNumber" : "external1",
  "dollarAmount" : 9000.2}
  
  {"dateTime" : "2010-06-20 12:11:23",
   "userAccountNumber"  : "account1",
   "payeeAccountNumber" : "external1",
    "dollarAmount" : 9000.2}
    
    {"dateTime" : "2010-06-20 12:11:23",
     "userAccountNumber"  : "account1",
     "payeeAccountNumber" : "external1",
      "dollarAmount" : 9000.2}
  
## Design decisions
   /// max int in days is more than 5 million years, dont really need long
## Possible improvements
