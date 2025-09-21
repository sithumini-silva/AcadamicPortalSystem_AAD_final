$(document).ready(function () {
    loadStudentIds();
    loadExamIds();
    loadNextResultId();
});

// Fetch the JWT token from localStorage
function getToken() {
    return localStorage.getItem("jwtToken");
}

function loadNextResultId() {
    let token = getToken();  // Fetch the token

    if (!token) {
        alert("You need to log in!");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/results/next-id",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + token  // Add JWT token to the header
        },
        success: function (nextId) {
            $("#result_id").val(nextId);
        },
        error: function (error) {
            console.log("Error:", error);
            alert("Failed to load next result ID.");
        }
    });
}

function loadStudentIds() {
    let token = getToken();  // Fetch the token

    if (!token) {
        alert("You need to log in!");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/user/get",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + token  // Add JWT token to the header
        },
        success: function (users) {
            let studentSelect = $("#student_id");
            studentSelect.empty();
            studentSelect.append('<option value="">Select Student</option>');

            users.forEach(user => {
                if (user.role === 'student' || user.role === 'Student' || user.role === 'STUDENT') {
                    studentSelect.append(`<option value="${user.u_id}">${user.u_id}</option>`);
                }
            });
        },
        error: function (error) {
            console.log("Error:", error);
            alert("Failed to load students.");
        }
    });
}

function loadExamIds() {
    let token = getToken();  // Fetch the token

    if (!token) {
        alert("You need to log in!");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/exam/getAllExamIds",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + token  // Add JWT token to the header
        },
        success: function (exams) {
            console.log(exams);  // Log the response to inspect it
            let examSelect = $("#exam_id");
            examSelect.empty();
            examSelect.append('<option value="">Select Exam</option>');

            // Loop through the array of exam IDs (numbers)
            exams.forEach(examId => {
                examSelect.append(`<option value="${examId}">${examId}</option>`);
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error Status:", textStatus);
            console.log("Error Thrown:", errorThrown);
            console.log("Response Text:", jqXHR.responseText);
            alert("Failed to load exams.");
        }
    });
}


function saveResult() {
    let token = getToken();  // Fetch the token

    if (!token) {
        alert("You need to log in!");
        return;
    }

    let resultData = {
        msg: $("#msg").val(),
        totalMark: $("#total_mark").val(),
        exam: { id: $("#exam_id").val() },
        student: { id: $("#student_id").val() }
    };

    $.ajax({
        url: "http://localhost:8080/api/v1/results/save",
        type: "POST",
        contentType: "application/json",
        headers: {
            "Authorization": "Bearer " + token  // Add JWT token to the header
        },
        data: JSON.stringify(resultData),
        success: function () {
            alert("Result saved successfully!");
            viewResults();
            clearForm();
        },
        error: function (error) {
            console.log("Error:", error);
            alert("Failed to save result.");
        }
    });
}

function viewResults() {
    let token = getToken();  // Fetch the token

    if (!token) {
        alert("You need to log in!");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/results/get",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + token  // Add JWT token to the header
        },
        success: function (results) {
            let tableBody = $("#resultTableBody");
            tableBody.empty();

            results.forEach(result => {
                // Log the individual result object to check its structure
                console.log(result);

                const examId = result.examId ? result.examId : 'N/A';  // Use 'N/A' if examId is missing
                const studentId = result.studentId ? result.studentId : 'N/A';  // Use 'N/A' if studentId is missing

                tableBody.append(`
                    <tr>
                        <td>${result.id}</td>
                        <td>${examId}</td>
                        <td>${studentId}</td>
                        <td>${result.msg}</td>
                        <td>${result.totalMark}</td>
                    </tr>
                `);
            });
        },
        error: function (error) {
            console.log("Error:", error);
            alert("Failed to load results.");
        }
    });
}

function deleteResult() {
    let token = getToken();  // Fetch the token
    let resultId = $("#result_id").val();

    if (!token) {
        alert("You need to log in!");
        return;
    }

    if (resultId === "") {
        alert("Please provide a Result ID to delete.");
        return;
    }

    $.ajax({
        url: `http://localhost:8080/api/v1/results/delete/${resultId}`,
        type: "DELETE",
        headers: {
            "Authorization": "Bearer " + token  // Add JWT token to the header
        },
        success: function () {
            alert("Result deleted successfully!");
            viewResults();
            clearForm();
        },
        error: function (error) {
            console.log("Error:", error);
            alert("Failed to delete result.");
        }
    });
}

function clearForm() {
    $("#result_id").val("");
    $("#exam_id").val("");
    $("#student_id").val("");
    $("#msg").val("");
    $("#total_mark").val("");
}
