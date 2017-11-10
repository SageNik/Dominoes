<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8"/>

    <%--<script type="text/javascript"--%>
            <%--src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>--%>
    <%--<script type="text/javascript"--%>
            <%--src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>--%>

    <title>${title}</title>

</head>
<header>
    <div id="block-header" >
        <div>
            <table align = "center" >
                <tr >
                    <td id="block-top" >
                        <h1> D O M I N O E S</h1>
                    </td>
                </tr>
            </table>
<p><a href="/home"> На Главную </a></p>
        </div>

    </div>
</header>
<body>

<jsp:include page="${link}" />
</body>
</html>
