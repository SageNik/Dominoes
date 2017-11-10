<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="text-align: center">
    <h2> Страница генерации набора костей домино</h2>
    <h3>Укажите количество костей в наборе (от 0 до 28). При значении "0" будет выбрано случайное число. Затем нажмите кнопку "Сгенерировать" </h3>
   <form action="generation" method="post">
    <input id="amount" name="amount" maxlength="2" type="number" max="28" min="0" value="0" style="width: 40px"><label for="amount"> костей домино</label>
       <input name="generate" type="submit" value="Сгенерировать"/>
   </form>
</div>
<c:if test="${setDominoes!=null}">
<div style="text-align: center">

    <h3>Сгенерирован следующий набор домино под номером ${setDominoes.setNumber}:</h3>
    <table>
        <tr style="text-align: center">
    <c:forEach items="${setDominoes.dominoBones}" var="domino">
            [ <c:out value="${domino.sideOne}"></c:out> : <c:out value="${domino.sideTwo}"></c:out> ]
    </c:forEach>
        </tr>
    </table>
    <p>Чтобы перейти на страницу построения цепочек из данной последовательности, нажмите кнопку "Перейти" </p>
    <input type="button" onclick= location.href="/building" value="Перейти"/>
</div>
</c:if>