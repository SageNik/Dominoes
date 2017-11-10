package ua.domino.servlet;

import ua.domino.entity.SetDominoes;
import ua.domino.model.HistoryChainDominoesViewModel;
import ua.domino.model.ResultChainDominoesViewModel;
import ua.domino.service.impl.ChainDominoesServiceImpl;
import ua.domino.service.impl.SetDominoServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ник on 29.10.2017.
 */
public class BuildingServlet extends AbstractServlet {

    private SetDominoServiceImpl setDominoService = new SetDominoServiceImpl();
    private ChainDominoesServiceImpl chainDominoesService = new ChainDominoesServiceImpl();

    protected void doGetHandler(HttpServletRequest request, HttpServletResponse response) throws Exception{

        List<SetDominoes> setDominoesList = setDominoService.getAllSetDominoes();
        List<HistoryChainDominoesViewModel> chainDominoesList = chainDominoesService.getAllChainDominoes();
        Collections.reverse(setDominoesList);
        Collections.reverse(chainDominoesList);

        request.setAttribute("historyChainDominoes", chainDominoesList);
        request.setAttribute("setList", setDominoesList);
        request.setAttribute("title", "Построение цепочек");
        goToJSP("build-page.jsp", request,response);
    }

    protected void doPostHandler(HttpServletRequest request, HttpServletResponse response)throws Exception{

        String setDominoesIdParam = request.getParameter("selectSet");
        String modeParam = request.getParameter("selectMode");
        if(setDominoesIdParam!=null && setDominoesIdParam!="" && modeParam!="" && modeParam!=null){

            Long setDominoesId = Long.parseLong(setDominoesIdParam);
            SetDominoes currentSetDominoes = setDominoService.getSetDominoesById(setDominoesId);
            List<ResultChainDominoesViewModel> chainDominoesViewList = chainDominoesService.buildChains(modeParam, setDominoesId);

            request.setAttribute("setDominoes", currentSetDominoes);
            request.setAttribute("chainsViews", chainDominoesViewList);
            doGetHandler(request,response);
        }else{
            doGetHandler(request,response);
        }

    }
}
