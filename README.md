# 🏥 Clínic Backend — Spring Boot + AWS + Docker + Testing
![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Docker](https://img.shields.io/badge/Docker-ready-blue)
![AWS](https://img.shields.io/badge/AWS-EC2%20%7C%20RDS-orange)
![Tests](https://img.shields.io/badge/tests-passing-brightgreen)


## 📌 Description
This project is the **backend of the clinical management system**, developed with **Spring Boot** and **MySQL**.

The system allows for the management of patients, doctors, appointments, and authentication using **JWT**.

It includes configuration for easy execution using **Docker Compose**, without the need to install MySQL locally.

🔗 This backend connects with the frontend built in Angular:  
👉 [clinic-management-frontend](https://github.com/Lenin-LG/clinic-management-frontend.git)


---

## ⚙️ Prerequisites

Before running the project, make sure you have the following installed:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- Git

---

## 📁 Project structure

```
.
├── Dockerfile
├── docker-compose.yml
├── .env
├── src/
│   ├── main/
│   └── test/
└── README.md
```
---
## 🧠 Main technologies

| Category | Technology                      |
|------------|---------------------------------|
| Backend Framework | Spring Boot 3                   |
| Base de Datos | MySQL 8                         |
| Seguridad | Spring Security + JWT           |
| Contenedores | Docker, Docker Compose          |
| Testing | JUnit 5, Mockito,TestConteiners |
| Documentación API | Swagger (Springdoc OpenAPI)     |
| Despliegue | AWS EC2, AWS RDS                |
---
## 🔬 Testing

The project includes unit and integration testing using:
- **JUnit 5**
- **Mockito**
- **Spring Boot Test**

To run the tests:

```bash
./mvnw test
```
---
## 📦 Packaging the project

To compile and generate the ready-to-run .jar file:
```bash
./mvnw clean package
```
If you want to package without running the tests (faster):
```bash
./mvnw clean package -DskipTests
```
The resulting file will be generated in:
```
target/clinica-0.0.1-SNAPSHOT.jar
```

---

## 🧩 `.env` file

Create a `.env` file in the project root directory with the following content:

```env
MYSQL_USER=root
MYSQL_PASSWORD=12345678
SPRING_DATASOURCE_URL=jdbc:mysql://mysql-clinica:3306?createDatabaseIfNotExist=true&serverTimezone=America/Lima&allowPublicKeyRetrieval=true&useSSL=false
JWT_SECRET=TDSZydxbpLTzBiSyAdfK6qzd8nBt9WeOFBO-Pi7NO5X1IWgLA594XmYEj99lEK_ZEyKKs2dkmIe8g1dFBYuQJg
```

---

## 🐳 Running with Docker Compose

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Lenin-LG/clinic-management-backend.git
   cd clinic-management-backend
   ```

2. **Build and lift the containers(You need to have packaged it by generating the .jar file.):**
   ```bash
   docker-compose up --build -d
   ```

3. **Verify that the services are running:**
   ```bash
   docker ps
   ```

   You should see:
   ```
   CONTAINER ID   IMAGE               PORTS
   xxxxxxx        mysql:8.0           3307->3306/tcp
   yyyyyyy        clinica             8080->8080/tcp
   ```

4. **Access the backend:**
   ```
   http://localhost:8080
   ```
5. **Accede Swagger:**
   ```
   http://localhost:8080/swagger-ui.html
   ```

---

## 🗂️ Database

- Internal host: `mysql-clinica`
- Port: `3306`
- User: `root`
- Password: `12345678`
- Database: `clinica_db`

To access manually:
```bash
docker exec -it mysql-clinica mysql -uroot -p12345678
```

---

## 🧹 Useful commands

- **See logs:**
  ```bash
  docker-compose logs -f clinica
  ```
- **Restart containers:**
  ```bash
  docker-compose restart
  ```
- **Stop and delete containers:**
  ```bash
  docker-compose down
  ```

---

## 📖 Final Notes
- The project originally connected to an RDS database on AWS, but this public release includes an internal MySQL container.

- JWT is configured using the `JWT_SECRET` variable in the `.env` file.

- To deploy to AWS EC2, simply clone the repository and run the same commands.
---
## 🚀 Upcoming improvements

- Implement CI/CD with GitHub Actions
- AWS S3 integration for uploading medical files
- Email notification system (Spring Mail)
---
## 🧑‍💼 Author

**Lenin Laura García**  
Backend Developer — Spring Boot, AWS, Docker  
📧 leninlauragarcia1704@gmail.com  
