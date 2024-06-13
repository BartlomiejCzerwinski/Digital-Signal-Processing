package pl.jkkk.cps.view.controller.mainpanel;

import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import pl.jkkk.cps.Main;
import pl.jkkk.cps.logic.exception.NotSameLengthException;
import pl.jkkk.cps.logic.model.ADC;
import pl.jkkk.cps.logic.model.Data;
import pl.jkkk.cps.logic.model.enumtype.OneArgsOperationType;
import pl.jkkk.cps.logic.model.enumtype.SignalType;
import pl.jkkk.cps.logic.model.enumtype.TwoArgsOperationType;
import pl.jkkk.cps.logic.model.enumtype.WindowType;
import pl.jkkk.cps.logic.model.signal.BandPassFilter;
import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.ConvolutionSignal;
import pl.jkkk.cps.logic.model.signal.CorrelationSignal;
import pl.jkkk.cps.logic.model.signal.DiscreteSignal;
import pl.jkkk.cps.logic.model.signal.HighPassFilter;
import pl.jkkk.cps.logic.model.signal.LowPassFilter;
import pl.jkkk.cps.logic.model.signal.RectangularSignal;
import pl.jkkk.cps.logic.model.signal.RectangularSymmetricSignal;
import pl.jkkk.cps.logic.model.signal.Signal;
import pl.jkkk.cps.logic.model.signal.SinusoidalRectifiedOneHalfSignal;
import pl.jkkk.cps.logic.model.signal.SinusoidalRectifiedTwoHalfSignal;
import pl.jkkk.cps.logic.model.signal.SinusoidalSignal;
import pl.jkkk.cps.logic.model.signal.TriangularSignal;
import pl.jkkk.cps.view.fxml.DouglasPeuckerAlg;
import pl.jkkk.cps.view.fxml.PopOutWindow;
import pl.jkkk.cps.view.fxml.StageController;
import pl.jkkk.cps.view.model.ChartRecord;
import pl.jkkk.cps.view.model.CustomTabPane;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.jkkk.cps.view.fxml.FxHelper.appendLabelText;
import static pl.jkkk.cps.view.fxml.FxHelper.changeLineChartToScatterChart;
import static pl.jkkk.cps.view.fxml.FxHelper.changeScatterChartToLineChart;
import static pl.jkkk.cps.view.fxml.FxHelper.clearAndFillLineChart;
import static pl.jkkk.cps.view.fxml.FxHelper.clearAndFillScatterChart;
import static pl.jkkk.cps.view.fxml.FxHelper.getCurrentCustomTabPaneFromTabPane;
import static pl.jkkk.cps.view.fxml.FxHelper.getIndexFromComboBox;
import static pl.jkkk.cps.view.fxml.FxHelper.getSelectedTabIndex;
import static pl.jkkk.cps.view.fxml.FxHelper.getValueFromComboBox;
import static pl.jkkk.cps.view.fxml.FxHelper.isCheckBoxSelected;
import static pl.jkkk.cps.view.fxml.FxHelper.switchTabToAnother;

public class Loader {

    /*------------------------ FIELDS REGION ------------------------*/
    private ComboBox comboBoxSignalTypes;
    private ComboBox comboBoxOperationTypesTwoArgs;
    private ComboBox comboBoxFirstSignalTwoArgs;
    private ComboBox comboBoxSecondSignalTwoArgs;

    private TextField textFieldAmplitude;
    private TextField textFieldStartTime;
    private TextField textFieldSignalDuration;
    private TextField textFieldBasicPeriod;
    private TextField textFieldFillFactor;
    private TextField textFieldJumpTime;
    private TextField textFieldProbability;
    private TextField textFieldSamplingFrequency;

    private TabPane tabPaneResults;

    private CheckBox checkBoxDataChart;
    private CheckBox checkBoxHistogram;
    private CheckBox checkBoxSignalParams;
    private CheckBox checkBoxComparison;

