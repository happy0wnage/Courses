<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<elem:head/>

<body>
<elem:menu/>
<jsp:useBean id="currentDate" class="java.util.Date"/>
<div class="all">
    <div class="main" id="null">
        <c:if test="${empty courseMarks}">
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

        <c:forEach var="entry" items="${courseMarks}">
            <c:if test="${not empty entry.value}">
                <div class="container">
                    <div class="elements">
                        <p id="title">${entry.key.course.name}</p>
                        <c:if test="${entry.key.course.startDate > currentDate}">
                            <div class="alert alert-dismissible alert-info">
                                <button type="button" class="close" data-dismiss="alert">×</button>
                                <strong>The course has not started yet</strong>
                            </div>
                        </c:if>
                        <c:if test="${entry.key.course.endDate < currentDate}">
                            <div class="alert alert-dismissible alert-info">
                                <button type="button" class="close" data-dismiss="alert">×</button>
                                <strong>The course has ended</strong>
                            </div>
                        </c:if>

                        <table class="table table-striped table-hover ">
                            <thead>
                            <tr>
                                <th>Student</th>
                                <th id="middle"><fmt:message
                                        key="main.journal.mark"/>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message
                                        key="main.journal.day"/></th>
                                <c:if test="${role.name == 'lecturer' && (entry.key.course.startDate lt currentDate && entry.key.course.endDate gt currentDate)}">
                                    <th><fmt:message key="action"/></th>
                                </c:if>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach var="userMarks" items="${entry.value}">
                                <tr>
                                    <td id="name-brd"><a href="profile?idUser=${userMarks.key.id}">
                                        <img class="radial" id="abs"
                                             src="imageGeneratorServlet?id=${userMarks.key.id}&source=user"/>
                                        <strong>
                                                ${userMarks.key.secondName}&nbsp;
                                                ${userMarks.key.firstName}
                                        </strong>

                                    </a>
                                    </td>
                                    <td id="middle">
                                        <c:if test="${empty userMarks.value}">
                                            &mdash;
                                        </c:if>
                                        <c:set var="avg" scope="page" value="0"/>
                                        <c:set var="iterator" scope="page" value="0"/>

                                        <c:forEach var="marks" items="${userMarks.value}">
                                            <div id="brd" class="well well-sm">
                                                <span class="badge">${marks.mark}</span>
                                                <fmt:formatDate dateStyle="FULL"
                                                                value="${marks.day}"/>
                                                <c:if test="${role.name == 'lecturer'}">
                                                    &nbsp;
                                                    <a href="" data-toggle="modal"
                                                       data-target="#changeMarkModal${marks.id}"><span
                                                            class="glyphicon glyphicon-pencil"
                                                            aria-hidden="true"></span></a>
                                                    &nbsp;&nbsp;
                                                    <a href="deleteMark?idMark=${marks.id}"><span
                                                            class="glyphicon glyphicon-remove"
                                                            aria-hidden="true"></span></a>
                                                </c:if>
                                            </div>
                                            <c:set var="iterator" value="${iterator + 1}"/>
                                            <c:set var="avg" value="${avg + marks.mark}"/>

                                            <div id="changeMarkModal${marks.id}" class="modal">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal"
                                                                    aria-hidden="true">
                                                                x
                                                            </button>
                                                            <h4 class="modal-title">Change mark</h4>
                                                        </div>

                                                        <div class="modal-body">
                                                            <form class="form-horizontal" _lpchecked="1" method="post"
                                                                  action="changeMark?idMark=${marks.id}">
                                                                <fieldset>
                                                                    <div class="form-group">
                                                                        <label class="col-lg-2 control-label">Mark</label>

                                                                        <div class="col-lg-10">
                                                                            <select id="max-height" multiple=""
                                                                                    class="form-control"
                                                                                    name="mark">
                                                                                <c:forEach var="i" begin="1" end="100"
                                                                                           step="1">
                                                                                    <option value="${i}">${i}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>

                                                                </fieldset>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-default"
                                                                            data-dismiss="modal">
                                                                        Close
                                                                    </button>
                                                                    <button id="addMark"
                                                                            type="submit" class="btn btn-primary">Save
                                                                        changes
                                                                    </button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>


                                        </c:forEach>
                                        <c:if test="${avg != 0}">
                                            <ul id="brd" class="nav nav-pills">
                                                <li id="brd" class="active"><a><fmt:message
                                                        key="main.journal.avg"/><span
                                                        class="badge">

                                                    <fmt:formatNumber maxFractionDigits="2" value="${avg/iterator}"/>
                                            </span></a></li>
                                            </ul>
                                        </c:if>
                                    </td>
                                    <c:if test="${role.name == 'lecturer' && (entry.key.course.startDate lt currentDate && entry.key.course.endDate gt currentDate)}">
                                        <td id="right-action">
                                            <a data-toggle="modal"
                                               data-target="#addMark${userMarks.key.id}${entry.key.id}"
                                               href="#"><span
                                                    class="glyphicon glyphicon-plus"
                                                    aria-hidden="true"></span>&nbsp;<fmt:message
                                                    key="main.journal.add"/></a>
                                            <br/><br/>
                                        </td>
                                    </c:if>
                                </tr>
                                <%--<mdl:mark/> TODO --%>

                                <div id="addMark${userMarks.key.id}${entry.key.id}" class="modal">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-hidden="true">
                                                    x
                                                </button>
                                                <h4 class="modal-title"><fmt:message key="main.journal.new"/></h4>
                                            </div>

                                            <div class="modal-body">
                                                <form class="form-horizontal" _lpchecked="1" method="post"
                                                      action="addMark?idUser=${userMarks.key.id}&idJournal=${entry.key.id}">
                                                    <fieldset>
                                                        <div class="form-group">
                                                            <label class="col-lg-2 control-label"><fmt:message key="main.journal.new.mark"/></label>

                                                            <div class="col-lg-10">
                                                                <select id="max-height" multiple="" class="form-control"
                                                                        name="mark">
                                                                    <c:forEach var="i" begin="1" end="100" step="1">
                                                                        <option value="${i}">${i}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>

                                                        <div class="form-group">
                                                            <label class="col-lg-2 control-label"><fmt:message key="main.journal.new.date"/></label></label>

                                                            <div class="col-lg-10">
                                                                <input type="text" class="form-control"
                                                                       name="inputDate">
                                                            </div>
                                                        </div>

                                                    </fieldset>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default"
                                                                data-dismiss="modal">
                                                            <fmt:message key="close"/>
                                                        </button>
                                                        <button id="addMark"
                                                                type="submit" class="btn btn-primary"><fmt:message key="save"/>
                                                        </button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            </tbody>
                        </table>
                        <br/>
                    </div>
                </div>
            </c:if>
        </c:forEach>

    </div>
    <elem:right/>
</div>
<c:remove var="alertMessage" scope="session"/>
<c:remove var="errorMessage" scope="session"/>
</body>
</html>