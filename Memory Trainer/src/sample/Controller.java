package sample;

import java.util.*;
import java.net.URL;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Controller implements Initializable
{
    @FXML
    private Label t;
    @FXML
    private Label t1;
    @FXML
    private TextField e;
    @FXML
    private Button bStart;
    @FXML
    private Button bRev;
    @FXML
    private ListView<String> l;
    @FXML
    private ComboBox<String> cb;
    private ObservableList<String> NumberList;
    private ObservableList<String> TempoList;
    private int[] mas;
    private int v;
    private int f;
    private int time;

    public Controller() {
        super();
        this.v = 0;
        this.f = 0;
        this.time = 1000;
        this.NumberList = FXCollections.observableArrayList("3 числа", "4 числа", "5 чисел", "6 чисел", "7 чисел", "8 чисел", "9 чисел", "10 чисел", "11 чисел", "12 чисел");
        this.TempoList = FXCollections.observableArrayList("1 секунда", "2 секунды","3 секунды","4 секунды","5 секунд","6 секунд","7 секунд","8 секунд","9 секунд","10 секунд");
        this.mas = new int[12];
    }

    @FXML
    private void revisionButton() {
        if (this.mas[0] == 0) {
            this.alertWindow("", "Нажмите СТАРТ!", "ОШИБКА!");
            return;
        }
        if (this.e.getText() == null || this.e.getText().trim().length() == 0) {
            this.alertWindow("", "Введите свой ряд!", "ОШИБКА!");
            return;
        }
        final String us = this.e.getText();
        String p = "";
        for (int i = 0; i < 3 + this.l.getSelectionModel().getSelectedIndex(); ++i) {
            p = p + this.mas[i] + "";
        }
        if (p.equals(us)) {
            this.alertWindow("", "Всё правильно!\nВы ввели верный числовой ряд!", "Результат");
            ++this.v;
        }
        else {
            this.alertWindow("НЕВЕРНО!", "Надо было так:\n" + p, "Результат");
            ++this.f;
        }
        this.mas = new int[12];
        this.bStart.setDisable(false);
        this.cb.setDisable(false);
        this.l.setDisable(false);
    }

    @FXML
    private void startButton() {
        final int lp = 3 + this.l.getSelectionModel().getSelectedIndex();
        String str = "";
        int i;
        for (i = 0; i < lp + 1; ++i) {
            if (i != 0 && i != lp) {
                do {
                    this.mas[i] = new Random().nextInt(9) + 1;
                } while (this.mas[i] == this.mas[i - 1]);
                str+= this.mas[i] + "";
            }
            else if (i != lp) {
                this.mas[i] = new Random().nextInt(9) + 1;
                str += this.mas[i] + "";
            }
        }
        t.setText(str);
        this.bStart.setDisable(true);
        this.bRev.setDisable(true);
        this.cb.setDisable(true);
        this.l.setDisable(true);
        this.timeHideNumbers();
    }

    @FXML
    private void timeBox() {
        time=(this.cb.getSelectionModel().getSelectedIndex()+1)*1000;
    }

    @FXML
    private void resultButton() {
        if (this.v == 0 && this.f == 0) {
            this.alertWindow("", "Пока нет результатов.", "Итоги");
        }
        else {
            this.alertWindow("На данный момент...", "Верных рядов: " + this.v + "\nНеверных рядов: " + this.f, "Итоги");
        }
    }

    @FXML
    private void showList() {
        this.t1.setText("Игра в " + this.l.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void autorInfoLabel() {
        startDonate("Об Авторе","Автор: Алекс Мирный\nМесто работы: КБЖБ\nХотите поддержать автора?");
    }

    private void timeHideNumbers() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                            timer.cancel();
                            t.setText("?");
                            e.setText("");
                            e.requestFocus();
                            bRev.setDisable(false);
                    }
                });
            }
        },time);
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
    private void alertWindow(final String s, final String s2, final String str) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(str);
        alert.setHeaderText(s);
        alert.setContentText(s2);
        alert.showAndWait();
    }

    public void initialize(final URL url, final ResourceBundle rb) {
        this.l.setItems(this.NumberList);
        this.l.getSelectionModel().selectFirst();
        this.cb.setItems(this.TempoList);
        this.cb.getSelectionModel().selectFirst();
        this.t1.setText("Игра в 3 числа");
    }
}
