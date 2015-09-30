<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<elem:head/>

<body>
<elem:menu/>
<div class="all">

    <div class="main" id="null">

        <div class="settings">

            <c:if test="${(role.name == 'admin' || role.name == 'lecturer') && not empty course}">
                <div class="btn-group btn-group-justified">
                    <a class="btn btn-default" data-toggle="modal" data-target="#addNews"><fmt:message
                            key="main.news.header"/></a>
                </div>
            </c:if>

            <div class="list-group">
                <a href="newsPage" id="active" class="list-group-item">
                    <h5 class="list-group-item-heading"><fmt:message key='main.news.all'/></h5>

                    <p class="list-group-item-text"><fmt:message key="main.news.info"/></p>
                </a>
                <c:forEach var="c" items="${course}">
                    <a href="newsPage?idCourse=${c.id}" class="list-group-item">
                        <h5 class="list-group-item-heading">${c.name}</h5>

                        <p class="list-group-item-text">${c.description}</p>
                    </a>
                </c:forEach>
            </div>

            <c:if test="${not empty alertMessage}">
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key="warning"/></h3>
                    </div>
                    <div class="panel-body">
                        <fmt:message key="${alertMessage}"/><br/>
                        <a href="profilePage" class="alert-link"><fmt:message key="conversation.go_to_profile"/></a>
                    </div>
                </div>
            </c:if>
        </div>

        <c:forEach items="${newsCourse}" var="entry">
            <c:forEach items="${entry.value}" var="value">

                <div class="container">
                    <div class="elements">
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-dismissible alert-danger">
                                <button type="button" class="close" data-dismiss="alert">×</button>
                                <strong>Oh snap!</strong><fmt:message key="${errorMessage}"/>
                            </div>
                        </c:if>

                        <div class="top-settings">
                            <div class="author">
                                    ${value.user.fullName}&nbsp;&nbsp;|&nbsp;&nbsp;<fmt:formatDate
                                    value="${value.postDate}"
                                    type="BOTH"
                                    dateStyle="long"
                                    timeStyle="Medium"/>
                            </div>
                            <c:if test="${(role.name == 'admin') || (role.name == 'lecturer')}">
                                <div class="right-news">
                                    <ul class="nav nav-pills">
                                        <li><a href="" data-toggle="modal" data-target="#changeNews${value.id}"><span
                                                class="glyphicon glyphicon-pencil"
                                                aria-hidden="true"></span>&nbsp;&nbsp;<fmt:message
                                                key="edit"/><span
                                                class="badge"></span></a></li>
                                        <li><a href="" data-toggle="modal" data-target="#modalDelete${value.id}"><span
                                                class="glyphicon glyphicon-remove"
                                                aria-hidden="true"></span>&nbsp;&nbsp;<fmt:message
                                                key="delete"/> <span
                                                class="badge"></span></a></li>
                                    </ul>
                                </div>

                                <div id="changeNews${value.id}" class="modal">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-hidden="true">x
                                                </button>
                                                <h4 class="modal-title"><fmt:message key="main.news.change_news"/> <strong>${value.title}</strong></h4>
                                            </div>

                                            <div class="modal-body">
                                                <form class="form-horizontal" _lpchecked="1" method="post"
                                                      action="changeNews?idNews=${value.id}"
                                                      enctype="multipart/form-data">
                                                    <fieldset>
                                                        <div class="form-group">
                                                            <label for="title"
                                                                   class="col-lg-2 control-label"><fmt:message
                                                                    key='main.news.new.title'/> </label>

                                                            <div class="col-lg-10">
                                                                <input type="text" class="form-control"
                                                                       placeholder="<fmt:message key='main.news.new.title'/>"
                                                                       style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                                                                       autocomplete="off" name="title"
                                                                       value="${value.title}">
                                                            </div>
                                                        </div>

                                                        <div class="form-group">
                                                            <label for="body"
                                                                   class="col-lg-2 control-label"><fmt:message
                                                                    key='main.news.new.body'/></label>

                                                            <div class="col-lg-10">
                                                                <textarea id="t-area" class="form-control" rows="3"
                                                                          id="body"
                                                                          name="body">${value.body}</textarea>
                                                            </div>
                                                        </div>

                                                        <div class="form-group">
                                                            <label class="col-lg-2 control-label"><fmt:message
                                                                    key="main.course.new.theme"/></label>

                                                            <div class="col-lg-10">
                                                                <c:forEach var="entry" items="${newsCourse}">
                                                                    <c:choose>
                                                                        <c:when test="${entry.key.id == value.id}">
                                                                            <div class="col-lg-10">
                                                                                <div class="radio">
                                                                                    <label>
                                                                                        <input type="radio"
                                                                                               name="idCourse"
                                                                                               value="${entry.key.id}"
                                                                                               checked/>
                                                                                            ${entry.key.name}
                                                                                    </label>
                                                                                </div>
                                                                            </div>
                                                                            <br/>
                                                                        </c:when>

                                                                        <c:otherwise>
                                                                            <div class="col-lg-10">
                                                                                <div class="radio">
                                                                                    <label>
                                                                                        <input type="radio"
                                                                                               name="idCourse"
                                                                                               value="${entry.key.id}"
                                                                                               />
                                                                                            ${entry.key.name}
                                                                                    </label>
                                                                                </div>
                                                                            </div>
                                                                            <br/>

                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:forEach>
                                                            </div>
                                                        </div>

                                                        <div class="form-group">
                                                            <label class="col-lg-2 control-label"><fmt:message
                                                                    key='main.news.new.img'/></label>

                                                            <div class="col-lg-10">
                                                                <input type="file" class="btn btn-default"
                                                                       name="photo"/>
                                                            </div>
                                                        </div>


                                                    </fieldset>
                                                    <div class="modal-footer">
                                                        <a class="btn btn-default" data-dismiss="modal"><fmt:message
                                                                key='close'/></a>
                                                        <button type="submit" class="btn btn-primary"><fmt:message
                                                                key='save'/></button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div id="modalDelete${value.id}" class="modal">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-hidden="true">x
                                                </button>
                                                <h4 class="modal-title"><fmt:message key="main.news.delete_question"/></h4>
                                            </div>
                                            <div class="modal-footer">
                                                <a href="deleteNews?idNews=${value.id}"
                                                   class="btn btn-primary"><fmt:message key="delete"/></a>
                                                <button type="button" class="btn btn-default" data-dismiss="modal">
                                                    <fmt:message key="close"/>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </c:if>
                        </div>
                        <br/>

                        <p id="title">${value.title}</p>

                        <c:if test="${not empty value.picture }">
                            <img width="625" src="imageGeneratorServlet?id=${value.id}&source=news"/>
                            <br/>
                        </c:if>

                        <c:choose>
                            <c:when test="${fn:length(value.body) >= 200}">
                                <ul class="nav nav-tabs">
                                    <li class="active"><a href="#short${value.id}" data-toggle="tab">Short</a></li>
                                    <li><a href="#all${value.id}" data-toggle="tab">Open all</a></li>
                                </ul>


                                <div id="myTabContent" class="tab-content">
                                    <div class="tab-pane fade active in" id="short${value.id}">
                                        <c:set var="shortBody" value="${fn:substring(value.body, 0, 200)}"/>

                                        <p>${shortBody}...</p>
                                    </div>

                                    <div class="tab-pane fade" id="all${value.id}">
                                        <p>${value.body}</p>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <hr/>
                                <p>${value.body}</p>
                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>

            </c:forEach>
        </c:forEach>

        <div id="addNews" class="modal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
                        <h4 class="modal-title"><fmt:message key="main.news.new"/></h4>
                    </div>

                    <div class="modal-body">
                        <form class="form-horizontal" _lpchecked="1" method="post" action="addNews"
                              enctype="multipart/form-data">
                            <fieldset>
                                <div class="form-group">
                                    <label for="title" class="col-lg-2 control-label"><fmt:message
                                            key='main.news.new.title'/> </label>

                                    <div class="col-lg-10">
                                        <input type="text" class="form-control"
                                               placeholder="<fmt:message key='main.news.new.title'/>"
                                               style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                                               autocomplete="off" name="title">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="body" class="col-lg-2 control-label"><fmt:message
                                            key='main.news.new.body'/></label>

                                    <div class="col-lg-10">
                                        <textarea id="t-area" class="form-control" rows="3" id="body"
                                                  name="body"></textarea>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-2 control-label"><fmt:message
                                            key="main.course.new.theme"/></label>

                                    <div class="col-lg-10">
                                        <c:forEach var="entry" items="${newsCourse}">
                                            <div class="col-lg-10">
                                                <div class="radio">
                                                    <label>
                                                        <input type="radio"
                                                               name="idCourse"
                                                               value="${entry.key.id}"
                                                               checked/>
                                                            ${entry.key.name}
                                                    </label>
                                                </div>
                                            </div>
                                            <br/>
                                        </c:forEach>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-2 control-label"><fmt:message key='main.news.new.img'/></label>

                                    <div class="col-lg-10">
                                        <input type="file" class="btn btn-default" name="photo"/>
                                    </div>
                                </div>


                            </fieldset>
                            <div class="modal-footer">
                                <a class="btn btn-default" data-dismiss="modal"><fmt:message key='close'/></a>
                                <button type="submit" class="btn btn-primary"><fmt:message key='save'/></button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <elem:right/>
</div>
<c:remove var="alertMessage" scope="session"/>
<c:remove var="errorMessage" scope="session"/>

</body>
</html>