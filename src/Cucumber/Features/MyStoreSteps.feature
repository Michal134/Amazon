Feature: Shopping on Amazon.com

  Scenario: Amazon.com Shopping Process
    Given the user is on the Amazon.com homepage
    When the user clicks the "Sign in" button
    And the user enters the email "jamessmithtest664@gmail.com" and password "Portfolio123."
    And the user clicks the "Sign in" button
    Then the user is logged in

    Given the user is on the Amazon.com homepage
    When the user enters "Water" into the search bar
    And the user clicks the "Search" button
    And the user selects the first available product
    And the user clicks the "Add to Cart" button
    When the user clicks the "The Cart" icon button
    Then a screenshot of the order confirmation is saved
