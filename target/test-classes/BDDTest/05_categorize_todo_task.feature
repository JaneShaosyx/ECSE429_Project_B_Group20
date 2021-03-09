@tagStory
Feature:
  As a software engineer, I want to categorize my todo tasks, so that I can do my tasks in order.

  Background:
    Given the service is running
    And there are these categories in the system:
      | title  | description                                                                          |
      | High   | High priority: need to be done ASAP                                                  |
      | Medium | Medium priority: can be done when all the High priority tasks have already been done |
      | Low    | Low priority: can be done later                                                      |

  Scenario Outline: The software engineer set category of a task to a category (Normal Flow)
    Given the todo task with name "<todoTitle>" registered in the system
    And the category name with "<todoCategories>" is in the system
    When user requests to categorize a task with "<todoTitle>" as "<todoCategories>"
    Then the todo task with name "<todoTitle>" should be in category "<todoCategories>"

    Examples:
      | todoTitle | todoCategories |
      | curTodo   | HIGH           |
      | curTodo2  | MEDIUM         |

  Scenario Outline: The software engineer change the category for the specific todo task (Alternate Flow)
    Given the todo task with name "<todoTitle>" registered in the system
    And the todo task with name "<todoTitle>" has a "<originalCategory>" category
    And the category with title "<updatedCategory>" registered in the system
    When user tries to remove "<originalCategory>" category categorization from "<todoTitle>"
    And user requests to categorize a todo task with "<todoTitle>" with a new category "<updatedCategory>"
    Then the todo task "<todoTitle>" should be a task with "<updatedCategory>" category

    Examples:
      | todoTitle | originalCategory | updatedCategory |
      | curTodo   | HIGH             | Low             |
      | curTodo2  | MEDIUM           | Low             |

  Scenario Outline: The software engineer add the category for a non-existent todo task(Error Flow)
    Given the todo task with name "<todoTitle>" is not in the system
    When user requests to categorize the todo task with name "<todoTitle>" as "<todoCategories>"
    Then the system should output an error message of categotrizing a non-existent todo task: "<errorCode>"
    Examples:
      | todoTitle         | todoCategories | errorCode |
      | wrong_todo_task_1 | HIGH           | 404       |
      | wrong_todo_task_2 | HIGH           | 404       |