package kz.bitlab.hotels.servlets;

import kz.bitlab.hotels.db.DBManager;
import kz.bitlab.hotels.db.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("re_password");
        String fullName = request.getParameter("full_name");

        String redirect = "/register?passworderror&email=" + (email != null ? email : "") + "&full_name=" + (fullName != null ? fullName : "");

        if (rePassword.equals(password)) {
            redirect = "/register?usererror&email=" + (email != null ? email : "") + "&full_name=" + (fullName != null ? fullName : "");
            User user = DBManager.getUserByEmail(email);

            if (user == null) {

                User newUser = new User(null, email, password, fullName, "/res/avatars/default_man.jpg");
                DBManager.addUser(newUser);
                redirect = "/register?success";

            }
        }

        response.sendRedirect(redirect);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
