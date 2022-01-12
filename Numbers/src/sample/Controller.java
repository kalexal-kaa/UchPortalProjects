package sample;

import java.util.*;
import java.net.URL;
import java.util.stream.IntStream;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Controller implements Initializable
{
    @FXML
    private Label lGameInfo,lTimer;
    @FXML
    private TextField e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,e13,e14,e15,e16;
    @FXML
    private Button bStart,bRev,bShow;
    @FXML
    private ListView<String> list;
    @FXML
    private ComboBox<String> cbTime;

    private final ObservableList<String> gameTypeList;
    private final ObservableList<String> timeList;
    private String[] mas,userMas;
    private final Random random;
    private final Toast toast;
    private int time,n,v,f;

    public Controller() {
        super();
        this.n=0;
        this.v = 0;
        this.f = 0;
        this.time = 1000;
        this.gameTypeList = FXCollections.observableArrayList("3 числа", "4 числа", "5 чисел", "6 чисел", "7 чисел",
                "8 чисел", "9 чисел", "10 чисел", "11 чисел", "12 чисел","13 чисел","14 чисел","15 чисел","16 чисел");
        this.timeList = FXCollections.observableArrayList("1 секунда", "2 секунды","3 секунды","4 секунды",
                "5 секунд","6 секунд","7 секунд","8 секунд","9 секунд","10 секунд");
        this.random=new Random();
        this.toast=new Toast();
    }
    @FXML
    private void revisionButton() {
        userMas=new String[16];
        IntStream.range(0, 16).forEach(i -> userMas[i] = "");
        userMas[0]= e1.getText();
        userMas[1]=e2.getText();
        userMas[2]=e3.getText();
        userMas[3]=e4.getText();
        userMas[4]=e5.getText();
        userMas[5]=e6.getText();
        userMas[6]=e7.getText();
        userMas[7]=e8.getText();
        userMas[8]=e9.getText();
        userMas[9]=e10.getText();
        userMas[10]=e11.getText();
        userMas[11]=e12.getText();
        userMas[12]=e13.getText();
        userMas[13]=e14.getText();
        userMas[14]=e15.getText();
        userMas[15]=e16.getText();
        int num;
        for (String userMas : userMas) {
            if (userMas.equals("")||userMas.equals(" ")) {
                continue;
            }
            try {
                num = Integer.parseInt(userMas);
            } catch (NumberFormatException nfe) {
                toast.setMessage("Некорректные символы!");
                return;
            }
            if (num > 9 || num < 1) {
                toast.setMessage("Числа должны быть в диапазоне от 1 до 9");
                return;
            }
        }
        if (Arrays.equals(userMas, mas)) {
            this.toast.setMessage("ВЕРНО!");
            ++this.v;
        }
        else {
            this.alertWindow("НЕВЕРНО!", "Нажмите ПОКАЗАТЬ, чтобы увидеть верную расстановку.", "Результат");
            this.bShow.setDisable(false);
            ++this.f;
        }
        this.bStart.setDisable(false);
        this.cbTime.setDisable(false);
        this.list.setDisable(false);
        this.bRev.setDisable(true);
    }
    @FXML
    private void startButton() {
        mas = new String[16];
        IntStream.range(0, 16).forEach(i -> mas[i] = "");
        final int lp = 3 + this.list.getSelectionModel().getSelectedIndex();
        int[] array = new int[16];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        List<Integer> list = new ArrayList<>();
        int[] lpArray = new int[lp];

        for (int j : array) {
            list.add(j);
        }
        Collections.shuffle(list);
        for (int k = 0; k < lpArray.length; k++) {
            this.mas[list.get(k)] = String.valueOf(random.nextInt(9) + 1);
        }
        setNumbers();
        timerForNumbers();
        this.bShow.setText("показать");
        this.bShow.setDisable(true);
        this.bStart.setDisable(true);
        this.cbTime.setDisable(true);
        this.list.setDisable(true);
        this.n=0;
    }
    @FXML
    private void timeSelect() {
        time=(this.cbTime.getSelectionModel().getSelectedIndex()+1)*1000;
    }
    @FXML
    private void resultButton() {
        if (this.v == 0 && this.f == 0) {
            this.toast.setMessage( "Пока нет результатов");
        }
        else {
            this.alertWindow("На данный момент...", "Верных полей: " + this.v + "  Неверных полей: " + this.f, "Итоги");
        }
    }
    @FXML
    private void showListItem() {
        this.lGameInfo.setText("Игра в " + this.list.getSelectionModel().getSelectedItem());
    }
    @FXML
    private void programInfo() {
        alertWindow("","Название: Тренировка Памяти\nВерсия: 2.0\nАвтор: Алексей Крючков","О Программе");
    }
    @FXML
    private void showAction(){
        switch (n){
            case 0:
                n++;
                setNumbers();
                bShow.setText("назад");
                break;
                case 1:
                    n=0;
                    e1.setText(userMas[0]);
                    e2.setText(userMas[1]);
                    e3.setText(userMas[2]);
                    e4.setText(userMas[3]);
                    e5.setText(userMas[4]);
                    e6.setText(userMas[5]);
                    e7.setText(userMas[6]);
                    e8.setText(userMas[7]);
                    e9.setText(userMas[8]);
                    e10.setText(userMas[9]);
                    e11.setText(userMas[10]);
                    e12.setText(userMas[11]);
                    e13.setText(userMas[12]);
                    e14.setText(userMas[13]);
                    e15.setText(userMas[14]);
                    e16.setText(userMas[15]);
                    bShow.setText("показать");
                    break;
                    default:
                        break;
        }
    }
    private void timerForNumbers() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    e1.clear();
                    e2.clear();
                    e3.clear();
                    e4.clear();
                    e5.clear();
                    e6.clear();
                    e7.clear();
                    e8.clear();
                    e9.clear();
                    e10.clear();
                    e11.clear();
                    e12.clear();
                    e13.clear();
                    e14.clear();
                    e15.clear();
                    e16.clear();
                    bRev.setDisable(false);
                    timer.cancel();
                });
            }
        },time);
        int t=cbTime.getSelectionModel().getSelectedIndex()+1;
        for (int i=0;i<=t;i++){
            int a=t-i;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(()->{
                        if (a==0){
                            lTimer.setText("");
                            return;
                        }
                        lTimer.setText(String.format("%d", a));
                    });
                }
            }, 1000L *i);
        }
    }
    private void setNumbers(){
        e1.setText(mas[0]);
        e2.setText(mas[1]);
        e3.setText(mas[2]);
        e4.setText(mas[3]);
        e5.setText(mas[4]);
        e6.setText(mas[5]);
        e7.setText(mas[6]);
        e8.setText(mas[7]);
        e9.setText(mas[8]);
        e10.setText(mas[9]);
        e11.setText(mas[10]);
        e12.setText(mas[11]);
        e13.setText(mas[12]);
        e14.setText(mas[13]);
        e15.setText(mas[14]);
        e16.setText(mas[15]);
    }
    private void alertWindow(final String s, final String s2, final String str) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(450,150);
        alert.setTitle(str);
        alert.setHeaderText(s);
        alert.setContentText(s2);
        alert.showAndWait();
    }
    public void initialize(final URL url, final ResourceBundle rb) {
        this.bShow.setDisable(true);
        this.bRev.setDisable(true);
        this.list.setItems(this.gameTypeList);
        this.list.getSelectionModel().selectFirst();
        this.cbTime.setItems(this.timeList);
        this.cbTime.getSelectionModel().selectFirst();
        this.lGameInfo.setText("Игра в 3 числа");
        this.bShow.setText("показать");
    }
}
