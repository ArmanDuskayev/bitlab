<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <%@include file="head.jsp" %>
</head>
<body>
<%@include file="navbar.jsp" %>
<div class="container mt-3">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="/">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">Login</li>
        </ol>
    </nav>
</div>
<div class="container mt-3">
    <div class="row">
        <div class="col-sm-12">
            <div class="alert alert-danger" role="alert" id="alert_id" style="display: none;">
                <div id="alert_message_id"></div>
            </div>
            <%
                String userError = request.getParameter("usererror");
                String email = request.getParameter("email");
                if (userError != null) {
            %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Error!</strong> User with email <b><%=email%></b> doesn't exist!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
            <%
                String passwordError = request.getParameter("passworderror");
                if (passwordError != null) {
            %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Error!</strong> Incorrect password!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
        </div>

        <div class="col-sm-6 offset-3">
            <form action="/login" method="post" id="login_form_id">
                <div class="form-group">
                    <label>EMAIL : </label>
                    <input type="email" name="email" id="email_id" class="form-control" value="<%=(email!=null?email:"")%>" required>
                </div>
                <div class="form-group">
                    <label>PASSWORD : </label>
                    <input type="password" name="password" id="password_id" class="form-control" required>
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-success" id="sign_in_button">SIGN IN</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function (){
        $("#sign_in_button").click(function (){
            $.get("/ajax_check_user", {

                user_email : $("#email_id").val(),
                user_password : $("#password_id").val()

            }, function (json){

                jsonObj = JSON.parse(json);

                if (jsonObj["status"] == "error"){
                    $("#alert_id").css("display", "block");
                    $("#alert_message_id").html(jsonObj["message"]);
                } else if (jsonObj["status"] == "ok") {
                    $("#login_form_id").submit();
                }

            })
        });
    })
</script>
</html>
