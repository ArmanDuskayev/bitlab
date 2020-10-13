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

        System.out.println("control point 1");
        if (user!=null) {

            System.out.println("control point 2");
            try {

                System.out.println("control point 3");
                Long userId = Long.parseLong(request.getParameter("user_id"));
                Long commentId = Long.parseLong(request.getParameter("comment_id"));
                Long hotelId = Long.parseLong(request.getParameter("hotel_id"));

                if (user.getId()==userId){
                    System.out.println("control point 4");
                    if (DBManager.deleteComment(commentId)) {
                        System.out.println("control point 5");
                        //если твой коммент удален успешно
                        redirect = "/details?id=" + hotelId + "#addCommentDiv";
                    } else {
                        System.out.println("control point 6");
                        //если твой коммент но ошибка удаления
                        redirect = "/details?id=" + hotelId + "#addCommentDiv&errordelcomm";
                    }
                } else {
                    System.out.println("control point 7");
                    //если не твой коммент
                    redirect = "/details?id=" + hotelId + "&errorcomment";
                }

            } catch (Exception e) {
                System.out.println("control point 8");
                e.printStackTrace();
            }

        }

        System.out.println("control point 9");
        response.sendRedirect(redirect);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
