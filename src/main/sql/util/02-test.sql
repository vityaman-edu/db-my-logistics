SELECT withdraw, income, source, target, item, amount
FROM ((
    SELECT 
      transfer.withdraw_moment                     AS withdraw, 
      transfer.withdraw_moment + transfer.duration AS income, 
      source.name                                  AS source,
      target.name                                  AS target,
      item_kind.name                               AS item, 
      amount                                       AS amount
    FROM transfer
    JOIN storage AS source ON source.id = transfer.source_id
    JOIN storage AS target ON target.id = transfer.target_id
    JOIN transfer_atom ON transfer_atom.transfer_id = transfer.id
    JOIN item_kind ON transfer_atom.item_kind_id = item_kind.id
  ) UNION ALL (
    SELECT 
      NULL  AS withdraw,
      supply.moment  AS income,
      NULL           AS source,
      target.name    AS target,
      item_kind.name AS item, 
      amount         AS amount
    FROM supply
    JOIN storage AS target ON target.id = supply.target_id
    JOIN supply_atom ON supply_atom.supply_id = supply.id
    JOIN item_kind ON supply_atom.item_kind_id = item_kind.id
  ))
ORDER BY withdraw NULLS FIRST, income;

CREATE FUNCTION max_moment() RETURNS timestamp AS $$
  SELECT MAX(moment) 
  FROM (
    SELECT MAX(transfer.withdraw_moment + transfer.duration) AS moment FROM transfer
  ) UNION ALL (
    SELECT MAX(supply.moment) AS moment FROM supply
  )
$$ LANGUAGE SQL;

SELECT storage.name, item_kind.name, amount
FROM storage
JOIN storage_balance(max_moment()) 
  ON storage.id = storage_balance.storage_id
JOIN item_kind ON storage_balance.item_kind_id = item_kind.id
ORDER BY storage.name, item_kind.name;

SELECT storage.name, item_kind.name, capacity
FROM storage
JOIN storage_capacity_free(max_moment()) 
  ON storage.id = storage_capacity_free.storage_id
JOIN item_kind ON storage_capacity_free.item_kind_id = item_kind.id
ORDER BY storage.name, item_kind.name;