Feature: My

  @Dashboard
  Scenario: My - for test #TC001
    Given Given Scenario
      | Excel         | Location                                           | Sheet    |
      | ddt_data.xlsx | src/main/java/resources/externalData/ddt_data.xlsx | SheetName |
    When When Scenario
    And And Scenario
    Then Then Scenario
      | Excel         | Location                                           | Sheet    |
      | ddt_data.xlsx | src/main/java/resources/externalData/ddt_data.xlsx | SheetName |
