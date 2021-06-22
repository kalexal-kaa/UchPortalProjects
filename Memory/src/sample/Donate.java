package sample;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.awt.EventQueue;
import javax.swing.UnsupportedLookAndFeelException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import java.awt.Desktop;
import javax.swing.LayoutStyle;
import javax.swing.GroupLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Donate extends JFrame
{
    private JButton jButton1;
    private JLabel jLabel1;

    public Donate() {
        super();
        this.initComponents();
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jButton1 = new JButton();
        this.setDefaultCloseOperation(2);
        this.setTitle("Переход");
        this.jLabel1.setFont(new Font("Noto Sans", 1, 14));
        this.jLabel1.setText("Перейти на сайт Яндекс.Денег?");
        this.jButton1.setText("ПЕРЕЙТИ");
        this.jButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        final GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(33, 33, 33).addComponent(this.jLabel1, -2, 334, -2).addContainerGap(33, 32767)).addComponent(this.jButton1, -1, -1, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(84, 84, 84).addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 68, 32767).addComponent(this.jButton1, -2, 62, -2).addGap(66, 66, 66)));
        this.pack();
    }

    private void jButton1ActionPerformed(final ActionEvent evt) {
        this.launchBrowser("money.yandex.ru/to/410013393034316");
    }

    private void launchBrowser(final String uriStr) {
        if (Desktop.isDesktopSupported()) {
            final Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    final URI uri = new URI("http://" + uriStr);
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
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Donate.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex2) {
            Logger.getLogger(Donate.class.getName()).log(Level.SEVERE, null, ex2);
        }
        catch (IllegalAccessException ex3) {
            Logger.getLogger(Donate.class.getName()).log(Level.SEVERE, null, ex3);
        }
        catch (UnsupportedLookAndFeelException ex4) {
            Logger.getLogger(Donate.class.getName()).log(Level.SEVERE, null, ex4);
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Donate().setVisible(true);
            }
        });
    }
}


