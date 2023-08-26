<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="style.css">
<link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>

<link rel="icon" type="image/png" href="img/logo.png">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<style>
.home {
    background-image: url('img/JBS_background.jpg');
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
}

.loading-screen {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 9999;
}

.loader {
    border: 8px solid #f3f3f3;
    border-top: 8px solid #3498db;
    border-radius: 50%;
    width: 50px;
    height: 50px;
    animation: spin 1s linear infinite;
}

@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}
</style>



<title>Miniworld-Development</title>
</head>
<body>

	<nav class="sidebar close">
        <header>
            <div class="image-text">
                <span class="image">
                    <img src="img/logo.jpg" alt="">
                </span>

                <div class="text logo-text">
                    <span class="name"></span>
                    <span class="profession">Miniworld</span>
                </div>
            </div>

            <i class='bx bx-chevron-right toggle'></i>
        </header>
<!-- cargar pantalla -->
	<div class="loading-screen">
    	<div class="loader"></div>
	</div>
<!-- - -->

        <div class="menu-bar">
            <div class="menu">

                <li class="search-box">
                    <i class='bx bx-search icon'></i>
                    <input type="text" placeholder="Buscar...">
                </li>

                <ul class="menu-links">
                    <li class="nav-link">
                        <a href="index.jsp">
                            <i class='bx bx-home-alt icon' ></i>
                            <span class="text nav-text">Inicio</span>
                        </a>
                    </li>

                    <li class="nav-link">
                        <a href="attorney.jsp">
                            <i class='bx bx-bar-chart-alt-2 icon' ></i>
                            <span class="text nav-text">Attorney</span>
                        </a>
                    </li>

                    <li class="nav-link">
                        <a href="manager.jsp">
                            <i class='bx bx-bell icon'></i>
                            <span class="text nav-text">Manager</span>
                        </a>
                    </li>

                    <li class="nav-link">
                        <a href="operation.jsp">
                            <i class='bx bx-pie-chart-alt icon' ></i>
                            <span class="text nav-text">Operation</span>
                        </a>
                    </li>

                    <li class="nav-link">
                        <a href="attorney2.jsp">
                            <i class='bx bx-heart icon' ></i>
                            <span class="text nav-text">Prueba</span>
                        </a>
                    </li>
                    
                    <li class="nav-link">
                        <a href="manager2.jsp">
                            <i class='bx bx-heart icon' ></i>
                            <span class="text nav-text">Prueba2</span>
                        </a>
                    </li>
                    <li class="nav-link">
                		<a href="Login.jsp">
                    		<i class='bx bx-log-in-circle icon'></i>
                    		<span class="text nav-text">Login</span>
                		</a>
            		</li>

                </ul>
            </div>
                
            </div>

    </nav>
    <section class="home">
        <div class="text">Jose Buenaventura Sepulveda</div>
    </section>

    <script src="script.js"></script>
				</body>

<script>
// Wait for the page to fully load
window.addEventListener('load', function() {
    // Simulate a delay of 3 seconds
    setTimeout(function() {
        // Hide the loading screen
        document.querySelector('.loading-screen').style.display = 'none';
    }, 1000); // 1000 milliseconds = 1 seconds
});
</script>

	<!-- Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>

</body>
</html>
