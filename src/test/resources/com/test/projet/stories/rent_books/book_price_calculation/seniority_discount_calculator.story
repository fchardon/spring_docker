
!-- GivenStories:com/test/projet/stories/rent_books/rules/seniorities_rules.story

Scenario: Defined seniorities rules

Given the member has <year> of tenure, the rate applied is <percent>%.


Examples:
|year|percent|
|0   |0       |
|1   |1      |
|2   |3      |
|5   |5      |
|10  |7      |

Scenario: Price calculation for 1 year of tenure

Given we are the 2016/10/01.
And the member 123 exists.
And the customer is member since 2015/01/01.

When the member rents a book at 10 €.

Then the member pays 9.9 €.


Scenario: Price calculation for 0 years

Given we are the 2016/10/01.
And the member 123 exists.
And the customer is member since 2016/10/01.

When the member rents a book at 10 €.

Then the member pays 10 €.