package pl.bcpr.cps.view.controller.mainpanel;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pl.bcpr.cps.view.fxml.FxHelper;
import pl.bcpr.cps.view.model.CustomTab;
import pl.bcpr.cps.view.model.CustomTabPane;
import pl.bcpr.cps.logic.model.enumtype.AlgorithmType;
import pl.bcpr.cps.logic.model.enumtype.OneArgsOperationType;
import pl.bcpr.cps.logic.model.enumtype.SignalType;
import pl.bcpr.cps.logic.model.enumtype.TwoArgsOperationType;
import pl.bcpr.cps.logic.model.enumtype.WaveletType;
import pl.bcpr.cps.logic.model.enumtype.WindowType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.bcpr.cps.view.fxml.FxHelper.prepareLineChart;

public class Initializer {

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
    private AnchorPane oneArgsPane;
    private TextField textFieldQuantizationLevels;
    private TextField textFieldSampleRate;
    private TextField textFieldReconstructionSincParam;
    private AnchorPane windowTypePane;
    private TextField textFieldCuttingFrequency;
    private TextField textFieldFilterRow;

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

    public void prepareTabPaneInputs() {
        fillGenerationTab();
        fillOneArgsTab();
        fillTwoArgsTab();
    }

    public void prepareTabPaneResults(int index) {
        Pane pane = new Pane(
                FxHelper.prepareLabelWithPosition("Wartość średnia sygnału: ", 25, 40),
                FxHelper.prepareLabelWithPosition("Wartość średnia bezwzględna sygnału: ", 25, 80),
                FxHelper.prepareLabelWithPosition("Wartość skuteczna sygnału: ", 25, 120),
                FxHelper.prepareLabelWithPosition("Wariancja sygnału: ", 25, 160),
                FxHelper.prepareLabelWithPosition("Moc średnia sygnału: ", 25, 200)
        );

        tabPaneResults.getTabs().add(new Tab("Karta " + index,
                new CustomTabPane(
                        new CustomTab("Wykres", FxHelper.prepareLineChart(), false),
                        new CustomTab("Parametry", pane, false),
                        new CustomTab("W1", new VBox(
                                FxHelper.prepareLineChart(
                                        "Część rzeczywista amplitudy w funkcji częstotliwości"),
                                FxHelper.prepareLineChart(
                                        "Część urojona amplitudy w funkcji częstotliwości")),
                                false),
                        new CustomTab("W2", new VBox(
                                FxHelper.prepareLineChart("Moduł liczby zespolonej"),
                                FxHelper.prepareLineChart("Argument liczby w funkcji częstotliwości")),
                                false)
                )));
    }

