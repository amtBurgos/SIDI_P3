<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="es.ubu.lsi.controlador.Manager"%>
<%@page import="java.lang.String"%>

<jsp:useBean id="nickname" scope="session" class="java.lang.String" />
<jsp:useBean id="manager" type="es.ubu.lsi.controlador.Manager"
	scope="application" />

<%-- <jsp:useBean id="manager" scope="session" class="es.ubu.lsi.Manager" />
 --%>
<%
	//Manager manager = Manager.getManager();
	nickname = (String) session.getAttribute("nickname");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description"
	content="Sistemas Distribuidos - Practica 3 Segunda Convocatoria - 09/06/2017">
<meta name="author" content="Andrés Miguel Terán">
<meta name="author" content="Francisco Saiz Güemes">
<link rel="stylesheet" href="./css/sala.css">
<script src="./js/sala.js"></script>
<script type="text/javascript">
	//establece el tiempo a 5 seg.
	setInterval(refreshIframe, 12000);

	/**
	 * Recargamos los dos iframes cada 5 segundos
	 */
	function refreshIframe() { // recarga el iframe de la página
		frames[0].location.reload(true);
		frames[1].location.reload(true);
	}
</script>
<title>Chat Room <%
	out.print(nickname);
%>
</title>
</head>
<body>
	<h1>
		Chat Room
		<%
		// Ponemos el nombre del usuario
		out.print(nickname);
	%>
	</h1>
	<!-- Hiperenlace de logout -->
	<a href="../Logout">Logout</a>
	<br>
	<!-- Estructura de la página -->
	<div id="divMensajeUsuario">
		<!-- Div para escribir los mensajes -->
		<div id="divEscribirMensaje">
			<form action="../SendMensaje" method="post"
				name="FormularioEnviarMensaje" onsubmit="return validarMensaje()">
				<h4>Mensaje</h4>
				<textarea id="textArea" rows="8" cols="45" name="mensaje" autofocus="autofocus"
					required="required"></textarea>

				<div id="botonesMensaje">
					<input type="reset" value="Borrar"> <input type="submit"
						value="Enviar">
				</div>
			</form>
		</div>

		<!-- Div para ver los usuarios conectados -->
		<div id="divUsuariosConectados">
			<h4>Usuarios conectados</h4>
			<iframe src="../GetUsuarios?nickname=<%out.print(nickname);%>"
				width="240" height="120"> </iframe>
		</div>
	</div>
	<!-- Div para leer los mensajes que llegan -->
	<div id="divVerMensajes">
		<h4>Mensajes Recibidos</h4>
		<iframe src="../GetMensajes?nickname=<%out.print(nickname);%>"
			width="600" height="350"></iframe>
	</div>
	</div>
</body>
</html>