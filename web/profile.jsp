<%@ page import="kz.bitlab.hotels.db.Country" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <%@include file="head.jsp" %>
</head>
<body class="pb-5 mb-5">
<%@include file="navbar.jsp" %>
<div class="container mt-3">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="/">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page"><%=currentUser.getFullName()%> from <%=currentUser.getCity().getName() + ", " + currentUser.getCity().getCountry().getName()%></li>
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
                String oldPassError = request.getParameter("oldpasserror");
                if (oldPassError != null) {
            %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Error!</strong> Your current password is not correct!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
            <%
                String newPassError = request.getParameter("newpasserror");
                if (newPassError != null) {
            %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Error!</strong> New passwords are not same!
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
                Password updated successfully!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
            <%
                String pictureUpdated = request.getParameter("pictureupdated");
                if (pictureUpdated != null) {
            %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                Picture updated successfully!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
        </div>
        <div class="col-sm-12">
            <div style="display: flex">
                <div class="col-sm-3 pl-0 mt-2">
                    <img src="<%=currentUser.getPicture()%>" alt="Your Avatar" width="100%;" style="border-radius: 50%;">
                    <%
                        if (!(currentUser.getPicture().equals("/res/avatars/default_man.jpg")||currentUser.getPicture().equals("/res/avatars/default_woman.jpg"))) {
                    %>
                    <div class="text-right">
                        <form action="/deleteavatar" method="post">
                            <button type="submit" class="btn btn-link">
                                <small class="text-muted">Delete picture</small>
                            </button>
                        </form>
                    </div>
                    <%
                        }
                    %>
                </div>

                <div class="col-sm-9">
                    <form action="/updatepictire" method="post">
                        <div class="row mt-2">
                            <div class="col-sm-12">
                                <label>AVATAR :</label>
                                <input type="text" name="url" class="form-control form-control-sm" placeholder="Put URL of picture" required>
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-sm-12">
                                <div class="text-right">
                                    <button class="btn btn-success btn-sm float-center">Update picture</button>
                                </div>
                            </div>
                        </div>
                    </form>

                    <form action="/updateprofile" method="post">
                        <div class="row mt-5">
                            <div class="col-sm-12">
                                <label>FULL NAME :</label>
                                <input type="text" name="full_name" class="form-control form-control-sm" value="<%=currentUser.getFullName()%>" required>
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-sm-12">
                                <div class="text-right">
                                    <button class="btn btn-success btn-sm float-center">Update Full Name</button>
                                </div>
                            </div>
                        </div>
                    </form>

                    <form action="">
                        <div class="form-group">
                            <label>COUNTRY : </label>
                            <select class="form-control form-control-sm" id="country_id">
                                <option value="0">Select Country</option>
                                <%
                                    ArrayList<Country> countries = (ArrayList<Country>) request.getAttribute("countries");
                                    if (countries != null && !countries.isEmpty()) {
                                        for (Country c : countries) {
                                %>
                                <option value="<%=c.getId()%>"><%=c.getName()%></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>CITY : </label>
                            <select class="form-control form-control-sm" id="city_id" name="city_id"></select>
                        </div>
                        <div class="row mt-2">
                            <div class="col-sm-12">
                                <div class="text-right">
                                    <button type="button" class="btn btn-success btn-sm" id="sign_up_button">Change City</button>
                                </div>
                            </div>
                        </div>
                    </form>

                    <form action="/updatepassword" method="post" id="pass_form_id">
                        <div class="row mt-5">
                            <div class="col-sm-12">
                                <label>ENTER YOUR CURRENT PASSWORD :</label>
                                <input type="password" name="old_password" id="old_password" class="form-control form-control-sm" required>
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-sm-12">
                                <label>NEW PASSWORD :</label>
                                <input type="password" name="new_password" class="form-control form-control-sm" required>
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-sm-12">
                                <label>REPEAT NEW PASSWORD :</label>
                                <input type="password" name="re_new_password" class="form-control form-control-sm" required>
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-sm-12">
                                <div class="text-right">
                                    <button type="button" class="btn btn-success btn-sm float-center" id="change_pass_btn_id">Change password</button>
                                </div>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function (){
        $("#change_pass_btn_id").click(function (){
            $.post("/ajax_check_pass", {

                user_password : $("#old_password").val()

            }, function (json){

                jsonObj = JSON.parse(json);

                if (jsonObj["status"] == "error"){
                    $("#alert_id").css("display", "block");
                    $("#alert_message_id").html(jsonObj["message"]);
                } else if (jsonObj["status"] == "ok") {
                    $("#pass_form_id").submit();
                }

            })
        });
    })
</script>
</html>
