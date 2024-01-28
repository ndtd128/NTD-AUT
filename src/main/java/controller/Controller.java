package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.UploadUtil;
import utils.autoUnitTestUtil.ConcolicTesting;
import utils.autoUnitTestUtil.concolicResult.ConcolicParameterData;
import utils.autoUnitTestUtil.concolicResult.ConcolicTestData;
import utils.autoUnitTestUtil.concolicResult.ConcolicTestResult;
import utils.cloneProjectUtil.CloneProjectUtil;
import utils.cloneProjectUtil.projectTreeObjects.Folder;
import utils.cloneProjectUtil.projectTreeObjects.JavaFile;
import utils.cloneProjectUtil.projectTreeObjects.ProjectTreeObject;
import utils.cloneProjectUtil.projectTreeObjects.Unit;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private FileChooser fileChooser = new FileChooser();
    private File choseFile;
    private Unit choseUnit;
    private Coverage choseCoverage;

    public enum Coverage {
        STATEMENT,
        BRANCH
    }

    @FXML
    private Label filePreview;

    @FXML
    private Button uploadFileButton;

    @FXML
    private TreeView<ProjectTreeObject> projectTreeView;

    @FXML
    private Button generateButton;

    @FXML
    private ChoiceBox<String> coverageChoiceBox;

    @FXML
    private ListView<ConcolicTestData> testCaseListView;

    @FXML
    private VBox testCaseDetailVBox;

    @FXML
    private Label executeTimeLabel;

    @FXML
    private Label functionStatementCoverageLabel;

    @FXML
    private ListView<ConcolicParameterData> generatedTestDataListView;

    @FXML
    private Label outputLabel;

    @FXML
    private Label requireCoverageLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label testCaseIDLabel;

    @FXML
    private Label errorAlertLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        uploadFileButton.setDisable(true);
        generateButton.setDisable(true);
        coverageChoiceBox.setDisable(true);
        coverageChoiceBox.getItems().addAll("Statement coverage", "Branch coverage");
        coverageChoiceBox.setOnAction(this::selectCoverage);
        testCaseDetailVBox.setDisable(true);

        testCaseListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ConcolicTestData>() {
            @Override
            public void changed(ObservableValue<? extends ConcolicTestData> observableValue, ConcolicTestData concolicTestData, ConcolicTestData t1) {
                ConcolicTestData testData = testCaseListView.getSelectionModel().getSelectedItem();
                if(testData != null) {
                    setTestCaseDetail(testData);
                    testCaseDetailVBox.setDisable(false);
                }
            }
        });
    }

    @FXML
    void chooseFileButtonClicked(MouseEvent event) {
        choseFile = fileChooser.showOpenDialog(new Stage());
        if(choseFile != null) {
            filePreview.setText(choseFile.getAbsolutePath());
            uploadFileButton.setDisable(false);
        }
    }

    @FXML
    void uploadFileButtonClicked(MouseEvent event) throws IOException, InterruptedException {
        reset();
        try {
            CloneProjectUtil.deleteFilesInDirectory("\\NTD-AUT\\src\\main\\uploadedProject");
            UploadUtil.javaUnzipFile(choseFile.getPath(), "\\NTD-AUT\\src\\main\\uploadedProject");

            String javaDirPath = CloneProjectUtil.getJavaDirPath("\\NTD-AUT\\src\\main\\uploadedProject");
            if(javaDirPath.equals("")) throw new RuntimeException("Invalid project");

            Folder folder = CloneProjectUtil.cloneProject(javaDirPath, "\\NTD-AUT\\src\\main\\java\\clonedProject");

            TreeItem<ProjectTreeObject> rootFolder = switchToTreeItem(folder);

            projectTreeView.setRoot(rootFolder);
            errorAlertLabel.setText("");
        } catch (Exception e) {
            errorAlertLabel.setText("INVALID PROJECT ZIP FILE (eg: not a zip file, project's source code contains cases we haven't handled, ...)");
        }
    }

    private void reset() {
        projectTreeView.setRoot(null);
        coverageChoiceBox.setValue("");
        coverageChoiceBox.setDisable(true);
        generateButton.setDisable(true);
        testCaseListView.getItems().clear();
        errorAlertLabel.setText("");
        resetTestCaseDetailVBox();
    }

    private void resetTestCaseDetailVBox() {
        testCaseDetailVBox.setDisable(true);
        testCaseIDLabel.setText("   Test case ID:");
        functionStatementCoverageLabel.setText("   Function statement coverage:");
        requireCoverageLabel.setText("   Required coverage:");
        executeTimeLabel.setText("   Execute time:");
        outputLabel.setText("   Output:");
        statusLabel.setText("   Status:");
        generatedTestDataListView.getItems().clear();
    }

    private TreeItem<ProjectTreeObject> switchToTreeItem(ProjectTreeObject treeObject) {
        if(treeObject instanceof Folder) {
            TreeItem<ProjectTreeObject> item = new TreeItem<>(treeObject, new ImageView(new Image("\\img\\folder_icon.png")));
            List<ProjectTreeObject> children = ((Folder) treeObject).getChildren();
            for (ProjectTreeObject child : children) {
                item.getChildren().add(switchToTreeItem(child));
            }
            return item;
        } else if (treeObject instanceof JavaFile) {
            TreeItem<ProjectTreeObject> item = new TreeItem<>(treeObject, new ImageView(new Image("\\img\\java_file_icon.png")));
            List<Unit> units = ((JavaFile) treeObject).getUnits();
            for (Unit unit : units) {
                item.getChildren().add(switchToTreeItem(unit));
            }
            return item;
        } else if (treeObject instanceof Unit) {
            return new TreeItem<>(treeObject, new ImageView(new Image("\\img\\unit_icon.png")));
        } else {
            throw new RuntimeException("Invalid ProjectTreeObject");
        }
    }
    @FXML
    void selectUnit(MouseEvent event) {
        TreeItem<ProjectTreeObject> selectedItem = projectTreeView.getSelectionModel().getSelectedItem();

        if(selectedItem != null) {
            ProjectTreeObject treeObject = selectedItem.getValue();
            if (treeObject instanceof Unit) {
                choseUnit = (Unit) treeObject;
                coverageChoiceBox.setDisable(false);
                coverageChoiceBox.setValue("");
                generateButton.setDisable(true);
            } else {
                choseUnit = null;
                coverageChoiceBox.setDisable(true);
                coverageChoiceBox.setValue("");
                generateButton.setDisable(true);
            }
        }
    }

    private void selectCoverage(ActionEvent actionEvent) {
        generateButton.setDisable(false);

        String coverage = coverageChoiceBox.getValue();
        if(coverage.equals("Statement coverage")) {
            choseCoverage = Coverage.STATEMENT;
        } else if (coverage.equals("Branch coverage")) {
            choseCoverage = Coverage.BRANCH;
        } else if (coverage.equals("")) {
            // do nothing!
        } else {
            throw new RuntimeException("Invalid coverage");
        }
    }

    @FXML
    void generateButtonClicked(MouseEvent event) throws IOException, NoSuchFieldException, ClassNotFoundException, InterruptedException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        resetTestCaseDetailVBox();

        ConcolicTestResult result = ConcolicTesting.runFullConcolic(choseUnit.getPath(), choseUnit.getMethodName(), choseUnit.getClassName(), choseCoverage);

        testCaseListView.getItems().clear();
        testCaseListView.getItems().addAll(result.getFullTestData());
    }

    private void setTestCaseDetail(ConcolicTestData testData) {
        testCaseIDLabel.setText("   Test case ID: " + testData.getTestCaseID());
        functionStatementCoverageLabel.setText("   Function statement coverage: " + testData.getFunctionCoverage());
        requireCoverageLabel.setText("   Required coverage: " + testData.getRequiredCoverage());
        executeTimeLabel.setText("   Execute time: " + testData.getExecuteTime());
        outputLabel.setText("   Output: " + testData.getOutput());
        statusLabel.setText("   Status: " + testData.getStatus());

        generatedTestDataListView.getItems().clear();
        generatedTestDataListView.getItems().addAll(testData.getParameterDataList());
    }
}
