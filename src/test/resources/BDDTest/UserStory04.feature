@tagToIdentifyThatBeginAfterShouldRunForThisFeatureOnly
Feature: View projects
  As a user,
  I want to see all the projects I've created so far
  so I can have a overall impression on the tasks I need to complete.

  Background:
    Given the rest-api todo list manager is running
    And there exists three projects:
      | id | title            | active | completed | description |
      | 2  | ecse 429 project | false  | false     | half done   |
      | 3  | comp 310 project | false  | true      |             |
      | 1  | Office Work      | false  | false     |             |

  #Normal flow
  Scenario: Viewing a project with existed valid ID
    When request to see a project with a given ID 3
    Then system will list only that project

  #Alternate flow
  Scenario: Viewing all the projects
    When request to list all the projects
    Then system will successfully list all projects


  #Error flow
  Scenario:  Viewing a project with invalid ID
    When request to see a project with ID 1000
    Then system show error can't find such project

