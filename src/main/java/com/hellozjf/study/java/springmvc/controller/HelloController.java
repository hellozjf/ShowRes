package com.hellozjf.study.java.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hellozjf.study.java.springmvc.pojo.HelloPojo;

@Controller
public class HelloController {

	@RequestMapping(value = "/hello.do", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String hello() {
		HelloPojo helloPojo = new HelloPojo();
		helloPojo.setId(1);
		helloPojo.setName("hello");
		
		return JSONObject.toJSONString(helloPojo);
	}
}
