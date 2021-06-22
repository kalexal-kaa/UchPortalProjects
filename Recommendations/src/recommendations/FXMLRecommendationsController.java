/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommendations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author kaa
 */
public class FXMLRecommendationsController implements Initializable {
    
    @FXML
    private ListView<String> list;
    @FXML
    private TextArea t;
    @FXML
    ImageView iv;
    
    private final ObservableList<String> recommendationsList;

    public FXMLRecommendationsController() {
        this.recommendationsList = FXCollections.observableArrayList("внимательность", "как все успеть", "о гневе",
                "домашние задания", "как стать отличником", "общение", "развитие памяти", "плохое настроение",
                "распорядок дня", "самоуважение","о дружбе","спорт");
    }
      @FXML
    private void listItemAction() {
        int index=list.getSelectionModel().getSelectedIndex() + 1;
        t.setText(reader(index));
        showImage(index);
    }
    @FXML
    private void sourceInfo(){
        informationWindow("Тексты взяты с сайтов:\nvashechudo.ru, ped-kopilka.ru, kakprosto.ru\n"
                + "Изображения взяты с сайта yandex.ru/images/","Источники");
    }
    @FXML
    private void programInfo() {
        this.informationWindow("Название: Полезные Советы\nВерсия: 1.0", "О Программе");
    }

    @FXML
    private void autorInfo() {
        exitOrDonate(1, "Об Авторе", "", "Автор: Алексей Крючков\nМесто работы: КБЖБ\nХотите поддержать автора?");
    }
    @FXML
    private void exit() {
        exitOrDonate(0, "ВЫХОД", "Выход из программы", "Вы действительно хотите выйти из программы?");
    }
    private void showImage(int n){
        final Image i = new Image("res/images/" + n + ".png");
        this.iv.setImage(i);
    }
    private void exitOrDonate(int a,String s1,String s2,String s3){ 
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(s1);
        alert.setHeaderText(s2);
        alert.setContentText(s3);
        final Optional<ButtonType> resultAlert = alert.showAndWait();
        if (resultAlert.get() == ButtonType.OK) {
            if(a==0){
               System.exit(0);
            }else{
                new Donate().setVisible(true);
            }
        }
    }
    private String reader(final int n) {
        String f = "";
        try {
            final InputStream is = ClassLoader.getSystemResourceAsStream("res/textes/" + n);
            final InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
            try (final BufferedReader br = new BufferedReader(isr)) {
                String str;
                while ((str = br.readLine()) != null) {
                    f = f + str + "\n";
                }
            }
        }
        catch (IOException e) {
            e.getMessage();
        }
        return f;
    }

    private void informationWindow(final String s, final String str) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(str);
        alert.setHeaderText("");
        alert.setContentText(s);
        alert.showAndWait();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.list.setItems(this.recommendationsList);
        this.t.setText("ДОБРО ПОЖАЛОВАТЬ!!!");
        this.showImage(0);
    }    
    
}
