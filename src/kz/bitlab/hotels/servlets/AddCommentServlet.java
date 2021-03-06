package kz.bitlab.hotels.servlets;

import kz.bitlab.hotels.db.Comment;
import kz.bitlab.hotels.db.DBManager;
import kz.bitlab.hotels.db.Hotel;
import kz.bitlab.hotels.db.User;
import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/addcomment")
public class AddCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User currentUser = (User) request.getSession().getAttribute("USER");

        if (currentUser != null) {

            request.setCharacterEncoding("utf8");

            Long hotelId = Long.parseLong(request.getParameter("hotel_id"));

            Hotel hotel = DBManager.getHotel(hotelId);

            if (hotel != null) {

                Long parentId = Long.parseLong(request.getParameter("parent_id"));

                String textComment = request.getParameter("comment");

                if (!textComment.trim().equals("")) {

                    Comment comment = new Comment();
                    comment.setComment(StringEscapeUtils.escapeHtml4(textComment));
                    comment.setUser(currentUser);
                    comment.setHotel(hotel);
                    comment.setParentId(parentId);

                    if (DBManager.addComment(comment)) {
                        response.sendRedirect("/details?id=" + hotel.getId() + "#addCommentDiv");
                    }

                }

            } else {
                response.sendRedirect("/");
            }

        } else {
            response.sendRedirect("/login");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
