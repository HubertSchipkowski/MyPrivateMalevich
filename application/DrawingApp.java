package application;

import model.*;


import java.util.ArrayList;
import java.util.EventObject;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;


/**
 * Dateiname	: DrawingApp.java
 *
 * Little desktop drawing application in Java with JavaFX.
 *
 * @author      Hubert Schipkowski (schipkowski@sincerelycontent.com)
 *
 *
 * @version     1.0.1, 28.06.2020
 *
 */


public class DrawingApp extends Application {


	// Dimensions
	public static enum basisShape {
		RECTANGLE, ELLIPSE, TRIANGLE, POLYGON, LINE
	}

	final double CANVASWIDTH = 1800;
	final double CANVASHEIGHT = 700;



	// Containers for Data clipboards
				int threshold = 0;

				double startX = 0.0, startY = 0.0;
				double endX = 0.0, endY = 0.0;

				static int currentIndexData = 0;
				Double currentStartXData = 0.0;
				Double currentStartYData = 0.0;
				Double currentWidthData = 0.0;
				Double currentHeightData = 0.0;
				Double currentCircumferenceData = 0.0;
				Double currentAreaData = 0.0;


				String currentIndexText = "";
				String currentStartXText = "";
				String currentStartYText = "";
				String currentWidthText = "";
				String currentHeightText = "";
				String currentID = "";
				String currentCircumferenceText = "";
				String currentAreaText = "";

				Random mixer = new Random();
				static basisShape currentMix = basisShape.RECTANGLE;


				public static int getCurrentIndexData() {
					return currentIndexData;
				}


	// Data Storage

				Drawing register = new Drawing();




