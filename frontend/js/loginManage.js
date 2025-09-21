$(document).ready(function () {

    $(".btn-login").on("click", function (event) {
        event.preventDefault();

        var email = $("#inputEmail").val().trim();
        var password = $("#inputPassword").val().trim();

        if (!email || !password) {
            Swal.fire({
                icon: 'warning',
                title: 'Missing Fields',
                text: 'Please fill in all required fields.',
                confirmButtonColor: '#4FB783'
            });
            return;
        }

        $.ajax({
            url: 'http://localhost:8080/api/v1/auth/authenticate',
            method: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({
                email: email,
                password: password
            }),

            success: function (response) {
                console.log("Login successful:", response);

                if (response && response.data && response.data.token) {
                    onLoginSuccess(response.data); // clear inputs & save token

                    const token = response.data.token;
                    const decoded = jwt_decode(token);
                    let role = decoded.role || "";

                    Swal.fire({
                        icon: 'success',
                        title: 'Login Successful',
                        text: 'Welcome back!',
                        confirmButtonColor: '#4FB783',
                        timer: 1500,
                        showConfirmButton: false
                    });

                    setTimeout(() => {
                        if (role === "ROLE_ADMIN" || role === "Admin" || role === "ADMIN") {
                            window.location.href = "../static/teacherDash.html";
                        } else if (role === "ROLE_STUDENT" || role === "Student" || role === "STUDENT") {
                            window.location.href = "../static/studentDash.html";
                        } else if (role === "ROLE_TEACHER" || role === "Teacher" || role === "TEACHER") {
                            window.location.href = "../static/studentDash.html";
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: 'Invalid Role',
                                text: 'Role not recognized: ' + role,
                                confirmButtonColor: '#4FB783'
                            });
                        }
                    }, 1600);

                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Token Missing',
                        text: 'Login successful, but token is missing. Please try again.',
                        confirmButtonColor: '#4FB783'
                    });
                }
            },

            error: function () {
                Swal.fire({
                    icon: 'error',
                    title: 'Login Failed',
                    text: 'Please check your credentials.',
                    confirmButtonColor: '#4FB783'
                });
            }
        });
    });

    function onLoginSuccess(response) {
        // Save token & role
        localStorage.setItem("jwtToken", response.token);
        localStorage.setItem("userRole", jwt_decode(response.token).role);

        $("#inputEmail").val("");
        $("#inputPassword").val("");

        console.log("Token after saving:", localStorage.getItem("jwtToken"));
    }
});
