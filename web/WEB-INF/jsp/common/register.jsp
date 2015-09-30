<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<elem:head/>

<body>
<elem:menu/>
<div class="main">
    <form class="form-horizontal" action="register" name="registerForm" method="post" enctype="multipart/form-data"
          onsubmit="return validate();">
        <fieldset>
            <c:if test="${not empty errorMessage}">

                <div class="alert alert-dismissible alert-danger">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <fmt:message key='${errorMessage}'/>
                </div>
            </c:if>

            <legend><fmt:message key="registration"/></legend>

            <div class="form-group" id="firstName">
                <div class="col-lg-12">
                    <label for="firstName"><fmt:message key="registration.fname"/>
                        <abbr title=<fmt:message key="registration.required_field"/>>*</abbr>
                    </label>
                    <input type="text" class="form-control"
                           placeholder="<fmt:message key='registration.fname'/>"
                           style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                           autocomplete="off" name="firstName" value="${incorrectUser.firstName}">
                    <span class="help-block"><fmt:message key="registration.restriction.f_name"/> </span>
                </div>

            </div>

            <div class="form-group" id="secondName">
                <div class="col-lg-12">
                    <label for="secondName"><fmt:message key="registration.lname"/>
                        <abbr title=<fmt:message key="registration.required_field"/>>*</abbr>
                    </label>
                    <input type="text" class="form-control"
                           placeholder="<fmt:message key='registration.lname'/>"
                           style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                           autocomplete="off" name="secondName" value="${incorrectUser.secondName}">
                    <span class="help-block"><fmt:message key="registration.restriction.l_name"/></span>
                </div>
            </div>

            <div class="form-group" id="middleName">
                <div class="col-lg-12">
                    <label for="middleName"><fmt:message key='registration.mname'/></label>
                    <input type="text" class="form-control"
                           placeholder="<fmt:message key='registration.mname'/>"
                           style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                           autocomplete="off" name="middleName" value="${incorrectUser.middleName}">
                    <span class="help-block"><fmt:message key="registration.restriction.m_name"/></span>
                </div>

            </div>

            <div class="form-group" id="login">
                <div class="col-lg-12">
                    <label for="login"><fmt:message key='registration.login'/>
                        <abbr title=<fmt:message key="registration.required_field"/>>*</abbr>
                    </label>
                    <input type="text" class="form-control"
                           placeholder="<fmt:message key='registration.login'/>"
                           style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                           autocomplete="off" name="login" value="${incorrectUser.login}" autocomplete="off">
                    <span class="help-block"><fmt:message key="registration.restriction.login"/></span>
                </div>
            </div>

            <div class="form-group" id="password">
                <div class="col-lg-12">
                    <label for="password" ><fmt:message key='registration.pass'/>
                        <abbr title=<fmt:message key="registration.required_field"/>>*</abbr>
                    </label>
                    <input type="password" class="form-control"
                           placeholder="<fmt:message key='registration.pass'/>"
                           style="background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                           autocomplete="off" name="password" autocomplete="off">
                    <span class="help-block"><fmt:message key="registration.restriction.password"/></span>
                </div>
            </div>

            <div class="form-group" id="email">
                <div class="col-lg-12">
                    <label for="email" ><fmt:message key='registration.email'/>
                        <abbr title=<fmt:message key="registration.required_field"/>>*</abbr>
                    </label>
                    <input type="text" class="form-control"
                           placeholder="<fmt:message key='registration.email'/>"
                           style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                           autocomplete="off" name="email" value="${incorrectUser.email}">
                    <span class="help-block"><fmt:message key="registration.restriction.email"/></span>
                </div>
            </div>

            <div class="form-group" id="phone">
                <div class="col-lg-12">
                    <label for="phone" ><fmt:message key='registration.phone'/>
                        <abbr title=<fmt:message key="registration.required_field"/>>*</abbr>
                    </label>
                    <input type="text" class="form-control" placeholder="+380XXXXXXXXX"
                           style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                           autocomplete="off" name="phone" value="${incorrectUser.phone}">
                    <span class="help-block"><fmt:message key="registration.restriction.phone"/> </span>
                </div>
            </div>

            <c:if test="${role.name == 'admin'}">
                <div class="form-group">
                    <div class="col-lg-12">
                        <label ><fmt:message key='registration.role'/></label>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" name="admin" value="admin"> <fmt:message
                                    key='registration.admin'/>
                            </label>
                            <br/>
                            <label>
                                <input type="checkbox" name="lecturer" value="lecturer"> <fmt:message
                                    key='registration.lecturer'/>
                            </label>
                        </div>
                        <span class="help-block"><fmt:message key='registration.info'/></span>
                    </div>
                </div>
            </c:if>
            <c:if test="${role.name != 'admin'}">
                <div class="form-group" id="card">
                    <div class="col-lg-12">
                        <label for="card" ><fmt:message key='registration.card_number'/>
                            <abbr title=<fmt:message key="registration.required_field"/>>*</abbr>
                        </label>
                        <input type="text" class="form-control"
                               placeholder="<fmt:message key='registration.card_number'/>"
                               style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                               autocomplete="off" name="card" value="${incorrectUser.studentCardNumber}">
                        <span class="help-block"><fmt:message key="registration.restriction.card"/></span>
                    </div>
                </div>
            </c:if>

            <div class="form-group" id="inputPhoto">
                <div class="col-lg-12">
                    <label ><fmt:message key='registration.photo'/></label>
                    <input type="file" class="btn btn-default col-lg-12" name="photo"/>
                    <span class="help-block"><fmt:message key="registration.restriction.photo"/></span>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-10">
                    <input type="submit" class="btn btn-primary col-lg-5 " value="<fmt:message key='registration.submit'/>"/>&nbsp;
                    <a type="submit" class="btn btn-default" href="index.html"><fmt:message key='close'/></a>
                </div>
            </div>
        </fieldset>
    </form>
</div>
<c:remove var="alertMessage" scope="session"/>
<c:remove var="errorMessage" scope="session"/>

</body>
</html>