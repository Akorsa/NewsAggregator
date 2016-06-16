<%@include file="../layout/taglib.jsp" %>
<c:if test="${not empty items}">
    Search results for :
    <table>
        <c:forEach var="item" items="${items}" varStatus="rowCounter">
            <c:if test="${rowConter.count eq 0}">
                <tr>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Link</th>
                </tr>
            </c:if>
            <tr>
                <td>${item.title}</td>
                <td>${item.description}</td>
                <td>${item.link}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>