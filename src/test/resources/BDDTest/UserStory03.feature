@tagToIdentifyThatBeginAfterShouldRunForThisFeatureOnly
Feature: Update project
  As a user,
  I want to update some info of an existed project
  so I can change it when there comes some emergency situation

  Background:
    Given the rest-api todo list manager is running

  #Normal flow
  Scenario: Update a project with all the fields
    When updating project id 1 with following params:
      | id | title           | completed | active | description                     |
      | 1  | new Office Work | true      | true   | finished, but need double check |
    Then system will update project id 1 with new contents.

  #Alternate flow
  Scenario: Update a project with only title
    When updating project id 1 with only new title,status and description not change
    Then system will update project id 1 and set the optional fields to default values.

  #Error flow
  Scenario: Update the ID of a project in a wrong format
    When updating project id 1 to id "1"
    Then will show the error message give status Bad request

