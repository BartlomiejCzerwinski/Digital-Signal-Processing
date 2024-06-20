package pl.bcpr.cps.view.fxml.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FxmlStageSetup {

    private static Stage applicationStage;
    private static WindowDimensions windowDimensions;
    private static String globalCssStyling;

    protected FxmlStageSetup() {
    }

    protected static Stage getApplicationStage() {
        return applicationStage;
    }

    protected static void setApplicationStage(Stage applicationStage) {
        FxmlStageSetup.applicationStage = applicationStage;
    }

    protected static WindowDimensions getWindowDimensions() {
        return windowDimensions;
    }

    protected static void setWindowDimensions(WindowDimensions windowDimensions) {
        FxmlStageSetup.windowDimensions = windowDimensions;
    }

    protected static String getGlobalCssStyling() {
        return globalCssStyling;
    }

    protected static void setGlobalCssStyling(String globalCssStyling) {
        FxmlStageSetup.globalCssStyling = globalCssStyling;
    }

    private static Parent loadFxml(String fxml) throws IOException {
        return new FXMLLoader(FxmlStageSetup.class.getResource(fxml)).load();
    }

    private static void prepareStage(String filePath, String title,
                                     WindowDimensions dimensions) throws IOException {
        Scene scene = new Scene(loadFxml(filePath));
        if (globalCssStyling != null) {
            scene.getStylesheets().add(globalCssStyling);
        }

        applicationStage.setScene(scene);
        applicationStage.setTitle(title);
        applicationStage.setWidth(dimensions.getWidth());
        applicationStage.setHeight(dimensions.getHeight());
        applicationStage.show();
    }

    protected static void buildStage(Stage stage, String filePath,
                                     String title, WindowDimensions dimensions,
                                     String cssFilePath) throws IOException {
        setApplicationStage(stage);
        setWindowDimensions(dimensions);
        setGlobalCssStyling(cssFilePath);
        prepareStage(filePath, title, windowDimensions);
    }

    protected static void loadStage(String filePath, String title) throws IOException {
        setApplicationStage(new Stage());
        prepareStage(filePath, title, windowDimensions);
    }

    protected static void reloadStage(String filePath, String title) throws IOException {
        applicationStage.close();
        loadStage(filePath, title);
    }
}
    