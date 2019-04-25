<%@ page import="java.util.*,
	vn.edu.vnuk.tasks.dao.*,
	vn.edu.vnuk.tasks.model.*" 
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
    
    <head>
        <meta charset="utf-8" 
            name="viewport" 
            content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    </head>


    <body>

	    <a href="addTask" class="btn btn-primary">
	        <i class="fa fa-plus" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;New task
	    </a>
	
	    <br /><br />
	    
	    <div id="my-notice"></div>
	    
	    <table class="table table-bordered table-hover table-responsive table-striped">
	        <thead>
	            <tr>
	                <th></th>
	                <th>Id</th>
	                <th>Description</th>
	                <th>Status</th>
	                <th>Date of achievement</th>
	            </tr>
	        </thead>
	
	        <tbody>
	
	            <c:forEach var="task" items="${tasks}">
	
	                <tr>
	                    <td>
	                        <c:if test="${task.complete eq false}">
	                            <button type="button"
	                                    class="btn btn-xs btn-success my-task-to-complete" 
	                                    value="${task.id}"
	                                    data-toggle="tooltip" 
	                                    title="Complete task" 
	                                    data-placement="bottom">
	                                <i class="fa fa-check" aria-hidden="true"></i>
	                            </button>
	                        </c:if>
	                        
	                        <a href="editTask?id=${task.id}" class="btn btn-xs btn-primary" 
	                           data-toggle="tooltip" title="Edit task" data-placement="bottom">
	                            <i class="fa fa-pencil" aria-hidden="true"></i>
	                        </a>
	                           
	                           
	                        <%--    DELETING WITHOUT AJAX   --%>
	                           
	                        <%--a href="deleteTask/${task.id}" class="btn btn-xs btn-danger"
	                           data-toggle="tooltip" title="Delete task" data-placement="bottom">
	                            <i class="fa fa-trash" aria-hidden="true"></i>
	                        </a--%>
	                           
	                           
	                        <%--    DELETING WITH AJAX   --%>
	
	                        <button type="button"
	                                class="btn btn-xs btn-danger my-task-to-delete" 
	                                value="${task.id}"
	                                data-toggle="tooltip" 
	                                title="Delete task" 
	                                data-placement="bottom">
	                            <i class="fa fa-trash" aria-hidden="true"></i>
	                        </button>
	
	                    </td>
	                    
	                    <td>${task.id}</td>
	                    <td>${task.description}</td>
	                    <td id="status-of-task-${task.id}">${task.complete ? "Complete" : "To be done"}</td>
	                    <td id="date-of-achievement-for-task-${task.id}">
	                        <c:if test="${not empty task.dateOfCompletion}">
	                            <fmt:formatDate value="${task.dateOfCompletion.time}" pattern="dd/MM/yyyy" />
	                        </c:if>
	                    </td>
	                </tr>
	
	            </c:forEach>
	
	        </tbody>
	    </table>    
    
    </body>

</html>
