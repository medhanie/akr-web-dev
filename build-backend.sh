cd backend/
mvn clean install
mvn sonar:sonar \
  -Dsonar.projectKey=akrwebdev \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=f89c4c9f9623a22851a3b3e39f795dc0d644375b
