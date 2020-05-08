package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.model.DataBase;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.List;

import static com.katyshevtseva.vocabularyapp.utils.Constants.*;

class CatalogueTuner {
    private static CatalogueTuner instance;
    private DataBase dataBase;
    private GridPane catalogueTable;
    private List<String> catalogue;
    private ScrollPane cataloguePlacement;

    public static void create(DataBase dataBase, ScrollPane cataloguePlacement) {
        instance = new CatalogueTuner(dataBase, cataloguePlacement);
    }

    public static CatalogueTuner getInstance() {
        return instance;
    }

    private CatalogueTuner(DataBase dataBase, ScrollPane cataloguePlacement) {
        this.dataBase = dataBase; //todo ну теперь то мы можем получить бд и не передавая ее через аргументы
        this.cataloguePlacement = cataloguePlacement;
        catalogueTable = createAndTuneCatalogueTable();
    }

    private GridPane createAndTuneCatalogueTable() {
        GridPane catalogueTable = new GridPane();
        catalogueTable.setVgap(CATALOGUE_TABLE_V_GAP);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPrefWidth(CATALOGUE_TABLE_WIDTH);
        catalogueTable.getColumnConstraints().add(columnConstraints);
        return catalogueTable;
    }

    public void updateCatalogue() {
        cataloguePlacement.setContent(getUpdatedCatalogueTable());
    }

    private GridPane getUpdatedCatalogueTable() {
        updateCatalogueTable();
        return catalogueTable;
    }

    private void updateCatalogueTable() {
        catalogue = dataBase.getCatalogue();
        catalogueTable.getChildren().clear();
        for (int listIndex = 0; listIndex < catalogue.size(); listIndex++) {
            tuneCatalogueTableRow(listIndex);
        }
    }

    private void tuneCatalogueTableRow(int listIndex) {
        Label label = new Label(catalogue.get(listIndex));
        Button deleteBtn = new Button("", getListDeletionIcon());
        deleteBtn.setTooltip(new Tooltip("delete list"));
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> openWordList(listIndex));
        deleteBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> deleteList(listIndex));
        catalogueTable.add(label, 0, listIndex);
        catalogueTable.add(deleteBtn, 1, listIndex);
    }

    private ImageView getListDeletionIcon() {  //todo мб когда-нибудь вынесем это в утилитные методы
        Image image = new Image(IMAGES_PATH + LIST_DELETION_IMAGE_NAME);
        ImageView imageViewWithRedCross = new ImageView(image);
        imageViewWithRedCross.setFitHeight(BUTTON_IMAGE_SIZE);
        imageViewWithRedCross.setFitWidth(BUTTON_IMAGE_SIZE);
        return imageViewWithRedCross;
    }

    private void deleteList(int listIndex) {
        String listToDelete = catalogue.get(listIndex);
        ListDeletionController.deleteWordList(listToDelete);
    }

    private void openWordList(int listIndex) {
        String listName = catalogue.get(listIndex);
        ListController.showWordList(listName);
    }
}
