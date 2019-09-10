Meta:

Scenario: create new image
When creating new image from API
Then image appears in DB

Scenario: edit image
When edit image from API
Then image is edited in DB

Scenario: delete image
When delete image from API
Then image is deleted in DB

