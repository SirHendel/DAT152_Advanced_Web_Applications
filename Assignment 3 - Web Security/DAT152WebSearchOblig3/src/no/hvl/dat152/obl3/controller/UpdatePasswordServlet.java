package no.hvl.dat152.obl3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.hvl.dat152.obl3.database.AppUser;
import no.hvl.dat152.obl3.database.AppUserDAO;
import no.hvl.dat152.obl3.util.CsrfHandler;
import no.hvl.dat152.obl3.util.Validator;

@WebServlet("/updatepassword")
public class UpdatePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// check that the user has a valid session
		if (RequestHelper.isLoggedIn(request)) {
			request.getRequestDispatcher("updatepassword.jsp").forward(request, response);
			request.setAttribute("csrftoken", request.getSession().getAttribute("csrftoken"));
		} else {
			request.setAttribute("message", "Session has expired. Login again!");
			request.getRequestDispatcher("login").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.removeAttribute("message");

		boolean successfulPasswordUpdate = false;

		String passwordnew = Validator.validString(request.getParameter("passwordnew"));
		String confirmedPasswordnew = Validator.validString(request.getParameter("confirm_passwordnew"));

		if (RequestHelper.isLoggedIn(request)) {

			AppUser user = (AppUser) request.getSession().getAttribute("user");

			AppUserDAO userDAO = new AppUserDAO();

			if (passwordnew.equals(confirmedPasswordnew)) {
				if (CsrfHandler.isCSRFTokenMatch(request)) {

					if (Validator.validPassword(passwordnew)) {
						successfulPasswordUpdate = userDAO.updateUserPassword(user.getUsername(), passwordnew);
					} else {
						request.setAttribute("message", "Password is not strong!");
						request.getRequestDispatcher("updatepassword.jsp").forward(request, response);
					}

					if (successfulPasswordUpdate) {
						request.getSession().invalidate(); // invalidate current session and force user to login again
						request.setAttribute("message", "Password successfully updated. Please login again!");
						response.sendRedirect("login");

					} else {
						request.setAttribute("message", "Password update failed!");
						request.getRequestDispatcher("updatepassword.jsp").forward(request, response);
					}
				} else {
					request.getSession().invalidate();
					request.setAttribute("message", "CSRF ATTACK AVOIDED");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}

			} else {
				request.setAttribute("message", "Password fields do not match. Try again!");
				request.getRequestDispatcher("updatepassword.jsp").forward(request, response);
			}

		} else {
			request.getSession().invalidate();
			request.getRequestDispatcher("index.html").forward(request, response);
		}

	}

}
