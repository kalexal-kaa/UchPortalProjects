package sample;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.text.SimpleDateFormat;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

public class Controller implements Initializable {
    @FXML
    private Label label;
    @FXML
    private TextField tf;
    @FXML
    private CheckBox cb;
    @FXML
    private CheckBox cbox;
    @FXML
    private ListView<String> list;
    @FXML
    private TextArea t;
    @FXML
    private ImageView image;

    private final ObservableList<String> schemasArithList;
    private final ObservableList<String> schemasEquatList;
    private int bal;
    private int y;
    private int f;
    private final Random random;
    private String userName = "anonym";
    private String type;
    private String path;
    private String l;
    private final Generator generator;
    private final Toast toast;

    public Controller() {
        super();
        this.bal = 0;
        this.y = 0;
        this.f = 0;
        this.l = "";
        this.schemasArithList = FXCollections.observableArrayList("случайно", "a+b-c:d", "ab+c-d:e", "a:bc", "ab:c", "a:b+c-d", "a+b-c", "a-b+c", "a+bc-d", "ab-c+d-e", "a+b-cd", "a+b", "a-b", "a*b", "a:b");
        this.schemasEquatList = FXCollections.observableArrayList("случайно", "a+b-c:x=r", "ab+c-d:x=r", "a:bx=r", "ax:c=r", "a:b+c-x=r", "a+x-c=r", "a-b+x=r", "a+bx-d=r", "ax-c+d-e=r", "a+b-cx=r", "a+x=r", "x-b=r", "ax=r", "a:x=r");
        this.generator=new Generator();
        this.toast=new Toast();
        this.random=new Random();
    }

    @FXML
    private void inspectAction() {
        int us;
        try {
            us = Integer.parseInt(this.tf.getText());
        } catch (NumberFormatException e) {
            toast.setMessage("введите ответ");
            this.tf.requestFocus();
            return;
        }
        if (this.cbox.isSelected() && isEmpty(t.getText()) ) {
            this.toast.setMessage("введите ход решения");
            this.t.requestFocus();
            return;
        }
        if (this.cbox.isSelected() && this.textAnswerAnalizator(this.t.getText())) {
            this.t.requestFocus();
            return;
        }
        if (us == this.generator.result) {
            if (!this.l.equals(this.label.getText())) {
                ++this.y;
            } else {
                ++this.f;
            }
            this.l = "";
            final String s = type;
            int n = -1;
            if (s.hashCode() == 463370811) {
                if (s.equals("примеры")) {
                    n = 0;
                }
            }
            if (n == 0) {
                this.alertWindow("ВЕРНО!", this.label.getText() + "=" + us, "РЕЗУЛЬТАТ");
                if (this.cb.isSelected()) {
                    this.recordInHistory("верно: " + this.label.getText() + "=" + us + " (" + actionTime() + ")");
                }
            } else {
                this.alertWindow("ВЕРНО!", this.label.getText() + "\nx=" + us, "РЕЗУЛЬТАТ");
                if (this.cb.isSelected()) {
                    this.recordInHistory("верно: " + this.label.getText() + " x=" + us + " (" + actionTime() + ")");
                }
            }
        } else {
            ++this.f;
            final String s2 = type;
            int n2 = -1;
            if (s2.hashCode() == 463370811) {
                if (s2.equals("примеры")) {
                    n2 = 0;
                }
            }
            if (n2 == 0) {
                this.alertWindow("НЕВЕРНО!", "Верный ответ:\n " + this.label.getText() + "=" + this.generator.result, "РЕЗУЛЬТАТ");
                if (this.cb.isSelected()) {
                    this.recordInHistory("неверно: " + this.label.getText() + "=" + us + " верный ответ: " + this.generator.result + " (" + actionTime() + ")");
                }
            } else {
                this.alertWindow("НЕВЕРНО!", "Верный ответ:\n " + this.label.getText() + "\nx=" + this.generator.result, "РЕЗУЛЬТАТ");
                if (this.cb.isSelected()) {
                    this.recordInHistory("неверно: " + this.label.getText() + " x=" + us + " верный ответ: x=" + this.generator.result + " (" + actionTime() + ")");
                }
            }
        }
        if (this.cbox.isSelected()) {
            this.recordInHistory("ход решения для " + this.label.getText() + ":\n" + this.t.getText() + " ("+actionTime()+")");
        }
        this.image.setDisable(false);
        this.showBal();
        if (this.cb.isSelected()) {
            this.recordInHistory("текущая оценка: " + this.bal);
        }
        if (this.list.getSelectionModel().getSelectedIndex() == -1 || this.list.getSelectionModel().getSelectedIndex() == 0) {
            this.nextFormula(random.nextInt(14) + 1,  type);
        } else {
            this.nextFormula(this.list.getSelectionModel().getSelectedIndex(), type);
        }
        this.t.setText("");
        this.tf.setText("");
        this.tf.requestFocus();
    }

