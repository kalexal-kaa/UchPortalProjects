package sample;
import javafx.fxml.*;
import javafx.collections.*;
import javafx.scene.control.*;
import java.nio.charset.*;
import java.io.*;
import javafx.scene.image.*;
import java.net.*;
import java.util.*;
public class Controller implements Initializable{
    @FXML
    private ListView<String> list;
    @FXML
    private ImageView iv;
    @FXML
    private TextArea t;
    private ObservableList<String> chapterList;

    public Controller() {
        this.chapterList = FXCollections.observableArrayList("меркурий", "венера", "земля", "марс", "юпитер", "сатурн", "уран", "нептун", "плутон", "астероиды", "кометы", "солнце", "экзопланеты", "за пределами");
    }

    @FXML
    private void listItemAction() {
        final int selectedIndex = this.list.getSelectionModel().getSelectedIndex() + 1;
        this.t.setText(this.reader(selectedIndex));
        this.showImage(selectedIndex);
    }

    @FXML
    private void programInfo() {
        this.informationWindow("Название: Астрономия\nВерсия: 1.0", "О программе");
    }

    @FXML
    private void autorInfo() {
        this.informationWindow("Автор: Алексей Крючков\nМесто работы: КБЖБ", "Об авторе");
    }
    @FXML
    private void exit() {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ВЫХОД");
        alert.setHeaderText("Выход из программы");
        alert.setContentText("Вы действительно хотите выйти из программы?");
        final Optional<ButtonType> resultAlert = alert.showAndWait();
        if (resultAlert.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    String reader(final int n) {
        String f = "";
        try {
            final InputStream is = ClassLoader.getSystemResourceAsStream("res/textes/" + n + ".txt");
            final InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
            try (final BufferedReader br = new BufferedReader(isr)) {
                String str;
                while ((str = br.readLine()) != null) {
                    f = f + str + "\n";
                }
            }
        }
        catch (IOException e) {
            e.getMessage();
        }
        return f;
    }

    void showImage(final int n) {
        final Image i = new Image("res/images/" + n + ".jpg");
        this.iv.setImage(i);
    }

    void informationWindow(final String s, final String str) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(str);
        alert.setHeaderText("");
        alert.setContentText(s);
        alert.showAndWait();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.list.setItems(this.chapterList);
        this.t.setText("ДОБРО ПОЖАЛОВАТЬ!!!");
        this.showImage(15);
    }
}
