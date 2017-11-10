package ua.domino.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ник on 29.10.2017.
 */
@WebServlet(name = "HomeServlet", urlPatterns = "/home")
public class HomeServlet extends  AbstractServlet{

    protected void doGetHandler(HttpServletRequest request, HttpServletResponse response) throws Exception{

        request.setAttribute("title", "Домашняя");
        goToJSP("home.jsp", request, response);
    }
}
