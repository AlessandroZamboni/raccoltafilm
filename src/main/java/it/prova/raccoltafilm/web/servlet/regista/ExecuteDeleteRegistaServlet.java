package it.prova.raccoltafilm.web.servlet.regista;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.raccoltafilm.model.Regista;
import it.prova.raccoltafilm.service.MyServiceFactory;

@WebServlet("/ExecuteDeleteRegistaServlet")
public class ExecuteDeleteRegistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteDeleteRegistaServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idDeletRegistaParam = request.getParameter("idRegistaDelete");

		try {
			Regista registaDaEliminare = MyServiceFactory.getRegistaServiceInstance()
					.caricaSingoloElementoConFilms((Long.parseLong(idDeletRegistaParam)));
			if (!registaDaEliminare.getFilms().isEmpty()) {
				request.setAttribute("errorMessage", "Impossibile eliminare questo regista.");
				request.setAttribute("registi_list_attribute",
						MyServiceFactory.getRegistaServiceInstance().listAllElements());
				request.getRequestDispatcher("regista/list.jsp").forward(request, response);
				return;

			}
			MyServiceFactory.getRegistaServiceInstance().rimuovi(registaDaEliminare);
			request.setAttribute("registi_list_attribute",
					MyServiceFactory.getRegistaServiceInstance().listAllElements());
			request.setAttribute("successMessage", "Operazione effettuata con successo");
		} catch (Exception e) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("regista/list.jsp").forward(request, response);
			return;
		}

		response.sendRedirect("ExecuteListRegistaServlet?operationResult=SUCCESS");
	}

}
