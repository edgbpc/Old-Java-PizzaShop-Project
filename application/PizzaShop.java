//Eric Goodwin CS2261 Section E01
//Dr. Salam Dhou

//required files - background.jpg, pizza-cheese.png, vegetable2.jpg, pepperoni1.jpg, OrderButton.png as provided in the zip file 


package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class PizzaShop extends Application {
	
	
static BorderPane pane = new BorderPane();  //creates the border pane.  
	
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
 
	   //sets the background image
	  Image background = new Image("background.jpg");
	  BackgroundImage bgImg = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize (BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

	  //sets the panes in the BorderPane Object
	  pane.setTop(new TopPane());
	  pane.setLeft(new LeftPane());
	  pane.setCenter(new CenterPane()); 
	  pane.setBackground(new Background(bgImg));
    
	  Scene OrderScene = new Scene(pane);
    
	  primaryStage.setHeight(500);
	  primaryStage.setWidth(650);
	  primaryStage.setResizable(false);
	  primaryStage.setTitle("Chubby Kitty Pizza Shop"); // Set the stage title
	  primaryStage.setScene(OrderScene); // Place the scene in the stage
    
	  primaryStage.show(); // Display the stage
    
 
  }
    
    //this method sets the bottom pane once the order button has been pushed
    static void setnewbottom(){
    	pane.setBottom(new BottomPane()); //adds the bottom pane
	
    }
  

    //this method resets all the pains to default
	static void resetpanes(){
		pane.setLeft(new LeftPane());
		pane.setBottom(new BottomPane());
		pane.setCenter(new CenterPane());
	}
  
    //this method is invoke when order button is pushed.  redraws the center pane and adds the arcs
	static void addcenteroverlay(int cheeseArcLenth, int veggieArcLenth, int pepArcLength){
		pane.setCenter(new CenterPane(cheeseArcLenth, veggieArcLenth, pepArcLength));
	}


	  
  
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */

	public static void main (String[] args){
		Application.launch(args);
	}
}
 


//TopPane contains the name of the Pizza Shop
class TopPane extends StackPane {
  public TopPane() {
	  setStyle("-fx-font-weight: bold; -fx-font-size: 15");
    getChildren().add(new Label("Chubby Kitty Pizza Company"));
  
 
    setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
  }
}

//Bottom Pane contains the code for the reset button
class BottomPane extends FlowPane {
	BottomPane(){
		setPadding(new Insets(0, 0, 0, 300));
		Button reset = new Button(" Click Here To Reset");
		reset.setStyle("-fx-font-weight: bold");
		getChildren().add(reset);
		
		reset.setOnAction(e-> {
	    	//System.out.print("Pushed"); TESTING ONLY
	    	LeftPane.cheeseArcLength=0;  //resets all the arclengths to 0
	    	LeftPane.veggieArcLength=0;
	    	LeftPane.pepArcLength = 0;

	    	PizzaShop.resetpanes();  //calls all the constructors to reset everything to default
	    });
	}
	
	
}


//LeftPane contains the menu of options and the majority of the logic necessary for the execution of the application
class LeftPane extends FlowPane {

	//variables control the size of the arc
	static int cheeseArcLength = 0;
	static int veggieArcLength = 0;
	static int pepArcLength =0;
		

	public LeftPane(){

		
		setVgap(15);
		
		getChildren().add(new Label("Place Your Order"));
		getChildren().add(new Label("\n\n\n"));
		
		setStyle("-fx-font-weight: bold; -fx-font-size: 14");
		
		
		setPrefWidth(150);
		
		//creates the selection boxes
		ComboBox<Integer> cheeseBox = new ComboBox<>();
	    cheeseBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8);
	    cheeseBox.setValue(0);
	    	    	    
