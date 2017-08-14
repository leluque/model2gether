<%--
    Document   : diagrama-grafico
    Created on : 30/03/2015, 23:21:09
    Author     : Leandro Luque
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : not empty cookie.model2getherlang.value ? cookie.model2getherlang.value : pageContext.request.locale.language}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="br.com.i18n.resources" />
<!DOCTYPE html>
<html lang="${language}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/3rd/jsuml2/css-core/UDStyle.css" media="screen" />
        <!-- jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
        <!-- Bootstrap -->
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app/app.css" />
        <title><fmt:message key="graphical.title" /></title>
    </head>
    <body>
        <c:import url="/WEB-INF/jspf/main-menu.jsp"></c:import>
            <section class="container">
                <section class="content">
                    <fmt:message key="general.warning" />
                    <section class="tab-content-main-menu">
                        <section id="uc" class="wrapper-submenu">
                            <ul id="menu-uc" class="app-menu row">
                                <li class="item col-md-1">
                                    <button id="create-act" title="<fmt:message key="graphical.addActor" />" class="mousetrap command create-act">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="graphical.addActor" />"></i>
                                    <span class="text hidden"><fmt:message key="graphical.addActor" /></span>
                                </button>
                            </li>
                            <li class="item col-md-1">
                                <button id="create-uc" title="<fmt:message key="graphical.addUseCase" />" class="mousetrap command create-uc">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="graphical.addUseCase" />"></i>
                                    <span class="text hidden"><fmt:message key="graphical.addUseCase" /></span>
                                </button>
                            </li>
                            <li class="item col-md-1">
                                <button id="create-rel" title="<fmt:message key="graphical.addAssociation" />" class="mousetrap command create-rel">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="graphical.addAssociation" />"></i>
                                    <span class="text hidden"><fmt:message key="graphical.addAssociation" /></span>
                                </button>
                            </li>
                            <li class="item col-md-1">
                                <button id="create-inc" title="<fmt:message key="graphical.addInclusion" />" class="mousetrap command create-inc">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="graphical.addInclusion" />"></i>
                                    <span class="text hidden"><fmt:message key="graphical.addInclusion" /></span>
                                </button>
                            </li>
                            <li class="item col-md-1">
                                <button id="create-ext" title="<fmt:message key="graphical.addExtension" />" class="mousetrap command create-ext">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="graphical.addExtension" />"></i>
                                    <span class="text hidden"><fmt:message key="graphical.addExtension" /></span>
                                </button>
                            </li>
                            <li class="item col-md-1">
                                <button id="create-gen" title="<fmt:message key="graphical.addInheritance" />" class="mousetrap command create-inherit">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="graphical.addInheritance" />"></i>
                                    <span class="text hidden"><fmt:message key="graphical.addInheritance" /></span>
                                </button>
                            </li>
                            <li class="item col-md-1">
                                <button id="delete-elm" title="<fmt:message key="graphical.deleteElement" />" class="mousetrap command delete_elm">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="graphical.deleteElement" />"></i>
                                    <span class="text hidden"><fmt:message key="graphical.deleteElement" /></span>
                                </button>
                            </li>
                            <li class="item col-md-1">
                                <button id="rename-elm" title="<fmt:message key="graphical.renameElement" />" class="mousetrap command update_elm">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="graphical.renameElement" />"></i>
                                    <span class="text hidden"><fmt:message key="graphical.renameElement" /></span>
                                </button>
                            </li>
                        </ul>
                    </section>
                </section>
                <!--<section>
                    <section>
                        <div class="form-group">
                            <input type="checkbox" name="acompanhar" />
                            <label>Acompanhar mudanças de</label>
                            <select>
                                <option value="">Todos</option>
                                <option value="">Manuela Luque</option>
                                <option value="">João Pedro</option>
                            </select>
                            <button type="button">
                                <span class="glyphicon glyphicon-volume-up"></span> Notificar usuário
                            </button>
                        </div>
                    </section>
                </section>-->

                <section class="wrapper-canvas" role="main">
                    <div id="umlUC" class="mousetrap uml umlUC"></div>
                </section>
            </section>
        </section>
        <script>
                var idDiagram = "${id}";
                var diagramBase = JSON.parse("${diagrama}");
                $(document).ready(function(){
                    openDiagram(diagramBase);
                });
        </script>
        <%-- <c:import url="/WEB-INF/jspf/footer.jsp"></c:import> --%>
        <script type="application/javascript" src="assets/3rd/jquery.js"></script>
        <script type="application/javascript" src="assets/3rd/jsuml2/js-core/UDCore.js"></script>
        <script type="application/javascript" src="assets/3rd/jsuml2/js-core/UDModules.js"></script>
        <script type="application/javascript" src="assets/3rd/bootstrap/js/bootstrap_min.js"></script>
        <script type="application/javascript" src="assets/3rd/bootstrap/js/collapse.js"></script>
        <script type="application/javascript" src="assets/3rd/mousetrap/mousetrap_min.js"></script>
        <script type="application/javascript" src="assets/3rd/classie.js"></script>
        <script type="application/javascript" src="assets/js/app/websocket.js"></script>
        <script type="application/javascript" src="assets/js/app/app.js"></script>
        <script type="application/javascript" src="assets/js/app/mapKeyBoard.js"></script>
        <script type="application/javascript" src="assets/js/app/libUseCase_grafico.js"></script>
    </body>
</html>
