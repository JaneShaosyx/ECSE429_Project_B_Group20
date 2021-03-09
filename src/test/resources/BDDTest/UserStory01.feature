@tagToIdentifyThatBeginAfterShouldRunForThisFeatureOnly
Feature: Update todo
  As a user,
  I want to update some info of an existed todos
  so I can change it when there comes some emergency situation

  Background:
    Given the rest-api todo list manager is running

  #Normal flow
  Scenario: Update a todo with all the fields
    When updating todo id 1 with following params:
    |id	|title	       |doneStatus	|description|
    |1	|assignment 1   |false       |    ASAP   |
    Then system will update todo id 1 with new contents.

  #Alternate flow
  Scenario: Update a todo with only title
    When updating todo id 1 with only new title,status and description not change
    Then system will update todo id 1 and set the optional fields to default values.

  #Error flow
  Scenario: Update the ID of a todo in a wrong format
    When updating todo id 1 to id "1"
    Then will show the error message give status Bad request

