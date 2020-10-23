package kz.bitlab.hotels.servlets;

import kz.bitlab.hotels.db.Country;
import kz.bitlab.hotels.db.DBManager;
import kz.bitlab.hotels.db.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(value = "/profile")
public class ProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("USER");

        if (user != null) {

            ArrayList<Country> countries = DBManager.getAllCountries();
            request.setAttribute("countries", countries);
            request.getRequestDispatcher("/profile.jsp").forward(request, response);

        } else {
            response.sendRedirect("/login");
        }

    }
}
