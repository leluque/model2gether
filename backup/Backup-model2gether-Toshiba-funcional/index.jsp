<%--
    Document   : index
    Created on : 17/02/2015, 21:25:59
    Author     : Leandro Luque
--%>
<%@page import="br.com.models.Entity"%>
<%@page import="br.com.util.UserUtil"%>
<%@page import="br.com.dao.UserDao"%>
<%@page trimDirectiveWhitespaces="true" %>
<%@page import="br.com.models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="en" />
<fmt:setBundle basename="br.com.i18n.resources" />
<%

    UserDao dao = new UserDao();
    User usr = (User) dao.findWithoutPassword((Entity) session.getAttribute("user"));
    if (usr != null) {
        usr.setPassword("");
        session.setAttribute("user", usr);
    }

%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
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

        <title><fmt:message key="index.title" /></title>
    </head>
    <body>
        <c:import url="/WEB-INF/jspf/main-menu.jsp"></c:import>
            <section class="container">
                <section class="content">
                    <section class="wrapper-table">
                        <form action="">
                            <table class="table table-hover">
                                <thead>
                                    <tr id="line1" class="line relative">
                                        <th class="title"><fmt:message key="index.name" /></th>
                                    <th class="title" style="text-align: left"><fmt:message key="index.type" /></th>
                                    <th class="title" style="text-align: left"><fmt:message key="index.sharedWith" /></th>
                                    <th class="title">&nbsp;</th>
                                    <th class="title text-center" colspan="2"><fmt:message key="index.representationMode" /></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="diagram" items="${user.myDiagrams}" varStatus="loop">
                                    <tr id="line-${loop.index}" title="${diagram.name}" class="line">
                                        <td class="dropdown text-left">
                                            <span class="dropdown-toggle" data-toggle="dropdown" alt="${diagram.name}" aria-expanded="false">
                                                <a href="#" class="btn-warning btn" data-toggle="modal" data-target="#editDiagramModal" onclick="$('#editDiagramName').val('${diagram.name}');$('#editDiagramId').val('${diagram.id}');"><i class="fa fa-pencil"></i></a>
                                                ${diagram.name}</span>
                                        </td>
                                        <td  style="text-align: left"><fmt:message key="index.useCaseDiagram" /></td>
                                        <td class="last-col relative">
                                            <c:choose>
                                                <c:when test="${not empty diagram.collaborators}">
                                                    <c:forEach var="collaborator" items="${diagram.collaborators}" varStatus="loop">
                                                        <c:set var="out" value="${loop.index==0 ? '' : out} ${collaborator.name} <br />"></c:set>
                                                    </c:forEach>
                                                    <div class="text" style="text-align: left; white-space: nowrap; width: 90%; overflow: hidden; text-overflow: ellipsis">${out}</div>
                                                </c:when>
                                                <c:when test="${empty diagram.collaborators}"><span class="text">-</span></c:when>
                                            </c:choose>
                                        </td>
                                        <td id="c2" class="compartilhar text-center">
                                            <button type="button" class="btn-share btn-primary btn" data-toggle="modal" data-target="#compartilhar" data-diagram="${diagram.id}">
                                                <i class="fa fa-share-alt"></i> Edit Sharing
                                            </button>
                                        </td>
                                        <td class="text-center" class="wrapper-option-edit compartilhar">
                                            <a href="ServletController?action=LoadDiagram&id=${diagram.id}&type=textual&name=${diagram.name}" class="btn-info btn"><i class="fa fa-align-justify"></i> <fmt:message key="index.text" /></a>
                                        </td>
                                        <td class="text-center">
                                            <a href="ServletController?action=LoadDiagram&id=${diagram.id}&type=grafico&name=${diagram.name}" class="btn-info btn"><i class="fa fa-object-group"></i> <fmt:message key="index.diagram" /></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:forEach var="diagram" items="${user.thirdDiagrams}" varStatus="loop">
                                    <tr id="line-${loop.index}" title="${diagram.name}" class="line">

                                        <td class="text-center dropdown">
                                            <span class="dropdown-toggle" data-toggle="dropdown" role="button" data-diagram="${diagram}" alt="${diagram.name}" aria-expanded="false">${diagram.name}</span>
                                        </td>
                                        <td class="text-center">Diagrama de caso de uso (UML)</td>
                                        <td class="last-col relative">
                                            <span class="text">Compartilhado comigo.</span>
                                        </td>

                                        <td id="c2" class="compartilhar">
                                            <input data-toggle="modal" data-target="#compartilhar" name="${diagram.id}" type="button" class="btn-share btn-default button-disabled" alt="compartilhar desabilitado" value="compartilhar" disabled />
                                        </td>
                                        <td class="text-center" class="wrapper-option-edit">
                                            <a href="ServletController?action=LoadDiagram&id=${diagram.id}&type=textual&name=${diagram.name}" class="option-edit">Texto</a>
                                        </td>
                                        <td class="text-center">
                                            <a href="ServletController?action=LoadDiagram&id=${diagram.id}&type=grafico&name=${diagram.name}" class="option-edit">Gráfico</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </form>
                </section>
            </section>
        </section>

        <section id="modal-compartilhar-diagrama">
            <div class="modal fade" id="compartilhar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="exampleModalLabel">Diagram Sharing</h4>
                        </div>
                        <div class="modal-body">
                            <table class="table table-striped">
                                <thead class="table-head">
                                    <tr class="line">
                                        <th class="title">Shared with:</th>
                                        <th class="title">Diagram</th>
                                        <th class="title">Action</th>
                                    </tr>
                                </thead>
                                <tfoot></tfoot>
                                <tbody class="table-body">
                                    <c:forEach var="diagram" items="${user.myDiagrams}" varStatus="loop">
                                        <c:choose>
                                            <c:when test="${not empty diagram.collaborators}">
                                                <c:forEach var="collaborator" items="${diagram.collaborators}" varStatus="loop">
                                                    <tr class="diagrams-shared">
                                                        <td class="text-center">
                                                            <span>${collaborator.name}</span>
                                                        </td>
                                                        <td class="text-center">
                                                            <span class="dropdown-toggle" data-toggle="dropdown" role="button" alt="${diagram.name}" aria-expanded="false">${diagram.name}</span>
                                                        </td>
                                                        <td class="cell">
                                                            <a href="ServletController?action=UnshareDiagram" data-iddiagram="${diagram.id}" data-idcollaborator="${collaborator.id}" class="remove block text-center">
                                                                <div class="glyphicon glyphicon-remove-circle"></div>
                                                            </a>
                                                        </td>

                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                            <c:when test="${empty diagram.collaborators}"><span class="text">-</span></c:when>
                                    </c:choose>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>

                        <div id="compartilhar" class="modal-body">
                            <form id="compartilhar-diagrama" action="ServletController?action=ShareDiagram" method="POST">
                                <div class="form-group">
                                    <label for="email-convite" class="">Share the model with a new user</label>
                                    <input type="email" id="email-convite" required aria-required="true" class="form-control" aria-label="E-mail"  placeholder="Type in the user e-mail" name="email" />
                                </div>
                                <div class="form-group">
                                    <input type="submit" class="btn btn-default button form-control" name="enviar" title="Share" aria-label="Share" value="Share" />
                                </div>
                                <hiddeh name="diagram" id="data-diagram"></hiddeh>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>


        <script type="application/javascript" src="assets/3rd/jquery.js"></script>
        <script type="application/javascript" src="assets/3rd/bootstrap/js/bootstrap_min.js"></script>
        <script type="application/javascript" src="assets/3rd/bootstrap/js/collapse.js"></script>
        <script type="application/javascript" src="assets/3rd/mousetrap/mousetrap_min.js"></script>
        <script type="application/javascript" src="assets/js/app/mapKeyBoard.js"></script>

        <script type="application/javascript">
            'use strict';
            $('#myModal, #compartilhar').on('show.bs.modal', function(evt) {/*console.log(evt);*/});
            $('.btn-share').on('click', function(e) {
            var diagram = $(this).data('diagram');
            document.getElementById("data-diagram").value = diagram;
            });

            //window.onload = function () {
            var formShare = document.getElementById('compartilhar-diagrama');
            formShare.addEventListener("submit", function(event){
            event.preventDefault();
            //console.dir(this);
            var email = this[0].value;
            var id = document.getElementById("data-diagram").value;
            //console.log(email, id)
            $.ajax({
            url: this.action,
            type: "POST",
            dataType: "text",
            data: {
            email: email,
            diagram: id
            },
            statusCode: {
            404: function(ans) {
            console.log("statusCode 404");
            //console.dir(ans);
            }
            ,500: function() {console.log("statusCode 500");}
            ,200: function(ans) {
            console.dir("statusCode 200");
            console.log(ans);
            var builderHTML;
            if(ans === "shared") {
            builderHTML =
            "<div class=\"message-success\">"
            .concat("<div class=\"wrapper-text\">")
            .concat("<p class=\"text\">The model was successfully shared!</p>")
            .concat("</div>")
            .concat("</div>");
            setTimeout(function(){ $('#formReload').submit(); }, 1500);
            } else if(ans === "notshared") {
            builderHTML =
            "<div class=\"message-error\">"
            .concat("<div class=\"wrapper-text\">")
            .concat("<p class=\"text\">It was not possible to share the model.</p>")
            .concat("<p class=\"text\">The user is unknown or the model is already shared with the user.</p>")
            .concat("</div>")
            .concat("</div>");
            }
            var boxMsgSuccess = $('#compartilhar-diagrama').find(".message-success");
            var boxMsgError = $('#compartilhar-diagrama').find(".message-error");
            if(boxMsgSuccess) {
            $(boxMsgSuccess).remove();
            }
            if(boxMsgError){
            $(boxMsgError).remove();
            }
            $('#compartilhar').find('#compartilhar-diagrama').append(builderHTML);
            }
            },
            sucess: function(ans) {
            console.log("sucess");
            console.dir(ans);            
            },
            error:  function(xhr, statusCode, err) {
            console.log("error");
            console.dir(xhr);
            }
            });
            });

            var formunshare = document.querySelectorAll('.remove');
            //console.log(formunshare);
            for(var i in formunshare) {
            //console.log(formunshare[i], formunshare[i].addEventListener);
            var link = formunshare[i];
            //console.log(link, typeof link);
            if(typeof link === "object") {
            link.addEventListener("click", function(event){
            event.preventDefault();
            var anchor = this;
            var url = this.pathname.concat(this.search);
            var obj = {
            idcollaborator: this.dataset.idcollaborator,
            iddiagram: this.dataset.iddiagram
            };
            console.dir(url);
            console.dir(obj);
            $.ajax({
            url: url,
            type: "POST",
            datatype: "text",
            data: obj,
            statusCode: {
            500: function(){
            console.log("statusCode 500");
            },
            200: function(data, textStatus) {
            console.log(data);
            console.log(textStatus);
            var builderHTML;
            var str;
            if(data === "unshared") {
            builderHTML =
            "<div class=\"message-success\">"
            .concat("<div class=\"wrapper-text\">")
            .concat("<p class=\"text\">Sharing removed!</p>")
            .concat("</div>")
            .concat("</div>");
            } else if(data === "fail-unshared") {
            builderHTML =
            "<div class=\"message-error\">"
            .concat("<div class=\"wrapper-text\">")
            .concat("<p class=\"text\">An error happened</p>")
            .concat("<p class=\"text\">while removing the sharing.</p>")
            .concat("</div>")
            .concat("</div>");
            }
            var boxMsgSuccess = $('#compartilhar-diagrama').find(".message-success");
            var boxMsgError = $('#compartilhar-diagrama').find(".message-error");
            if(boxMsgSuccess) {
            $(boxMsgSuccess).remove();
            }
            if(boxMsgError){
            $(boxMsgError).remove();
            }
            $('#compartilhar').find('#compartilhar-diagrama').append(builderHTML);
            console.dir(anchor);
            var p = $(anchor).parent().parent();
            console.log(p);
            $(p).fadeOut("slow", function(){
            $(this).remove();
            });
            }
            },
            sucess: function(data, textStatus, jqXHR) {

            },
            error: function(xhr, textStatus, error) {
            console.log(xhr, textStatus, error);
            }
            });
            });
            }
            /*


            formunshare[i].addEventListener("click", function(event){
            event.preventDefault();
            return false;

            });
            */
            }
            /*
            window.onload = function(){
            NodeList.prototype.forEach = Array.prototype.forEach;
            HTMLCollection.prototype.forEach = Array.prototype.forEach;
            var lineTable = document.querySelectorAll('table>tbody>tr.line');
            lineTable.forEach(function(e) {
            //console.log(e);
            ['mouseover', 'mouseout'].forEach(function(nameEvent) {
            //alert(nameEvent);
            e.addEventListener(nameEvent, function(){
            var element = this;
            //console.dir(element.childNodes[9].firstElementChild);
            var child = element.childNodes[9].firstElementChild;
            //console.log(child.tagName);

            if(child.tagName === 'INPUT') {
            classie.toggle(child, 'hidden');
            } else {
            child = child.childNodes[1];
            //console.log('form', child);
            classie.toggle(child, 'hidden');
            }
            //console.log(typeof element);
            //var td = this.children[4];
            //console.dir(td);

            //
            }, false);
            });

            })
            };
            */

            $(document).ready(function(){
            $('#compartilhar').on('hidden.bs.modal', function () {
            $('#formReload').submit();
            });
            
//            $('#editDiagramModal').on('hidden.bs.modal', function () {
//            $('#formReload').submit();
//            });

            });
        </script>

        <form id="formReload"></form>

        <section id="modal-editar-diagrama">
            <div class="modal fade" id="editDiagramModal" tabindex="-1" role="dialog" aria-labelledby="modal-editar-diagrama-titulo" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="modal-editar-diagrama-titulo"><fmt:message key="index.editDiagramTitle" /></h4>
                        </div>
                        <div class="modal-body">
                            <form action='ServletController' method='get'>
                                <input type='hidden' name='action' value='UpdateModel' />
                                <input type='hidden' name='id' value='' id='editDiagramId' />
                                <div class="form-group">
                                    <label for="modos" class="control-label">Diagram name:</label>
                                    <input type="text" required aria-required="true" class="form-control" id="editDiagramName" aria-label="enviar" placeholder="<fmt:message key="mainMenu.diagramNamePlaceholder" />" name="name"/>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="submit" class="btn btn-danger form-control" name="remove"  title="Remove diagram!" aria-label="enviar" value="Remove diagram!" />
                                    </div>
                                    <div class="col-sm-6">
                                        <input type="submit" class="btn btn-primary form-control" name="update" title="Save changes!" aria-label="enviar" value="Save changes!" />
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>