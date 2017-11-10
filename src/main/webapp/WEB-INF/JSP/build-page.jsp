<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="text-align: center">
    <h2> Страница построения цепочек костей домино</h2>
<h3>Выберите подготовленный набор домино, способ построение цепочек и нажмите "Построить"</h3>
   <form action="/building" method="post">
       <table style="margin-left: auto;margin-right: auto ;width: auto">
           <tr style="text-align: center">
               <td >
    <select name="selectSet">
        <option disabled>Выберите набор домино</option>
        <c:if test="${setList!=null}">
            <c:forEach items="${setList}" var="savedList">
                <option value="${savedList.id}">
                    Набор № <c:out value="${savedList.setNumber}"></c:out>
                </option>
            </c:forEach>
        </c:if>
    </select>
               </td>
               <td>
                   <select name="selectMode">
                       <option value="all">Все цепочки</option>
                       <option value="longest">Самую длинную</option>
                   </select>
               </td>
               <td>
                   <input type="submit" value="Построить">
               </td>
           </tr>
       </table>
   </form>
</div>
<c:if test="${chainsViews!=null}">
    <div style="text-align: center">

        <h3>Результаты:</h3>

        <h4>Сгенерированый набор домино под номером ${setDominoes.setNumber}:</h4>
        <table>
            <tr style="text-align: center">
                <c:forEach items="${setDominoes.dominoBones}" var="domino">
                    [ <c:out value="${domino.sideOne}"></c:out> : <c:out value="${domino.sideTwo}"></c:out> ]
                </c:forEach>
            </tr>
        </table>
        <br/>
        <table style="margin-left: auto;margin-right: auto ;width: auto">

                <c:forEach items="${chainsViews}" var="chain">
            <tr style="text-align: center">
                        [ <c:out value="${chain.dominoSideOne}"></c:out> : <c:out value="${chain.dominoSideTwo}"></c:out> ]
             </tr><br/>
            <c:if test="${chain.isFinishChain == 'true'}">
               <tr>
                   Цепочка №: <c:out value="${chain.chainNumber}"></c:out>
               </tr>
                <tr>
                ________________________________________________________________________________________________________
                </tr>
                <br/>
            </c:if>

                </c:forEach>

        </table>
    </div>
</c:if>

<div style="text-align: center">
    <h3>История резуьтатов</h3>
    <table style="margin-left: auto;margin-right: auto ;width: auto">
        <tr style="text-align: center">
            <td>№ набора</td>
            <td>№ цепочки</td>
            <td>Количество элементов</td>
        </tr>
        <c:if test="${historyChainDominoes!=null}">
        <c:forEach items="${historyChainDominoes}" var="chainH">
            <tr style="text-align: center">
                <td>${chainH.setNumber}</td>
                <td>${chainH.chainNumber}</td>
                <td>${chainH.elementsAmount}</td>
            </tr>
        </c:forEach>
        </c:if>
    </table>
</div>