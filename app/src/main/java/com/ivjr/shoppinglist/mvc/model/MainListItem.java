package com.ivjr.shoppinglist.mvc.model;

public class MainListItem {

	public MainListItem(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
