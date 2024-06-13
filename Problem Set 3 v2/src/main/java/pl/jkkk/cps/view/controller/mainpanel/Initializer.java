package pl.jkkk.cps.view.controller.mainpanel;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import pl.jkkk.cps.logic.model.enumtype.OneArgsOperationType;
import pl.jkkk.cps.logic.model.enumtype.SignalType;
import pl.jkkk.cps.logic.model.enumtype.TwoArgsOperationType;
import pl.jkkk.cps.logic.model.enumtype.WindowType;
import pl.jkkk.cps.view.model.CustomTab;
import pl.jkkk.cps.view.model.CustomTabPane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.jkkk.cps.view.fxml.FxHelper.fillComboBox;
import static pl.jkkk.cps.view.fxml.FxHelper.getTabNameList;
import static pl.jkkk.cps.view.fxml.FxHelper.getValueFromComboBox;
import static pl.jkkk.cps.view.fxml.FxHelper.prepareLabelWithPosition;
import static pl.jkkk.cps.view.fxml.FxHelper.prepareLineChart;
import static pl.jkkk.cps.view.fxml.FxHelper.removeAndAddNewPaneChildren;
import static pl.jkkk.cps.view.fxml.FxHelper.setTextFieldPosition;
import static pl.jkkk.cps.view.fxml.FxHelper.textFieldSetValue;

public class Initializer {

    /*------------------------ FIELDS REGION ------------------------*/
    private ComboBox comboBoxSignalTypes;
    private ComboBox comboBoxOperationTypesTwoArgs;
    private ComboBox comboBoxFirstSignalTwoArgs;
    private ComboBox comboBoxSecondSignalTwoArgs;
    private Pane chooseParamsTab;

    private TextField textFieldAmplitude;
    private TextField textFieldStartTime;
    private TextField textFieldSignalDuration;
    private TextField textFieldBasicPeriod;
    private TextField textFieldFillFactor;
    private TextField textFieldJumpTime;
    private TextField textFieldProbability;
    private TextField textFieldSamplingFrequency;

    private TabPane tabPaneResults;

    private ComboBox comboBoxOperationTypesOneArgs;
    private ComboBox comboBoxSignalOneArgs;
    private ComboBox comboBoxComparisonFirstSignal;
    private ComboBox comboBoxComparisonSecondSignal;
    private AnchorPane comparisonPane;
    private AnchorPane oneArgsPane;
    private TextField textFieldQuantizationLevels;
    private TextField textFieldSampleRate;
    private TextField textFieldReconstructionSincParam;
    private AnchorPane windowTypePane;
    private TextField textFieldCuttingFrequency;
    private TextField textFieldFilterRow;

    /*------------------------ METHODS REGION ------------------------*/
    public Initializer(ComboBox comboBoxSignalTypes, ComboBox comboBoxOperationTypesTwoArgs,
                       ComboBox comboBoxFirstSignalTwoArgs, ComboBox comboBoxSecondSignalTwoArgs,
                       Pane chooseParamsTab, TextField textFieldAmplitude,
                       TextField textFieldStartTime, TextField textFieldSignalDuration,
                       TextField textFieldBasicPeriod, TextField textFieldFillFactor,
                       TextField textFieldJumpTime, TextField textFieldProbability,
                       TextField textFieldSamplingFrequency, TabPane tabPaneResults,
                       ComboBox comboBoxOperationTypesOneArgs, ComboBox comboBoxSignalOneArgs,
                       AnchorPane oneArgsPane, TextField textFieldQuantizationLevels,
                       TextField textFieldSampleRate, TextField textFieldReconstructionSincParam,
                       AnchorPane windowTypePane, TextField textFieldCuttingFrequency,
                       TextField textFieldFilterRow) {
        this.comboBoxSignalTypes = comboBoxSignalTypes;
        this.comboBoxOperationTypesTwoArgs = comboBoxOperationTypesTwoArgs;
        this.comboBoxFirstSignalTwoArgs = comboBoxFirstSignalTwoArgs;
        this.comboBoxSecondSignalTwoArgs = comboBoxSecondSignalTwoArgs;
        this.chooseParamsTab = chooseParamsTab;
        this.textFieldAmplitude = textFieldAmplitude;
        this.textFieldStartTime = textFieldStartTime;
        this.textFieldSignalDuration = textFieldSignalDuration;
        this.textFieldBasicPeriod = textFieldBasicPeriod;
        this.textFieldFillFactor = textFieldFillFactor;
        this.textFieldJumpTime = textFieldJumpTime;
        this.textFieldProbability = textFieldProbability;
        this.textFieldSamplingFrequency = textFieldSamplingFrequency;
        this.tabPaneResults = tabPaneResults;
        this.comboBoxOperationTypesOneArgs = comboBoxOperationTypesOneArgs;
        this.comboBoxSignalOneArgs = comboBoxSignalOneArgs;
        this.oneArgsPane = oneArgsPane;
        this.textFieldQuantizationLevels = textFieldQuantizationLevels;
        this.textFieldSampleRate = textFieldSampleRate;
        this.textFieldReconstructionSincParam = textFieldReconstructionSincParam;
        this.windowTypePane = windowTypePane;
        this.textFieldCuttingFrequency = textFieldCuttingFrequency;
        this.textFieldFilterRow = textFieldFilterRow;
    }

