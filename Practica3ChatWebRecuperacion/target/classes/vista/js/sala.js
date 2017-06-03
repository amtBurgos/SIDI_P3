/**
 * Comprueba si el mensaje está lleno de espacios en blanco.
 * 
 * @returns true/false si el mensaje es válido o no.
 */
function validarMensaje() {
	var retorno = false;
	if (document.forms["FormularioEnviarMensaje"]["mensajeTextArea"].value
			.trim().length == 0) {
		alert("Rellena el mensaje para mandarlo.");
		document.forms["FormularioEnviarMensaje"]["mensajeTextArea"].value = "";
		document.forms["FormularioEnviarMensaje"]["mensajeTextArea"].focus();
	} else {
		retorno = true;
	}
	return retorno;
}