curl -X POST http://localhost:8080/sync -H "Content-Type: application/json" -d '{"url": "https://example.com"}'

curl -X POST http://localhost:8080/async -H "Content-Type: application/json" -d '{"url": "https://example.com"}'

curl -X POST http://localhost:8080/writecache -H "Content-Type: application/json" -d '{"url": "https://example.com"}'

curl -X POST http://localhost:8080/periodic -H "Content-Type: application/json" -d '{"url": "https://example.com"}'

curl -X GET http://localhost:8080/periodic/FMh03yBF