	    ComboBox<Integer> veggieBox = new ComboBox<>();
	    veggieBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8);
	    veggieBox.setValue(0);
	 
	    ComboBox<Integer> pepBox = new ComboBox<>();	
	    pepBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8);
	    pepBox.setValue(0);
	    
	    //image for the order button
		ImageView orderButtonImage = new ImageView("OrderButton.png");
	    orderButtonImage.setFitHeight(75);
	    orderButtonImage.setFitWidth(150);
	    
	    //order button
	    Button orderButton = new Button();
	    orderButton.setGraphic(orderButtonImage);
	    
	    //labels
	    Label PizzaComing = new Label("Thank you for ordering. \nYour pizza is on the way!");	
	    Label totalPriceLabel = new Label("$0.00");
		    
	    	cheeseBox.setOnAction(e -> {
	    	double TotalPrice = (cheeseBox.getValue() * 1.50) + (veggieBox.getValue() * 2.00 + (pepBox.getValue() * 2.25));
	    	//formatter.format(TotalPrice);  TESTING ONLY
	    	String TotalPriceString = String.format("%.2f", TotalPrice);  //sets the precision to display the price as X.XX
	    	//System.out.println(TotalPrice);  TESTING ONLY
	    	totalPriceLabel.setText("$" + TotalPriceString);
	    	
	    	
	    	//System.out.println(PizzaShop.getCheeseQty(cheeseBox.getValue())); TESTING ONLY
	    	
	    	switch (cheeseBox.getValue()){
	    	case 0: break;
	    	case 1: cheeseArcLength = 45;
	    			break;
	    	case 2: cheeseArcLength = 90;
				break;
	    	case 3: cheeseArcLength = 135;
				break;
	    	case 4: cheeseArcLength = 180;
	    		break;
	    	case 5: cheeseArcLength = 225;
	    		break;
	    	case 6: cheeseArcLength = 270;
				break;
	    	case 7: cheeseArcLength = 315;
				break;
	    	case 8: cheeseArcLength = 360;
				break;
		    		
	    	}
	    	});
	    
	    	veggieBox.setOnAction(e -> {
		    	double TotalPrice = (cheeseBox.getValue() * 1.5) + (veggieBox.getValue() * 2.0 + (pepBox.getValue() * 2.25));
		    //	System.out.println(TotalPrice); TESTING ONLY
		    	String TotalPriceString = String.format("%.2f", TotalPrice);  //sets the precision to display the price as X.XX
		    	totalPriceLabel.setText("$" + TotalPriceString);
		    	
		    	//this switch sets the value of the arclength for each type of pizza.
		    	switch (veggieBox.getValue()){
		    	case 0: break;
		    	case 1: veggieArcLength = 45;
		    			break;
		    	case 2: veggieArcLength = 90;
					break;
		    	case 3: veggieArcLength = 135;
					break;
		    	case 4: veggieArcLength = 180;
		    		break;
		    	case 5: veggieArcLength = 225;
		    		break;
		    	case 6: veggieArcLength = 270;
					break;
		    	case 7: veggieArcLength = 315;
					break;
		    	case 8: veggieArcLength = 360;
					break;
		    	}
		    });
	    	
	    	pepBox.setOnAction(e -> {
		    	double TotalPrice = (cheeseBox.getValue() * 1.5) + (veggieBox.getValue() * 2.0 + (pepBox.getValue() * 2.25));
		   // 	System.out.println(TotalPrice); TESTING ONLY
		    	String TotalPriceString = String.format("%.2f", TotalPrice);  //sets the precision to display the price as X.XX
		    	totalPriceLabel.setText("$" + TotalPriceString);
		    	
		    	switch (pepBox.getValue()){
		    	case 0: break;
		    	case 1: pepArcLength = 45;
		    			break;
		    	case 2: pepArcLength = 90;
					break;
		    	case 3: pepArcLength = 135;
					break;
		    	case 4: pepArcLength = 180;
		    		break;
		    	case 5: pepArcLength = 225;
		    		break;
		    	case 6: pepArcLength = 270;
					break;
		    	case 7: pepArcLength = 315;
					break;
		    	case 8: pepArcLength = 360;
					break;
		    	}
		    });
	 
	    	
	    	
	    //places the buttons and labels for the menu
	    getChildren().add(new Label("Cheese\t\t\n $1.50/slice"));
	    getChildren().add(cheeseBox);
	    getChildren().add(new Label("Vegetable\t\n $2.00/slice"));
	    getChildren().add(veggieBox);
	    getChildren().add(new Label("Pepperoni\t\n $2.25/slice"));
	    getChildren().add(pepBox);
	    getChildren().add(new Label("Total Price: "));
	    getChildren().add(totalPriceLabel);
	       
	    
	    getChildren().add(orderButton);
	    
		
	    //calls methods necessary to create the reset button and arcs
	    //i thought it made sense to disable the combo boxes as well as the order button once pushed
	    orderButton.setOnAction(e -> {
	        orderButton.setDisable(true);
	        cheeseBox.setDisable(true);
	        veggieBox.setDisable(true);
	        pepBox.setDisable(true);
	  	    getChildren().add(PizzaComing);
	  	   
	  	    PizzaShop.addcenteroverlay(cheeseArcLength, veggieArcLength, pepArcLength);  //redraws center pane and adds arcs
	  	    PizzaShop.setnewbottom(); //adds the bottompane with reset button
	  	   
	  	      	    	      		
		    });
	}
		  
}


