package es.ubu.lsi.controlador.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.ubu.lsi.controlador.Manager;

/**
 * Comprueba si existe un usuario.
 * 
 * @author Andrés Miguel Terán
 * @author Francisco Saiz Güemes
 */
@WebServlet(description = "Comprueba si ya existe un usuario", urlPatterns = { "/CheckUsuario" })
public class CheckUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Tipos de errores que se darán en este servlet.
	 */
	private enum ERROR {
		/**
		 * Error en el servidor al añadir un usuario.
		 */
		ERROR_SERVIDOR,
		/**
		 * Error porque ya existe el usuario a añadir.
		 */
		ERROR_LOGIN;
	}

	/**
	 * Manager singleton de la aplicacion.
	 */
	private Manager manager = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckUsuario() {
		super();
		manager = new Manager();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Cogemos el nickname
		String nickname = request.getParameter("nickname");

		// Comprobamos si existe el manager en este servlet
		ServletContext context = getServletContext();
		//if (manager == null) {
			//if (context.getAttribute("manager") == null) {
				//manager = Manager.getManagerSingleton();
				// Lo dejamos en el contexto para los demás servlets
						
				context.setAttribute("manager", manager);
		//	} else {
				//manager = (Manager) context.getAttribute("manager");
			//}
		//}

		// Si hemos salido de la sala pulsando el hiperenlace del indice, y
		// tenemos la sesion abierta pues dejamos entrar al usuario si vuelve
		// con el mismo nickname, y si ha puesto otro nickname cerramos la
		// anterior sesion y creamos un usuario nuevo
		HttpSession ses = request.getSession(false);
		if (ses != null) {
			String viejoNickname = (String) ses.getAttribute("nickname");
			// Vemos si ha introducido el mismo nombre
			if (viejoNickname.toLowerCase().equals(nickname.toLowerCase())) {
				response.sendRedirect("./vista/Sala.jsp");
			} else {
				if (!manager.existeUsuario(nickname)) {
					// Añadimos al usuario
					boolean added = manager.anadirUsuario(nickname, manager.getHora());
					if (added == true) {
						// Quitamos el viejo de la aplicacion
						manager.eliminarUsuario(viejoNickname);

						// Ponemos los nuevos valores
						ses.setAttribute("nickname", nickname);
						response.sendRedirect("./vista/Sala.jsp");
					} else {
						// Si hay algun fallo al añadir mostramos el error
						response.setContentType("text/html");
						PrintWriter out = response.getWriter();
						out.print(getErrorPage(ERROR.ERROR_SERVIDOR, nickname));
						out.close();
					}
				} else {
					// Si el nuevo con el que quiere entrar ya existe mostramos
					// error
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.print(getErrorPage(ERROR.ERROR_LOGIN, nickname));
					out.close();
				}
			}
		} else {
			// Si no hay ninguna session
			// Comprobamos que el usuario que intenta entrar no exista ya
			if (manager.existeUsuario(nickname)) {
				// Si existe informamos de que ya existe y los mandamos al index
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.print(getErrorPage(ERROR.ERROR_LOGIN, nickname));
				out.close();
			} else {
				// Si no existe creamos una sesion y lo redirigimos a la sala
				HttpSession session = request.getSession(true);
				session.setAttribute("nickname", nickname);

				// Añadimos al usuario
				boolean anadido = manager.anadirUsuario(nickname, manager.getHora());

				if (anadido == true) {
					// Redireccionamos a la sala
					response.sendRedirect("./vista/Sala.jsp");
				} else {
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.print(getErrorPage(ERROR.ERROR_SERVIDOR, nickname));
					out.close();
				}
			}
		}
	}

	/**
	 * Pagina de error dependiendo si el error es que ya existe el usuario o que
	 * un fallo de otro tipo en el servidor.
	 * 
	 * @param error
	 *            tipo de error
	 * @param nickname
	 *            nombre de usuario
	 * @return pagina de respuesta
	 * @throws IOException
	 *             IOException
	 */
	private String getErrorPage(ERROR error, String nickname) throws IOException {
		String respuesta = "";
		respuesta += "<html>";
		respuesta += "<head>";
		respuesta += "<title>Error</title>";
		respuesta += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">";
		respuesta += "<meta http-equiv=\"refresh\" content=\"5;url=index.html\" />";
		respuesta += "<link rel=\"stylesheet\" href=\"./vista/css/comun.css\">";
		respuesta += "</head>";
		respuesta += "<body>";
		if (error == ERROR.ERROR_SERVIDOR) {
			respuesta += "<h1>Error en el servidor</h1>";
			respuesta += "<p style=\"color: red;\">El nombre de usuario '" + nickname
					+ "' no se ha podido registrar en el servidor.</p>";
		} else if (error == ERROR.ERROR_LOGIN) {
			respuesta += "<h1>Error al hacer login</h1>";
			respuesta += "<p style=\"color: red;\">El nombre de usuario '" + nickname + "' ya existe.</p>";
		}
		respuesta += "<p>Se redirigirá a la página principal en 5 segundos</p>";
		respuesta += "</body>";
		respuesta += "</html>";
		return respuesta;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
