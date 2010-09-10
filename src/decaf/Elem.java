package decaf;

public class Elem {

    java.util.Map attr;
    String val;
    String text;
    Elem[] child;
    T type;
    int line;
    int index;

    Elem(String text, int count) {
        this.text = text;
        attr = new java.util.HashMap();
        if (count > -1) {
            child = new Elem[count];
        } else {
            child = null;
        }
    }

    boolean leaf() {
        return child == null;
    }

    public String src() {
        if (leaf()) {
            return val;
        } else {
            StringBuilder sb = new StringBuilder();
            for (Elem e : child) {
                sb.append(e.src());
            }
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        return text + ": " + type;
    }
}
