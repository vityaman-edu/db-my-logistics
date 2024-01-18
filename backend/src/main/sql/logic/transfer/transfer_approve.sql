CREATE FUNCTION transfer_reset_votes (
  transfer_id integer
) RETURNS void AS $$
  UPDATE transfer
  SET target_approver_id = NULL,
      source_approver_id = NULL
  WHERE transfer.id = transfer_id;
$$ LANGUAGE SQL;

CREATE FUNCTION transfer_validate (
  transfer transfer
) RETURNS void AS $$
DECLARE
  atom RECORD;
  withdraw_moment timestamp;
  income_moment   timestamp;
BEGIN
  withdraw_moment := transfer.withdraw_moment;
  income_moment   := transfer.income_moment;
  
  FOR atom IN
    SELECT *
    FROM transfer_atom 
    WHERE transfer_atom.transfer_id = transfer.id
  LOOP
    PERFORM storage_transaction_validate(
      withdraw_moment, transfer.source_id, 
      atom.item_kind_id, -atom.amount
    );

    PERFORM storage_transaction_validate(
      income_moment, transfer.target_id, 
      atom.item_kind_id, atom.amount
    );
  END LOOP;
END;
$$ LANGUAGE plpgsql;

CREATE FUNCTION transfer_approve (
  transfer_id integer, 
  user_id     integer
) RETURNS void AS $$
DECLARE
  source_admin_id integer;
  target_admin_id integer;
  transfr         transfer;
BEGIN
  SELECT * INTO transfr
  FROM transfer 
  WHERE transfer.id = transfer_id;

  SELECT id INTO source_admin_id
  FROM admin
  WHERE admin.storage_id = transfr.source_id
    AND admin.user_id = transfer_approve.user_id;

  SELECT id INTO target_admin_id
  FROM admin
  WHERE admin.storage_id = transfr.target_id
    AND admin.user_id = transfer_approve.user_id;

  IF source_admin_id IS NULL AND target_admin_id IS NULL THEN
    RAISE EXCEPTION 
      'can not approve transfer with id % '
      'as a lack of rights of user with id %'
    , transfr.id, user_id;
  END IF;

  PERFORM transfer_validate(transfr);

  IF source_admin_id IS NOT NULL THEN
    UPDATE transfer 
    SET source_approver_id = source_admin_id
    WHERE transfer.id = transfr.id;
  END IF;

  IF target_admin_id IS NOT NULL THEN
    UPDATE transfer 
    SET target_approver_id = target_admin_id
    WHERE transfer.id = transfr.id;
  END IF;

  RETURN;
END;
$$ LANGUAGE plpgsql;