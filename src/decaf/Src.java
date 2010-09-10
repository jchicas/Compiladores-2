package decaf;

public class Src {

    javax.swing.tree.TreeModel r;
    javax.swing.JTextPane txt;
    java.io.File f;
    boolean s = false;
    int i;

    Src(java.io.File f, int i) {
        this.i = i;
        this.f = f;
        r = new javax.swing.tree.DefaultTreeModel(
                new javax.swing.tree.DefaultMutableTreeNode("class"));
        txt = new javax.swing.JTextPane();
        txt.setFont(new java.awt.Font("monospaced", java.awt.Font.PLAIN, 11));
        txt.addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                s = false;
                txt.getHighlighter().removeAllHighlights();
            }
        });
        java.awt.FontMetrics fm = txt.getFontMetrics(txt.getFont());
        javax.swing.text.TabStop[] tabs = new javax.swing.text.TabStop[10];
        for (int j = 0; j < tabs.length; j++) {
            int tab = j + 1;
            tabs[j] = new javax.swing.text.TabStop(tab * fm.charWidth('w') * 4);
        }
        javax.swing.text.TabSet tabSet = new javax.swing.text.TabSet(tabs);
        javax.swing.text.SimpleAttributeSet attr = new javax.swing.text.SimpleAttributeSet();
        javax.swing.text.StyleConstants.setTabSet(attr, tabSet);
        int length = txt.getDocument().getLength();
        txt.getStyledDocument().setParagraphAttributes(0, length, attr, false);
    }

    void Save() throws Exception {
        java.io.RandomAccessFile wr = new java.io.RandomAccessFile(f, "rw");
        wr.seek(0);
        wr.writeBytes(txt.getText() + "\n");
        wr.setLength(wr.getFilePointer());
        wr.close();
        s = true;
    }

    void Load() throws Exception {
        java.util.Scanner sc = new java.util.Scanner(f);
        StringBuilder sb = new StringBuilder();
        while (sc.hasNext()) {
            sb.append(sc.nextLine()).append("\n");
        }
        txt.setText(sb.toString());
        sc.close();
    }
}
