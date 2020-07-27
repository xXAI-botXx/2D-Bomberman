package com.tobia.base;

public class Vertex {
	private float x;
	private float y;
	
	
	public Vertex(float xx, float yy) {
		x=xx;
		y=yy;
	}
	
	public String toString() {
		return"("+x+","+y+")";
	}
	
	public double length() {
		return Math.sqrt(x*x+y*y);
	}
	
	public Vertex skalarMult(float s) {
		return new Vertex(x*s, y*s);
	}
	
	public void skalarMultMod(float s) {
		x=s*x;
		y=s*y;
	}
	
	public Vertex add(Vertex v2) {
		return new Vertex(x+v2.x,y+v2.y);
	}
	
	public void addMod(Vertex v2) {
		x=x+v2.x;
		y=y+v2.y;
	}
	
	public Vertex sub(Vertex v2) {
		return new Vertex(x-v2.x,y-v2.y);
	}
	
	public void setX(float x) { //oder double xx
		this.x=x;					//oder x=xx;
	}
	
	public void setY(float y) {
		this.y=y;
	}
	
	public void addX(int i) {
		x += i;
	}
	
	public void addY(int i) {
		y += i;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public boolean equals(Object thatObject) {	//prüft die x und y koordinate von zwei objekten
		if(thatObject instanceof Vertex) {		//ist das eingegebene Objekt ein Objekt der Klasse Vertex?
			Vertex that = (Vertex) thatObject;  //thatObject wird gecastet, da es imoment nur irgendein Objekt ist
			return this.x==that.x && this.y==that.y;
		}else {
			return false;
		}
	}
	
	public double distance(Vertex that) {
		return Math.sqrt((that.x-x)*(that.x-/*this.*/x)+(that.y-y)*(that.y-y));
	}
}
