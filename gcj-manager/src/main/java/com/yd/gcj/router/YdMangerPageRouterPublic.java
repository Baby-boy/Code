package com.yd.gcj.router;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class YdMangerPageRouterPublic {
	
	private final static String pageFiles = "public/";
	
	private final static String fuwushangPageFiles = "public/fuwushang/";
	
	private final static String guzhuPageFiles = "public/guzhu/";
	
	
	@RequestMapping("/hander")
	public String hander(HttpServletRequest request){
		return pageFiles+"hander";
	}
	
	@RequestMapping("/foot")
	public String foot(){
		return pageFiles+"foot";
	}
	
	@RequestMapping("/guzhu/{pageName}")
	public String guzhu(@PathVariable String pageName){
		String pathName = "/index";
		switch (pageName) {
			case "hander":
				pathName = guzhuPageFiles + guzhuHander();
				break;
			case "left":
				pathName = guzhuPageFiles + guzhuLeft();
				break;
		}
		
		return pathName;
	}
	
	@RequestMapping("/fuwushang/{pageName}")
	public String fuwushagn(@PathVariable String pageName){
		System.out.println("服务商路由");
		String pathName = "/index";
		switch (pageName) {
			case "hander":
				pathName = fuwushangPageFiles + fuwushangHander();
				break;
			case "left":
				pathName = fuwushangPageFiles + fuwushangLeft();
				break;
		}
		return pathName;
	}
	
	
	/*雇主个人信息模块*/
	public String guzhuHander(){
		return "hander";
	}
	
	public String guzhuLeft(){
		return "left";
	}
	
	
	/*服务商个人信息模块*/
	public String fuwushangHander(){
		return "hander";
	}
	
	public String fuwushangLeft(){
		return "left";
	}
	
	
	
}
