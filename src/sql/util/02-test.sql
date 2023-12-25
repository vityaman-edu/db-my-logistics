SELECT storage.name, item_kind.name, amount
FROM storage
JOIN storage_balance('2023-01-08 00:00'::timestamp) 
  ON storage.id = storage_balance.storage_id
JOIN item_kind ON storage_balance.item_kind_id = item_kind.id
ORDER BY storage.name, item_kind.name;

SELECT storage.name, item_kind.name, capacity
FROM storage
JOIN storage_capacity_free('2023-01-08 00:00'::timestamp) 
  ON storage.id = storage_capacity_free.storage_id
JOIN item_kind ON storage_capacity_free.item_kind_id = item_kind.id
ORDER BY storage.name, item_kind.name;
