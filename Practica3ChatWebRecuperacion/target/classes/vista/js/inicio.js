/**
 * Comprueba si el nombre es válido o no.
 * 
 * @returns true/false si el nombre es válido o no.
 */
function validarNickname() {
	var retorno = false;
	if (document.forms["formularioInicio"]["nickname"].value.trim().length == 0) {
		alert("Rellena el nickname por favor");
		limpiarNickname();
	} else {
		retorno = true;
	}
	return retorno;
}

/**
 * Limpia el input del nickname y lo enfoca.
 */
function limpiarNickname() {
	document.forms["formularioInicio"]["nickname"].value = "";
	document.forms["formularioInicio"]["nickname"].focus();
}