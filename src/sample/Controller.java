package sample;

import com.sun.javafx.sg.prism.NGShape;
import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    public TextField stepsField;

    @FXML
    public ComboBox methodSelector;

    @FXML
    public LineChart mainChart;

    @FXML
    public LineChart errorChart;

    @FXML
    public LineChart maxErrorChart;

    @FXML
    public TextField x0Field;

    @FXML
    public TextField y0Field;

    @FXML
    public TextField xnField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        methodSelector.getItems().addAll(Model.euler, Model.eulerImproved, Model.rungeKutta, Model.directSolution);
    }

    @FXML
    public void plot(ActionEvent event){
        mainChart.setCreateSymbols(false);
        errorChart.setCreateSymbols(false);
        maxErrorChart.setCreateSymbols(false);
        double x0;
        double y0;
        double xFinal;
        int gridSteps;
        String method;
        try {
            x0 = Double.parseDouble(x0Field.getText());
            y0 = Double.parseDouble(y0Field.getText());
            xFinal = Double.parseDouble(xnField.getText());
            gridSteps = Integer.parseInt(stepsField.getText());
            if(gridSteps < 1)
                throw (new Exception("gridSteps can't be less than 1"));
            method = (String)methodSelector.getValue();
            if(method == null)
                throw (new Exception("method should be selected"));
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input!", ButtonType.CANCEL);
            alert.show();
            return;
        }
        double[] xAxes = new double[gridSteps];
        xAxes[0] = x0;
        for (int i = 1; i < gridSteps; i++) {
            xAxes[i] = x0 + i*(xFinal - x0)/gridSteps;
        }
        double[] yAxes = Model.getYAxes(xAxes, y0, method);
        XYChart.Series series = new XYChart.Series();
        XYChart.Series errorSeries = new XYChart.Series();
        XYChart.Series maxErrorSeries = new XYChart.Series();
        maxErrorSeries.setName(method);
        errorSeries.setName(method);
        series.setName(method);
        double[] originalYAxes = Model.getDSAxes(xAxes, y0);
        for (int i = 0; i < xAxes.length; i++) {
            series.getData().add(new XYChart.Data(xAxes[i], yAxes[i]));
            errorSeries.getData().add(new XYChart.Data(xAxes[i], Math.abs(originalYAxes[i] - yAxes[i])));
        }
        for (int i = 1; i < gridSteps; i++) {
            xAxes = new double[i];
            xAxes[0] = x0;
            for (int j = 1; j < i; j++) {
                xAxes[j] = x0 + j*(xFinal - x0)/i;
            }
            yAxes = Model.getYAxes(xAxes, y0, method);
            originalYAxes = Model.getDSAxes(xAxes, y0);
            double maxError = Math.abs(originalYAxes[0] - yAxes[0]);
            for (int j = 1; j < i; j++) {
                double error = Math.abs(originalYAxes[j] - yAxes[j]);
                if(error>maxError)
                    maxError = error;
            }
            maxErrorSeries.getData().add(new XYChart.Data(i, maxError));
        }
        mainChart.getData().addAll(series);
        errorChart.getData().addAll(errorSeries);
        maxErrorChart.getData().addAll(maxErrorSeries);
    }

    @FXML
    public void clear(ActionEvent event){
        mainChart.getData().clear();
        errorChart.getData().clear();
        maxErrorChart.getData().clear();
    }
}
