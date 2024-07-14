<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Asiakl</title>
    <link rel="icon" type="image/png" href="images/favicon.png">
    <style>
        body{
            background-image: url(${additionaleffect}), url(${backgroundimage});
            background-color: black;
            background-repeat: no-repeat;
            background-position: center;
            background-attachment: fixed;
            background-size: cover;
        }
        #login{
            float: left;
        }
        #content{
            background-color: black;
            color: #f0b986d7;
            width: 20vw;
            min-width: 200px;
            max-width: 100%;
            height: auto;
            padding: 15px;
            border-radius: 15px;
            box-shadow: 0px 0px 18px #b9833bd7;
            float: left;
        }
        #text{
            margin: 0;
        }
        .choice{
            margin-top: 10px;
            margin-bottom: 10px;
            text-align: center;
        }
        #right_picture{
            width: 50%;
            float: right;
        }
        #img-button {
            background: none;
            border: none;
            padding: 0;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div id="content">
    <% if(session == null || session.getAttribute("userId") == null){ %>
    <form id="login" action="${pageContext.request.contextPath}/login" method="post">
        <label for="username">Никнейм</label>
        <input id="username" type="text" name="username"><br>
        <label for="password">Пароль</label>
        <input id="password" type="password" name="password"><br>
        <input type="image" src="images/start.png" alt="Start"><br>
        ${error}
    </form>
    <% } %>
    <p id="text">${content}</p>
    <form class="choice" action="${pageContext.request.contextPath}/choice" method="post">
        <input type="hidden" name="action" value="aggression">
        <button type="submit" style="background: none; border: none; color: #d51d1d; font: inherit; cursor: pointer;">
            ${aggression}
        </button>
    </form>
    <form class="choice" action="${pageContext.request.contextPath}/choice" method="post">
        <input type="hidden" name="action" value="peacefulness">
        <button type="submit" style="background: none; border: none; color: #0d750d; font: inherit; cursor: pointer;">
            ${peacefulness}
        </button>
    </form>
</div>
<div class style="float: right">
    <form action="${pageContext.request.contextPath}/return" method="post">
        <input type="hidden" name="action" value="update">
        <button type="submit" id="img-button">
            <img id="right_picture" src="${rightpicture}" alt="Asiakl">
        </button>
    </form>
</div>
<script type="text/javascript">
    window.history.pushState(null, null, window.location.href);
    window.onpopstate = function() {
        window.history.go(1);
    };
</script>
</body>
</html>