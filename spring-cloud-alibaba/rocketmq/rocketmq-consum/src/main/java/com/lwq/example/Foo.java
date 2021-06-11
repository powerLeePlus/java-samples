package com.lwq.example;

/**
 * @author lwq
 * @date 2021/6/11 0011
 */
public class Foo {

	private int id;

	private String bar;

	public Foo() {
	}

	public Foo(int id, String bar) {
		this.id = id;
		this.bar = bar;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"id\":")
				.append(id);
		sb.append(",\"bar\":\"")
				.append(bar).append('\"');
		sb.append('}');
		return sb.toString();
	}
}
