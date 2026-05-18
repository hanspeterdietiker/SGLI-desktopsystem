package org.desktop.system.sgli.sgli.Components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import java.util.function.Consumer;

public class ActionTableCell<T> extends TableCell<T, Void> {
    private final HBox container;

    public ActionTableCell(Consumer<T> onEdit, Consumer<T> onDelete) {
        Button editBtn = new Button("Editar");
        editBtn.getStyleClass().add("edit-button");

        Button deleteBtn = new Button("Deletar");
        deleteBtn.getStyleClass().add("delete-button");


        editBtn.setOnAction(event -> {
            T item = getTableView().getItems().get(getIndex());
            if (item != null) {
                onEdit.accept(item);
            }
        });


        deleteBtn.setOnAction(event -> {
            T item = getTableView().getItems().get(getIndex());
            if (item != null) {
                onDelete.accept(item);
            }
        });

        container = new HBox(10, editBtn, deleteBtn);
        container.setAlignment(Pos.CENTER);
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(empty ? null : container);
    }
}