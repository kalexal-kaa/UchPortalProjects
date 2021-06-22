package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label t;
    @FXML
    private TextField tf;

    private int result,y,n,sk;

    public Controller(){
        super();
    }
    @FXML
    private void inspectAction(){
        int us;
        try {
            us = Integer.parseInt(this.tf.getText());
        } catch (NumberFormatException e) {
            this.alertWindow("","Введите число!","Ошибка");
            this.tf.requestFocus();
            return;
        }
        if (us==result){
            this.alertWindow("ВЕРНО!", this.t.getText() + "=" + us,"Результат");
            y++;
        }else {
            this.alertWindow("НЕВЕРНО!", "Верный ответ:\n " + this.t.getText() + "=" + this.result,"Результат");
            n++;
        }
        t.setText(multiplicationGenerator());
        tf.setText("");
        tf.requestFocus();
    }
    @FXML
    private void autorInfo(){
        startDonate("Об Авторе","Автор: Алекс Мирный\nМесто работы: КБЖБ\nХотите поддержать автора?");
    }
    @FXML
    private void skip(){
     t.setText(multiplicationGenerator());
     sk++;
    }
    @FXML
    private void showResults(){
       if (y==0&&n==0&&sk==0){
               this.alertWindow("", "Пока нет результатов.", "Итоги");
           }else {
               this.alertWindow("На данный момент...", "Верных ответов: " + this.y + "\nНеверных ответов: " + this.n+"\nПропущено примеров: " + this.sk, "Итоги");
       }
    }
    private String multiplicationGenerator() {
        int a,b;
        final Random random = new Random();
        do {
            a = random.nextInt(10);
            b = random.nextInt(10);
        }while (a==0||b==0||a==1||b==1);
        this.result = a * b;
        return a + " * " + b;
    }
    private void alertWindow(final String s, final String s1,final String s2) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(s2);
        alert.setHeaderText(s);
        alert.setContentText(s1);
        alert.showAndWait();
    }
    private void startDonate(String a,String b){
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(a);
        alert.setHeaderText("");
        alert.setContentText(b);
        final Optional<ButtonType> resultAlert = alert.showAndWait();
        if (resultAlert.get() == ButtonType.OK) {
            new Donate().setVisible(true);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        t.setText(multiplicationGenerator());
        tf.requestFocus();
    }
}
