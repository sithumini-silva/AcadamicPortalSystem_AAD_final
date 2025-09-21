$('#emailForm').on('submit', function(e) {
    e.preventDefault();

    const token = localStorage.getItem('jwtToken');

    if (!token) {
        alert('You are not authenticated!');
        return;
    }

    const recipients = $('#to').val().split(',').map(email => email.trim());
    const subject = $('#subject').val();
    const body = $('#body').val();

    if (recipients.length === 0 || subject === '' || body === '') {
        alert('Please fill all fields.');
        return;
    }

    $.ajax({
        url: 'http://localhost:8080/email/send',
        type: 'POST',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        data: {
            to: recipients.join(','),
            subject: subject,
            body: body
        },
        contentType: 'application/x-www-form-urlencoded',
        success: function(response) {
            alert('Email sent successfully to all recipients');
            saveEmailRecord(recipients.join(','), subject, body);
            $('#emailForm')[0].reset();
        },
        error: function(xhr, status, error) {
            console.error("Error: " + error);
            alert('Failed to send email.');
        }
    });
});

function saveEmailRecord(recipients, subject, body) {
    const token = localStorage.getItem('jwtToken');

    if (!token) {
        alert('You are not authenticated!');
        return;
    }

    $.ajax({
        url: 'http://localhost:8080/email/save',
        type: 'POST',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        data: {
            to: recipients,
            subject: subject,
            body: body
        },
        contentType: 'application/x-www-form-urlencoded',
        success: function(saveResponse) {
            console.log('Email record saved successfully!');
        },
        error: function(saveError) {
            console.error("Error saving email record: ", saveError);
            alert('Failed to save email record.');
        }
    });
}

function openInboxPopup() {
    $('#inboxPopup').fadeIn();

    loadInbox();
}

function closeInboxPopup() {

    $('#inboxPopup').fadeOut();
}

function loadInbox() {
    const token = localStorage.getItem('jwtToken');

    // Load emails from the backend
    $.ajax({
        url: 'http://localhost:8080/email/inbox',
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function(data) {
            console.log(data);
            let html = '';
            if (data.length === 0) {
                html = '<p>No emails found.</p>';
            } else {
                data.forEach(email => {

                    const date = new Date(email.sentAt);
                    const formattedDate = date.toLocaleString();

                    html += `<div style="margin-bottom: 15px;">
                                <strong>${email.subject}</strong><br>
                                <small>${email.recipients}</small><br>
                                <p>${email.body}</p><br>
                                <small><i>${formattedDate}</i></small>  
                             </div><hr>`;
                });
            }
            $('#inboxEmailList').html(html);
        },
        error: function(err) {
            $('#inboxEmailList').html('<p>Error loading inbox.</p>');
            console.error('Inbox load error:', err);
        }
    });
}

//sent mails
function openSentPopup() {
    $('#sentPopup').fadeIn();
    loadSentEmails();
}

function closeSentPopup() {
    $('#sentPopup').fadeOut();
}

function loadSentEmails() {
    const token = localStorage.getItem('jwtToken');

    if (!token) {
        alert('You are not authenticated!');
        return;
    }

    $.ajax({
        url: 'http://localhost:8080/email/inbox',
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function(data) {
            let html = '';

            if (data.length === 0) {
                html = '<p>No sent emails found.</p>';
            } else {
                data.forEach(email => {
                    const formattedDate = new Date(email.sentAt).toLocaleString();
                    html += `
                            <div style="margin-bottom: 15px;">
                                <strong>${email.subject}</strong><br>
                                <small>To: ${email.recipients}</small><br>
                                <p>${email.body}</p>
                                <small><i>${formattedDate}</i></small>
                            </div><hr>
                        `;
                });
            }

            $('#sentEmailList').html(html);
        },
        error: function(error) {
            console.error('Error loading sent emails:', error);
            $('#sentEmailList').html('<p>Error loading sent emails.</p>');
        }
    });
}


//trash
function openTrashPopup() {
    $('#trashPopup').fadeIn();
    loadTrashEmails();
}

function closeTrashPopup() {
    $('#trashPopup').fadeOut();
}

function loadTrashEmails() {
    const token = localStorage.getItem('jwtToken');

    if (!token) {
        alert('You are not authenticated!');
        return;
    }

    $.ajax({
        url: 'http://localhost:8080/email/inbox',
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            let html = '';

            if (data.length === 0) {
                html = '<p>No deleted emails found.</p>';
            } else {
                data.forEach(email => {
                    const formattedDate = new Date(email.sentAt).toLocaleString();
                    html += `
                        <div style="margin-bottom: 15px; border-bottom: 1px solid #ccc; padding-bottom: 10px;">
                            <small>${email.id})</small><br>
                            <strong>${email.subject}</strong><br>
                            <small>To: ${email.recipients}</small><br>
                            <p>${email.body}</p>
                            <small><i>${formattedDate}</i></small>
                            <button onclick="deleteEmail(${email.id}, 'trash')" style="margin-top:5px;">🗑️ Permanently Delete</button>
                        </div>
                    `;
                });
            }

            $('#trashEmailList').html(html);
        },
        error: function (error) {
            console.error('Error loading trash emails:', error);
            $('#trashEmailList').html('<p>Error loading trash emails.</p>');
        }
    });
}


function deleteEmail(emailId, source) {
    if (!confirm("Are you sure you want to delete this email permanently?")) return;

    const token = localStorage.getItem('jwtToken');

    $.ajax({
        url: `http://localhost:8080/email/delete/${emailId}`,
        type: 'DELETE',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (response) {
            if (response) {
                alert('Email permanently deleted.');
                if (source === 'trash') {
                    loadTrashEmails();
                }
            } else {
                alert('Email could not be deleted.');
            }
        },
        error: function () {
            alert('Failed to delete email.');
        }
    });
}