    @FXML
    private void showResultAction() {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Взять подсказку");
        alert.setHeaderText("");
        alert.setContentText("Вы действительно хотите взять подсказку?");
        final Optional<ButtonType> resultAlert =  alert.showAndWait();
        if (resultAlert.get() == ButtonType.OK) {
            this.l = this.label.getText();
            this.tf.setText(this.generator.result + "");
            if (this.cb.isSelected()) {
                final String s = type;
                int n = -1;
                if (s.hashCode() == 463370811) {
                    if (s.equals("примеры")) {
                        n = 0;
                    }
                }
                if (n == 0) {
                    this.recordInHistory("взята подсказка для примера " + this.l + " ответ: " + this.generator.result + " (" + actionTime() + ")");
                } else {
                    this.recordInHistory("взята подсказка для уравнения " + this.l + " ответ: x=" + this.generator.result + " (" + actionTime() + ")");
                }
            }
        }
    }

    @FXML
    private void registrationAction() {
        final TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Регистрация");
        dialog.setHeaderText("Введите ваше имя для регистрации в истории");
        dialog.setContentText("Ваше имя:");
        final Optional<String> name = dialog.showAndWait();
        if (name.isPresent()) {
            if (isEmpty(name.get())){
                final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Регистрация");
                alert.setHeaderText("");
                alert.setContentText("Зарегистрировать вас под именем anonym?");
                final Optional<ButtonType> resultAlert =  alert.showAndWait();
                if (resultAlert.get() == ButtonType.OK) {
                    userName="anonym";
                }else{
                    return;
                }
            }else {
                userName=name.get();
            }
            toast.setMessage("Имя: "+userName);
            this.recordInHistory("регистрация решающего\nимя: " + userName + " ("+dayDateAndTime()+")");
            this.tf.requestFocus();
            this.bal = 0;
            this.y = 0;
            this.f = 0;
            this.l = "";
            this.image.setImage(null);
            this.image.setDisable(true);
        }else {
            toast.setMessage("регистрация отменена");
            this.recordInHistory("регистрация решающего\nрегистрация отменена ("+dayDateAndTime()+")");
        }
    }

