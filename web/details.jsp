<%@ page import="kz.bitlab.hotels.db.Hotel" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="kz.bitlab.hotels.db.Comment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Details</title>
    <%@include file="head.jsp" %>
    <style>
        img {
            max-width: 100%;
        }

        .btn-link.focus, .btn-link:focus {
            -webkit-box-shadow: none !important;
            box-shadow: none !important;
        }
    </style>
</head>
<body class="mb-5 pb-5">
<%@include file="navbar.jsp" %>
<div class="container mt-3">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="/">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">Detail</li>
        </ol>
    </nav>
</div>
<div class="container mt-3">
    <div class="row">
        <div class="col-sm-12">
            <%
                String errorDelete = request.getParameter("errordelete");
                if (errorDelete != null) {
            %>
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                COULDN'T DELETE THIS BLOG!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
            <%
                String errorComment = request.getParameter("errorcomment");
                if (errorComment != null) {
            %>
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                COULDN'T DELETE! THIS IS NOT YOUR COMMENT
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
            <%
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                Hotel hotel = (Hotel) request.getAttribute("hotel");
                if (hotel != null) {

            %>
            <div class="jumbotron pb-3 pt-5">
                <h1><%=hotel.getName()%>
                </h1>
                <h3>For <%=hotel.getPrice()%> USD</h3>
                <h4><%=hotel.getStars()%> stars</h4>
                <hr class="my-4">
                <p><%=hotel.getDescription()%>
                </p>
                <div class="d-flex w-100 justify-content-between mt-5">
                    <label style="color: gray; font-size: 14px;">posted by <%=hotel.getAuthor().getFullName()%>
                        at <%=formatter.format(hotel.getAddedDate())%>
                    </label>
                    <%
                        if (currentUser != null) {
                            if (currentUser.getId() == hotel.getAuthor().getId()) {
                    %>
                    <div class="text-right">
                        <a class="btn btn-primary btn-sm" href="/edithotel?id=<%=hotel.getId()%>" role="button">Edit</a>
                        <button type="button" class="btn btn-danger btn-sm" data-toggle="modal"
                                data-target="#staticBackdrop">
                            Delete
                        </button>
                    </div>
                    <%
                            }
                        }
                    %>
                </div>
            </div>

            <%
                if (currentUser != null) {
            %>
            <div class="panel" id="addCommentDiv">
                <div class="panel-body">
                    <form action="/addcomment" method="post">
                        <input type="hidden" name="hotel_id" value="<%=hotel.getId()%>">
                        <h5>Add comment:</h5>
                        <textarea class="form-control" rows="3" placeholder="Add your comment here"
                                  name="comment"></textarea>
                        <div class="text-right">
                            <button class="btn btn-sm btn-success mt-2" type="submit">Comment</button>
                        </div>
                    </form>
                </div>
            </div>
            <%
            } else {
            %>
            <div class="text-center">
                Only registered users can add comments, Please <a href="/register">register</a> or <a href="/login">login</a>.
            </div>
            <%
                }
            %>

            <%
                String errorDelComment = request.getParameter("errordelcomm");
                if (errorDelComment != null) {
            %>
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                COULDN'T DELETE THIS COMMENT!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>

            <h4 class="text-center mt-3">Comments</h4>
            <div class="mt-3">
                <%
                    ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("comments");
                    if (comments != null && !comments.isEmpty()) {
                        for (Comment c : comments) {
                %>
                <div class="media mt-4">
                    <img src="<%=c.getUser().getPicture()%>" height="50px;" style="border-radius: 50%;" class="mr-3">
                    <div class="media-body">
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mt-0"><%=c.getUser().getFullName()%>
                            </h5>
                            <div class="mt-0 mr-0">
                                <small class="text-muted"><%=formatter.format(c.getAddedDate())%>
                                </small>
                            </div>
                        </div>
                        <%=c.getComment()%>

                        <div class="text-right">
                            <%
                                if (currentUser != null) {
                            %>
                            <form action="/deletecomment" method="post">
                                <input type="hidden" name="hotel_id" value="<%=hotel.getId()%>">
                                <input type="hidden" name="user_id" value="<%=c.getUser().getId()%>">
                                <input type="hidden" name="comment_id" value="<%=c.getId()%>">
                                <button type="submit" class="btn btn-link"><small class="text-muted">Delete</small>
                                </button>
                            </form>
                            <hr class="my-1">
                            <%
                            } else {
                            %>
                            <br>
                            <hr class="my-1">
                            <%
                                }
                            %>
                        </div>

                    </div>
                </div>
                <%
                    }
                } else {
                %>
                <div class="text-center">No comments here...</div>
                <%
                    }
                %>
            </div>

            <%
                }
            %>

            <!-- Modal -->
            <div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1"
                 role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <form action="/delete" method="post">
                            <input type="hidden" name="hotelid" value="<%=hotel.getId()%>">
                            <div class="modal-header">
                                <h5 class="modal-title" id="staticBackdropLabel">Do you really want to delete this
                                    Hotel?</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                Are you sure?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                                <button class="btn btn-primary">Yes, delete</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
