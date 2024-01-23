# NTD-AUT: A Non-Test Driver Concolic-Based Unit Testing Method for Java Projects
## For Dev
### Requirements
- [Install Scene Builder (Version: 21.0.0)](https://gluonhq.com/products/scene-builder/)
- [Download JavaFX SDK (Version: 17.0.10, Type: SDK)](https://gluonhq.com/products/javafx/)
### How to Run?
1. Clone and Open the project in IntelliJ IDEA
2. Add JavaFX SDK to the project (File -> Project Structure -> Libraries -> Add -> Java -> Select the lib folder of JavaFX SDK)
3. Edit Configuration 
   - New Configuration -> Application
   - Main class: `gui.Main`
   - Modify options -> Add VM options: `--module-path <path-to-lib-folder-of-javafx-sdk> --add-modules javafx.controls,javafx.fxml` (TODO: Change the path to your JavaFX SDK lib folder)
4. Run the project

### How to using Scene Builder to edit GUI?
1. For the first time, you need to add the path to Scene Builder: File -> Settings -> Languages & Frameworks -> JavaFX -> Scene Builder executable path (TODO: Change the path to your SceneBuilder.exe file)
2. Choosing the .fxml file that you want to edit in the project
3. Open context menu and select `Open in Scene Builder`