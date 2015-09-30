<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<elem:head/>
<jsp:useBean id="currentDate" class="java.util.Date"/>
<body>
<elem:menu/>
<div class="all">

    <div class="main">

        <c:if test="${role.name == 'admin'}">
            <ul class="nav nav-pills">
                <li class="active"><a data-toggle="modal" data-target="#course" href="#"><span
                        class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>&nbsp;<fmt:message
                        key="main.new_course"/></a></li>
                <li><a href="#" data-toggle="modal" data-target="#theme"><span class="glyphicon glyphicon-plus-sign"
                                                                               aria-hidden="true"></span>&nbsp;<fmt:message
                        key="main.new_theme"/></a></li>
            </ul>
            <br/>
        </c:if>

        <c:if test="${not empty infoMessage}">
            <br/>

            <div class="alert alert-dismissible alert-info">
                <button type="button" class="close" data-dismiss="alert">×</button>
                    ${infoMessage}
            </div>
        </c:if>

        <c:if test="${not empty coursesToDelete}">
            <br/>

            <div class="alert alert-dismissible alert-warning">
                <button type="button" class="close" data-dismiss="alert">×</button>
                <h4>Warning!</h4>

                <p><fmt:message key="main.theme.on_delete"/><strong> - "${themeToDelete.name}"</strong><fmt:message
                        key="main.theme.on_delete_2"/></p>
                <c:forEach items="${coursesToDelete}" var="ctd">
                    <strong>&mdash;&nbsp${ctd.name}</strong><br/>
                </c:forEach>
            </div>
        </c:if>

        <ul class="breadcrumb">
            <li><a href="index.html?sortedValue=date"><fmt:message key="main.start_date"/>&nbsp;<span
                    class="glyphicon glyphicon-sort-by-attributes"
                    aria-hidden="true"></span></a></li>
            <li><a href="index.html?sortedValue=duration"><fmt:message key="main.course.duration"/>&nbsp;<span
                    class="glyphicon glyphicon-sort-by-attributes" aria-hidden="true"></span></a></li>
            <li><a href="index.html?sortedValue=name"><fmt:message key="name"/>&nbsp;<span
                    class="glyphicon glyphicon-sort-by-alphabet"
                    aria-hidden="true"></span></a></li>
            <li><a href="index.html?sortedValue=count"><fmt:message key="students_count"/>&nbsp;<span
                    class="glyphicon glyphicon-sort-by-order" aria-hidden="true"></span></a></li>
        </ul>

        <ul class="nav nav-pills">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <c:choose>
                        <c:when test="${not empty param.idTheme}">
                            <c:forEach var="t" items="${availableThemes}">
                                <c:if test="${t.id == param.idTheme}">
                                    ${t.name}
                                </c:if>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="main.Select_theme"/>
                        </c:otherwise>
                    </c:choose>
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <c:forEach var="t" items="${availableThemes}">
                        <li><a href="index.html?idTheme=${t.id}">${t.name}</a></li>
                    </c:forEach>
                </ul>
            </li>

            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <c:choose>
                        <c:when test="${not empty param.idLecturer}">
                            <c:forEach var="l" items="${availableLecturers}">
                                <c:if test="${l.id == param.idLecturer}">
                                    ${l.fullName}
                                </c:if>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="main.Select_lecturer"/>
                        </c:otherwise>
                    </c:choose>
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <c:forEach var="l" items="${availableLecturers}">
                        <li><a href="index.html?idLecturer=${l.id}">${l.fullName}</a></li>
                    </c:forEach>
                </ul>
            </li>

            <li class="active">
                <a href="index.html" class="btn btn-primary btn-xs"><fmt:message key="reset"/></a>
            </li>

        </ul>

        <c:choose>
            <c:when test="${not empty noCourseMessage}">
                <div class="alert alert-dismissible alert-warning">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <h4><fmt:message key="${noCourseMessage}"/></h4>
                    <a href="profilePage" class="alert-link"><fmt:message key="conversation.go_to_profile"/></a>
                </div>
            </c:when>
            <c:otherwise>
                <h2><fmt:message key="main.become_attendee"/></h2>

                <div class="list-group">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th><fmt:message key="main.course.course"/></th>
                            <c:if test="${role.name == 'student' || role.name == 'admin'}">
                                <th><fmt:message key="action"/></th>
                            </c:if>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${not empty alertMessage}">
                            <div class="alert alert-dismissible alert-success">
                                <button type="button" class="close" data-dismiss="alert">x</button>
                                <fmt:message key='${alertMessage}'/>
                            </div>
                        </c:if>
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-dismissible alert-danger">
                                <button type="button" class="close" data-dismiss="alert">x</button>
                                <fmt:message key='${errorMessage}'/>
                            </div>
                        </c:if>
                        <c:forEach items="${course}" var="c">
                            <c:if test="${c.endDate gt currentDate}">
                                <tr>
                                    <td>
                                        <h3><p class="text-primary">${c.name}</p></h3>
                                            ${c.description}
                                        <br/>
                                        <br/>

                                        <blockquote>
                                            <p>${c.theme.name}</p>
                                            <small>${c.theme.description}</small>
                                        </blockquote>


                                        <p class="text-muted">
                                            <strong><fmt:message
                                                    key="main.course.duration"/>: </strong>${c.duration}&nbsp;<fmt:message
                                                key="main.days"/>
                                            <br/>
                                            <strong><fmt:message
                                                    key="main.number_of_students"/>: </strong> ${c.usersCount}
                                            <br/>
                                            <strong><fmt:message
                                                    key="main.course.new.lecturer"/>:&nbsp; </strong>${c.userLecturer.fullName}

                                        </p>

                                        <p class="text-danger">
                                            <small><fmt:formatDate dateStyle="full"
                                                                   value="${c.startDate}"/>&nbsp;&mdash;&nbsp;<fmt:formatDate
                                                    dateStyle="full" value="${c.endDate}"/></small>
                                        </p>

                                    </td>
                                    <td id="fixed-size">
                                        <c:if test="${role.name == 'student'}">
                                            <a href="subscribeCourse?idCourse=${c.id}&name=${c.name}"><span
                                                    class="glyphicon glyphicon-plus"
                                                    aria-hidden="true"></span>&nbsp;<fmt:message
                                                    key="main.course.subscribe"/></a>
                                            <br/>
                                        </c:if>
                                        <c:if test="${role.name == 'admin'}">
                                            <a href="" data-toggle="modal" data-target="#editCourse${c.id}"><span
                                                    class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                                                <fmt:message
                                                        key="edit"/> </a>
                                            <%--//Edit course--%>

                                            <div id="editCourse${c.id}" class="modal">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal"
                                                                    aria-hidden="true">x
                                                            </button>
                                                            <h4 class="modal-title"><fmt:message
                                                                    key="main.course.change_course"/> ${c.name}</h4>
                                                        </div>

                                                        <div class="modal-body">
                                                            <form class="form-horizontal" method="post"
                                                                  action="changeCourse?idCourse=${c.id}">
                                                                <fieldset>
                                                                    <div class="form-group">
                                                                        <label class="col-lg-2 control-label"><fmt:message
                                                                                key="main.course.new.name"/></label>

                                                                        <div class="col-lg-10">
                                                                            <input type="text" class="form-control"
                                                                                   placeholder="<fmt:message key="main.course.new.name"/>"
                                                                                   style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                                                                                   autocomplete="off" name="name"
                                                                                   value="${c.name}">
                                                                        </div>
                                                                    </div>

                                                                    <div class="form-group">
                                                                        <label class="col-lg-2 control-label"><fmt:message
                                                                                key="main.course.new.desc"/></label>

                                                                        <div class="col-lg-10">
                                                                            <textarea id="t-area" class="form-control"
                                                                                      rows="3"
                                                                                      name="description">${c.description}</textarea>
                                                                        </div>
                                                                    </div>

                                                                    <div class="form-group">
                                                                        <label class="col-lg-2 control-label"><fmt:message
                                                                                key="main.course.new.lecturer"/></label>

                                                                        <div class="col-lg-10">
                                                                            <c:forEach items="${roleUser}" var="ru">
                                                                                <div class="col-lg-10">
                                                                                    <div class="radio">
                                                                                        <label>
                                                                                            <c:choose>
                                                                                                <c:when test="${c.userLecturer.id == ru.user.id}">
                                                                                                    <input type="radio"
                                                                                                           name="idLecturer"
                                                                                                           value="${ru.user.id}"
                                                                                                           checked>
                                                                                                    <b>${ru.user.fullName}</b>
                                                                                                    </input>
                                                                                                </c:when>
                                                                                                <c:otherwise>
                                                                                                    <input type="radio"
                                                                                                           name="idLecturer"
                                                                                                           value="${ru.user.id}">
                                                                                                    <b>${ru.user.fullName}</b>
                                                                                                    </input>
                                                                                                </c:otherwise>
                                                                                            </c:choose>
                                                                                        </label>
                                                                                    </div>
                                                                                </div>
                                                                                <br/>
                                                                            </c:forEach>
                                                                        </div>
                                                                    </div>


                                                                    <div class="form-group">
                                                                        <label class="col-lg-2 control-label"><fmt:message
                                                                                key="main.course.new.duration"/></label>

                                                                        <div class='col-lg-10'>
                                                                            <input name="duration" type='text'
                                                                                   class="form-control"/>
                                                                    <span class="help-block"><fmt:message
                                                                            key="main.course.max_date_value"/> </span>
                                                                        </div>


                                                                    </div>

                                                                    <div class="form-group">
                                                                        <label class="col-lg-2 control-label"><fmt:message
                                                                                key="main.course.new.theme"/></label>

                                                                        <div class="col-lg-10">
                                                                            <c:forEach items="${theme}" var="t">
                                                                                <div class="col-lg-10">
                                                                                    <div class="radio">
                                                                                        <label>
                                                                                            <c:choose>
                                                                                                <c:when test="${c.theme.id == t.id}">
                                                                                                    <input type="radio"
                                                                                                           name="idTheme"
                                                                                                           value="${t.id}"
                                                                                                           checked/>

                                                                                                    <b>${t.name}</b>
                                                                                                    <br/>
                                                                                                    ${t.description}
                                                                                                    <br/>
                                                                                                    <a href="deleteTheme?idTheme=${t.id}"><span
                                                                                                            class="glyphicon glyphicon-remove"
                                                                                                            aria-hidden="true"></span>Delete</a>
                                                                                                </c:when>
                                                                                                <c:otherwise>
                                                                                                    <input type="radio"
                                                                                                           name="idTheme"
                                                                                                           value="${t.id}"/>

                                                                                                    <b>${t.name}</b>
                                                                                                    <br/>
                                                                                                    ${t.description}
                                                                                                    <br/>
                                                                                                    <a href="deleteTheme?idTheme=${t.id}"><span
                                                                                                            class="glyphicon glyphicon-remove"
                                                                                                            aria-hidden="true"></span>Delete</a>
                                                                                                </c:otherwise>
                                                                                            </c:choose>
                                                                                        </label>
                                                                                    </div>
                                                                                </div>
                                                                                <br/>
                                                                            </c:forEach>
                                                                        </div>
                                                                    </div>
                                                                </fieldset>
                                                                <div class="modal-footer">
                                                                    <a href="" type="button" class="btn btn-default"
                                                                       data-dismiss="modal"><fmt:message
                                                                            key="close"/></a>
                                                                    <button type="submit" class="btn btn-primary">
                                                                        <fmt:message
                                                                                key="save"/></button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>


                                            <%--//Edit course finish--%>


                                            <br/>
                                            <a href="" data-toggle="modal" data-target="#deleteCourse${c.id}"><span
                                                    class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                                <fmt:message
                                                        key="main.course.delete_question"/></a>

                                            <div id="deleteCourse${c.id}" class="modal">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal"
                                                                    aria-hidden="true">x
                                                            </button>
                                                            <h4 class="modal-title"><fmt:message
                                                                    key="main.course.delete_question"/></h4>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-default"
                                                                    data-dismiss="modal">
                                                                Close
                                                            </button>
                                                            <a href="deleteCourse?idCourse=${c.id}"
                                                               class="btn btn-primary"><fmt:message key="delete"/></a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                        </c:if>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <mdl:direction/>
    <mdl:course/>
    <elem:right/>
</div>
<c:remove var="alertMessage" scope="session"/>
<c:remove var="noCourseMessage" scope="session"/>
<c:remove var="errorMessage" scope="session"/>
<c:remove var="infoMessage" scope="session"/>

</body>
</html>