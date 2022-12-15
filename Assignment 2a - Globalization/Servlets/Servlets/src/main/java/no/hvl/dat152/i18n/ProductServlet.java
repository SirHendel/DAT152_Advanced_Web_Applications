package no.hvl.dat152.i18n;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.jsp.jstl.core.Config;
import no.hvl.dat152.i18n.DAO.DescriptionDAO;
import no.hvl.dat152.i18n.DAO.ProductDAO;
import no.hvl.dat152.i18n.model.Cart;
import no.hvl.dat152.i18n.model.Description;
import no.hvl.dat152.i18n.model.Product;


/**
 * Servlet implementation class SetLanguageServlet.
 */
@WebServlet("/Products")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new SetLanguageServlet.
     *
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Description> returnList = new ArrayList<>();
        String language = request.getLocale().toString();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("locale")) {
                    // Set locale from cookie
                    language = cookie.getValue();
                }
            }
        } else {
            Locale locale = request.getLocale();

//            Config.set(request.getSession(), Config.FMT_LOCALE, locale.getLanguage() + "_" + locale.getCountry());
            Config.set(request.getSession(), Config.FMT_LOCALE, locale.getLanguage());

//            Cookie localeCookie = new Cookie("locale", locale.getLanguage() + "_" + locale.getCountry());
            Cookie localeCookie = new Cookie("locale", locale.getLanguage());
            localeCookie.setMaxAge(365 * 24 * 60 * 60); // One year in seconds
            response.addCookie(localeCookie);
            // Cookie with locale sent to client
        }

        for (Product p : ProductDAO.getProducts()){
            for (Description d : DescriptionDAO.getDescriptionsByProductId(p.getId())){
                if (d.getLangCode().equals(language)) {
                    returnList.add(d);
                }
            }
        }

        // return products
        request.setAttribute("descriptions" , returnList);
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long productID = Long.parseLong(request.getParameter("ID"));
        Product product = ProductDAO.getProductByID(productID);
        HttpSession session = request.getSession();
        if (session != null) {
            Cart cart = (Cart) session.getAttribute("Cart");
            if(cart == null) {
                cart = new Cart();
            }
            cart.addToCart(product);
            request.getSession().setAttribute("Cart", cart);
        } else {
            Cart cart = new Cart();
            cart.addToCart(product);
            request.getSession().setAttribute("Cart", cart);
        }

        response.sendRedirect("Products");
    }
}
