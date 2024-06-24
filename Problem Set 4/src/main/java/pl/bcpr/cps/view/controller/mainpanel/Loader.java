package pl.bcpr.cps.view.controller.mainpanel;

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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import pl.bcpr.cps.Main;
import pl.bcpr.cps.logic.exception.NotSameLengthException;
import pl.bcpr.cps.logic.model.transform.Transformer;
import pl.bcpr.cps.view.fxml.DouglasPeuckerAlg;
import pl.bcpr.cps.view.fxml.FxHelper;
import pl.bcpr.cps.view.fxml.PopOutWindow;
import pl.bcpr.cps.view.fxml.StageController;
import pl.bcpr.cps.view.model.ChartRecord;
import pl.bcpr.cps.view.model.CustomTabPane;
import pl.bcpr.cps.logic.model.ADC;
import pl.bcpr.cps.logic.model.Operation;
import pl.bcpr.cps.logic.model.data.Data;
import pl.bcpr.cps.logic.model.enumtype.AlgorithmType;
import pl.bcpr.cps.logic.model.enumtype.OneArgsOperationType;
import pl.bcpr.cps.logic.model.enumtype.SignalType;
import pl.bcpr.cps.logic.model.enumtype.TwoArgsOperationType;
import pl.bcpr.cps.logic.model.enumtype.WaveletType;
import pl.bcpr.cps.logic.model.enumtype.WindowType;
import pl.bcpr.cps.logic.model.signal.BandPassFilter;
import pl.bcpr.cps.logic.model.signal.ComplexSignal;
import pl.bcpr.cps.logic.model.signal.ContinuousSignal;
import pl.bcpr.cps.logic.model.signal.ConvolutionSignal;
import pl.bcpr.cps.logic.model.signal.CorrelationSignal;
import pl.bcpr.cps.logic.model.signal.DiscreteSignal;
import pl.bcpr.cps.logic.model.signal.HighPassFilter;
import pl.bcpr.cps.logic.model.signal.LowPassFilter;
import pl.bcpr.cps.logic.model.signal.OperationResultContinuousSignal;
import pl.bcpr.cps.logic.model.signal.OperationResultDiscreteSignal;
import pl.bcpr.cps.logic.model.signal.RectangularSignal;
import pl.bcpr.cps.logic.model.signal.RectangularSymmetricSignal;
import pl.bcpr.cps.logic.model.signal.Signal;
import pl.bcpr.cps.logic.model.signal.SinusoidalRectifiedOneHalfSignal;
import pl.bcpr.cps.logic.model.signal.SinusoidalRectifiedTwoHalfSignal;
import pl.bcpr.cps.logic.model.signal.SinusoidalSignal;
import pl.bcpr.cps.logic.model.signal.TriangularSignal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static pl.bcpr.cps.view.fxml.FxHelper.changeLineChartToScatterChart;
import static pl.bcpr.cps.view.fxml.FxHelper.changeScatterChartToLineChart;

public class Loader {

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
    private CheckBox checkBoxTransformation;

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

    private Map<Integer, Signal> signals = new HashMap<>();
    private double overallTime = 0;

