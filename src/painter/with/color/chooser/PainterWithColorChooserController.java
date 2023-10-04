/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package painter.with.color.chooser;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author OscarFabianHP
 */
public class PainterWithColorChooserController implements Initializable {

    
    private enum PenSize {
        SMALL(2),
        MEDIUM(4),
        LARGE(6);
        
        private final int size;

        private PenSize(int size) {
            this.size=size;
        }

        public int getSize() {
            return size;
        }
    }
    /**
     * Initializes the controller class.
     */
    @FXML private Slider redSlider;
    @FXML private Slider greenSlider;
    @FXML private Slider blueSlider;
    @FXML private Slider alphaSlider;
    
    @FXML private TextField redTextField;
    @FXML private TextField greenTextField;
    @FXML private TextField blueTextField;
    @FXML private TextField alphaTextField;
    
    @FXML private Rectangle rectangleColor;
    
    @FXML private RadioButton smallRadioButton;
    @FXML private RadioButton mediumRadioButton;
    @FXML private RadioButton largeRadioButton;
    
    @FXML private ToggleGroup sizeToggleGroup;
    
    @FXML private AnchorPane drawingAnchorPane;
    
    private int red=0;
    private int green=0;
    private int blue=0;
    private double alpha=1.0;
    
    private Color brushColor = Color.BLACK; //color predeterminado de circulo 
    private PenSize penSize = PenSize.MEDIUM; //radius del circulo
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //inicializa los RadioButton
        smallRadioButton.setUserData(PenSize.SMALL);
        mediumRadioButton.setUserData(PenSize.MEDIUM);
        largeRadioButton.setUserData(PenSize.LARGE);
        
        //creo los bind del campo de texto con el slider correspondiente
        redTextField.textProperty().bind(redSlider.valueProperty().asString("%.0f"));
        greenTextField.textProperty().bind(greenSlider.valueProperty().asString("%.0f"));
        blueTextField.textProperty().bind(blueSlider.valueProperty().asString("%.0f"));
        alphaTextField.textProperty().bind(alphaSlider.valueProperty().asString("%.2f"));

        //creo los Listener para cada Slider
        redSlider.valueProperty().addListener(
        (observableValue, oldValue, newValue) -> {
                red = newValue.intValue();
                rectangleColor.setFill(Color.rgb(red, green, blue, alpha));
                brushColor = Color.rgb(red, green, blue, alpha);

                        });
        
        greenSlider.valueProperty().addListener(
        (observableValue, oldValue, newValue) ->
        {
            green = newValue.intValue();
            rectangleColor.setFill(Color.rgb(red, green, blue, alpha));
            brushColor = Color.rgb(red, green, blue, alpha);

        });
        
        blueSlider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                blue = newValue.intValue();
                rectangleColor.setFill(Color.rgb(red, green, blue, alpha));
                brushColor = Color.rgb(red, green, blue, alpha);
            }
        });
        
        alphaSlider.valueProperty().addListener(
        (observableValue, oldValue, newValue) ->
        {
            alpha = newValue.doubleValue();
            rectangleColor.setFill(Color.rgb(red, green, blue, alpha));
            brushColor = Color.rgb(red, green, blue, alpha);

        });
      
        //manejador para radiobutton pensize
        sizeToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) {
                penSize = (PenSize) newToggle.getUserData();
            }
            
        });  
        
    }   
    
    @FXML
    private void drawingAreaMouseDragged(MouseEvent event){
        Circle newCircle = new Circle(event.getX(), event.getY(), penSize.getSize(), brushColor);
        drawingAnchorPane.getChildren().add(newCircle);
    }
    
    @FXML
    private void undoButtonPressed(ActionEvent event){
        
        int count = drawingAnchorPane.getChildren().size(); //obtiene formas dibujadas (nodos) en el panel de dibujo
        
        if(count>0) //si existen elementos dibujados en el panel de dibujo
            drawingAnchorPane.getChildren().remove(count-1); //remueve el ultimo elemento dibujado
    }
    
    @FXML
    private void clearButtonPressed(ActionEvent event){
        drawingAnchorPane.getChildren().clear(); //limpia todos las formas dibujadas
    }
    
//    @FXML //metodo para poner en onAction al seleccionar los RadioButton
//    private void sizeRadioButonSelected(ActionEvent event){
//        penSize = (PenSize) sizeToggleGroup.getSelectedToggle().getUserData();
//    }
    
}
