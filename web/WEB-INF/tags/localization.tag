<%@ tag trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<c:choose>
    <c:when test="${userLang != null}">
        <fmt:setLocale scope="session" value="${userLang}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale scope="session" value="en"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="language" scope="session"/>