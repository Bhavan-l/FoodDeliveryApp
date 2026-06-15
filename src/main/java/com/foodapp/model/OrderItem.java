package com.foodapp.model;

public class OrderItem {

	private int orderItemId;
	private int orderId;
	private String itemName;
	private int qty;
	private double totalPrice;

	public OrderItem() {
	}

	public OrderItem(int orderItemId, int orderId,
			String itemName, int qty, double totalPrice) {

		this.orderItemId = orderItemId;
		this.orderId = orderId;
		this.itemName = itemName;
		this.qty = qty;
		this.totalPrice = totalPrice;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
}