    private ComboBox comboBoxOperationTypesOneArgs;
    private ComboBox comboBoxSignalOneArgs;
    private AnchorPane oneArgsPane;
    private TextField textFieldQuantizationLevels;
    private TextField textFieldSampleRate;
    private TextField textFieldReconstructionSincParam;
    private AnchorPane windowTypePane;
    private TextField textFieldCuttingFrequency;
    private TextField textFieldFilterRow;

    private Map<Integer, Signal> signals = new HashMap<>();
    private double overallTime = 0;

    private final ADC adc = new ADC();

    /*------------------------ METHODS REGION ------------------------*/
    public Loader(ComboBox comboBoxSignalTypes, ComboBox comboBoxOperationTypesTwoArgs,
                  ComboBox comboBoxFirstSignalTwoArgs, ComboBox comboBoxSecondSignalTwoArgs,
                  TextField textFieldAmplitude, TextField textFieldStartTime,
                  TextField textFieldSignalDuration, TextField textFieldBasicPeriod,
                  TextField textFieldFillFactor, TextField textFieldJumpTime,
                  TextField textFieldProbability, TextField textFieldSamplingFrequency,
                  TabPane tabPaneResults,
                  ComboBox comboBoxOperationTypesOneArgs, ComboBox comboBoxSignalOneArgs,
                  AnchorPane oneArgsPane,
                  TextField textFieldQuantizationLevels, TextField textFieldSampleRate,
                  TextField textFieldReconstructionSincParam, AnchorPane windowTypePane,
                  TextField textFieldCuttingFrequency, TextField textFieldFilterRow,
                  CheckBox checkBoxDataChart, CheckBox checkBoxHistogram,
                  CheckBox checkBoxSignalParams, CheckBox checkBoxComparison) {
        this.comboBoxSignalTypes = comboBoxSignalTypes;
        this.comboBoxOperationTypesTwoArgs = comboBoxOperationTypesTwoArgs;
        this.comboBoxFirstSignalTwoArgs = comboBoxFirstSignalTwoArgs;
        this.comboBoxSecondSignalTwoArgs = comboBoxSecondSignalTwoArgs;
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
        this.checkBoxDataChart = checkBoxDataChart;
        this.checkBoxHistogram = checkBoxHistogram;
        this.checkBoxSignalParams = checkBoxSignalParams;
        this.checkBoxComparison = checkBoxComparison;
    }

