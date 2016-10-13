package com.proud.console.util.view;

import com.proud.console.FxUtilTest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for ComboBoxFX utils
 * Created by Fabrizio Faustinoni on 13/10/2016.
 */
public class ComboFxUtilTest extends FxUtilTest {
    private static ObservableList<String> options = FXCollections.observableArrayList("AAAAA","BBBB", "CCCC");

    @Test
    public void autoCompleteComboBoxPlus() throws Exception {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().setAll(options);
        comboBox.setPromptText("Choose");
        ComboFxUtil.autoCompleteComboBoxPlus(
                comboBox,
                (typedText, itemToCompare) -> itemToCompare.toString().toLowerCase().contains(typedText.toLowerCase()));

        assertTrue(comboBox.isEditable());
    }

    @Test
    public void getComboBoxValue() throws Exception {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().setAll(options);
        comboBox.setPromptText("Choose");
        ComboFxUtil.autoCompleteComboBoxPlus(
                comboBox,
                (typedText, itemToCompare) -> itemToCompare.toString().toLowerCase().contains(typedText.toLowerCase()));

        comboBox.getSelectionModel().select(0);
        assertEquals(options.get(0),  ComboFxUtil.getComboBoxValue(comboBox));

    }

}