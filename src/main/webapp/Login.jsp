<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }

        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }

        #login-form {
            background-color: rgba(255, 255, 255, 0.9);
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
        }

        .login-image {
            max-width: 100px; /* Adjust the size as needed */
            margin-bottom: 20px;
            text-align: center;
        }

        .login-image img {
            max-width: 100%;
            height: auto;
        }

        .alert {
            margin-top: 10px;
            display: none;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="login-image">
            <img src="img/logo.png" alt="Logo">
        </div>
        <form id="login-form">
            <div class="mb-3">
                <label for="username" class="form-label">Username:</label>
                <input type="text" id="username" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password:</label>
                <input type="password" id="password" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email:</label>
                <input type="email" id="email" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary">Login</button>
        </form>
        <div class="alert alert-danger mt-3" role="alert" id="error-message" style="display: none;">Rellene todos los campos.</div>
        <div class="alert alert-success mt-3" role="alert" id="success-message" style="display: none;">Inicio de sesión exitoso. Redirigiendo a la página principal...</div>
    </div>

    <script>
        const form = document.getElementById('login-form');
        const errorMessage = document.getElementById('error-message');
        const successMessage = document.getElementById('success-message');

        form.addEventListener('submit', function(event) {
            event.preventDefault();

            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            const email = document.getElementById('email').value;

            if (!username || !password || !email) {
                errorMessage.style.display = 'block';
                successMessage.style.display = 'none';
            } else {
                errorMessage.style.display = 'none';
                successMessage.style.display = 'block';
                setTimeout(() => {
                    window.location.href = 'index.jsp'; // Replace with your actual main page URL
                }, 3000); // Redirect after 3 seconds
            }
        });
    </script>
</body>
</html>
