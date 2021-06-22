package sample;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class Controller implements Initializable {
    @FXML
    private TextArea t;
    @FXML
    private ListView<String> list;

    private String path,separator;

    private int indikator;

    private final Toast toast;

    public Controller() {
        this.toast = new Toast();
        this.separator = System.getProperty("file.separator");
        this.indikator=0;
    }
    @FXML
    private void createNoteAction() throws IOException {
        String item=dateAndTime();
        if(isWindows()){
            item=item.replace(":","-");
        }
        File f=new File(path+separator+item);
        if(f.createNewFile()){
            showNotes();
        }
        list.getSelectionModel().select(item);
        t.clear();
    }
    @FXML
    private void saveNoteAction(){
        String l=list.getSelectionModel().getSelectedItem();
        if(l==null){
            alertWindow("Выберите заметку из списка слева.\nЕсли список пуст, то создайте новую заметку.");
            if(!isEmpty(t.getText())){
                StringSelection ss = new StringSelection(t.getText());
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
                toast.setMessage("Сохранено в буфер обмена");
            }
            return;
        }
        if(isEmpty(t.getText())){
            toast.setMessage("Ничего нет для сохранения");
            return;
        }
        writer(path + separator + l , t.getText());
        if(indikator==0){
            toast.setMessage("Сохранено");
        }else{
            toast.setMessage("Ошибка");
            indikator=0;
        }
    }
    @FXML
    private void deleteNoteAction() throws IOException {
        File f=new File(path+separator+list.getSelectionModel().getSelectedItem());
        if(f.delete()){
            showNotes();
            t.clear();
        }else{
            toast.setMessage("Ошибка удаления");
        }
    }
    @FXML
    private void listItemAction(){
        this.t.setText(this.reader(path+separator+this.list.getSelectionModel().getSelectedItem()));
    }
    @FXML
    private void authorInfo(){
        startDonate();
    }
    void exit(){
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(500,140);
        alert.setTitle("ВЫХОД");
        alert.setHeaderText("Выйти из программы?");
        alert.setContentText("Несохраненные изменения будут потеряны навсегда.");
        final Optional<ButtonType> resultAlert = alert.showAndWait();
        if (resultAlert.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
    private void alertWindow(final String s) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(400, 140);
        alert.setTitle("Внимание!");
        alert.setHeaderText("");
        alert.setContentText(s);
        alert.showAndWait();
    }
    private boolean isEmpty(String s){
        return s == null || s.trim().length() == 0;
    }
    private String dateAndTime() {
        final Calendar calendar = new GregorianCalendar();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy    hh:mm:ss");
        String dayName = "";
        switch (calendar.get(7)) {
            case 1:
                dayName = "Вс.";
                break;
            case 2:
                dayName = "Пн.";
                break;
            case 3:
                dayName = "Вт.";
                break;
            case 4:
                dayName = "Ср.";
                break;
            case 5:
                dayName = "Чт.";
                break;
            case 6:
                dayName = "Пт.";
                break;
            case 7:
                dayName = "Сб.";
                break;
        }
        return dayName + " " + sdf.format(calendar.getTime());
    }
    private void showNotes(){
        File files=new File(path);
        File[] f=files.listFiles();
        assert f != null;
        String[] notes=new String[f.length];
        for(int i=0;i<notes.length;i++){
            notes[i]=f[i].getName();
        }
        list.setItems(FXCollections.observableArrayList(notes).sorted());
    }
    private void startDonate(){
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Об Авторе");
        alert.setHeaderText("");
        alert.setContentText("Автор: Алекс Мирный\nМесто работы: КБЖБ\nХотите поддержать автора?");
        final Optional<ButtonType> resultAlert = alert.showAndWait();
        if (resultAlert.get() == ButtonType.OK) {
            new Donate().setVisible(true);
        }
    }
    private String reader(final String s) {
        StringBuilder f=new StringBuilder();
        try {
            final File file = new File(s);
            final BufferedReader br;
            try (FileReader fr = new FileReader(file)) {
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
    private void dirCreator(final String fPath) {
        final File file = new File(fPath);
        if (!file.exists()) {
            file.mkdir();
            if(file.exists()){
                alertWindow("Создан каталог <NoteBook>.\nВаши заметки будут здесь:\n"+fPath);
            }else{
                alertWindow("Ошибка!\nКаталог <NoteBook> не будет создан.\n" +
                        "Попробуйте создать указанный каталог вручную по следующему пути:\n"+fPath+"\nПрограмма будет закрыта.");
                System.exit(0);
            }
        }
    }
    private boolean isWindows() {
        final String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win");
    }
    private boolean permissionRead(File file){
        if(!file.canRead()){
            file.setReadable(true);
            return !file.canRead();
        }
        return false;
    }
    private boolean permissionWrite(File file){
        if(!file.canWrite()){
            file.setWritable(true);
            return !file.canWrite();
        }
        return false;
    }
    private void writer(String pathFile,String text){
        try (final FileWriter fw = new FileWriter(pathFile)) {
            fw.write(text);
        } catch (IOException e) {
            e.getMessage();
            indikator=1;
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String parentPath=null;
        try {
            parentPath=URLDecoder.decode(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
        }
        path=parentPath+separator+"NoteBook";
        this.dirCreator(this.path);
        File f=new File(path);
        if(permissionRead(f)||permissionWrite(f)){
            if(permissionRead(f)&&permissionWrite(f)){
                alertWindow("Не удалось получить разрешение на чтение и запись файлов в каталог <NoteBook>.\nПопробуйте дать разрешение вручную.");
            }else if(permissionRead(f)){
                alertWindow("Не удалось получить разрешение на чтение файлов в каталоге <NoteBook>.\nПопробуйте дать разрешение вручную.");
            }else{
                alertWindow("Не удалось получить разрешение на запись файлов в каталог <NoteBook>.\nПопробуйте дать разрешение вручную.");
            }
            System.exit(0);
        }
        showNotes();
    }
}
