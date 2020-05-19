package com.katyshevtseva.vocabularyapp.controller;

class ListDeletionController implements AnswerReceiver {
    private String listToDelete;

    void deleteList(String listToDelete) {
        this.listToDelete = listToDelete;
        QuestionController.askQuestion("Are you sure you want to delete the list?", this);
    }

    @Override
    public void receivePositiveAnswer() {
        deleteList();
    }

    private void deleteList() {
        MainController.getDataBase().deleteList(listToDelete);
        CatalogueTuner.getInstance().updateCatalogue();
    }
}
