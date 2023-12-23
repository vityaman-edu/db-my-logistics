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

run "util/00-drop.sql"
run "schema/00-core.sql"
run "schema/01-user.sql"

print "Database created!"
