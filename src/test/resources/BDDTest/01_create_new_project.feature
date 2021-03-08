Feature:
  As a software engineer, I want to create a new project model for my current project, so that I can manage the work I need to do.

  Background:
    Given the service is running

  Scenario Outline: The software engineer creates a new project named curProject (Normal Flow)
    Given  the project name with "<projectTitle>" is not in the system:
    When user requests to create a project with title "<projectTitle>" and description "<projectDescription>"
    Then the project with title "<projectTitle>" and description "<projectDescription>" should be created:

    Examples:
      | projectTitle | projectDescription                     |
      | curProject   | My current project                     |
      | curProject2  | Another job which I am doing right now |

  Scenario Outline: The software engineer change the completed status of a existing project (Alternate Flow)
    Given  the project name with "<projectTitle>" , completed status "<oldCompleteStatus>" is registered in the system:
    When user requests to change the complete status of the project with title "<projectTitle>"  to "<newCompleteStatus>"
    Then the complete status of the project with title "<projectTitle>" will be set to "<newCompleteStatus>"

    Examples:
      | projectTitle | oldCompleteStatus | newCompleteStatus |
      | curProject   | false             | true              |
      | curProject2  | true              | false             |

  Scenario Outline: The software engineer fail to create a new project due to wrong data type of active status(Error Flow)
    Given  the project name with "<projectTitle>" is not in the system:
    When user requests to create a project with title "<projectTitle>" and active status "<activeStatus>"
    Then the system should output an error code of wrong data type of active status: "<code>"

    Examples:
      | projectTitle | activeStatus | code |
      | curProject   | not active   | 400  |
      | curProject2  | active       | 400  |
