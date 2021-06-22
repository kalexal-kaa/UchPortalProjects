/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmicalculator;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author kaa
 */
public class CalculatorController implements Initializable {
    
    @FXML
    private Button cButton;
    @FXML
    private TextField h,w,c;
    @FXML
    private TextArea t;
    @FXML
    private ImageView iv;
    @FXML
    private ComboBox<String> cb;
    
    private final ObservableList<String> List;
    
    public CalculatorController(){
        super();
        this.List = FXCollections.observableArrayList("мужчина", "женщина");
    }
    @FXML
    private void showResultsAction() {
        float userH,userW,userC;
        try{
        userH=Float.parseFloat(h.getText());
        userW=Float.parseFloat(w.getText());
        userC=Float.parseFloat(c.getText());
        }catch(NumberFormatException e){
            e.getMessage();
            setToast("Введите корректные данные во все 3 поля!");
            return;
        }
        float index;
        int gender;
        String sGender,s;
    if (cb.getSelectionModel().getSelectedIndex()==0){
        gender=19;
        sGender="Пол: мужской";
    }else{
        gender=16;
        sGender="Пол: женский";
    }
     userH=userH/100;
     index=userW/(userH*userH);
     index=index*(gender/userC);
     
     if(index<16)s="Дефицит веса";
     else if(index>=16&&index<20)s="Недостаточный вес";
     else if(index>=20&&index<25)s="Норма";
     else if(index>=25&&index<30)s="Предожирение";
     else if(index>=30&&index<35)s="Первая степень ожирения";
     else if(index>=35&&index<40)s="Вторая степень ожирения";
     else s="Морбидное ожирение";
     
     t.setText(sGender+"\n"+somatoType(gender, userC)+"\n"+"ИМТ: "+index+"\n"+s+"\n"+normalMassMin(userC, userH, gender)
     +"\n"+normalMassMax(userC, userH, gender));
     cButton.setDisable(false);
    }
    @FXML
    private void clearAction(){
        t.clear();
        cButton.setDisable(true);
    }
    @FXML
    private void autorInfo(){
        startDonate("Об Авторе","Автор: Алекс Мирный\nМесто работы: КБЖБ\nХотите поддержать автора?");
    }
    @FXML
    private void appInfo(){
        informationWindow("Название: Калькулятор ИМТ\nВерсия: 1.0", "О Программе");
    }
    private String normalMassMin(float x,float y,int z){
        return "Нижняя граница нормального веса: "+20*(x*(y*y)/z)+" кг.";
    }
    private String normalMassMax(float x,float y,int z){
        return "Верхняя граница нормального веса: "+25*(x*(y*y)/z)+" кг."; 
    }
    private String somatoType(int a,float b){
        String s="",sTypeA="Тип телосложения: астенический",sTypeN="Тип телосложения: нормостенический",sTypeH="Тип телосложения: гиперстенический";
        switch(a){
            case 19:
                if(b<18)s=sTypeA;
                else if(b>=18&&b<=20)s=sTypeN;
                else s=sTypeH;
                break;
            case 16:
                if(b<15)s=sTypeA;
                else if(b>=15&&b<=17)s=sTypeN;
                else s=sTypeH;
                break;
                default:
                break;
        }
        return s;
    }
    private void showImage() {
        final Image i = new Image("images/icon.png");
        iv.setImage(i);
    }
    private void setToast(final String toastMsg){
Stage toastStage=new Stage();
toastStage.setResizable(false);
toastStage.initStyle(StageStyle.TRANSPARENT);
Text text = new Text(toastMsg);
text.setFont(Font.font("Verdana",20));
text.setFill(Color.WHITE);
StackPane root = new StackPane(text);
root.getStyleClass().add("toast");
root.setOpacity(0);
Scene scene = new Scene(root);
scene.getStylesheets().add((getClass().getResource("css/toaststyle.css")).toExternalForm());
scene.setFill(null);
toastStage.setScene(scene);
toastStage.show();
Timeline tl1 = new Timeline();
KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(500),new KeyValue (toastStage.getScene().getRoot().opacityProperty(), 1));
tl1.getKeyFrames().add(fadeInKey1);
tl1.setOnFinished((ae) ->
{
new Thread(() -> {
try
{
Thread.sleep(2000);
}
catch (InterruptedException e)
{
e.getMessage();
}
Timeline tl2 = new Timeline();
KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(500),new KeyValue (toastStage.getScene().getRoot().opacityProperty(),0));
tl2.getKeyFrames().add(fadeOutKey1);
tl2.setOnFinished((aeb) -> toastStage.close());
tl2.play();
}).start();
});
tl1.play();
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
    private void informationWindow(final String s, final String str) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(str);
        alert.setHeaderText("");
        alert.setContentText(s);
        alert.showAndWait();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          cb.setItems(List);
          cb.getSelectionModel().selectFirst();
          cButton.setDisable(true);
          showImage();
    }    
}