//load subjects
function openSubjectsPopup() {
    $('#subjectsPopup').fadeIn();
    loadSubjects();
}

function closeSubjectsPopup() {
    $('#subjectsPopup').fadeOut();
}

function loadSubjects() {
    const token = localStorage.getItem('jwtToken');

    if (!token) {
        alert('You are not authenticated!');
        return;
    }

    $.ajax({
        url: 'http://localhost:8080/api/v1/subject/get',
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function(data) {
            let html = '';
            let subjectCount = data.length;

            $('#subjectsMailboxCount').text(subjectCount);

            if (subjectCount === 0) {
                html = '<p>No subjects found.</p>';
            } else {
                data.forEach(subject => {
                    const formattedDate = new Date(subject.date).toLocaleDateString(); // only date
                    const formattedTime = subject.time || 'Invalid time'; // plain time from DB

                    html += `
                        <div class="subject-row" data-id="${subject.id}" style="margin-bottom: 15px; border-bottom: 1px solid #ccc; padding-bottom: 10px; cursor: pointer;">
                            <strong>${subject.name}</strong><br>
                            <small>Students Count: ${subject.st_count}</small><br>
                            <small>Date: ${formattedDate}</small><br>
                            <small>Time: ${formattedTime}</small><br>
                        </div>
                    `;
                });
            }

            $('#subjectsEmailList').html(html);

            // Bind click event to subject rows
            $('.subject-row').on('click', function() {
                const subjectName = $(this).find('strong').text();
                const studentCount = $(this).find('small').eq(0).text().split(': ')[1];
                const date = $(this).find('small').eq(1).text().split(': ')[1];
                const time = $(this).find('small').eq(2).text().split(': ')[1];

                $('#subject').val(subjectName);
                $('#body').val(`Date: ${date}\nTime: ${time}\nStudent Count: ${studentCount}`);

                // ✅ Close the popup form
                closeSubjectsPopup();
            });
        },
        error: function(error) {
            console.error('Error loading subjects:', error);
            $('#subjectsEmailList').html('<p>Error loading subjects.</p>');
        }
    });
}

// function closeSubjectsPopup() {
//     $('#subjectsPopup').fadeOut(); // You can also use .hide()
// }

function openUsersPopup() {
    $('#usersPopup').fadeIn();
    loadUsers();
}


function closeUsersPopup() {
    $('#usersPopup').fadeOut();
}
function loadUsers() {
    const token = localStorage.getItem('jwtToken');

    if (!token) {
        alert('You are not authenticated!');
        return;
    }

    $.ajax({
        url: 'http://localhost:8080/api/v1/user/get',
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function(data) {
            let html = '';
            if (data.length === 0) {
                html = '<p>No users found.</p>';
            } else {
                data.forEach(user => {
                    html += `
                        <div class="user-row" style="margin-bottom: 15px; border-bottom: 1px solid #ccc; padding-bottom: 10px;">
                            <input type="checkbox" class="user-checkbox" data-email="${user.email}"> 
                            <strong>${user.name}</strong><br>
                            <small>Email: ${user.email}</small><br>
                            <small>Role: ${user.role}</small><br>
                        </div>
                    `;
                });
            }
            $('#usersList').html(html);


            $('.user-checkbox').on('change', function() {
                let selectedEmails = [];


                $('.user-checkbox:checked').each(function() {
                    selectedEmails.push($(this).data('email'));
                });


                $('#to').val(selectedEmails.join(', '));

            });
        },
        error: function(error) {
            console.error('Error loading users:', error);
            $('#usersList').html('<p>Error loading users.</p>');
        }
    });
}

function openExamsPopup() {
    $('#examsPopup').show();
    loadExams();
}

function closeExamsPopup() {
    $('#examsPopup').hide();
}

function loadExams() {
    const token = localStorage.getItem('jwtToken');

    if (!token) {
        alert('You are not authenticated!');
        return;
    }

    $.ajax({
        url: 'http://localhost:8080/api/v1/exam/get',
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function(data) {
            let html = '';
            if (data.length === 0) {
                html = '<p>No exams found.</p>';
            } else {
                data.forEach(exam => {
                    html += `
                        <div class="exam-row" style="margin-bottom: 15px; border-bottom: 1px solid #ccc; padding-bottom: 10px;">
                            <input type="checkbox" class="exam-checkbox" data-name="${exam.description}" data-duration="${exam.duration}" data-id="${exam.id}">
                            <strong>${exam.description}</strong><br>
                            <small>Duration: ${exam.duration} hours</small><br>
                        </div>
                    `;
                });
            }
            $('#examsList').html(html);

            $('.exam-checkbox').on('change', function() {
                let selectedExams = [];
                let examDescriptions = [];
                let examDurations = [];
                let bodyText = '';

                $('.exam-checkbox:checked').each(function() {
                    selectedExams.push($(this).data('name') + ' (ID: ' + $(this).data('id') + ')');
                    examDescriptions.push($(this).data('name'));
                    examDurations.push("Duration: " + $(this).data('duration') + " hours");


                    bodyText += $(this).data('name') + "\n" + "Duration: " + $(this).data('duration') + " hours\n\n";
                });


                $('#subject').val(examDescriptions.join(', '));

                $('#body').val(bodyText.trim());
            });
        },
        error: function(error) {
            console.error('Error loading exams:', error);
            $('#examsList').html('<p>Error loading exams.</p>');
        }
    });
}

$(document).ready(function() {
    loadExams();
});








