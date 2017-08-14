<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page trimDirectiveWhitespaces="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>model2gether - Shortcuts</title>
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
    <section class="container">
        <section class="content">
            <h1>The following shortcuts may be used in this system:</h1>
            <ul>
                <li>General shortcuts:
                    <ul>
                        <li>Ctrl+Shift+Alt+m creates a new use case diagram;</li>
                    </ul>
                </li>
                <li>Editing mode:
                    <ul>
                        <li>Ctrl+Shift+Alt+f gives focus to the editing area;</li>
                        <li>Ctrl+Shift+Alt+a creates a new actor;</li>
                        <li>Ctrl+Shift+Alt+u creates a new use case;</li>
                        <li>Ctrl+Shift+Alt+r creates a new association relationship;</li>
                        <li>Alt+w creates a new inclusion relationship;</li>
                        <li>Ctrl+Shift+e creates a new extension relationship;</li>
                        <li>Shift+Alt+h creates a new inheritance relationship;</li>
                        <li>Shift+Alt+x deletes an element;</li>
                        <li>Shift+Alt+m renames an element.</li>
                    </ul>
                </li>
            </ul>
        </section>
    </section>
</body>
</html>
