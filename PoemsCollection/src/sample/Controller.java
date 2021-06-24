package sample;
import javafx.fxml.*;
import javafx.collections.*;
import javafx.scene.control.*;
import java.nio.charset.*;
import java.io.*;
import javafx.scene.image.*;
import java.net.*;
import java.util.*;
public class Controller implements Initializable {
    @FXML
    private ListView<String> list;
    @FXML
    private ImageView iv;
    @FXML
    private TextArea t;
    @FXML
    private Button b;
    private ObservableList<String> autorList;
    private String[] autorMas;
    private int a,dir;

    public Controller() {
        this.a = 0;
        this.autorMas = new String[] { "пушкин", "некрасов", "есенин", "тютчев", "фет", "маяковский", "высоцкий", "бальмонт", "рождественский", "ахматова", "цветаева", "бунин" };
        this.autorList = FXCollections.observableArrayList(this.autorMas);
    }

    @FXML
    private void listItemAction() {
        if (this.a == 0) {
            this.dir = this.list.getSelectionModel().getSelectedIndex() + 1;
            final String[] poems = new String[12];
            for (int i = 0; i < 12; ++i) {
                poems[i] = this.reader(this.dir, i + 1, false);
            }
            this.list.setItems(FXCollections.observableArrayList(poems));
            this.t.setText(this.reader(this.dir, 0, true));
            this.showImage(this.dir);
            this.b.setDisable(false);
            this.a = 1;
        }
        else {
            this.t.setText(this.reader(this.dir, this.list.getSelectionModel().getSelectedIndex() + 1, true));
        }
    }

    @FXML
    private void backAction() {
        this.list.setItems(FXCollections.observableArrayList(this.autorMas));
        this.t.setText("");
        this.showImage(13);
        this.b.setDisable(true);
        this.a = 0;
    }

    @FXML
    private void programInfo() {
        this.informationWindow("Название: Стихотворения\nВерсия: 1.0", "О программе");
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

    String reader(final int m, final int n, final boolean p) {
        String f = "";
        try {
            final InputStream is = ClassLoader.getSystemResourceAsStream("res/textes/" + m + "/" + n);
            final InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
            try (final BufferedReader br = new BufferedReader(isr)) {
                if (p) {
                    String str;
                    while ((str = br.readLine()) != null) {
                        f = f + str + "\n";
                    }
                }
                else {
                    f = br.readLine();
                }
            }
        }
        catch (IOException e) {
            e.getMessage();
        }
        return f;
    }

    void informationWindow(final String s, final String str) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(str);
        alert.setHeaderText("");
        alert.setContentText(s);
        alert.showAndWait();
    }

    void showImage(final int n) {
        final Image i = new Image("res/images/" + n + ".jpg");
        this.iv.setImage(i);
    }

    public void initialize(final URL url, final ResourceBundle rb) {
        this.list.setItems(this.autorList);
        this.t.setText("ДОБРО ПОЖАЛОВАТЬ!!!");
        this.showImage(13);
        this.b.setDisable(true);
    }
}
