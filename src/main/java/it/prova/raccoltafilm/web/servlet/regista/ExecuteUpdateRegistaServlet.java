package it.prova.raccoltafilm.web.servlet.regista;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.raccoltafilm.model.Regista;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.utility.UtilityForm;

@WebServlet("/ExecuteUpdateRegistaServlet")
public class ExecuteUpdateRegistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteUpdateRegistaServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("regista.id");
		String nomeParam = request.getParameter("nome");
		String cognomeParam = request.getParameter("cognome");
		String nicknameParam = request.getParameter("nickName");
		String dataDiNascitaParam = request.getParameter("dataDiNascita");
		String sessoParam = request.getParameter("sesso");

		Regista registaInstance = UtilityForm.createRegistaFromParams(nomeParam, cognomeParam, nicknameParam,
				dataDiNascitaParam, sessoParam);
		registaInstance.setId(Long.parseLong(idParam));

		try {
			// se la validazione non risulta ok
			if (!UtilityForm.validateRegistaBean(registaInstance)) {
				request.setAttribute("update_regista_attr", registaInstance);
				// questo mi serve per la select di registi in pagina
				request.setAttribute("registi_list_attribute",
						MyServiceFactory.getRegistaServiceInstance().listAllElements());
				request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
				request.getRequestDispatcher("/regista/edit.jsp").forward(request, response);
				return;
			}

			MyServiceFactory.getRegistaServiceInstance().aggiorna(registaInstance);
			request.setAttribute("registi_list_attribute",
					MyServiceFactory.getRegistaServiceInstance().listAllElements());
		} catch (Exception e) {
			e.printStackTrace();			
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/regista/edit.jsp").forward(request, response);
			return;
		}

		response.sendRedirect("ExecuteListRegistaServlet?operationResult=SUCCESS");
	}

}
