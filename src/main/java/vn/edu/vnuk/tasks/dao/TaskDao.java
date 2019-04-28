package vn.edu.vnuk.tasks.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vn.edu.vnuk.tasks.model.Task;

@Repository
public class TaskDao {
	
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public TaskDao(JdbcTemplate jdbcTemplate) {
	  this.jdbcTemplate = jdbcTemplate;
    }


    //  CREATE
    public void create(Task task) throws SQLException{

        String sqlQuery = "INSERT INTO tasks (description) VALUES (?)";

        try {
            System.out.println(
            		String.format(
            				"%s new record in DB!",
            				
            				this.jdbcTemplate.update(
            						sqlQuery,
            						new Object[] {task.getDescription()}
        						)
        				)
        		);

            
        } catch (Exception e) {
        	
            e.printStackTrace();
        
        }

    }
    
    
    //  READ (List of Tasks)
    public List<Task> read() throws SQLException {

        try {
        
        	return this.jdbcTemplate.query(
        			"SELECT * FROM tasks",
        			new BeanPropertyRowMapper<Task>(Task.class)
    			);

        	
        } catch (Exception e) {
        	
            e.printStackTrace();
        
        }
        
        
		return null;


    }


    //  READ (Single Task)
    public Task read(Long id) throws SQLException{
        
    	return this.jdbcTemplate.queryForObject(
        		"SELECT * FROM tasks where id = ?",
        		new Object[] {id},
        		Task.class
    		);
    
    }  

    
    //  UPDATE
    public void update(Task task) throws SQLException {
        String sqlQuery = "update tasks set description=?, is_complete=?, date_of_completion=? where id=?";
        
        try {
        	this.jdbcTemplate.update(
					sqlQuery,
					
					new Object[] {
						task.getDescription(),
						task.isComplete(),
						task.getDateOfCompletion(),
						task.getId()
					}
				);
            
            
            System.out.println("Task successfully modified.");
        } 

        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    
    //  DELETE
    public void delete(Long id) throws SQLException {
        String sqlQuery = "delete from tasks where id=?";

        try {

            System.out.println(
            		String.format(
            				"%s record successfully removed from DB!",
            				
            				this.jdbcTemplate.update(
            						sqlQuery,
            						new Object[] {id}
        						)
        				)
        		);

        } 

        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    
    //  OTHERS
    
    public void complete(Long id) throws SQLException{
        
        Task task = this.read(id);
        task.setIsComplete(true);
        task.setDateOfCompletion(new Date(System.currentTimeMillis()));
        
        this.update(task);
        
    }
    
}