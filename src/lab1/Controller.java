package lab1;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.*;


public class Controller {

    @FXML private Text Scale;
    @FXML private Text Start;
    @FXML private Text Finish;
    @FXML private Text Step;


    @FXML private TableView<Dot> table;


    @FXML private TableColumn<Dot, Double> x;
    @FXML private TableColumn<Dot, Double> y;


    @FXML private TextField hField;
    @FXML private TextField xField;
    @FXML private TextField yField;
    @FXML private TextField sField;

    @FXML private Pane pane;
    @FXML private Canvas canvas;


    private static final String LANGUAGE_EN = "en";
    private static final String LANGUAGE_RU = "ru";
    private static final String LANGUAGE_UA = "ua";


    private static final String KEY_START = "Start";
    private static final String KEY_FINISH = "Finish";
    private static final String KEY_STEP = "Step";
    private static final String KEY_SCALE = "Scale";

    private Locale locale = new Locale(LANGUAGE_RU);
    private static final String  baseName = "lab1.MyBundle";
    private ResourceBundle rb = ResourceBundle.getBundle(baseName, locale);


    @FXML
    public void initialize() {
        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());

        canvas.heightProperty().addListener((observable, oldValue, newValue) -> draw());
        canvas.widthProperty().addListener((observable, oldValue, newValue) -> draw());

        Start.setText(rb.getString(KEY_START)); //локаль
        Finish.setText(rb.getString(KEY_FINISH)); //локаль
        Step.setText(rb.getString(KEY_STEP)); //локаль
        Scale.setText(rb.getString(KEY_SCALE)); //локаль


        x.setCellValueFactory(new PropertyValueFactory<>("y"));
        y.setCellValueFactory(new PropertyValueFactory<>("x"));
    }

    public void draw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.rgb(255, 255, 255));
      //  g.setFill(Color.rgb(255, 0, 11));

        double height = canvas.getHeight();
        double width = canvas.getWidth();

        g.fillRect(0,0, width, height);
        g.strokeLine(0, canvas.getHeight()/2, canvas.getWidth(), canvas.getHeight()/2 );
        g.strokeLine(canvas.getWidth()/2, 0, canvas.getWidth()/2, canvas.getHeight() );

     //   DoubleUnaryOperator fun = (x) -> cos( 4*x ) * cos( 2*x ); //Тут можно ввести любую функцию
      //   DoubleUnaryOperator fun = (x) ->    (1.3 + 0.5*x*x*x*x) / (log(1.14 - sqrt(0.34 - x*x))); //Тут можно ввести любую функцию
      //  DoubleUnaryOperator fun = (x) ->    cos(x*x-1) / sqrt(x); //Тут можно ввести любую функцию
        DoubleUnaryOperator fun = (x) ->    tan(x*x) / (x*x +1); //Тут можно ввести любую функцию
     //  DoubleUnaryOperator fun = (x) ->    (0.1-0.3*x*x*x)/(5-sqrt(0.25+x*x*x*x)); //Тут можно ввести любую функцию


        if (xField.getText()!=null && yField.getText()!=null) {
            try {
                double start = Double.parseDouble(xField.getText());
                double finish = Double.parseDouble(yField.getText());
                double h = Double.parseDouble(hField.getText());
                int scale = Integer.parseInt(sField.getText());
                DrawFunc(start, finish, h, scale,  fun);

            } catch (NumberFormatException ignored) {}
        }

    }

    private void DrawFunc(double start, double finish, double h, int scale, DoubleUnaryOperator fun) {
        Func func = new Func();
        if(finish < start){
            double temp = start; start = finish; finish = temp;
        }
        double[][] arr = func.calcFunc(start, finish, h, fun);
        ArrayList<Dot> dots = new ArrayList<>();

        for (int i = 0; i < arr[0].length; i++) {
            if(!Double.isNaN(arr[0][i]));
                dots.add(new Dot(arr[0][i], arr[1][i]));
        }

        table.setItems(FXCollections.observableArrayList(dots));



        for (int i = 0; i < arr[0].length-1; i++) {
            GraphicsContext g1 = canvas.getGraphicsContext2D();
            g1.setFill(Color.rgb(0, 0, 0));
            g1.translate(canvas.getWidth()/2, canvas.getHeight()/2);
            g1.strokeLine(arr[1][i] * scale, -arr[0][i] * scale, arr[1][i + 1] * scale, -arr[0][i + 1] * scale); //человеческая система координат
            g1.translate(-canvas.getWidth()/2, -canvas.getHeight()/2);
        }
    }

    public void setRu(ActionEvent actionEvent) {
        locale = new Locale(LANGUAGE_RU);
        rb =  ResourceBundle.getBundle(baseName, locale);
        initialize();
    }
    public void setEn(ActionEvent actionEvent) {
        locale = new Locale(LANGUAGE_EN);
        rb =  ResourceBundle.getBundle(baseName, locale);
        initialize();
    }
    public void setUa(ActionEvent actionEvent) {
        locale = new Locale(LANGUAGE_UA);
        rb =  ResourceBundle.getBundle(baseName, locale);
        initialize();
    }

}