    /*--------------------------------------------------------------------------------------------*/
    public void computeCharts() {
        String selectedSignal = getValueFromComboBox(comboBoxSignalTypes);

        try {
            Double amplitude = Double.parseDouble(textFieldAmplitude.getText());
            Double rangeStart = Double.parseDouble(textFieldStartTime.getText());
            Double rangeLength = Double.parseDouble(textFieldSignalDuration.getText());
            Double term = Double.parseDouble(textFieldBasicPeriod.getText());
            Double fulfillment = Double.parseDouble(textFieldFillFactor.getText());
            Double jumpMoment = Double.parseDouble(textFieldJumpTime.getText());
            Double probability = Double.parseDouble(textFieldProbability.getText());
            Double sampleRate = Double.parseDouble(textFieldSamplingFrequency.getText());
            Double cuttingFrequency = Double.parseDouble(textFieldCuttingFrequency.getText());
            Integer filterRow = Integer.parseInt(textFieldFilterRow.getText());

            WindowType selectedWindowType = null;
            if (selectedSignal.equals(SignalType.LOW_PASS_FILTER.getName())
                    || selectedSignal.equals(SignalType.BAND_PASS_FILTER.getName())
                    || selectedSignal.equals(SignalType.HIGH_PASS_FILTER.getName())) {
                selectedWindowType = WindowType.fromString(
                        getValueFromComboBox((ComboBox) windowTypePane.getChildren().get(1)));
            }
            Signal signal = null;

            if (selectedSignal.equals(SignalType.SINUSOIDAL_SIGNAL.getName())) {
                signal = new SinusoidalSignal(rangeStart, rangeLength, amplitude, term);
            } else if (selectedSignal.equals(SignalType.SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL.getName())) {
                signal = new SinusoidalRectifiedOneHalfSignal(rangeStart, rangeLength,
                        amplitude, term);
            } else if (selectedSignal.equals(SignalType.SINUSOIDAL_RECTIFIED_IN_TWO_HALVES.getName())) {
                signal = new SinusoidalRectifiedTwoHalfSignal(rangeStart, rangeLength,
                        amplitude, term);
            } else if (selectedSignal.equals(SignalType.RECTANGULAR_SIGNAL.getName())) {
                signal = new RectangularSignal(rangeStart, rangeLength, amplitude,
                        term, fulfillment);
            } else if (selectedSignal.equals(SignalType.SYMMETRICAL_RECTANGULAR_SIGNAL.getName())) {
                signal = new RectangularSymmetricSignal(rangeStart, rangeLength, amplitude,
                        term, fulfillment);
            } else if (selectedSignal.equals(SignalType.TRIANGULAR_SIGNAL.getName())) {
                signal = new TriangularSignal(rangeStart, rangeLength, amplitude, term,
                        fulfillment);
            } else if (selectedSignal.equals(SignalType.LOW_PASS_FILTER.getName())) {
                signal = new LowPassFilter(sampleRate, filterRow, cuttingFrequency,
                        WindowType.fromEnum(selectedWindowType, filterRow));
            } else if (selectedSignal.equals(SignalType.BAND_PASS_FILTER.getName())) {
                signal = new BandPassFilter(sampleRate, filterRow, cuttingFrequency,
                        WindowType.fromEnum(selectedWindowType, filterRow));
            } else if (selectedSignal.equals(SignalType.HIGH_PASS_FILTER.getName())) {
                signal = new HighPassFilter(sampleRate, filterRow, cuttingFrequency,
                        WindowType.fromEnum(selectedWindowType, filterRow));
            }

            representSignal(signal);

        } catch (IllegalArgumentException e) {
            PopOutWindow.messageBox("Błędne Dane", "Wprowadzono błędne dane",
                    Alert.AlertType.WARNING);
        }
    }

    public void performOneArgsOperationOnCharts() {
        Signal signal = null;
        String selectedOperationOneArgs = getValueFromComboBox(comboBoxOperationTypesOneArgs);
        Integer selectedSignalIndex = getIndexFromComboBox(comboBoxSignalOneArgs);
        Signal selectedSignal = signals.get(selectedSignalIndex);

        try {
            long startTime = System.currentTimeMillis();

            if (selectedOperationOneArgs.equals(OneArgsOperationType.SAMPLING.getName())) {
                double sampleRate = Double.valueOf(textFieldSampleRate.getText());

                signal = adc.sampling((ContinuousSignal) selectedSignal, sampleRate);

            }
            overallTime += ((System.currentTimeMillis() - startTime) / 1000.0);

            representSignal(signal);

        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            PopOutWindow.messageBox("Błędne dane", "Wprowadzono błędne dane",
                    Alert.AlertType.WARNING);
        } catch (ClassCastException e) {
            e.printStackTrace();
            PopOutWindow.messageBox("Błędne dane", "Wybrano niepoprawny typ sygnału",
                    Alert.AlertType.WARNING);
        }
    }

