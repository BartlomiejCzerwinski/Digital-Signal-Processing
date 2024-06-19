package pl.bcpr.cps.view.fxml;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import pl.bcpr.cps.view.fxml.core.WindowDimensions;
import pl.bcpr.cps.view.fxml.core.FxmlStageSetup;

import java.io.IOException;

public class StageController {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    public static Stage getApplicationStage() {
        return FxmlStageSetup.getApplicationStage();
    }

    public static WindowDimensions getWindowDimensions() {
        return FxmlStageSetup.getWindowDimensions();
    }

    public static String getGlobalCssStyling() {
        return FxmlStageSetup.getGlobalCssStyling();
    }

    public static void setGlobalCssStyling(String globalCssStyling) {
        FxmlStageSetup.setGlobalCssStyling(globalCssStyling);
    }

    public static void buildStage(Stage stage, String filePath, String title,
                                  WindowDimensions dimensions, String cssFilePath) throws IOException {

            FxmlStageSetup.buildStage(stage, filePath, title, dimensions, cssFilePath);

    }

    public static void loadStage(String filePath, String title) {
        try {
            FxmlStageSetup.loadStage(filePath, title);
        } catch (IOException | IllegalStateException e) {
            PopOutWindow.messageBox("Stage Loading Error",
                    "Stage cannot be properly loaded", Alert.AlertType.ERROR);
        }
    }

    public static void reloadStage(String filePath, String title) {
        try {
            FxmlStageSetup.reloadStage(filePath, title);
        } catch (IOException | IllegalStateException e) {
            PopOutWindow.messageBox("Stage Reloading Error",
                    "Stage cannot be properly reloaded", Alert.AlertType.ERROR);
        }
    }
}
    