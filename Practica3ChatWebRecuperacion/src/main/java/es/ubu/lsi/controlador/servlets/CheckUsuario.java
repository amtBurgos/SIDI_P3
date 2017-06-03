package es.ubu.lsi.controlador.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.ubu.lsi.controlador.Manager;
import es.ubu.lsi.modelo.Usuario;

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
	 * Manager singleton de la aplicacion.
	 */
	private Manager manager = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckUsuario() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nickname = request.getParameter("nickname");

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

		LinkedList<Usuario> user = manager.getUsuarios();
		int size = user.size();

		// Comprobamos si el usuario que intenta entrar existe
		if (manager.existeUsuario(nickname)) {
			// Si existe informamos de que ya existe y los mandamos al index
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>El usuario ya existe</title>");
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
			out.println("<meta http-equiv=\"refresh\" content=\"5;url=index.html\" />");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Error al hacer login</h1>");
			out.println("<p style=\"color: red;\">El nombre de usuario '" + nickname + "' ya existe.</p><br>"
					+ "<p>Se redirigirá a la página principal en 5 segundos</p>");
			out.println("</body>");
			out.println("</html>");
			out.close();
		} else {
			// Si no existe creamos una sesion y lo redirigimos a la sala
			HttpSession session = request.getSession(true);
			session.setAttribute("nickname", nickname);

			// Guardamos la hora
			int hora = GregorianCalendar.getInstance().get(Calendar.HOUR_OF_DAY);
			int minutos = GregorianCalendar.getInstance().get(Calendar.MINUTE);

			// Añadimos al usuario
			boolean anadido = manager.anadirUsuario(nickname, hora + ":" + minutos);

			// Redireccionamos a la sala
			// response.sendRedirect("./Sala.jsp");

			if (anadido == true) {
				request.getRequestDispatcher("./vista/Sala.jsp").forward(request, response);
			} else {
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>El usuario ya existe</title>");
				out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
				out.println("<meta http-equiv=\"refresh\" content=\"5;url=index.html\" />");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Error en el servidor</h1>");
				out.println("<p style=\"color: red;\">El nombre de usuario '" + nickname
						+ "' no se ha podido registrar en el servidor.</p><br>"
						+ "<p>Se redirigirá a la página principal en 5 segundos</p>");
				out.println("</body>");
				out.println("</html>");
				out.close();
			}
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
