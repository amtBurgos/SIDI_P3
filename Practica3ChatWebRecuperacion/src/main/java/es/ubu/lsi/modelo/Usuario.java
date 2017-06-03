package es.ubu.lsi.modelo;

/**
 * Clase que implementa un usuario.
 * 
 * @author Andrés Miguel Terán
 * @author Francisco Saiz Güemes
 */
public class Usuario {

	/**
	 * Nombre de usuario.
	 */
	private String nickname;

	/**
	 * Hora a la que se conectó el usuario.
	 */
	private String hora;

	/**
	 * Construye un usuario
	 * 
	 * @param nickname
	 *            nombre de usuario
	 * @param hora
	 *            hora de conexion
	 */
	public Usuario(String nickname, String hora) {
		this.nickname = nickname;
		this.hora = hora;
	}

	/**
	 * Devuelve el nombre de usuario.
	 * 
	 * @return nickname nombre de usuario
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Devuelve la hora de conexion de un usuario.
	 * 
	 * @return hora de conexion
	 */
	public String getHora() {
		return hora;
	}

	/**
	 * Convierte un usuario en cadena.
	 */
	@Override
	public String toString() {
		return nickname + " " + hora;
	}
}
