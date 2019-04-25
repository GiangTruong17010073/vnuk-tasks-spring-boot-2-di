package vn.edu.vnuk.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
	
    @RequestMapping("/hello")
    public String execute(){
        return "hello";
    }
    
    @GetMapping("/hello-spring")
    public String execute(ModelMap modelMap){
        modelMap.put("greeting", "Hello Spring");
        return "hello-spring";
    }
    
    @GetMapping("/hello-message")
    public String execute(@RequestParam(name="message", required=false, defaultValue="Chào buổi sáng") String message, Model model){
        model.addAttribute("greeting", message);
        return "hello-spring";
    }

}
