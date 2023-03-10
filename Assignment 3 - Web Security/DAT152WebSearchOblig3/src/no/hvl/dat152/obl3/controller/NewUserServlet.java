package no.hvl.dat152.obl3.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.hvl.dat152.obl3.database.AppUser;
import no.hvl.dat152.obl3.database.AppUserDAO;
import no.hvl.dat152.obl3.util.Crypto;
import no.hvl.dat152.obl3.util.CsrfHandler;
import no.hvl.dat152.obl3.util.Role;
import no.hvl.dat152.obl3.util.ServerConfig;
import no.hvl.dat152.obl3.util.Validator;

@WebServlet("/newuser")
public class NewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("dictconfig", ServerConfig.DEFAULT_DICT_URL);
		request.getRequestDispatcher("newuser.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		boolean successfulRegistration = false;

		String username = Validator.validString(request
				.getParameter("username"));
		String password = Validator.validString(request
				.getParameter("password"));
		String confirmedPassword = Validator.validString(request
				.getParameter("confirm_password"));
		String firstName = Validator.validString(request
				.getParameter("first_name"));
		String lastName = Validator.validString(request
				.getParameter("last_name"));
		String mobilePhone = Validator.validString(request
				.getParameter("mobile_phone"));
		String preferredDict = Validator.validString(request
				.getParameter("dicturl"));

		AppUser user = null;
		if (password.equals(confirmedPassword)) {
			if(Validator.validPassword(password)) {
				AppUserDAO userDAO = new AppUserDAO();
	
				try {
					byte[] salt = Crypto.getSalt();
					String passHash = Crypto.generateHashWithSalt(password, salt);
					String clientID = Crypto.generateRandomCryptoCode();
					user = new AppUser(username, passHash, salt,
							firstName, lastName, mobilePhone, Role.USER.toString(),
							clientID);
					System.out.println("New user clientID: " + clientID);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					e.printStackTrace();
				}			
				CsrfHandler.generateCSRFToken(request);
				successfulRegistration = userDAO.saveUser(user);
			} else {
				request.setAttribute("message", "Password is not strong!");
				request.getRequestDispatcher("newuser.jsp").forward(request,
						response);
				return;
			}
		}

		if (successfulRegistration) {
			request.getSession().setAttribute("user", user);
			Cookie dicturlCookie = new Cookie("dicturl", preferredDict);
			dicturlCookie.setMaxAge(60*10);
			response.addCookie(dicturlCookie);

			response.sendRedirect("searchpage");

		} else {
			request.setAttribute("message", "Registration failed!");
			request.getRequestDispatcher("newuser.jsp").forward(request,
					response);
		}
	}

}
