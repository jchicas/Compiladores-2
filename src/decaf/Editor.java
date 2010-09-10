package decaf;

public class Editor extends javax.swing.JFrame {

    private static int count = 0;
    java.util.ArrayList<Src> doc;

    public Editor() {
        doc = new java.util.ArrayList<Src>();
        initComponents();
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
        msplit = new javax.swing.JSplitPane();
        esplit = new javax.swing.JSplitPane();
        tabs = new javax.swing.JTabbedPane();
        cscroll = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        tscroll = new javax.swing.JScrollPane();
        tree = new javax.swing.JTree();
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
                New(evt);
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
                Open(evt);
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
                Save(evt);
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
                Compile(evt);
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
                Close(evt);
            }
        });
        tool_bar.add(close_btn);

        msplit.setDividerLocation(120);

        esplit.setDividerLocation(250);
        esplit.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.event.ChangeListener changeListener = new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent changeEvent) {
                javax.swing.JTabbedPane sourceTabbedPane = (javax.swing.JTabbedPane) changeEvent.getSource();
                tree.setModel(doc.get(sourceTabbedPane.getSelectedIndex()).r);
            }
        };
        tabs.addChangeListener(changeListener);
        esplit.setTopComponent(tabs);

        console.setColumns(20);
        console.setEditable(false);
        console.setRows(5);
        console.setToolTipText("-- console");
        cscroll.setViewportView(console);

        esplit.setRightComponent(cscroll);

        msplit.setRightComponent(esplit);

        tscroll.setViewportView(tree);

        msplit.setLeftComponent(tscroll);

        status.setText("-- decaf editor");
        status.setToolTipText("-- status bar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(msplit, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
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
                .addComponent(msplit, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(status)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void New(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_New
        Src src = new Src(null, tabs.getTabCount());
        doc.add(src);
        tabs.addTab("new-" + count++, new javax.swing.JScrollPane(src.txt));
    }//GEN-LAST:event_New

    private void Open(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Open
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        switch (fc.showOpenDialog(this)) {
            case javax.swing.JFileChooser.APPROVE_OPTION:
                try {
                    Src src = new Src(fc.getSelectedFile(), tabs.getTabCount());
                    src.Load();
                    doc.add(src);
                    tabs.addTab(src.f.getName(), new javax.swing.JScrollPane(src.txt));
                } catch (Exception e) {
                    console.append("-- error reading file: " + e.getMessage() + "\n");
                }
                break;
            default:
                console.append("-- operation cancelled by user\n");
                break;
        }
    }//GEN-LAST:event_Open

    private void Save(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Save
        if (tabs.getSelectedIndex() < 0) {
            console.append("-- no source file selected\n");
        } else if (doc.get(tabs.getSelectedIndex()).f == null) {
            javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
            switch (fc.showSaveDialog(this)) {
                case javax.swing.JFileChooser.APPROVE_OPTION:
                    try {
                        doc.get(tabs.getSelectedIndex()).f = fc.getSelectedFile();
                        doc.get(tabs.getSelectedIndex()).Save();
                    } catch (Exception e) {
                        console.append("-- error saving file: " + e.getMessage() + "\n");
                    }
                    break;
                default:
                    console.append("-- operation cancelled by user\n");
                    break;
            }
        } else {
            try {
                doc.get(tabs.getSelectedIndex()).Save();
            } catch (Exception e) {
                console.append("-- error saving file: " + e.getMessage() + "\n");
            }
        }
    }//GEN-LAST:event_Save

    private void Compile(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Compile
        if (tabs.getSelectedIndex() < 0) {
            console.append("-- no source file selected\n");
        } else if (doc.get(tabs.getSelectedIndex()).s) {
            Decaf decaf = new Decaf();
            console.append("-- parsing file " + doc.get(tabs.getSelectedIndex()).f.getName() + "\n");
            Elem e = decaf.parse(doc.get(tabs.getSelectedIndex()).f.getAbsolutePath());
            if (e.type.type == T.e) {
                for (Elem k : ((Err) e.type).err.keySet()) {
                    console.append("    -- line " + k.line + ": " + ((Err) e.type).err.get(k) + "\n");
                }
                console.append("-- " + ((Err) e.type).err.size() + " total messages\n");
            } else {
                console.append("-- file succesfully parsed\n");
                doc.get(tabs.getSelectedIndex()).r = new javax.swing.tree.DefaultTreeModel(decaf.s1.pop());
                tree.setModel(doc.get(tabs.getSelectedIndex()).r);
            }
        } else {
            console.append("-- file must be saved to compile\n");
        }
    }//GEN-LAST:event_Compile

    private void Close(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Close
        if (tabs.getSelectedIndex() < 0) {
            console.append("-- no source file selected\n");
        } else {
            int i = tabs.getSelectedIndex();
            tabs.remove(i);
            doc.remove(i);
        }
    }//GEN-LAST:event_Close

    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Look & Feel not supported
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
    private javax.swing.JScrollPane cscroll;
    private javax.swing.JSplitPane esplit;
    private javax.swing.JSplitPane msplit;
    private javax.swing.JButton new_btn;
    private javax.swing.JButton open_btn;
    private javax.swing.JButton parse_btn;
    private javax.swing.JButton save_btn;
    private javax.swing.JLabel status;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JToolBar tool_bar;
    private javax.swing.JTree tree;
    private javax.swing.JScrollPane tscroll;
    // End of variables declaration//GEN-END:variables
}
