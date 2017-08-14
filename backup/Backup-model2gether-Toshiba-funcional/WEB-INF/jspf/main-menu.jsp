<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en" />
<fmt:setBundle basename="br.com.i18n.resources" />

<%--
<c:if test="${empty user}">
    <c:redirect url="signIn.jsp" />
</c:if>
--%>
<!--navbar-fixed-top http://getbootstrap.com/examples/navbar-fixed-top/-->

<!-- Font-awesome -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css" />

<style>
    .navbar-default {
        background-color: #000000;
        border-color: #7f7f7f;
    }
    .navbar-default .navbar-brand {
        color: #ecf0f1;
    }
    .navbar-default .navbar-brand:hover, .navbar-default .navbar-brand:focus {
        color: #ffffff;
    }
    .navbar-default .navbar-text {
        color: #ecf0f1;
    }
    .navbar-default .navbar-nav > li > a {
        color: #ecf0f1;
    }
    .navbar-default .navbar-nav > li > a:hover, .navbar-default .navbar-nav > li > a:focus {
        color: #ffffff;
    }
    .navbar-default .navbar-nav > li > .dropdown-menu {
        background-color: #000000;
    }
    .navbar-default .navbar-nav > li > .dropdown-menu > li > a {
        color: #ecf0f1;
    }
    .navbar-default .navbar-nav > li > .dropdown-menu > li > a:hover,
    .navbar-default .navbar-nav > li > .dropdown-menu > li > a:focus {
        color: #ffffff;
        background-color: #7f7f7f;
    }
    .navbar-default .navbar-nav > li > .dropdown-menu > li > .divider {
        background-color: #000000;
    }
    .navbar-default .navbar-nav > .active > a, .navbar-default .navbar-nav > .active > a:hover, .navbar-default .navbar-nav > .active > a:focus {
        color: #ffffff;
        background-color: #7f7f7f;
    }
    .navbar-default .navbar-nav > .open > a, .navbar-default .navbar-nav > .open > a:hover, .navbar-default .navbar-nav > .open > a:focus {
        color: #ffffff;
        background-color: #7f7f7f;
    }
    .navbar-default .navbar-toggle {
        border-color: #7f7f7f;
    }
    .navbar-default .navbar-toggle:hover, .navbar-default .navbar-toggle:focus {
        background-color: #7f7f7f;
    }
    .navbar-default .navbar-toggle .icon-bar {
        background-color: #ecf0f1;
    }
    .navbar-default .navbar-collapse,
    .navbar-default .navbar-form {
        border-color: #ecf0f1;
    }
    .navbar-default .navbar-link {
        color: #ecf0f1;
    }
    .navbar-default .navbar-link:hover {
        color: #ffffff;
    }

    @media (max-width: 767px) {
        .navbar-default .navbar-nav .open .dropdown-menu > li > a {
            color: #ecf0f1;
        }
        .navbar-default .navbar-nav .open .dropdown-menu > li > a:hover, .navbar-default .navbar-nav .open .dropdown-menu > li > a:focus {
            color: #ffffff;
        }
        .navbar-default .navbar-nav .open .dropdown-menu > .active > a, .navbar-default .navbar-nav .open .dropdown-menu > .active > a:hover, .navbar-default .navbar-nav .open .dropdown-menu > .active > a:focus {
            color: #ffffff;
            background-color: #7f7f7f;
        }
    }
</style>
<nav class="navbar navbar-default">
    <div class="container-menu-fixed">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <span class="navbar-brand"><i class="fa fa-object-group"></i> <fmt:message key="general.toolName" /></span>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul id="settings" class="nav navbar-nav navbar-right">
                <li><a href="shortcuts.jsp" title="Shortcuts"><span class="fa fa fa-keyboard-o"></span> Shortcuts</a></li>
                <li><a href="profile.jsp" title="<fmt:message key="mainMenu.profileTitle" />"><span class="glyphicon glyphicon-user"></span> <fmt:message key="mainMenu.profile" /></a></li>
                <li><a href="index.jsp" title="<fmt:message key="mainMenu.listTitle" />"><span class="glyphicon glyphicon-list"></span> <fmt:message key="mainMenu.list" /></a></li>
                <li><a href="#" id="create-diagram" data-toggle="modal" class="mousetrap" data-target="#myModal" title="<fmt:message key="mainMenu.newUCDiagramTitle" />"><span class="glyphicon glyphicon-plus"></span> <fmt:message key="mainMenu.newUCDiagram" /></a></li>
                <li><a href="ServletController?action=Logout" id="exit" title="<fmt:message key="mainMenu.logoutTitle" />"><fmt:message key="mainMenu.logout" /></a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<section id="modal-novo-diagrama">
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="exampleModalLabel"><fmt:message key="mainMenu.newUCDiagramModalTitle" /></h4>
                </div>
                <div class="modal-body">
                    <form id="novo-diagrama" action="ServletController?action=LoadDiagram" method="POST">
                        <!--
                        <div class="form-group">
                            <label for="diagramas" class="control-label">Diagramas</label>
                            <div  class="btn-group">
                                <select class="form-control" title="Tipo de Diagrama">
                                    <option value="">Sequência</option>
                                    <option value="">Caso de uso</option>
                                    <option>-</option>
                                </select>
                            </div>
                        </div>
                        -->
                        <div class="form-group">
                            <label for="mode" ><fmt:message key="mainMenu.representationMode" /></label>
                            <p>
                            <div  class="btn-group">
                                <select id="mode" name="type" class="form-control" required aria-required="true" aria-label="<fmt:message key="mainMenu.representationMode" />">
                                    <option value="textual"><fmt:message key="general.textualMode" /></option>
                                    <option value="grafico"><fmt:message key="general.graphicalMode" /></option>
                                </select>
                            </div>
                            </p>
                        </div>
                        <div class="form-group">
                            <label for="modos" class="control-label"><fmt:message key="mainMenu.diagramName" /></label>
                            <input type="text" required aria-required="true" class="form-control" aria-label="enviar" placeholder="<fmt:message key="mainMenu.diagramNamePlaceholder" />" name="name"/>
                        </div>
                        <div class="form-group">
                            <input type="submit" class="btn btn-primary button form-control" name="enviar" title="<fmt:message key="mainMenu.buttonTitle"/>" aria-label="enviar" value="<fmt:message key="mainMenu.buttonTitle"/>" />
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>