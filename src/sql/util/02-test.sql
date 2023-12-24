-- SELECT storage.name, item_kind.name, amount
-- FROM storage
-- JOIN storage_income ON storage.id = storage_income.storage_id
-- JOIN item_kind ON storage_income.item_kind_id = item_kind.id
-- ORDER BY storage.name;

-- SELECT storage.name, item_kind.name, amount
-- FROM storage
-- JOIN storage_outcome ON storage.id = storage_outcome.storage_id
-- JOIN item_kind ON storage_outcome.item_kind_id = item_kind.id
-- ORDER BY storage.name;

SELECT storage.name, item_kind.name, amount
FROM storage
JOIN storage_balance ON storage.id = storage_balance.storage_id
JOIN item_kind ON storage_balance.item_kind_id = item_kind.id
ORDER BY storage.name;

-- SELECT storage.name, item_kind.name, capacity
-- FROM storage
-- JOIN storage_capacity ON storage.id = storage_capacity.storage_id
-- JOIN item_kind ON storage_capacity.item_kind_id = item_kind.id
-- ORDER BY storage.name;

SELECT storage.name, item_kind.name, capacity
FROM storage
JOIN storage_free_capacity ON storage.id = storage_free_capacity.storage_id
JOIN item_kind ON storage_free_capacity.item_kind_id = item_kind.id
ORDER BY storage.name;