    private void fillGenerationTab() {
        FxHelper.textFieldSetValue(textFieldAmplitude, String.valueOf(1));
        FxHelper.textFieldSetValue(textFieldStartTime, String.valueOf(0));
        FxHelper.textFieldSetValue(textFieldSignalDuration, String.valueOf(5));
        FxHelper.textFieldSetValue(textFieldBasicPeriod, String.valueOf(1));
        FxHelper.textFieldSetValue(textFieldFillFactor, String.valueOf(0.5));
        FxHelper.textFieldSetValue(textFieldJumpTime, String.valueOf(2));
        FxHelper.textFieldSetValue(textFieldProbability, String.valueOf(0.5));
        FxHelper.textFieldSetValue(textFieldSamplingFrequency, String.valueOf(16));
        FxHelper.textFieldSetValue(textFieldCuttingFrequency, String.valueOf(4));
        FxHelper.textFieldSetValue(textFieldFilterRow, String.valueOf(15));

        FxHelper.fillComboBox(comboBoxSignalTypes, SignalType.getNamesList());

        List<Node> basicInputs = Stream.of(
                FxHelper.prepareLabelWithPosition("Wybierz Parametry", 168, 14),
                FxHelper.prepareLabelWithPosition("Amplituda", 50, 50),
                FxHelper.prepareLabelWithPosition("Czas początkowy", 50, 90),
                FxHelper.prepareLabelWithPosition("Czas trwania sygnału", 50, 130),
                FxHelper.setTextFieldPosition(textFieldAmplitude, 270, 50),
                FxHelper.setTextFieldPosition(textFieldStartTime, 270, 90),
                FxHelper.setTextFieldPosition(textFieldSignalDuration, 270, 130)
        ).collect(Collectors.toCollection(ArrayList::new));

        chooseParamsTab.getChildren().setAll(basicInputs);
        FxHelper.setPaneVisibility(false, windowTypePane);

        comboBoxSignalTypes.setOnAction((event -> {
            String selectedSignal = FxHelper.getValueFromComboBox(comboBoxSignalTypes);
            FxHelper.setPaneVisibility(false, windowTypePane);

            if (selectedSignal.equals(SignalType.SINUSOIDAL_SIGNAL.getName())
                    || selectedSignal.equals(SignalType.SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL.getName())
                    || selectedSignal.equals(SignalType.SINUSOIDAL_RECTIFIED_IN_TWO_HALVES.getName())) {

                chooseParamsTab.getChildren().setAll(basicInputs);
                chooseParamsTab.getChildren().addAll(
                        FxHelper.prepareLabelWithPosition("Okres podstawowy", 50, 170),
                        FxHelper.setTextFieldPosition(textFieldBasicPeriod, 270, 170)
                );
            } else if (selectedSignal.equals(SignalType.RECTANGULAR_SIGNAL.getName())
                    || selectedSignal.equals(SignalType.SYMMETRICAL_RECTANGULAR_SIGNAL.getName())
                    || selectedSignal.equals(SignalType.TRIANGULAR_SIGNAL.getName())) {

                chooseParamsTab.getChildren().setAll(basicInputs);
                chooseParamsTab.getChildren().addAll(
                        FxHelper.prepareLabelWithPosition("Okres podstawowy", 50, 170),
                        FxHelper.setTextFieldPosition(textFieldBasicPeriod, 270, 170),
                        FxHelper.prepareLabelWithPosition("Wspł wypełnienia", 50, 210),
                        FxHelper.setTextFieldPosition(textFieldFillFactor, 270, 210)
                );
            } else if (selectedSignal.equals(SignalType.LOW_PASS_FILTER.getName())
                    || selectedSignal.equals(SignalType.BAND_PASS_FILTER.getName())
                    || selectedSignal.equals(SignalType.HIGH_PASS_FILTER.getName())) {

                chooseParamsTab.getChildren().setAll(
                        FxHelper.prepareLabelWithPosition("Wybierz Parametry", 168, 14),
                        FxHelper.prepareLabelWithPosition("Częstotliwość próbkowania", 50, 50),
                        FxHelper.prepareLabelWithPosition("Rząd filtra", 50, 90),
                        FxHelper.prepareLabelWithPosition("Częstotliwość odcięcia", 50, 130),
                        FxHelper.setTextFieldPosition(textFieldSamplingFrequency, 270, 50),
                        FxHelper.setTextFieldPosition(textFieldFilterRow, 270, 90),
                        FxHelper.setTextFieldPosition(textFieldCuttingFrequency, 270, 130)
                );

                ComboBox comboBoxWindowType = (ComboBox) windowTypePane.getChildren().get(1);
                FxHelper.fillComboBox(comboBoxWindowType, WindowType.getNamesList());
                FxHelper.setPaneVisibility(true, windowTypePane);
            }
        }));
    }

