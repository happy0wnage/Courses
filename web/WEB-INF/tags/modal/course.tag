<%@ tag trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<div id="course" class="modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
                <h4 class="modal-title"><fmt:message key="main.course.new"/></h4>
            </div>

            <div class="modal-body">
                <form class="form-horizontal" _lpchecked="1" method="post" action="addCourse" onsubmit="return courseValidate();">
                    <fieldset>
                        <div class="form-group" id="courseName">
                            <label for="inputName" class="col-lg-2 control-label"><fmt:message
                                    key="main.course.new.name"/></label>

                            <div class="col-lg-10">
                                <input type="text" class="form-control" id="inputName"
                                       placeholder="<fmt:message key="main.course.new.name"/>"
                                       style="cursor: auto; background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QsPDhss3LcOZQAAAU5JREFUOMvdkzFLA0EQhd/bO7iIYmklaCUopLAQA6KNaawt9BeIgnUwLHPJRchfEBR7CyGWgiDY2SlIQBT/gDaCoGDudiy8SLwkBiwz1c7y+GZ25i0wnFEqlSZFZKGdi8iiiOR7aU32QkR2c7ncPcljAARAkgckb8IwrGf1fg/oJ8lRAHkR2VDVmOQ8AKjqY1bMHgCGYXhFchnAg6omJGcBXEZRtNoXYK2dMsaMt1qtD9/3p40x5yS9tHICYF1Vn0mOxXH8Uq/Xb389wff9PQDbQRB0t/QNOiPZ1h4B2MoO0fxnYz8dOOcOVbWhqq8kJzzPa3RAXZIkawCenHMjJN/+GiIqlcoFgKKq3pEMAMwAuCa5VK1W3SAfbAIopum+cy5KzwXn3M5AI6XVYlVt1mq1U8/zTlS1CeC9j2+6o1wuz1lrVzpWXLDWTg3pz/0CQnd2Jos49xUAAAAASUVORK5CYII=); background-attachment: scroll; background-position: 100% 50%; background-repeat: no-repeat;"
                                       autocomplete="off" name="inputName">
                            </div>
                        </div>

                        <div class="form-group" id="courseDescription">
                            <label for="inputDescription" class="col-lg-2 control-label"><fmt:message
                                    key="main.course.new.desc"/></label>

                            <div class="col-lg-10">
                                <textarea id="t-area" class="form-control" rows="3" id="inputDescription"
                                          name="inputDescription"></textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-lg-2 control-label"><fmt:message key="main.course.new.lecturer"/></label>

                            <div class="col-lg-10">
                                <c:forEach items="${roleUser}" var="ru">
                                    <div class="col-lg-10">
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="inputLecturer"
                                                       value="${ru.user.id}" checked="">
                                                <b>${ru.user.fullName}</b>
                                                </input>
                                            </label>
                                        </div>
                                    </div>
                                    <br/>
                                </c:forEach>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-lg-2 control-label"><fmt:message key="main.course.new.duration"/></label>

                            <div class='col-lg-10'>
                                <input name="duration" type='text' class="form-control" id='datepicker'/>
                                <span class="help-block"><fmt:message key="main.course.max_date_value"/> </span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="inputName" class="col-lg-2 control-label"><fmt:message
                                    key="main.course.new.theme"/></label>

                            <div class="col-lg-10">
                                <c:forEach items="${theme}" var="t">
                                    <div class="col-lg-10">
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="inputTheme"
                                                       value="${t.id}" checked=""/>

                                                <b>${t.name}</b>
                                                <br/>
                                                    ${t.description}
                                                <br/>
                                                <a href="deleteTheme?idTheme=${t.id}"><span
                                                        class="glyphicon glyphicon-remove"
                                                        aria-hidden="true"></span>Delete</a>
                                            </label>
                                        </div>
                                    </div>
                                    <br/>
                                </c:forEach>
                            </div>
                        </div>
                    </fieldset>
                    <div class="modal-footer">
                        <a href="" type="button" class="btn btn-default" data-dismiss="modal"><fmt:message
                                key="close"/></a>
                        <button type="submit" class="btn btn-primary"><fmt:message key="save"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>