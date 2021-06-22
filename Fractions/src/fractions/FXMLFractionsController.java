/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractions;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author kaa
 */
public class FXMLFractionsController implements Initializable {
    
    @FXML
    private Label t;
    @FXML
    private TextField tf1,tf2,tf3;
    @FXML
    ComboBox<String> cb1,cb2;
    
    ObservableList<String> List1,List2;
    
    int v,f,sk,diapasone;
    double a,b,c,d,nUp,nDown,x;
    String s;
    
    public FXMLFractionsController(){
        x=0;
        diapasone=10;
        List1=FXCollections.observableArrayList("10","20","30","40","50","60","70","80","90","100");
        List2=FXCollections.observableArrayList("случайное","сложение","вычитание","умножение","деление");
    }
    @FXML
    private void inspectAction() {
        String st = null;
        if(null != s)switch (s) {
            case "+":
                st=fractionReduction((a*d)+(c*b), b*d);
                break;
            case "-":
                st=fractionReduction((a*d)-(c*b), b*d);
                break;
            case "*":
                st=fractionReduction(a*c, b*d);
                break;
            case ":":
                st=fractionReduction(a*d, b*c);
                break;
            default:
                break;
        }
        if(nUp>nDown){
                st=integerPart();
            }
        int r1,r2,r3;
        try{
        r1=Integer.parseInt(tf1.getText());
        r2=Integer.parseInt(tf2.getText());
        r3=Integer.parseInt(tf3.getText());
        }catch(NumberFormatException e){
            e.getMessage();
            alert("Введите корректные данные во все 3 поля!\nЕсли целая часть отсутствует, то введите ноль.");
            return;
        }
        if(r1==(int)x&&r2==(int)nUp&&r3==(int)nDown){
            alert("ВЕРНО! "+t.getText()+"="+st);
            generateExample();
            v++;
            x=0;
        }else{
            alert("НЕВЕРНО! Верный ответ:\n "+t.getText()+"="+st);
            generateExample();
            f++;
            x=0;
        }
    }
    @FXML
    private void skip(){
        generateExample();
        sk++;
    }
    @FXML
    private void showResults(){
          if (v==0&&f==0&&sk==0){
               this.alert("Пока нет результатов.");
           }else {
               this.alert("Верных ответов: " + this.v + "\nНеверных ответов: " + this.f+"\nПропущено примеров: " + this.sk);
       }
    }
    @FXML
    private void autorInfo(){
        startDonate("Об Авторе","Автор: Алекс Мирный\nМесто работы: КБЖБ\nХотите поддержать автора?");
    }
    @FXML
    private void diapasoneAction(){
        diapasone=10+(10*cb1.getSelectionModel().getSelectedIndex());
    }
    private void alert(final String s) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText("");
        alert.setContentText(s);
        alert.showAndWait();
    }
    private String integerPart(){
        double y;
        x=nUp/nDown;
        y=nUp%nDown;
        if(y==0){
            x=0;
            return (int)nUp+"/"+(int)nDown;
        }
        nUp=y;
        return (int)x+" цел. "+(int)y+"/"+(int)nDown;
    }
    private String generateFraction(){
        int a1 = 0,b1 = 0;
         do {
            a1 = 1 + (int) (Math.random() * diapasone); 
            b1 = 2 + (int) (Math.random() * (diapasone-1));
        } while (a1 >= b1);
        return fractionReduction(a1, b1);
    }
    private String fractionReduction(double n1,double n2){
       double temp1 = n1,temp2 = n2; 
       while (n1 != n2){
         if(n1 > n2)
            n1 = n1 - n2;
         else
            n2 = n2 - n1;
       }      
      nUp = temp1 / n1 ;
      nDown = temp2 / n1 ;
        return (int)nUp+"/"+(int)nDown;    
   }
    private void generateExample(){
        String s1,s2;
        s=sign();
        if("-".equals(s)){
        do {                
        s1=generateFraction();
        a=nUp;
        b=nDown;
        s2=generateFraction();
        c=nUp;
        d=nDown; 
         } while ((a/b)<=(c/d));
            t.setText(s1+" - "+s2);
            return;
        }
        s1=generateFraction();
        a=nUp;
        b=nDown;
        s2=generateFraction();
        c=nUp;
        d=nDown;
        t.setText(s1+" "+s+" "+s2);
    }
    private String sign(){
        String str = null;
        int sig;
        if(cb2.getSelectionModel().getSelectedIndex()==0){
            sig=(int)(Math.random()*4);
        }else{
            sig=cb2.getSelectionModel().getSelectedIndex()-1;
        }
        switch(sig){
            case 0:
                str="+";
                break;
                case 1:
                    str="-";
                    break;
                    case 2:
                        str="*";
                        break;
                        case 3:
                            str=":";
                            break;
                            default:
                                break;
                            
        }
        return str;
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
    public void initialize(URL url, ResourceBundle rb) {
        cb1.setItems(List1);
        cb1.getSelectionModel().selectFirst();
        cb2.setItems(List2);
        cb2.getSelectionModel().selectFirst();
        generateExample();
    }    
    
}
