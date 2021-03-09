@tagToIdentifyThatBeginAfterShouldRunForThisFeatureOnly
Feature:
  As a software engineer, I want to get categories so that I can check the categories of my jobs and better manage them.

  Background:
    Given my service is running

  Scenario Outline: The software engineer get a category with id (Normal Flow)

    Given  the category with id "<CategorytId>" and title "<CategoryTitle>" is existed in the system:
    When user requests to get a category with id "<CategorytId>"
    Then only the category with id "<CategorytId>" and title "<CategoryTitle>" should be returned

    Examples:
      | CategorytId | CategoryTitle|
      | 1         | Office   |
      | 2         | Home   |

  Scenario Outline: The software engineer get all the instances of categories (Alternate Flow)
    Given  the category list size is known and does not include the category with the given title "<CategoryTitle>"
    When user add a new category with title "<CategoryTitle>"
    And requests to get all the instances of categories
    Then the system should return instances of categories in the updated category list contains a new one with the given title "<CategoryTitle>" and the size of the category list will be incremented.
    Examples:
      | CategorytId | CategoryTitle|
      | 1         | cate 1   |
      | 2         | cate 2   |

  Scenario Outline: The software engineer fail to get a category due to wrong data type of category id(Error Flow)
    Given  the category list is not empty
    When user requests to get a category with id "<CategorytId>"
    Then Then the system should output an error code of no category instance founded: "<code>"

    Examples:
      | CategorytId |code|
      | adb        |404   |
