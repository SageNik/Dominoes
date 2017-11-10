package ua.domino.servlet;


import ua.domino.entity.SetDominoes;
import ua.domino.service.impl.SetDominoServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ник on 29.10.2017.
 */
public class GenerationServlet extends AbstractServlet {

    protected void doGetHandler(HttpServletRequest request, HttpServletResponse response) throws Exception{

        request.setAttribute("title", "Наборы костей");
        goToJSP("generation-page.jsp", request,response);
    }

    protected void doPostHandler(HttpServletRequest request, HttpServletResponse response)throws Exception{

        String amountParam = request.getParameter("amount");
        if(amountParam!=null && amountParam!=""){
            Integer amount = Integer.parseInt(amountParam);
            if(amount == 0){
                amount = 1+(int)(Math.random()*28);
            }

            SetDominoServiceImpl setDominoService = new SetDominoServiceImpl();
            SetDominoes setDominoes = setDominoService.create(amount);
            request.setAttribute("setDominoes", setDominoes);
            doGetHandler(request, response);
        }
    }
}
