# My Logistics

ITMO Databases Course Work

## Build & Run

### Database

```bash
docker compose up
docker exec -it db-my-logistics-db-1 
bash my-logistics/ci/db/test.sh postgres postgres
docker compose rm db
```
