Feature:
  As a software engineer, I want to create different categories for the task, so that I categorize my todo tasks.

  Background:
    Given the service is running

  Scenario Outline: The software engineer add a new category to the categories list (Normal Flow)
    Given  the category name with "<categoryTitle>" is not in the system
    When user requests to create a category with title "<categoryTitle>" and description of "<categoryDescription>"
    Then the category with title "<categoryTitle>" and description "<categoryDescription>" should be created

    Examples:
      | categoryTitle | categoryDescription                                                                  |
      | High          | High priority: need to be done ASAP                                                  |
      | Medium        | Medium priority: can be done when all the High priority tasks have already been done |
      | Low           | Low priority: can be done later                                                      |

  Scenario Outline: The software engineer add a new category to the categories list without description(Alternate Flow)
    Given  the category name with "<categoryTitle>" is not in the system
    When user requests to create a category with title "<categoryTitle>"
    Then the category with title "<categoryTitle>" should be created

    Examples:
      | categoryTitle |
      | High          |
      | Medium        |
      | Low           |

  Scenario Outline: The software engineer fail to create a new category due to absence of title (Error Flow)
    Given  the category name description of "<categoryDescription>" is not in the system
    When user requests to create a category with description "<categoryDescription>"
    Then the system should output an error code of create category without title: "<errorCode>"

    Examples:
      | categoryDescription                                                                  | errorCode |
      | High priority: need to be done ASAP                                                  | 400       |
      | Medium priority: can be done when all the High priority tasks have already been done | 400       |
      | Low priority: can be done later                                                      | 400       |
