package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.utils.Utils;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.List;

import static com.katyshevtseva.vocabularyapp.utils.Constants.*;

class CatalogueTuner {
    private static CatalogueTuner instance;
    private GridPane catalogueTable;
    private List<String> catalogue;
    private ScrollPane cataloguePlacement;

    static void create(ScrollPane cataloguePlacement) {
        instance = new CatalogueTuner(cataloguePlacement);
    }

    public static CatalogueTuner getInstance() {
        return instance;
    }

    private CatalogueTuner(ScrollPane cataloguePlacement) {
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

    void updateCatalogue() {
        cataloguePlacement.setContent(getUpdatedCatalogueTable());
    }

    private GridPane getUpdatedCatalogueTable() {
        updateCatalogueTable();
        return catalogueTable;
    }

    private void updateCatalogueTable() {
        catalogue = MainController.getDataBase().getCatalogue();
        catalogueTable.getChildren().clear();
        for (int listIndex = 0; listIndex < catalogue.size(); listIndex++) {
            tuneCatalogueTableRow(listIndex);
        }
    }

    private void tuneCatalogueTableRow(int listIndex) {
        Label label = new Label(catalogue.get(listIndex));
        Button deleteButton = new Button();
        Utils.setImageOnButton(RED_CROSS_IMAGE_NAME, deleteButton, BUTTON_IMAGE_SIZE);
        deleteButton.setTooltip(new Tooltip("delete list"));
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> openWordList(listIndex));
        deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> deleteList(listIndex));
        catalogueTable.add(label, 0, listIndex);
        catalogueTable.add(deleteButton, 1, listIndex);
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
