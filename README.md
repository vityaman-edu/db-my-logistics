# My Logistics

ITMO Databases Course Work

## Build & Run

```bash
# Build the backend app docker image
sbt "Docker/stage"

# Start infra: database & backend
docker compose up --build --force-recreate
```
