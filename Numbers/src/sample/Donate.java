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

public class Donate extends JFrame
{

    public Donate() {
        super();
        this.initComponents();
    }

    private void initComponents() {
        JLabel jLabel = new JLabel();
        JButton jButton = new JButton();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Поддержать Автора");
        jLabel.setFont(new Font("Noto Sans", 1, 16));
        jLabel.setText("Перейти на сайт Яндекс.Денег?");
        jButton.setFont(new Font("Noto Sans",1,18));
        jButton.setText("ПЕРЕЙТИ");
        jButton.addActionListener(evt -> jButtonActionPerformed());
        final GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(33, 33, 33).addComponent(jLabel, -2, 334, -2).addContainerGap(33, 32767)).addComponent(jButton, -1, -1, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(84, 84, 84).addComponent(jLabel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 68, 32767).addComponent(jButton, -2, 62, -2).addGap(66, 66, 66)));
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void jButtonActionPerformed() {
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
                    final Exception ioe = null;
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
        catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException ex) {
            Logger.getLogger(Donate.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(() -> new Donate().setVisible(true));
    }
}


