package it.prova.raccoltafilm.web.servlet.film;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.raccoltafilm.model.Film;
import it.prova.raccoltafilm.service.MyServiceFactory;


@WebServlet("/ExecuteDeleteFilmServlet")
public class ExecuteDeleteFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteDeleteFilmServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idDeletFilmParam = request.getParameter("idFilmDelete");
		
		Film filmInstance = null;
		try {
			filmInstance = MyServiceFactory.getFilmServiceInstance().caricaSingoloElemento(Long.parseLong(idDeletFilmParam));

			MyServiceFactory.getFilmServiceInstance().rimuovi(filmInstance);
			
			request.setAttribute("film_list_attribute",MyServiceFactory.getFilmServiceInstance().listAllElements());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/film/delete.jsp").forward(request, response);
			return;
		}
		
		response.sendRedirect("ExecuteListFilmServlet?operationResult=SUCCESS");
	}

}
