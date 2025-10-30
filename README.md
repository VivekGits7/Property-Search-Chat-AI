# 🧠 NoBroDiractAI-BackEnd

## 🚀 Overview  
**NoBroDiractAI-BackEnd** is the backend service powering the *NoBro Direct AI* project — an intelligent real-estate assistant that helps generate property summaries, insights, and search results using AI.  

Built with **Java Spring Boot**, it provides REST APIs to connect with the frontend (React + Vite app) and handle AI-powered data processing.

---

## ✨ Features  
- RESTful API endpoints for property-related queries.  
- AI-driven description and summary generation.  
- JSON-based communication with frontend.  
- Easy integration with React frontend (POST: `/api/search`).  
- Spring Boot architecture for scalability and maintainability.  

---

## 🧩 Tech Stack  
- **Language:** Java 17+  
- **Framework:** Spring Boot  
- **Build Tool:** Maven  
- **Database:** (Add if applicable – e.g., MySQL / PostgreSQL)  
- **AI Integration:** OpenAI / Custom AI model (as used)  
- **API Testing:** Postman / cURL  

---

## ⚙️ Getting Started  

### 1️⃣ Prerequisites  
- Java 17 or newer  
- Maven 3.8+  
- IDE like IntelliJ IDEA or VS Code  
- Internet connection (for AI API access)  

### 2️⃣ Clone the Repository  

git clone https://github.com/VivekGits7/NoBroDiractAI-BackEnd.git
cd NoBroDiractAI-BackEnd
3️⃣ Configure Environment / Properties
In src/main/resources/application.properties, update:

properties
Copy code
server.port=8083

# Database (optional)
spring.datasource.url=jdbc:mysql://localhost:3306/nobrodiractai
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD

# AI Key (if applicable)
ai.api.key=YOUR_API_KEY
4️⃣ Build and Run
bash
Copy code
mvn clean install
mvn spring-boot:run
Your backend will now start on:
👉 http://localhost:8083

📡 API Overview
Method	Endpoint	Description
POST	/api/search	Takes a property query and returns AI-generated results
GET	/api/health	Health check endpoint

Example Request:
json
Copy code
POST http://localhost:8083/api/search
Content-Type: application/json

{
  "query": "Ashwini is a 1BHK residential apartment located in Mumbai Chembur..."
}
Example Response:
json
Copy code
{
  "summary": "Ashwini is a 1BHK apartment in Chembur, Mumbai near Babys School. Ideal for small families..."
}
🧪 Testing
Run the unit tests:

bash
Copy code
mvn test
You can also test endpoints using Postman or curl.

🌍 Deployment
You can deploy this Spring Boot backend on:

Render / Railway (Free Hosting)

Vercel (Frontend Only)

AWS / Heroku / DigitalOcean

Example Dockerfile:

dockerfile
Copy code
FROM openjdk:17-jdk-alpine
COPY target/nobrodiractai-backend.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
👨‍💻 Developer Info
Author: Vivek Vishwakarma
GitHub: VivekGits7

📜 License
This project is licensed under the MIT License.
See the LICENSE file for details.
