<%-- 
    Document   : pagination
    Created on : Aug 16, 2025, 2:02:04?AM
    Author     : Admin
--%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="joiner" value="${fn:contains(baseUrl, '?') ? '&' : '?'}" />

<c:if test="${totalPages > 1}">
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                <a class="page-link" href="${baseUrl}${joiner}page=${currentPage - 1}" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>

            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="${baseUrl}${joiner}page=${i}">${i}</a>
                </li>
            </c:forEach>

            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                <a class="page-link" href="${baseUrl}${joiner}page=${currentPage + 1}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>

</c:if>

