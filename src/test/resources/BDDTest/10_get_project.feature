@tagToIdentifyThatBeginAfterShouldRunForThisFeatureOnly
Feature:
  As a software engineer, I want to get projects so that I can start to work on that project.

  Background:
    Given my service is running

  Scenario Outline: The software engineer get a project with id (Normal Flow)
    Given  the project with id "<ProjectId>" and title "<ProjectTitle>" is existed in the system:
    When user requests to get a project with id "<ProjectId>"
    Then only the project with id "<ProjectId>" and title "<ProjectTitle>" should be returned

    Examples:
      | ProjectId | ProjectTitle|
      | 1         | Office Work |


  Scenario Outline: The software engineer get all the instances of projects (Alternate Flow)
    Given  the project list size is known and does not include the project with the given title "<ProjectTitle>"
    When user add a new project with title "<ProjectTitle>"
    And requests to get all the instances of projects
    Then the system should return instances of projects in the updated project list contains a new one with the given title "<ProjectTitle>" and the size of the project list will be incremented.
    Examples:
      | ProjectId | ProjectTitle|
      | 1         | project 1   |
      | 2         | project 2   |

  Scenario Outline: The software engineer fail to get a project due to wrong data type of project id(Error Flow)
    Given  the project list is not empty
    When user requests to get a project with id "<ProjectId>"
    Then Then the system should output an error code of no project instance founded: "<code>"

    Examples:
      | ProjectId |code|
      | adb        |404  |
