package kz.bitlab.hotels.servlets;

import kz.bitlab.hotels.db.DBManager;
import kz.bitlab.hotels.db.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/delete")
public class DeleteHotelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("USER");
        String redirect = "/login";

        if (user!=null) {

            try {

                Long hotelId = Long.parseLong(request.getParameter("hotelid"));
                if (!DBManager.deleteHotel(hotelId)) {
                    redirect = "/details?id=" + hotelId + "&errordelete";
                } else {
                    redirect = "/home";
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
