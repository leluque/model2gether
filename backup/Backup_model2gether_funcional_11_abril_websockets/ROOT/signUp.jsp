<%--
    Document   : signUp.jsp
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
        <title><fmt:message key="sign-up.title" /></title>
    </head>
    <body>
        <div id='pageContainer'>
            <div class='container'>
                <div class='row'>
                    <div class="col-sm-8 col-sm-offset-2 text topSection">
                        <h1><i class="fa fa-object-group"></i> <fmt:message key="general.toolName" /></h1>
                        <div class="description">
                            <p><fmt:message key="sign-up.description" /></p>
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
                            <div class="form-top">
                                <div class="form-top-left">
                                    <p><fmt:message key="sign-up.instructions" /></p>
                                </div>
                                <div class="form-top-right"><i class="fa fa-plus-square"></i></div>
                            </div>
                            <div class="form-bottom">
                                <form role="form" id="signUp" action="ServletController" method="get">
                                    <input type="hidden" name="action" value="SignUp" />
                                    <div class="form-group input-group">
                                        <label class="sr-only" for="username"><fmt:message key="sign-up.username" /></label>
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                        <input id="username" type="text" class="form-control" name="name" required aria-required="true" value="" placeholder="<fmt:message key="sign-up.username-placehoder" />">
                                    </div>
                                    <div class="form-group input-group">
                                        <label class="sr-only" for="email"><fmt:message key="sign-up.email" /></label>
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                                        <input id="email" type="text" class="form-control" name="email" required aria-required="true" value="" placeholder="<fmt:message key="sign-up.email-placehoder" />">
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label class="sr-only" for="password"><fmt:message key="sign-up.password" /></label>
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                            <input id="password" type="password" class="form-control" name="pass" required aria-required="true" value="" placeholder="<fmt:message key="sign-up.password-placehoder" />">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label class="sr-only" for="password"><fmt:message key="sign-up.repeatedPassword" /></label>
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-repeat"></i></span>
                                            <input id="repeatedPassword" type="password" class="form-control" required aria-required="true" value="" placeholder="<fmt:message key="sign-up.repeatedPassword-placehoder" />">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <p><strong><fmt:message key="sign-up.representationMode" /></strong></p>
                                        <label class="radio-inline"><input type="radio" name="representationMode" value="0" required aria-required="true"><fmt:message key="general.textualMode" /></label>
                                        <label class="radio-inline"><input type="radio" name="representationMode" value="1" required aria-required="true" checked="checked"><fmt:message key="general.graphicalMode" /></label>
                                    </div>
                                    <button type="submit" class="btn btn-primary btn-lg action"><fmt:message key="sign-up.button" /></button>
                                </form>
                            </div>
                            <div class="form-footer">
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="navbar navbar-fixed-bottom footer"><p><fmt:message key="footer.start" /> Leandro Luque <fmt:message key="footer.end" /></p></div>

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
            <script type="text/javascript">
                window.onload = function () {
                    'use strict';
                    var form = document.getElementById('signUp');
                    form.addEventListener('submit', function (evt) {
                        var pwd1 = document.getElementById('password').value;
                        var pwd2 = document.getElementById('repeatedPassword').value;
                        //console.log(pwd1, pwd2);
                        if (pwd1 !== pwd2) {
                            console.log('Senha diferentes');
                            evt.preventDefault();
                            return false;
                        } else {
                            console.log('Senha similares');
                            return true;
                        }
                    }, false);
                };
            </script>
    </body>
</html>