    public void performTwoArgsOperationOnCharts() {
        String selectedOperation = getValueFromComboBox(comboBoxOperationTypesTwoArgs);

        int s1Index = getIndexFromComboBox(comboBoxFirstSignalTwoArgs);
        int s2Index = getIndexFromComboBox(comboBoxSecondSignalTwoArgs);

        Signal s1 = signals.get(s1Index);
        Signal s2 = signals.get(s2Index);
        Signal resultSignal = null;

        try {
            if (selectedOperation.equals(TwoArgsOperationType.CONVOLUTION.getName())) {
                resultSignal = new ConvolutionSignal((DiscreteSignal) s1, (DiscreteSignal) s2);
            } else if (selectedOperation.equals(TwoArgsOperationType.CORRELATION.getName())) {
                resultSignal = new CorrelationSignal((DiscreteSignal) s1, (DiscreteSignal) s2);
            }
            representSignal(resultSignal);
        } catch (NotSameLengthException e) {
            PopOutWindow.messageBox("Błednie wybrane wykresy",
                    "Wykresy mają błędnie dobraną długość",
                    Alert.AlertType.WARNING);
        } catch (ClassCastException e) {
            PopOutWindow.messageBox("Błędne dane",
                    "Wybrano niepoprawny typ sygnału",
                    Alert.AlertType.WARNING);
        } catch (NullPointerException e) {
            PopOutWindow.messageBox("Brak Wygenerowanego Sygnału",
                    "Sygnał nie został jeszcze wygenerowany",
                    Alert.AlertType.WARNING);
        }
    }



    /*--------------------------------------------------------------------------------------------*/
    private void representSignal(Signal signal) {
        /* remember signal */
        int tabIndex = getSelectedTabIndex(tabPaneResults);
        signals.put(tabIndex, signal);

        /* generate discrete representation of signal */
        List<Data> signalData = signal.generateDiscreteRepresentation();

        /* prepare line/point chart data */
        List<Data> data;
        if (signal instanceof ContinuousSignal) {
            DouglasPeuckerAlg douglasPeucker = new DouglasPeuckerAlg();
            data = signalData;
            data = new ArrayList<>(douglasPeucker
                    .calculate(data, (data.get(data.size() - 1).getX() - data.get(0)
                            .getX()) * 1.0 / 10000.0, 0, data.size() - 1));
        } else {
            data = signalData;
        }

        System.out.println("Wygenerowanu punktów: " + data.size());

        List<ChartRecord<Number, Number>> chartData =
                data.stream().map(d -> new ChartRecord<Number, Number>(d.getX(), d.getY()))
                        .collect(Collectors.toList());

        /* prepare params */
        double[] signalParams = new double[5];
        signalParams[0] = Signal.meanValue(signalData);
        signalParams[1] = Signal.absMeanValue(signalData);
        signalParams[2] = Signal.rmsValue(signalData);
        signalParams[3] = Signal.varianceValue(signalData);
        signalParams[4] = Signal.meanPowerValue(signalData);

        /* render it all */
        fillCustomTabPaneWithData(tabPaneResults, chartData, signalParams,
                signal instanceof DiscreteSignal);
    }

    private void fillCustomTabPaneWithData(TabPane tabPane,
                                           Collection<ChartRecord<Number, Number>> mainChartData,
                                           double[] signalParams, boolean isScatterChart) {
        CustomTabPane customTabPane = getCurrentCustomTabPaneFromTabPane(tabPane);

        try {

            if (isScatterChart) {
                changeLineChartToScatterChart(tabPane);
                clearAndFillScatterChart((ScatterChart) customTabPane.getChartTab()
                        .getContent(), mainChartData);
                switchTabToAnother(customTabPane, 0);


            } else {
                changeScatterChartToLineChart(tabPane);
                clearAndFillLineChart((LineChart) customTabPane.getChartTab()
                        .getContent(), mainChartData);
                switchTabToAnother(customTabPane, 0);

            }

            fillParamsTab(customTabPane, signalParams);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*--------------------------------------------------------------------------------------------*/
    private void fillParamsTab(CustomTabPane customTabPane, double[] signalParams) {
        Pane pane = (Pane) customTabPane.getParamsTab().getContent();
        List<Node> paneChildren = pane.getChildren();

        DecimalFormat df = new DecimalFormat("##.####");
        appendLabelText(paneChildren.get(0), "" + df.format(signalParams[0]));
        appendLabelText(paneChildren.get(1), "" + df.format(signalParams[1]));
        appendLabelText(paneChildren.get(2), "" + df.format(signalParams[2]));
        appendLabelText(paneChildren.get(3), "" + df.format(signalParams[3]));
        appendLabelText(paneChildren.get(4), "" + df.format(signalParams[4]));
    }
}
