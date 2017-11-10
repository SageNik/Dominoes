package ua.domino.service.impl;

import org.apache.log4j.Logger;
import ua.domino.dao.impl.MySqlChainDominoesDAO;
import ua.domino.entity.*;
import ua.domino.model.HistoryChainDominoesViewModel;
import ua.domino.model.ResultChainDominoesViewModel;
import ua.domino.service.interfaces.ChainDominoesService;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Ник on 28.10.2017.
 */
public class ChainDominoesServiceImpl implements ChainDominoesService {

    private static final Logger LOGGER = Logger.getLogger(ChainDominoesServiceImpl.class.getName());
    MySqlChainDominoesDAO mySqlChainDominoesDAO = new MySqlChainDominoesDAO();
    SetDominoServiceImpl setDominoService = new SetDominoServiceImpl();

    @Override
    public List<HistoryChainDominoesViewModel> getAllChainDominoes() {
        LOGGER.info("Finding all chain dominoes");
        List<ChainDominoes> chainDominoesList = mySqlChainDominoesDAO.findAllChainDominoes();
        List<HistoryChainDominoesViewModel> historyChainDominoesViewModels = new ArrayList<>();

        for (ChainDominoes chain:chainDominoesList) {
            HistoryChainDominoesViewModel viewModel = HistoryChainDominoesViewModel.buildByChainDominoes(chain);
            historyChainDominoesViewModels.add(viewModel);
        }
        return historyChainDominoesViewModels;
    }

    @Override
    public List<ResultChainDominoesViewModel> buildChains(String mode, Long setDominoesId) {

        LOGGER.info("Start to build chains");
        List<ChainDominoes> result = new ArrayList<>();
        List<ResultChainDominoesViewModel> resultViewModels = new ArrayList<>();

        if (setDominoesId != null) {
            SetDominoes setDominoes = setDominoService.getSetDominoesById(setDominoesId);
            setDominoes.setChainDominoesList(new CopyOnWriteArrayList<ChainDominoes>());

            createChainDominoes(setDominoes);
            createFirstChainLink(setDominoes);

            while (setCurentTailVariants(setDominoes)) {
                checkTails(setDominoes);
            }

            if ("all".equals(mode)) {
                for (ChainDominoes chain : setDominoes.getChainDominoesList()) {
                    ChainDominoes savedChain = save(chain);
                    result.add(savedChain);
                }
            } else if ("longest".equals(mode)) {
                List<ChainDominoes> tempChainList = setDominoes.getChainDominoesList();
                Collections.sort(tempChainList, chainDominoesComparator());
                ChainDominoes savedChain = save(tempChainList.get(0));
                result.add(savedChain);
            }
        }
        LOGGER.info("Build chains finished");

        convertToResultViewModel(result, resultViewModels);
        return resultViewModels;
    }

    private void convertToResultViewModel(List<ChainDominoes> result, List<ResultChainDominoesViewModel> resultViewModels) {
        LOGGER.info("Converting built chains to result view models");
        for (ChainDominoes chain:result) {
            for (int i = 0; i<chain.getChainLinks().size();i++) {
                ResultChainDominoesViewModel viewModel = ResultChainDominoesViewModel.buildByChainLink(chain.getChainLinks().get(i));
               if(i==(chain.getChainLinks().size()-1)){
                   viewModel.setIsFinishChain("true");
               }
                resultViewModels.add(viewModel);
            }
        }
    }

    private void createFirstChainLink(SetDominoes setDominoes) {
        LOGGER.info("Creating first chain link");
        ChainLink chainLink = new ChainLink();
        chainLink.setChainNumber(setDominoes.getChainDominoesList().get(0).getChainNumber());
        chainLink.setDomino(getFirstBone(setDominoes.getChainDominoesList().get(0)));
        String linkNumber = String.valueOf(setDominoes.getChainDominoesList().get(0).getChainNumber() + "100");
        chainLink.setLinkNumber(Long.parseLong(linkNumber));
        setFirstTails(setDominoes.getChainDominoesList().get(0), chainLink);
        setDominoes.getChainDominoesList().get(0).getChainLinks().add(chainLink);
        LOGGER.info("The first chain link created");
    }

