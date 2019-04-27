$(function(){

    //  COMPLETING TASK
    $('.my-task-to-complete').on('click', function (e){
        e.preventDefault();

        var $task = $(this);
        var taskId = $task.val();
        
        $.ajax({
           
            type: "POST",
            url: "completeTask",
            
            data: {
                id: taskId
            },
            
            success: function() {
                
                var date = new Date();
                var today = ("0" + date.getDate()).slice(-2) 
                        + "/" + ("0" + (date.getMonth() + 1)).slice(-2) 
                        + "/" + date.getFullYear();

                $task.hide();
                $("#status-of-task-" + taskId).text("Complete");
                $("#date-of-achievement-for-task-" + taskId).text(today);

                $('#my-notice').text('Task ' + taskId + ' has successfully been completed.')
                        .addClass('my-notice-green')
                        .removeClass('my-notice-red');
            },
            
            error: function() {
                $('#my-notice').text('Something went wrong with completion of task ' + taskId)
                        .removeClass('my-notice-green')
                        .addClass('my-notice-red');
            }
            
        });
        
    });


    //  DELETING TASK
    $('.my-task-to-delete').on('click', function (e){
        e.preventDefault();

        var $task = $(this);
        var taskId = $task.val();

        
        $.ajax({
           
            type: "DELETE",
            url: "/tasks/" + taskId,
            
            data: {},
            
            success: function() {
                
                $task.closest('tr').remove();
                
                $('#my-notice').addClass('my-notice-green');
                $('#my-notice i').addClass('far fa-smile');
                $('#my-notice span:last-child').text("Task " + taskId + " has successfully been deleted.");
                    
                    

            },
            
            
            error: function() {
                $('#my-notice').addClass('my-notice-red');
                $('#my-notice i').addClass('fas fa-dizzy');
                $('#my-notice span:last-child').text("Something went wrong with deletion of task " + taskId);
            }
            
        });
        
    });

});
