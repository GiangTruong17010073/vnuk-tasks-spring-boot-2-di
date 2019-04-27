/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vnuk.tasks.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vnuk.tasks.dao.TaskDao;
import vn.edu.vnuk.tasks.model.Task;

/**
 *
 * @author michel
 */
@Controller
public class TasksController {
	
//  @RequestMapping(value={"", "/", "tasks"})
    
	
	@RequestMapping("/tasks")
    public String index(Model model, ServletRequest request) throws SQLException{
        model.addAttribute("tasks", new TaskDao((Connection) request.getAttribute("myConnection")).read());
        model.addAttribute("template", "task/index");
        return "layout";
    }
    
    
    @RequestMapping("/tasks/{id}")
    public String show(@PathVariable("id") Long id, Model model, ServletRequest request) throws SQLException{
        model.addAttribute("task", new TaskDao((Connection) request.getAttribute("myConnection")).read(id));
        model.addAttribute("template", "task/show");
        return "layout";
    }
    
    
    @RequestMapping("/tasks/new")
    public String add(Task task, Model model, @ModelAttribute("fieldErrors") ArrayList<FieldError> fieldErrors){
    	
    	for(FieldError fieldError : fieldErrors) {
    		model.addAttribute(String.format("error_field_%s", fieldError.getField()), fieldError.getDefaultMessage());
    	}
    	
        model.addAttribute("template", "task/new");
        System.out.println(model);
        return "layout";
    }
    
    
    @RequestMapping("/tasks/{id}/edit")
    public String edit(@RequestParam Map<String, String> params, Model model, ServletRequest request) throws SQLException{
    	Long id = Long.parseLong(params.get("id").toString());
        model.addAttribute("task", new TaskDao((Connection) request.getAttribute("myConnection")).read(id));
        return "task/edit";
    }
    
    
    @RequestMapping(value="/tasks", method=RequestMethod.POST)
    public String create(@Valid Task task, BindingResult bindingResult, ServletRequest request, RedirectAttributes redirectAttributes) throws SQLException{
        
        if (bindingResult.hasErrors()) {
        	redirectAttributes.addFlashAttribute("fieldErrors", bindingResult.getAllErrors());
            return "redirect:/tasks/new";
        }
        
        new TaskDao((Connection) request.getAttribute("myConnection")).create(task);
        return "redirect:tasks";
    }
    
    
    @RequestMapping(value="/tasks/{id}", method=RequestMethod.PATCH)
    public String update(@Valid Task task, BindingResult result, ServletRequest request) throws SQLException{
        
        if (result.hasFieldErrors("description")) {
            return "task/edit";
        }
        
        
        
        Calendar dateOfCompletion = null;
        
        
        if(task.getDateInStringFormat() != null) {
        	
    		// 	converting string to data
    		try {
    			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(task.getDateInStringFormat());
    			dateOfCompletion = Calendar.getInstance();
    			dateOfCompletion.setTime(date);
    		} 
    		
    		catch (ParseException e) {
    			System.out.println("Error while converting date");
    			return null;
    		}
        }
        
        task.setDateOfCompletion(dateOfCompletion);
        
        new TaskDao((Connection) request.getAttribute("myConnection")).update(task);
        return String.format("redirect:tasks/%s", task.getId());
    }
    
    
    //  DELETE WITH AJAX
    @RequestMapping(value="/tasks/{id}", method = RequestMethod.DELETE)
    public void delete(Long id, ServletRequest request, HttpServletResponse response) throws SQLException {
    	new TaskDao((Connection) request.getAttribute("myConnection")).delete(id);
        response.setStatus(200);
    }
    
    
    @RequestMapping(value="/tasks/{id}/complete", method = RequestMethod.PATCH)
    public void complete(Long id, ServletRequest request, HttpServletResponse response) throws SQLException {
    	new TaskDao((Connection) request.getAttribute("myConnection")).complete(id);
        response.setStatus(200);
    }
    
}
