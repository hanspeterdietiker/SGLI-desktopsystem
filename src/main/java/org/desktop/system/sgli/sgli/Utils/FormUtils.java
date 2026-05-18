package org.desktop.system.sgli.sgli.Utils;

import javafx.scene.Node;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.TextInputControl;

public class FormUtils {

    private FormUtils() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não pode ser instanciada");
    }


    public static void clearFields(Node... nodes) {
        for (Node node : nodes) {
            if (node instanceof TextInputControl textInput) {
                textInput.clear();
            } else if (node instanceof ComboBoxBase<?> comboBoxBase) {
                comboBoxBase.setValue(null);
            }
        }
    }
}