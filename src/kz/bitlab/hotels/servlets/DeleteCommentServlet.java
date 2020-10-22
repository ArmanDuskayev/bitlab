package kz.bitlab.hotels.servlets;

import kz.bitlab.hotels.db.DBManager;
import kz.bitlab.hotels.db.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/deletecomment")
public class DeleteCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("USER");
        String redirect = "/login";

        if (user!=null) {

            try {

                Long userId = Long.parseLong(request.getParameter("user_id"));
                Long commentId = Long.parseLong(request.getParameter("comment_id"));
                Long hotelId = Long.parseLong(request.getParameter("hotel_id"));

                if (user.getId()==userId){
                    if (DBManager.deleteComment(commentId)) {
                        //если твой коммент удален успешно
                        redirect = "/details?id=" + hotelId + "#addCommentDiv";
                    } else {
                        //если твой коммент но ошибка удаления
                        redirect = "/details?id=" + hotelId + "#addCommentDiv&errordelcomm";
                    }
                } else {
                    //если не твой коммент
                    redirect = "/details?id=" + hotelId + "&errorcomment";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        response.sendRedirect(redirect);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
