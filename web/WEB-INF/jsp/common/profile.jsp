<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<elem:head/>

<body>
<elem:menu/>
<div class="all">
    <div id="null" class="main">
        <div class="profile-image">
            <c:choose>
                <c:when test="${not empty userProfile.photo}">
                    <img width="180" height="180" src="imageGeneratorServlet?id=${userProfile.id}&source=user" alt=""/>
                </c:when>
                <c:otherwise>
                    <img width="180" height="180" src="${pageContext.request.contextPath}\resources\images\noimage.jpg"
                         alt=""/>
                </c:otherwise>
            </c:choose>

            <hr/>
            <strong>
                <ul class="nav nav-pills nav-stacked">
                    <li><a href="conversationServlet?idToUser=${userProfile.id}"><span
                            class="glyphicon glyphicon-envelope" aria-hidden="true"></span>&nbsp;&nbsp;<fmt:message
                            key="main.profile.write_msg"/></a>
                    </li>
                </ul>
            </strong>
        </div>

        <div class="profile-info">
            <h4>${userProfile.fullName}</h4>
            <hr/>
            <label class="text-primary"><fmt:message key="main.profile.login"/>:&nbsp;</label>${userProfile.login}
            <br/>
            <label class="text-primary"><fmt:message key="main.profile.email"/>:&nbsp;</label>${userProfile.email}
            <br/>
            <c:forEach var="urp" items="${userRoleProfile}">
                <c:if test="${urp.role.name == 'student'}">
                    <label class="text-primary"><fmt:message
                            key="main.profile.card_number"/>:&nbsp;</label>${userProfile.studentCardNumber}
                </c:if>
            </c:forEach>
            <br/>

            <div class="list-group">
                <a href="conversationServlet?idToUser=${userProfile.id}" class="list-group-item">
                    <h4 class="list-group-item-heading"><fmt:message
                            key="main.profile.write_msg_to"/>&nbsp;${userProfile.login}</h4>

                    <p class="list-group-item-text"><fmt:message key="main.profile.can_write_message"/></p>
                </a>

            </div>
            <c:forEach var="urp" items="${userRoleProfile}">
                <c:if test="${urp.role.name == 'lecturer'}">
                    <table class="table table-striped table-hover ">
                        <thead>
                        <tr>
                            <th><fmt:message key="main.profile.table.course.name"/></th>
                            <th><fmt:message key="main.profile.table.course.direction"/></th>
                            <th><fmt:message key="main.profile.table.course.start"/></th>
                            <th><fmt:message key="main.profile.table.course.end"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="c" items="${courses}">
                            <tr>
                                <td>
                                    <strong>${c.name}</strong>
                                </td>
                                <td>
                                        ${c.theme.name}
                                </td>
                                <td><fmt:formatDate value="${c.startDate}" type="date"/></td>
                                <td><fmt:formatDate value="${c.endDate}" type="date"/></td>
                            </tr>
                            <tr>
                                <td colspan="6">
                                    <small><cite
                                            title="<fmt:message key="main.profile.table.course.title"/>">${c.description}</cite>
                                    </small>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </c:forEach>

        </div>
    </div>
    <elem:right/>
</div>
<c:remove var="alertMessage" scope="session"/>
<c:remove var="errorMessage" scope="session"/>

</body>
</html>