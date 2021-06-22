package sample;
import javafx.fxml.*;
import javafx.collections.*;
import javafx.scene.control.*;
import java.nio.charset.*;
import java.io.*;
import java.net.*;
import java.util.*;
public class Controller implements Initializable{
    @FXML
    private ListView<String> list;
    @FXML
    private TextArea t;
    private ObservableList<String> proverbsList;

    public Controller() {
        this.proverbsList = FXCollections.observableArrayList("труд", "трусость", "здоровье", "времена года", "вежливость", "деньги", "добро", "мастерство", "свобода", "бедность и богатство", "счастье и радость", "смелость", "молодость и старость", "будущее", "обещания");
    }

    @FXML
    private void listItemAction() {
        this.t.setText(this.reader(this.list.getSelectionModel().getSelectedIndex() + 1));
    }

    @FXML
    private void programInfo() {
        this.informationWindow("Название: Пословицы и Поговорки\nВерсия: 1.0", "О программе");
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

    private String reader(final int n) {
        String f = "";
        try {
            final InputStream is = ClassLoader.getSystemResourceAsStream("res/textes/" + n);
            final InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
            try (final BufferedReader br = new BufferedReader(isr)) {
                String str;
                while ((str = br.readLine()) != null) {
                    f = f + str + "\n\n";
                }
            }
        }
        catch (IOException e) {
            e.getMessage();
        }
        return f;
    }

    private void informationWindow(final String s, final String str) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(str);
        alert.setHeaderText("");
        alert.setContentText(s);
        alert.showAndWait();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.list.setItems(this.proverbsList);
        this.t.setText("ДОБРО ПОЖАЛОВАТЬ!!!");
    }
}
