package com.tobia.base;



import javafx.scene.canvas.GraphicsContext;

public interface GameObject {
	
	public void render(GraphicsContext g); 
	public void update();	//vereinfacht einiges
}
