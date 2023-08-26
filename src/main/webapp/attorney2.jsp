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
<link rel="icon" type="image/png" href="/img/logo.png">

<title>Miniworld</title>

<!-- Llamar automaticamente la lista de apoderado -->
<script>window.onload = function() {
	fnBtnBuscar();
	};
</script>
</head>
<body>


	<jsp:include page="index.jsp"></jsp:include>

	<div class="container">

		<h1>CRUD DE APODERADOS</h1>

		<!-- Card de datos de entrada -->
		<div class="card">
			<div class="card-header">Criterios de busqueda</div>
			<div class="card-body">
				<form method="post" action="attorneyBuscar">
					<div class="mb-3 row">
						<div class="col-sm-4">
							<input type="text" class="form-control" id="last_names"
								name="last_names" placeholder="Ingrese apellido">
						</div>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="names" name="names"
								placeholder="Ingrese nombre">
						</div>
						<div class="col-sm-2">
							<button type="button" class="btn btn-primary mb-3" id="btnBuscar"
								name="btnBuscar">Buscar</button>
						</div>
						<div class="col-sm-2">
							<button type="button" class="btn btn-outline-primary mb-3" id="btnNuevo"
								name="btnNuevo">Nuevo</button>
						</div>
						<div class="col-sm-2">
							<button type="button" class="btn btn-outline-primary mb-3" id="btnListar"
								name="btnListar">Listar Inactivos</button>
						</div>

						<div class="col-sm-2">
							<button type="button" class="btn btn-outline-primary mb-3"
								id="btnGenerarPDF" name="btnGenerarPDF">Generar PDF</button>
						</div>
						<div class="col-sm-2">
							<button type="button" class="btn btn-outline-primary mb-3"
								id="btnGenerarCSV" name="btnGenerarCSV">Generar CSV</button>
						</div>
						<div class="col-sm-2">
							<button type="button" class="btn btn-outline-primary mb-3"
								id="btnGenerarXLS" name="btnGenerarXLS">Generar XLS</button>
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
							<th>Nombre</th>
							<th>Apellido</th>
							<th>Tipo de documento</th>
							<th>Nº de documento</th>
							<th>Email</th>
							<th>Celular</th>
							
						</tr>
					</thead>
					<tbody id="detalleTabla">
					</tbody>
				</table>
			</div>
		</div>

		<!-- Formulario de edición de registro -->
		<div class="card" id="divRegistro" style="display: none;">
			<div class="card-header" id="tituloRegistro">{accion} ENCARGADO</div>
			<div class="card-body">
				<form class="needs-validation" id="managerForm" novalidate>
					<input type="hidden" id="accion" name="accion">
					<div class="row mb-3 ">
						<label for="frmId" class="col-sm-2 col-form-label"></label>
						<div class="col-md-6 mb-3 d-none">
							<input type="text" class="form-control" id="frmId"
								disabled="disabled" value="0">
						</div>
					</div>
					<!-- Validacion de Nombre-->
					<div class="row mb-3">
						<label for="frmNames" class="col-sm-2 col-form-label">Nombre</label>
						<div class="col-md-6 mb-3">
							<input type="text" class="form-control" id="frmNames"
								name="frmNames" required pattern="[A-Za-záéíóúÁÉÍÓÚ\s]+">
							<div class="valid-feedback">Correcto</div>
							<div class="invalid-feedback">Es necesario escribir solo
								letras en el nombre</div>
						</div>
					</div>

					<script>
						// Obtener el campo de entrada de nombres
						var nombresInput = document.getElementById('frmNames');

						// Agregar un event listener para el evento 'input'
						nombresInput.addEventListener('input', function(event) {
							// Obtener el valor actual del campo de nombres
							var nombres = event.target.value;

							// Expresión regular para validar solo letras y espacios(incluyendo tildes)
                            var regex = /^[A-Za-z\u00C0-\u00FF\s]+$/;

							// Validar el valor ingresado
							if (nombres === '') {
								// El campo está vacío
								nombresInput.classList.remove('is-valid');
								nombresInput.classList.remove('is-invalid');
							} else if (regex.test(nombres)) {
								// El valor es válido
								nombresInput.classList.remove('is-invalid');
								nombresInput.classList.add('is-valid');
							} else {
								// El valor es inválido
								nombresInput.classList.remove('is-valid');
								nombresInput.classList.add('is-invalid');
							}
						});
					</script>

					<!-- Validacion de Apellidos -->
					<div class="row mb-3">
						<label for="frmLast_names" class="col-sm-2 col-form-label">Apellido</label>
						<div class="col-md-6 mb-3">
							<input type="text" class="form-control" id="frmLast_names"
								name="frmLast_names" required pattern="[A-Za-záéíóúÁÉÍÓÚ\s]+">
							<div class="valid-feedback">Correcto</div>
							<div class="invalid-feedback">Es necesario escribir solo
								letras en el apellido</div>
						</div>
					</div>

					<script>
						// Obtener el campo de entrada de apellidos
						var apellidosInput = document
								.getElementById('frmLast_names');

						// Agregar un event listener para el evento 'input'
						apellidosInput.addEventListener('input',
								function(event) {
									// Obtener el valor actual del campo de apellidos
									var apellidos = event.target.value;

									// Expresión regular para validar solo letras y espacios(incluyendo tildes)
		                            var regex = /^[A-Za-z\u00C0-\u00FF\s]+$/;

									// Validar el valor ingresado
									if (apellidos === '') {
										// El campo está vacío
										apellidosInput.classList
												.remove('is-valid');
										apellidosInput.classList
												.remove('is-invalid');
									} else if (regex.test(apellidos)) {
										// El valor es válido
										apellidosInput.classList
												.remove('is-invalid');
										apellidosInput.classList
												.add('is-valid');
									} else {
										// El valor es inválido
										apellidosInput.classList
												.remove('is-valid');
										apellidosInput.classList
												.add('is-invalid');
									}
								});
					</script>


					<!-- Validacion de Tipo de documento -->
					<div class="row mb-3">
						<label for="frmDocument_type" class="col-sm-2 col-form-label">Tipo
							de documento</label>
						<div class="col-md-6 mb-3">
							<select class="form-control" id="frmDocument_type"
								name="frmDocument_type" required>
								<option value="">Seleccionar Identificacion</option>
								<option value="DNI">DNI</option>
								<option value="CE">CE</option>
								<!-- Agrega más opciones si es necesario -->
							</select>
							<div class="valid-feedback">Todo bien</div>
							<div class="invalid-feedback">Es necesario seleccionar una
								identificacion</div>
						</div>
					</div>
					<script>
						// Obtener el campo de entrada de tipo de documento
						var tipoDocumentoInput = document
								.getElementById('frmDocument_type');

						// Agregar un event listener para el evento 'change'
						tipoDocumentoInput.addEventListener('change',
								function(event) {
									// Obtener el valor seleccionado del campo de tipo de documento
									var tipoDocumento = event.target.value;

									// Validar el valor seleccionado
									if (tipoDocumento === '') {
										// No se ha seleccionado un tipo de documento válido
										tipoDocumentoInput.classList
												.remove('is-valid');
										tipoDocumentoInput.classList
												.add('is-invalid');
									} else {
										// Se ha seleccionado un tipo de documento válido
										tipoDocumentoInput.classList
												.remove('is-invalid');
										tipoDocumentoInput.classList
												.add('is-valid');
									}
								});
					</script>
					<!-- Validacion de Numero de DNI y CE -->
					<div class="row mb-3">
						<label for="frmDocument_number" class="col-sm-2 col-form-label">Número
							de documento</label>
						<div class="col-md-6 mb-3">
							<input type="text" class="form-control" id="frmDocument_number"
								name="frmDocument_number" required>
							<div class="valid-feedback">Todo bien</div>
							<div class="invalid-feedback">Solo puedes ingresar números
								en el DNI/CE</div>
						</div>
					</div>

					<script>
						// Obtener los elementos del formulario
						var documentTypeInput = document
								.getElementById('frmDocument_type');
						var documentNumberInput = document
								.getElementById('frmDocument_number');

						// Agregar event listeners para los eventos 'change' e 'input'
						documentTypeInput.addEventListener('change', function(
								event) {
							// Restablecer el campo de número de documento al cambiar el tipo de documento
							documentNumberInput.value = '';
							documentNumberInput.classList.remove('is-valid');
							documentNumberInput.classList.remove('is-invalid');
							documentNumberInput.removeAttribute('maxlength'); // Quitar la limitación de longitud
							if (documentTypeInput.value === 'DNI') {
								documentNumberInput.setAttribute('maxlength',
										'8'); // Establecer la longitud máxima a 8 para DNI
							} else if (documentTypeInput.value === 'CE') {
								documentNumberInput.setAttribute('maxlength',
										'20'); // Establecer la longitud máxima a 20 para CE
							}
						});

						documentNumberInput
								.addEventListener(
										'input',
										function(event) {
											// Obtener el valor actual del campo de número de documento
											var documentNumber = event.target.value;

											// Obtener el valor seleccionado del campo de tipo de documento
											var documentType = documentTypeInput.value;

											// Validar el valor ingresado según el tipo de documento seleccionado
											if (documentNumber === '') {
												// El campo está vacío
												documentNumberInput.classList
														.remove('is-valid');
												documentNumberInput.classList
														.remove('is-invalid');
											} else if (documentType === 'DNI'
													&& /^\d{1,8}$/
															.test(documentNumber)) {
												// El valor es válido para DNI (8 dígitos numéricos)
												documentNumberInput.classList
														.remove('is-invalid');
												documentNumberInput.classList
														.add('is-valid');
											} else if (documentType === 'CE'
													&& /^\d{1,20}$/
															.test(documentNumber)) {
												// El valor es válido para CE (20 dígitos numéricos)
												documentNumberInput.classList
														.remove('is-invalid');
												documentNumberInput.classList
														.add('is-valid');
											} else {
												// El valor es inválido
												documentNumberInput.classList
														.remove('is-valid');
												documentNumberInput.classList
														.add('is-invalid');
											}
										});
					</script>

					<!-- Validacion de Email -->
					<div class="row mb-3">
						<label for="frmEmail" class="col-sm-2 col-form-label">Email</label>
						<div class="col-md-6 mb-3">
							<input type="text" class="form-control" id="frmEmail"
								name="frmEmail" required
								pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$">
							<div class="valid-feedback">Todo bien</div>
							<div class="invalid-feedback">Es necesario el Correo
								Electrónico (ejemplo@dominio.com)</div>
						</div>
					</div>

					<script>
						// Obtener el campo de entrada de email
						var emailInput = document.getElementById('frmEmail');

						// Agregar un event listener para el evento 'input'
						emailInput
								.addEventListener(
										'input',
										function(event) {
											// Obtener el valor actual del campo de email
											var email = event.target.value;

											// Expresión regular para validar el formato de email
											var regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

											// Validar el valor ingresado
											if (email === '') {
												// El campo está vacío
												emailInput.classList
														.remove('is-valid');
												emailInput.classList
														.remove('is-invalid');
											} else if (regex.test(email)) {
												// El valor es válido
												emailInput.classList
														.remove('is-invalid');
												emailInput.classList
														.add('is-valid');
											} else {
												// El valor es inválido
												emailInput.classList
														.remove('is-valid');
												emailInput.classList
														.add('is-invalid');
											}
										});
					</script>


					<!-- Validacion de Celular -->
					<div class="row mb-3">
						<label for="frmCell_phone" class="col-sm-2 col-form-label">Celular</label>
						<div class="col-md-6 mb-3">
							<input type="text" class="form-control" id="frmCell_phone"
								name="frmCell_phone" required pattern="[0-9]{9}" maxlength="9">
							<div class="valid-feedback">Todo bien</div>
							<div class="invalid-feedback">Es necesario el número de
								celular (9 dígitos)</div>
						</div>
					</div>

					<script>
						// Obtener el campo de entrada de celular
						var celularInput = document
								.getElementById('frmCell_phone');

						// Agregar un event listener para el evento 'input'
						celularInput.addEventListener('input', function(event) {
							// Obtener el valor actual del campo de celular
							var celular = event.target.value;

							// Expresión regular para validar exactamente 9 dígitos numéricos
							var regex = /^[0-9]{9}$/;

							// Validar el valor ingresado
							if (celular === '') {
								// El campo está vacío
								celularInput.classList.remove('is-valid');
								celularInput.classList.remove('is-invalid');
							} else if (regex.test(celular)) {
								// El valor es válido
								celularInput.classList.remove('is-invalid');
								celularInput.classList.add('is-valid');
							} else {
								// El valor es inválido
								celularInput.classList.remove('is-valid');
								celularInput.classList.add('is-invalid');
							}
						});
					</script>

					<div class="row mb-3 ">
						<label for="frmStatus" class="col-sm-2 col-form-label"></label>
						<div class="col-md-6 mb-3 d-none">
							<input type="text" class="form-control" id="frmStatus"
								disabled="disabled" value="A">
						</div>
					</div>
					<div class="botones-container">
						<button onclick="fnCancelar()" class="btn btn-outline-secondary">Cancelar</button>
						<button type="button" class="btn btn-outline-primary"
							id="btnProcesar">Procesar</button>
					</div>
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
			document.getElementById("tituloRegistro").innerHTML = ACCION_EDITAR
					+ " REGISTRO";
			document.getElementById("accion").value = ACCION_EDITAR;
			fnCargarForm(id);
			// Mostrar formulario
			document.getElementById("divResultado").style.display = "none";
			document.getElementById("divRegistro").style.display = "block";
		}

		// Función fnActualizarTabla
		function fnActualizarTabla() {
			let detalleTabla = "";

			arreglo
					.forEach(function(item) {
						detalleTabla += "<tr>";
						detalleTabla += "<td>" + item.id + "</td>";
						detalleTabla += "<td>" + item.names + "</td>";
						detalleTabla += "<td>" + item.last_names + "</td>";
						detalleTabla += "<td>" + item.document_type + "</td>";
						detalleTabla += "<td>" + item.document_number + "</td>";
						detalleTabla += "<td>" + item.email + "</td>";
						detalleTabla += "<td>" + item.cell_phone + "</td>";
						detalleTabla += "<td>" + item.status + "</td>";
						detalleTabla += "<td>";
						detalleTabla += "<a href='javascript:fnEditar("	+ item.id + ");'>Editar</a> ";
						detalleTabla += "<a href='javascript:fnEliminar("+ item.id + ");'>Eliminar</a> ";
						detalleTabla += "<a href='javascript:toggleStatus(" + item.id + ");' class='btn btn-outline-primary'>Activar/Desactivar</a> ";						
						detalleTabla += "</td>";
						detalleTabla += "</tr>";
					});

			document.getElementById("detalleTabla").innerHTML = detalleTabla;
		}

		// Funcion fnEliminar
		function fnEliminar(id) {
			// Preparando el formulario
			document.getElementById("tituloRegistro").innerHTML = ACCION_ELIMINAR
					+ " REGISTRO";
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
			datos += "&last_names="
					+ document.getElementById("frmLast_names").value;
			datos += "&names=" + document.getElementById("frmNames").value;
			datos += "&document_type="
					+ document.getElementById("frmDocument_type").value;
			datos += "&email=" + document.getElementById("frmEmail").value;
			datos += "&document_number="
					+ document.getElementById("frmDocument_number").value;
			datos += "&cell_phone="
					+ document.getElementById("frmCell_phone").value;
			datos += "&status=" + document.getElementById("frmStatus").value;

			// El envio con AJAX
			let xhr = new XMLHttpRequest();
			xhr.open("POST", "attorneyProcesar", true);
			xhr.setRequestHeader('Content-type',
					'application/x-www-form-urlencoded');
			xhr.onreadystatechange = function() {
				if (xhr.readyState === 4 && xhr.status === 200) {
					// La solicitud se completó correctamente
					console.log(xhr.responseText);
					// Interpretar la respuesta JSON
		            var response = JSON.parse(xhr.responseText);
		            alert(response.message);
		         // Redireccionar manualmente
		            window.location.href = "attorneyBuscar";
		            
		            // Limpiar los datos después de procesar
		            limpiarDatos();
				}
			};
			xhr.send(datos);
		}

		function fnCancelar() {
			  // Realizar la cancelación de los datos

			  // Redirigir a la página de búsqueda
			  window.location.href = "attorneyBuscar";
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
			let last_names = document.getElementById("last_names").value;
			let names = document.getElementById("names").value;
			// Preparar la URL
			let url = "attorneyBuscar2?last_names=" + last_names + "&names="
					+ names;
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
								if (item.status === "A") { // Filtrar por estado "A"
									detalleTabla += "<tr>";
									detalleTabla += "<td>" + item.id + "</td>";
									detalleTabla += "<td>" + item.names
											+ "</td>";
									detalleTabla += "<td>" + item.last_names
											+ "</td>";
									detalleTabla += "<td>" + item.document_type
											+ "</td>";
									detalleTabla += "<td>"
											+ item.document_number + "</td>";
									detalleTabla += "<td>" + item.email
											+ "</td>";											
									detalleTabla += "<td>" + item.cell_phone										
											+ "</td>";
									detalleTabla += "<td>";
									detalleTabla += "<a href='javascript:fnEditar(" + item.id + ");'>Editar</a> ";

									detalleTabla += "<button onclick='toggleStatus(" + item.id + ");' class='btn btn-primary'>Desactivar</button>";
									detalleTabla += "</td>";

								}
							});
					document.getElementById("detalleTabla").innerHTML = detalleTabla;
					// Mostrar formulario
					document.getElementById("divResultado").style.display = "block";
					document.getElementById("divRegistro").style.display = "none";
				}
			};
			xhttp.send();
		}



		function toggleStatus(attorneyId) {
			if (confirm("¿Estás seguro de que deseas activar/desactivar este apoderado?")) {
				let xhr = new XMLHttpRequest();
				xhr.open("POST", "attorneyToggleStatus", true);
				xhr.setRequestHeader('Content-type',
						'application/x-www-form-urlencoded');
				xhr.onreadystatechange = function() {
					if (xhr.readyState === 4 && xhr.status === 200) {
						let response = JSON.parse(xhr.responseText);
						alert(response.message);
						// Aquí puedes realizar cualquier otra acción después de cambiar el estado del apoderado
					}
				};
				xhr.send("attorneyId=" + attorneyId);
			}
		}

		// Acceder al botón "btnListar"
		let btnListar = document.getElementById("btnListar");

		// Programar el control
		btnListar.addEventListener("click", fnBtnListar);

		// Función fnBtnListar
		function fnBtnListar() {
			// Datos
			let last_names = document.getElementById("last_names").value;
			let names = document.getElementById("names").value;
			// Preparar la URL
			let url = "attorneyBuscar2?last_names=" + last_names + "&names="
					+ names;
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
								if (item.status === "I") { // Filtrar por estado "I"
									detalleTabla += "<tr>";
									detalleTabla += "<td>" + item.id + "</td>";
									detalleTabla += "<td>" + item.last_names
											+ "</td>";
									detalleTabla += "<td>" + item.names
											+ "</td>";
									detalleTabla += "<td>" + item.document_type
											+ "</td>";
									detalleTabla += "<td>" + item.email
											+ "</td>";
									detalleTabla += "<td>"
											+ item.document_number + "</td>";
									detalleTabla += "<td>" + item.cell_phone
											+ "</td>";
									detalleTabla += "<td>" + item.status
											+ "</td>";
									detalleTabla += "<td>";
									detalleTabla += "<a href='javascript:fnEditar("
											+ item.id + ");'>Editar</a> ";

									detalleTabla += "<a href='javascript:toggleStatus("
											+ item.id
											+ ");' class='btn btn-primary'>Activar</a> ";
									detalleTabla += "</td>";
									detalleTabla += "</tr>";
								}
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
			formData.append("names", document.getElementById("frmNames").value);
			formData.append("last_names", document
					.getElementById("frmLast_names").value);
			formData.append("document_type", document
					.getElementById("frmDocument_type").value);
			formData.append("document_number", document
					.getElementById("frmDocument_number").value);
			formData.append("email", document.getElementById("frmEmail").value);
			formData.append("cell_phone", document
					.getElementById("frmCell_phone").value);
			formData.append("status",
					document.getElementById("frmStatus").value);

			// Realizar una solicitud AJAX para generar el PDF
			let xhr = new XMLHttpRequest();
			xhr.open("POST", "GenerarPDFattorney", true); // Reemplaza "generarPDF" con la URL que maneje la generación del PDF
			xhr.setRequestHeader('Content-type', 'application/json');
			xhr.responseType = "blob";

			xhr.onload = function() {
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
			formData.append("last_names", document
					.getElementById("frmLast_names").value);
			formData.append("names", document.getElementById("frmNames").value);
			formData.append("document_type", document
					.getElementById("frmDocument_type").value);
			formData.append("email", document.getElementById("frmEmail").value);
			formData.append("document_number", document
					.getElementById("frmDocument_number").value);
			formData.append("cell_phone", document
					.getElementById("frmCell_phone").value);
			formData.append("status",
					document.getElementById("frmStatus").value);

			// Realizar una solicitud AJAX para generar el CSV
			let xhr = new XMLHttpRequest();
			xhr.open("POST", "GenerarCSVattorney", true); // Reemplaza "GenerarCSVattorney" con la URL que maneje la generación del CSV
			xhr.setRequestHeader('Content-type', 'application/json');
			xhr.responseType = "blob";

			xhr.onload = function() {
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
			formData.append("names", document.getElementById("frmNames").value);
			formData.append("last_names", document.getElementById("frmLast_names").value);
			formData.append("document_type", document.getElementById("frmDocument_type").value);
			formData.append("document_number", document.getElementById("frmDocument_number").value);
			formData.append("email", document.getElementById("frmEmail").value);
			formData.append("cell_phone", document.getElementById("frmCell_phone").value);
			formData.append("status",document.getElementById("frmStatus").value);

			// Realizar una solicitud AJAX para generar el XLSX
			let xhr = new XMLHttpRequest();
			xhr.open("POST", "GenerarXLSattorney", true); // Reemplaza "GenerarXLSattorney" con la URL que maneje la generación del XLS
			xhr.setRequestHeader('Content-Type', 'application/json');
			xhr.responseType = "blob";

			xhr.onload = function() {
				if (xhr.status === 200) {
					// Crear un objeto URL con la respuesta del servidor
					let url = URL.createObjectURL(xhr.response);

					// Crear un enlace y simular un clic para descargar el XLS
					let a = document.createElement("a");
					a.href = url;
					a.download = "apoderados.xlsx";
					document.body.appendChild(a);
					a.click();
					document.body.removeChild(a);
				}
			};

			xhr.send(JSON.stringify(Object.fromEntries(formData)));

		}

		function fnCargarForm(id) {
			arreglo
					.forEach(function(item) {
						if (item.id == id) {
							document.getElementById("frmId").value = item.id;
							document.getElementById("frmLast_names").value = item.last_names;
							document.getElementById("frmNames").value = item.names;
							document.getElementById("frmDocument_type").value = item.document_type;
							document.getElementById("frmEmail").value = item.email;
							document.getElementById("frmDocument_number").value = item.document_number;
							document.getElementById("frmCell_phone").value = item.cell_phone;
							document.getElementById("frmStatus").value = item.status;
							//break;
						}
					});
		}
	</script>

</body>