    @FXML
    public void exit() {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ВЫХОД");
        alert.setHeaderText("Выход из программы");
        alert.setContentText("Вы действительно хотите выйти из программы?");
        final Optional<ButtonType> resultAlert =  alert.showAndWait();
        if (resultAlert.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private void skipAction() {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Пропуск задания");
        alert.setHeaderText("");
        alert.setContentText("Вы действительно хотите пропустить это задание?");
        final Optional<ButtonType> resultAlert =  alert.showAndWait();
        if (resultAlert.get() == ButtonType.OK) {
            ++this.f;
            if (this.cb.isSelected()) {
                final String s = type;
                int n = -1;
                if (s.hashCode() == 463370811) {
                    if (s.equals("примеры")) {
                        n = 0;
                    }
                }
                if (n == 0) {
                    this.recordInHistory("пример " + this.label.getText() + " пропущен (" + actionTime() + ")");
                } else {
                    this.recordInHistory("уравнение " + this.label.getText() + " пропущено (" + actionTime() + ")");
                }
            }
            if (this.list.getSelectionModel().getSelectedIndex() == -1 || this.list.getSelectionModel().getSelectedIndex() == 0) {
                this.nextFormula(random.nextInt(14) + 1,type);
            } else {
                this.nextFormula(this.list.getSelectionModel().getSelectedIndex(), type);
            }
            this.t.setText("");
            this.tf.setText("");
            this.tf.requestFocus();
            this.image.setDisable(false);
            this.showBal();
            if (this.cb.isSelected()) {
                this.recordInHistory("текущая оценка: " + this.bal);
            }
        }
    }

    @FXML
    private void showHistory() {
        final FileChooser fc = new FileChooser();
        fc.setTitle("Просмотр истории");
        fc.setInitialDirectory(new File(path));
        File historyFile = fc.showOpenDialog(null);
        if (historyFile != null) {
            final Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setResizable(true);
            alert.setTitle("История");
            alert.setHeaderText("Просмотр Истории\n"+historyFile.getName());
            final TextArea ta = new TextArea(readerHistory(historyFile.getPath()));
            ta.setEditable(false);
            ta.setWrapText(true);
            ta.setMaxWidth(Double.MAX_VALUE);
            ta.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(ta, Priority.ALWAYS);
            GridPane.setHgrow(ta, Priority.ALWAYS);
            final GridPane gp = new GridPane();
            gp.setMaxWidth(Double.MAX_VALUE);
            gp.add(ta, 0, 0);
            alert.getDialogPane().setContent(gp);
            alert.showAndWait();
        }
    }

    @FXML
    private void examples(){
        type="примеры";
        this.list.setItems(this.schemasArithList);
        this.list.getSelectionModel().selectFirst();
        this.nextFormula(random.nextInt(14) + 1, "примеры");
        this.recordInHistory("выбраны примеры ("+actionTime()+")");
        this.tf.requestFocus();
    }
    @FXML
    private void equations(){
        type="уравнения";
        this.list.setItems(this.schemasEquatList);
        this.list.getSelectionModel().selectFirst();
        this.nextFormula(random.nextInt(14) + 1, "уравнения");
        this.recordInHistory("выбраны уравнения ("+actionTime()+")");
        this.tf.requestFocus();
    }
    @FXML
    private void typeSelect() {
        if (this.list.getSelectionModel().getSelectedIndex() == -1 || this.list.getSelectionModel().getSelectedIndex() == 0) {
            this.nextFormula(random.nextInt(14) + 1,type);
            this.recordInHistory("выбрана случайная формула ("+actionTime()+")");
        } else {
            this.nextFormula(this.list.getSelectionModel().getSelectedIndex(),type);
            this.recordInHistory("выбрана формула " + this.list.getSelectionModel().getSelectedItem() + " ("+actionTime()+")");
        }
        this.tf.requestFocus();
    }

    @FXML
    private void checkRecHistoryAction() {
        if (this.cb.isSelected()) {
            this.recordInHistory("запись истории разрешена");

            this.tf.requestFocus();
        } else {
            this.recordInHistory("запрещена запись истории");

            this.tf.requestFocus();
        }
    }

    @FXML
    private void checkAccSolutionAction() {
        if (this.cbox.isSelected()) {
            this.recordInHistory("учет хода решения активирован");

            this.tf.requestFocus();
        } else {
            this.recordInHistory("учет хода решения деактивирован");

            this.tf.requestFocus();
        }
    }

    @FXML
    private void imageAction() {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Результаты");
        alert.setHeaderText("Предварительные Результаты");
        alert.setContentText("На данный момент было заданий: " + (this.y + this.f));
        final Label l1 = new Label("Подробнее:");
        final TextArea ta = new TextArea("Верных ответов: " + this.y + "\nНевыполнено заданий: " + this.f + "\nПроцент верных ответов: " + 100 * this.y / (this.y + this.f) + "%\nПредварительная оценка: " + this.bal);
        ta.setEditable(false);
        ta.setWrapText(true);
        ta.setMaxWidth(Double.MAX_VALUE);
        ta.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(ta, Priority.ALWAYS);
        GridPane.setHgrow(ta, Priority.ALWAYS);
        final GridPane gp = new GridPane();
        gp.setMaxWidth(Double.MAX_VALUE);
        gp.add( l1, 0, 0);
        gp.add( ta, 0, 1);
        alert.getDialogPane().setExpandableContent(gp);
        alert.showAndWait();
    }

    @FXML
    private void authorInfo() {
        alertWindow("","Автор: Алексей Крючков","Об Авторе");
    }
    @FXML
    private void programInfo(){
        alertWindow("","Название: Математика\nВерсия: 8.0","О Программе");
    }
    @FXML
    private void cleanHistory(){
        final FileChooser fc = new FileChooser();
        fc.setTitle("Очищение истории");
        fc.setInitialDirectory(new File(path));
        File historyFile = fc.showOpenDialog(null);
        if (historyFile != null) {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(true);
        alert.setTitle("Очищение истории");
        alert.setHeaderText("Очистить историю?");
        alert.setContentText("Вы действительно хотите очистить эту историю?\nИмя файла истории: "+historyFile.getName());
        final Optional<ButtonType> resultAlert =  alert.showAndWait();
        if (resultAlert.get() == ButtonType.OK) {
            try (final FileWriter sw = new FileWriter(historyFile.getPath(), false)) {
                sw.write(dayDateAndTime()+"\nистория очищена\n");
            } catch (Exception e) {
                e.getMessage();
            }
            toast.setMessage("очищение истории");
           }
        }
    }

    @FXML
    private void deleteHistoryFile(){
        final FileChooser fc = new FileChooser();
        fc.setTitle("Удаление файла истории");
        fc.setInitialDirectory(new File(path));
        File historyFile = fc.showOpenDialog(null);
        if (historyFile != null) {
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setResizable(true);
            alert.setTitle("Удаление файла истории");
            alert.setHeaderText("Удалить файл истории?");
            alert.setContentText("Вы действительно хотите удалить этот файл истории?\nИмя файла истории: "+historyFile.getName());
            final Optional<ButtonType> resultAlert =  alert.showAndWait();
            if (resultAlert.get() == ButtonType.OK) {
                if(historyFile.delete()){
                    toast.setMessage("файл удален");
                }else{
                    toast.setMessage("ошибка удаления");
                }
            }
        }
    }
    private String readerHistory(final String p) {
        StringBuilder sb = new StringBuilder();
        try {
            final File file = new File(p);
            final FileReader fr = new FileReader(file);
            try (final BufferedReader br = new BufferedReader(fr)) {
                String str;
                while ((str = br.readLine()) != null) {
                    sb.append(str).append("\n");
                }
                fr.close();
            }
        }
        catch (IOException e) {
            e.getMessage();
        }
        return sb.toString();
    }
    private boolean isEmpty(String s){
        return s == null || s.trim().length() == 0;
    }
    private boolean textAnswerAnalizator(final String s) {
        int n = 0;
        int k = 0;
        int p = 0;
        final char[] m = s.toCharArray();
        for (int i = 0; i < s.length(); ++i) {
            switch (m[i]) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    ++n;
                    break;
                case '*':
                case '+':
                case '-':
                case '/':
                    ++k;
                    break;
                case '=':
                    ++p;
                    break;
            }
        }
        if (n == 0) {
            this.alertWindow("Исправьте!", "Нет чисел в решении", "Ход Решения");
            return true;
        }
        if (k == 0) {
            this.alertWindow("Исправьте!", "Нет знаков действий", "Ход Решения");
            return true;
        }
        if (p == 0) {
            this.alertWindow("Исправьте!", "Нет знака равенства", "Ход Решения");
            return true;
        }
        return false;
    }

    private void showImage(final String path) {
        final Image i = new Image(path);
        this.image.setImage(i);
    }

    private void showBal() {
        final int percent = 100 * this.y / (this.y + this.f);
        if (percent >= 0 && percent <= 20) {
            this.showImage("images/1.png");
            this.bal = 1;
        } else if (percent > 20 && percent <= 40) {
            this.showImage("images/2.png");
            this.bal = 2;
        } else if (percent > 40 && percent <= 60) {
            this.showImage("images/3.png");
            this.bal = 3;
        } else if (percent > 60 && percent <= 80) {
            this.showImage("images/4.png");
            this.bal = 4;
        } else {
            this.showImage("images/5.png");
            this.bal = 5;
        }
    }

    private void alertWindow(final String s, final String s2, final String str) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(true);
        alert.setTitle(str);
        alert.setHeaderText(s);
        alert.setContentText(s2);
        alert.showAndWait();
    }

