    let questionsData = [];
    let answersData = {};

    function getJWTToken() {
    return localStorage.getItem("jwtToken");
}

    async function loadQuestionsForExam(examId) {
    try {
    const token = getJWTToken();
    const response = await fetch('http://localhost:8080/api/v1/question/get', {
    method: 'GET',
    headers: {
    'Authorization': 'Bearer ' + token,
    'Content-Type': 'application/json'
}
});

    if (!response.ok) throw new Error('Failed to load questions');

    let allQuestions = await response.json();


    questionsData = allQuestions.filter(q => q.examId === parseInt(examId));

    if (questionsData.length === 0) {
    document.getElementById('examPaper').innerHTML = '<p>No questions found for this exam.</p>';
    return;
}

    answersData = {};
    questionsData.forEach(q => {
    answersData[q.qid] = [];
});

    renderQuestions();

} catch (error) {
    console.error('Error:', error);
    document.getElementById('examPaper').innerHTML = '<p>Error loading questions. Please refresh the page.</p>';
}
}





    function renderQuestions() {
    const questionsHTML = questionsData.map((q, index) => `
        <div class="question">
            <p><strong>${index + 1}. ${q.content}</strong></p> <!-- Dynamically set question number -->
            <div class="options">
                ${['option1', 'option2', 'option3', 'option4'].map((opt, i) => `
                    <label>
                        <input type="checkbox"
                            name="question_${q.qid}"
                            value="${i + 1}"
                            onchange="saveAnswer(${q.qid}, ${i + 1}, this)"
                            ${answersData[q.qid].includes(i + 1) ? 'checked' : ''}>
                        ${String.fromCharCode(97 + i)}) ${q[opt]}
                    </label><br>
                `).join('')}
            </div>
        </div>
        <hr>
    `).join('');

    document.getElementById('examPaper').innerHTML = `
        <h3>Answer All Questions</h3>
        ${questionsHTML}
    `;
}



    function saveAnswer(qid, answer, checkbox) {
    if (checkbox.checked) {

    answersData[qid].push(answer);
} else {

    answersData[qid] = answersData[qid].filter(ans => ans !== answer);
}
    console.log("Saved Answers:", answersData);
}


    async function submitAnswers() {
    try {
    const examId = document.getElementById('examId').value;
    const token = getJWTToken();
    const response = await fetch('http://localhost:8080/api/v1/question/submit1?examId=' + examId, {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + token
},
    body: JSON.stringify(answersData)
});

    if (!response.ok) throw new Error('Failed to submit answers');

    const result = await response.json();
    const score = result.score;
    const totalQuestions = result.totalQuestions;
    const percentage = result.percentage;
    const grade = result.grade;

    // Display score, percentage, and grade
    document.getElementById('result').innerHTML = `
            <p>Your Score: ${score} out of ${totalQuestions}</p>
            <p>Percentage: ${percentage.toFixed(2)}%</p>
            <p>Grade: ${grade}</p>
        `;
} catch (error) {
    console.error('Error:', error);
    document.getElementById('result').innerText = 'Error calculating score. Try again.';
}
}



    async function loadExams() {
    try {
    const token = getJWTToken();
    const response = await fetch('http://localhost:8080/api/v1/exam/getAllExamIds', {
    headers: {
    'Authorization': 'Bearer ' + token
}
});
    if (!response.ok) throw new Error('Failed to load exams');

    const examIds = await response.json();
    const examSelect = document.getElementById('examId');
    examIds.forEach(id => {
    const option = document.createElement('option');
    option.value = id;
    option.textContent = ` ${id}`;
    examSelect.appendChild(option);
});
} catch (error) {
    console.error('Error:', error);
}
}

    // Load users
    async function loadUsers() {
    try {
    const token = getJWTToken();
    const response = await fetch('http://localhost:8080/api/v1/user/get', {
    headers: {
    'Authorization': 'Bearer ' + token
}
});
    if (!response.ok) throw new Error('Failed to load users');

    const users = await response.json();
    const userSelect = document.getElementById('userId');
    users.forEach(user => {
    const option = document.createElement('option');
    option.value = user.u_id;
    option.textContent = ` ${user.u_id}`;
    userSelect.appendChild(option);
});
} catch (error) {
    console.error('Error:', error);
}
}


    async function saveResult() {
    const userId = document.getElementById("userId").value;
    const examId = document.getElementById("examId").value;

    if (!userId || !examId) {
    alert("Please select an exam and user before saving results.");
    return;
}


    const scoreText = document.getElementById("result").querySelector("p:nth-child(1)")?.textContent || "";
    const percentageText = document.getElementById("result").querySelector("p:nth-child(2)")?.textContent || "";
    const gradeText = document.getElementById("result").querySelector("p:nth-child(3)")?.textContent || "";


    const score = scoreText.split(": ")[1] || "0";
    const percentage = percentageText.split(": ")[1]?.replace("%", "") || "0"; // Remove '%' sign
    const grade = gradeText.split(": ")[1] || "N/A";

    const resultData = {
    msg: grade,
    totalMark: percentage,
    exam: { id: examId },
    student: { u_id: userId }
};

    try {
    const token = getJWTToken();
    const response = await fetch('http://localhost:8080/api/v1/results/save', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + token
},
    body: JSON.stringify(resultData)
});

    const result = await response.json();
    alert("Saved successfully");
} catch (error) {
    console.error('Error:', error);
    alert('Failed to save result.');
}
}


    window.addEventListener('DOMContentLoaded', () => {
    loadExams();
    loadUsers();
});


    document.getElementById('examId').addEventListener('change', (event) => {
    const examId = event.target.value;
    if (examId) {
    loadQuestionsForExam(examId);
}
});

