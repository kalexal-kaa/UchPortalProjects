package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

public class Controller {
      private File directoryFb2,fileTxt,fileImage;
      @FXML
      private TextField bookName,authorFirstName,authorLastName,authorMiddleName,fileName,txtPath,fb2Path,imagePath;
      @FXML
      private TextArea annotation;
      @FXML
      private CheckBox cb;
      private BufferedImage image;
      private final BookMaker bm;
      private final Toast toast;
      private String fb2Name;

    public Controller() {
        this.bm = new BookMaker();
        this.toast = new Toast();
    }

    @FXML
      private void bookCreate() throws IOException {
         String bookTitle = bookName.getText();
          if (isEmpty(bookTitle)){
              alert("Введите название");
              bookName.requestFocus();
              return;
          }
          if (fileTxt==null){
              alert("Укажите путь до исходного txt-файла");
              txtPath.requestFocus();
              return;
          }
          if (directoryFb2==null){
              alert("Укажите директорию для сохранения готового файла");
              fb2Path.requestFocus();
              return;
          }
          String stringImage,msgImage,msgAnnotation,msgAuthor;
          String firstName=authorFirstName.getText();
          String middleName=authorMiddleName.getText();
          String lastName=authorLastName.getText();
          String fileNameText=fileName.getText();
          String annotationText=annotation.getText();
          if (isEmpty(firstName)&&isEmpty(middleName)&&isEmpty(lastName)){
              msgAuthor="Автор: не указан";
          }else {
              msgAuthor="Автор: "+firstName+" "+middleName+" "+lastName;
          }
          if (isEmpty(fileNameText)){
              fb2Name= bookTitle;
          }else {
              if (fileNameText.contains(".fb2")) {
                  fb2Name = removeFormat(fileNameText);
              }else {
                  fb2Name = fileNameText;
              }
          }
          if (isEmpty(annotationText)){
              msgAnnotation="Аннотация: без аннотации";
          }else {
              msgAnnotation="Аннотация: заполнено";
          }
          if (!cb.isSelected()) {
              if (fileImage == null) {
                  stringImage = encodeToString(ImageIO.read(getClass().getResource("/images/icon.png")));
                  msgImage="Обложка: обложка по умолчанию(иконка приложения)";
              } else {
                  stringImage = encodeToString(image);
                  msgImage="Обложка: "+fileImage.getAbsolutePath();
              }
          }else {
              stringImage=null;
              msgImage="Обложка: без обложки";
          }
          String fb2=bm.fb2Creator(firstName,middleName,lastName, bookTitle,annotationText,getDate(),reader(fileTxt.getAbsolutePath()),stringImage);
          setConfirmation("Название книги: "+ bookTitle +"\n"+msgAuthor+"\n"+msgAnnotation+"\n"+msgImage+"\n" +
                  "Будет создан файл с именем "+fb2Name.replace(" ","_")+".fb2"+
                  " в указанной директории "+directoryFb2.getAbsolutePath(),fb2);
      }
      @FXML
      private void pathToTXT(){
          final FileChooser fc = new FileChooser();
          fc.setTitle("Выбрать txt-файл");
          fc.setInitialDirectory(null);
          FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files", "*.txt");
          fc.getExtensionFilters().add(extFilter);
          fileTxt = fc.showOpenDialog(null);
          if (fileTxt != null) {
              txtPath.setText(fileTxt.getAbsolutePath());
              if (isEmpty(bookName.getText())) {
                  bookName.setText(removeFormat(fileTxt.getName()));
              }
              if (isEmpty(fileName.getText())){
                  fileName.setText(removeFormat(fileTxt.getName()).replace(" ","_")+".fb2");
              }
              if (directoryFb2==null){
                  directoryFb2=new File(fileTxt.getParent());
                  fb2Path.setText(directoryFb2.getAbsolutePath());
              }
          }
      }
      @FXML
      private void pathToFb2(){
          final DirectoryChooser fc = new DirectoryChooser();
          fc.setTitle("Где сохранить готовый файл?");
          fc.setInitialDirectory(null);
          directoryFb2 = fc.showDialog(null);
          if (directoryFb2 != null) {
              fb2Path.setText(directoryFb2.getAbsolutePath());
          }
      }
      @FXML
      private void pathToImage() throws IOException {
          final FileChooser fc = new FileChooser();
          fc.setTitle("Выбрать файл для обложки(png/jpg)");
          fc.setInitialDirectory(null);
          FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("IMAGE files(png/jpg)", "*.png","*.jpg");
          fc.getExtensionFilters().add(extFilter);
          fileImage = fc.showOpenDialog(null);
          if (fileImage != null) {
              image=ImageIO.read(fileImage);
              imagePath.setText(fileImage.getAbsolutePath());
          }
      }
      @FXML
      private void clearImagePath(){
        imagePath.clear();
        fileImage=null;
      }
      @FXML
      private void clearTxtPath(){
        txtPath.clear();
        fileTxt=null;
      }
      @FXML
      private void clearFb2Path(){
        fb2Path.clear();
        directoryFb2=null;
      }
      @FXML
      private void authorInfo() {
          alert("Автор: Алексей Крючков\nTXTtoFB2Converter\nВерсия: 4");
    }
    private String encodeToString(BufferedImage image) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "png", bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.getMessage();
        }
        return imageString;
    }
    private void alert(final String s) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(400, 120);
        alert.setTitle("");
        alert.setHeaderText("");
        alert.setContentText(s);
        alert.showAndWait();
    }
    private String removeFormat(String s){
        String name = s;
        int i=s.lastIndexOf(".");
        if(i!=-1){
            name=s.substring(0,i);
        }
        return name.replace("_", " ");
    }
    private void setConfirmation(String s,String s1) {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(600,240);
        alert.setTitle("Подтвердить");
        alert.setHeaderText("Создать книгу?");
        alert.setContentText(s);
        final Optional<ButtonType> resultAlert = alert.showAndWait();
        if (resultAlert.get() == ButtonType.OK) {
            writer(directoryFb2.getAbsolutePath()+System.getProperty("file.separator")+fb2Name.replace(" ","_")+".fb2",s1);
            toast.setMessage("Создание книги");
        }else{
            toast.setMessage("Создание книги отменено");
        }
    }
    private String getDate(){
          final Calendar calendar = new GregorianCalendar();
          final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
              return sdf.format(calendar.getTime());
      }
    private boolean isEmpty(String p){
        return p.trim().length()==0;
    }
    private boolean isWindows() {
        final String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win");
    }
    private void writer(String pathFile, String text){
        try (final PrintWriter pw = new PrintWriter(pathFile, "UTF-8")) {
            pw.write(text);
        } catch (IOException e) {
            e.getMessage();
        }
    }
    private String reader(String s) {
        String charsetName;
        if(isWindows()){
            charsetName = "Windows-1251";
        }else{
            charsetName = "UTF-8";
        }
        StringBuilder f = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(s), charsetName))){
            String str;
            while ((str = br.readLine()) != null) {
                f.append("<p>").append(str).append("</p>");
            }
            br.close();
        }
        catch (IOException e) {
            e.getMessage();
        }
        return f.toString();
    }
}
