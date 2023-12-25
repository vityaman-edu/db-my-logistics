CREATE FUNCTION transfer_approve (
  transfer_id integer, 
  user_id     integer
) RETURNS void AS $$
DECLARE
  source_admin_id    integer;
  source_id          integer;
  target_admin_id    integer;
  target_id          integer;
BEGIN
  SELECT transfer.source_id, transfer.target_id
  INTO source_id, target_id
  FROM transfer WHERE transfer.id = transfer_id;

  SELECT id INTO source_admin_id
  FROM admin
  WHERE admin.storage_id = source_id
    AND admin.user_id = transfer_approve.user_id;

  SELECT id INTO target_admin_id
  FROM admin
  WHERE admin.storage_id = target_id
    AND admin.user_id = transfer_approve.user_id;

  IF source_admin_id IS NOT NULL THEN
    UPDATE transfer 
    SET source_approver_id = source_admin_id
    WHERE transfer.id = transfer_id;
  END IF;

  IF target_admin_id IS NOT NULL THEN
    UPDATE transfer 
    SET target_approver_id = target_admin_id
    WHERE transfer.id = transfer_id;
  END IF;

  IF source_admin_id IS NULL AND target_admin_id IS NULL THEN
    RAISE EXCEPTION 
      'can''t approve transfer with id % '
      'as a lack of rights of user with id %'
    , transfer_id, user_id;
  END IF;

  RETURN;
END;
$$ LANGUAGE plpgsql;