    private ChainDominoes save(ChainDominoes chain) {
        LOGGER.info("Saving chain to database");
        ChainDominoes savedChain = mySqlChainDominoesDAO.firstStepSave(chain);
        for (ChainLink link : chain.getChainLinks()) {
            mySqlChainDominoesDAO.secondStepSave(link, savedChain.getId());
        }
        LOGGER.info("Chain saved to database");
        return chain;
    }

    private Comparator<ChainDominoes> chainDominoesComparator() {
        return new Comparator<ChainDominoes>() {
            @Override
            public int compare(ChainDominoes o1, ChainDominoes o2) {
                Integer val1 = o2.getChainLinks().size();
                Integer val2 = o1.getChainLinks().size();
                return val1.compareTo(val2);
            }
        };
    }

    private void createChainDominoes(SetDominoes setDominoes) {
        LOGGER.info("Creating first chain Dominoes");
        ChainDominoes chainDominoes = new ChainDominoes();
        chainDominoes.setSetNumber(setDominoes.getId());
        chainDominoes.setChainNumber(setDominoes.getId().toString() + "1000");
        chainDominoes.setCurrentHeapDomino(new CopyOnWriteArrayList<Domino>(setDominoes.getDominoBones()));
        chainDominoes.setChainLinks(new LinkedList<ChainLink>());
        chainDominoes.setTails(new CopyOnWriteArrayList<Tail>());

        setDominoes.getChainDominoesList().add(chainDominoes);
        LOGGER.info("The first chain Dominoes created");
    }

    private void checkTails(SetDominoes setDominoes) {
        LOGGER.info("Try to check all tails and create alternative chain");
        for (ChainDominoes chain : setDominoes.getChainDominoesList()) {
            for (Tail tail : chain.getTails()) {
                Integer tailNumber = tail.getTailNumber();
                Domino usedVariant = null;
                boolean moreVariants = false;
                for (Domino variant : tail.getVariants()) {
                    if (chain.getCurrentHeapDomino().contains(variant)) {
                        if (!moreVariants) {
                            ChainLink newLink = makeNewChainLink(chain, variant, tail.getChainLink().getLinkNumber());
                            chain.getChainLinks().add(newLink);
                            usedVariant = variant;
                            moreVariants = true;
                        } else {
                            String chainNumber = String.valueOf(Integer.parseInt(setDominoes.getChainDominoesList().get(setDominoes.getChainDominoesList().size() - 1).getChainNumber()) + 1);
                            ChainDominoes newChain = makeNewChainDominoes(chain, chainNumber);

                            if (newChain != null) {
                                ChainLink newLink = makeNewChainLink(newChain, variant, tail.getChainLink().getLinkNumber());
                                newChain.getCurrentHeapDomino().remove(variant);
                                newChain.getChainLinks().add(newLink);
                                changeTail(newChain, tailNumber, variant);
                                setDominoes.getChainDominoesList().add(newChain);
                            }
                        }
                        tail.getVariants().remove(variant);
                    }

                }
                if (usedVariant != null) {
                    chain.getCurrentHeapDomino().remove(usedVariant);
                    changeTail(chain, tail.getTailNumber(), usedVariant);
                }
            }
        }
    }

