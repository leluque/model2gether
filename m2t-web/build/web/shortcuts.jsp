<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page trimDirectiveWhitespaces="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : not empty cookie.model2getherlang.value ? cookie.model2getherlang.value : pageContext.request.locale.language}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="br.com.i18n.resources" />
<!DOCTYPE html>
<html lang="${language}">
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="shortcuts.title"/></title>
        <link rel="stylesheet" href="assets/3rd/jsuml2/UDStyle.css" media="screen" />
        <!-- jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
        <!-- Bootstrap -->
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
        <link rel="stylesheet" href="assets/3rd/codemirror/codemirror.css" />
        <link rel="stylesheet" href="assets/css/app/app.css" />
        <!-- jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    </head>
    <body>
        <c:import url="/WEB-INF/jspf/main-menu.jsp"></c:import>
            <section class="container" role="main">
                <header style="text-align:center"><fmt:message key="content" /></header>
                <section class="content">
                    <h1><fmt:message key="shortcuts.instructions" /></h1>
                    <p><fmt:message key="shortcuts.help" /></p>
                    <p><fmt:message key="shortcuts.shortcutInstructions" /></p>
                <ul>
                    <li><fmt:message key="shortcuts.general" />
                        <ul>
                            <li><fmt:message key="shortcuts.newUseCaseDiagram" /></li>
                        </ul>
                    </li>
                    <li><fmt:message key="shortcuts.editingMode" />
                        <ul>
                            <li><fmt:message key="shortcuts.focusEditingArea" />;</li>
                            <li><fmt:message key="shortcuts.newActor" /></li>
                            <li><fmt:message key="shortcuts.newUseCase" /></li>
                            <li><fmt:message key="shortcuts.newAssociation" /></li>
                            <li><fmt:message key="shortcuts.newInclusion" /></li>
                            <li><fmt:message key="shortcuts.newExtension" /></li>
                            <li><fmt:message key="shortcuts.newInheritance" /></li>
                            <li><fmt:message key="shortcuts.deleteElement" /></li>
                            <li><fmt:message key="shortcuts.renameElement" /></li>
                            <li>Alt+Shift+b visualiza uma mensagem anterior no histórico de ações</li>
                            <li>Alt+Shift+r visualiza uma mensagem posterior no histórico de ações</li>
                        </ul>
                    </li>
                </ul>
            </section>
        </section>
    </body>
</html>