	@Override
	public void start(Stage mainWindow) {
		try {


			// Framework + Controls

			BorderPane layout = new BorderPane();

			layout.setPrefSize(1800, 900);

			Scene container = new Scene(layout,1800,900);


			DrawingPane canvas = new DrawingPane();


			Rectangle canvasBackground = new Rectangle(0, 0, CANVASWIDTH, CANVASHEIGHT);

			IOPane controls = new IOPane();
			HBox controlPanel = new HBox();

			Label indexLabel = new Label("Index: ");
			TextField indexController = new TextField("0");

			Label xLabel = new Label("X: ");
			TextField startXController = new TextField("0");

			Label yLabel = new Label("Y: ");
			TextField startYController = new TextField("0");

			Label widthLabel = new Label("w: ");
			TextField widthController = new TextField("0");

			Label heightLabel = new Label("h: ");
			TextField heightController = new TextField("0");


			Button send = new Button("Send");


			Label formLabel = new Label("Figure: ");
			TextField idLabel = new TextField("");
			Label circumferenceL = new Label("C: ");
			TextField circumferenceLabel = new TextField("0");
			Label areaL = new Label("A: ");
			TextField areaLabel = new TextField("0");




			// Miscellaneous

			EventObject mouseEventCatcher = new EventObject(canvas);
			Color farbe1 = new Color(0.933, 0.910, 0.835, 1);


			// Modifications for styling


			canvasBackground.setFill(farbe1);
			canvasBackground.setStroke(Color.GOLDENROD);
			mainWindow.setTitle("My Private Malevich");

			// Arrangement


			layout.setCenter(canvas);
			layout.setBottom(controls);


			canvas.getChildren().add(canvasBackground);

			controlPanel.setAlignment(Pos.BASELINE_CENTER);
			controlPanel.setPadding(new Insets(25,25,25,25));
			controlPanel.getChildren().addAll(indexLabel, indexController, xLabel, startXController, yLabel, startYController, widthLabel, widthController, heightLabel, heightController, send, formLabel, idLabel, circumferenceL, circumferenceLabel, areaL, areaLabel);
			controls.setTop(controlPanel);




			// Mouse Data Channel

			canvas.setOnMousePressed(s -> {
				startX = s.getX();
				startY = s.getY();
				});


			canvas.setOnMouseDragged(f -> {

				if (f.getX() < CANVASWIDTH) {
				endX = f.getX();
				}

				if (f.getY() < CANVASHEIGHT) {
				endY = f.getY();
				}

				threshold++;
			});


			canvas.setOnMouseReleased(e -> {


				if (threshold > 0 && threshold < 20) {

					if (register.getSize() > 0) {
						for (int i = 0; i < register.getSize(); i++) {
							if (register.getFigure(i).selectTest(e.getX(), e.getY()) == true) {

							currentIndexData = i+1;

							selectFigure(currentIndexData,
									  indexController,
									  startXController,
									  startYController,
									  widthController,
									  heightController,
									  idLabel,
									  circumferenceLabel,
									  areaLabel,
									  e.getX(),
									  e.getY());
							render(canvas,canvasBackground);

							break;
							}
						}
						threshold = 0;
					}


				} else if (threshold >= 20 && startX < endX && startY < endY) {


					basisShape[] mixerShapes = basisShape.values();

					for (int i = 0; i < mixerShapes.length; i++ ) {
						System.out.println(mixerShapes[i]);
					}


					currentMix = mixerShapes[mixer.nextInt(mixerShapes.length)];
					System.out.println(currentMix);


					int shapeIndex = canvas.getChildren().size();


					double[] newEllipseParameters = ellipseMapper1(startX, startY, endX, endY);

					double[] newTriangleParameters = triangleMapper1(startX, startY, endX, endY);

					double[] newPolygonParameters = polygonMapper1(startX, startY, endX, endY);

					double[] newLineParameters = lineMapper1(startX, startY, endX, endY);


					ArrayList <Color> colorBox = new ArrayList<Color>();
					colorBox.add(Color.CHOCOLATE);
					colorBox.add(Color.BLUE);
					colorBox.add(Color.GREEN);
					colorBox.add(Color.YELLOW);



					switch (currentMix) {

						case RECTANGLE: Rectangle re = new Rectangle(startX, startY, endX - startX, endY - startY);


							DrawingApp.currentIndexData = shapeIndex;
							re.setId("Rechteck");
							re.setFill(colorBox.get(mixer.nextInt(colorBox.size())));
							re.setStroke(Color.CHOCOLATE);

							Figure fr = new Figure();

							register.addFigure(fr.shapeToFigure(shapeIndex,
									re.getId(),
									re.getX(),
									re.getY(),
									re.getWidth(),
									re.getHeight(),
									re.getFill(),
									re.getStroke()));
							break;



						case ELLIPSE: Ellipse el = new Ellipse(newEllipseParameters[0],
														 newEllipseParameters[1],
														 newEllipseParameters[2],
														 newEllipseParameters[3]);


								DrawingApp.currentIndexData = shapeIndex;
								el.setId("Ellipse");
								el.setFill(colorBox.get(mixer.nextInt(colorBox.size())));
								el.setStroke(Color.CHOCOLATE);

								Figure fe = new Figure();

								register.addFigure(fe.shapeToFigure(shapeIndex,
										el.getId(),
										startX,
										startY,
										endX - startX,
										endY - startY,
										el.getFill(),
										el.getStroke()));

								for (int i = 0; i < register.getSize(); i++ ) {
									System.out.println(register.getIndex(i) + " " + register.getFigure(i).getId());
								}


									break;


						case TRIANGLE: Polygon p3 = new Polygon();
									p3.getPoints().addAll(newTriangleParameters[0],
														  newTriangleParameters[1],
														  newTriangleParameters[2],
														  newTriangleParameters[3],
														  newTriangleParameters[4],
														  newTriangleParameters[5]);

									DrawingApp.currentIndexData = shapeIndex;
									p3.setId("Dreieck");
									p3.setFill(colorBox.get(mixer.nextInt(colorBox.size())));
									p3.setStroke(Color.CHOCOLATE);

									Figure ft = new Figure();

									register.addFigure(ft.shapeToFigure(shapeIndex,
											p3.getId(),
											startX,
											startY,
											endX - startX,
											endY - startY,
											p3.getFill(),
											p3.getStroke()));

									for (int i = 0; i < register.getSize(); i++ ) {
										System.out.println(register.getIndex(i) + " " + register.getFigure(i).getId());
									}


										break;


						 case POLYGON: Polygon p6 = new Polygon();
								 p6.getPoints().addAll(newPolygonParameters[0],
										  newPolygonParameters[1],
										  newPolygonParameters[2],
										  newPolygonParameters[3],
										  newPolygonParameters[4],
										  newPolygonParameters[5],
										  newPolygonParameters[6],
										  newPolygonParameters[7],
										  newPolygonParameters[8],
										  newPolygonParameters[9],
										  newPolygonParameters[10],
										  newPolygonParameters[11]);

									DrawingApp.currentIndexData = shapeIndex;
									p6.setId("Polygon");
									p6.setFill(colorBox.get(mixer.nextInt(colorBox.size())));
									p6.setStroke(Color.CHOCOLATE);

									Figure fp = new Figure();

									register.addFigure(fp.shapeToFigure(shapeIndex,
											p6.getId(),
											startX,
											startY,
											endX - startX,
											endY - startY,
											p6.getFill(),
											p6.getStroke()));

									for (int i = 0; i < register.getSize(); i++ ) {
										System.out.println(register.getIndex(i) + " " + register.getFigure(i).getId());
									}

										break;

						 case LINE: Line l = new Line();

								 l.setStartX(newLineParameters[0]);
								 l.setStartY(newLineParameters[1]);
								 l.setEndX(newLineParameters[2]);
								 l.setEndY(newLineParameters[3]);



									DrawingApp.currentIndexData = shapeIndex;
									l.setId("Linie");
									l.setFill(Color.BLACK);
									l.setStroke(Color.BLACK);

									Figure fl = new Figure();

									register.addFigure(fl.shapeToFigure(shapeIndex,
											l.getId(),
											startX,
											startY,
											endX - startX,
											endY - startY,
											l.getFill(),
											l.getStroke()));

									for (int i = 0; i < register.getSize(); i++ ) {
										System.out.println(register.getIndex(i) + " " + register.getFigure(i).getId());
									}

										break;

					}


					readModel(indexController,
							  startXController,
							  startYController,
							  widthController,
							  heightController,
							  idLabel,
							  circumferenceLabel,
							  areaLabel);

					threshold = 0;

					render(canvas,canvasBackground);





				// Button Data Channel

					send.setOnAction(update -> {



						if(register.getSize() > 0) {



							if (Integer.parseInt(indexController.getText()) > 0 && Integer.parseInt(indexController.getText()) <= register.getSize()) {

								if (currentIndexData != Integer.parseInt(indexController.getText())) {

									currentIndexData = Integer.parseInt(indexController.getText());

									double x = Double.parseDouble(currentStartXText);
									double y = Double.parseDouble(currentStartYText);

									selectFigure(currentIndexData,
											  indexController,
											  startXController,
											  startYController,
											  widthController,
											  heightController,
											  idLabel,
											  circumferenceLabel,
											  areaLabel,
										      x,
										      y);
									render(canvas,canvasBackground);


								} else {


									writeModel(indexController,
											  startXController,
											  startYController,
											  widthController,
											  heightController,
											  idLabel,
											  circumferenceLabel,
											  areaLabel);

									for (int i = 0; i <= register.getSize()-1; i++) {
										System.out.println("Index: " + register.getIndex(i) + " - ID: " + register.getFigure(i).getId());
									}

									render(canvas,canvasBackground);
								}

							} else {
								currentIndexData = 1;

								double x = Double.parseDouble(currentStartXText);
								double y = Double.parseDouble(currentStartYText);

								selectFigure(currentIndexData,
										  indexController,
										  startXController,
										  startYController,
										  widthController,
										  heightController,
										  idLabel,
										  circumferenceLabel,
										  areaLabel,
										  x,
										  y);
								render(canvas,canvasBackground);
							}

						}
					});
				}


				System.out.println(e.getEventType());
			});


			// Publishing


			mainWindow.setScene(container);
			mainWindow.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	// Methods


	// Mapping

	public double[] ellipseMapper1(double startX, double startY, double endX, double endY) {
		double centerX = startX + ((endX - startX)/2);
		double centerY = startY + ((endY - startY)/2);
		double radiusX = (endX - startX)/2;
		double radiusY = (endY - startY)/2;

		double[] ellipseParameters = {centerX, centerY, radiusX, radiusY};

		return ellipseParameters;
	}


	public double[] ellipseMapper2(double startX, double startY, double width, double height) {
		double centerX = startX + width/2;
		double centerY = startY + height/2;
		double radiusX = width/2;
		double radiusY = height/2;

		double[] ellipseParameters = {centerX, centerY, radiusX, radiusY};

		return ellipseParameters;
	}



	public double[] triangleMapper1(double startX, double startY, double endX, double endY) {
		double point1X = startX;
		double point1Y = startY;

		double point2X = startX+(endX - startX);
		double point2Y = startY+((endY - startY)/2);

		double point3X = startX;
		double point3Y = startY + (endY - startY);

		double[] triangleParameters = {point1X, point1Y,
									   point2X, point2Y,
									   point3X, point3Y};

		return triangleParameters;

	}

	public double[] triangleMapper2(double startX, double startY, double width, double height) {
		double point1X = startX;
		double point1Y = startY;

		double point2X = startX+width;
		double point2Y = startY+(height/2);

		double point3X = startX;
		double point3Y = startY + height;

		double[] triangleParameters = {point1X, point1Y,
									   point2X, point2Y,
									   point3X, point3Y};

		return triangleParameters;
	}




	public double[] polygonMapper1(double startX, double startY, double endX, double endY) {

		double point1X = startX + ((endX - startX)/4);
		double point1Y = startY;

		double point2X = startX + (3 * ((endX - startX)/4));
		double point2Y = startY;

		double point3X = startX + (endX - startX);
		double point3Y = startY + ((endY - startY)/2);

		double point4X = startX + (3 * ((endX - startX)/4));
		double point4Y = startY + (endY - startY);

		double point5X = startX + ((endX - startX)/4);
		double point5Y = startY + (endY - startY);

		double point6X = startX;
		double point6Y = startY + ((endY - startY)/2);

		double[] polygonParameters = {point1X, point1Y,
									   point2X, point2Y,
									   point3X, point3Y,
									   point4X, point4Y,
									   point5X, point5Y,
									   point6X, point6Y};

		return polygonParameters;
	}





public double[] polygonMapper2(double startX, double startY, double width, double height) {

		double point1X = startX + (width/4);
		double point1Y = startY;

		double point2X = startX + (3 * (width/4));
		double point2Y = startY;

		double point3X = startX + width;
		double point3Y = startY + (height/2);

		double point4X = startX + (3 * (width/4));
		double point4Y = startY + height;

		double point5X = startX + (width/4);
		double point5Y = startY + height;

		double point6X = startX;
		double point6Y = startY + (height/2);

		double[] polygonParameters = {point1X, point1Y,
									   point2X, point2Y,
									   point3X, point3Y,
									   point4X, point4Y,
									   point5X, point5Y,
									   point6X, point6Y};

		return polygonParameters;
	}



public double[] lineMapper1(double startX, double startY, double endX, double endY) {

		double lStartX = startX;
		double lStartY = startY;

		double lEndX = endX;
		double lEndY = endY;


		double[] lineParameters = {lStartX,
								   lStartY,
								   lEndX,
								   lEndY};


		return lineParameters;

}



public double[] lineMapper2(double startX, double startY, double width, double height) {

	double lStartX = startX;
	double lStartY = startY;

	double lEndX = startX + width;
	double lEndY = startY + height;


	double[] lineParameters = {lStartX,
							   lStartY,
							   lEndX,
							   lEndY};


	return lineParameters;


}

	// Handling

	public void writeModel(TextField indexController,
						   TextField startXController,
						   TextField startYController,
						   TextField widthController,
						   TextField heightController,
						   TextField idLabel,
						   TextField circumferenceLabel,
						   TextField areaLabel) {


		int registerIndex = currentIndexData-1;


		currentIndexText = indexController.getText();
		currentStartXText = startXController.getText();
		currentStartYText = startYController.getText();
		currentWidthText = widthController.getText();
		currentHeightText = heightController.getText();

		currentCircumferenceText = circumferenceLabel.getText();
		currentAreaText = areaLabel.getText();

		textToNumber();

		if (currentWidthData == 0 || currentHeightData == 0) {
			register.removeFigure(registerIndex);
			indexController.setText("0");
			startXController.setText("0");
			startYController.setText("0");
			widthController.setText("0");
			heightController.setText("0");
			idLabel.setText(" ");
			circumferenceLabel.setText("0");
			areaLabel.setText("0");


			if (register.getSize() > 0 && currentIndexData <= register.getSize()) {

				for (int i = 0; i < register.getSize(); i++) {
				register.getFigure(i).setIndex(i+1);
					}
				}

		} else {

		if (currentStartXData >= 0 && currentStartYData >= 0 && currentStartXData + currentWidthData < CANVASWIDTH && currentStartYData + currentHeightData < CANVASHEIGHT) {

		register.getFigure(registerIndex).setIndex(currentIndexData);
		register.getFigure(registerIndex).setStartX(currentStartXData);
		register.getFigure(registerIndex).setStartY(currentStartYData);
		register.getFigure(registerIndex).setWidth(currentWidthData);
		register.getFigure(registerIndex).setHeight(currentHeightData);
		register.getFigure(registerIndex).setCircumference(currentCircumferenceData);
		register.getFigure(registerIndex).setArea(currentAreaData);



		currentID = register.getFigure(registerIndex).getId();



		if (currentID.equals("Rechteck") || currentID.equals("Quadrat")) {
			register.getFigure(registerIndex).setQuadrangle(currentWidthData, currentHeightData);
		} else if (currentID.equals("Ellipse") || currentID.equals("Kreis")) {
			register.getFigure(registerIndex).setEllipse(currentWidthData, currentHeightData);
		} else if (currentID.contentEquals("Dreieck")) {
			register.getFigure(registerIndex).setTriangle(currentWidthData, currentHeightData);
		} else if (currentID.contentEquals("Polygon")) {
			register.getFigure(registerIndex).setPolygon(currentWidthData, currentHeightData);
		} else if (currentID.contentEquals("Linie")) {
			register.getFigure(registerIndex).setLine(currentWidthData, currentHeightData);
		}


		currentID = register.getFigure(registerIndex).getId();
		numberToText();
		idLabel.setText(currentID);
			}
		}
	}



	public void textToNumber() {

		try {

		currentIndexData = Integer.parseInt(currentIndexText);
		currentStartXData = Double.parseDouble(currentStartXText);
		currentStartYData = Double.parseDouble(currentStartYText);
		currentWidthData = Double.parseDouble(currentWidthText);
		currentHeightData = Double.parseDouble(currentHeightText);
		currentCircumferenceData = Double.parseDouble(currentCircumferenceText);
		currentAreaData = Double.parseDouble(currentAreaText);
		} catch(NumberFormatException e) {
			System.out.println("Keine numerische Eingabe.");
			currentIndexText = String.valueOf(currentIndexData);
			currentStartXText = String.valueOf(currentStartXData);
			currentStartYText = String.valueOf(currentStartYData);
			currentWidthText = String.valueOf(currentWidthData);
			currentHeightText = String.valueOf(currentHeightData);
			currentCircumferenceText = String.valueOf(currentCircumferenceData);
			currentAreaText = String.valueOf(currentAreaData);

			currentIndexData = Integer.parseInt(currentIndexText);
			currentStartXData = Double.parseDouble(currentStartXText);
			currentStartYData = Double.parseDouble(currentStartYText);
			currentWidthData = Double.parseDouble(currentWidthText);
			currentHeightData = Double.parseDouble(currentHeightText);
			currentCircumferenceData = Double.parseDouble(currentCircumferenceText);
			currentAreaData = Double.parseDouble(currentAreaText);
		}
	}



	public void readModel(TextField indexController,
						  TextField startXController,
						  TextField startYController,
						  TextField widthController,
						  TextField heightController,
						  TextField idLabel,
						  TextField circumferenceLabel,
						  TextField areaLabel) {


		int registerIndex = currentIndexData-1;

		currentIndexData = register.getFigure(registerIndex).getIndex();
		currentStartXData = register.getFigure(registerIndex).getStartX();
		currentStartYData = register.getFigure(registerIndex).getStartY();
		currentWidthData = register.getFigure(registerIndex).getWidth();
		currentHeightData = register.getFigure(registerIndex).getHeight();
		currentID = register.getFigure(registerIndex).getId();
		currentCircumferenceData = register.getFigure(registerIndex).getCircumference();
		currentAreaData = register.getFigure(registerIndex).getArea();


		numberToText();

		indexController.setText(currentIndexText);
		startXController.setText(currentStartXText);
		startYController.setText(currentStartYText);
		widthController.setText(currentWidthText);
		heightController.setText(currentHeightText);
		idLabel.setText(currentID);
		circumferenceLabel.setText(currentCircumferenceText);
		areaLabel.setText(currentAreaText);
	}




	public void numberToText() {
		currentIndexText = String.valueOf(currentIndexData);
		currentStartXText = String.valueOf(currentStartXData);
		currentStartYText = String.valueOf(currentStartYData);
		currentWidthText = String.valueOf(currentWidthData);
		currentHeightText = String.valueOf(currentHeightData);
		currentCircumferenceText = String.valueOf(currentCircumferenceData);
		currentAreaText = String.valueOf(currentAreaData);
	}





	public void selectFigure(int newIndex,
							  TextField indexController,
							  TextField startXController,
							  TextField startYController,
							  TextField widthController,
							  TextField heightController,
							  TextField idLabel,
							  TextField circumferenceLabel,
							  TextField areaLabel,
							  Double x,
							  Double y) {
		currentIndexData = newIndex;
		int registerIndex = currentIndexData-1;


		for (int i = 0; i < register.getSize(); i++) {
			if (register.getFigure(i).selectTest(x, y) == true) {
		register.getFigure(i).setStrokeColor(Color.BLACK);
			} else {
				register.getFigure(i).setStrokeColor(Color.CHOCOLATE);
			}
			continue;
		}



		currentStartXData = register.getFigure(registerIndex).getStartX();
		currentStartYData = register.getFigure(registerIndex).getStartY();
		currentWidthData = register.getFigure(registerIndex).getWidth();
		currentHeightData = register.getFigure(registerIndex).getHeight();
		currentID = register.getFigure(registerIndex).getId();
		currentCircumferenceData = register.getFigure(registerIndex).getCircumference();
		currentAreaData = register.getFigure(registerIndex).getArea();

		numberToText();

		indexController.setText(currentIndexText);
		startXController.setText(currentStartXText);
		startYController.setText(currentStartYText);
		widthController.setText(currentWidthText);
		heightController.setText(currentHeightText);
		idLabel.setText(currentID);
		circumferenceLabel.setText(currentCircumferenceText);
		areaLabel.setText(currentAreaText);

	}



	// Drawing


	public void render(DrawingPane canvas, Rectangle canvasBackground) {


		canvas.getChildren().clear();
		canvas.getChildren().add(canvasBackground);

		if (register.getSize() > 0) {
		for (int i = 0; i < register.getSize(); i++) {

			Figure f = register.getFigure(i);


			if (f.getId().equals("Rechteck") || f.getId().equals("Quadrat")) {

					canvas.getChildren().add(f.figureToRectangle(f.getId(),
															f.getStartX(),
															f.getStartY(),
															f.getWidth(),
															f.getHeight(),
															f.getFillColor(),
															f.getStrokeColor()));
				} else if (f.getId().equals("Ellipse") || f.getId().equals("Kreis")) {

					double[] givenEllipseParameters = ellipseMapper2(f.getStartX(), f.getStartY(), f.getWidth(), f.getHeight());



					canvas.getChildren().add(f.figureToEllipse(f.getId(),
							givenEllipseParameters[0],
							givenEllipseParameters[1],
							givenEllipseParameters[2],
							givenEllipseParameters[3],
							f.getFillColor(),
							f.getStrokeColor()));

				} else if (f.getId().equals("Dreieck")) {



					double[] givenTriangleParameters = triangleMapper2(f.getStartX(), f.getStartY(), f.getWidth(), f.getHeight());



					canvas.getChildren().add(f.figureToTriangle(f.getId(),
							givenTriangleParameters[0],
							givenTriangleParameters[1],
							givenTriangleParameters[2],
							givenTriangleParameters[3],
							givenTriangleParameters[4],
							givenTriangleParameters[5],
							f.getFillColor(),
							f.getStrokeColor()));

				} else if (f.getId().equals("Polygon")) {



					double[] givenPolygonParameters = polygonMapper2(f.getStartX(), f.getStartY(), f.getWidth(), f.getHeight());



					canvas.getChildren().add(f.figureToPolygon(f.getId(),
							givenPolygonParameters[0],
							givenPolygonParameters[1],
							givenPolygonParameters[2],
							givenPolygonParameters[3],
							givenPolygonParameters[4],
							givenPolygonParameters[5],
							givenPolygonParameters[6],
							givenPolygonParameters[7],
							givenPolygonParameters[8],
							givenPolygonParameters[9],
							givenPolygonParameters[10],
							givenPolygonParameters[11],
							f.getFillColor(),
							f.getStrokeColor()));

				} else if (f.getId().equals("Linie")) {



					double[] givenLineParameters = lineMapper2(f.getStartX(), f.getStartY(), f.getWidth(), f.getHeight());



					canvas.getChildren().add(f.figureToLine(f.getId(),
							givenLineParameters[0],
							givenLineParameters[1],
							givenLineParameters[2],
							givenLineParameters[3],
							f.getFillColor(),
							f.getStrokeColor()));

				}
			}
	}
}





	public static void main(String[] args) {
		launch(args);
	}
}
