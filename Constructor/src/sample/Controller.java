package sample;

import java.io.*;
import java.net.URLDecoder;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javafx.scene.control.TextInputDialog;
import java.util.Random;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;

public class Controller implements Initializable
{
    @FXML
    private Label l1;
    @FXML
    private Label l2;
    @FXML
    private Label l3;
    @FXML
    private TextField tf;
    @FXML
    private ComboBox<String> cb;
    @FXML
    private TextArea t;
    @FXML
    private Button b;

    private String testPath;
    private final String separator;
    private int r;
    private int calc;
    private final ObservableList<String> list;
    private File file,directoryTests;
    private final Toast toast;

    public Controller() {
        super();
        this.separator = "file.separator";
        this.list = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11","12");
        this.toast=new Toast();
    }

    public void nextQuestionAction() {
        if (isEmpty(this.t.getText())) {
            this.toast.setMessage("Введите вопрос и варианты ответа");
            t.requestFocus();
            return;
        }
        if (isEmpty(this.cb.getValue())) {
            this.toast.setMessage("Выберите вариант ответа");
            cb.requestFocus();
            return;
        }
        if (this.r == 1) {
            this.recordInFile(this.cb.getValue(), this.testPath + System.getProperty(this.separator) + "ответы1", true);
            this.recordInFile(this.t.getText(), this.testPath + System.getProperty(this.separator) + this.volumeAnswers("ответы1"), false);
            this.l3.setText("Вопрос №" + this.volumeAnswers("ответы1") + " сохранен");
            this.t.clear();
            this.cb.setValue("");
        }
        else {
            ++this.calc;
            if (this.calc == this.volumeAnswers("ответы1")) {
                this.b.setText("результат");
            }
            if (this.calc > this.volumeAnswers("ответы1")) {
                if("anonym".equals(userName())){
                    final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("ВНИМАНИЕ!");
                    alert.setHeaderText("Поле имени не заполнено!");
                    alert.setContentText("Хотите продолжить без ввода имени?");
                    final Optional<ButtonType> resultAlert = alert.showAndWait();
                    if (resultAlert.get() != ButtonType.OK) {
                        toast.setMessage("Введите ваше имя и фамилию");
                        tf.requestFocus();
                        return;
                    }
                }
                this.calc = 0;
                this.recordInFile(this.cb.getValue(), this.testPath + System.getProperty(this.separator) + "ответы2", true);
                this.recordInFile("\n" + this.dayDateAndTime() + "\n" + this.userName() + "\n" + this.resultsTest(), this.testPath + System.getProperty(separator) + "результаты" + System.getProperty(separator) + userName(), true);
                this.showMessage(this.resultsTest(), "Тест завершен", "Результат");
                this.recordInFile("", this.testPath + System.getProperty(this.separator) + "ответы2", false);
                this.cleanAll();
                this.b.setText("следующий вопрос");
                this.b.setDisable(true);
                this.t.setEditable(false);
                return;
            }
            this.t.setText(this.readerFile(this.testPath + System.getProperty(this.separator) + this.calc));
            this.recordInFile(this.cb.getValue(), this.testPath + System.getProperty(this.separator) + "ответы2", true);
            this.l3.setText("Вопрос № " + this.calc + ". Осталось вопросов: " + (this.volumeAnswers("ответы1") - this.calc));
            this.cb.setValue("");
        }
    }

