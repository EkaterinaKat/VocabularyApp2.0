package com.katyshevtseva.vocabularyapp.controller;

import com.katyshevtseva.vocabularyapp.model.DataBase;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.List;

import static com.katyshevtseva.vocabularyapp.utils.Constants.*;

class CatalogueTuner {
    private DataBase dataBase;
    private GridPane catalogueTable;
    private List<String> catalogue;

    CatalogueTuner(DataBase dataBase) {
        this.dataBase = dataBase;
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

    public GridPane getUpdatedCatalogueTable() {
        updateCatalogueTable();
        return catalogueTable;
    }

    private void updateCatalogueTable() {
        catalogue = dataBase.getCatalogue();
        for (int listIndex = 0; listIndex < catalogue.size(); listIndex++) {
            tuneCatalogueTableRow(listIndex);
        }
    }

    private void tuneCatalogueTableRow(int listIndex) {
        Label label = new Label(catalogue.get(listIndex));
        Button deleteBtn = new Button("", getListDeletionIcon());
        deleteBtn.setTooltip(new Tooltip("delete list"));
        int finalListIndex = listIndex;  //todo warning
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> { //todo warning
            openWordListWindow(finalListIndex);
        });
        deleteBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> { //todo warning
            openListDeletionWindow(finalListIndex);
        });
        catalogueTable.add(label, 0, listIndex);
        catalogueTable.add(deleteBtn, 1, listIndex);
    }

    private ImageView getListDeletionIcon() {  //todo мб когда-нибудь вынесем это в утилитные методы
        Image image = new Image(IMAGES_PATH + LIST_DELETION_IMAGE_NAME);
        ImageView imageViewWithRedCross = new ImageView(image);
        imageViewWithRedCross.setFitHeight(LIST_DELETION_ICON_SIZE);
        imageViewWithRedCross.setFitWidth(LIST_DELETION_ICON_SIZE);
        return imageViewWithRedCross;
    }

    private void openListDeletionWindow(int listIndex) {
//        RemoveListController.mainController = this;  //todo
//        RemoveListController.listName = catalogue.get(listIndex);
//        WindowCreator.getInstance().createModalWindow("remove_list_sample.fxml",
//                LIST_DELETION_WINDOW_TITLE, LIST_DELETION_WINDOW_WIDTH, LIST_DELETION_WINDOW_HEIGHT, false);
    }

    private void openWordListWindow(int listIndex) {
//        WordListController.nameOfList = catalogue.get(listIndex); //todo
//        WindowCreator.getInstance().createModalWindow("word_list_sample.fxml",
//                catalogue.get(listIndex), WORD_LIST_WINDOW_WIDTH, WORD_LIST_WINDOW_HEIGHT, true);
    }
}
