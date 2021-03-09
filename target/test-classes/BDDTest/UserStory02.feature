@tagToIdentifyThatBeginAfterShouldRunForThisFeatureOnly
Feature: Update Category
  As a user,
  I want to update some info of an existed category
  so I can change it depending on the change of situation

  Background:
    Given the rest-api todo list manager is running

  #Normal flow
  Scenario: Update a category with all the fields
    When updating category id 1 with following params:
      |id	|title	       |description                |
      |1	|School        |   for school assignment   |
    Then system will update category id 1 with new contents.

  #Alternate flow
  Scenario: Update a category with only title
    When updating category id 1 with only new title,status and description not change
    Then system will update category id 1 and set the optional fields to default values.

  #Error flow
  Scenario: Update the ID of a category in a wrong format
    When updating category id 1 to id "1"
    Then will show the error message give status Bad request

