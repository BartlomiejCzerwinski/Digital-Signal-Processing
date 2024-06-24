package pl.bcpr.cps.view.controller.mainpanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import pl.bcpr.cps.view.constant.Constants;
import pl.bcpr.cps.view.fxml.FxHelper;
import pl.bcpr.cps.view.fxml.StageController;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPanel implements Initializable {

    @FXML
    private TabPane tabPaneInputs;
    @FXML
    private Pane chooseParamsTab;
    @FXML
    private AnchorPane oneArgsPane;
    @FXML
    private AnchorPane windowTypePane;
    @FXML
    private ComboBox comboBoxSignalTypes;
    @FXML
    private ComboBox comboBoxOperationTypesTwoArgs;
    @FXML
    private ComboBox comboBoxFirstSignalTwoArgs;
    @FXML
    private ComboBox comboBoxSecondSignalTwoArgs;
    @FXML
    private TabPane tabPaneResults;

    @FXML
    private ComboBox comboBoxOperationTypesOneArgs;
    @FXML
    private ComboBox comboBoxSignalOneArgs;
    @FXML
    private CheckBox checkBoxDataChart;
    @FXML
    private CheckBox checkBoxHistogram;
    @FXML
    private CheckBox checkBoxSignalParams;
    @FXML
    private CheckBox checkBoxComparison;
    @FXML
    private CheckBox checkBoxTransformation;

    private TextField textFieldAmplitude = new TextField();
    private TextField textFieldStartTime = new TextField();
    private TextField textFieldSignalDuration = new TextField();
    private TextField textFieldBasicPeriod = new TextField();
    private TextField textFieldFillFactor = new TextField();
    private TextField textFieldJumpTime = new TextField();
    private TextField textFieldProbability = new TextField();
    private TextField textFieldSamplingFrequency = new TextField();
    private TextField textFieldQuantizationLevels = new TextField();
    private TextField textFieldSampleRate = new TextField();
    private TextField textFieldReconstructionSincParam = new TextField();
    private TextField textFieldCuttingFrequency = new TextField();
    private TextField textFieldFilterRow = new TextField();

    private Initializer initializer;
    private Loader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializer = new Initializer(
                comboBoxSignalTypes, comboBoxOperationTypesTwoArgs, comboBoxFirstSignalTwoArgs,
                comboBoxSecondSignalTwoArgs, chooseParamsTab, textFieldAmplitude,
                textFieldStartTime, textFieldSignalDuration, textFieldBasicPeriod,
                textFieldFillFactor, textFieldJumpTime, textFieldProbability,
                textFieldSamplingFrequency, tabPaneResults,
                comboBoxOperationTypesOneArgs, comboBoxSignalOneArgs,
                oneArgsPane, textFieldQuantizationLevels,
                textFieldSampleRate, textFieldReconstructionSincParam, windowTypePane,
                textFieldCuttingFrequency, textFieldFilterRow
        );

        loader = new Loader(
                comboBoxSignalTypes, comboBoxOperationTypesTwoArgs, comboBoxFirstSignalTwoArgs,
                comboBoxSecondSignalTwoArgs, textFieldAmplitude, textFieldStartTime,
                textFieldSignalDuration, textFieldBasicPeriod, textFieldFillFactor,
                textFieldJumpTime, textFieldProbability, textFieldSamplingFrequency,
                tabPaneResults,
                comboBoxOperationTypesOneArgs, comboBoxSignalOneArgs,
                oneArgsPane, textFieldQuantizationLevels,
                textFieldSampleRate, textFieldReconstructionSincParam, windowTypePane,
                textFieldCuttingFrequency, textFieldFilterRow, checkBoxDataChart,
                checkBoxHistogram, checkBoxSignalParams, checkBoxComparison, checkBoxTransformation
        );

        initializer.prepareTabPaneResults(0);
        initializer.prepareTabPaneInputs();
    }

    @FXML
    private void onActionLoadChart(ActionEvent actionEvent) {
        loader.loadChart();
    }

    @FXML
    private void onActionSaveChart(ActionEvent actionEvent) {
        loader.saveChart();
    }

    @FXML
    private void onActionButtonGenerateComparison(ActionEvent actionEvent) {
        loader.generateComparison();
    }

    @FXML
    private void onActionButtonGenerateData(ActionEvent actionEvent) throws Exception {
        Integer selectedTab = FxHelper.getSelectedTabIndex(tabPaneInputs);

        switch (selectedTab) {
            case 0: {
                loader.computeCharts();
                break;
            }
            case 1: {
                loader.performOneArgsOperationOnCharts();
                break;
            }
            case 2: {
                loader.performTwoArgsOperationOnCharts();
                break;
            }
        }
    }

    @FXML
    private void onActionButtonAddNewTab(ActionEvent actionEvent) {
        initializer.prepareTabPaneResults(tabPaneResults.getTabs().size());

        FxHelper.fillComboBox(comboBoxFirstSignalTwoArgs, FxHelper.getTabNameList(tabPaneResults.getTabs()));
        FxHelper.fillComboBox(comboBoxSecondSignalTwoArgs, FxHelper.getTabNameList(tabPaneResults.getTabs()));

        FxHelper.fillComboBox(comboBoxSignalOneArgs, FxHelper.getTabNameList(tabPaneResults.getTabs()));
    }

    @FXML
    private void onActionButtonOpenAnimationWindow(ActionEvent actionEvent) {
        StageController.reloadStage(Constants.PATH_ANIMATION_PANEL, Constants.TITLE_ANIMATION_PANEL);
        StageController.getApplicationStage().setResizable(false);
    }

    @FXML
    private void onActionButtonChangeTheme(ActionEvent actionEvent) {
        FxHelper.changeTheme(Constants.PATH_MAIN_PANEL, Constants.TITLE_MAIN_PANEL,
                Constants.PATH_CSS_DARK_STYLING, Constants.PATH_CSS_LIGHT_STYLING);
        StageController.getApplicationStage().setResizable(false);
    }

    @FXML
    private void onActionButtonReloadStage(ActionEvent actionEvent) {
        StageController.reloadStage(Constants.PATH_MAIN_PANEL, Constants.TITLE_MAIN_PANEL);
        StageController.getApplicationStage().setResizable(false);
    }

    @FXML
    private void onActionButtonCloseProgram(ActionEvent actionEvent) {
        System.exit(0);
    }
}