    private ChainDominoes makeNewChainDominoes(ChainDominoes chain, String chainNumber) {
        LOGGER.info("Making new chain Dominoes");
        ChainDominoes newChain = null;
        try {
            newChain = chain.clone();
            newChain.setChainNumber(chainNumber);
            newChain.getChainLinks().removeLast();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        LOGGER.info("The new chain Dominoes made");
        return newChain;
    }


    private ChainLink makeNewChainLink(ChainDominoes chain, Domino variant, Long parentLinkNumber) {
        LOGGER.info("Making new chain link");
        ChainLink newLink = new ChainLink();
        newLink.setChainNumber(chain.getChainNumber());
        newLink.setDomino(variant);
        newLink.setLinkNumber(chain.getChainLinks().getLast().getLinkNumber() + 1);
        newLink.setParentId(parentLinkNumber);
        LOGGER.info("The new chain link made");
        return newLink;
    }

    private void changeTail(ChainDominoes chainDom, Integer tailNumber, Domino variantDomino) {
        LOGGER.info("Changing all chains tail to current values ");
        for (Tail currentTail : chainDom.getTails()) {
            if (currentTail.getTailNumber() == tailNumber) {
                if (variantDomino.getDouble()) {
                    chainDom.getTails().add(new Tail(chainDom.getChainLinks().getLast(), variantDomino.getSideOne(), chainDom.getTails().size()));
                    chainDom.getTails().add(new Tail(chainDom.getChainLinks().getLast(), variantDomino.getSideOne(), chainDom.getTails().size()));
                }
                if (currentTail.getTailValue() == variantDomino.getSideOne()) {
                    currentTail.setTailValue(variantDomino.getSideTwo());
                } else if (currentTail.getTailValue() == variantDomino.getSideTwo()) {
                    currentTail.setTailValue(variantDomino.getSideOne());
                }
                currentTail.setChainLink(chainDom.getChainLinks().getLast());
                break;
            }
        }
    }

    private boolean setCurentTailVariants(SetDominoes setDominoes) {
        LOGGER.info("Finding and setting all current variants for each tail each chain");
        boolean hasVariant = false;
        for (ChainDominoes chain : setDominoes.getChainDominoesList()) {
            for (Tail currentTail : chain.getTails()) {
                currentTail.setVariants(new CopyOnWriteArrayList<Domino>());
                for (Domino dom : chain.getCurrentHeapDomino()) {
                    if (currentTail.getTailValue() == dom.getSideOne() || currentTail.getTailValue() == dom.getSideTwo()) {
                        if (!currentTail.getVariants().contains(dom)) {
                            currentTail.getVariants().add(dom);
                        }
                        hasVariant = true;
                    }
                }
            }
        }
        return hasVariant;
    }

    private void setFirstTails(ChainDominoes chainDominoes, ChainLink chainLink) {
        LOGGER.info("Setting first tails");
        if (chainLink.getDomino().getDouble()) {
            chainDominoes.getTails().add(new Tail(chainLink, chainLink.getDomino().getSideOne(), 1));
            chainDominoes.getTails().add(new Tail(chainLink, chainLink.getDomino().getSideOne(), 2));
            chainDominoes.getTails().add(new Tail(chainLink, chainLink.getDomino().getSideTwo(), 3));
            chainDominoes.getTails().add(new Tail(chainLink, chainLink.getDomino().getSideTwo(), 4));
        } else {
            chainDominoes.getTails().add(new Tail(chainLink, chainLink.getDomino().getSideOne(), 1));
            chainDominoes.getTails().add(new Tail(chainLink, chainLink.getDomino().getSideTwo(), 2));
        }
    }

    private Domino getFirstBone(ChainDominoes chainDominoes) {
        LOGGER.info("Getting first bone");
        List<Domino> doubles = getDoubles(chainDominoes);
        List<Domino> tempHeap = new ArrayList<>(chainDominoes.getCurrentHeapDomino());
        Domino bone = null;
        if (doubles.isEmpty()) {
            Collections.sort(tempHeap, dominoComparator());
            for (Domino dom:tempHeap) {
                if(!dom.getDouble()) {
                    bone = dom;
                    break;
                }
            }
        } else {
            bone = doubles.get(0);
        }
        if (bone != null) {
            chainDominoes.getCurrentHeapDomino().remove(bone);
        }
        return bone;
    }

    private List<Domino> getDoubles(ChainDominoes chainDominoes) {
        LOGGER.info("Getting all double bones from dominoes pool");
        List<Domino> doubles = new ArrayList<>();
        for (Domino dom : chainDominoes.getCurrentHeapDomino()) {
            if (dom.getDouble() && dom.getSideOne() != 0) {
                doubles.add(dom);
            }
        }
        Collections.sort(doubles, new Comparator<Domino>() {
            @Override
            public int compare(Domino o1, Domino o2) {
                return o1.getSideOne().compareTo(o2.getSideOne());
            }
        });
        return doubles;
    }

    private Comparator<Domino> dominoComparator() {
        return new Comparator<Domino>() {
            @Override
            public int compare(Domino o1, Domino o2) {
                if (o1.getSideOne() == o2.getSideOne() && o1.getSideTwo() == o2.getSideTwo()) {
                    return 0;
                }
                if (o1.getSideOne() < o2.getSideOne()) {
                    return -1;
                }
                if (o1.getSideOne() == o2.getSideOne() && o1.getSideTwo() > o2.getSideTwo()) {
                    return 1;
                }
                if (o1.getSideOne() == o2.getSideOne() && o1.getSideTwo() < o2.getSideTwo()) {
                    return -1;
                }
                return 1;
            }
        };
    }
}
