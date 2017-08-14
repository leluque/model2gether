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
        <meta charset="UTF-8">
        <!-- jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
        <!-- Bootstrap -->
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
        <link rel="stylesheet" href="assets/3rd/codemirror/codemirror.css" />
        <link rel="stylesheet" href="assets/css/app/app.css" />
        <title>Model2gether - Textual diagram editing</title>
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
                                        <i class="sprite-icon-menu icon"></i>
                                        <span class="text hidden">Delete Element</span>
                                    </button>
                                </li>
                                <li class="item col-md-1">
                                    <button id="rename-elm" title="Rename Element" class="mousetrap command update_elm">
                                        <i class="sprite-icon-menu icon"></i>
                                        <span class="text hidden">Rename Element</span>
                                    </button>
                                </li>
                            </ul>
                        </section>
                    </section>

                    <section class="wrapper-canvas">
                        <div id="umlUC">
                            <textarea id="textDiagram" cols="100" rows="22" style="padding: 5px" class="mousetrap"></textarea>
                            <input type="text" id="msgRecebida" readonly="" placeholder="Mensagem" style="border: 0; width: 640px;padding: 5px;" />
                            <textarea id="errorsArea" style="width: 100%; padding: 5px;color: red;" class="hidden" readonly></textarea>
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
                            <h4 id="title-criar-elemento" class="modal-title">Create Element</h4>
                        </div>
                        <div class="modal-body">
                            <form id="formCreateElement" class="form" method="POST" action="#">
                                <input id="elementName" type="text" class="form-control" name="elementName" aria-label="Type in the element name" required aria-required="true" />
                                <button class="btn btn-primary" style="margin-top: 10px;" type="submit" aria-label="Criar">Create!</button>
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
                            <h4 class="modal-title" id="titleModalRelation">Add a new relationship</h4>
                        </div>
                        <div class="modal-body">
                            <form id="formCreateRelation" class="form" method="POST" action="#">
                                <div class="col-sm-6">
                                    <label for="elementSource">From</label>
                                    <select id="elementSource" class="form-control" name="elementSource" aria-label="Select the 'from' element." required="" aria-required="true"></select>
                                </div>
                                <div class="col-sm-6">
                                    <label for="elementTarget">To</label>
                                    <select id="elementTarget" class="form-control" name="elementTarget" aria-label="Select the 'to' element" required="" aria-required="true"></select>
                                </div>
                                <button class="btn btn-primary" style="margin-top: 10px; margin-left: 15px;" type="submit">Create!</button>
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
                            <h4 id="titulo-excluir-elemento" class="modal-title">Delete Element</h4>
                        </div>
                        <div class="modal-body">
                            <form id="formExcludeElement" class="form" method="POST" action="#">
                                <select id="elementToExclude" class="form-control" name="elementToExclude" aria-label="Select the element you want to delete" required="" aria-required="true"></select>
                                <button class="btn btn-primary" style="margin-top: 10px;" type="submit">Delete!</button>
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
                            <h4 id="titulo-renomear-elemento" class="modal-title">Rename Element</h4>
                        </div>
                        <div class="modal-body">
                            <form id="formRenameElement" class="form" method="POST" action="#">
                                <select id="elementToRename" class="form-control" name="elementToRename" aria-label="Select the element you want to rename" required="" aria-required="true"></select>
                                <input id="elementNewName" type="text" class="form-control" name="elementNewName" aria-label="Type in a new name for the element" required aria-required="true" placeholder="New name" style="margin-top: 10px;" />
                                <button class="btn btn-primary" style="margin-top: 10px;" type="submit">Rename!</button>
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
        <script type="application/javascript" src="assets/js/app/websocket.js"></script>
        <script type="application/javascript" src="assets/js/app/app.js"></script>
        <script type="application/javascript" src="assets/js/app/mapKeyBoard.js"></script>
        <script type="application/javascript" src="assets/js/app/libUseCase_textual.js"></script>
    </body>
</html>