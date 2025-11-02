package com.junhee92kr.cafe.graphql;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MenuQuery {
	@QueryMapping
	public String getMenuList() {
		return "hello";
	}
}
