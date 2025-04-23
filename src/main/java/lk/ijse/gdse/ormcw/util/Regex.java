package lk.ijse.gdse.ormcw.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextField textField ,String text) {
        String filed = switch (textField){
            case NAME ->  "^[A-Za-z]+(?: [A-Za-z]+)*$";
            case AGE -> "^[1-9][0-9]?$";
            case TEL -> "^[0]([1-9]{2})([0-9]){7}$";
            case DURATION -> "\\d{1,2}\\s?(month|year)s?";
            case TIME -> "^(0[1-9]|1[0-2]):[0-5][0-9]\\s?(AM|PM)$\n";

            case FEE -> "^([0-9]){1,}[.]([0-9]){1,}$";
            case PASSWORD -> "^(?! *$)[^\\s]+$";
        };
        Pattern pattern = Pattern.compile(filed);

        if(text !=null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }
        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;

    }
    public static boolean setTextColor(TextField location, javafx.scene.control.TextField textField) {
        if (lk.ijse.gdse.ormcw.util.Regex.isTextFieldValid(location, textField.getText())) {
            textField.setStyle("-fx-border-color: transparent; -fx-border-radius: 0; -fx-border-width: 0; -fx-focus-color: transparent;");
            return true;
        } else {
            textField.setStyle("-fx-border-color: red; -fx-border-radius: 5; -fx-border-width: 3;");
            return false;
        }
    }
}
