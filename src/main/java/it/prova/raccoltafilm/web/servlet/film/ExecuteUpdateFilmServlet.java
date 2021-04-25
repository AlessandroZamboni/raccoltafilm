package it.prova.raccoltafilm.web.servlet.film;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.raccoltafilm.model.Film;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.utility.UtilityForm;

@WebServlet("/ExecuteUpdateFilmServlet")
public class ExecuteUpdateFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteUpdateFilmServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("film.id");
		String titoloParam = request.getParameter("titolo");
		String genereParam = request.getParameter("genere");
		String dataPubblicazioneParam = request.getParameter("dataPubblicazione");
		String minutiDurataParam = request.getParameter("minutiDurata");
		String registaIdParam = request.getParameter("regista.id");

		Film filmInstance = UtilityForm.createFilmFromParams(titoloParam, genereParam, minutiDurataParam,
				dataPubblicazioneParam, registaIdParam);
		filmInstance.setId(Long.parseLong(idParam));

		try {
			// se la validazione non risulta ok
			if (!UtilityForm.validateFilmBean(filmInstance)) {
				request.setAttribute("update_film_attr", filmInstance);
				// questo mi serve per la select di registi in pagina
				request.setAttribute("film_list_attribute",
						MyServiceFactory.getFilmServiceInstance().listAllElements());
				request.setAttribute("registi_list_attribute",
						MyServiceFactory.getRegistaServiceInstance().listAllElements());
				request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
				request.getRequestDispatcher("/film/edit.jsp").forward(request, response);
				return;
			}

			MyServiceFactory.getFilmServiceInstance().aggiorna(filmInstance);
			request.setAttribute("film_list_attribute", MyServiceFactory.getFilmServiceInstance().listAllElements());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/film/edit.jsp").forward(request, response);
			return;
		}

		response.sendRedirect("ExecuteListFilmServlet?operationResult=SUCCESS");
	}

}
