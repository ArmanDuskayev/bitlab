package kz.bitlab.hotels.servlets;

import kz.bitlab.hotels.db.DBManager;
import kz.bitlab.hotels.db.Hotel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(value = "/home")
public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<Hotel> hotels = DBManager.getAllHotels();
        request.setAttribute("hotels", hotels);
        request.getRequestDispatcher("/index.jsp").forward(request, response);

    }
}
