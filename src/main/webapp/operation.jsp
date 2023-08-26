<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
	
		<!-- Favicon -->
<link rel="icon" type="image/png" href="img/insignia.png">

<title>Miniworld</title>
</head>
<body>

	<jsp:include page="index.jsp"></jsp:include>

	<div class="container">

		<h1>CRUD DE OPERATION</h1>

		<!-- Card de datos de entrada -->
		<div class="card">
			<div class="card-header">Criterios de busqueda</div>
			<div class="card-body">
				<form method="post" action="courseBuscar">
					<div class="mb-3 row">
						<div class="col-sm-4">
							<input type="text" class="form-control" id="name_course"
								name="name_course" placeholder="Ingrese cursos">
						</div>
						
						<div class="col-sm-2">
							<button type="button" class="btn btn-primary mb-3" id="btnBuscar"
								name="btnBuscar">Buscar</button>
						</div>
						<div class="col-sm-2">
							<button type="button" class="btn btn-primary mb-3" id="btnNuevo"
								name="btnNuevo">Nuevo</button>
						</div>
						<div class="col-sm-2">
    						<button type="button" class="btn btn-primary mb-3" id="btnGenerarPDF" name="btnGenerarPDF">Generar PDF</button>
						</div>
						<div class="col-sm-2">
    						<button type="button" class="btn btn-primary mb-3" id="btnGenerarCSV" name="btnGenerarCSV">Generar CSV</button>
						</div>
						<div class="col-sm-2">
    						<button type="button" class="btn btn-primary mb-3" id="btnGenerarXLS" name="btnGenerarXLS">Generar XLS</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<br />

		<!-- Card de resultados -->
		<div class="card" id="divResultado">
			<div class="card-header">Resultado</div>
			<div class="card-body">
				<table class="table">
					<thead>
						<tr>
							<th>ID</th>
							<th>Nombre de Curso</th>

						</tr>
					</thead>
					<tbody id="detalleTabla">
					</tbody>
				</table>
			</div>
		</div>

		<!-- Formulario de edición de registro -->
		<div class="card" id="divRegistro" style="display: none;">
			<div class="card-header" id="tituloRegistro">{accion} EMPLEADO</div>
			<div class="card-body">
				<form>
					<input type="hidden" id="accion" name="accion">
					<div class="row mb-3">
						<label for="frmId" class="col-sm-2 col-form-label">ID</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="frmId"
								disabled="disabled" value="0">
						</div>
					</div>
					<div class="row mb-3">
						<label for="frmName_course" class="col-sm-2 col-form-label">Curso</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="frmName_course">
						</div>
					</div>
					
					<button type="button" class="btn btn-primary" id="btnProcesar">Procesar</button>
				</form>
			</div>
		</div>
	</div>

	<!-- Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		src="https://unpkg.com/xlsx/dist/xlsx.full.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>

	<script>
	
		// Constantes del CRUD
		const ACCION_NUEVO = "NUEVO";
		const ACCION_EDITAR = "EDITAR";
		const ACCION_ELIMINAR = "ELIMINAR";

		// Arreglo de registros
		let arreglo = [];
		
		// Acceder a los controles
		let btnBuscar = document.getElementById("btnBuscar");
		let btnNuevo = document.getElementById("btnNuevo");
		let btnProcesar = document.getElementById("btnProcesar");

		// Programar los controles
		btnBuscar.addEventListener("click", fnBtnBuscar);
		btnNuevo.addEventListener("click", fnBtnNuevo);
		btnProcesar.addEventListener("click", fnBtnProcesar);

		// Funcion fnEditar
		function fnEditar(id) {
			// Preparando el formulario
			document.getElementById("tituloRegistro").innerHTML = ACCION_EDITAR + " REGISTRO";
			document.getElementById("accion").value = ACCION_EDITAR;
			fnCargarForm(id);
			// Mostrar formulario
			document.getElementById("divResultado").style.display = "none";
			document.getElementById("divRegistro").style.display = "block";
		}

		// Funcion fnEliminar
	    function fnEliminar(id) {
	        // Preparando el formulario
	        document.getElementById("tituloRegistro").innerHTML = ACCION_ELIMINAR + " REGISTRO";
	        document.getElementById("accion").value = ACCION_ELIMINAR;
	        fnCargarForm(id);
	        // Mostrar formulario
	        document.getElementById("divResultado").style.display = "none";
	        document.getElementById("divRegistro").style.display = "block";
	    }

		// Funcion fnBtnProcesar
		function fnBtnProcesar() {
			// Preparar los datos
			let datos = "accion=" + document.getElementById("accion").value;
			datos += "&id=" + document.getElementById("frmId").value;
			datos += "&name_course="
					+ document.getElementById("frmName_course").value;
			
			// El envio con AJAX
			let xhr = new XMLHttpRequest();
			xhr.open("POST", "courseProcesar", true);
			xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
			xhr.onreadystatechange = function() {
				if (xhr.readyState === 4 && xhr.status === 200) {
					// La solicitud se completó correctamente
					console.log(xhr.responseText);
					alert(xhr.responseText);
				}
			};
			xhr.send(datos);
		}

		// Funcion fnBtnNuevo
		function fnBtnNuevo() {
			// Preparando el formulario
			document.getElementById("tituloRegistro").innerHTML = ACCION_NUEVO
					+ " REGISTRO";
			document.getElementById("accion").value = ACCION_NUEVO;
			// Mostrar formulario
			document.getElementById("divResultado").style.display = "none";
			document.getElementById("divRegistro").style.display = "block";
		}

		// Función fnBtnBuscar
		function fnBtnBuscar() {
			// Datos
			let name_course = document.getElementById("name_course").value;
			// Preparar la URL
			let url = "courseBuscar2?name_course=" + name_course;
			// La llama AJAX
			let xhttp = new XMLHttpRequest();
			xhttp.open("GET", url, true);
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					let respuesta = xhttp.responseText;
					arreglo = JSON.parse(respuesta);
					let detalleTabla = "";
					arreglo
							.forEach(function(item) {
								detalleTabla += "<tr>";
								detalleTabla += "<td>" + item.id + "</td>";
								detalleTabla += "<td>" + item.name_course
										+ "</td>";
								detalleTabla += "<td>";
								detalleTabla += "<a href='javascript:fnEditar(" + item.id + ");'>Editar</a> ";
								detalleTabla += "<a href='javascript:fnEliminar(" + item.id + ");'>Eliminar</a>";
								detalleTabla += "</td>";
								detalleTabla += "</tr>";
							});
					document.getElementById("detalleTabla").innerHTML = detalleTabla;
					// Mostrar formulario
					document.getElementById("divResultado").style.display = "block";
					document.getElementById("divRegistro").style.display = "none";
				}
			};
			xhttp.send();
		}
		
		
		
		
		
		
		
		
		// Función generarPDF
	
	// Acceder al botón de generar PDF
	let btnGenerarPDF = document.getElementById("btnGenerarPDF");

	// Programar el evento click del botón
	btnGenerarPDF.addEventListener("click", generarPDF);

	// Función para generar el PDF
	function generarPDF() {
	    // Crear un objeto FormData con los criterios de búsqueda
	    let formData = new FormData();
	    formData.append("name_courses", document.getElementById("frmName_course").value);

	    
	    
	    // Realizar una solicitud AJAX para generar el PDF
	    let xhr = new XMLHttpRequest();
	    xhr.open("POST", "GenerarPDFcourse", true);  // Reemplaza "generarPDF" con la URL que maneje la generación del PDF
	    xhr.setRequestHeader('Content-type', 'application/json');
	    xhr.responseType = "blob";

	    xhr.onload = function () {
	        if (xhr.status === 200) {
	            // Crear un objeto URL con la respuesta del servidor
	            let url = URL.createObjectURL(xhr.response);

	            // Crear un enlace y simular un clic para descargar el PDF
	            let a = document.createElement("a");
	            a.href = url;
	            a.download = "registros.pdf";
	            document.body.appendChild(a);
	            a.click();
	            document.body.removeChild(a);
	        }
	    };

	    xhr.send(formData);
	}
	
	
	
	
	// Función generarCSV
	
	// Acceder al botón de generar CSV
	let btnGenerarCSV = document.getElementById("btnGenerarCSV");

	// Programar el evento click del botón
	btnGenerarCSV.addEventListener("click", generarCSV);
	
	function generarCSV() {
	    // Crear un objeto FormData con los datos para el CSV
	    let formData = new FormData();
	    formData.append("name_course", document.getElementById("frmName_course").value);

	    // Realizar una solicitud AJAX para generar el CSV
	    let xhr = new XMLHttpRequest();
	    xhr.open("POST", "GenerarCSVcourse", true); // Reemplaza "GenerarCSVstudent" con la URL que maneje la generación del CSV
	    xhr.setRequestHeader('Content-type', 'application/json');
	    xhr.responseType = "blob";

	    xhr.onload = function () {
	        if (xhr.status === 200) {
	            // Crear un objeto URL con la respuesta del servidor
	            let url = URL.createObjectURL(xhr.response);

	            // Crear un enlace y simular un clic para descargar el CSV
	            let a = document.createElement("a");
	            a.href = url;
	            a.download = "registros.csv";
	            document.body.appendChild(a);
	            a.click();
	            document.body.removeChild(a);
	        }
	    };

	    xhr.send(formData);
	}
	
	
	
	
	
	// Función generarXLS

	// Acceder al botón de generar XLS
	let btnGenerarXLS = document.getElementById("btnGenerarXLS");

	// Programar el evento click del botón
	btnGenerarXLS.addEventListener("click", generarXLS);

	function generarXLS() {
	  // Crear un objeto FormData con los datos para el XLS
	  let formData = new FormData();
	  formData.append("name_course", document.getElementById("frmName_course").value);

	  // Realizar una solicitud AJAX para generar el XLS
	  let xhr = new XMLHttpRequest();
	  xhr.open("POST", "GenerarXLScourse", true); // Reemplaza "GenerarXLSstudent" con la URL que maneje la generación del XLS
	  xhr.setRequestHeader('Accept', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
	  xhr.responseType = "blob";

	  xhr.onload = function () {
	    if (xhr.status === 200) {
	      // Crear un objeto URL con la respuesta del servidor
	      let url = URL.createObjectURL(xhr.response);

	      // Crear un enlace y simular un clic para descargar el XLS
	      let a = document.createElement("a");
	      a.href = url;
	      a.download = "registros.xls";
	      document.body.appendChild(a);
	      a.click();
	      document.body.removeChild(a);
	    }
	  };

	  xhr.send(formData);
	}

		
		
		
		
		
		
		
		function fnCargarForm(id){
			arreglo.forEach(function(item) {
				if(item.id == id){
					document.getElementById("frmId").value = item.id;
					document.getElementById("frmName_course").value = item.name_course;

					//break;
				}
			});
		}
	</script>

</body>
