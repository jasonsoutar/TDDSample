Feature: Says hullo

  Scenario: Hello endpoint says hello
    When I call the hello end point
    Then 'Hello World' is returned