    public void openItem() {
        final DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Открытие теста");
        dc.setInitialDirectory(directoryTests);
        file = dc.showDialog(null);
        if (file != null) {
            if (file.equals(directoryTests)){
                file=null;
                return;
            }
            this.testPath = file.getAbsolutePath();
            if (!new File(this.testPath + System.getProperty(this.separator) + "ответы1").exists()) {
                this.alertWindow("", "Тест " + file.getName() + " пока пустой.\nДополните его.", "Внимание!");
                this.cleanAll();
                this.t.setEditable(false);
                this.b.setText("следующий вопрос");
                this.b.setDisable(true);
                return;
            }
            if (this.volumeAnswers("ответы2") != 0) {
                final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("ВНИМАНИЕ!");
                alert.setHeaderText("Тест не пройден!");
                alert.setContentText("Хотите продолжить?");
                final Optional<ButtonType> resultAlert = alert.showAndWait();
                if (resultAlert.get() == ButtonType.OK) {
                    this.t.setText(this.readerFile(this.testPath + System.getProperty(this.separator) + (this.volumeAnswers("ответы2") + 1)));
                    this.l3.setText("Вопрос № " + (this.volumeAnswers("ответы2") + 1) + ". Осталось вопросов: " + (this.volumeAnswers("ответы1") - this.volumeAnswers("ответы2") - 1));
                    this.l1.setText(file.getName());
                    this.l2.setText("тестирование");
                    if (this.volumeAnswers("ответы1") - this.volumeAnswers("ответы2") - 1 == 0) {
                        this.b.setText("результат");
                    }
                    else {
                        this.b.setText("следующий вопрос");
                    }
                    this.b.setDisable(false);
                    this.t.setEditable(false);
                    this.calc = this.volumeAnswers("ответы2") + 1;
                    this.r = 0;
                    return;
                }
            }
            this.t.setText(this.readerFile(this.testPath + System.getProperty(this.separator) + "1"));
            this.recordInFile("", this.testPath + System.getProperty(this.separator) + "ответы2", false);
            this.l3.setText("Вопрос № 1. Осталось вопросов: " + (this.volumeAnswers("ответы1") - 1));
            this.l1.setText(file.getName());
            this.l2.setText("тестирование");
            if (this.volumeAnswers("ответы1") - 1 == 0) {
                this.b.setText("результат");
            }
            else {
                this.b.setText("следующий вопрос");
            }
            this.b.setDisable(false);
            this.t.setEditable(false);
            this.calc = 1;
            this.r = 0;
        }
    }
    public void closeItem(){
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Вопрос");
        alert.setHeaderText("");
        alert.setContentText("Перевести программу в исходное состояние?");
        final Optional<ButtonType> resultAlert = alert.showAndWait();
        if (resultAlert.get() == ButtonType.OK) {
            this.cleanAll();
            this.t.setEditable(false);
            this.b.setText("следующий вопрос");
            this.b.setDisable(true);
        }
    }
    public void createItem() {
        final TextInputDialog dialog = new TextInputDialog(new Random().nextInt(10000)+"");
        dialog.setTitle("Создание теста");
        dialog.setHeaderText("Введите название нового теста");
        dialog.setContentText("Название теста:");
        final Optional<String> name = dialog.showAndWait();
        if (name.isPresent() && !"".equals(name.get())) {
            this.testPath = this.directoryTests.getAbsolutePath() + System.getProperty(this.separator) + name.get();
            file = new File(this.testPath);
            if (file.exists()) {
                this.alertWindow("", "Тест с таким названием уже имеется\nВыберите другое название", "Ошибка");
                this.cleanAll();
                this.t.setEditable(false);
                this.b.setText("следующий вопрос");
                this.b.setDisable(true);
                return;
            }
            file.mkdir();
            new File(testPath + System.getProperty(separator) + "результаты").mkdir();
            this.l1.setText(file.getName());
            this.l2.setText("конструирование");
            this.l3.setText("");
            this.b.setText("следующий вопрос");
            this.b.setDisable(false);
            this.t.setEditable(true);
            this.t.clear();
            this.r = 1;
        }
    }

    public void appendItem() {
        final DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Дополнение теста");
        dc.setInitialDirectory(directoryTests);
        file = dc.showDialog(null);
        if (file != null) {
            if (file.equals(directoryTests)){
                file=null;
                return;
            }
            this.testPath = file.getAbsolutePath();
            this.l1.setText(file.getName());
            this.l2.setText("конструирование");
            this.l3.setText("");
            this.b.setText("следующий вопрос");
            this.b.setDisable(false);
            this.t.setEditable(true);
            this.t.clear();
            this.r = 1;
        }
    }
    public void deleteItem() {
        if("тестирование".equals(l2.getText())){
            alertWindow("", "Удаление тестов недоступно в режиме тестирования", "Сообщение");
            return;
        }
        if (file == null) {
            final DirectoryChooser dc = new DirectoryChooser();
            dc.setTitle("Удаление теста");
            dc.setInitialDirectory(directoryTests);
            File f = dc.showDialog(null);
            if (f!=null) {
                if (f.equals(directoryTests)){
                    return;
                }
                final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Вопрос");
                alert.setHeaderText("");
                alert.setContentText("Удалить тест " + f.getName() + "?");
                final Optional<ButtonType> resultAlert = alert.showAndWait();
                if (resultAlert.get() == ButtonType.OK) {
                    deleteDirectory(f);
                }
              }
            } else {
                final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Вопрос");
                alert.setHeaderText("Удаление теста");
                alert.setContentText("Удалить тест " + file.getName() + "?");
                final Optional<ButtonType> resultAlert = alert.showAndWait();
                if (resultAlert.get() == ButtonType.OK) {
                    deleteDirectory(file);
                    cleanAll();
                    this.b.setText("следующий вопрос");
                    this.b.setDisable(true);
                    this.t.setEditable(false);
                }
            }
        }
    public void authorInfoItem() {
       alertWindow("Об Авторе","Автор: Крючков Алексей","");
    }

