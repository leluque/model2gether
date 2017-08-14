<%--
    Document   : profile
    Created on : 19/05/2015, 12:43:55
    Author     : Leandro Luque
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang='en'>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
        <!-- Bootstrap -->
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
        <link rel="stylesheet" href="assets/css/app/app.css" />

        <title>Profile Editing</title>
    </head>
    <body>
        <c:import url="/WEB-INF/jspf/main-menu.jsp"></c:import>
            <section class="container">

                <section class="row row-offcanvas row-offcanvas-right wrapper-form-and-menu">
                    <div class="col-md-8">
                        <section id="wrap-settings" class="wrapper">

                            <div id="security" class="none panel panel-default">
                                <div class="painel-heading">
                                    <h3 class="panel-title">Profile Editing</h3>
                                    <p class="text">This area allows you to change your username and password.</p>
                                </div>
                                <div class="panel-body">
                                    <form action="ServletController?action=UpdateProfile" method="post" id="security" class="form">
                                        <div class="wrap-component-form">
                                            <label for="user" class="identifier hidden-label">Usuario:</label>
                                            <input id="user" type="text" class="text" name="username" aria-label="usuário" required aria-required="true" placeholder="Type in your username" />
                                        </div>
                                        <div class="wrap-component-form">
                                            <label for="pwd" class="identifier hidden-label">Nova senha:</label>
                                            <input type="password" name="passwd" class="text" id="pwd" aria-label="senha" required aria-required="true" placeholder="Type in your password" />
                                        </div>
                                        <div class="wrap-component-form">
                                            <label for="pwd" class="identifier hidden-label">Confirmar nova senha:</label>
                                            <input type="password" name="passwd2" class="text" id="pwd" aria-label="senha" required aria-required="true" placeholder="Confirm the informed password" />
                                        </div>
                                        <input type="hidden" name="update" value="security" />
                                        <div class="wrap-component-form">
                                            <input type="submit" class="btn btn-default button" title="enviar" aria-label="Update"  value="Update!" />
                                        </div>
                                    </form>
                                </div>
                            </div>

                            <div id="profile" class="panel panel-default">
                                <div class="painel-heading">
                                    <h3 class="panel-title">Profile Editing</h3>
                                    <p class="text">This area allows you to change your e-mail.</p>
                                </div>
                                <div class="panel-body">
                                    <form action="ServletController?action=UpdateProfile" method="post" id="profile" class="form">
                                        <div class="wrap-component-form">
                                            <label for="user" class="identifier hidden-label">Email:</label>
                                            <input id="user" type="email" class="text" name="mail" aria-label="mail" required aria-required="true" placeholder="Type in your e-mail" />
                                        </div>
                                        <input type="hidden" name="update" value="profile" />
                                        <div class="wrap-component-form">
                                            <input type="submit" class="btn btn-default button" title="enviar" aria-label="Update"  value="Update!" />
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </section>
                    <c:choose>
                        <c:when test="${message == 'atualizado'}">
                            <div class="message-sucess">
                                <div class="wrapper-text">
                                    <p class="text">The data was successfully updated.</p>
                                </div>
                            </div>
                        </c:when>
                        <c:when test="${message == 'erro'}">

                            <div class="message-error">
                                <div class="wrapper-text">
                                    <p class="text">An error occurred while updating your profile.</p>
                                </div>
                            </div>
                        </c:when>
                    </c:choose>

                </div><!--/.col-xs-12.col-sm-9-->

                <div class="col-md-4 sidebar-offcanvas" id="sidebar">
                    <div id="menu-settings" class="list-group">
                        <a href="#" data-target="#profile"  class="profile list-group-item active">E-mail</a>
                        <a href="#" data-target="#security" class="security list-group-item">Username and Password</a>
                    </div>
                </div><!--/.sidebar-offcanvas-->
            </section>
        </section>
        <script type="application/javascript" src="assets/3rd/jquery.js"></script>
        <script type="application/javascript" src="assets/3rd/classie.js"></script>
        <script type="application/javascript" src="assets/3rd/mousetrap/mousetrap_min.js"></script>
        <script type="application/javascript" src="assets/js/app/mapKeyBoard.js"></script>
        <script type="application/javascript">
            'use strict';
            window.onload= function() {
            var p = document.querySelector('.profile');
            var s = document.querySelector('.security');

            var buttons = [p, s];

            buttons.forEach(function(button){
            ['click', 'focus'].forEach(function(event){
            button.addEventListener(event, function(){
            var id = this.dataset.target;
            wrapAddClass(this, 'active', '#menu-settings');
            wrapRemoveClass(document.querySelector(id), 'none', '#wrap-settings');
            }, false);
            });
            });

            function wrapAddClass(e, clazz, wrap) {
            var w = document.querySelector(wrap);
            //console.dir(e);
            //console.dir(w);
            for(var i in w.children) {
            //console.dir(menu.children[i]);
            //console.log(typeof w.children[i]);
            if(typeof w.children[i] === "object") {
            classie.remove(w.children[i], clazz);
            }
            }
            classie.add(e, clazz);
            }

            function wrapRemoveClass(e, clazz, wrap) {
            var w = document.querySelector(wrap);
            console.dir(e);
            //console.dir(w.children);
            for(var i in w.children) {
            //console.dir(menu.children[i]);
            //console.log(typeof w.children[i]);
            if(typeof w.children[i] === "object") {
            classie.add(w.children[i], clazz);
            }
            }
            classie.remove(e, clazz);
            }
            var form = document.getElementById('security');
            form.addEventListener('submit', function(evt) {
            var pwd1 = document.getElementById('passwd').value;
            var pwd2 = document.getElementById('passwd2').value;
            //console.log(pwd1, pwd2);
            if(pwd1 !== pwd2) {
            console.log('Different passwords');
            evt.preventDefault();
            return false;
            } else {
            console.log('Equal passwords');
            return true;
            }
            }, false);

            };
        </script>
    </body>
</html>
