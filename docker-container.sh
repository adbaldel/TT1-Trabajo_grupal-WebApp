mvn -DskipTests clean package
docker image build -t tt1/simwebapp .
docker run -d \
  --name simwebapp \
  -e API_URL=http://localhost:8081
  -p 8080:8080 \
  tt1/simwebapp