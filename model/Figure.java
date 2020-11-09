package model;


import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Figure {

	
	private static final double PI = Math.PI;
	
	private int index;
	
	private String id;
	
	private double startX;
	private double startY;
	
	private double width;
	private double height;
	private Paint fillColor;
	private Paint strokeColor;
	
	private double circumference;
	private double area;

	
	public Figure() {
		
	}
	
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public double getStartX() {
		return startX;
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}

	public double getStartY() {
		return startY;
	}

	public void setStartY(double startY) {
		this.startY = startY;
	}
	
	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getCircumference() {
		return circumference;
	}
	
	public void setCircumference(double circumference) {
		this.circumference = circumference;
	}

	public double getArea() {
		return area;
	}
	
	public void setArea(double area) {
		this.area = area;
	}

	public Paint getFillColor() {
		return fillColor;
	}

	public void setFillColor(Paint fillColor) {
		this.fillColor = fillColor;
	}

	public Paint getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(Paint strokeColor) {
		this.strokeColor = strokeColor;
	}
	
	
	


	

	
	public static double getPi() {
		return PI;
	}
	
	
	public Figure shapeToFigure(int shapeIndex,
									   String shapeId,
									   double startX,
									   double startY,
									   double width,
									   double height,
									   Paint fillColor,
									   Paint strokeColor) {
		Figure f = new Figure();
		f.setIndex(shapeIndex);
		if (shapeId.equals("Rechteck") || shapeId.equals("Quadrat")) {
			f.setQuadrangle(width, height);
			}
		if (shapeId.equals("Ellipse") || shapeId.equals("Kreis")) {
			f.setEllipse(width, height);
			}
		if (shapeId.equals("Dreieck")) {
			f.setTriangle(width, height);
		}
		if (shapeId.equals("Polygon")) {
			f.setPolygon(width, height);
		}
		
		if (shapeId.equals("Linie")) {
			f.setLine(width, height);
		}
		
		f.setStartX(startX);
		f.setStartY(startY);
		f.setWidth(width);
		f.setHeight(height);
		f.setFillColor(fillColor);
		f.setStrokeColor(strokeColor);
		
		return f;
	}
	
	
	public Rectangle figureToRectangle(String figureId,
								   double startX,
								   double startY,
								   double width,
								   double height,
								   Paint fillColor,
								   Paint strokeColor) {
		Rectangle r = new Rectangle(startX, startY, width, height);
        r.setId(figureId);
        r.setFill(fillColor);
        r.setStroke(strokeColor);
						
        return r;
	}
	
	public Ellipse figureToEllipse(String figureId,
			   double centerX,
			   double centerY,
			   double radiusX,
			   double radiusY,
			   Paint fillColor,
			   Paint strokeColor) {
	
		Ellipse e = new Ellipse(centerX, centerY, radiusX, radiusY);
		e.setId(figureId);
		e.setFill(fillColor);
		e.setStroke(strokeColor);
			
		return e;
}
	
	// figureToTriangle
	
	public Polygon figureToTriangle(String figureId,
			   double point1X,
			   double point1Y,
			   double point2X,
			   double point2Y,
			   double point3X,
			   double point3Y,
			   Paint fillColor,
			   Paint strokeColor) {
	
		Polygon p3 = new Polygon();
		p3.getPoints().addAll(point1X,
							  point1Y,
							  point2X,
							  point2Y,
							  point3X,
							  point3Y);
		p3.setId(figureId);
		p3.setFill(fillColor);
		p3.setStroke(strokeColor);
			
		return p3;
}
	
	// figureToPolygon
	
	public Polygon figureToPolygon(String figureId,
			   double point1X,
			   double point1Y,
			   double point2X,
			   double point2Y,
			   double point3X,
			   double point3Y,
			   double point4X,
			   double point4Y,
			   double point5X,
			   double point5Y,
			   double point6X,
			   double point6Y,
			   Paint fillColor,
			   Paint strokeColor) {
	
		Polygon p6 = new Polygon();
		p6.getPoints().addAll(point1X,
							  point1Y,
							  point2X,
							  point2Y,
							  point3X,
							  point3Y,
							  point4X,
							  point4Y,
							  point5X,
							  point5Y,
							  point6X,
							  point6Y);
							  
		p6.setId(figureId);
		p6.setFill(fillColor);
		p6.setStroke(strokeColor);
			
		return p6;
	}
	
	
	

		public Line figureToLine(String figureId,
				   double lStartX,
				   double lStartY,
				   double lEndX,
				   double lEndY,
				   Paint fillColor,
				   Paint strokeColor) {
		
			
			Line l = new Line(lStartX, lStartY, lEndX, lEndY);
			l.setId(figureId);
			l.setFill(fillColor);
			l.setStroke(strokeColor);
				
			return l;
		
		}
	
	
	public void setQuadrangle(double width, double height) {
		
		
		
		this.circumference = (2 * width) + (2 * height);
		this.area = width*height;
		
		if (width == height) {
			this.id = "Quadrat";
		} else {
			this.id = "Rechteck";
		}
	}
	
	public void setEllipse(double width, double height) {
	
		
		if (width == height) {
			this.circumference = 2 * PI * width/2;
			this.area = PI * (width/2 * width/2);
			this.id = "Kreis";
		} else {
			 double wurzel = Math.sqrt(2 * ((width/2*width/2)+(height/2*height/2)));
			 this.circumference = PI * wurzel;
			 this.area = (width/2 * height/2) * PI;
			 this.id = "Ellipse";
		}

	}
	
public void setTriangle(double width, double height) {
	    double c = Math.sqrt((width * width) + (height * height));
		this.circumference = width + height + c;
		this.area = (width * height)/2;
		this.id = "Dreieck";
	}

public void setPolygon(double width, double height) {
	
	int corners = 6;
	this.circumference = corners * (2 * (width/2) * Math.sin(PI/corners));
	this.area = ((corners)/2) * ((width/2) * (width/2)) * Math.sin(PI*(360/corners)/180);
	this.id = "Polygon";
}


public void setLine(double width, double height) {
	this.circumference = 0;
	this.area = 0;
	this.id = "Linie";
}



	


	public boolean selectTest(double x, double y) {
		
		if (x > this.startX && x < (this.startX + this.width) && y > this.startY && y < (this.startY + this.height)) {
			return true;
			} else {
				return false;
			}
		}	
	}
