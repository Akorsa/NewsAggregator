<%@include file="../layout/taglib.jsp" %>
<c:if test="${not empty items}">
    Search results for :
    <table>
        <c:forEach var="book" items="${items}" varStatus="rowCounter">
            <c:if test="${rowConter.count eq 0}">
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>ISBN</th>
                </tr>
            </c:if>
            <tr>
                <td>${book.title}</td>
                <td>${book.description}</td>
                <td>${book.isbn}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>