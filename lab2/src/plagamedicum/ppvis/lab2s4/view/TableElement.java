package plagamedicum.ppvis.lab2s4.view;
import java.time.LocalDate;
import gherkin.lexer.Pa;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import plagamedicum.ppvis.lab2s4.model.Patient;

public class TableElement {
    private int rowsOnPage;
    private int currentPage = 1;
    private int numberOfPages;
    private Label paginationLabel;
    private Label itemsCountLabel;
    private Button resetSearchButton;
    private TextField rowsOnPageField;
    private TableView<Patient> table;
    private ToolBar navigator;
    private ToolBar pagination;
    private Pane tableElement;
    private List<Patient> defaultPatientList;
    private ObservableList<Patient> patientObsList;
    private ObservableList<Patient> curPatientObsList;

    public TableElement(List<Patient> patientList){
        final int TABLE_HEIGHT = 600;
        final int TABLE_WIDTH = 1460;
        final int DEFAULT_ROWS_ON_PAGE_NUMBER = 17;
        final String PATIENT_FIO_COLUMN_LABEL_TEXT = "ФИО пациента";
        final String ADDRESS_COLUMN_LABEL_TEXT = "Адрес прописки";
        final String BERTH_DATE_COLUMN_LABEL_TEXT = "Дата рождения";
        final String DATE_OF_RECEIPT_COLUMN_LABEL_TEXT = "Дата приема";
        final String DOCTOR_FIO_COLUMN_LABEL_TEXT = "ФИО врача";
        final String CONCLUSION_COLUMN_LABEL_TEXT = "Заключение";
        final String ROWS_ON_PAGE_LABEL_TEXT = "Рядов на странице: ";
        final String TO_BEGIN_BUTTON_LABEL_TEXT = "<<";
        final String TO_LEFT_BUTTON_LABEL_TEXT = "<";
        final String TO_RIGHT_BUTTON_LABEL_TEXT = ">";
        final String TO_END_BUTTON_LABEL_TEXT = ">>";
        Property sProperty = new SimpleStringProperty();
        Button toBeginButton = new Button(TO_BEGIN_BUTTON_LABEL_TEXT);
        Button toLeftButton = new Button(TO_LEFT_BUTTON_LABEL_TEXT);
        Button toRightButton = new Button(TO_RIGHT_BUTTON_LABEL_TEXT);
        Button toEndButton = new Button(TO_END_BUTTON_LABEL_TEXT);
        TableColumn<Patient, String> patFioCol = new TableColumn<>(PATIENT_FIO_COLUMN_LABEL_TEXT);
        TableColumn<Patient, String> addressCol = new TableColumn<>(ADDRESS_COLUMN_LABEL_TEXT);
        TableColumn<Patient, String> berdateCol = new TableColumn<>(BERTH_DATE_COLUMN_LABEL_TEXT);
        TableColumn<Patient, String> recdateCol = new TableColumn<>(DATE_OF_RECEIPT_COLUMN_LABEL_TEXT);
        TableColumn<Patient, String> dokFioCol = new TableColumn<>(DOCTOR_FIO_COLUMN_LABEL_TEXT);
        TableColumn<Patient, String> concCol = new TableColumn<>(CONCLUSION_COLUMN_LABEL_TEXT);

        defaultPatientList = patientList;
        patientObsList = FXCollections.observableArrayList(defaultPatientList);
        curPatientObsList = FXCollections.observableArrayList();

        patFioCol.setMinWidth(200);
        patFioCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("alignSnp"));
        addressCol.setMinWidth(200);
        addressCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("address"));
        berdateCol.setMinWidth(200);
        berdateCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("alignBd"));
        recdateCol.setMinWidth(200);
        recdateCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("alignRd"));
        dokFioCol.setMinWidth(200);
        dokFioCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("alignVrach"));
        concCol.setMinWidth(400);
        concCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("conclusion"));


        paginationLabel = new Label();
        navigator = new ToolBar(
                toBeginButton,
                toLeftButton,
                paginationLabel,
                toRightButton,
                toEndButton
        );

        itemsCountLabel = new Label("/" + patientObsList.size() + "/");
        rowsOnPageField = new TextField();
        rowsOnPageField.setText(String.valueOf(DEFAULT_ROWS_ON_PAGE_NUMBER));
        resetSearchButton = new Button("Сбросить поиск");
        resetSearchButton.setVisible(false);
        pagination = new ToolBar(
                itemsCountLabel,
                new Separator(),
                new Label(ROWS_ON_PAGE_LABEL_TEXT),
                rowsOnPageField,
                new Separator(),
                navigator,
                resetSearchButton
        );

        table = new TableView<>();
        table.setMinHeight(TABLE_HEIGHT);
        table.setMaxWidth(TABLE_WIDTH);
        table.getColumns().addAll(
                patFioCol,
                addressCol,
                berdateCol,
                recdateCol,
                dokFioCol,
                concCol
        );
        table.setItems(curPatientObsList);
        setRowsOnPage();

        tableElement = new VBox();
        tableElement.getChildren().addAll(table,
                                          pagination);

        rowsOnPageField.setOnAction(ae -> setRowsOnPage());
        toBeginButton.setOnAction(ae -> goBegin());
        toLeftButton.setOnAction(ae -> goLeft());
        toRightButton.setOnAction(ae -> goRight());
        toEndButton.setOnAction(ae -> goEnd());
        resetSearchButton.setOnAction(ae->{
            resetToDefaultItems();
            resetSearchButton.setVisible(false);
        });
    }

    public Pane get(){
        return tableElement;
    }

    public void rewriteDefaultList(List<Patient> list){
        defaultPatientList = list;
    }

    public void resetToDefaultItems(){
        setObservableList(defaultPatientList);
    }

    public void setObservableList(List<Patient> list){
        patientObsList = FXCollections.observableArrayList(list);
        resetSearchButton.setVisible(true);

        setRowsOnPage();
    }

    private void setRowsOnPage(){
        rowsOnPage = Integer.valueOf(rowsOnPageField.getText());
        currentPage = 1;

        refreshPage();
    }

    private void goBegin(){
        currentPage = 1;
        refreshPage();
    }

    private void goLeft(){
        if(currentPage > 1){
            currentPage--;
        }
        refreshPage();
    }

    private void goRight(){
        if(currentPage < numberOfPages){
            currentPage++;
        }
        refreshPage();
    }

    private void goEnd(){
        currentPage = numberOfPages;
        refreshPage();
    }

    private void refreshPage(){
        int fromIndex = (currentPage - 1) * rowsOnPage,
            toIndex   =  currentPage      * rowsOnPage;

        if(toIndex > patientObsList.size()){
            toIndex = patientObsList.size();
        }

        curPatientObsList.clear();
        curPatientObsList.addAll(
                patientObsList.subList(
                        fromIndex,
                        toIndex
                )
        );

        refreshPaginationLabel();
    }

    private void refreshPaginationLabel(){
        numberOfPages = (patientObsList.size() - 1) / rowsOnPage + 1;
        paginationLabel.setText(currentPage + "/" + numberOfPages);
        itemsCountLabel.setText("/" + patientObsList.size() + "/");
    }
}
