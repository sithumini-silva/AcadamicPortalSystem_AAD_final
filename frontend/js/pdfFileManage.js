function getJWTToken() {
    return localStorage.getItem("jwtToken");
}

$(document).ready(function () {
    loadUsers();
    fetchPDFs();
});

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
        const userSelect = document.getElementById('userDropdown');
        userSelect.innerHTML = '<option value="">--Select Admin--</option>';

        users.forEach(user => {
            if (user.role && user.role.toUpperCase() === "ADMIN") {
                const option = document.createElement('option');
                option.value = user.u_id;
                option.textContent = ` ${user.u_id}`;
                userSelect.appendChild(option);
            }
        });
    } catch (error) {
        console.error('Error:', error);
        $('#message').text('Error loading users: ' + error.message);
    }
}



$('#uploadForm').submit(function (event) {
    event.preventDefault();

    const file = $('#pdfFile')[0].files[0];
    const userId = $('#userDropdown').val();

    if (!file || !userId) {
        $('#message').text("Please choose a user and select a PDF.");
        return;
    }

    if (file.size > 10 * 1024 * 1024) {
        $('#message').text("File size exceeds 10MB limit.");
        return;
    }

    const formData = new FormData();
    formData.append("file", file);
    formData.append("userId", userId);

    $.ajax({
        url: "http://localhost:8080/api/v1/pdf/upload",
        type: "POST",
        headers: {
            "Authorization": "Bearer " + getJWTToken()
        },
        data: formData,
        processData: false,
        contentType: false,
        success: function (response) {
            $('#message').text(response);
            $('#uploadForm')[0].reset();
        },
        error: function (xhr) {
            let errorMsg = "Upload failed";
            if (xhr.responseJSON && xhr.responseJSON.message) {
                errorMsg += ": " + xhr.responseJSON.message;
            } else if (xhr.responseText) {
                errorMsg += ": " + xhr.responseText;
            }
            $('#message').text(errorMsg);
        }
    });
});

function fetchPDFs() {
    $.ajax({
        url: "http://localhost:8080/api/v1/pdf/all",
        method: "GET",
        headers: {
            'Authorization': 'Bearer ' + getJWTToken()
        },

        success: function (pdfList) {
            const tableBody = $("#pdfTable");
            tableBody.empty();

            pdfList.forEach(pdf => {
                const userInfo = pdf.userId ? `${pdf.userId} ` : 'N/A';

                const row = `
                    <tr>
                        <td>${pdf.id}</td>
                        <td>${pdf.fileName}</td>
                        <td>${userInfo}</td>
                        <td>
                            <a href="http://localhost:8080/api/v1/pdf/view/${pdf.id}" target="_blank">View</a> |
                            <a href="http://localhost:8080/api/v1/pdf/download/${pdf.id}" target="_blank">Download</a>
                            <button class="deleteBtn" data-id="${pdf.id}" style="background-color: #c95353">Delete</button>
                        </td>
                    </tr>
                `;
                tableBody.append(row);
            });


            $('.deleteBtn').click(function () {
                const id = $(this).data('id');
                if (confirm('Are you sure you want to delete this PDF?')) {
                    $.ajax({
                        url: 'http://localhost:8080/api/v1/pdf/delete/' + id,
                        type: 'DELETE',
                        headers: {
                            'Authorization': 'Bearer ' + getJWTToken()
                        },
                        success: function () {
                            alert('PDF deleted!');
                            resetForm();
                            fetchPDFs(); // Refresh table
                        },
                        error: function (err) {
                            alert('Delete failed: ' + err.responseText);
                        }
                    });
                }
            });
        },
        error: function (err) {
            console.error("Failed to fetch PDFs", err);
        }
    });

}



function resetForm() {
    $('#uploadForm')[0].reset();
    $('#pdfId').val('');
    $('#message').text('');
}




