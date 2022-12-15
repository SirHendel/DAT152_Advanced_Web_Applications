
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/tlds/T.tld" prefix="T"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html">
  <meta charset="UTF-8">
  <title>Assignment 2 - Group 3</title>
</head>

<body>
<p><jsp:include page="languages.jsp" /></p>

<fmt:bundle basename="no.hvl.dat152.i18n.Messages">

  <h1><fmt:message key="HomeTitle" /></h1>
  <img src="Java-logo.gif" alt="Java Logo" height="120" />
  <p><fmt:message key="HomeSentence" />
    <a href="Products"><fmt:message key="HomeProducts" /> </a>
  </p>

</fmt:bundle>
<T:copyright  since="2008"> HVL </T:copyright>

</body>
</html>
