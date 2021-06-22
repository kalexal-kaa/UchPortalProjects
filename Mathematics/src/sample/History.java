package sample;

import java.awt.EventQueue;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.awt.Color;
import java.awt.Font;

public final class History extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JTextArea t;

    History() {
        super();
        this.initComponents();
        final Controller mathController = new Controller();
        mathController.setPath(mathController.pathToDirectory());
        this.t.setText(this.readerHistory(mathController.getPath()));
    }

    private void initComponents() {
        JScrollPane jScrollPane1 = new JScrollPane();
        this.t = new JTextArea();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("История");
        this.t.setEditable(false);
        this.t.setColumns(20);
        this.t.setFont(new Font("Ubuntu", 1, 15));
        this.t.setRows(5);
        this.t.setBorder(BorderFactory.createLineBorder(new Color(218, 230, 215), 15));
        this.t.setFocusable(false);
        jScrollPane1.setViewportView(this.t);
        final GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 591, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jScrollPane1, -1, 469, 32767));
        this.pack();
        setLocationRelativeTo(null);
    }

    private String readerHistory(final String p) {
        StringBuilder f = new StringBuilder();
        try {
            final File file = new File(p + System.getProperty("file.separator") + "history");
            final FileReader fr = new FileReader(file);
            try (final BufferedReader br = new BufferedReader(fr)) {
                String str;
                while ((str = br.readLine()) != null) {
                    f.append(str).append("\n");
                }
                fr.close();
            }
        }
        catch (IOException e) {
            e.getMessage();
        }
        return f.toString();
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
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex3) {
            final Exception ex = null;
            Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(() -> new History().setVisible(true));
    }

}

