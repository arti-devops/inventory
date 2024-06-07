Rename-Item -Path "compose-*.yaml" -NewName "docker-compose.txt"
Rename-Item -Path "docker-compose.txt" -NewName "docker-compose.yaml"
docker-compose up --build -d