    /*--------------------------------------------------------------------------------------------*/
    public void prepareTabPaneInputs() {
        fillGenerationTab();
        fillOneArgsTab();
        fillTwoArgsTab();
    }

    public void prepareTabPaneResults(int index) {
        Pane pane = new Pane(
                prepareLabelWithPosition("Wartość średnia sygnału: ", 25, 40),
                prepareLabelWithPosition("Wartość średnia bezwzględna sygnału: ", 25, 80),
                prepareLabelWithPosition("Wartość skuteczna sygnału: ", 25, 120),
                prepareLabelWithPosition("Wariancja sygnału: ", 25, 160),
                prepareLabelWithPosition("Moc średnia sygnału: ", 25, 200)
        );

        tabPaneResults.getTabs().add(new Tab("Karta " + index,
                new CustomTabPane(
                        new CustomTab("Wykres", prepareLineChart(), false),
                        new CustomTab("Parametry", pane, false)
                )));
    }

    /*--------------------------------------------------------------------------------------------*/
    private void fillGenerationTab() {
        textFieldSetValue(textFieldAmplitude, String.valueOf(1));
        textFieldSetValue(textFieldStartTime, String.valueOf(0));
        textFieldSetValue(textFieldSignalDuration, String.valueOf(5));
        textFieldSetValue(textFieldBasicPeriod, String.valueOf(1));
        textFieldSetValue(textFieldFillFactor, String.valueOf(0.5));
        textFieldSetValue(textFieldJumpTime, String.valueOf(2));
        textFieldSetValue(textFieldProbability, String.valueOf(0.5));
        textFieldSetValue(textFieldSamplingFrequency, String.valueOf(16));
        textFieldSetValue(textFieldCuttingFrequency, String.valueOf(4));
        textFieldSetValue(textFieldFilterRow, String.valueOf(15));

        fillComboBox(comboBoxSignalTypes, SignalType.getNamesList());

        List<Node> basicInputs = Stream.of(
                prepareLabelWithPosition("Wybierz Parametry", 168, 14),
                prepareLabelWithPosition("Amplituda", 50, 50),
                prepareLabelWithPosition("Czas początkowy", 50, 90),
                prepareLabelWithPosition("Czas trwania sygnału", 50, 130),
                setTextFieldPosition(textFieldAmplitude, 270, 50),
                setTextFieldPosition(textFieldStartTime, 270, 90),
                setTextFieldPosition(textFieldSignalDuration, 270, 130)
        ).collect(Collectors.toCollection(ArrayList::new));

        chooseParamsTab.getChildren().setAll(basicInputs);
        windowTypePane.setVisible(false);

        comboBoxSignalTypes.setOnAction((event -> {
            String selectedSignal = getValueFromComboBox(comboBoxSignalTypes);
            windowTypePane.setVisible(false);

            if (selectedSignal.equals(SignalType.SINUSOIDAL_SIGNAL.getName())
                    || selectedSignal.equals(SignalType.SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL.getName())
                    || selectedSignal.equals(SignalType.SINUSOIDAL_RECTIFIED_IN_TWO_HALVES.getName())) {

                chooseParamsTab.getChildren().setAll(basicInputs);
                chooseParamsTab.getChildren().addAll(
                        prepareLabelWithPosition("Okres podstawowy", 50, 170),
                        setTextFieldPosition(textFieldBasicPeriod, 270, 170)
                );
            } else if (selectedSignal.equals(SignalType.RECTANGULAR_SIGNAL.getName())
                    || selectedSignal.equals(SignalType.SYMMETRICAL_RECTANGULAR_SIGNAL.getName())
                    || selectedSignal.equals(SignalType.TRIANGULAR_SIGNAL.getName())) {

                chooseParamsTab.getChildren().setAll(basicInputs);
                chooseParamsTab.getChildren().addAll(
                        prepareLabelWithPosition("Okres podstawowy", 50, 170),
                        setTextFieldPosition(textFieldBasicPeriod, 270, 170),
                        prepareLabelWithPosition("Wspł wypełnienia", 50, 210),
                        setTextFieldPosition(textFieldFillFactor, 270, 210)
                );
            } else if (selectedSignal.equals(SignalType.LOW_PASS_FILTER.getName())
                    || selectedSignal.equals(SignalType.BAND_PASS_FILTER.getName())
                    || selectedSignal.equals(SignalType.HIGH_PASS_FILTER.getName())) {

                chooseParamsTab.getChildren().setAll(
                        prepareLabelWithPosition("Wybierz Parametry", 168, 14),
                        prepareLabelWithPosition("Częstotliwość próbkowania", 50, 50),
                        prepareLabelWithPosition("Rząd filtra", 50, 90),
                        prepareLabelWithPosition("Częstotliwość odcięcia", 50, 130),
                        setTextFieldPosition(textFieldSamplingFrequency, 270, 50),
                        setTextFieldPosition(textFieldFilterRow, 270, 90),
                        setTextFieldPosition(textFieldCuttingFrequency, 270, 130)
                );

                ComboBox comboBoxWindowType = (ComboBox) windowTypePane.getChildren().get(1);
                fillComboBox(comboBoxWindowType, WindowType.getNamesList());
                windowTypePane.setVisible(true);
            }
        }));
    }

