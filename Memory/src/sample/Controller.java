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
    ObservableList<String> NumberList;
    ObservableList<String> TempoList;
    private int[] mas;
    private int v;
    private int f;
    private int temp;

    public Controller() {
        super();
        this.v = 0;
        this.f = 0;
        this.temp = 1000;
        this.NumberList = FXCollections.observableArrayList("3 числа", "4 числа", "5 чисел", "6 чисел", "7 чисел", "8 чисел", "9 чисел", "10 чисел", "11 чисел", "12 чисел");
        this.TempoList = FXCollections.observableArrayList("медленно", "быстро","очень быстро");
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
        for (int i = 0; i < lp + 1; ++i) {
            if (i != 0 && i != lp) {
                do {
                    this.mas[i] = new Random().nextInt(9) + 1;
                } while (this.mas[i] == this.mas[i - 1]);
                str = this.mas[i] + "";
            }
            else if (i != lp) {
                this.mas[i] = new Random().nextInt(9) + 1;
                str = this.mas[i] + "";
            }
            this.timeShowNumber(i + 1, this.temp, lp, str);
        }
        this.bStart.setDisable(true);
        this.bRev.setDisable(true);
        this.cb.setDisable(true);
        this.l.setDisable(true);
    }

    @FXML
    private void tempoBox() {
        switch (this.cb.getSelectionModel().getSelectedIndex()) {
            case 0:
                this.temp = 1000;
                break;
            case 1:
                this.temp = 500;
                break;
            case 2:
                this.temp = 250;
                break;
        }
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

    private void timeShowNumber(final int a, final int a1, final int lim, final String b) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (a == lim + 1) {
                            timer.cancel();
                            t.setText("?");
                            e.setText("");
                            e.requestFocus();
                            bRev.setDisable(false);
                            return;
                        }
                        t.setText(b);
                        timer.cancel();
                    }
                });
            }
        },a*a1);
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
        this.cb.setValue("медленно");
        this.t1.setText("Игра в 3 числа");
    }
}
