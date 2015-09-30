<%@ tag trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<div class="right-block">

    <c:choose>
        <c:when test="${not empty user.login}">
            <h6><fmt:message key="main.right.login.welcome"/> <strong>${user.firstName}</strong></h6>
            <hr/>
            <div class="img">
                <c:choose>
                    <c:when test="${not empty user.photo}">
                        <img id="profile-image" src="imageGeneratorServlet?id=${user.id}&source=user" alt=""/>
                    </c:when>
                    <c:otherwise>
                        <img id="profile-image" src="${pageContext.request.contextPath}\resources\images\noimage.jpg"
                             alt=""/>
                    </c:otherwise>
                </c:choose>

                <div class="change-image">
                    <ul class="nav nav-pills nav-stacked">
                        <li class="active"><a href="#" data-toggle="modal" data-target="#imageModal"><span
                                class="glyphicon glyphicon-picture"
                                aria-hidden="true"></span>&nbsp;&nbsp;<fmt:message key="main.right.login.photo.upload"/></a>
                        </li>
                    </ul>

                    <div id="imageModal" class="modal">
                        <div class="modal-dialog">
                            <div class="modal-content">

                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x
                                    </button>
                                    <h4 class="modal-title"><fmt:message key="main.right.login.photo.new_photo"/></h4>
                                </div>

                                <div class="modal-body">
                                    <form id="myPhoto" class="form-horizontal" _lpchecked="1" method="post"
                                          action="changeImage?id=${user.id}&source=user"
                                          enctype="multipart/form-data" onsubmit="return validatePhoto(this);">
                                        <fieldset>
                                            <div class="form-group" id="inputPhoto">
                                                <div class="col-lg-12">
                                                    <label><fmt:message
                                                            key="main.right.login.photo.change_photo"/></label>
                                                    <input type="file" class="btn btn-default " name="photo"
                                                           id="photo"/>
                                                    <span class="help-block"><fmt:message
                                                            key="registration.restriction.photo"/></span>
                                                </div>
                                            </div>
                                        </fieldset>
                                        <div class="modal-footer">
                                            <a class="btn btn-default" data-dismiss="modal"><fmt:message
                                                    key="close"/></a>
                                            <button type="submit" class="btn btn-primary"><fmt:message
                                                    key="upload"/></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${fn:length(userRole) gt 1}">
                <div class="btn-group">
                    <a id="max-width" class="btn btn-primary"><strong>Role:&nbsp;${role.name}</strong>&nbsp;&nbsp;&nbsp;Change:</a>
                    <a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span
                            class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="userRole" items="${userRole}">
                            <li><a href="changeRole?idRole=${userRole.role.id}">${userRole.role.name}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
            <hr/>


            <strong>
                <ul class="nav nav-pills nav-stacked">
                    <li><a href="profilePage"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;&nbsp;
                        <fmt:message key="main.right.profile"/></a></li>
                    <c:if test="${role.name != 'lecturer'}">
                        <li><a href="coursesPage"><span class="glyphicon glyphicon-star" aria-hidden="true"></span>&nbsp;&nbsp;
                            <fmt:message key="main.right.courses"/></a></li>
                    </c:if>
                    <c:if test="${role.name == 'student'}">
                        <li><a href="journalPage"><span class="glyphicon glyphicon-education" aria-hidden="true"></span>&nbsp;&nbsp;<fmt:message
                                key="main.right.my_marks"/></a></li>
                    </c:if>
                    <li><a href="newsPage"><span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>&nbsp;&nbsp;<fmt:message
                            key="main.right.news"/></a>
                    </li>
                    <li><a href="messagesPage"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>&nbsp;&nbsp;<fmt:message
                            key="main.right.msgs"/></a>
                    <li class="active"><a href="logout"><span class="glyphicon glyphicon-log-out"
                                                              aria-hidden="true"></span>&nbsp;&nbsp;<fmt:message
                            key="main.right.logout"/></a>
                    </li>
                </ul>
            </strong>

        </c:when>
        <c:otherwise>
            <form class="form-horizontal" _lpchecked="1" method="post" action="login">
                <fieldset>
                    <legend><fmt:message key="main.right.login.please_login"/></legend>

                    <c:if test="${not empty loginErrorMessage}">
                        <div class="alert alert-dismissible alert-danger">
                            <button type="button" class="close" data-dismiss="alert">x</button>
                            <fmt:message key='${loginErrorMessage}'/>
                        </div>
                    </c:if>

                    <div class="form-group">

                        <div class="col-lg-12">
                            <label for="inputEmail"><fmt:message
                                    key="main.right.login.login"/>: </label>
                            <input type="text" class="form-control" id="inputEmail"
                                   placeholder="<fmt:message key="main.right.login.login"/>"
                                   style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                                   autocomplete="off" name="login">
                        </div>
                    </div>
                    <div class="form-group">

                        <div class="col-lg-12">
                            <label for="inputPassword"><fmt:message
                                    key="main.right.login.pass"/>: </label>
                            <input type="password" class="form-control" id="inputPassword"
                                   placeholder="<fmt:message key="main.right.login.pass"/>"
                                   style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                                   autocomplete="off" name="password">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-lg-10">
                            <input type="submit" class="btn btn-primary col-lg-4"
                                   value="<fmt:message key="main.right.login.login"/>"/>&nbsp;
                            <a class="btn btn-default" href="register"><fmt:message key="main.right.registration"/></a>
                        </div>
                    </div>
                </fieldset>
            </form>
        </c:otherwise>
    </c:choose>
    <br/>

    <div class="well well-sm">
        <strong><fmt:message key="main.right.choose_lang"/>:</strong>&nbsp;&nbsp;&nbsp;
        <c:forEach var="locale" items="${locales}">
            <c:if test="${locale == 'ru'}">
                <a href="changeLang?lang=${locale}"><img
                        src="${pageContext.request.contextPath}\resources\images\russian.png"/></a>&nbsp;&nbsp;&nbsp;
            </c:if>
            <c:if test="${locale == 'en'}">
                <a href="changeLang?lang=${locale}"><img
                        src="${pageContext.request.contextPath}\resources\images\english.png"/></a>
            </c:if>
        </c:forEach>
    </div>
</div>
<c:remove var="loginErrorMessage" scope="session"/>