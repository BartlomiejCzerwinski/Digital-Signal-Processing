package pl.bcpr.cps.view.controller.animationpanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import pl.bcpr.cps.logic.model.simulator.DistanceSensor;
import pl.bcpr.cps.logic.model.simulator.Environment;
import pl.bcpr.cps.view.constant.Constants;
import pl.bcpr.cps.view.fxml.FxHelper;
import pl.bcpr.cps.view.exception.AnimationNotStartedException;
import pl.bcpr.cps.view.fxml.PopOutWindow;
import pl.bcpr.cps.view.fxml.StageController;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static pl.bcpr.cps.view.fxml.FxHelper.prepareLineChart;

public class AnimationPanel implements Initializable {

    /*------------------------ FIELDS REGION ------------------------*/
    @FXML
    private HBox paneAnimationPanel;

    @FXML
    private TextField textFieldTimeStep;
    @FXML
    private TextField textFieldSignalVelocity;
    @FXML
    private TextField textFieldItemVelocity;
    @FXML
    private TextField textFieldStartItemDistance;
    @FXML
    private TextField textFieldProbeSignalTerm;
    @FXML
    private TextField textFieldSampleRate;
    @FXML
    private TextField textFieldBufferLength;
    @FXML
    private TextField textFieldReportTerm;

    @FXML
    private TextField textFieldResultTimeStamp;
    @FXML
    private TextField textFieldResultRealDistance;
    @FXML
    private TextField textFieldResultCalculatedDistance;

    @FXML
    private LineChart lineChartSignalProbe;
    @FXML
    private NumberAxis axisXSignalProbe;
    @FXML
    private LineChart lineChartSignalFeedback;
    @FXML
    private NumberAxis axisXSignalFeedback;
    @FXML
    private LineChart lineChartSignalCorrelation;
    @FXML
    private NumberAxis axisXSignalCorrelation;

    private AnimationThread animationThread = new AnimationThread();

    /*------------------------ METHODS REGION ------------------------*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FxHelper.textFieldSetValue(textFieldTimeStep, String.valueOf(0.1));
        FxHelper.textFieldSetValue(textFieldSignalVelocity, String.valueOf(100));
        FxHelper.textFieldSetValue(textFieldItemVelocity, String.valueOf(0.5));
        FxHelper.textFieldSetValue(textFieldStartItemDistance, String.valueOf(25));
        FxHelper.textFieldSetValue(textFieldProbeSignalTerm, String.valueOf(1));
        FxHelper.textFieldSetValue(textFieldSampleRate, String.valueOf(20));
        FxHelper.textFieldSetValue(textFieldBufferLength, String.valueOf(60));
        FxHelper.textFieldSetValue(textFieldReportTerm, String.valueOf(0.5));

        FxHelper.prepareLineChart(lineChartSignalProbe);
        FxHelper.prepareLineChart(lineChartSignalFeedback);
        FxHelper.prepareLineChart(lineChartSignalCorrelation);
    }

    @FXML
    private void onActionButtonReturn(ActionEvent actionEvent) {
        if (!animationThread.getIsAnimationRunning().get()) {
            StageController.reloadStage(Constants.PATH_MAIN_PANEL, Constants.TITLE_MAIN_PANEL);
        } else {
            try {
                animationThread.stopAnimation();
                //TimeUnit.MILLISECONDS.sleep(200);
                StageController.reloadStage(Constants.PATH_MAIN_PANEL, Constants.TITLE_MAIN_PANEL);
            } catch (AnimationNotStartedException e) {
                PopOutWindow.messageBox("Błąd Zatrzymania",
                        "Animacja nie została rozpoczęta",
                        Alert.AlertType.WARNING);
            }
        }
        changeParamsTextFieldsEditable(true);
        StageController.getApplicationStage().setResizable(false);
    }

    @FXML
    private void onActionButtonStartAnimation(ActionEvent actionEvent) {
        changeParamsTextFieldsEditable(false);

        try {
            Double timeStep = FxHelper.getTextFieldValueToDouble(textFieldTimeStep);
            Double signalVelocity = FxHelper.getTextFieldValueToDouble(textFieldSignalVelocity);
            Double itemVelocity = FxHelper.getTextFieldValueToDouble(textFieldItemVelocity);
            Double startItemDistance = FxHelper.getTextFieldValueToDouble(textFieldStartItemDistance);
            Double probeSignalTerm = FxHelper.getTextFieldValueToDouble(textFieldProbeSignalTerm);
            Double sampleRate = FxHelper.getTextFieldValueToDouble(textFieldSampleRate);
            Integer bufferLength = FxHelper.getTextFieldValueToInteger(textFieldBufferLength);
            Double reportTerm = FxHelper.getTextFieldValueToDouble(textFieldReportTerm);

            DistanceSensor distanceSensor = new DistanceSensor(probeSignalTerm,
                    sampleRate, bufferLength, reportTerm, signalVelocity);
            Environment environment = new Environment(timeStep, signalVelocity,
                    itemVelocity, distanceSensor, startItemDistance);

            animationThread.startAnimation(environment, lineChartSignalProbe,
                    lineChartSignalFeedback, lineChartSignalCorrelation,
                    axisXSignalProbe, axisXSignalFeedback, axisXSignalCorrelation,
                    textFieldResultTimeStamp, textFieldResultRealDistance,
                    textFieldResultCalculatedDistance);
        } catch (NumberFormatException e) {
            PopOutWindow.messageBox("Błędne Dane",
                    "Wprowadzono błędne dane",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void onActionButtonPauseAnimation(ActionEvent actionEvent) {
        if (animationThread.getIsAnimationRunning().get()) {
            if (!animationThread.getIsAnimationPaused().get()) {
                animationThread.pauseAnimation();
            } else {
                animationThread.repauseAnimation();
            }
        } else {
            PopOutWindow.messageBox("",
                    "Animacja nie została rozpoczęta",
                    Alert.AlertType.WARNING);
        }
    }



    private void changeParamsTextFieldsEditable(boolean value) {
        FxHelper.textFieldSetEditable(value,
                textFieldTimeStep, textFieldSignalVelocity,
                textFieldItemVelocity, textFieldStartItemDistance,
                textFieldProbeSignalTerm, textFieldSampleRate,
                textFieldBufferLength, textFieldReportTerm);
    }
}
    