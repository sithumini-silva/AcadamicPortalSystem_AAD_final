📌 Project Description

👩‍🎓 Teachers/Students → Register, log in, view schedules, take online exams with auto-grading, check results, and download notes.
👨‍🏫 Admins → Manage subjects, questions, schedules, results, notes, emails, and users.
📩 Communication → Automated emails & notifications.
📑 Resources → Upload and share PDF notes.
🔐 Role-based Access → Separate dashboards for Students, Teachers, and Admins.


⚙️ Setup Instructions  

1️⃣ Clone the Repository  
[git clone](https://github.com/sithumini-silva/AcadamicPortalSystem_AAD_final.git)
cd academic-portal-system

2️⃣ Backend Setup (Spring Boot)
      #  Open the backend folder in your IDE (IntelliJ/Eclipse).
      #  Configure MySQL Database in application.properties:
              spring.datasource.url=jdbc:mysql://localhost:3306/academic_portal
              spring.datasource.username=root
              spring.datasource.password=Ijse@123

# Run the backend using:
   mvn spring-boot:run

[The backend will start at:](http://localhost:8080)

3️⃣ Frontend Setup (HTML, CSS, JS, Bootstrap, Ajax)  
    📂 Open the **`frontend`** folder.  
    ▶️ Run the project using **Live Server (VS Code)** or open `index.html` in your browser.  
    ⚡ Make sure the **backend is running** so the frontend can communicate with it.  

4️⃣ Sample Login Credentials  
    👨‍💼 **Admin** → `admin@example.com / admin123`  
    🎓 **Student** → `student@example.com / student123`  

5️⃣ Access the Application  
    🌐 **Frontend** → [http://localhost:5500](http://localhost:5500) *(if using Live Server)*  
    ⚙️ **Backend API** → [http://localhost:8080](http://localhost:8080)  



🎥 Demo Video  
👉 [Watch on YouTube](https://youtu.be/TA4hguvR8pY)


