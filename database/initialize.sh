set -o errexit
cd $(dirname -- "$0")

print() {
  YC="\033[1;34m" # Yes Color
  NC="\033[0m"    # No Color
  printf "$YC[test] $1$NC\n"
}

run() {
  print "Running SQL script: $1..."
  psql \
    -h localhost \
    -p 5432 \
    -d $POSTGRES_DB \
    -U $POSTGRES_USER \
    -a -f sql/$1
}

print "Creating the database..."

run "util/00-drop.sql" | tail -5

run "schema/00-domain.sql"
run "schema/01-storage.sql"
run "schema/02-role.sql"
run "schema/03-transfer.sql"
run "schema/04-supply.sql"
run "schema/05-consume.sql"

run "logic/item/item_kind_create.sql"

run "logic/role/manager_assign.sql"
run "logic/role/admin_assign.sql"
run "logic/role/user_create.sql"

run "logic/storage/location_create.sql"
run "logic/storage/storage_transaction.sql"
run "logic/storage/storage_balance.sql"
run "logic/storage/storage_transaction_integrity.sql"
run "logic/storage/storage_capacity.sql"
run "logic/storage/storage_cell_create.sql"
run "logic/storage/storage_create.sql"

run "logic/transfer/transfer_approve.sql"
run "logic/transfer/transfer_create.sql"
run "logic/transfer/transfer_integrity.sql"
run "logic/transfer/transfer_atom_create.sql"

run "logic/supply/supply_create.sql"
run "logic/supply/supply_atom_create.sql"
run "logic/supply/supply_integrity.sql"

run "logic/consume/consume_create.sql"
run "logic/consume/consume_atom_create.sql"
run "logic/consume/consume_integrity.sql"

run "util/01-fill.sql"

run "util/02-test.sql"

run "util/03-high-load.sql"

print "Database created!"
