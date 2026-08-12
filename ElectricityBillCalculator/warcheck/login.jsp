<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>Login - Electricity Bill Calculator</title>

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

                        Electricity Bill Calculator

                    </h2>


                    <% if (request.getParameter("error") != null) { %>

                        <div class="alert alert-danger">

                            <%= request.getParameter("error") %>

                        </div>

                    <% } %>


                    <% if (request.getParameter("message") != null) { %>

                        <div class="alert alert-success">

                            <%= request.getParameter("message") %>

                        </div>

                    <% } %>


                    <form action="login"
                          method="post">

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
                                class="form-control"
                                required>

                        </div>


                        <button
                            type="submit"
                            class="btn btn-primary w-100">

                            Login

                        </button>

                    </form>


                    <p class="text-center mt-3">

                        Don't have an account?

                        <a href="register.jsp">
                            Register
                        </a>

                    </p>

                </div>

            </div>

        </div>

    </div>

</div>

</body>
</html>