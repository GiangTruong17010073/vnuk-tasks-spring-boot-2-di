<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<c:import url="/resources/jsp/body-open.jsp" />
    <h3>Update task n° ${task.id}</h3>
    <form:errors path="task.description" cssStyle="color: red;"/>
    <form action="updateTask" method="post">
        <input type="hidden" name="id" value="${task.id}" />
        Description : <br />
        
        <textarea name="description" rows="5" cols="100">${task.description}</textarea>
        <br /><br />
        
        Achieved ? <input type="checkbox" name="achieved" value="true" ${task.achieved? 'checked' : '' }/>
        <br /><br />
        
        Date of achievement : <br />
        <input type="text" name="dateInStringFormat" class="my-date-field"
            value="<fmt:formatDate value="${task.dateOfAchievement.time}"
            pattern="dd/MM/yyyy" />"
        />
        <br /><br />
        
        <button type="submit" class="btn btn-success">
            <i class="fa fa-repeat" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Update
        </button>
        <a class="btn btn-default" href="tasks">
            <i class="fa fa-times-rectangle-o" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Cancel
        </a>
    </form>
<c:import url="/resources/jsp/body-close.jsp" />
