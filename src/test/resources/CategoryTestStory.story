
Meta:
@categories category1

Scenario: Create new category
When create new category
Then category appears in database

Scenario: Edit category
When edit category over API
Then category is edited in DB

Scenario: Delete category
When delete category
Then category isn't in database