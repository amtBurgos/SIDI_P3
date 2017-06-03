package es.ubu.lsi.modelo;

/**
 * Clase que implementa un mensaje.
 * 
 * @author Andrés Miguel Terán
 * @author Francisco Saiz Güemes
 */
public class Mensaje {

	/**
	 * Nombre de usuario.
	 */
	private String nickname;

	/**
	 * Mensaje que escribe el usuario.
	 */
	private String mensaje;

	/**
	 * Hora en la que se envió el mensaje.
	 */
	private String hora;

	/**
	 * Construye un mensaje
	 * 
	 * @param nickname
	 *            nombre de usuario
	 * @param mensaje
	 *            mensaje
	 */
	public Mensaje(String nickname, String mensaje, String hora) {
		this.nickname = nickname;
		this.mensaje = mensaje;
		this.hora = hora;
	}

	/**
	 * Obtiene el remitente del mensaje.
	 * 
	 * @return nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Obtiene el mensaje.
	 * 
	 * @return mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Obtiene la hora en la que se envio el mensaje.
	 * 
	 * @return hora del mensaje
	 */
	public String getHora() {
		return hora;
	}
}
