

Scenario: create new address
Given user wants to add new address
When the name for street is <streetName>
Then status code is 200
Then address is successfully created

Examples:
|streetName    |
|Mihaila Pupina|


Scenario: create new address, but wrong user id
Given user wants to add new address
When the name for street is <streetName>
Then address is successfully created

Examples:
|streetName    |
|Mihaila Pupina|



Scenario:
Given user wants to delete address
When street name is <streetName>
Then street is successfully deleted

Examples:
|streetName    |
|Mihaila Pupina|