    /*--------------------------------------------------------------------------------------------*/
    private void actionComboBoxOperationTypesOneArgs() {
        Pane topPane = (Pane) oneArgsPane.getChildren().get(0);
        ComboBox comboBoxMethod = (ComboBox) topPane.getChildren().get(1);
        Pane bottomPane = (Pane) oneArgsPane.getChildren().get(1);

        String selectedOperation = getValueFromComboBox(comboBoxOperationTypesOneArgs);
        topPane.setVisible(false);

        if (selectedOperation.equals(OneArgsOperationType.SAMPLING.getName())) {
            bottomPane.setVisible(true);

            removeAndAddNewPaneChildren(bottomPane,
                    prepareLabelWithPosition("Częstotliwość próbkowania", 14, 33),
                    setTextFieldPosition(textFieldSampleRate, 250, 30)
            );

        }

    }

    private void fillOneArgsTab() {
        fillComboBox(comboBoxOperationTypesOneArgs, OneArgsOperationType.getNamesList());
        textFieldSetValue(textFieldQuantizationLevels, String.valueOf(10));
        textFieldSetValue(textFieldSampleRate, String.valueOf(10));
        textFieldSetValue(textFieldReconstructionSincParam, String.valueOf(1));
        fillComboBox(comboBoxSignalOneArgs, getTabNameList(tabPaneResults.getTabs()));

        Pane topPane = (Pane) oneArgsPane.getChildren().get(0);
        Pane bottomPane = (Pane) oneArgsPane.getChildren().get(1);
        topPane.setVisible(false);
        removeAndAddNewPaneChildren(bottomPane,
                prepareLabelWithPosition("Częstotliwość próbkowania", 14, 33),
                setTextFieldPosition(textFieldSampleRate, 250, 30)
        );

        comboBoxOperationTypesOneArgs.setOnAction((event -> {
            actionComboBoxOperationTypesOneArgs();
        }));
    }

    /*--------------------------------------------------------------------------------------------*/
    private void fillTwoArgsTab() {
        fillComboBox(comboBoxOperationTypesTwoArgs, TwoArgsOperationType.getNamesList());
        fillComboBox(comboBoxFirstSignalTwoArgs, getTabNameList(tabPaneResults.getTabs()));
        fillComboBox(comboBoxSecondSignalTwoArgs, getTabNameList(tabPaneResults.getTabs()));
    }

    /*--------------------------------------------------------------------------------------------*/
}