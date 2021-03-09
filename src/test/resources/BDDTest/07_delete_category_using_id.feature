@tagToIdentifyThatBeginAfterShouldRunForThisFeatureOnly
Feature:
  As a software engineer, I want to delete a category that I am no longer using so that my category list can be simplified

  Background:
    Given my service is running

  Scenario Outline: The software engineer delete a category by using id (Normal Flow)
    Given the category with id "<categoryId>" is existed in the system:
    When user requests to delete the category with id "<categoryId>"
    And request to find the category with id "<categoryId>"
    Then the system should output an error code of no category instance founded: "<code>"

    Examples:
      | categoryId | code |
      | 1      | 404      |
      | 2         | 404   |


  Scenario Outline: The software engineer delete a category and will not found it in all categories(Alternate Flow)
    Given the category with id "<categoryId>" is existed in the system:
    When user requests to delete the category with title "<categoryId>"
    And request to find all categories
    Then the category with id "<categoryId>" will not found in the all categories result

    Examples:
      | categoryId    |
      | Project A    |
      | Assignment 1 |

  Scenario Outline: The software engineer failed to delete a category due to wrong data type of id (Error Flow)
    Given the category list is not empty
    When user requests to delete the category with id "<categoryId>"
    Then Then the system should output an error code of no category instance founded: "<code>"

    Examples:
      | categoryId |code|
      | adb     |404|



