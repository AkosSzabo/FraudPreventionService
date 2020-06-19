# FraudPreventionService

Create a microservice that evaluates a new transaction and marks it possible fraudulent if:
1. Amount is unexpectedly high
2. Frequency deviates from the expected value
3. The payee is unknown
Do these checks against historical transactions data

## Building and running the application
### Building the artifact
`mnv clean package` from the project folder
### Running integration tests
`mvn clean verify -P int-test` from the project folder
### Running the service locally
After building the artifact or its available in the /target directory 
`java -jar -Dspring.profiles.active=swagger target/FraudPreventionService-0.0.1-SNAPSHOT.war`
### Using the service
- Local URI: *http://localhost:5000/api/prevention/check*
- Local swagger UI: *http://localhost:5000/swagger-ui.html*
- AWS URI: *http://fraudpreventionserviceapp2-dev.eba-yfjuzmbr.eu-west-2.elasticbeanstalk.com/api/prevention/check*
- AWS swagger UI: *http://fraudpreventionserviceapp2-dev.eba-yfjuzmbr.eu-west-2.elasticbeanstalk.com/swagger-ui.html*

Operation: `POST ` 
Relative path: `/api/prevention/check  `
Payload examples: 
```
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
```

see transactions historical data for reference:
*https://github.com/AkosSzabo/FraudPreventionService/blob/master/src/main/resources/import.sql*
## Design decisions
- lombok dependency: removes most of the boilerplate codes
- Using in-memory DB: this service would probably rather make a call to a transactions microservice, it is just done like this for simplicity
- No currency type handling: done to simplify the codebase
- ChronoUnit.DAYS.between is cast to integer: integer in days is more than 5 million years, dont really need long, for transactions, also long literals are an eyesore
- Why POST endpoint: as json Can be argued that this endpoint should be GET, if we think about is getting a resource, but I think its more like creating a calculation, and overall passing a json payload is nicer solution
- Parallel stream in ParallelFraudPreventionService: Hence the name of the service class implementation, rules can be evaluated independently, this leverages that
- TransactionContext is not immutable: good practice to keep domain objects immutable te avoid side effects resulting in issues, TransactionContext enriched with TransactionHistory is just a way to keep it simple here 
## Possible improvements
- Frequency calculation using distribution rather than simple average, that's why LocalDateTimeFrequencyCollector is introduced (not really needed right now could be just a simple difference of days)  Its more of a bit of functional programming fooling around
- A more sophisticated amount check 
- coverage, sonar, security scan, mutation test
## Architecture in Practice
- As mentioned transactions data might be better kept under the ownership of an other service, so this service would have a REST client calling transactions service 
- if this service would have a DB it should be storing the rules, rule mappings
- Rules representation will be unavoidable at one point. Either having some kind of templating or at least setting up criteria by which some rules apply to some accounts/ regions / country. That's why the original code contained a rule map, now List. To be able to dynamically control the evaluation
- Some kind of machine learning can be introduced look for patterns that look fraudulent. Training neural network with live data even if its not used live right from the start but gathering statistics. Historical data used in training. Machine learning implementation can run prod parallel with heuristics 
- These request can be persisted as jobs with status and maybe moving away from providing a response right away if some timely processing is required
- Before the rules evaluated the required data can be fetched by Async call if multiple MS involved
- Caching introduced as needed
- Introducing multiple instances, horizontal scaling depending on metrics, service discovery, geographical load balancing, circuit breaking and bulkheads need can be evaluated
- Some kind of Big Data - Map Reduce solution might be warranted depending on volume