    private void fillOneArgsTab() {
        FxHelper.fillComboBox(comboBoxOperationTypesOneArgs, OneArgsOperationType.getNamesList());
        FxHelper.textFieldSetValue(textFieldQuantizationLevels, String.valueOf(10));
        FxHelper.textFieldSetValue(textFieldSampleRate, String.valueOf(10));
        FxHelper.textFieldSetValue(textFieldReconstructionSincParam, String.valueOf(1));
        FxHelper.fillComboBox(comboBoxSignalOneArgs, FxHelper.getTabNameList(tabPaneResults.getTabs()));

        final Pane topPane = (Pane) oneArgsPane.getChildren().get(0);
        final Pane middlePane = (Pane) oneArgsPane.getChildren().get(1);
        final Pane bottomPane = (Pane) oneArgsPane.getChildren().get(2);

        FxHelper.setPaneVisibility(false, topPane, middlePane);
        FxHelper.removeAndAddNewPaneChildren(bottomPane,
                FxHelper.prepareLabelWithPosition("Częstotliwość próbkowania", 14, 33),
                FxHelper.setTextFieldPosition(textFieldSampleRate, 250, 30)
        );

        comboBoxOperationTypesOneArgs.setOnAction((event ->
                actionComboBoxOperationTypesOneArgs(topPane, middlePane, bottomPane)));
    }

    private void actionComboBoxOperationTypesOneArgs(Pane topPane,
                                                     Pane middlePane, Pane bottomPane) {
        final String methodLabelValue = "Wybierz Metodę";
        final String algorithmLabelValue = "Wybierz Typ Algorytmu";
        final String algorithmLevelLabelValue = "Wybierz Poziom";

        final Label labelMethodOrAlgorithm = (Label) topPane.getChildren().get(0);
        final ComboBox comboBoxMethodOrAlgorithm = (ComboBox) topPane.getChildren().get(1);

        String selectedOperation = FxHelper.getValueFromComboBox(comboBoxOperationTypesOneArgs);
        FxHelper.setPaneVisibility(false, topPane, middlePane);

        if (selectedOperation.equals(OneArgsOperationType.SAMPLING.getName())) {
            FxHelper.setPaneVisibility(true, bottomPane);

            FxHelper.removeAndAddNewPaneChildren(bottomPane,
                    FxHelper.prepareLabelWithPosition("Częstotliwość próbkowania", 14, 33),
                    FxHelper.setTextFieldPosition(textFieldSampleRate, 250, 30)
            );

        } else if (selectedOperation.equals(OneArgsOperationType.DISCRETE_FOURIER_TRANSFORMATION.getName())
                || selectedOperation.equals(OneArgsOperationType.COSINE_TRANSFORMATION.getName())
                || selectedOperation.equals(OneArgsOperationType.WALSH_HADAMARD_TRANSFORMATION.getName())
                || selectedOperation.equals(OneArgsOperationType.WAVELET_TRANSFORMATION.getName())) {

            FxHelper.setPaneVisibility(false, bottomPane);
            FxHelper.setPaneVisibility(true, topPane, middlePane);

            labelMethodOrAlgorithm.setText(algorithmLabelValue);

            if (!selectedOperation.equals(OneArgsOperationType.WAVELET_TRANSFORMATION.getName())) {
                FxHelper.fillComboBox(comboBoxMethodOrAlgorithm, AlgorithmType.getNamesList());
            }

            if (selectedOperation.equals(OneArgsOperationType
                    .WAVELET_TRANSFORMATION.getName())) {
                FxHelper.fillComboBox(comboBoxMethodOrAlgorithm, WaveletType.getNamesList());
                labelMethodOrAlgorithm.setText(algorithmLevelLabelValue);
            }
        }
    }

    private void fillTwoArgsTab() {
        FxHelper.fillComboBox(comboBoxOperationTypesTwoArgs, TwoArgsOperationType.getNamesList());
        FxHelper.fillComboBox(comboBoxFirstSignalTwoArgs, FxHelper.getTabNameList(tabPaneResults.getTabs()));
        FxHelper.fillComboBox(comboBoxSecondSignalTwoArgs, FxHelper.getTabNameList(tabPaneResults.getTabs()));
    }
}
