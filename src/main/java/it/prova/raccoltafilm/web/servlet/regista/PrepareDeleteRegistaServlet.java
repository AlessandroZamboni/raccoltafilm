package it.prova.raccoltafilm.web.servlet.regista;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.raccoltafilm.service.MyServiceFactory;

@WebServlet("/PrepareDeleteRegistaServlet")
public class PrepareDeleteRegistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PrepareDeleteRegistaServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idRegistaParam = request.getParameter("idRegista");

		try {
			request.setAttribute("delete_regista_attr", MyServiceFactory.getRegistaServiceInstance()
					.caricaSingoloElementoConFilms(Long.parseLong(idRegistaParam)));
		} catch (Exception e) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher("/regista/delete.jsp").forward(request, response);
	}

}
