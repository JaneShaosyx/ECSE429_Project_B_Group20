@tagToIdentifyThatBeginAfterShouldRunForThisFeatureOnly
Feature: Assign a category to a project
  As a user,
  I want to combine a project with a category
  so I can have a more detailed classification

  Background:
    Given the rest-api todo list manager is running


  #Normal flow
  Scenario: assign a new category to the project
    Given a following category:
      | id | title | description |
      | 3  | home  |             |
    When assigning the category id 3 with project id 1
    Then system will add category 3 under project 1

  #Alternate flow
  Scenario: assign a already assigned category to project
    Given a following category:
      | id | title | description |
      | 3  | home  |             |
    And this category is already assigned to project id 1
    When assigning this category with project id 1 again
    Then system will not add the repeated category again

  #Error flow
  Scenario: assign a non-existed category
    When request to assign invalid category id 100 to project id 1
    Then the system will show the error message "Could not find thing matching value for id"