    private void recordInHistory(final String text) {
        try (final FileWriter sw = new FileWriter(this.path + System.getProperty("file.separator") + userName, true)) {
            sw.write(text + "\n");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void dirCreator(final String fPath) {
        final File file = new File(fPath);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private String dayDateAndTime() {
        final Calendar calendar = new GregorianCalendar();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy г. hh:mm:ss");
        String dayName = "";
        switch (calendar.get(7)) {
            case 1:
                dayName = "воскресенье";
                break;
            case 2:
                dayName = "понедельник";
                break;
            case 3:
                dayName = "вторник";
                break;
            case 4:
                dayName = "среда";
                break;
            case 5:
                dayName = "четверг";
                break;
            case 6:
                dayName = "пятница";
                break;
            case 7:
                dayName = "суббота";
                break;
        }
        return dayName + " " + sdf.format(calendar.getTime());
    }

    private String actionTime(){
        final Calendar calendar = new GregorianCalendar();
        final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        return sdf.format(calendar.getTime());
    }

    private String pathToDirectory() {
        String parentPath=null;
        try {
            parentPath= URLDecoder.decode(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
        }
        return parentPath+System.getProperty("file.separator")+"Математика";
    }

    private void nextFormula(final int n, final String s) {
        if ("примеры".equals(s)) {
            switch (n) {
                case 1:
                    this.label.setText(this.generator.arithSchema1());
                    break;
                case 2:
                    this.label.setText(this.generator.arithSchema2());
                    break;
                case 3:
                    this.label.setText(this.generator.arithSchema3());
                    break;
                case 4:
                    this.label.setText(this.generator.arithSchema4());
                    break;
                case 5:
                    this.label.setText(this.generator.arithSchema5());
                    break;
                case 6:
                    this.label.setText(this.generator.arithSchema6());
                    break;
                case 7:
                    this.label.setText(this.generator.arithSchema7());
                    break;
                case 8:
                    this.label.setText(this.generator.arithSchema8());
                    break;
                case 9:
                    this.label.setText(this.generator.arithSchema9());
                    break;
                case 10:
                    this.label.setText(this.generator.arithSchema10());
                    break;
                case 11:
                    this.label.setText(this.generator.arithSchema11());
                    break;
                case 12:
                    this.label.setText(this.generator.arithSchema12());
                    break;
                case 13:
                    this.label.setText(this.generator.arithSchema13());
                    break;
                case 14:
                    this.label.setText(this.generator.arithSchema14());
                    break;
            }
        }
        else {
            switch (n) {
                case 1:
                    this.label.setText(this.generator.equatSchema1());
                    break;
                case 2:
                    this.label.setText(this.generator.equatSchema2());
                    break;
                case 3:
                    this.label.setText(this.generator.equatSchema3());
                    break;
                case 4:
                    this.label.setText(this.generator.equatSchema4());
                    break;
                case 5:
                    this.label.setText(this.generator.equatSchema5());
                    break;
                case 6:
                    this.label.setText(this.generator.equatSchema6());
                    break;
                case 7:
                    this.label.setText(this.generator.equatSchema7());
                    break;
                case 8:
                    this.label.setText(this.generator.equatSchema8());
                    break;
                case 9:
                    this.label.setText(this.generator.equatSchema9());
                    break;
                case 10:
                    this.label.setText(this.generator.equatSchema10());
                    break;
                case 11:
                    this.label.setText(this.generator.equatSchema11());
                    break;
                case 12:
                    this.label.setText(this.generator.equatSchema12());
                    break;
                case 13:
                    this.label.setText(this.generator.equatSchema13());
                    break;
                case 14:
                    this.label.setText(this.generator.equatSchema14());
                    break;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dirCreator(this.path = this.pathToDirectory());
        this.recordInHistory("\n" + this.dayDateAndTime() + "\nпрограмма запущена");
        this.cb.setSelected(true);
        this.cbox.setSelected(false);
        this.type="примеры";
        this.list.setItems(this.schemasArithList);
        this.list.getSelectionModel().selectFirst();
        this.image.setImage(null);
        this.image.setDisable(true);
        this.nextFormula(random.nextInt(14) + 1,this.type);
        this.tf.requestFocus();
    }
}