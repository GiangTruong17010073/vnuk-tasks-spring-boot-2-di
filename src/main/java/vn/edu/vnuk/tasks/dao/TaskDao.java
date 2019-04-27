package vn.edu.vnuk.tasks.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.edu.vnuk.tasks.jdbc.ConnectionFactory;
import vn.edu.vnuk.tasks.model.Task;

public class TaskDao {
	
    private Connection connection;

    public TaskDao(){
        this.connection = new ConnectionFactory().getConnection();
    }

    public TaskDao(Connection connection){
        this.connection = connection;
    }


    //  CREATE
    public void create(Task task) throws SQLException{

        String sqlQuery = "insert into tasks (description) "
                        +	"values (?)";

        PreparedStatement statement;

        try {
                statement = connection.prepareStatement(sqlQuery);

                //	Replacing "?" through values
                statement.setString(1, task.getDescription());

                // 	Executing statement
                statement.execute();

                System.out.println("New record in DB !");

        } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } finally {
                System.out.println("Done !");
                connection.close();
        }

    }
    
    
    //  READ (List of Tasks)
    @SuppressWarnings("finally")
    public List<Task> read() throws SQLException {

        String sqlQuery = "select * from tasks";
        PreparedStatement statement;
        List<Task> tasks = new ArrayList<Task>();

        try {

            statement = connection.prepareStatement(sqlQuery);

            // 	Executing statement
            ResultSet results = statement.executeQuery();
            
            while(results.next()){

                Task task = new Task();
                task.setId(results.getLong("id"));
                task.setDescription(results.getString("description"));
                task.setIsComplete(results.getBoolean("is_complete"));

                Date dateOfCompletion = results.getDate("date_of_completion");
                
                if (dateOfCompletion == null){
                    task.setDateOfCompletion(null);
                }
                
                else{
                    Calendar date = Calendar.getInstance();
                    date.setTime(dateOfCompletion);
                    task.setDateOfCompletion(date);
                }

                tasks.add(task);

            }

            results.close();
            statement.close();


        } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } finally {
                connection.close();
                return tasks;
        }


    }


    //  READ (Single Task)
    public Task read(Long id) throws SQLException{
        return this.read(id, true);
    }  

    
    //  UPDATE
    public void update(Task task) throws SQLException {
        String sqlQuery = "update tasks set description=?, is_complete=?, date_of_completion=? where id=?";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, task.getDescription());
            statement.setBoolean(2, task.isComplete());
            
            statement.setDate(
            		3,
            		task.getDateOfCompletion() == null ? null : new Date(task.getDateOfCompletion().getTimeInMillis())
            	);
            
            statement.setLong(4, task.getId());
            statement.execute();
            statement.close();
            
            System.out.println("Task successfully modified.");
        } 

        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        finally {
            connection.close();
        }
        
    }
    
    
    //  DELETE
    public void delete(Long id) throws SQLException {
        String sqlQuery = "delete from tasks where id=?";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setLong(1, id);
            statement.execute();
            statement.close();
            
            System.out.println("Task successfully deleted.");

        } 

        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        finally {
            connection.close();
        }

    }
    
    
    //  OTHERS
    
    public void complete(Long id) throws SQLException{
        
        Task task = this.read(id, false);
        task.setIsComplete(true);
        task.setDateOfCompletion(Calendar.getInstance());
        
        this.update(task);
        
    }
  
    
    //  PRIVATE
    
    @SuppressWarnings("finally")
    private Task read(Long id, boolean closeAfterUse) throws SQLException{

        String sqlQuery = "select * from tasks where id=?";

        PreparedStatement statement;
        Task task = new Task();

        try {
            statement = connection.prepareStatement(sqlQuery);

            //	Replacing "?" through values
            statement.setLong(1, id);

            // 	Executing statement
            ResultSet results = statement.executeQuery();

            if(results.next()){

                task.setId(results.getLong("id"));
                task.setDescription(results.getString("description"));
                task.setIsComplete(results.getBoolean("is_complete"));

                Date dateOfCompletion = results.getDate("date_of_completion");
                
                if (dateOfCompletion == null){
                    task.setDateOfCompletion(null);
                }
                
                else{
                    Calendar date = Calendar.getInstance();
                    date.setTime(dateOfCompletion);
                    task.setDateOfCompletion(date);
                }

            }

            statement.close();

        } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } finally {
            
            if (closeAfterUse) {
                connection.close();
    
            }
            
            return task;
        }

    }

}