/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vnuk.tasks.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
        return "_layout";
    }
    
    
    @RequestMapping("/tasks/{id}")
    public String show(@PathVariable("id") Long id, Model model, ServletRequest request) throws SQLException{
        model.addAttribute("task", new TaskDao((Connection) request.getAttribute("myConnection")).read(id));
        model.addAttribute("template", "task/show");
        return "_layout";
    }
    
    
    @RequestMapping("/tasks/new")
    public String add(Task task, Model model, @ModelAttribute("fieldErrors") ArrayList<FieldError> fieldErrors){
    	
    	for(FieldError fieldError : fieldErrors) {
    		model.addAttribute(
    				String.format("%sFieldError", fieldError.getField()),
    				fieldError.getDefaultMessage()
    			);
    	}
    	
        model.addAttribute("template", "task/new");
        return "_layout";
    }
    
    
    @RequestMapping("/tasks/{id}/edit")
    public String edit(
    		
		@RequestParam(value="backToShow", defaultValue="false") Boolean backToShow,
		@PathVariable("id") Long id,
		Task task,
		Model model,
		ServletRequest request,
		@ModelAttribute("fieldErrors") ArrayList<FieldError> fieldErrors
		
	) throws SQLException{
    	
    	
    	task = new TaskDao((Connection) request.getAttribute("myConnection")).read(id);
    	
    	for(FieldError fieldError : fieldErrors) {
    		model.addAttribute(
    				String.format("%sFieldError", fieldError.getField()),
    				fieldError.getDefaultMessage()
    			);
    	}
    	
    	
    	model.addAttribute("backToShow", backToShow);
    	model.addAttribute("urlCompletion", backToShow ? String.format("/%s", id) : "");
    	model.addAttribute("task", task);
        model.addAttribute("template", "task/edit");

        return "_layout";
    
        
    }
    
    
    @RequestMapping(value="/tasks", method=RequestMethod.POST)
    public String create(
		
    	@Valid Task task,
    	BindingResult bindingResult,
    	ServletRequest request,
    	RedirectAttributes redirectAttributes
    
    ) throws SQLException{
        
    	
        if (bindingResult.hasErrors()) {
        	redirectAttributes.addFlashAttribute("fieldErrors", bindingResult.getAllErrors());
            return "redirect:/tasks/new";
        }
        
        new TaskDao((Connection) request.getAttribute("myConnection")).create(task);
        return "redirect:/tasks";
        
        
    }
    
    
    @RequestMapping(value="/tasks/{id}", method=RequestMethod.PATCH)
    public String update(
    		
    		@RequestParam(value="backToShow", defaultValue="false") Boolean backToShow,
    		@PathVariable("id") Long id,
    		@Valid Task task,
    		BindingResult bindingResult,
    		ServletRequest request,
    		RedirectAttributes redirectAttributes
    		
    	) throws SQLException{
    	
        
    	if (bindingResult.hasErrors()) {
        	redirectAttributes.addFlashAttribute("fieldErrors", bindingResult.getAllErrors());
            return String.format("redirect:/tasks/%s/edit", id);
        }
        
        new TaskDao((Connection) request.getAttribute("myConnection")).update(task);
        return backToShow ? String.format("redirect:/tasks/%s", id) : "redirect:/tasks";
        
        
    }
    
    
    //  delete with ajax
    @RequestMapping(value="/tasks/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id, ServletRequest request, HttpServletResponse response) throws SQLException {
    	new TaskDao((Connection) request.getAttribute("myConnection")).delete(id);
        response.setStatus(200);
    }
    
    
    //  complete with ajax
    @RequestMapping(value="/tasks/{id}/complete", method = RequestMethod.PATCH)
    public void complete(@PathVariable("id") Long id, ServletRequest request, HttpServletResponse response) throws SQLException {
    	new TaskDao((Connection) request.getAttribute("myConnection")).complete(id);
        response.setStatus(200);
    }
    
}
