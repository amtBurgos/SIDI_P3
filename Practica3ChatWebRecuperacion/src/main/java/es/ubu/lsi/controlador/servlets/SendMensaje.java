package es.ubu.lsi.controlador.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.ubu.lsi.controlador.Manager;

/**
 * Envia un mensaje para que el servidor pueda guardarlo.
 * 
 * @author Andrés Miguel Terán
 * @author Francisco Saiz Güemes
 */
@WebServlet(description = "Envia un mensaje", urlPatterns = { "/SendMensaje" })
public class SendMensaje extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Manager singleton de la aplicacion.
	 */
	private Manager manager = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendMensaje() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext context = getServletContext();
		// Comprobamos si existe el manager en este servlet
		if (manager == null) {
			if (context.getAttribute("manager") == null) {
				manager = Manager.getManagerSingleton();
				context.setAttribute("manager", manager);
			} else {
				manager = (Manager) context.getAttribute("manager");
			}
		}

		// Añadimos el mensaje que quiere enviar el usuario
		String nickname = request.getParameter("nickname");
		String mensaje = request.getParameter("mensaje");
		manager.anadirMensaje(nickname, mensaje);
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
