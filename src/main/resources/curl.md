curl http://localhost:8080/reservations/tenants?name=Dolorita%20Mattacks

curl http://localhost:8080/reservations/rental-place?id=2

curl.exe -H "Content-Type: application/json" -d '{\"startOfRental\": \"2022-07-12\",\"endOfRental\": \"2022-07-14\",\"landlordId\": \"1\",\"tenantId\": \"6\",\"rentalPlaceId\": \"2\",\"cost\": \"990.90\"}' http://localhost:8080/reservations/add

curl.exe -X PUT -H "Content-Type: application/json" -d '{\"startOfRental\": \"2022-08-15\",\"endOfRental\": \"2022-09-14\",\"landlordId\": \"5\",\"tenantId\": \"9\",\"rentalPlaceId\": \"5\",\"cost\": \"990.90\"}' http://localhost:8080/reservations/update/1
