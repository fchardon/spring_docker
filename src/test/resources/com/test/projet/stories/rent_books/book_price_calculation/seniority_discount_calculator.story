In order to make the most appropriate purchase decisions
As a buyer
I want to be able to place items I want to buy in a virtual cart before placing my order

Scenario: Price calculation for 1 year of tenure

Given we are the 2016/10/01.
And the member 123 exists.
And the customer is member since 2015/01/01.

When the member rents a book at 10 €.

Then the member pays 9.9 €.

Scenario: Price calculation for 0 year

Given we are the 2016/10/01.
And the member 123 exists.
And the customer is member since 2016/10/01.

When the member rents a book at 10 €.

Then the member pays 10 €.