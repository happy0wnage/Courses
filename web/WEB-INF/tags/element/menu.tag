<%@ tag trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-2">
                <span class="sr-only">Toggle1 navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.html">Student courses</a>
        </div>
        <c:if test="${not empty user}">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-2">
            <ul class="nav navbar-nav">
                <li><a href="index.html"><fmt:message key="main.top.menu.main"/><span
                        class="sr-only">(current)</span></a>
                </li>
                <li><a href="newsPage"><fmt:message key="main.top.menu.news"/></a></li>
                <li><a href="coursesPage"><fmt:message key="main.top.menu.courses"/></a></li>
                <li><a href="journalPage"><fmt:message key="main.top.menu.journal"/></a></li>
                <li><a href="profilePage"><fmt:message key="main.top.menu.profile"/></a></li>
            </ul>
            </c:if>
        </div>
    </div>
</nav>