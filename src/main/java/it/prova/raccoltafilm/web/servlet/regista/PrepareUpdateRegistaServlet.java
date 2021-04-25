package it.prova.raccoltafilm.web.servlet.regista;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.raccoltafilm.service.MyServiceFactory;

@WebServlet("/PrepareUpdateRegistaServlet")
public class PrepareUpdateRegistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PrepareUpdateRegistaServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idRegistaParam = request.getParameter("idRegista");

		if (!NumberUtils.isCreatable(idRegistaParam)) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("home").forward(request, response);
			return;
		}

		try {
			request.setAttribute("update_regista_attr", MyServiceFactory.getRegistaServiceInstance()
					.caricaSingoloElementoConFilms(Long.parseLong(idRegistaParam)));
			request.setAttribute("registi_list_attribute",
					MyServiceFactory.getRegistaServiceInstance().listAllElements());
			request.setAttribute("film_list_attribute", MyServiceFactory.getFilmServiceInstance().listAllElements());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("home").forward(request, response);
			return;
		}

		request.getRequestDispatcher("/regista/edit.jsp").forward(request, response);
	}

}
