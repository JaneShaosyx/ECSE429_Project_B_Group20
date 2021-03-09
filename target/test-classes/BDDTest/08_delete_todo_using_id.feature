@tagToIdentifyThatBeginAfterShouldRunForThisFeatureOnly
Feature:
  As a software engineer, I want to delete a todo that I am no longer using so that i can declutter my working list and remove the todo more correctly

  Background:
    Given my service is running

  Scenario Outline: The software engineer delete a todo by using id (Normal Flow)
    Given the todo with id "<todoId>" is existed in the system:
    When user requests to delete the todo with id "<todoId>"
    And request to find the todo with id "<todoId>"
    Then the system should output an error code of no todo instance founded: "<code>"

    Examples:
      | todoId | code |
      | 1         | 404  |
      | 2         | 404   |


  Scenario Outline: The software engineer delete a todo and will not found it in all categories(Alternate Flow)
    Given the todo with id "<todoId>" is existed in the system:
    When user requests to delete the todo with title "<todoId>"
    And request to find all categories
    Then the todo with id "<todoId>" will not found in the all categories result

    Examples:
      | todoId  |
      | 1    |
      | 2    |

  Scenario Outline: The software engineer failed to delete a todo due to wrong data type of id (Error Flow)
    Given the todo list is not empty
    When user requests to delete the todo with id "<todoId>"
    Then Then the system should output an error code of no todo instance founded: "<code>"

    Examples:
      | todoId |code|
      | adb     |404|