    public void programInfoItem() {
        alertWindow("О Программе","Название: Конструктор Текстовых Тестов\nВерсия: 7.0","");
    }

    public void resultsOpenItem() {
        if("тестирование".equals(l2.getText())){
            alertWindow("", "Просмотр результатов недоступен в режиме тестирования", "Сообщение");
            return;
        }
        final DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Просмотр результатов");
        dc.setInitialDirectory(directoryTests);
        final File file1 = dc.showDialog(null);
        if (file1 != null) {
            final FileChooser fc = new FileChooser();
            fc.setTitle("Выбор файла для просмотра");
            fc.setInitialDirectory(new File(file1+System.getProperty(separator)+"результаты"));
            final File file2 = fc.showOpenDialog(null);
            if(file2 != null){
                this.showMessage(this.readerFile(file2.getAbsolutePath()), "Результаты для теста " + file1.getName()+"\nТестируемый: "+file2.getName(), "Результаты");
            }
        }
    }
    public void resultsDeleteItem(){
        if("тестирование".equals(l2.getText())){
            alertWindow("", "Удаление результатов недоступно в режиме тестирования", "Сообщение");
            return;
        }
        final DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Удаление результатов");
        dc.setInitialDirectory(directoryTests);
        final File file1 = dc.showDialog(null);
        if (file1 != null) {
            final FileChooser fc = new FileChooser();
            fc.setTitle("Выбор файла для удаления");
            fc.setInitialDirectory(new File(file1+System.getProperty(separator)+"результаты"));
            final File file2 = fc.showOpenDialog(null);
            if(file2 != null){
                final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setResizable(true);
                alert.setTitle("Вопрос");
                alert.setHeaderText("");
                alert.setContentText("Удалить файл результата для теста " + file1.getName() + "?\nТестируемый: "+file2.getName());
                final Optional<ButtonType> resultAlert = alert.showAndWait();
                if (resultAlert.get() == ButtonType.OK) {
                      if(file2.delete()){
                          toast.setMessage("Файл удален");
                   }
                }
            }
        }
    }
    public void exitItem() {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ВЫХОД");
        alert.setHeaderText("Выход из программы");
        alert.setContentText("Вы действительно хотите выйти из программы?");
        final Optional<ButtonType> resultAlert = alert.showAndWait();
        if (resultAlert.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void instructionItem() {
        this.showMessage(this.readerInstruction(), "", "Инструкция");
    }
    private boolean isEmpty(String s){
        return s == null || s.trim().length() == 0;
    }

    private static void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;
            for (String aChildren : children) {
                File f = new File(dir, aChildren);
                deleteDirectory(f);
            }
        }
        dir.delete();
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

    private String userName() {
        if (isEmpty(this.tf.getText())) {
            return "anonym";
        }
        return this.tf.getText();
    }

    private void alertWindow(final String s, final String s2, final String str) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(true);
        alert.setTitle(str);
        alert.setHeaderText(s);
        alert.setContentText(s2);
        alert.showAndWait();
    }

    private String readerLine(final int m, final String s) {
        String str = null;
        try {
            final File file2 = new File(this.testPath + System.getProperty(this.separator) + s);
            final BufferedReader br;
            try (FileReader fr = new FileReader(file2)) {
                br = new BufferedReader(fr);
                for (int i = 0; i < m; ++i) {
                    str = br.readLine();
                }
            }
            br.close();
        }
        catch (IOException e) {
            e.getMessage();
        }
        return str;
    }

