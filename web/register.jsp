<%@ page import="java.util.ArrayList" %>
<%@ page import="kz.bitlab.hotels.db.Country" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <%@include file="head.jsp" %>
</head>
<body>
<%@include file="navbar.jsp" %>
<div class="container mt-3">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="/">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">Register user</li>
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
                String fullName = request.getParameter("full_name");
                if (userError != null) {
            %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Error!</strong> User with email <b><%=email%>
            </b> already exists!
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
                <strong>Error!</strong> Passwords are not same!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
            <%
                String cityError = request.getParameter("cityerror");
                if (cityError != null) {
            %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Error!</strong> Choose correct city!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
            <%
                String success = request.getParameter("success");
                if (success != null) {
            %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                User added successfully!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
        </div>

        <div class="col-sm-6 offset-3">
            <form action="/register" method="post" id="register_form_id">
                <div class="form-group">
                    <label>EMAIL : </label>
                    <input type="email" name="email" id="email_id" class="form-control" value="<%=(email!=null?email:"")%>" required>
                </div>
                <div class="form-group">
                    <label>PASSWORD : </label>
                    <input type="password" name="password" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>REPEAT PASSWORD : </label>
                    <input type="password" name="re_password" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>FULL NAME : </label>
                    <input type="text" name="full_name" class="form-control" value="<%=(fullName!=null?fullName:"")%>"
                           required>
                </div>
                <div class="form-group">
                    <label>COUNTRY : </label>
                    <select class="form-control" id="country_id">
                        <option value="0">Select Country</option>
                        <%
                            ArrayList<Country> countries = (ArrayList<Country>) request.getAttribute("countries");
                            if (countries != null && !countries.isEmpty()) {
                                for (Country c : countries) {
                        %>
                        <option value="<%=c.getId()%>"><%=c.getName()%>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label>CITY : </label>
                    <select class="form-control" id="city_id" name="city_id"></select>
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-success" id="sign_up_button">SIGN UP</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        $("#country_id").change(function () {
            countryId = $("#country_id").val();
            $.get("/ajaxcities", {
                country_id: countryId
            }, function (data) {
                citiesArray = JSON.parse(data);
                console.log(citiesArray);
                options = "";
                for (i = 0; i < citiesArray.length; i++) {
                    options += "<option value='" + citiesArray[i]["id"] + "'>" + citiesArray[i]["name"] + "</option> "
                }
                $("#city_id").html(options);
            })
        });
        $("#sign_up_button").click(function (){
            $.get("/ajax_register", {

                user_email : $("#email_id").val()

            }, function (json){

                jsonObj = JSON.parse(json);

                if (jsonObj["status"] == "error"){
                    $("#alert_id").css("display", "block");
                    $("#alert_message_id").html(jsonObj["message"]);
                } else if (jsonObj["status"] == "ok") {
                    $("#register_form_id").submit();
                }
            })
        });
    });
</script>
</html>
