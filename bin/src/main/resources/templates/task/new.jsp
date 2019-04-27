<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<c:import url="/resources/jsp/body-open.jsp" />
    <h3>Create task</h3>
    <form:errors path="task.description" cssStyle="color: red;"/>
    <form action="tasks" method="post">
        Description : <br />
        <textarea name="description" rows="5" cols="100"></textarea>
        <br />
        <button type="submit" class="btn btn-success">
            <i class="fa fa-check" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Create
        </button>
        <a class="btn btn-default" href="tasks">
            <i class="fa fa-times-rectangle-o" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Cancel
        </a>
    </form>
<c:import url="/resources/jsp/body-close.jsp" />