    private String readerInstruction() {
        StringBuilder f = new StringBuilder();
        try {
            final InputStream is = ClassLoader.getSystemResourceAsStream("res/textes/instruction");
            final InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
            try (final BufferedReader br = new BufferedReader(isr)) {
                String str;
                while ((str = br.readLine()) != null) {
                    f.append(str).append("\n");
                }
                is.close();
                isr.close();
            }
        }
        catch (IOException e) {
            e.getMessage();
        }
        return f.toString();
    }

    private void cleanAll() {
        this.t.clear();
        this.tf.clear();
        this.cb.setValue("");
        this.l1.setText("");
        this.l2.setText("");
        this.l3.setText("");
        file=null;
    }

    private String resultsTest() {
        StringBuilder str = new StringBuilder();
        int m = 0;
        for (int i = 0; i < this.volumeAnswers("ответы1"); ++i) {
            str.append(i+1).append(") ").append("Вопрос: \n").append(readerFile(this.testPath + System.getProperty(this.separator) + (i+1))).append("\nВерный ответ: ").append(this.readerLine(i + 1, "ответы1")).append("\nОтвет тестируемого: ").append(this.readerLine(i + 1, "ответы2")).append("    ");
            if (this.readerLine(i + 1, "ответы1").equals(this.readerLine(i + 1, "ответы2"))) {
                ++m;
                str.append("ВЕРНЫЙ ОТВЕТ\n\n");
            }else{
                str.append("ОШИБКА!\n\n");
            }
        }
        str.append("Верных ответов: ").append(m).append("\nНеверных ответов: ").append(this.volumeAnswers("ответы1") - m).append("\nПроцент верных ответов: ").append(m * 100 / this.volumeAnswers("ответы1"));
        return str.toString();
    }

    private int volumeAnswers(final String sf) {
        int m = 0;
        try {
            final File file3 = new File(this.testPath + System.getProperty(this.separator) + sf);
            final BufferedReader br;
            try (FileReader fr = new FileReader(file3)) {
                br = new BufferedReader(fr);
                while (br.readLine() != null) {
                    ++m;
                }
            }
            br.close();
        }
        catch (IOException e) {
            e.getMessage();
        }
        return m;
    }

    private String readerFile(final String s) {
        StringBuilder f = new StringBuilder();
        try {
            final File file4 = new File(s);
            final BufferedReader br;
            try (FileReader fr = new FileReader(file4)) {
                br = new BufferedReader(fr);
                String str;
                while ((str = br.readLine()) != null) {
                    f.append(str).append("\n");
                }
            }
            br.close();
        }
        catch (IOException e) {
            e.getMessage();
        }
        return f.toString();
    }

    private void recordInFile(final String text, final String fileName, final boolean b) {
        try (final FileWriter sw = new FileWriter(fileName, b)) {
            String st;
            if ("".equals(text)) {
                st = "";
            }
            else {
                st = "\n";
            }
            sw.write(text + st);
        }
        catch (Exception e) {
            e.getMessage();
        }
    }
    private void showMessage(final String s, final String s1, final String s2) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(true);
        alert.setTitle(s2);
        alert.setHeaderText(s1);
        final TextArea ta = new TextArea(s);
        ta.setEditable(false);
        ta.setWrapText(true);
        ta.setMaxWidth(Double.MAX_VALUE);
        ta.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(ta, Priority.ALWAYS);
        GridPane.setHgrow(ta, Priority.ALWAYS);
        final GridPane gp = new GridPane();
        gp.setMaxWidth(Double.MAX_VALUE);
        gp.add(ta, 0, 0);
        alert.getDialogPane().setContent((Node)gp);
        alert.showAndWait();
    }
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        String parentPath=null;
        try {
            parentPath= URLDecoder.decode(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
        }
        directoryTests=new File(parentPath+System.getProperty(separator)+"Тесты");
        if (!directoryTests.exists()){
            directoryTests.mkdir();
        }
        this.cb.setItems(this.list);
        this.cb.setEditable(true);
        this.t.setEditable(false);
        this.cb.setValue("");
        this.b.setDisable(true);
    }
}
