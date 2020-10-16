package kz.bitlab.hotels.servlets;

import kz.bitlab.hotels.db.City;
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

@WebServlet(value = "/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("re_password");
        String fullName = request.getParameter("full_name");
        Long cityId = 0L;

        try {
            cityId = Long.parseLong(request.getParameter("city_id"));
        } catch (Exception e) {

        }

        String redirect = "/register?passworderror&email=" + (email != null ? email : "") + "&full_name=" + (fullName != null ? fullName : "");

        if (rePassword.equals(password)) {

            redirect = "/register?usererror&email=" + (email != null ? email : "") + "&full_name=" + (fullName != null ? fullName : "");
            User user = DBManager.getUserByEmail(email);

            if (user == null) {

                City city = DBManager.getCityById(cityId);
                redirect = "/register?cityerror&email=" + (email != null ? email : "") + "&full_name=" + (fullName != null ? fullName : "");
                System.out.println("test point 3 + " + city);
                if (city!=null) {
                    User newUser = new User(null, email, password, fullName, "/res/avatars/default_man.jpg", city);
                    DBManager.addUser(newUser);
                    redirect = "/register?success";
                }

            }
        }

        response.sendRedirect(redirect);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<Country> countries = DBManager.getAllCountries();
        request.setAttribute("countries", countries);
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
