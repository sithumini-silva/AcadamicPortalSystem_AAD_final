$(document).ready(function () {
    loadNextId();
    $('#dataTable').on('click', 'tr', function () {
        var userId = $(this).find('td:eq(0)').text();
        var name = $(this).find('td:eq(1)').text();
        var contact = $(this).find('td:eq(2)').text();
        var address = $(this).find('td:eq(3)').text();
        var email = $(this).find('td:eq(4)').text();
        var role = $(this).find('td:eq(5)').text();

        $('#u_id').val(userId);
        $('#name').val(name);
        $('#contact').val(contact);
        $('#address').val(address);
        $('#email').val(email);
        $('#role').val(role);
    });
});


function saveData() {
    let user = {
        name: $("#name").val(),
        contact: $("#contact").val(),
        address: $("#address").val(),
        email: $("#email").val(),
        role: $("#role").val(),
        password: $("#password").val()
    };

    $.ajax({
        url: "http://localhost:8080/api/v1/user/save",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(user),
        success: function () {
            alert("User saved successfully!");
            $("#userForm")[0].reset();
            loadNextId();
            loadAllUsers();
            clearFields();
        },
        error: function (xhr) {
            alert("Error: " + xhr.responseText);
        }
    });
}
function loadNextId() {
    let token = localStorage.getItem("jwtToken");
    console.log("u_token",token)

    if (!token) {
        alert("Not authenticated!");
        return;
    }

    fetch("http://localhost:8080/api/v1/user/next-id", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (!response.ok) throw new Error("Unauthorized access!");
            return response.json(); // Parse response as JSON
        })
        .then(nextId => {
            console.log("Next ID received:", nextId); // Debugging line

            const uIdInput = document.getElementById("u_id");
            if (uIdInput) {
                uIdInput.value = nextId; // Set value correctly
            } else {
                console.error("Element with id 'u_id' not found.");
            }
        })
        .catch(error => {
            console.error("Error fetching next ID:", error);
            // alert("Authentication required!");
        });
}


function updateData() {
    let token = localStorage.getItem("jwtToken");
    if (!token) {
        console.error("No token found in localStorage");
        alert("Authorization token is missing!");
        return;
    }

    let userId = $("#u_id").val().trim();

    if (!userId || isNaN(userId) || parseInt(userId) === 0) {
        alert("Invalid user ID! Please select a valid user.");
        return;
    }

    let userData = {
        u_id: parseInt(userId),
        name: $("#name").val().trim(),
        contact: $("#contact").val().trim(),
        address: $("#address").val().trim(),
        email: $("#email").val().trim(),
        role: $("#role").val(),
        password: $("#password").val().trim()
    };

    $.ajax({
        url: "http://localhost:8080/api/v1/user/update",
        method: "PUT",
        contentType: "application/json",
        headers: {
            "Authorization": "Bearer " + token
        },
        data: JSON.stringify(userData),
        success: function (response) {
            alert("User updated successfully!");
            console.log("Response:", response);
            loadAllUsers();
            clearFields();
        },
        error: function (xhr, status, error) {
            console.error("Update failed:", xhr.responseText);
            alert("Error updating user: " + xhr.responseText);
        }
    });
}


function loadAllUsers() {


    //***************
    $.ajax({
        url: "http://localhost:8080/api/v1/user/get",
        type: "GET",
        success: function (data) {
            let tableBody = $("#dataTable");
            tableBody.empty();
            data.forEach(user => {
                tableBody.append(`
                    <tr>
                        <td>${user.u_id}</td>
                        <td>${user.name}</td>
                        <td>${user.contact}</td>
                        <td>${user.address}</td>
                        <td>${user.email}</td>
                        <td>${user.role}</td>
                    </tr>
                `);
            });
        },
        error: function () {
            alert("Error loading Users.");
        }
    });
}

function deleteData() {
    let u_id = $("#u_id").val();

    $.ajax({
        url: `http://localhost:8080/api/v1/user/delete/${u_id}`,
        type: "DELETE",
        success: function () {
            alert("User deleted successfully!");
            loadAllUsers();
            clearFields();
        },
        error: function () {
            alert("Error deleting User.");
        }
    });
}

function clearFields() {
    $("#u_id").val('');
    $("#name").val('');
    $("#contact").val('');
    $("#address").val('');
    $("#email").val('');
    $("#role").val('');
    $("#password").val('');
}
