$(function(){

    //  COMPLETING TASK
    $('.my-task-to-complete').on('click', function (e){
        e.preventDefault();

        var $task = $(this);
        var taskId = $task.val();
        var $tr = $('tr[data-task-id="' + taskId + '"]');
        
        
        $.ajax({
           
            type: "PATCH",
            url: "tasks/" + taskId + "/complete",

            
            success: function() {
                
                $task.remove();
                
                $tr.find('.my-task-to-edit').remove();
                $tr.find(".task-status").text("Complete");
                $tr.find(".task-date-of-completion").text($.format.date(new Date(), 'dd MMMM yyyy'));
                
                $('#my-notice').addClass('my-notice-green').removeClass("my-no-line-height");
                $('#my-notice i').addClass('far fa-smile');
                $('#my-notice span:last-child').text("Task #" + taskId + " has successfully been completed.");
                
            },
            
            
            error: function() {
            	
                $('#my-notice').addClass('my-notice-red').removeClass("my-no-line-height");
                $('#my-notice i').addClass('fas fa-dizzy');
                $('#my-notice span:last-child').text("Something went wrong with completion of task #" + taskId);
            
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
                
                $('tr[data-task-id="' + taskId + '"]').remove();
                
                $('#my-notice').addClass('my-notice-green').removeClass("my-no-line-height");
                $('#my-notice i').addClass('far fa-smile');
                $('#my-notice span:last-child').text("Task #" + taskId + " has successfully been deleted.");
                    
            },
            
            
            error: function() {
            	
                $('#my-notice').addClass('my-notice-red').removeClass("my-no-line-height");
                $('#my-notice i').addClass('fas fa-dizzy');
                $('#my-notice span:last-child').text("Something went wrong with deletion of task #" + taskId);
            
            }
            
        });
        
    });

});
