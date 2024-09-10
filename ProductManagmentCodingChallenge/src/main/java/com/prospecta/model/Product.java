package com.prospecta.model;

public class Product {
	private int id;
	private double price;
	private String title, description, category, image;
	private Rating rating;
	
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Product(int id, double price, String title, String description, String category, String image,
			Rating rating) {
		super();
		this.id = id;
		this.price = price;
		this.title = title;
		this.description = description;
		this.category = category;
		this.image = image;
		this.rating = rating;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public Rating getRating() {
		return rating;
	}


	public void setRating(Rating rating) {
		this.rating = rating;
	}
	
	
}
