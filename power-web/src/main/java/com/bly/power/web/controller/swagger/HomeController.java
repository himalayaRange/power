package com.bly.power.web.controller.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class HomeController {

	@RequestMapping("/swagger")
	public String swagger() {
		
		return "redirect:swagger-ui.html";
	}
}
