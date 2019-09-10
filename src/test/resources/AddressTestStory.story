

Scenario: create new address
When the name for street is <streetName>
Then address is successfully created

Examples:
|streetName    |
|Mihaila Pupina|

Scenario:
When edit address via API and name is <streetName>
Then changes are saved in DB
Examples:
|streetName    |
|Mihaila Pupina|


Scenario:
When delete through API and street name is <streetName>
Then street is deleted, and no entry is in database

Examples:
|streetName    |
|Mihaila Pupina|

