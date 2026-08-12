<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>Register - Electricity Bill Calculator</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

</head>

<body class="bg-light">

<div class="container">

    <div class="row justify-content-center mt-5">

        <div class="col-md-5">

            <div class="card shadow">

                <div class="card-body p-4">

                    <h2 class="text-center mb-4">
                        Create Account
                    </h2>


                    <% if (request.getParameter("error") != null) { %>

                        <div class="alert alert-danger">
                            <%= request.getParameter("error") %>
                        </div>

                    <% } %>


                    <form action="register"
                          method="post"
                          id="registerForm">

                        <div class="mb-3">

                            <label class="form-label">
                                Full Name
                            </label>

                            <input
                                type="text"
                                name="name"
                                class="form-control"
                                required>

                        </div>


                        <div class="mb-3">

                            <label class="form-label">
                                Email
                            </label>

                            <input
                                type="email"
                                name="email"
                                class="form-control"
                                required>

                        </div>


                        <div class="mb-3">

                            <label class="form-label">
                                Password
                            </label>

                            <input
                                type="password"
                                name="password"
                                id="password"
                                class="form-control"
                                required>

                        </div>


                        <button
                            type="submit"
                            class="btn btn-primary w-100">

                            Register

                        </button>

                    </form>


                    <p class="text-center mt-3">

                        Already have an account?

                        <a href="login.jsp">
                            Login
                        </a>

                    </p>

                </div>

            </div>

        </div>

    </div>

</div>


<script
    src="https://code.jquery.com/jquery-3.7.1.min.js">
</script>

<script>

$(document).ready(function() {

    $("#registerForm").submit(function(event) {

        let password =
            $("#password").val();

        if (password.length < 6) {

            alert(
                "Password must contain at least 6 characters."
            );

            event.preventDefault();
        }

    });

});

</script>

</body>
</html>