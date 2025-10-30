> **<u>NoBroDirectAI – Property Finder Chat
> AI</u>**<img src="./b5ucvr2g.png" style="width:7.31417in" /><img src="./eixgksk0.png" style="width:7.31417in" />

**LinkedIn:**
[***<u>https://www.linkedin.com/in/vivek-vishwakarma-</u>***](https://www.linkedin.com/in/vivek-vishwakarma-)

**Project** **GitHub:**
[***<u>https://github.com/VivekGits7/Property-Search-Chat-AI</u>***](https://github.com/VivekGits7/Property-Search-Chat-AI)

**Specific** **Frontend** **code** **GitHub:**
[***<u>https://github.com/VivekGits7/NoBroDirectAI-FrontEnd</u>***](https://github.com/VivekGits7/NoBroDirectAI-FrontEnd)
**Specific** **Backend** **code** **GitHub:**
[***<u>https://github.com/VivekGits7/NoBroDirectAI-BackEnd</u>***](https://github.com/VivekGits7/NoBroDirectAI-BackEnd)

**<u>Project Summary:</u>**

This project aims to build an intelligent **Chat** **Search** **AI**
**system** that helps users find real estate projects through natural
language queries like “3BHK flat in Pune under ₹1.2 Cr.”

Instead of using filters, users can chat with the interface—built using
React + Tailwind CSS —to get instant, data-driven property
recommendations. The backend (powered by Java Spring Boot ) processes
user messages, extracts key filters such as city, BHK type, budget, and
locality, and searches through the provided CSV datasets containing
project, address, and configuration details.

It then generates a short, meaningful summary based solely on CSV data
and displays a list of matching project cards with details like price,
location, status, and BHK. This system combines natural language
understanding, structured data retrieval, and a user-friendly interface
to make property discovery faster, smarter, and more interactive.

**<u>Tech Stack</u>**

**Programming** **Languages:** Java17, JavaScript

**Core** **Concepts:** Data Structures & Algorithms, Object-Oriented
Programming (OOP), Problem Solving.

**Java** **Frameworks** **&** **Libraries:** Spring, Spring Boot,
Lombok, commons-csv

**Build** **&** **Tools:** Maven, Postman, GSON, JSON, and API
Documentation (Swagger) **Back-End** **Development:** RESTful APIs and
designing with Microservices Architecture. **Application** **Servers:**
Experience with Embedded Tomcat for Java applications.

**Front-End** **Frameworks:** Tailwind CSS, React.js.

**Web** **Technologies:** HTML5, CSS3, JavaScript and YAML. **Version**
**Control:** Git, GitHub

**IDEs:** IntelliJ IDEA, WebStorm, and Visual Studio Code for
development and debugging.

**<u>Setup Guide</u>**

**<u>Frontend Setup Guide</u>**

> <img src="./th1q43jb.png"
> style="width:0.52917in;height:0.4525in" />**1.** **Clone** **the**
> **Repo**

**Open** **a** **terminal** **in** **VS** **Code** **or** **WebStorm**
**and** **run:**

***git*** ***clone***
[***<u>https://github.com/VivekGits7/NoBroDirectAI-FrontEnd.git</u>***](https://github.com/VivekGits7/NoBroDirectAI-FrontEnd.git)

**Then** **go** **inside** **the** **project** **folder:** ***cd***
***NoBroDirectAI-FrontEnd***<img src="./44byaxto.png"
style="width:7.08681in;height:2.32708in" />

> <img src="./vbwz5cce.png"
> style="width:0.52917in;height:0.4525in" />**2.** **Install**
> **Dependencies**

<img src="./4kzgftwg.png"
style="width:0.52917in;height:0.4525in" />**Since** **node_modules**
**is** **not** **in** **your** **repo,** **install** **them** **fresh**
**using** **npm:** ***npm*** ***install***

**This** **reads** **the** **package.json** **file** **and**
**downloads** **all** **required** **libraries** **(React,** **Vite,**
**Tailwind,** **Framer** **Motion,** **etc.)** **into** **a** **new**
**node_modules** **folder.**

<img src="./n2f54j4v.png"
style="width:0.52917in;height:0.4525in" />**3.** **Run** **the**
**Development** **Server** **Start** **the** **app** **with:** ***npm***
***run*** ***dev***

**After** **a** **few** **seconds,** **you’ll** **see** **an**
**output** **like:** *VITE* *v5.0.0* *ready* *in* *400ms*

➜ *Local:* *http://localhost:5173/*

<img src="./lauoo4xp.png"
style="width:0.5275in;height:0.4525in" /><img src="./ssexeb0u.png" style="width:7.31417in" />**Open**
**that** **URL** **in** **your** **browser.**

<img src="./wqhkdcit.png"
style="width:0.52917in;height:0.4525in" />**<u>Backend Setup Guide</u>**
**1.** **Clone** **the** **Repo**

Open a terminal in **IntelliJ** **IDEA** or **Eclipse** **IDE** and run:

In the **IntelliJ** **IDEA** Project Section, click the Clone Repository
button and give the **Backend** **code** **repo** **URL:**
***https://github.com/VivekGits7/NoBroDirectAI-BackEnd***

<img src="./q0evbyv0.png"
style="width:0.52917in;height:0.4525in" />Then go inside the project
folder: ***cd*** ***NoBroDirectAI-BackEnd*** **2.** **Check** **Maven**
**Dependencies**

If not automatically downloaded, you can manually install them:
***mvn*** ***clean*** ***install***

This will:

> • Download all dependencies from pom.xml • Compile the source
>
> • Create the target folder with the .jar file

<img src="./rliinlmg.png"
style="width:0.52917in;height:0.4525in" />**3.** **Run** **the**
**Spring** **Boot** **Application** Run this command inside the project
root: ***mvn*** ***spring-boot:*** ***run***

> <img src="./3fybn1qg.png"
> style="width:0.5275in;height:0.4525in" />**Backend** **is**
> **running** **on:**
> [***<u>http://localhost:8083</u>***](http://localhost:8083/)

**Or** **Run** **the** **JAR** **File**

**Run** **your** **Spring** **Boot** **backend:**

***java*** ***-jar*** ***NoBroDirectAI-BackEnd.jar***

> <img src="./keqmfnvu.png"
> style="width:0.52917in;height:0.4525in" />**4.** **Verify** **API**
> **Endpoint**

Test your main API using Postman or Curl:

POST *http://localhost:8083/api/search* Content-Type: application/json

{

"query": "1BHK residential apartment located at Prataprao Gujar Rd,
Neelam Nagar, Mulund East, Mumbai, Maharashtra 400081, near JBCN
International School Mulund.

It is unfurnished and currently under construction, featuring 1 bathroom
and 1 balcony, priced at around ₹1.2 crore.

<img src="./zvgpi13a.png"
style="width:7.08681in;height:1.46319in" />" }

<img src="./0tbwt1g4.png"
style="width:0.52917in;height:0.4525in" />**5.** **Folder**
**Structure** **Overview** NoBroDiractAI-BackEnd/

Root contains: • src/

> o main/
>
> ▪ java/com/NoBrokerage/NoBroDirectAI/
>
> ▪ controller/ → REST controllers (API endpoints) ▪ service/ → Business
> logic
>
> ▪ utils/ → Helper classes
>
> ▪ dto/ → Data transfer objects ▪ resources/
>
> ▪ application.properties → Server port, DB config ▪ data/ → Optional
> CSV folder
>
> o test/ → Unit tests
>
> • pom.xml → Maven dependencies
>
> <img src="./zdgtibk3.png"
> style="width:0.52917in;height:0.4525in" />• README.md → Project
> info<img src="./gdrovbah.png"
> style="width:7.08681in;height:0.96528in" /><img src="./5bm5lrxb.png" style="width:7.31417in" />

**6.** **Change** **Port** **or** **Config** **(Optional)** If port 8083
is busy, change it in:

src/main/resources/application.properties server.port=8083

<img src="./slv3iesz.png"
style="width:0.52917in;height:0.4525in" />**7.** **Connect**
**Frontend** **+** **Backend** Once the backend is running:

> • Make sure your frontend’s .env file has: •
> VITE_API_URL=http://localhost:8083
>
> • Then start your frontend: • npm run dev
>
> • The React chat UI will call your backend endpoint at
> http://localhost:8083/api/search

**<u>API Endpoints Overview</u>**

**1.** **Search** **Projects**

> **URL:** POST localhost:8083/api/search
>
> **Description:** Accepts a search query and returns matching
> property/project listings from the CSV data store.
>
> **Request** **Body** **Example:**
>
> **Response** **Example:**

<img src="./lquekgri.png"
style="width:7.08681in;height:3.65208in" /><img src="./q21kxxxk.png" style="width:7.31417in" />

> **Notes:** The summary is derived **only** from the CSV datasets (no
> external data). The project's array lists matched items with key
> details. If no matches are found, it returns a message summarising the
> absence and may propose broader filters.

**2.** **(Potential)** **Other** **Endpoints**

Based on the project’s architecture (controller/service layers) you may
have or add these endpoints in the future:

> **Endpoint** **Method** **Purpose**
>
> GET /api/allProperties GET Retrieve all properties detailed from the
> CSV file
>
> POST /api/filter
>
> GET /api/health

POST Takes natural language text and extracts filters

GET Health-check endpoint (status of service/data)

**Note:** These are suggested/typical endpoints; verify their presence
in your code. If any exist in your controller package, document them
similarly.

**<u>Frontend UI Overview</u>**

The app has 4 main sections:

> **Section**
>
> **Header**

**Description**

Shows project title and links to your profiles

> **Notes** **Section** Detailed guide on how to format queries for best
> results **Chat** **Window** Shows a conversation between the user and
> the AI (bot) **Input** **Panel** For typing messages and using control
> buttons
>
> <img src="./zbgkjoas.png"
> style="width:0.52917in;height:0.4525in" /><img src="./bhnpfowi.png"
> style="width:7.08681in;height:3.3493in" />**Detailed** **Feature**
> **Guide**
>
> <img src="./g2pqgzdp.png"
> style="width:0.52917in;height:0.4525in" /><img src="./c3i1xgsb.png"
> style="width:0.52917in;height:0.4525in" />**1.** **Header**
> **Includes:**
>
> • **LinkedIn** **Button** → Opens your LinkedIn profile • **GitHub**
> **Button** → Opens your GitHub profile
>
> <img src="./2dvc23eb.png"
> style="width:0.52917in;height:0.4525in" />**2.** **Notes** **Section**

<img src="./gl2qdwdu.png"
style="width:0.52917in;height:0.4525in" />This section helps users
understand how to use your AI correctly. **Buttons:**

> • **Expand** **/** **Collapse**
>
> <img src="./dy11riry.png"
> style="width:0.52917in;height:0.4525in" />o Expands the full
> instructional text for easy reading o Collapses it into a small box
> when not needed
>
> **Content** **Includes:**
>
> • Purpose of the AI
>
> • Correct prompt structure • Example searches
>
> • Field meanings (BHK, Status, Price, etc.) • API details (/api/search
> endpoint)
>
> <img src="./jyzblory.png"
> style="width:0.52917in;height:0.4525in" />**3.** **Chat** **Section**

<img src="./wckudnmr.png"
style="width:0.52917in;height:0.4525in" />The main area where user and
AI messages appear. **Message** **Types**

> **Type** **Role** **Style**
>
> **User** role: "user" Purple background, right-aligned **Bot**
> **(Assistant)** role: "bot" Gray background, left-aligned
>
> <img src="./h1qacpc3.png"
> style="width:0.52917in;height:0.4525in" />**4.** **AI**
> **Responses**<img src="./cces5q1e.png"
> style="width:7.08681in;height:0.85278in" /><img src="./yu3eseac.png"
> style="width:7.08681in;height:3.075in" />

Each AI (bot) message can include multiple sections:

> <img src="./g5ylkiem.png"
> style="width:0.52917in;height:0.4525in" />**a)** **Extracted**
> **Filters**

Displays what filters your backend extracted from the user query (e.g.,
city, bhk, budget).

> <img src="./3cgvfs1y.png"
> style="width:0.52917in;height:0.4525in" />**b)** **Summary**

<img src="./ek514fek.png"
style="width:7.08681in;height:1.21458in" />Short text summarising the
search results — generated by your backend (e.g., “Found 5 ready-to-move
2BHK flats in Pune…”)

> <img src="./r14lkgep.png"
> style="width:0.52917in;height:0.4525in" />**c)** **Cards**

Each property is shown in a **card** **format**, containing: • Project
Name

> • Full Address • Landmark
>
> • BHK, Bathrooms, Balconies
>
> • Property Type • Furnishing
>
> <img src="./vc5gzslq.png"
> style="width:7.08681in;height:3.29097in" />• Status • Price
>
> <img src="./zec1hyhb.png"
> style="width:0.52917in;height:0.4525in" />**5.** **Expandable**
> **“Show** **Slug”** **Feature**

Each property card has a small link at the bottom:

<img src="./bdpupcde.png"
style="width:4.20833in;height:1.22917in" />When clicked, it expands to
show the backend’sctaSlug or a placeholder if not available. This is
helpful for internal reference or frontend routing.

> <img src="./ihikj5lm.png"
> style="width:0.52917in;height:0.4525in" />**6.** **Input** **+**
> **Control** **Buttons**

At the bottom (sticky fixed section):

> **Button** **Function**
>
> **Send** handleSend()

**Description**

Sends the user’s text to backend endpoint /api/search via POST

> **All** **Properties**

handleAllProperties() Fetches all properties via /api/allProperties

> **Clear** **Chat** setMessages(\[\]) Clears all chat history from UI

<img src="./hm4s5h2j.png"
style="width:1.53125in;height:1.84375in" />

> <img src="./bmf0jfnc.png"
> style="width:0.52917in;height:0.4525in" /><img src="./bsbcignt.png"
> style="width:0.52917in;height:0.4525in" />**7.** **API** **Calls**
>
> **POST** **/api/search**

Used when the user sends a query.

> <img src="./pjcchtrf.png"
> style="width:0.52917in;height:0.4525in" />**GET**
> **/api/allProperties**

Returns **all** **available** **properties** in the dataset.

<img src="./hddkr30f.png"
style="width:0.52917in;height:0.4525in" />**8.** **Loading** **&**
**Error** **Handling** While requests are in progress:

> • The **Send** button shows Sending...
>
> • If the backend fails to respond: Error: Server returned 500

<img src="./nwjnsxsp.png"
style="width:0.52917in;height:0.4525in" />**9.** **Clear** **Chat**
Quickly resets the UI:

> <img src="./wovbo5wq.png"
> style="width:0.52917in;height:0.4525in" />**10.** **Smart** **Scroll**
>
> • Automatically scrolls to bottom on new messages.
>
> • Smoothly scrolls to the Notes section when expanded.
