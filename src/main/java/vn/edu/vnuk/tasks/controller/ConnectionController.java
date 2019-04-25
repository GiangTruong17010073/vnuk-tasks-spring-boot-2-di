package vn.edu.vnuk.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConnectionController {
	
    @RequestMapping("/connection/test")
    public String test(){
        return "connection/test";
    }
}
