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
import es.ubu.lsi.modelo.Usuario;

/**
 * Obtiene los usuarios conectados al chat.
 * 
 * @author Andrés Miguel Terán
 * @author Francisco Saiz Güemes
 */
@WebServlet(description = "Obtiene los usuarios conectados al chat", urlPatterns = { "/GetUsuarios" })
public class GetUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Manager singleton de la aplicacion.
	 */
	private Manager manager = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetUsuarios() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Comprobamos si existe el manager en este servlet y si no lo cogemos
		// del contexto, porque el CheckUsuario lo habrá dejado en su primera
		// ejecución
		if (manager == null) {
			ServletContext context = getServletContext();
			manager = (Manager) context.getAttribute("manager");
		}

		// Obtenemos el nombre de usuario de la query string
		String nickname = request.getParameter("nickname");

		// Obtenemos la lista con los usuarios conectados
		LinkedList<Usuario> usuarios = manager.getUsuarios();

		// Imprimimos los usuarios si no es el mismo que la peticion
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Estilos
		String estiloUsuario = " style=\"font-family: sans-serif; margin-top: 5px; margin-bottom: 5px; margin-left: 2px; font-size: small;\" ";

		for (Usuario u : usuarios) {
			if (!u.getNickname().equals(nickname)) {
				out.print("<p" + estiloUsuario + " >" + u.toString() + "</p>");
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
		doGet(request, response);
	}

}
