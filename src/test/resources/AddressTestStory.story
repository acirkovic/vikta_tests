
Scenario: create new address
Given the path is <path>
When the name for street is <streetName>
Then addresss is changed to <streetNameAssert>

Examples:
| path | streetName | streetNameAssert |
| /api/v1/address | Mirijevac | Mirijevac |


Scenario: edit new address
Given the path is <path>
When the name for street is <streetName>
Then addresss is changed to <streetNameAssert>

Examples:
| path | streetName | streetNameAssert |
| /api/v1/address | Mirijevac | Mirijevac |