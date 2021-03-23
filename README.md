# DatePicker
A start of a simple app to pick random dates for you and your signifigant other based on certain criteria (cost and time duration)

Right now it does not come prepoluated with any ideas - be sure to add your own!

The pages for it are likewise not complete - they are currently placeholders. You will need soemthing like postman (with desktop agent) to use the app:

[![Run in Postman](https://run.pstmn.io/button.svg)](https://god.postman.co/run-collection/17b33eaf9e048f52730c?action=collection%2Fimport)

To start it at localhost:8080, download the repo and ensure your java home is set to a jdk 1.8 or higher and run the below command from inside the repo:

gradlew bootrun --console=plain

# Available endpoints:

PUT /putDateIdea - takes a date object and adds it to the database. See below section on building a valid date json

GET /viewDateIdea/[dateId] - displayes a given date dependant on dateId (IE /viewDateIdea/123)

DELTE /deteleDateIdea/[dateId] - deletes a given date dependant on dateId (IE /deleteDateIdea/123)

POST /findDateIdeas - Returns a list of date ideas matching criteria on the date json. See below section on building a valid date json

POST /randomDateIdea - For the more indecesive, returns a single date idea matching criteria on the date json. See below section on building a valid date json

# Building a valid date json
A valid date JSON looks like the below

```
{
  "dateName":"Bowling",
  "dateDescription":"Go to the bowling alley",
  "cost":25.67,
  "duration":30
}
```


For the findDateIdeas and randomDateIdea endpoints, all fields are optional. If provided they serve the following functions, and invalid entries of the correct data type (such as a negative cost) will currently simply result in no dates returned as no dates will match that criteria:

* dateName - String - Finds dates containing the provided string in the name field
* dateDescription - String - This is currently unused and will be ignored if provided
* cost - Double - dates with a cost equal to or less than the provided value will be returned
* duration - Integer - dates with a duration equal to or less than the provided value will be returned


For the putDateIdea endpoint, all fields are required and are validated as below:

* dateName - String - Must be populated with at least 1 character and less than 45 characters
* dateDescription - String - Must be populated with at least 1 character and less than 255 characters
* cost - Double - Must be populated and at least 0.0
* duration - Integer - Must be populated and at least 1

# What's next?
There are a few features to enhance and add, more may be added later:

* Add more criteria, such as indoor/outdoor activities, seasons for the activity if its outdoor, how much preplanning is required, and other criteria I might think of.
* Add better feedback for invalid requests
* Implement html pages with forms for various endpoints
* Add a script to prepopulate some common/popular ideas to the DB on initialization.
