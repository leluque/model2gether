<%--
    Document   : diagrama-grafico
    Created on : 30/03/2015, 23:21:09
    Author     : Leandro Luque
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en" />
<fmt:setBundle basename="br.com.i18n.resources" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/3rd/jsuml2/css-core/UDStyle.css" media="screen" />
        <!-- jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
        <!-- Bootstrap -->
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app/app.css" />
        <title>Model2gether - Graphical diagram editing</title>
    </head>
    <body>
        <c:import url="/WEB-INF/jspf/main-menu.jsp"></c:import>
            <section class="container">
                <section class="content">
                    <section class="tab-content-main-menu">
                        <section id="uc" class="wrapper-submenu">
                            <ul id="menu-uc" class="app-menu row">
                                <li class="item col-md-1">
                                    <button id="create-act" title="Add Actor" class="mousetrap command create-act">
                                        <i class="sprite-icon-menu icon"></i>
                                        <span class="text hidden">Add Actor</span>
                                    </button>
                                </li>
                                <li class="item col-md-1">
                                    <button id="create-uc" title="Add Use Case" class="mousetrap command create-uc">
                                        <i class="sprite-icon-menu icon"></i>
                                        <span class="text hidden">Add Use Case</span>
                                    </button>
                                </li>
                                <li class="item col-md-1">
                                    <button id="create-rel" title="Add Association" class="mousetrap command create-rel">
                                        <i class="sprite-icon-menu icon"></i>
                                        <span class="text hidden">Add Association</span>
                                    </button>
                                </li>
                                <li class="item col-md-1">
                                    <button id="create-inc" title="Add Inclusion" class="mousetrap command create-inc">
                                        <i class="sprite-icon-menu icon"></i>
                                        <span class="text hidden">Add Inclusion</span>
                                    </button>
                                </li>
                                <li class="item col-md-1">
                                    <button id="create-ext" title="Add Extension" class="mousetrap command create-ext">
                                        <i class="sprite-icon-menu icon"></i>
                                        <span class="text hidden">Add Extension</span>
                                    </button>
                                </li>
                                <li class="item col-md-1">
                                    <button id="create-gen" title="Add Inheritance" class="mousetrap command create-inherit">
                                        <i class="sprite-icon-menu icon"></i>
                                        <span class="text hidden">Add Inheritance</span>
                                    </button>
                                </li>
                                <li class="item col-md-1">
                                    <button id="delete-elm" title="Delete Element" class="mousetrap command delete_elm">
                                        <i class="sprite-icon-menu icon" title="Delete Element"></i>
                                        <span class="text hidden">Delete Element</span>
                                    </button>
                                </li>
                                <li class="item col-md-1">
                                    <button id="rename-elm" title="Rename Element" class="mousetrap command update_elm">
                                        <i class="sprite-icon-menu icon" title="Rename Element"></i>
                                        <span class="text hidden">Rename Element</span>
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

                    <section class="wrapper-canvas">
                        <div id="umlUC" class="mousetrap uml umlUC"></div>
                    </section>
                </section>
            </section>
            <script>
                var idDiagram = "${id}";
                var diagramBase = JSON.parse("${diagrama}");
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
