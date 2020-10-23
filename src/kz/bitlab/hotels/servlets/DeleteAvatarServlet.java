package kz.bitlab.hotels.servlets;

import kz.bitlab.hotels.db.DBManager;
import kz.bitlab.hotels.db.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/deleteavatar")
public class DeleteAvatarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("USER");
        String redirect = "/login";

        if (user!=null) {

            try {

                if (!user.getPicture().equals("/res/avatars/default_man.jpg")) {

                    user.setPicture("/res/avatars/default_man.jpg");

                    if (DBManager.updateUserPicture(user)) {

                        redirect = "/profile?pictureupdated";

                    }

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
