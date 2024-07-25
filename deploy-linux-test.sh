cp docker-compose-test.txt docker-compose-test.yaml
docker-compose -f docker-compose-test.yaml --project-name inventorytest up --build -d