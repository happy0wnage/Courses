<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<elem:head/>
<jsp:useBean id="currentDate" class="java.util.Date"/>
<body>
<elem:menu/>
<div class="all">
    <div class="main" id="null">

        <c:if test="${empty course}">
            <div class="panel panel-warning">
                <div class="panel-heading">
                    <h3 class="panel-title"><fmt:message key="warning"/></h3>
                </div>
                <div class="panel-body">
                    <fmt:message key="main.course.no_course"/>
                    <br/>
                    <a href="profilePage" class="alert-link"><fmt:message key="conversation.go_to_profile"/></a>
                </div>
            </div>
        </c:if>


        <div class="elements">
            <c:if test="${not empty warningMessage}">
                <div class="alert alert-dismissible alert-warning">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <fmt:message key="${warningMessage}"/>
                </div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-dismissible alert-danger">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <fmt:message key="${errorMessage}"/>
                </div>
            </c:if>

            <c:if test="${not empty course}">
            <h3><p class="text-danger"><strong><fmt:message key="main.course.header.not_started"/></strong></p></h3>

            <table class="table table-striped ">
                <thead>
                <tr>
                    <th id="fixed-size"><fmt:message key="main.profile.table.course.name"/></th>
                    <th><fmt:message key="main.profile.table.course.direction"/></th>
                    <th><fmt:message key="main.course.new.lecturer"/></th>
                    <c:if test="${role.name == 'student'}">
                        <th><fmt:message key="action"/></th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:set var="count" value="0"/>
                <c:forEach var="c" items="${course}">
                    <c:if test="${c.startDate gt currentDate}">
                        <c:set var="count" value="${count + 1}"/>
                        <tr class="danger">
                            <td>
                                <strong>${c.name}</strong>
                            </td>
                            <td>
                                    ${c.theme.name}
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${user.id == c.userLecturer.id}">
                                        ${c.userLecturer.fullName}
                                    </c:when>
                                    <c:otherwise>
                                        <ul class="nav nav-pills">
                                            <li class="dropdown">
                                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                                        ${c.userLecturer.fullName} <span class="caret"></span>
                                                </a>
                                                <ul class="dropdown-menu">
                                                    <li>
                                                        <a href="conversationServlet?idToUser=${c.userLecturer.id}"><span
                                                                class="glyphicon glyphicon-envelope"
                                                                aria-hidden="true"></span>&nbsp;&nbsp;<fmt:message
                                                                key="main.profile.write_msg"/></a>
                                                    </li>
                                                    <li><a href="profile?idUser=${c.userLecturer.id}"><span
                                                            class="glyphicon glyphicon-user"
                                                            aria-hidden="true"></span>&nbsp;&nbsp;<fmt:message
                                                            key="main.course.info"/></a>
                                                    </li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <c:if test="${role.name == 'student'}">
                                <td id="fixed-size">
                                    <a href="unsubscribeCourse?idCourse=${c.id}&name=${c.name}"><span
                                            class="glyphicon glyphicon-remove"
                                            aria-hidden="true"></span>&nbsp;<fmt:message key="main.course.unsubscribe"/></a>
                                </td>
                            </c:if>
                        </tr>
                        <tr>
                            <td colspan="6">
                                <small><cite title="Source Title">${c.description}</cite></small>
                                <br/><br/>
                                <small>
                                    <strong><fmt:formatDate value="${c.startDate}" type="date"/>&nbsp;&mdash;&nbsp;
                                        <span id="endDate"><fmt:formatDate value="${c.endDate}" type="date"/></span>
                                    </strong> (&nbsp;${c.duration}&nbsp;days&nbsp;)
                                    <span id="fromNow"></span>
                                </small>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>

                <c:if test="${count == 0}">
                    <tr>
                        <td colspan="5">
                            <div class="alert alert-dismissible alert-warning">
                                <button type="button" class="close" data-dismiss="alert">×</button>
                                <h4><fmt:message key="warning"/></h4>

                                <p><fmt:message key="main.course.header.no_not_started"/></p>
                            </div>
                        </td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>

        <div class="elements">
            <h3><p class="text-danger"><strong><fmt:message key="main.course.header.current"/></strong></p></h3>
            <table class="table table-striped ">
                <thead>
                <tr>
                    <th id="fixed-size"><fmt:message key="main.profile.table.course.name"/></th>
                    <th><fmt:message key="main.profile.table.course.direction"/></th>
                    <th><fmt:message key="main.course.new.lecturer"/></th>
                    <c:if test="${role.name == 'student'}">
                        <th><fmt:message key="action"/></th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:set var="count" value="0"/>
                <c:forEach var="c" items="${course}">
                    <c:if test="${c.endDate gt currentDate && c.startDate lt currentDate}">
                        <c:set var="count" value="${count + 1}"/>
                        <tr class="danger">
                            <td>
                                <strong>${c.name}</strong>
                            </td>
                            <td>
                                    ${c.theme.name}
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${user.id == c.userLecturer.id}">
                                        ${c.userLecturer.fullName}
                                    </c:when>
                                    <c:otherwise>
                                        <ul class="nav nav-pills">
                                            <li class="dropdown">
                                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                                        ${c.userLecturer.fullName} <span class="caret"></span>
                                                </a>
                                                <ul class="dropdown-menu">
                                                    <li>
                                                        <a href="conversationServlet?idToUser=${c.userLecturer.id}"><span
                                                                class="glyphicon glyphicon-envelope"
                                                                aria-hidden="true"></span>&nbsp;&nbsp;<fmt:message
                                                                key="main.profile.write_msg"/></a></li>
                                                    <li><a href="profile?idUser=${c.userLecturer.id}"><span
                                                            class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;&nbsp;<fmt:message
                                                            key="main.course.info"/></a></li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <c:if test="${role.name == 'student'}">
                                <td id="fixed-size">
                                    <a href="unsubscribeCourse?idCourse=${c.id}&name=${c.name}"><span
                                            class="glyphicon glyphicon-remove"
                                            aria-hidden="true"></span>&nbsp;<fmt:message key="main.course.unsubscribe"/></a>
                                </td>
                            </c:if>
                        </tr>
                        <tr>
                            <td colspan="6">

                                <small><cite title="Source Title">${c.description}</cite></small>
                                <br/><br/>
                                <small>
                                    <strong><fmt:formatDate value="${c.startDate}" type="date"/>&nbsp;&mdash;&nbsp;
                                        <span id="endDate"><fmt:formatDate value="${c.endDate}" type="date"/></span>
                                    </strong> (&nbsp;${c.duration}&nbsp;days&nbsp;)
                                    <span id="fromNow"></span>
                                </small>
                                <c:if test="${c.left > 0}">
                                    <div class="progress">
                                        <div class="progress-bar" style="width: ${c.left}%;"></div>
                                    </div>
                                </c:if>


                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                <c:if test="${count == 0}">
                    <tr>
                        <td colspan="5">
                            <div class="alert alert-dismissible alert-warning">
                                <button type="button" class="close" data-dismiss="alert">×</button>
                                <h4><fmt:message key="warning"/></h4>

                                <p><fmt:message key="main.course.no_act_courses"/> </p>
                            </div>
                        </td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>

        <div class="elements">
            <h3><p class="text-danger"><strong><fmt:message key="main.course.header.finished"/></strong></p></h3>
            <table class="table table-striped ">
                <thead>
                <tr>
                    <th id="fixed-size"><fmt:message key="main.profile.table.course.name"/></th>
                    <th><fmt:message key="main.profile.table.course.direction"/></th>
                    <th><fmt:message key="main.course.new.lecturer"/></th>
                    <c:if test="${role.name == 'student'}">
                        <th><fmt:message key="action"/></th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:set var="count" value="0"/>
                <c:forEach var="c" items="${course}">
                    <c:if test="${c.endDate lt currentDate}">
                        <c:set var="count" value="${count + 1}"/>
                        <tr class="danger">
                            <td>
                                <strong>${c.name}</strong>
                            </td>
                            <td>
                                    ${c.theme.name}
                            </td>
                            <td>
                            <c:choose>
                                <c:when test="${user.id == c.userLecturer.id}">
                                    ${c.userLecturer.fullName}
                                </c:when>
                                <c:otherwise>
                                    <ul class="nav nav-pills">
                                        <li class="dropdown">
                                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                                    ${c.userLecturer.fullName} <span class="caret"></span>
                                            </a>
                                            <ul class="dropdown-menu">
                                                <li><a href="conversationServlet?idToUser=${c.userLecturer.id}"><span
                                                        class="glyphicon glyphicon-envelope" aria-hidden="true"></span>&nbsp;&nbsp;<fmt:message
                                                        key="main.profile.write_msg"/></a>
                                                </li>
                                                <li><a href="profile?idUser=${c.userLecturer.id}"><span
                                                        class="glyphicon glyphicon-user"
                                                        aria-hidden="true"></span>&nbsp;&nbsp;View
                                                    information</a>
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                </c:otherwise>
                            </c:choose>
                            </td>
                            <td>
                                <a href="journalPage"><span
                                        class="glyphicon glyphicon-education"
                                        aria-hidden="true"></span>&nbsp;<fmt:message key="main.course.go_journal"/></a>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6">
                                <small><cite title="Source Title">${c.description}</cite></small>
                                <br/><br/>
                                <small>
                                    <strong><fmt:formatDate value="${c.startDate}" type="date"/>&nbsp;&mdash;&nbsp;
                                        <span id="endDate"><fmt:formatDate value="${c.endDate}" type="date"/></span>
                                    </strong> (&nbsp;${c.duration}&nbsp;days&nbsp;)
                                    <span id="fromNow"></span>
                                </small>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                <c:if test="${count == 0}">
                    <tr>
                        <td colspan="5">
                            <div class="alert alert-dismissible alert-warning">
                                <button type="button" class="close" data-dismiss="alert">×</button>
                                <h4><fmt:message key="warning"/></h4>

                                <p><fmt:message key="main.course.header.no_finished"/></p>
                            </div>
                        </td>
                    </tr>
                </c:if>
                </tbody>
            </table>

            </c:if>
        </div>
    </div>
    <elem:right/>
</div>
<c:remove var="warningMessage" scope="session"/>
<c:remove var="errorMessage" scope="session"/>

</body>
</html>