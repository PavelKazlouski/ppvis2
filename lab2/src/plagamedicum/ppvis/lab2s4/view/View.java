package plagamedicum.ppvis.lab2s4.view;
import java.time.LocalDate;

import com.intellij.codeInspection.htmlInspections.HtmlExtraClosingTagInspection;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import plagamedicum.ppvis.lab2s4.Controller.Controller;
import plagamedicum.ppvis.lab2s4.model.Exam;
import plagamedicum.ppvis.lab2s4.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class View {
    private final String REGEX_DIGITS_ONLY = "^\\d+$";
    private Scene scene;
	private TableElement tableElement;
    private Controller controller;
    private Stage stage;
    private VBox root;
    private enum WindowType {
        DELETE, SEARCH
    }

	public View(Controller controller) {
        final int    STAGE_WIDTH  = 1460,
                     STAGE_HEIGHT = 780;
        final String STAGE_TITLE_TEXT = "Lab2";

        this.controller = controller;
        initWindow();
        stage = new Stage();
        stage.setWidth (STAGE_WIDTH);
        stage.setHeight(STAGE_HEIGHT);
        stage.setTitle (STAGE_TITLE_TEXT);
        stage.setScene(scene);
	}

	private void initWindow(){
        final String FILE_MENU_LABEL_TEXT              = "Файл";
        final String EDIT_MENU_LABEL_TEXT              = "Редактировать";
        final String NEW_DOC_MENU_ITEM_LABEL_TEXT      = "Новыый документ";
        final String OPEN_DOC_MENU_ITEM_LABEL_TEXT     = "Открыть документ";
        final String SAVE_DOC_MENU_ITEM_LABEL_TEXT     = "Сохранить документ";
        final String ADD_ITEM_MENU_ITEM_LABEL_TEXT     = "Добавить строки";
        final String SEARCH_ITEMS_MENU_ITEM_LABEL_TEXT = "Искать строки";
        final String DELETE_ITEMS_MENU_ITEM_LABEL_TEXT = "Удалить строки";
        final String CLOSE_APP_MENU_ITEM_LABEL_TEXT    = "Выйти";
        final String NEW_DOC_BUTTON_LABEL_TEXT         = "Новый документ";
        final String OPEN_DOC_BUTTON_LABEL_TEXT        = "Открыть документ";
        final String SAVE_DOC_BUTTON_LABEL_TEXT        = "Сохранить документ";
        final String ADD_ITEMS_BUTTON_LABEL_TEXT       = "Добавить строки";
        final String SEARCH_ITEMS_BUTTON_LABEL_TEXT    = "Искать строки";
        final String DELETE_ITEMS_BUTTON_LABEL_TEXT    = "Удалить строки";
        MenuItem newDocMenuItem = new MenuItem(NEW_DOC_MENU_ITEM_LABEL_TEXT);
        MenuItem openDocMenuItem = new MenuItem(OPEN_DOC_MENU_ITEM_LABEL_TEXT);
        MenuItem saveMenuItem = new MenuItem(SAVE_DOC_MENU_ITEM_LABEL_TEXT);
        MenuItem addItemsMenuItem = new MenuItem(ADD_ITEM_MENU_ITEM_LABEL_TEXT);
        MenuItem searchItemsMenuItem = new MenuItem(SEARCH_ITEMS_MENU_ITEM_LABEL_TEXT);
        MenuItem deleteItemsMenuItem = new MenuItem(DELETE_ITEMS_MENU_ITEM_LABEL_TEXT);
        MenuItem closeAppMenuItem = new MenuItem(CLOSE_APP_MENU_ITEM_LABEL_TEXT);
        Menu fileMenu = new Menu(FILE_MENU_LABEL_TEXT);
        Menu editMenu = new Menu(EDIT_MENU_LABEL_TEXT);
        MenuBar menuBar = new MenuBar();
        Button newDocButton = new Button(NEW_DOC_BUTTON_LABEL_TEXT);
        Button openDocButton = new Button(OPEN_DOC_BUTTON_LABEL_TEXT);
        Button saveDocButton = new Button(SAVE_DOC_BUTTON_LABEL_TEXT);
        Button addItemsButton = new Button(ADD_ITEMS_BUTTON_LABEL_TEXT);
        Button searchItemsButton = new Button(SEARCH_ITEMS_BUTTON_LABEL_TEXT);
        Button deleteItemsButton = new Button(DELETE_ITEMS_BUTTON_LABEL_TEXT);
        ToolBar  instruments;

        fileMenu.getItems().addAll(newDocMenuItem,
                openDocMenuItem,
                saveMenuItem,
                new SeparatorMenuItem(),
                closeAppMenuItem);
        editMenu.getItems().addAll(
                addItemsMenuItem,
                new SeparatorMenuItem(),
                searchItemsMenuItem,
                deleteItemsMenuItem);
        menuBar.getMenus().addAll(
                fileMenu,
                editMenu);

        instruments = new ToolBar(
                newDocButton,
                openDocButton,
                saveDocButton,
                new Separator(),
                addItemsButton,
                searchItemsButton,
                deleteItemsButton);

        tableElement = new TableElement(controller.getPatientList());

        root = new VBox();
        root.getChildren().addAll(
                menuBar,
                instruments,
                tableElement.get());
        scene = new Scene(root);

        newDocButton.setOnAction(ae -> newDoc());
            newDocMenuItem.setOnAction(ae -> newDoc());
        openDocButton.setOnAction(ae -> openDoc());
            openDocMenuItem.setOnAction(ae -> openDoc());
        saveDocButton.setOnAction(ae -> saveDoc());
            saveMenuItem.setOnAction(ae -> saveDoc());
        addItemsButton.setOnAction(ae -> addItems());
            addItemsMenuItem.setOnAction(ae -> addItems());
        searchItemsButton.setOnAction(ae -> searchItems());
            searchItemsMenuItem.setOnAction(ae -> searchItems());
        deleteItemsButton.setOnAction(ae -> deleteItems());
            deleteItemsMenuItem.setOnAction(ae -> deleteItems());
        closeAppMenuItem.setOnAction(ae -> Platform.exit());
    }

	public Stage getStage(){
	    return stage;
    }

	private void newDoc(){
	    final String ENTRY_NUM_LABEL_TEXT = "сгенерировать записей: ";
	    final String NEW_DOC_WINDOW_TITLE_TEXT = "Создать новвый документ";
        final String INFO_LABEL_TEXT = "\nКогда количество сгенерированых записей\n" +
                "не будет введено пользователем оно будет равно\n" +
                "изначальному значению.\n ";
	    TextField entNumField = new TextField("0");
        GridPane grid = new GridPane();
        Pane root = new VBox();
	    Alert newDocWindow;
        grid.addRow(0,
                new Label(ENTRY_NUM_LABEL_TEXT),
                entNumField
        );
        root.getChildren().addAll(
                grid,
                new Label(INFO_LABEL_TEXT)
        );

	    newDocWindow = createEmptyCloseableDialog();
	    newDocWindow.setTitle(NEW_DOC_WINDOW_TITLE_TEXT);
        newDocWindow.getDialogPane().setContent(root);
	    newDocWindow.show();

        ((Button)newDocWindow.getDialogPane().lookupButton(newDocWindow.getButtonTypes().get(0))).setOnAction(ae->{
            int entitiesNumber = 0;

            if(!entNumField.getText().isEmpty() & entNumField.getText().matches(REGEX_DIGITS_ONLY)){
                entitiesNumber = Integer.valueOf(entNumField.getText());
            }
            controller.newDoc(entitiesNumber);

            this.root.getChildren().remove(tableElement.get());
            tableElement = new TableElement(controller.getPatientList());
            this.root.getChildren().addAll(
                    tableElement.get()
            );

	        newDocWindow.close();
        });
    }

    private void openDoc(){
        FileChooser openDocChooser = new FileChooser();

        openDocChooser.setTitle("Открыть документ");
        openDocChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Все файлы", "*.*"),
                new FileChooser.ExtensionFilter("XML-документ", "*.xml")
        );

        try {
            controller.openDoc(openDocChooser.showOpenDialog(stage));
        } catch (Exception exception){
            exception.printStackTrace();
        }

        tableElement.rewriteDefaultList(controller.getPatientList());
        tableElement.resetToDefaultItems();
    }

    private void saveDoc(){
        FileChooser saveDocChooser = new FileChooser();

        saveDocChooser.setTitle("Сохранить документ");
        saveDocChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Все файлы", "*.*"),
                new FileChooser.ExtensionFilter("XML-документ", "*.xml")
        );

        controller.saveDoc(saveDocChooser.showSaveDialog(stage));
    }

	private void addItems(){
        final String WINDOW_TITLE_TEXT = "Добавить строки: ";
        final String SURNAME_LABEL_TEXT = "Фамилия: ";
        final String NAME_LABEL_TEXT = "Имя: ";
        final String PATRONYM_LABEL_TEXT = "Отчество: ";
        final String ADDRESS_LABEL_TEXT = "Адрес: ";
        final String BIRTH_DATE_LABEL_TEXT = "Дата рождения: ";
        final String DOCTOR_SURNAME_LABEL_TEXT = "Фамилия врача: ";
        final String DOCTOR_NAME_LABEL_TEXT = "Имя врача: ";
        final String DOCTOR_PATRONYM_LABEL_TEXT = "Отчество врача: ";
        final String DATE_OF_RECEIPT_LABEL_TEXT = "Дата приема: ";
        final String CONCLUSION_LABEL_TEXT = "Заключение: ";
        TextField surnameField  = new TextField();
        TextField doctorSurnameField  = new TextField();
        TextField doctorNameField = new TextField();
        TextField nameField = new TextField();
        TextField doctorPatronymField = new TextField();
        TextField patronymField = new TextField();
        TextField conclusionField = new TextField();
        TextField addressField = new TextField();
        DatePicker birthDate = new DatePicker();
        DatePicker dateOfReceipt = new DatePicker();
	    GridPane root = new GridPane();
        Alert addItemWindow;

        root.addRow(0,
                new Label(SURNAME_LABEL_TEXT),
                surnameField
        );
        root.addRow(1,
                new Label(NAME_LABEL_TEXT),
                nameField
        );
        root.addRow(2,
                new Label(PATRONYM_LABEL_TEXT),
                patronymField
        );
        root.addRow(3,
                new Label(ADDRESS_LABEL_TEXT),
                addressField
        );
        root.addRow(4,
                new Label(BIRTH_DATE_LABEL_TEXT),
                birthDate
        );
        root.addRow(5,
                new Label(DOCTOR_SURNAME_LABEL_TEXT),
                doctorSurnameField
        );
        root.addRow(6,
                new Label(DOCTOR_NAME_LABEL_TEXT),
                doctorNameField
        );
        root.addRow(7,
                new Label(DOCTOR_PATRONYM_LABEL_TEXT),
                doctorPatronymField
        );
        root.addRow(8,
                new Label(DATE_OF_RECEIPT_LABEL_TEXT),
                dateOfReceipt
        );
        root.addRow(9,
                new Label(CONCLUSION_LABEL_TEXT),
                conclusionField
        );

        addItemWindow = createEmptyCloseableDialog();
        addItemWindow.setTitle(WINDOW_TITLE_TEXT);
        addItemWindow.getDialogPane().setContent(root);
        addItemWindow.show();

        ((Button)addItemWindow.getDialogPane().lookupButton(addItemWindow.getButtonTypes().get(0))).setOnAction(ae->{
            controller.addPatient(
                    surnameField.getText(),
                    nameField.getText(),
                    patronymField.getText(),
                    birthDate.valueProperty().get(),
                    addressField.getText(),
                    dateOfReceipt.valueProperty().get(),
                    conclusionField.getText(),
                    doctorNameField.getText(),
                    doctorSurnameField.getText(),
                    doctorPatronymField.getText()
            );
            tableElement.resetToDefaultItems();
            addItemWindow.close();
        });
    }

    private class RequestElement{
        final String CRITERIA_1 = "Фамилия пациента или адрес прописки";
        final String CRITERIA_2 = "Дата рождения";
        final String CRITERIA_3 = "ФИО врача или дата последнего приема";
        private String selectedItem;
        private ComboBox criteriaComBox;
        private Button searchButton;
        private TableElement tableElement;
        private GridPane grid;
        private Pane criteriaChooser;
        private Pane root;
        private List<Label> criteria1LabelList;
        private List<Label> criteria2LabelList;
        private List<Label> criteria3LabelList;
        private List<TextField> criteria1FieldList;
        private List<DatePicker> criteria2DateList;
        private List<DatePicker> criteria3DateList;
        private List<TextField> criteria3FieldList;
        public RequestElement(WindowType windowType){
            criteriaComBox = new ComboBox();
            criteriaComBox.getItems().addAll(
                    CRITERIA_1,
                    CRITERIA_2,
                    CRITERIA_3
            );
            criteriaComBox.setValue(CRITERIA_1);
            searchButton = new Button("Искать");
            criteriaChooser = new HBox();

            criteria1LabelList = new ArrayList<>();
            criteria1FieldList = new ArrayList<>();
            criteria2LabelList = new ArrayList<>();
            criteria2DateList = new ArrayList<>();
            criteria3LabelList = new ArrayList<>();
            criteria3FieldList = new ArrayList<>();
            criteria3DateList = new ArrayList<>();
            initCriteriaLists();
            grid = new GridPane();
            switchPreset();

            tableElement = new TableElement(new ArrayList<>());

            this.root = new VBox();

            if(windowType == WindowType.SEARCH){
                criteriaChooser.getChildren().addAll(
                        new Label("Критерий поиска: "),
                        criteriaComBox,
                        searchButton
                );

                this.root.getChildren().addAll(
                        new Separator(),
                        new Separator(),
                        criteriaChooser,
                        grid,
                        new Separator(),
                        tableElement.get(),
                        new Separator(),
                        new Separator(),
                        new Separator(),
                        new Separator()
                );
            }

            if(windowType == WindowType.DELETE){
                criteriaChooser.getChildren().addAll(
                        new Label("Критерий поиска: "),
                        criteriaComBox
                );

                this.root.getChildren().addAll(
                        new Separator(),
                        new Separator(),
                        criteriaChooser,
                        grid
                );
            }


            criteriaComBox.setOnAction(ae -> switchPreset());
            searchButton.setOnAction(ae->{
                List<Patient> patientList = search();

                tableElement.setObservableList(patientList);
            });
        }

        private void switchPreset(){
            final int CRITERIA_1_FIELD_NUMBER = 2;
            final int CRITERIA_2_FIELD_NUMBER = 1;
            final int CRITERIA_3_FIELD_NUMBER = 3;

            grid.getChildren().clear();
            selectedItem = criteriaComBox.getSelectionModel().getSelectedItem().toString();
            switch (selectedItem){
                case CRITERIA_1:
                    for(int i = 0; i < CRITERIA_1_FIELD_NUMBER; i++){
                        grid.addRow(i,
                                criteria1LabelList.get(i),
                                criteria1FieldList.get(i)
                        );
                    }
                    break;
                case CRITERIA_2:
                    for(int i = 0; i < CRITERIA_2_FIELD_NUMBER; i++){
                        grid.addRow(i,
                                criteria2LabelList.get(i),
                                criteria2DateList.get(i)
                        );
                    }
                    break;
                case CRITERIA_3:
                    for(int i = 0; i < CRITERIA_3_FIELD_NUMBER; i++){
                        grid.addRow(i,
                                criteria3LabelList.get(i),
                                criteria3FieldList.get(i)
                        );
                    }
                    grid.addRow(3,
                            criteria3LabelList.get(3),
                            criteria3DateList.get(0)
                    );
                    break;
            }
        }

        private void initCriteriaLists(){
            final String SURNAME_LABEL_TEXT = "Фамилия пациента: ";
            final String ADDRESS_LABEL_TEXT = "Адрес прописки: ";
            final String BIRTH_DATE_LABEL_TEXT = "Дата рождения: ";
            final String DOCTOR_NAME_LABEL_TEXT = "Имя доктора: ";
            final String DOCTOR_SURNAME_LABEL_TEXT = "Фамилия доктора: ";
            final String DOCTOR_PATRONYM_LABEL_TEXT = "Отчество доктора: ";
            final String DATE_OF_RECEPT_LABEL_TEXT = "Дата последнего посещения: ";

            criteria1LabelList.add(new Label(ADDRESS_LABEL_TEXT));
            criteria1LabelList.add(new Label(SURNAME_LABEL_TEXT));
            criteria1FieldList.add(new TextField());
            criteria1FieldList.add(new TextField());
            criteria2LabelList.add(new Label(BIRTH_DATE_LABEL_TEXT));
            criteria2DateList.add(new DatePicker());
            criteria3LabelList.add(new Label(DOCTOR_SURNAME_LABEL_TEXT));
            criteria3LabelList.add(new Label(DOCTOR_NAME_LABEL_TEXT));
            criteria3LabelList.add(new Label(DOCTOR_PATRONYM_LABEL_TEXT));
            criteria3LabelList.add(new Label(DATE_OF_RECEPT_LABEL_TEXT));
            criteria3FieldList.add(new TextField());
            criteria3FieldList.add(new TextField());
            criteria3FieldList.add(new TextField());
            criteria3DateList.add(new DatePicker());
        }

        public Pane get(){
            return this.root;
        }

        public List search(){
            List criteriaList;
            criteriaList = new ArrayList<String >();
            criteriaList.add(criteria1FieldList.get(0).getText());
            criteriaList.add(criteria1FieldList.get(1).getText());
            criteriaList.add(criteria2DateList.get(0).toString());
            criteriaList.add(criteria3FieldList.get(0).getText());
            criteriaList.add(criteria3FieldList.get(1).getText());
            criteriaList.add(criteria3FieldList.get(2).getText());
            criteriaList.add(criteria3DateList.get(0).toString());

            return controller.search(selectedItem, criteriaList);
        }
    }

    private void searchItems(){
        final String WINDOW_TITLE_TEXT = "Искать строки";
        Alert searchItemsWindow;
        RequestElement requestElement = new RequestElement(WindowType.SEARCH);

        searchItemsWindow = createEmptyCloseableDialog();
        searchItemsWindow.setTitle(WINDOW_TITLE_TEXT);
        searchItemsWindow.getDialogPane().setContent(requestElement.get());
        searchItemsWindow.show();

        ((Button)searchItemsWindow.getDialogPane().lookupButton(searchItemsWindow.getButtonTypes().get(0))).setOnAction(
                ae-> searchItemsWindow.close()
        );
    }

    private void deleteItems(){
        final String WINDOW_TITLE_TEXT = "Удалить строки";
        Alert deleteItemsWindow;
        RequestElement requestElement = new RequestElement(WindowType.DELETE);

        deleteItemsWindow = createEmptyCloseableDialog();
        deleteItemsWindow.setTitle(WINDOW_TITLE_TEXT);
        deleteItemsWindow.getDialogPane().setContent(requestElement.get());
        deleteItemsWindow.setHeight(500);
        deleteItemsWindow.setWidth(500);
        deleteItemsWindow.show();

        ((Button)deleteItemsWindow.getDialogPane().lookupButton(deleteItemsWindow.getButtonTypes().get(0))).setOnAction(ae->{
            createDeleteInfoWindow(String.valueOf(requestElement.search().size()));
            controller.delete(requestElement.search());
            tableElement.resetToDefaultItems();
            deleteItemsWindow.close();
        });
    }

    private void createDeleteInfoWindow(String deleteInfo){
        final String CLOSE_BUTTON_LABEL_TEXT = "ОК";
        ButtonType   closeButton       = new ButtonType(CLOSE_BUTTON_LABEL_TEXT);
	    Alert window  = new Alert(Alert.AlertType.NONE);
	    VBox  vertice = new VBox();

	    vertice.getChildren().add(new Label("Удолено " + deleteInfo + " строк."));
	    window.getDialogPane().setContent(vertice);
        window.getButtonTypes().addAll(closeButton);
        window.show();
    }

    private Alert createEmptyCloseableDialog(){
        final String CLOSE_BUTTON_LABEL_TEXT = "Дальше";
        ButtonType   closeButton       = new ButtonType(CLOSE_BUTTON_LABEL_TEXT);
        Alert        window            = new Alert(Alert.AlertType.NONE);

        window.getButtonTypes().addAll(closeButton);
        window.setHeight(500);
        window.setWidth(500);
        return window;
    }
}
