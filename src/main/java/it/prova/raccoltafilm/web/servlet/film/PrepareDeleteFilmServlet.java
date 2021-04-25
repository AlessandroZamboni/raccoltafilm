package it.prova.raccoltafilm.web.servlet.film;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.raccoltafilm.service.MyServiceFactory;

@WebServlet("/PrepareDeleteFilmServlet")
public class PrepareDeleteFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareDeleteFilmServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idFilmParam = request.getParameter("idFilm");

		try {
			request.setAttribute("delete_film_attr", MyServiceFactory.getFilmServiceInstance().caricaSingoloElementoEager(Long.parseLong(idFilmParam)));
		} catch (Exception e) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		
		request.getRequestDispatcher("/film/delete.jsp").forward(request, response);
	}

}