    private final ADC adc = new ADC();

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
                  CheckBox checkBoxSignalParams, CheckBox checkBoxComparison,
                  CheckBox checkBoxTransformation) {
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
        this.checkBoxTransformation = checkBoxTransformation;
    }

    public void computeCharts() {
        String selectedSignal = FxHelper.getValueFromComboBox(comboBoxSignalTypes);

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
                        FxHelper.getValueFromComboBox((ComboBox) windowTypePane.getChildren().get(1)));
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

    public void performOneArgsOperationOnCharts() throws Exception {
        Signal signal = null;
        final String selectedOperationOneArgs = FxHelper.getValueFromComboBox(comboBoxOperationTypesOneArgs);
        final Integer selectedSignalIndex = FxHelper.getIndexFromComboBox(comboBoxSignalOneArgs);
        final Signal selectedSignal = signals.get(selectedSignalIndex);

        final Pane topPane = (Pane) oneArgsPane.getChildren().get(0);
        final Pane middlePane = (Pane) oneArgsPane.getChildren().get(1);
        final ComboBox comboBoxMethodOrAlgorithm = (ComboBox) topPane.getChildren().get(1);
        final TextField textFieldComputationTime = (TextField) middlePane.getChildren().get(1);

        final Transformer transformer = new Transformer();


            long startTime = System.currentTimeMillis();

            if (selectedOperationOneArgs.equals(OneArgsOperationType.SAMPLING.getName())) {
                double sampleRate = Double.valueOf(textFieldSampleRate.getText());
                signal = adc.sampling((ContinuousSignal) selectedSignal, sampleRate);

            } else if (selectedOperationOneArgs.equals(OneArgsOperationType
                    .DISCRETE_FOURIER_TRANSFORMATION.getName())) {
                final String algorithm = FxHelper.getValueFromComboBox(comboBoxMethodOrAlgorithm);

                if (algorithm.equals(AlgorithmType.BY_DEFINITION.getName())) {
                    signal = calculateInvocationTime(() -> transformer
                                    .discreteFourierTransform((DiscreteSignal) selectedSignal),
                            textFieldComputationTime
                    );

                } else if (algorithm.equals(AlgorithmType.FAST_TRANSFORMATION.getName())) {
                    signal = calculateInvocationTime(() -> transformer
                                    .fastFourierTransformInSitu((DiscreteSignal) selectedSignal),
                            textFieldComputationTime);
                }

            } else if (selectedOperationOneArgs.equals(OneArgsOperationType
                    .COSINE_TRANSFORMATION.getName())) {
                final String algorithm = FxHelper.getValueFromComboBox(comboBoxMethodOrAlgorithm);

                if (algorithm.equals(AlgorithmType.BY_DEFINITION.getName())) {
                    signal = calculateInvocationTime(() -> transformer
                                    .discreteCosineTransform((DiscreteSignal) selectedSignal),
                            textFieldComputationTime
                    );
                } else if (algorithm.equals(AlgorithmType.FAST_TRANSFORMATION.getName())) {
                    signal = calculateInvocationTime(() -> transformer
                                    .fastCosineTransform((DiscreteSignal) selectedSignal),
                            textFieldComputationTime
                    );
                }

            } else if (selectedOperationOneArgs.equals(OneArgsOperationType
                    .WALSH_HADAMARD_TRANSFORMATION.getName())) {
                final String algorithm = FxHelper.getValueFromComboBox(comboBoxMethodOrAlgorithm);

                if (algorithm.equals(AlgorithmType.BY_DEFINITION.getName())) {
                    signal = calculateInvocationTime(() -> transformer
                                    .walshHadamardTransform((DiscreteSignal) selectedSignal),
                            textFieldComputationTime
                    );
                } else if (algorithm.equals(AlgorithmType.FAST_TRANSFORMATION.getName())) {
                    signal = calculateInvocationTime(() -> transformer
                                    .fastWalshHadamardTransform((DiscreteSignal) selectedSignal),
                            textFieldComputationTime
                    );
                }

            } else if (selectedOperationOneArgs.equals(OneArgsOperationType
                    .WAVELET_TRANSFORMATION.getName())) {
                final String level = FxHelper.getValueFromComboBox(comboBoxMethodOrAlgorithm);

                if (level.equals(WaveletType.DB4.getName())) {
                    signal = calculateInvocationTime(() -> transformer
                                    .discreteWaveletTransform((DiscreteSignal) selectedSignal),
                            textFieldComputationTime
                    );
                }
            }

            overallTime += ((System.currentTimeMillis() - startTime) / 1000.0);

            if (signal instanceof ComplexSignal) {
                representComplexSignal(signal);
            } else {
                representSignal(signal);
            }


    }

    public void performTwoArgsOperationOnCharts() {
        String selectedOperation = FxHelper.getValueFromComboBox(comboBoxOperationTypesTwoArgs);

        int s1Index = FxHelper.getIndexFromComboBox(comboBoxFirstSignalTwoArgs);
        int s2Index = FxHelper.getIndexFromComboBox(comboBoxSecondSignalTwoArgs);

        Signal s1 = signals.get(s1Index);
        Signal s2 = signals.get(s2Index);
        Signal resultSignal = null;

        try {
            if (selectedOperation.equals(TwoArgsOperationType.CONVOLUTION.getName())) {
                resultSignal = new ConvolutionSignal((DiscreteSignal) s1, (DiscreteSignal) s2);
            } else if (selectedOperation.equals(TwoArgsOperationType.CORRELATION.getName())) {
                resultSignal = new CorrelationSignal((DiscreteSignal) s1, (DiscreteSignal) s2);
            } else {
                Operation operation;
                if (selectedOperation.equals(TwoArgsOperationType.ADDITION.getName())) {
                    operation = (a, b) -> a + b;
                } else if (selectedOperation.equals(TwoArgsOperationType.SUBTRACTION.getName())) {
                    operation = (a, b) -> a - b;
                } else if (selectedOperation.equals(TwoArgsOperationType.MULTIPLICATION.getName())) {
                    operation = (a, b) -> a * b;
                } else {
                    operation = (a, b) -> a / b;
                }
                if (s1 instanceof DiscreteSignal) {
                    resultSignal = new OperationResultDiscreteSignal(
                            (DiscreteSignal) s1,
                            (DiscreteSignal) s2,
                            operation);
                } else {
                    resultSignal = new OperationResultContinuousSignal(
                            (ContinuousSignal) s1,
                            (ContinuousSignal) s2,
                            operation);
                }
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

    public void generateComparison() {
        Integer selectedTab1Index = FxHelper.getIndexFromComboBox(comboBoxComparisonFirstSignal);
        Integer selectedTab2Index = FxHelper.getIndexFromComboBox(comboBoxComparisonSecondSignal);
        List<Node> paneChildren = comparisonPane.getChildren();

        List<Data> firstSignalData = signals.get(selectedTab1Index)
                .generateDiscreteRepresentation();
        List<Data> secondSignalData = signals.get(selectedTab2Index)
                .generateDiscreteRepresentation();
        DecimalFormat df = new DecimalFormat("##.####");

        try {
            double meanSquaredError = Signal.meanSquaredError(secondSignalData, firstSignalData);
            double signalToNoiseRatio = Signal.signalToNoiseRatio(secondSignalData,
                    firstSignalData);
            double peakSignalToNoiseRatio = Signal.peakSignalToNoiseRatio(secondSignalData,
                    firstSignalData);
            double maximumDifference = Signal.maximumDifference(secondSignalData, firstSignalData);
            double effectiveNumberOfBits = Signal.effectiveNumberOfBits(secondSignalData,
                    firstSignalData);

            FxHelper.appendLabelText(paneChildren.get(0), "" + df.format(meanSquaredError));
            FxHelper.appendLabelText(paneChildren.get(1), "" + df.format(signalToNoiseRatio));
            FxHelper.appendLabelText(paneChildren.get(2), "" + df.format(peakSignalToNoiseRatio));
            FxHelper.appendLabelText(paneChildren.get(3), "" + df.format(maximumDifference));
            FxHelper.appendLabelText(paneChildren.get(4), "" + df.format(effectiveNumberOfBits));
            FxHelper.appendLabelText(paneChildren.get(5), "" + df.format(overallTime));

        } catch (NotSameLengthException e) {
            PopOutWindow.messageBox("Błednie wybrane wykresy",
                    "Wykresy mają błędnie dobraną długość",
                    Alert.AlertType.WARNING);
        }
    }

    public void loadChart() {
        int tabIndex = FxHelper.getSelectedTabIndex(tabPaneResults);
    }

    public void saveChart() {
        int tabIndex = FxHelper.getSelectedTabIndex(tabPaneResults);
    }

    private Signal calculateInvocationTime(Callable<Signal> callable,
                                           TextField textField) throws Exception {
        long begin = System.currentTimeMillis();
        Signal signal = callable.call();
        double end = ((System.currentTimeMillis() - begin) / 1000.0);
        textField.setText(String.valueOf(end));

        return signal;
    }

    private void representComplexSignal(Signal signal) {
        CustomTabPane customTabPane = FxHelper.getCurrentCustomTabPaneFromTabPane(tabPaneResults);
        ComplexSignal complexSignal = (ComplexSignal) signal;

        List<ChartRecord<Number, Number>> chartDataReal =
                convertDiscreteRepresentationToChartRecord(complexSignal,
                        ComplexSignal.DiscreteRepresentationType.REAL);

        List<ChartRecord<Number, Number>> chartDataImaginary =
                convertDiscreteRepresentationToChartRecord(complexSignal,
                        ComplexSignal.DiscreteRepresentationType.IMAGINARY);

        List<ChartRecord<Number, Number>> chartDataAbs =
                convertDiscreteRepresentationToChartRecord(complexSignal,
                        ComplexSignal.DiscreteRepresentationType.ABS);

        List<ChartRecord<Number, Number>> chartDataArgument =
                convertDiscreteRepresentationToChartRecord(complexSignal,
                        ComplexSignal.DiscreteRepresentationType.ARG);


            VBox vBoxW1 = (VBox) customTabPane.getTabW1().getContent();
            FxHelper.clearAndFillLineChart((LineChart) vBoxW1.getChildren().get(0), chartDataReal);
            FxHelper.clearAndFillLineChart((LineChart) vBoxW1.getChildren().get(1), chartDataImaginary);

            VBox vBoxW2 = (VBox) customTabPane.getTabW2().getContent();
            FxHelper.clearAndFillLineChart((LineChart) vBoxW2.getChildren().get(0), chartDataAbs);
            FxHelper.clearAndFillLineChart((LineChart) vBoxW2.getChildren().get(1), chartDataArgument);

            if (FxHelper.isCheckBoxSelected(checkBoxTransformation)) {
                FxHelper.switchTabToAnother(customTabPane, 3);

                FxHelper.switchTabToAnother(customTabPane, 4);
            }

            FxHelper.switchTabToAnother(customTabPane, 3);

    }

    private List<ChartRecord<Number, Number>> convertDiscreteRepresentationToChartRecord(
            ComplexSignal complexSignal,
            ComplexSignal.DiscreteRepresentationType discreteRepresentationType) {
        complexSignal.setDiscreteRepresentationType(discreteRepresentationType);

        return complexSignal.generateDiscreteRepresentation()
                .stream()
                .map((it) -> new ChartRecord<Number, Number>(it.getX(), it.getY()))
                .collect(Collectors.toList());
    }

    private void representSignal(Signal signal) {
        int tabIndex = FxHelper.getSelectedTabIndex(tabPaneResults);
        signals.put(tabIndex, signal);

        List<Data> signalData = signal.generateDiscreteRepresentation();

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

        double[] signalParams = new double[5];
        signalParams[0] = Signal.meanValue(signalData);
        signalParams[1] = Signal.absMeanValue(signalData);
        signalParams[2] = Signal.rmsValue(signalData);
        signalParams[3] = Signal.varianceValue(signalData);
        signalParams[4] = Signal.meanPowerValue(signalData);

        fillCustomTabPaneWithData(tabPaneResults, chartData, signalParams,
                signal instanceof DiscreteSignal);
    }

    private void fillCustomTabPaneWithData(TabPane tabPane,
                                           Collection<ChartRecord<Number, Number>> mainChartData,
                                           double[] signalParams, boolean isScatterChart) {
        CustomTabPane customTabPane = FxHelper.getCurrentCustomTabPaneFromTabPane(tabPane);


            FxHelper.switchTabToAnother(customTabPane, 1);


            if (isScatterChart) {
                FxHelper.changeLineChartToScatterChart(tabPane);
                FxHelper.clearAndFillScatterChart((ScatterChart) customTabPane.getChartTab()
                        .getContent(), mainChartData);
                FxHelper.switchTabToAnother(customTabPane, 0);


            } else {
                FxHelper.changeScatterChartToLineChart(tabPane);
                FxHelper.clearAndFillLineChart((LineChart) customTabPane.getChartTab()
                        .getContent(), mainChartData);
                FxHelper.switchTabToAnother(customTabPane, 0);
            }

            fillParamsTab(customTabPane, signalParams);


    }

    private void fillParamsTab(CustomTabPane customTabPane, double[] signalParams) {
        Pane pane = (Pane) customTabPane.getParamsTab().getContent();
        List<Node> paneChildren = pane.getChildren();

        DecimalFormat df = new DecimalFormat("##.####");
        FxHelper.appendLabelText(paneChildren.get(0), "" + df.format(signalParams[0]));
        FxHelper.appendLabelText(paneChildren.get(1), "" + df.format(signalParams[1]));
        FxHelper.appendLabelText(paneChildren.get(2), "" + df.format(signalParams[2]));
        FxHelper.appendLabelText(paneChildren.get(3), "" + df.format(signalParams[3]));
        FxHelper.appendLabelText(paneChildren.get(4), "" + df.format(signalParams[4]));
    }
}
