Feature: Title Display Page functionality

Displays title page and relevant contents

Scenario: Display cast and crew

Given I load the limitation game title page display
When I click the cast and crew link
Then relevant details should appear

Scenario: Reviews links

Given I load the limitation game title page display
When I click user reviews link
Then relevant details should appear
And ratings and helpfulness dropdowns should available

Scenario: Drill down on More details

Given I load the limitation game title page display
When I click on More dropdown
Then More section should expand
And More tab should be appear as Less
And verify Details section appears
And verify StoryLine section appears
And verify Opinion section appears
And verify Related items section appears
And verify Photo and Video section appears

Scenario: Click on Less details

Given More section has been expanded
And Less section is displayed
When I click on Less button
Then Less Section will collapse
And should now be displayed as More
And no sub details should be available

Scenario: Play Trailer

Given I load the limitation game title page display
When I click on trailer
Then a new window opens 
And Trailer auotmatically plays





