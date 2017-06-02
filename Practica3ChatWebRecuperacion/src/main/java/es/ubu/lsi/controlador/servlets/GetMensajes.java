package es.ubu.lsi.controlador.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.ubu.lsi.controlador.Manager;
import es.ubu.lsi.modelo.Mensaje;
import es.ubu.lsi.modelo.Usuario;

/**
 * Obtiene los mensajes que han mandado los usuarios
 * 
 * @author Andrés Miguel Terán
 * @author Francisco Saiz Güemes
 */
@WebServlet(description = "Obtiene los mensajes que han mandado los usuarios", urlPatterns = { "/GetMensajes" })
public class GetMensajes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Manager singleton de la aplicacion.
	 */
	private Manager manager = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetMensajes() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Comprobamos si existe el manager en este servlet
		ServletContext context = getServletContext();
		if (manager == null) {
			if (context.getAttribute("manager") == null) {
				manager = Manager.getManagerSingleton();
				context.setAttribute("manager", manager);
			} else {
				manager = (Manager) context.getAttribute("manager");
			}
		}

		// Obtenemos el nombre de usuario pasado en la petición
		String nickname = request.getParameter("nickname");

		// Obtenemos la lista de mensajes
		LinkedList<Mensaje> mensajes = manager.getMensajes();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Los imprimimos
		for (Mensaje m : mensajes) {
			if (!m.getNickname().equals(nickname)) {
				out.print("<p>- " + m.getNickname() + ": " + m.getMensaje() + "</p><br>");
			} else {
				out.print("<p>- Yo: " + m.getMensaje() + "</p><br>");
			}
		}

		// Cerramos el writer
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
