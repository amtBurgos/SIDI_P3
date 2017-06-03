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
 * Realiza la desconexion de un usuario.
 * 
 * @author Andrés Miguel Terán
 * @author Francisco Saiz Güemes
 */
@WebServlet(description = "Realiza la desconexion de un usuario", urlPatterns = { "/Logout" })
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Manager singleton de la aplicacion.
	 */
	private Manager manager = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Logout() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Comprobamos si existe el manager en este servlet y si no lo cogemos
		// del contexto, porque el CheckUsuario lo habrá dejado es su primera
		// ejecución
		if (manager == null) {
			ServletContext context = getServletContext();
			manager = (Manager) context.getAttribute("manager");
		}

		// Obtenemos la sesion
		HttpSession session = request.getSession();
		String nickname = (String) session.getAttribute("nickname");

		// Borramos el usuario e invalidamos su session
		if (manager.eliminarUsuario(nickname)) {
			session.invalidate();
			response.sendRedirect("./index.html");
		}
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
