@tagStory
Feature:
  As a software engineer, I want to add a task to my current project, so that I can manage the todo tasks I need to do.

  Background:
    Given the service is running

  Scenario Outline: The software engineer add a existing task to my project (Normal Flow)
    Given  the project name with "<projectTitle>" todo name with "<todoTitle>" is in the system:
    When user requests to add a todo task with title "<todoTitle>" to the project with title "<projectTitle>"
    Then the a todo task with title "<todoTitle>" will be add to the tasks list of project with title "<projectTitle>"

    Examples:
      | projectTitle | todoTitle |
      | curProject   | curTodo   |
      | curProject2  | curTodo2  |

  Scenario Outline: The software engineer create a new todo task and add it to my project (Alternate Flow)
    Given  the project name with "<projectTitle>" is in the system
    And the todo task name with "<todoTitle>" is not in the system:
    When user requests to create a todo with title "<todoTitle>" and description "<todoDescription>"
    And user requests to add a todo task with title "<todoTitle>" to the project with title "<projectTitle>"
    Then the a todo task with title "<todoTitle>" will be add to the tasks list of project with title "<projectTitle>"

    Examples:
      | projectTitle | todoTitle | todoDescription                         |
      | curProject   | curTodo   | My current todo task                    |
      | curProject2  | curTodo2  | Another task which I am doing right now |

  Scenario Outline: The software engineer fail to add a todo task to a project because the project is non-existent (Error Flow)
    Given  the project name with "<projectTitle>" is not in the system
    And  the todo name with "<todoTitle>" and description "<todoDescription>" is in the system
    When user requests to add a todo task with title "<todoTitle>" and description "<todoDescription>" to the project with title "<projectTitle>"
    Then the system should output an error code of add todo task with non-existent project id: "<errorCode>"

    Examples:
      | todoTitle | todoDescription                         | projectTitle | errorCode |
      | curTodo   | My current todo task                    | curProject   | 404       |
      | curTodo2  | Another task which I am doing right now | curProject2  | 404       |
