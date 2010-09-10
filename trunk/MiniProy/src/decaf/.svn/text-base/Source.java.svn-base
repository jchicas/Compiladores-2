package decaf;

/**
 * Define un documento de código fuente en el lenguaje Decaf.
 */
public class Source {

    /**
     * Número de documentos creados en el editor.
     */
    private static int count = 0;
    /**
     * Área de texto del documento.
     */
    javax.swing.JTextArea txt;
    /**
     * Modelo del árbol sintáctico del documento.
     */
    javax.swing.tree.TreeModel model;
    /**
     * Mensages generados por el parser.
     */
    java.util.List msg;
    /**
     * Archivo relacionado con este documento.
     */
    java.io.File io;
    /**
     * Titulo del documento.
     */
    String title;
    /**
     * Número de pestaña del documento.
     */
    int i;

    /**
     * Constructor.
     */
    private Source(){
        msg = new java.util.LinkedList();
        txt = new javax.swing.JTextArea();
        model = new javax.swing.tree.DefaultTreeModel(
                new javax.swing.tree.DefaultMutableTreeNode("Program"));
    }

    /**
     * Abre el documento indicado.
     * @param fileName ruta del archivo.
     * @param i índice de la pestaña.
     * @return el manejador del documento,
     */
    static Source open(String fileName, int i){
        Source src = new Source();
        src.io = new java.io.File(fileName);
        src.title = src.io.getName();
        src.i = i;
        return src;
    }

    /**
     * Crea un nuevo documento en blanco.
     * @param i número de pestaña del documento.
     * @return manejador del documento creado.
     */
    static Source create(int i){
        Source src = new Source();
        src.title = "new-" + ++count;
        src.i = i;
        return src;
    }

    /**
     * Guarda el documento en earchivo relacionado.
     * @return <code>true</code> en caso que se halla guardado el documento o
     * <code>false</code> en caso contrario.
     */
    boolean save(){
        try {
            java.io.RandomAccessFile o = new java.io.RandomAccessFile(io, "rw");
            o.writeBytes(txt.getText().concat("\n").replace("\n", "\r\n"));
            o.setLength(o.getFilePointer());
            o.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    /**
     * Lee el documento indicado.
     * @return <code>true</code> en caso que se halla leído el documento o
     * <code>false</code> en caso contrario.
     */
    boolean load(){
        try {
            java.util.Scanner s = new java.util.Scanner(io);
            while(s.hasNext()){txt.append(s.nextLine() + "\n");}
            s.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
