<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="no.hvl.dat152.i18n.model.Product" %>
<%@ page import="no.hvl.dat152.i18n.model.Cart" %>
<%@ page import="java.util.List" %>
<%@ page import="no.hvl.dat152.i18n.model.Description" %>
<%@ taglib uri="/WEB-INF/tlds/T.tld" prefix="T"%>
<%--
  Created by IntelliJ IDEA.
  User: venthanvigneswaran
  Date: 01.10.22
  Time: 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cart</title>
</head>
<body>
<p><jsp:include page="languages.jsp" /></p>
<fmt:bundle basename="no.hvl.dat152.i18n.Messages">
<h1> Cart </h1>

<table style="width:80%">
    <tr>
        <th><p class = "langID"><fmt:message key="CartProductID" /></p></th>
        <th><p class = "langName"><fmt:message key="CartProductName" /></p></th>
        <th><p class = "langDescription"><fmt:message key="CartProductDesc" /></p></th>
        <th><p class = "langPrice"><fmt:message key="CartProductPrice" /></p></th>
    </tr>

    <div class="product">
        <%
            String language = request.getLocale().toString();
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("locale")) {
                    // Set locale from cookie
                    language = cookie.getValue();
                }
            }
            Cart cart = (Cart) session.getAttribute("Cart");
            double totalCart = 0.0;
            if(cart != null) {
                for(Product p : cart.showCart()) {
                    totalCart += p.getPriceByLanguage(language);
                    String desc = p.getDescriptionByLanguage(language).getText();
        %>
        <tr align="center">
            <td><p class="productID"><%= p.getId() %></p></td>
            <td><p class="productName"><%= p.getName() %></p></td>
            <td>
                <p class="productDescription">
                    <T:shorttext maxchars="10" value="<%= desc %>"/>
                </p>
            </td>
            <td><p class="Price"><fmt:formatNumber value="<%= p.getPriceByLanguage(language) %>" type="currency"></fmt:formatNumber></p></td>
        </tr>
        <%
                }
            }
        %>
    </div>

    <div class="totalAmount">
        <tr>
            <td colspan="4"></td>
            <td><strong><label class="langTotalAmount"><fmt:message key="CartTotal" />:</label>
                <label class="totalAmount"><fmt:formatNumber value="<%= totalCart %>" type="currency"></fmt:formatNumber></label> </strong></td>
        </tr>
    </div>
</table>
<p>
    <a href="home.jsp"><fmt:message key="Home" /> </a>
    <br />
    <a href="Products"><fmt:message key="HomeProducts" /> </a>
</p>
</fmt:bundle>
<T:copyright  since="2008"> HVL </T:copyright>
</body>
</html>
