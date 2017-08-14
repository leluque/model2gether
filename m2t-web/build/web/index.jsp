<%--
    Document   : index
    Created on : 17/02/2015, 21:25:59
    Author     : Leandro Luque
--%>
<%@page import="br.com.util.RequestHelper"%>
<%@page import="br.com.models.Entity"%>
<%@page import="br.com.util.UserUtil"%>
<%@page import="br.com.dao.UserDao"%>
<%@page trimDirectiveWhitespaces="true" %>
<%@page import="br.com.models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%

    UserDao dao = new UserDao();
    User usr = (User) dao.findWithoutPassword((Entity) session.getAttribute("user"));
    if (usr != null) {
        usr.setPassword("");
        session.setAttribute("user", usr);
    }

%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : not empty cookie.model2getherlang.value ? cookie.model2getherlang.value : pageContext.request.locale.language}" scope="session" />
<a href="index.jsp"></a>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="br.com.i18n.resources" />
<!DOCTYPE html>
<html lang="${language}">
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="assets/3rd/jsuml2/css-core/UDStyle.css" media="screen" />
        <!-- jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
        <!-- Bootstrap -->
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
        <link rel="stylesheet" href="assets/3rd/codemirror/codemirror.css" />
        <link rel="stylesheet" href="assets/css/app/app.css" />
        <title><fmt:message key="index.title" /></title>
    </head>
    <body>
        <c:import url="/WEB-INF/jspf/main-menu.jsp"></c:import>
        <header style="text-align:center" id="conteudo"><fmt:message key="content" /></header>
            <section class="container" role="main">
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
                                                <a href="#" class="btn-success btn" data-toggle="modal" data-target="#editDiagramModal" onclick="$('#editDiagramName').val('${diagram.name}');
                                                        $('#editDiagramId').val('${diagram.id}');"><i class="fa fa-pencil"></i>${diagram.name}</a>
                                            </span>
                                        </td>
                                        <td  style="text-align: left"><fmt:message key="index.useCaseDiagram" /></td>
                                        <td class="last-col relative">
                                            <c:choose>
                                                <c:when test="${not empty diagram.collaborators}">
                                                    Sim
                                                </c:when>
                                                <c:when test="${empty diagram.collaborators}"><span class="text">-</span></c:when>
                                            </c:choose>
                                        </td>
                                        <td id="c2" class="compartilhar text-center">
                                            <button type="button" class="btn-share btn-primary btn" data-toggle="modal" data-target="#compartilhar" data-diagram="${diagram.id}">
                                                <i class="fa fa-share-alt"></i> <fmt:message key="index.editSharing" />
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
                                            <span class="dropdown-toggle" data-toggle="dropdown" alt="${diagram.name}" aria-expanded="false" data-diagram="${diagram}">
                                                <a href="#" class="btn-success btn" data-toggle="modal" data-target="#editDiagramModal" onclick="$('#editDiagramName').val('${diagram.name}');
                                                        $('#editDiagramId').val('${diagram.id}');"><i class="fa fa-pencil"></i>${diagram.name}</a>
                                            </span>
                                        </td>
                                        <td class="text-center"><fmt:message key="index.useCaseDiagram" /></td>
                                        <td class="last-col relative">
                                            <span class="text"><fmt:message key="index.shareWithMe" /></span>
                                        </td>

                                        <td id="c2" class="compartilhar">
                                            <%--<button type="button" class="btn-share btn-primary btn" data-toggle="modal" data-target="#compartilhar" data-diagram="${diagram.id}">
                                                <i class="fa fa-share-alt"></i> <fmt:message key="index.editSharing" />
                                            </button>
                                            <input data-toggle="modal" data-target="#compartilhar" name="${diagram.id}" type="button" class="btn-share btn-default button-disabled" alt="<fmt:message key='index.share' />" value="<fmt:message key="index.share" />" disabled />--%>
                                        </td>
                                        <td class="text-center" class="wrapper-option-edit">
                                            <a href="ServletController?action=LoadDiagram&id=${diagram.id}&type=textual&name=${diagram.name}" class="btn-info btn"><i class="fa fa-align-justify"></i> <fmt:message key="index.text" /></a>
                                            <%--<a href="ServletController?action=LoadDiagram&id=${diagram.id}&type=textual&name=${diagram.name}" class="option-edit"><fmt:message key="index.textual" /></a>--%>
                                        </td>
                                        <td class="text-center">
                                            <a href="ServletController?action=LoadDiagram&id=${diagram.id}&type=grafico&name=${diagram.name}" class="btn-info btn"><i class="fa fa-object-group"></i> <fmt:message key="index.diagram" /></a>
                                            <%--                                            <a href="ServletController?action=LoadDiagram&id=${diagram.id}&type=grafico&name=${diagram.name}" class="option-edit"><fmt:message key="index.graphic" /></a>--%>
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
                            <h4 class="modal-title" id="exampleModalLabel"><fmt:message key="index.diagramSharing" /></h4>
                        </div>
                        <div class="modal-body">
                            <table class="table table-striped">
                                <thead class="table-head">
                                    <tr class="line">
                                        <th class="title"><fmt:message key="index.sharedWith" /></th>
                                        <th class="title"><fmt:message key="index.diagram" /></th>
                                        <th class="title"><fmt:message key="index.action" /></th>
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
                                                            <span>${collaborator.login}</span>
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
                                    <label for="email-convite" class=""><fmt:message key="index.shareModel" /></label>
                                    <input type="email" id="email-convite" required aria-required="true" class="form-control" aria-label="E-mail"  placeholder="<fmt:message key="index.userMail" />" name="email" />
                                </div>
                                <div class="form-group">
                                    <input type="submit" class="btn btn-default button form-control" name="enviar" title="<fmt:message key="index.share" />" aria-label="<fmt:message key="index.share" />" value="<fmt:message key="index.share" />" />
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
            .concat("<p class=\"text\"><fmt:message key="index.successfullyShared" /></p>")
            .concat("</div>")
            .concat("</div>");
            setTimeout(function(){ $('#formReload').submit(); }, 1500);
            } else if(ans === "notshared") {
            builderHTML =
            "<div class=\"message-error\">"
            .concat("<div class=\"wrapper-text\">")
            .concat("<p class=\"text\"><fmt:message key="index.sharingErrorAlreadySharedLine1" /></p>")
            .concat("<p class=\"text\"><fmt:message key="index.sharingErrorAlreadySharedLine2" /></p>")
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
            .concat("<p class=\"text\"><fmt:message key="index.sharingRemoved" /></p>")
            .concat("</div>")
            .concat("</div>");
            } else if(data === "fail-unshared") {
            builderHTML =
            "<div class=\"message-error\">"
            .concat("<div class=\"wrapper-text\">")
            .concat("<p class=\"text\"><fmt:message key="index.sharingRemovalErrorLine1" /></p>")
            .concat("<p class=\"text\"><fmt:message key="index.sharingRemovalErrorLine2" /></p>")
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
            }

            $(document).ready(function(){
            $('#compartilhar').on('hidden.bs.modal', function () {
            $('#formReload').submit();
            });

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
                                    <label for="modos" class="control-label"><fmt:message key="index.diagramName" /></label>
                                    <input type="text" required aria-required="true" class="form-control" id="editDiagramName" aria-label="enviar" placeholder="<fmt:message key="mainMenu.diagramNamePlaceholder" />" name="name"/>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <input type="submit" class="btn btn-danger form-control" name="remove"  title="<fmt:message key="index.removeDiagram" />" aria-label="enviar" value="<fmt:message key="index.removeDiagram" />" />
                                    </div>
                                    <div class="col-sm-6">
                                        <input type="submit" class="btn btn-primary form-control" name="update" title="<fmt:message key="index.saveChanges" />" aria-label="enviar" value="<fmt:message key="index.saveChanges" />" />
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