package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ListView<String> list1,list2;
    @FXML
    private TextArea text;
    private int indikator;
    private String path,separator;
    private Toast toast;

    public Controller() {
        this.indikator=0;
        this.separator = System.getProperty("file.separator");
        this.toast=new Toast();
    }

    @FXML
    private void list1Action(){
        showNotes(path+separator+list1.getSelectionModel().getSelectedItem());
        text.clear();
    }
    @FXML
    private void list2Action(){
        text.setText(reader(path+separator+list1.getSelectionModel().getSelectedItem()+separator+list2.getSelectionModel().getSelectedItem()));
    }
    @FXML
    private void add1() {
        final TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Создание группы");
        dialog.setHeaderText("Введите название новой группы");
        dialog.setContentText("Название группы:");
        final Optional<String> name = dialog.showAndWait();
        if (name.isPresent() && !"".equals(name.get())) {
            File file = new File(path+separator+name.get());
            if (file.exists()) {
                alertWindow("Группа с таким названием уже имеется\nВыберите другое название");
                return;
            }
            file.mkdir();
            if (file.exists()) {
                showNotes(file.getParent());
                list1.getSelectionModel().select(file.getName());
                list2.setItems(null);
                text.clear();
            }else {
                alertWindow("Ошибка!\nВозможно вы ввели недопустимые символы");
            }
        }

    }
    @FXML
    private void add2() throws IOException {
        String l=list1.getSelectionModel().getSelectedItem();
        if (l==null){
            alertWindow("Выберите группу");
            return;
        }
        final TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Создание записи");
        dialog.setHeaderText("Введите название новой записи");
        dialog.setContentText("Название записи:");
        final Optional<String> name = dialog.showAndWait();
        if (name.isPresent() && !"".equals(name.get())) {
            if (incorrectSymbols(name.get())){
                alertWindow("Запись не будет создана:\nОбнаружены недопустимые символы в названии записи");
                return;
            }
            File file = new File(path+separator+l+separator+name.get());
            if (file.exists()) {
                alertWindow("Запись не будет создана:\nЗапись с таким названием уже имеется");
                return;
            }
            if (file.createNewFile()){
                showNotes(file.getParent());
                list2.getSelectionModel().select(file.getName());
                text.clear();
            }
        }
    }
    @FXML
    private void del1(){
        String l=list1.getSelectionModel().getSelectedItem();
        if (l==null){
            alertWindow("Выберите группу");
            return;
        }
        File file=new File(path+separator+l);
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтвердить");
        alert.setHeaderText("Удаление группы");
        alert.setContentText("Удалить группу " + file.getName() + "?");
        final Optional<ButtonType> resultAlert = alert.showAndWait();
        if (resultAlert.get() == ButtonType.OK) {
            deleteDirectory(file);
            if (!file.exists()) {
                showNotes(path);
                list2.setItems(null);
                text.clear();
            }else {
                toast.setMessage("ошибка удаления");
            }
        }
    }
    @FXML
    private void del2(){
        String l1=list1.getSelectionModel().getSelectedItem();
        String l2=list2.getSelectionModel().getSelectedItem();
        if (l2==null){
            alertWindow("Выберите запись");
            return;
        }
        File file=new File(path+separator+l1+separator+l2);
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтвердить");
        alert.setHeaderText("Удаление записи");
        alert.setContentText("Удалить запись " + file.getName() + "?");
        final Optional<ButtonType> resultAlert = alert.showAndWait();
        if (resultAlert.get() == ButtonType.OK) {
            if (file.delete()) {
                showNotes(file.getParent());
                text.clear();
            }else {
                toast.setMessage("ошибка удаления");
            }
        }
    }
    @FXML
    private void saveAction(){
        String l1=list1.getSelectionModel().getSelectedItem();
        String l2=list2.getSelectionModel().getSelectedItem();
        if (l1==null){
            alertWindow("Выберите или создайте группу");
            saveInClipboard();
            return;
        }
        if(l2==null){
            alertWindow("Выберите или создайте запись");
            saveInClipboard();
            return;
        }
        if(isEmpty(text.getText())){
            toast.setMessage("ничего нет для сохранения");
            return;
        }
        writer(path + separator + l1+separator+l2 , text.getText());
        if(indikator==0){
           toast.setMessage("сохранено");
        }else{
            toast.setMessage("ошибка сохранения");
            indikator=0;
        }
    }
    @FXML
    private void authorInfo(){
        startDonate();
    }
    private void saveInClipboard(){
        if(!isEmpty(text.getText())){
            StringSelection ss = new StringSelection(text.getText());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
            toast.setMessage("сохранено в буфер обмена");
        }
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
    private void writer(String pathFile,String text){
        try (final FileWriter fw = new FileWriter(pathFile)) {
            fw.write(text);
        } catch (IOException e) {
            e.getMessage();
            indikator=1;
        }
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
    private void showNotes(String p){
        File files=new File(p);
        File[] f=files.listFiles();
        assert f != null;
        String[] notes=new String[f.length];
        for(int i=0;i<notes.length;i++){
            notes[i]=f[i].getName();
        }
        if (p.equals(path)) {
            list1.setItems(FXCollections.observableArrayList(notes).sorted());
        }else {
            list2.setItems(FXCollections.observableArrayList(notes).sorted());
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
    private boolean incorrectSymbols(String str){
            return str.contains(":")||str.contains("*")||str.contains("/")||str.contains("|")
                    ||str.contains("?")||str.contains("#")||str.contains("!")||str.contains("$")||str.contains("%")||str.contains(";");
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
    private void dirCreator(final String fPath) {
        final File file = new File(fPath);
        if (!file.exists()) {
            file.mkdir();
            if(file.exists()){
                alertWindow("Создан каталог <GroupNoteBook>.\nВаши группы заметок будут здесь:\n"+fPath);
            }else{
                alertWindow("Ошибка!\nКаталог <GroupNoteBook> не будет создан.\n" +
                        "Попробуйте создать указанный каталог вручную по следующему пути:\n"+fPath+"\nПрограмма будет закрыта.");
                System.exit(0);
            }
        }
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
    @Override
    public void initialize(URL url, ResourceBundle rb){
        String parentPath=null;
        try {
            parentPath= URLDecoder.decode(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
        }
        path=parentPath+separator+"GroupNoteBook";
        this.dirCreator(this.path);
        File f=new File(path);
        if(permissionRead(f)||permissionWrite(f)){
            if(permissionRead(f)&&permissionWrite(f)){
                alertWindow("Не удалось получить разрешение на чтение и запись файлов в каталог <GroupNoteBook>.\nПопробуйте дать разрешение вручную.");
            }else if(permissionRead(f)){
                alertWindow("Не удалось получить разрешение на чтение файлов в каталоге <GroupNoteBook>.\nПопробуйте дать разрешение вручную.");
            }else{
                alertWindow("Не удалось получить разрешение на запись файлов в каталог <GroupNoteBook>.\nПопробуйте дать разрешение вручную.");
            }
            System.exit(0);
        }
        showNotes(path);
    }
}
