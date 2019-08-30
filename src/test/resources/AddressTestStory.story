

Scenario: create new address
Given user wants to add new address
When the name for street is <streetName>
Then address is successfully created

Examples:
|path           |streetName|
|/api/v1/address|Mirijevac |
