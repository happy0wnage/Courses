<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<elem:head/>

<body>
<elem:menu/>
<div class="all">
    <div class="main">

        <h4>${user.fullName}</h4>
        <hr/>
        <label class="text-primary"><fmt:message key="main.profile.login"/> :&nbsp;</label>${user.login}
        <br/>
        <label class="text-primary"><fmt:message key="main.profile.email"/>:&nbsp;</label>${user.email}
        <br/>
        <label class="text-primary"><fmt:message key="main.profile.phone"/>:&nbsp;</label>${user.phone}
        <br/>
        <c:if test="${role.name == 'student'}">
            <label class="text-primary"><fmt:message
                    key="main.profile.card_number"/>:&nbsp;</label>${user.studentCardNumber}
        </c:if>
        <br/>


        <div class="list-group">
            <c:if test="${role.name == 'admin'}">
                <a href="register" class="list-group-item active">
                    <h4 class="list-group-item-heading"><span class="glyphicon glyphicon-user"
                                                              aria-hidden="true"></span>&nbsp;&nbsp;&nbsp;<fmt:message
                            key="main.profile.admin.register.lecturer"/></h4>
                </a>
            </c:if>
            <c:if test="${role.name != 'lecturer'}">
                <a href="coursesPage" class="list-group-item">
                    <h4 class="list-group-item-heading"><fmt:message key="main.profile.lecturer.courses"/></h4>

                    <p class="list-group-item-text"><fmt:message key="main.profile.lecturer.courses_info"/></p>
                </a>
            </c:if>
            <a href="messagesPage" class="list-group-item">

                <h4 class="list-group-item-heading"><fmt:message key="main.profile.lecturer.msgs"/></h4>

                <p class="list-group-item-text"><fmt:message key="main.profile.lecturer.msgs_info"/></p>
            </a>
            <a href="journalPage" class="list-group-item">
                <h4 class="list-group-item-heading"><fmt:message key="main.profile.lecturer.journals"/></h4>

                <p class="list-group-item-text"><fmt:message key="main.profile.lecturer.journals_info"/></p>
            </a>
            <a href="#anchor" class="list-group-item">

                <h4 class="list-group-item-heading"><fmt:message key="main.profile.change_user_profile"/></h4>

                <p class="list-group-item-text"><fmt:message key="main.profile.change_user_profile_message"/></p>
            </a>
            <br/>

            <c:if test="${role.name == 'admin'}">
                <table class="table table-striped table-hover ">
                    <thead>
                    <tr>
                        <th><fmt:message key='main.profile.admin.user'/></th>
                        <th></th>
                        <th><fmt:message key='main.profile.admin.status'/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="entry" items="${roleUser}">
                        <tr>
                            <td id="td-active" onclick="window.location.href='profile?idUser=${entry.key.id}'">
                                    ${entry.key.fullName}
                                <br/>

                                <p class="text-primary"><strong><fmt:message
                                        key='main.profile.admin.login'/>:&nbsp;</strong>${entry.key.login}

                                <p class="text-info"><strong><fmt:message key='main.profile.admin.roles'/>:</strong>
                                    <c:set var="allRoles" value=""/>
                                    <c:forEach var="roles" items="${entry.value}">
                                        <c:set var="allRoles" value="${allRoles.concat(',&nbsp').concat(roles.name)}"/>
                                    </c:forEach>
                                        ${fn:substringAfter(allRoles, ',&nbsp')}
                                </p>
                                </p>
                            </td>
                            <td>
                                <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
                                <strong>
                                    &nbsp;<fmt:message key='main.profile.admin.email'/>:&nbsp;</strong>
                                    ${entry.key.email}
                                <br/>
                                <span class="glyphicon glyphicon-earphone" aria-hidden="true"></span>
                                <strong>&nbsp;<fmt:message
                                        key='main.profile.admin.phone'/>:&nbsp;</strong>${entry.key.phone}
                                <br/>

                                <c:if test="${not empty entry.key.studentCardNumber}"><span
                                        class="glyphicon glyphicon-barcode" aria-hidden="true"></span><strong>&nbsp;&nbsp;<fmt:message
                                        key='main.profile.admin.card'/>:&nbsp;</strong>${entry.key.studentCardNumber}
                                </c:if>
                            </td>
                            <td>
                                <ul id="smaller" class="nav nav-pills">
                                    <c:choose>
                                        <c:when test="${entry.key.statusBlocked == true}">
                                            <li id="block" class="active"><a
                                                    href="changeUserStatus?idUser=${entry.key.id}">Unblock<span
                                                    class="badge"><span
                                                    class="glyphicon glyphicon-eye-close"
                                                    aria-hidden="true"></span></span></a></li>

                                        </c:when>
                                        <c:otherwise>
                                            <li id="block" class="active"><a
                                                    href="changeUserStatus?idUser=${entry.key.id}">Block<span
                                                    class="badge"><span
                                                    class="glyphicon glyphicon-eye-open"
                                                    aria-hidden="true"></span></span></a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${role.name == 'lecturer'}">
                <c:choose>
                    <c:when test="${empty courses}">
                        <div class="alert alert-dismissible alert-warning">
                            <button type="button" class="close" data-dismiss="alert">×</button>

                            <p><fmt:message key="main.profile.not_the_lecturer"/><br/>
                                <a href="conversationServlet?idToUser=20" class="alert-link"><fmt:message key="main.profile.msg_write_to_admin"/></a>
                            </p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-striped table-hover ">
                            <thead>
                            <tr>
                                <th><fmt:message key='main.profile.table.course.name'/></th>
                                <th><fmt:message key='main.profile.table.course.direction'/></th>
                                <th><fmt:message key='main.profile.table.course.start'/></th>
                                <th><fmt:message key='main.profile.table.course.end'/></th>
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
                                        <small><cite title="Source Title">${c.description}</cite></small>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>

            </c:if>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-dismissible alert-danger">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <strong><fmt:message key="${errorMessage}"/>!</strong>
                </div>
            </c:if>

            <form class="form-horizontal" action="changeProfile">
                <fieldset>
                    <legend><fmt:message key="main.profile.change_user_profile"/></legend>
                    <div class="form-group">
                        <label class="col-lg-2 control-label"><fmt:message key="main.profile.admin.login"/></label>

                        <div class="col-lg-10">
                            <input type="text" class="form-control" placeholder="Login" name="login"
                                   value="${user.login}"
                                   style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                                   autocomplete="off">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-2 control-label"><fmt:message key="main.profile.admin.email"/></label>

                        <div class="col-lg-10">
                            <input type="text" class="form-control" placeholder="Email" name="email"
                                   value="${user.email}"
                                   style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                                   autocomplete="off">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-2 control-label"><fmt:message key="main.profile.admin.phone"/></label>

                        <div class="col-lg-10">
                            <input type="text" class="form-control" placeholder="Phone" name="phone"
                                   value="${user.phone}"
                                   style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                                   autocomplete="off">
                        </div>
                    </div>

                    <c:if test="${role.name == 'student'}">
                        <div class="form-group">
                            <label class="col-lg-2 control-label"><fmt:message key="main.profile.admin.card"/></label>

                            <div class="col-lg-10">
                                <input type="text" class="form-control" placeholder="Card number" name="card"
                                       value="${user.studentCardNumber}"
                                       style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                                       autocomplete="off">
                            </div>
                        </div>
                    </c:if>

                    <div class="form-group">
                        <div class="col-lg-10 col-lg-offset-2">
                            <button type="submit" class="btn btn-primary"><fmt:message key="save"/></button>
                        </div>
                    </div>
                </fieldset>
            </form>
            <hr/>

            <a name="anchor"></a>

            <form class="form-horizontal" action="changePassword">
                <fieldset>
                    <legend><fmt:message key="main.profile.change_user_password"/></legend>
                    <div class="form-group">
                        <label class="col-lg-2 control-label"><fmt:message key="main.profile.old_pwd"/></label>

                        <div class="col-lg-10">
                            <input type="password" class="form-control" name="oldPassword"
                                   style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                                   autocomplete="off">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-2 control-label"><fmt:message key="main.profile.new_pwd"/></label>

                        <div class="col-lg-10">
                            <input type="password" class="form-control" name="newPassword"
                                   style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                                   autocomplete="off">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-2 control-label"><fmt:message key="main.profile.repeat_new_pwd"/></label>

                        <div class="col-lg-10">
                            <input type="password" class="form-control" name="newPasswordRepeat"
                                   style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                                   autocomplete="off">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-lg-10 col-lg-offset-2">
                            <button type="submit" class="btn btn-primary"><fmt:message key="save"/> </button>
                        </div>
                    </div>
                </fieldset>
            </form>

        </div>
    </div>
    <elem:right/>
</div>
<c:remove var="alertMessage" scope="session"/>
<c:remove var="errorMessage" scope="session"/>

</body>
</html>