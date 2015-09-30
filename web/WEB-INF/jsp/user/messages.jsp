<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<elem:head/>

<body>
<elem:menu/>
<div class="all">
    <div class="main">

        <c:choose>
            <c:when test="${empty privateMessages}">
                <div class="alert alert-dismissible alert-warning">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <p><fmt:message key="conversation.no_messages_list"/> <br/>
                        <a href="profilePage" class="alert-link"><fmt:message key="conversation.go_to_profile"/></a></p>
                </div>
            </c:when>
            <c:otherwise>
                <table class="table table-striped table-hover">
                    <c:forEach var="entry" items="${privateMessages}">
                        <c:if test="${entry.key.id != user.id}">
                            <tr>
                                <td id="small" onclick="window.location.href='profile?idUser=${entry.key.id}'">
                                    <img class="radial"
                                         src="imageGeneratorServlet?id=${entry.key.id}&source=user"
                                         alt=""
                                         width="50" height="50"/>
                                </td>
                                <td id="nick" onclick="window.location.href='profile?idUser=${entry.key.id}'">
                                    <p class="text-primary"> ${entry.key.secondName}&nbsp;${entry.key.firstName}</p>

                                    <p class="text-muted">
                                        <small>
                                            <fmt:formatDate type="BOTH" dateStyle="Full"
                                                            value="${entry.value.postDate}"/></small>
                                    </p>
                                </td>
                                <td id="cursor"
                                    onclick="window.location.href='conversationServlet?idToUser=${entry.key.id}'">
                                    <img class="radial"
                                         src="imageGeneratorServlet?id=${entry.value.user.id}&source=user"
                                         alt=""
                                         width="32" height="32"/>
                                    &mdash;&nbsp;${entry.value.body}
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
    <elem:right/>
</div>
<c:remove var="alertMessage" scope="session"/>
<c:remove var="errorMessage" scope="session"/>

</body>
</html>