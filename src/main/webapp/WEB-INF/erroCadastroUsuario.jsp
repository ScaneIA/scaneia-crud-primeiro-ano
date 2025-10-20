<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        #msgErro{
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
    <jsp:include page="VIEW/areaRestritaEmpresa/areaRestritaEmpresa.jsp"/>
    <div id="msgErro">
        <p><%=request.getAttribute("mensagem")%></p>
    </div>

    <script>
        setTimeout(function(){
            const msgErro = document.querySelector("#msgErro")
            msgErro.style.display = "none";
        }, 2500)
    </script>
</body>
</html>