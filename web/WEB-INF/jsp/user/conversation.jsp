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
        <ul class="pager">
            <li class="previous"><a href="messagesPage">← <fmt:message key="conversation.back"/></a></li>
        </ul>
        <c:choose>
            <c:when test="${empty privateMessages}">
                <table class="table table-striped table-hover">
                    <tr>
                        <td class="image-login">
                            <img class="radial" src="imageGeneratorServlet?id=${param.idToUser}&source=user" alt=""
                                 width="50"
                                 height="50"/>

                        </td>
                        <td class="message-text">
                            <fmt:message key="conversation.no_messages"/><br/>
                            <strong><fmt:message key="conversation.try"/>
                                <span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span>
                            </strong>
                        </td>
                    </tr>
                </table>
            </c:when>
            <c:otherwise>
                <table class="table table-striped table-hover">
                    <c:forEach var="pm" items="${privateMessages}">
                        <tr>
                            <td class="image-login">
                                <img class="radial" src="imageGeneratorServlet?id=${pm.user.id}&source=user" alt=""
                                     width="50"
                                     height="50"/>

                                <p class="text-primary"><strong>${pm.user.firstName}</strong></p>
                            </td>
                            <td class="message-text">
                                &mdash;&nbsp;${pm.body}
                            </td>
                            <td class="message-date">
                                <p class="text-muted"><fmt:formatDate type="BOTH" timeStyle="Medium"
                                                                      value="${pm.postDate}"/>
                                </p>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>

        <form class="form-horizontal" action="writeMessage?idToUser=${param.idToUser}" method="post">
            <div class="write-message">

                <textarea id="msgBody" class="mytext-area" rows="3" id="textArea" name="message"></textarea>
                <button id="sendMsg" type="submit" class="btn btn-primary"><fmt:message
                        key="conversation.send"/></button>
            </div>
            </fieldset>
        </form>

    </div>
    <elem:right/>
</div>
<c:remove var="alertMessage" scope="session"/>
<c:remove var="errorMessage" scope="session"/>

</body>
</html>