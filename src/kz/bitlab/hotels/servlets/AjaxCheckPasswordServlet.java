package kz.bitlab.hotels.servlets;

import com.google.gson.Gson;
import kz.bitlab.hotels.db.DBManager;
import kz.bitlab.hotels.db.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(value = "/ajax_check_pass")
public class AjaxCheckPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("USER");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String oldPassword = request.getParameter("user_password");

        String status = "error";
        String message = "Your current password is not correct!";

        if (user.getPassword().equals(oldPassword)){
            status = "ok";
        }

        HashMap<String, String> result = new HashMap<>();
        result.put("status", status);
        result.put("message", message);

        Gson gson = new Gson();
        out.print(gson.toJson(result));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
