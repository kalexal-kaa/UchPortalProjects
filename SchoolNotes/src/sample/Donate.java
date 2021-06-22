package sample;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.awt.EventQueue;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Desktop;
import java.awt.Font;

public class Donate extends JFrame {
    Donate() {
        super();
        this.initComponents();
    }

    private void initComponents() {
        JLabel jLabel1 = new JLabel();
        JButton jButton1 = new JButton();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Переход");
        jLabel1.setFont(new Font("Noto Sans", 1, 16));
        jLabel1.setText("Перейти на сайт Яндекс.Денег?");
        jButton1.setFont(new Font("Noto Sans",1,18));
        jButton1.setText("ПЕРЕЙТИ");
        jButton1.addActionListener(evt -> jButton1ActionPerformed());
        final GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(33, 33, 33).addComponent(jLabel1, -2, 334, -2).addContainerGap(33, 32767)).addComponent(jButton1, -1, -1, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(84, 84, 84).addComponent(jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 68, 32767).addComponent(jButton1, -2, 62, -2).addGap(66, 66, 66)));
        this.pack();
        setLocationRelativeTo(null);
    }

    private void jButton1ActionPerformed() {
        this.launchBrowser();
    }

    private void launchBrowser() {
        if (Desktop.isDesktopSupported()) {
            final Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    final URI uri = new URI("http://" + "money.yandex.ru/to/410013393034316");
                    desktop.browse(uri);
                }
                catch (IOException | URISyntaxException ex2) {
                    JOptionPane.showMessageDialog(null, "ERROR!", "WARNING", 0);
                }
            }
        }
    }

    public static void main(final String[] args) {
        try {
            for (final UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Donate.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(() -> new Donate().setVisible(true));
    }
}
