package model;


import java.util.ArrayList;


public class Drawing {
	
	private ArrayList<Figure> figures = new ArrayList<Figure>();
	
	public int getIndex(int i) {
		return this.figures.indexOf(getFigure(i));
	}
	
	public Figure getFigure(int index) {
		return this.figures.get(index);
	}
	
	public int getSize() {
		return this.figures.size();
	}

	
	public int getLastIndex() {
		return this.figures.indexOf(getFigure(getSize()));
	}
	
	public void addFigure(Figure figure) {
		this.figures.add(figure);
	}
	
	public void removeFigure(int i) {
		this.figures.remove(i);
	}

}
