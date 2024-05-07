package com.example.demo.controller.response;

import lombok.Data;

@Data
public class TodoResponse implements Response{
	private int id;
	private boolean completed;
	private String title;
	private int userId;
}
