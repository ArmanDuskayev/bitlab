package kz.bitlab.hotels.servlets;

import kz.bitlab.hotels.db.DBManager;
import kz.bitlab.hotels.db.Hotel;
import kz.bitlab.hotels.db.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/edithotel")
public class EditHotelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User currentUser = (User) request.getSession().getAttribute("USER");
        String redirect = "/";

        if (currentUser != null) {

            request.setCharacterEncoding("utf8");

            Long id = Long.parseLong(request.getParameter("id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            int stars = Integer.parseInt(request.getParameter("stars"));
            int price = Integer.parseInt(request.getParameter("price"));

            Hotel hotel = DBManager.getHotel(id);

            if (!name.trim().equals("")) {
                hotel.setName(name);
                hotel.setDescription(description);
                hotel.setStars(stars);
                hotel.setPrice(price);

                if (DBManager.saveHotel(hotel)) {
                    redirect = "/edithotel?id=" + id + "&success";
                } else {
                    redirect = "/edithotel?id=" + id + "&error";
                }
            }

        } else {
            redirect = "/login";
        }

        response.sendRedirect(redirect);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("USER");
        String redirect = "/login";

        if (user != null) {
            Hotel hotel = null;

            try {

                Long id = Long.parseLong(request.getParameter("id"));
                hotel = DBManager.getHotel(id);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (hotel != null && user.getId() == hotel.getAuthor().getId()) {
                request.setAttribute("hotel", hotel);
                request.getRequestDispatcher("/edithotel.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/404.jsp").forward(request, response);
            }
        }
        response.sendRedirect(redirect);
    }
}
