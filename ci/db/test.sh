set -o errexit
cd $(dirname -- "$0")
cd ..

DATABASE_NAME=$1
DATABASE_USER=$2

run() {
    psql \
        -h localhost -p 5432 \
        -d $DATABASE_NAME \
        -U $DATABASE_USER \
        -a -f src/$1
}

echo "Hello, World!"
