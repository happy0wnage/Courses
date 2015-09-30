<%@ tag trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jspf/directive/taglib.jspf" %>

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