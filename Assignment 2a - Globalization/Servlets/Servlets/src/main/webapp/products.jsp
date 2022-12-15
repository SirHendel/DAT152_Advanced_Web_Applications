<%@ page import="no.hvl.dat152.i18n.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="no.hvl.dat152.i18n.model.Description" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tlds/T.tld" prefix="T"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html">
    <meta charset="UTF-8">
    <title>Assignment 2 - Group 3 | Products</title>
</head>
<body>
<p><jsp:include page="languages.jsp" /></p>

<fmt:bundle basename="no.hvl.dat152.i18n.Messages">


    <div>
        <%
            String language = request.getLocale().toString();
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("locale")) {
                    // Set locale from cookie
                    language = cookie.getValue();
                }
            }
            List<Description> descriptions = (List<Description>) request.getAttribute("descriptions");
            for(Description d : descriptions) {
        %>
            <table>
                <thead>
                    <tr>
                        <th><%=d.getProduct().getName()%></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><img src="<%=request.getContextPath() + "/img/" + d.getProduct().getImageFile()%>" height="100px" width="150px"></td>
                        <td>
                            <table>
                                    <tr><td><fmt:message key="name"/>: <%=d.getProduct().getName()%></td></tr>
                                    <tr><td><fmt:message key="price"/>: <fmt:formatNumber value="<%= d.getProduct().getPriceByLanguage(language) %>" type="currency"></fmt:formatNumber></td></tr>
                                    <tr><td><fmt:message key="description"/>: <%=d.getText()%></td></tr>
                                    <tr><td>
                                        <form action="Products" method="POST">
                                            <input type="hidden" name="ID" value="<%= d.getProduct().getId() %>" />
                                            <button type="submit"><fmt:message key="AddCart" /></button>
                                        </form>
                                    </td></tr>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>
            <br>
        <%
            }
        %>
        <p>
            <a href="home.jsp"><fmt:message key="Home" /> </a>
            <br />
            <a href="CartPage.jsp"><fmt:message key="Cart" /> </a>
        </p>
    </div>


</fmt:bundle>
<T:copyright  since="2008"> HVL </T:copyright>
</body>
</html>
