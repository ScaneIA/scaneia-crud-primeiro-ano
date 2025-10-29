<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>

    <head>
        <title>Área RH</title>
        <style>
            #msgErro {
                position: fixed;
                bottom: 0;
                right: 0;
                margin: 2%;
                padding: 2%;



                border-radius: 5px;

                background-color: lightcoral;
            }
        </style>
    </head>

    <body>
        <jsp:include page="areaRestritaEmpresa.jsp" />
        <div id="msgErro">
            <p>
                <%=request.getAttribute("mensagem")%>
            </p>
        </div>

        <script>
            setTimeout(function () {
                const msgErro = document.querySelector("#msgErro")
                msgErro.style.display = "none";
            }, 2500)
        </script>
    </body>

    </html>