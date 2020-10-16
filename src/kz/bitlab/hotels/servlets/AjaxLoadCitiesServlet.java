package kz.bitlab.hotels.servlets;

import kz.bitlab.hotels.db.City;
import kz.bitlab.hotels.db.DBManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(value = "/ajaxcities")
public class AjaxLoadCitiesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("country_id"));
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        ArrayList<City> cities = DBManager.getCitiesByCountryId(id);

        if (cities != null && !cities.isEmpty()) {
            for (City c : cities) {
                out.print("<option value = '" + c.getId() + "'>" + c.getName() + "</option>");
            }
        }
    }
}