class CenterPane extends Pane {
	
	
	
	
	CenterPane(){
		
		setStyle("-fx-font-weight: bold; -fx-font-size: 15");
		
	
		//images used for the pizzas
		//a series of hboxes are used for the images and labels 
		
		ImageView cheeseImage = new ImageView("pizza-cheese.png");
		cheeseImage.setFitHeight(150);
		cheeseImage.setFitWidth(150);
		Label cheeseLabel = new Label("Cheese Pizza");
		
		ImageView veggieImage = new ImageView("vegetable2.jpg");
		veggieImage.setFitHeight(150);
		veggieImage.setFitWidth(150);
		Label veggieLabel = new Label("Vegetable Pizza");
		
		ImageView pepImage = new ImageView("pepperoni1.jpg");
		pepImage.setFitHeight(150);
		pepImage.setFitWidth(150);
		Label pepLabel = new Label("Pepperoni Pizza");

		
		HBox row1 = new HBox();
		row1.setPadding(new Insets(0, 500, 0, 150));
		
		HBox cheeseArcBox = new HBox();
		cheeseArcBox.setPadding(new Insets(25, 500, 0, 225));
		
		
		//purpose of the circles is to simulate a pizza pan and also used to bind the arc center to the circle center.
		//as a result, the arc pieces line up to simulate removal of a slice
		
		Circle cheeseCircle = new Circle();
		cheeseCircle.setCenterX(225);
		cheeseCircle.setCenterY(75);
		cheeseCircle.setRadius(75);
		cheeseCircle.setStroke(Color.BROWN);
		cheeseCircle.setStrokeWidth(5);
		cheeseCircle.setFill(Color.TRANSPARENT);
		
			
		row1.getChildren().add(cheeseImage);
	
		
		HBox row2 = new HBox();
		row2.setPadding(new Insets(150, 0, 500, 175));
		row2.getChildren().add(cheeseLabel);
		
		HBox row3 = new HBox();
		row3.setPadding(new Insets(200, 0, 500, 100));
		row3.setSpacing(25);
		row3.getChildren().add(pepImage);
		row3.getChildren().add(veggieImage);
		
		
		Circle PepCircle = new Circle();
		PepCircle.setCenterX(175);
		PepCircle.setCenterY(275);
		PepCircle.setRadius(69);
		PepCircle.setStroke(Color.BROWN);
		PepCircle.setStrokeWidth(5);
		PepCircle.setFill(null);
		
		Circle veggieCircle = new Circle();
		veggieCircle.setCenterX(352);
		veggieCircle.setCenterY(277);
		veggieCircle.setRadius(69);
		veggieCircle.setStroke(Color.BROWN);
		veggieCircle.setStrokeWidth(5);
		veggieCircle.setFill(null);
		
		HBox row4 = new HBox();
		row4.setPadding(new Insets(350, 0, 500, 125));
		row4.setSpacing(70);
		row4.getChildren().add(pepLabel);
		row4.getChildren().add(veggieLabel);
		
		
		//adds the hboxes that contains the images and circles
		getChildren().add(row1);
		getChildren().add(row2);
		getChildren().add(row3);
		getChildren().add(row4);
		
		getChildren().add(cheeseCircle);
		getChildren().add(PepCircle);
		getChildren().add(veggieCircle);
		
		
	}	

		//this constructor is what draws the arcs after the customer pushes the order now button.
		// purpose of this() is to call the default constructor so all the images and circles are maintained
		CenterPane(int cheeseArcLength, int veggieArcLength, int pepArcLength){
					
			this();  //calls the default constructor for the center pane
			
			Arc cheeseArc = new Arc();
			cheeseArc.setCenterX(225);
			cheeseArc.setCenterY(75);
			cheeseArc.setRadiusX(75);
			cheeseArc.setRadiusY(75);
			cheeseArc.setStartAngle(0);
			cheeseArc.setLength(cheeseArcLength);
			cheeseArc.setType(ArcType.ROUND);
			cheeseArc.setFill(Color.BROWN);
			
			Arc pepArc = new Arc();
			pepArc.setCenterX(175);
			pepArc.setCenterY(275);
			pepArc.setRadiusX(70);
			pepArc.setRadiusY(70);
			pepArc.setStartAngle(0);
			pepArc.setLength(pepArcLength);
			pepArc.setType(ArcType.ROUND);
			pepArc.setFill(Color.BROWN);
			
			Arc veggieArc = new Arc();
			veggieArc.setCenterX(352);
			veggieArc.setCenterY(277);
			veggieArc.setRadiusX(70);
			veggieArc.setRadiusY(70);
			veggieArc.setStartAngle(0);
			veggieArc.setLength(veggieArcLength);
			veggieArc.setType(ArcType.ROUND);
			veggieArc.setFill(Color.BROWN);
			

			
			getChildren().add(cheeseArc);
		
			getChildren().add(pepArc);
		
			getChildren().add(veggieArc);
		

	
		}
}






