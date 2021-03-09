@tagToIdentifyThatBeginAfterShouldRunForThisFeatureOnly
Feature:
  As a software engineer, I want to get  todo tasks so that I can start to work on them.

  Background:
    Given my service is running

  Scenario Outline: The software engineer get a todo with id (Normal Flow)
    Given  the todo with id "<TodoId>" and title "<TodoTitle>" is existed in the system:
    When user requests to get a todo with id "<TodoId>"
    Then only the todo with id "<TodoId>" and related title "<TodoTitle>" should be returned

    Examples:
      | TodoTitle | TodoId |
      | scan paperwork     | 1      |
      | file paperwork     | 2      |

  Scenario Outline: The software engineer get all the instances of todos after a new todo has been added (Alternate Flow)
    Given  the todo list size is known and does not include the todo with the given title "<TodoTitle>"
    When user add a new todo with title "<TodoTitle>"
    And requests to get all the instances of todos
    Then the system should return instances of todos in the updated todo list contains a new one with the given title "<TodoTitle>" and the size of the todo list will be incremented.

    Examples:
      | TodoTitle  |
      | todo1added |
      | todo2added |

  Scenario Outline: The software engineer fail to get a todo due to wrong data type of todo id(Error Flow)
    Given  the todo list is not empty
    When user requests to get a todo with id "<TodoId>"
    Then Then the system should output an error code of no instance founded: "<code>"

    Examples:
      | TodoId |
      | adb    |
