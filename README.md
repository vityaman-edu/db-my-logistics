# My Logistics

ITMO Databases Course Work

## Build & Run

```bash
# Build the backend app docker image
sbt "Docker/stage"

# Start infra: database & backend
docker compose up

# Initialize database
docker exec -it my-logistics-database bash
bash my-logistics/ci/db/test.sh localhost postgres postgres
```
