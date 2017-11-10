package ua.domino.servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ник on 28.10.2017.
 */
public abstract class AbstractServlet extends HttpServlet{

    private static final Logger LOGGER = Logger.getLogger(AbstractServlet.class);

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            doGetHandler(req, resp);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            this.goToJSP("error.jsp", req, resp);
        } catch (Throwable e) {
            LOGGER.error("Application can't fulfil this request", e);
            this.goToErrorPage(e, req, resp);
        }

    }

    protected void doGetHandler(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            doPostHandler(req, resp);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            this.redirectToPageNotFaund(req, resp);
        } catch (Throwable e) {
            LOGGER.error("Application can't fulfill this request", e);
            this.goToErrorPage(e, req, resp);
        }

    }

    protected void doPostHandler(HttpServletRequest req, HttpServletResponse resp) throws  Exception {

    }

    protected final void goToErrorPage(Throwable ex, HttpServletRequest request,
                                       HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("javax.servlet.error.exception", ex);
        this.goToJSP("error.jsp", request, response);

    }
    protected final void goToJSP(String page, HttpServletRequest request,
                                 HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("link", "../JSP/" + page);
        request.getRequestDispatcher("/WEB-INF/JSP/page-template.jsp")
                .forward(request, response);
    }
    protected final void redirectToPageNotFaund(HttpServletRequest request,
                                                HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("notfound", "true");
        goToJSP("error.jsp", request, response);
    }

    protected final void redirectRequest(String path,
                                         HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect( path);
    }
}
