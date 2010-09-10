package decaf;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.TreePath;

/**
 * Editor de texto con pestañas para editar elcódigo fuente.
 */
public class Editor extends javax.swing.JFrame {

    /**
     * Lista de documentos abiertos.
     */
    java.util.ArrayList<Source> docs;

    /**
     * Constructor.
     */
    public Editor() {
        docs = new java.util.ArrayList<Source>();
        initComponents();
        new_btnActionPerformed(null);
    }

    /**
     * Guarda un documento. Si este no tiene un archivo relacionado
     * selecciona uno.
     */
    private void saveFile() {
        Source src = docs.get(tabbed_01.getSelectedIndex());
        if (src.io == null) {
            final javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
            int returnVal = fc.showSaveDialog(Editor.this);
            if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
                src.io = fc.getSelectedFile();
                if (!src.io.exists()) {
                    try {
                        src.io.createNewFile();
                    } catch (IOException ex) {
                        src.io = null;
                        status.setText("-- can't save file '" + src.title + "'");
                        return;
                    }
                }
                src.title = src.io.getName();
                if (src.save()) {
                    tabbed_01.setTitleAt(src.i, src.title);
                    status.setText("-- file '" + src.title + "' succesfully saved");
                } else {
                    status.setText("-- can't save file '" + src.title + "'");
                }
            } else {
                status.setText("-- save command cancelled by user '" + src.title + "'");
            }
        } else {
            if (src.save()) {
                status.setText("-- file '" + src.title + "' succesfully saved");
            } else {
                status.setText("-- can't save file '" + src.title + "'");
            }
        }
    }

    /**
     * Abre el archivo indicado en una nueva pestaña del editor.
     * @param fileName ruta absoluta del archivo a abrir.
     */
    private void openFile(String fileName) {
        Source src = Source.open(fileName, tabbed_01.getTabCount());
        if (src.load()) {
            docs.add(src);
            tabbed_01.addTab(src.title, new javax.swing.JScrollPane(src.txt));
            status.setText("-- file '" + src.title + "' succesfully loaded");
        } else {
            status.setText("-- can't load file '" + src.title + "'");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tool_bar = new javax.swing.JToolBar();
        new_btn = new javax.swing.JButton();
        open_btn = new javax.swing.JButton();
        save_btn = new javax.swing.JButton();
        parse_btn = new javax.swing.JButton();
        close_btn = new javax.swing.JButton();
        split_01 = new javax.swing.JSplitPane();
        split_02 = new javax.swing.JSplitPane();
        scroll_01 = new javax.swing.JScrollPane();
        tree_01 = new javax.swing.JTree();
        scroll_02 = new javax.swing.JScrollPane();
        tree_02 = new javax.swing.JTree(new FileSystemModel());
        split_03 = new javax.swing.JSplitPane();
        tabbed_01 = new javax.swing.JTabbedPane();
        scroll_03 = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        status = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Decaf Editor");

        tool_bar.setFloatable(false);
        tool_bar.setRollover(true);
        tool_bar.setMaximumSize(new java.awt.Dimension(176, 50));
        tool_bar.setMinimumSize(new java.awt.Dimension(176, 50));
        tool_bar.setPreferredSize(new java.awt.Dimension(100, 50));

        new_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/new.png"))); // NOI18N
        new_btn.setToolTipText("-- open a document");
        new_btn.setFocusable(false);
        new_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        new_btn.setMaximumSize(new java.awt.Dimension(50, 50));
        new_btn.setMinimumSize(new java.awt.Dimension(50, 50));
        new_btn.setPreferredSize(new java.awt.Dimension(50, 50));
        new_btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        new_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new_btnActionPerformed(evt);
            }
        });
        tool_bar.add(new_btn);

        open_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/open.png"))); // NOI18N
        open_btn.setToolTipText("-- open a document");
        open_btn.setFocusable(false);
        open_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        open_btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        open_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                open_btnActionPerformed(evt);
            }
        });
        tool_bar.add(open_btn);

        save_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        save_btn.setToolTipText("-- save a document");
        save_btn.setFocusable(false);
        save_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        save_btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        save_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_btnActionPerformed(evt);
            }
        });
        tool_bar.add(save_btn);

        parse_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/parse.png"))); // NOI18N
        parse_btn.setToolTipText("-- parse document");
        parse_btn.setFocusable(false);
        parse_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        parse_btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        parse_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parse_btnActionPerformed(evt);
            }
        });
        tool_bar.add(parse_btn);

        close_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close.png"))); // NOI18N
        close_btn.setFocusable(false);
        close_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        close_btn.setMaximumSize(new java.awt.Dimension(57, 57));
        close_btn.setMinimumSize(new java.awt.Dimension(57, 57));
        close_btn.setPreferredSize(new java.awt.Dimension(57, 57));
        close_btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        close_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                close_btnActionPerformed(evt);
            }
        });
        tool_bar.add(close_btn);

        split_01.setDividerLocation(140);

        split_02.setDividerLocation(200);
        split_02.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        scroll_01.setViewportView(tree_01);

        split_02.setTopComponent(scroll_01);

        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = tree_02.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = tree_02.getPathForLocation(e.getX(), e.getY());
                if(selRow != -1) {
                    if(e.getClickCount() == 1) {
                        //single click
                    } else if(e.getClickCount() == 2) {
                        java.io.File f = (java.io.File) tree_02.getLastSelectedPathComponent();
                        if(f.isFile()){
                            openFile(f.getAbsolutePath());
                        }
                    }
                }
            }
        };
        tree_02.addMouseListener(ml);
        scroll_02.setViewportView(tree_02);

        split_02.setBottomComponent(scroll_02);

        split_01.setLeftComponent(split_02);

        split_03.setDividerLocation(250);
        split_03.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.event.ChangeListener changeListener = new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent changeEvent) {
                javax.swing.JTabbedPane sourceTabbedPane = (javax.swing.JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                tree_01.setModel(docs.get(index).model);
            }
        };
        tabbed_01.addChangeListener(changeListener);
        split_03.setTopComponent(tabbed_01);

        console.setColumns(20);
        console.setEditable(false);
        console.setRows(5);
        console.setToolTipText("-- console");
        scroll_03.setViewportView(console);

        split_03.setRightComponent(scroll_03);

        split_01.setRightComponent(split_03);

        status.setText("-- decaf editor");
        status.setToolTipText("-- status bar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(split_01, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(status)
                .addContainerGap(471, Short.MAX_VALUE))
            .addComponent(tool_bar, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tool_bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(split_01, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(status)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void open_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_open_btnActionPerformed
        final javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        int val = fc.showOpenDialog(Editor.this);
        if (val == javax.swing.JFileChooser.APPROVE_OPTION) {
            openFile(fc.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_open_btnActionPerformed

    private void new_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new_btnActionPerformed
        Source src = Source.create(tabbed_01.getTabCount());
        docs.add(src);
        tabbed_01.addTab(src.title, new javax.swing.JScrollPane(src.txt));
    }//GEN-LAST:event_new_btnActionPerformed

    private void close_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_close_btnActionPerformed
        if (tabbed_01.getSelectedIndex() >= 0) {
            Object[] o = {"Save", "Discard", "Cancel"};
            int n = javax.swing.JOptionPane.showOptionDialog(this,
                    "Do you wish to save your document?",
                    "Confirm Dialog",
                    javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE,
                    null,
                    o,
                    o[2]);
            switch (n) {
                case 0:
                    saveFile();
                case 1:
                    docs.remove(tabbed_01.getSelectedIndex());
                    tabbed_01.remove(tabbed_01.getSelectedIndex());
                    break;
                case 2:
                    break;
                
            }
        }
    }//GEN-LAST:event_close_btnActionPerformed

    private void save_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_btnActionPerformed
        saveFile();
    }//GEN-LAST:event_save_btnActionPerformed

    private void parse_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parse_btnActionPerformed
        Decaf decaf = new Decaf();
        Source src = docs.get(tabbed_01.getSelectedIndex());

        if (src.io == null) {
            Object[] o = {"Save", "Cancel"};
            int n = javax.swing.JOptionPane.showOptionDialog(this,
                    "You need to save the document before parsing, "
                    + "\ndo you wish to save your document now?",
                    "Confirm Dialog",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE,
                    null,
                    o,
                    o[1]);

            if (n == 0) {
                saveFile();
            } else {
                return;
            }
        }

        if (src.io == null) {
            status.setText("-- unexpected error while parsing");
            return;
        }

        decaf.parse(src);
        if (src.msg.isEmpty()) {
            tree_01.setModel(src.model);
            status.setText("-- file parsed succesfully!");
        } else {
            status.setText("-- parser returned " + src.msg.size() + " messages");
            console.append("-- parsing '" + src.title + "'\n");
            for (Object msg : src.msg) {
                console.append((String) msg);
                console.append("\n");
            }
        }
    }//GEN-LAST:event_parse_btnActionPerformed

    public static void main(String args[]) {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Editor().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton close_btn;
    private javax.swing.JTextArea console;
    private javax.swing.JButton new_btn;
    private javax.swing.JButton open_btn;
    private javax.swing.JButton parse_btn;
    private javax.swing.JButton save_btn;
    private javax.swing.JScrollPane scroll_01;
    private javax.swing.JScrollPane scroll_02;
    private javax.swing.JScrollPane scroll_03;
    private javax.swing.JSplitPane split_01;
    private javax.swing.JSplitPane split_02;
    private javax.swing.JSplitPane split_03;
    private javax.swing.JLabel status;
    private javax.swing.JTabbedPane tabbed_01;
    private javax.swing.JToolBar tool_bar;
    private javax.swing.JTree tree_01;
    private javax.swing.JTree tree_02;
    // End of variables declaration//GEN-END:variables
}
