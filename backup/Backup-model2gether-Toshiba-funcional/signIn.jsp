<%--
    Document   : signIn.jsp
    Created on : 19/03/2015, 12:17:43
    Author     : Leandro Luque
--%>
<%@page trimDirectiveWhitespaces="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en" />
<fmt:setBundle basename="br.com.i18n.resources" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="sign-in.title" /></title>
    </head>
    <body>
        <div id='pageContainer'>
            <div class='container'>
                <div class='row'>
                    <div class="col-sm-8 col-sm-offset-2 text topSection">
                        <h1><i class="fa fa-object-group"></i> <fmt:message key="general.toolName" /></h1>
                        <div class="description">
                            <p><fmt:message key="general.toolDescription" /></p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-sm-offset-3 form-box">
                        <c:if test="${message != null}">
                            <c:choose>
                                <c:when test="${messageType eq 'success'}"><div class="alert alert-info"></c:when>
                                    <c:otherwise><div class="alert alert-danger"></c:otherwise>
                                    </c:choose>
                                    <p class="text"><fmt:message key="${message}" /></p>
                                </div>
                            </c:if>
                        <c:if test="${param['message'] != null}">
                            <c:choose>
                                <c:when test="${messageType eq 'success'}"><div class="alert alert-info"></c:when>
                                    <c:otherwise><div class="alert alert-danger"></c:otherwise>
                                    </c:choose>
                                    <p class="text"><fmt:message key="${param['message']}" /></p>
                                </div>
                            </c:if>
                            <div class="form-top">
                                <div class="form-top-left">
                                    <p><fmt:message key="sign-in.instructions" /></p>
                                </div>
                                <div class="form-top-right"><i class="fa fa-key"></i></div>
                            </div>
                            <div class="form-bottom">
                                <form role="form" id="signin" action="ServletController" method="post">
                                    <input type="hidden" name="action" value="SignIn" />
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label class="sr-only" for="username"><fmt:message key="sign-in.username" /></label>
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                            <input id="username" type="text" class="form-control" name="name" required aria-required="true" value="" placeholder="<fmt:message key="sign-in.username-placehoder" />">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label class="sr-only" for="password"><fmt:message key="sign-in.password" /></label>
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                            <input id="password" type="password" class="form-control" name="pass" required aria-required="true" value="" placeholder="<fmt:message key="sign-in.password-placehoder" />">
                                        </div>
                                        <%--<div class="pull-right"><a href="#" class="discreteLink" data-target="#forgotPasswordModal" data-toggle="modal">Forgot password?</a></div>--%>
                                    </div>
                                    <!--<div class="checkbox">
                                        <label><input name="keepconn" type="checkbox" value="true"><fmt:message key="sign-in.keepMeConnected" /></label>
                                    </div>-->

                                    <button type="submit" class="btn btn-primary btn-lg action"><fmt:message key="sign-in.button" /></button>
                                </form>
                            </div>
                            <div class="form-footer">
                                <div class="pull-right"><a href="signUp.jsp"><fmt:message key="sign-in.registration.start" /></a> <fmt:message key="sign-in.registration.end" /></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="navbar navbar-fixed-bottom footer"><p><fmt:message key="footer.start" /> Leandro Luque <fmt:message key="footer.end" />.</p></div>

            <!-- Placed at the end of the document so the pages load faster. --!>
            <!-- jQuery -->
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
            <!-- Bootstrap -->
            <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
            <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
            <!-- Fuel UX -->
            <script src="http://www.fuelcdn.com/fuelux/3.11.0/js/fuelux.min.js"></script>
            <link href="//www.fuelcdn.com/fuelux/3.11.0/css/fuelux.min.css" rel="stylesheet">
            <!-- Font-awesome -->
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
            <!-- Google Fonts -->
            <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans">
            <!-- Page style -->
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app/sign.css">
            </body>
            </html>
