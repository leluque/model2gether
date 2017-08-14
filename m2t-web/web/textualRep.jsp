<%--
    Document   : diagrama-grafico
    Created on : 30/03/2015, 23:21:09
    Author     : Leandro Luque
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : not empty cookie.model2getherlang.value ? cookie.model2getherlang.value : pageContext.request.locale.language}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="br.com.i18n.resources" />
<!DOCTYPE html>
<html lang="${language}">
    <head>
        <meta charset="UTF-8">
        <!-- jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
        <!-- Bootstrap -->
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
        <link rel="stylesheet" href="assets/3rd/codemirror/codemirror.css" />
        <link rel="stylesheet" href="assets/css/app/app.css" />
        <title><fmt:message key="textual.title" /></title>
    </head>
    <body>
        <c:import url="/WEB-INF/jspf/main-menu.jsp"></c:import>

            <section class="container" role="main">
                <header style="text-align:center"><fmt:message key="menu" /></header>
                <fmt:message key="general.warning" />
            <section class="content">
                <section class="tab-content-main-menu">
                    <section id="uc" class="wrapper-submenu">
                        <ul id="menu-uc" class="app-menu row">
                            <li class="item col-md-1">
                                <button id="create-act" title="<fmt:message key="textual.addActor" />" class="mousetrap command create-act">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="textual.addActor" />"></i>
                                    <span class="text hidden"><fmt:message key="textual.addActor" /></span>
                                </button>
                            </li>
                            <li class="item col-md-1">
                                <button id="create-uc" title="<fmt:message key="textual.addUseCase" />" class="mousetrap command create-uc">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="textual.addUseCase" />"></i>
                                    <span class="text hidden"><fmt:message key="textual.addUseCase" /></span>
                                </button>
                            </li>
                            <li class="item col-md-1">
                                <button id="create-rel" title="<fmt:message key="textual.addAssociation" />" class="mousetrap command create-rel">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="textual.addAssociation" />"></i>
                                    <span class="text hidden"><fmt:message key="textual.addAssociation" /></span>
                                </button>
                            </li>
                            <li class="item col-md-1">
                                <button id="create-inc" title="<fmt:message key="textual.addInclusion" />" class="mousetrap command create-inc">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="textual.addInclusion" />"></i>
                                    <span class="text hidden"><fmt:message key="textual.addInclusion" /></span>
                                </button>
                            </li>
                            <li class="item col-md-1">
                                <button id="create-ext" title="<fmt:message key="textual.addExtension" />" class="mousetrap command create-ext">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="textual.addExtension" />"></i>
                                    <span class="text hidden"><fmt:message key="textual.addExtension" /></span>
                                </button>
                            </li>
                            <li class="item col-md-1">
                                <button id="create-gen" title="<fmt:message key="textual.addInheritance" />" class="mousetrap command create-inherit">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="textual.addInheritance" />"></i>
                                    <span class="text hidden"><fmt:message key="textual.addInheritance" /></span>
                                </button>
                            </li>
                            <li class="item col-md-1">
                                <button id="delete-elm" title="<fmt:message key="textual.deleteElement" />" class="mousetrap command delete_elm">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="textual.deleteElement" />"></i>
                                    <span class="text hidden"><fmt:message key="textual.deleteElement" /></span>
                                </button>
                            </li>
                            <li class="item col-md-1">
                                <button id="rename-elm" title="<fmt:message key="textual.renameElement" />" class="mousetrap command update_elm">
                                    <i class="sprite-icon-menu icon" title="<fmt:message key="textual.renameElement" />"></i>
                                    <span class="text hidden"><fmt:message key="textual.renameElement" /></span>
                                </button>
                            </li>
                        </ul>
                    </section>
                </section>

                <h3><fmt:message key="textual.awarenessControlling" /></h3>
                <!-- Controlador de mensagens -->
                <label for="followUpdates" accesskey="o"><input type="checkbox" name="followUpdates" id="followUpdates" checked="checked" accesskey="o" onclick="handleUpdates()" /><fmt:message key="textual.followUpdates" /></label>
                <label for="beepChanges" accesskey="e"><input type="checkbox" name="beepChanges" id="beepChanges" checked="checked" accesskey="e" onclick="handleBeeps()" /><fmt:message key="textual.handleBeeps" /></label>
                <label for="updateContent" accesskey="p"><input type="checkbox" name="updateContent" id="updateContent" checked="checked" accesskey="p" onclick="handleUpdateContent()" /><fmt:message key="textual.updateDSL" /></label>

                <h2><fmt:message key="content" /></h2>

                <section class="wrapper-canvas">
                    <div id="umlUC">
                        <textarea id="textDiagram" cols="100" rows="22" style="padding: 5px" class="mousetrap"></textarea>
                        <h2><fmt:message key="textual.messages" /></h2>
                        <input type="text" id="msgRecebida" placeholder="<fmt:message key="textual.message" />" title="<fmt:message key="textual.renameElement" />" style="border: 0; width: 640px;padding: 5px;" />
                        <textarea id="errorsArea" style="width: 100%; padding: 5px;color: red;" class="hidden display:none" readonly></textarea>
                    </div>
                </section>
            </section>

        </section>
        <!-- Modal para adicionar novo elemento -->
        <div class="modal fade" id="modal-novo-elemento" tabindex="-1" role="dialog" aria-hidden="true" aria-labelledby="title-criar-elemento">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 id="title-criar-elemento" class="modal-title"><fmt:message key="textual.createElement" /></h4>
                    </div>
                    <div class="modal-body">
                        <form id="formCreateElement" class="form" method="POST" action="#">
                            <input id="elementName" type="text" class="form-control" name="elementName" aria-label="<fmt:message key="textual.typeElementName" />" required aria-required="true" />
                            <button class="btn btn-primary" style="margin-top: 10px;" type="submit" aria-label="<fmt:message key="textual.createButton" />"><fmt:message key="textual.createButton" /></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal para adicionar nova relacao -->
        <div class="modal fade" id="modal-nova-relacao" tabindex="-1" role="dialog" aria-hidden="true" aria-labelledby="titleModalRelation">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="titleModalRelation"><fmt:message key="textual.addNewRelationship" /></h4>
                    </div>
                    <div class="modal-body">
                        <form id="formCreateRelation" class="form" method="POST" action="#">
                            <div class="col-sm-6">
                                <label for="elementSource">From</label>
                                <select id="elementSource" class="form-control" name="elementSource" aria-label="<fmt:message key="textual.sourceElement" />" required="" aria-required="true"></select>
                            </div>
                            <div class="col-sm-6">
                                <label for="elementTarget">To</label>
                                <select id="elementTarget" class="form-control" name="elementTarget" aria-label="<fmt:message key="textual.targetElement" />" required="" aria-required="true"></select>
                            </div>
                            <button class="btn btn-primary" style="margin-top: 10px; margin-left: 15px;" type="submit" aria-label="<fmt:message key="textual.createButton" />"><fmt:message key="textual.createButton" /></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal para excluir elemento -->
        <div class="modal fade" id="modal-excluir-elemento" tabindex="-1" role="dialog" aria-hidden="true" aria-labelledby="titulo-excluir-elemento">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Fechar"><span aria-hidden="true">&times;</span></button>
                        <h4 id="titulo-excluir-elemento" class="modal-title"><fmt:message key="textual.deleteElement" /></h4>
                    </div>
                    <div class="modal-body">
                        <form id="formExcludeElement" class="form" method="POST" action="#">
                            <select id="elementToExclude" class="form-control" name="elementToExclude" aria-label="<fmt:message key="textual.deleteElementInstructions" />" required="" aria-required="true"></select>
                            <button class="btn btn-primary" style="margin-top: 10px;" type="submit" aria-label="<fmt:message key="textual.deleteButton" />"><fmt:message key="textual.deleteButton" /></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal para excluir elemento -->
        <div class="modal fade" id="modal-renomear-elemento" tabindex="-1" role="dialog" aria-hidden="true" aria-labelledby="titulo-renomear-elemento">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 id="titulo-renomear-elemento" class="modal-title"><fmt:message key="textual.renameElement" /></h4>
                    </div>
                    <div class="modal-body">
                        <form id="formRenameElement" class="form" method="POST" action="#">
                            <select id="elementToRename" class="form-control" name="elementToRename" aria-label="<fmt:message key="textual.renameInstruction1" />" required="" aria-required="true"></select>
                            <input id="elementNewName" type="text" class="form-control" name="elementNewName" aria-label="<fmt:message key="textual.renameInstruction2" />" required aria-required="true" placeholder="New name" style="margin-top: 10px;" />
                            <button class="btn btn-primary" style="margin-top: 10px;" type="submit" aria-label="<fmt:message key="textual.renameButton" />"><fmt:message key="textual.renameButton" /></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script>
            var idDiagram = "${id}";
            var diagramBase = JSON.parse("${diagrama}");
        </script>
        <%--<c:import url="/WEB-INF/jspf/footer.jsp"></c:import>--%>
        <script type="application/javascript" src="assets/3rd/jquery.js"></script>
        <script type="application/javascript" src="assets/3rd/jsuml2/js-core/UDCore.js"></script>
        <script type="application/javascript" src="assets/3rd/jsuml2/js-core/UDModules.js"></script>
        <script type="application/javascript" src="assets/3rd/bootstrap/js/bootstrap_min.js"></script>
        <script type="application/javascript" src="assets/3rd/bootstrap/js/collapse.js"></script>
        <script type="application/javascript" src="assets/3rd/mousetrap/mousetrap_min.js"></script>
        <script type="application/javascript" src="assets/3rd/classie.js"></script>
        <script type="application/javascript" src="assets/3rd/hotkeys/jquery.hotkeys.js"></script>
        <script type="application/javascript" src="assets/js/app/websocket.js"></script>
        <script type="application/javascript" src="assets/js/app/mapKeyBoard.js"></script>
        <script type="application/javascript" src="assets/js/app/libUseCase_textual.jsp"></script>
        <script type="application/javascript" src="assets/js/app/app.js"></script>

        <script>
            $(document).ready(function () {
                openDiagram(diagramBase);
                $(document).bind('keydown', 'shift+up', showPreviousMessage);
                $("#msgRecebida").bind('keydown', 'shift+up', showPreviousMessage);
                $("#textDiagram").bind('keydown', 'shift+up', showPreviousMessage);
                $(document).bind('keydown', 'shift+down', showNextMessage);
                $("#msgRecebida").bind('keydown', 'shift+down', showNextMessage);
                $("#textDiagram").bind('keydown', 'shift+down', showNextMessage);
            });
        </script>

    </body>
</html>