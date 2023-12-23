set -o errexit
cd $(dirname -- "$0")
cd ../..

print() {
    YC="\033[1;34m" # Yes Color
    NC="\033[0m"    # No Color
    printf "$YC[test] $1$NC\n"
}

DATABASE_NAME=$1
DATABASE_USER=$2

run() {
    print "Running SQL script: $1..."
    psql \
        -h localhost -p 5432 \
        -d $DATABASE_NAME \
        -U $DATABASE_USER \
        -a -f src/sql/$1
}

print "Creating the database..."

run "util/00-drop.sql" | tail -5
run "schema/00-domain.sql"
run "schema/01-storage.sql"
run "schema/02-role.sql"
run "schema/03-order.sql"
run "schema/04-transfer.sql"
run "logic/storage/location_create.sql"
run "logic/storage/storage_create.sql"
run "logic/storage/item_kind_create.sql"
run "logic/storage/cell_create.sql"
run "logic/role/manager_assign.sql"
run "logic/role/admin_assign.sql"
run "logic/role/user_create.sql"
run "logic/role/consumer_create.sql"
run "logic/role/transporter_create.sql"
run "logic/order/order_create.sql"
run "logic/order/item_group_create.sql"
run "logic/transfer/transfer_request_create.sql"
run "logic/transfer/transfer_group_create.sql"
run "util/01-fill.sql"

print "Database created!"
