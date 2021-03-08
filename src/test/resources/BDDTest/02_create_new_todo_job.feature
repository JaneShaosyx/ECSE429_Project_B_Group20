Feature:
  As a software engineer, I want to create a new todo task, so that I can record which tasks I need to do.

  Background:
    Given the service is running

  Scenario Outline: The software engineer creates a new todo task named curTodo (Normal Flow)
    Given  the todo name with "<todoTitle>" and description "<todoDescription>" is not in the system:
    When user requests to create a todo with title "<todoTitle>" and description "<todoDescription>"
    Then the todo task with title "<todoTitle>" and description "<todoDescription>" should be created:

    Examples:
      | todoTitle | todoDescription                         |
      | curTodo   | My current todo task                    |
      | curTodo2  | Another task which I am doing right now |

  Scenario Outline: The software engineer change the done status of a existing todo task (Alternate Flow)
    Given  the todo task name with "<todoTitle>" ,  done status "<oldDoneStatus>" is registered in the system:
    When user requests to change the  done status of the todo task with title "<todoTitle>"  to "<newDoneStatus>"
    Then the  done status of the todo task with title "<todoTitle>" will be set to "<newDoneStatus>"

    Examples:
      | todoTitle   | oldDoneStatus | newDoneStatus |
      | curProject  | false         | true          |
      | curProject2 | true          | false         |

  Scenario Outline: The software engineer fail to create a new todo task due to wrong type of done status(Error Flow)
    Given  the todo name with "<todoTitle>" and done status "<doneStatus>" is not in the system:
    When user requests to create a todo with title "<todoTitle>" and done status "<doneStatus>"
    Then the system should output an error code of wrong data type of doneStatus: "<errorCode>"

    Examples:
      | todoTitle   | doneStatus | errorCode |
      | curProject  | not done   | 400       |
      | curProject2 | done       | 400       |