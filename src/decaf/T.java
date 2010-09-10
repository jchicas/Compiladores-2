package decaf;

abstract class T {

    static final int e = -1;
    static final int v = 0;
    static final int n = 1;
    static final int l = 2;
    static final int b = 3;
    static final int s = 4;
    static final int g = 5;
    static final int f = 6;
    int size;
    int type;

    T(int type, int size) {
        this.type = type;
        this.size = size;
    }

    @Override
    public boolean equals(Object t) {
        return t.getClass().equals(this.getClass())
                && toString().equals(t.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    static T merge(T a, T b, String o, Elem e) {
        T t = new Void();
        switch (cmp[a.type + 1][b.type + 1]) {
            case 0: // O(a):err && O(b):err
                ((Err) a).err.putAll(((Err) b).err);
                t = a;
                break;
            case 1: // O(a):* && O(b):err
                t = b;
                break;
            case 2: // O(a):err && O(b):*
                t = a;
                break;
            case 3: // O(a):void && O(b):void
                if (!o.equals("err")) { // revisar si es error
                    Err err = new Err();
                    err.err.put(e, "invalid operation '" + a + " " + o + " " + b + "'");
                    t = err;
                }
                break;
            case 4: // O(a):num && O(b):num
                if ("{'<', '>', '<=', '>=', '==', '!='}".contains("'" + o + "'")) {
                    t = new Binary();
                } else if ("{'+', '-', '*', '/', '%'}".contains("'" + o + "'")) {
                    t = new Number();
                } else if (!"{'='}".contains("'" + o + "'")) {
                    Err err = new Err();
                    err.err.put(e, "invalid operation '" + a + " " + o + " " + b + "'");
                    t = err;
                }
                break;
            case 5: // O(a):letter && O(b):letter
                if ("{'<', '>', '<=', '>=', '==', '!='}".contains("'" + o + "'")) {
                    t = new Binary();
                } else if (!"{'='}".contains("'" + o + "'")) {
                    Err err = new Err();
                    err.err.put(e, "invalid operation '" + a + " " + o + " " + b + "'");
                    t = err;
                }
                break;
            case 6: // O(a):binary && O(b):binary
                if ("{'>', '<', '>=', '<=', '==', '!=', '&&', '||'}".contains("'" + o + "'")) {
                    t = new Binary();
                } else if (!"{'='}".contains("'" + o + "'")) {
                    Err err = new Err();
                    err.err.put(e, "invalid operation '" + a + " " + o + " " + b + "'");
                    t = err;
                }
                break;
            case 7: // O(a):struct && O(b):struct
            case 8: // O(a):group && O(b):group
                if ("{'==', '!='}".contains("'" + o + "'") && a.equals(b)) {
                    e.type = new Binary();
                } else if (!"{'='}".contains("'" + o + "'") && !a.equals(b)) {
                    Err err = new Err();
                    err.err.put(e, "invalid operation '" + a + " " + o + " " + b + "'");
                    t = err;
                }
                break;
            default: // O(a):* != O(b):*
                Err err = new Err();
                err.err.put(e, "invalid operation '" + a + " " + o + " " + b + "'");
                t = err;
                break;
        }
        return t;
    }
    // Matriz para ver el comportamiento de los tipos:
    //                      e   v   n   l   b   s   g
    static int[][] cmp = {{0,  2,  2,  2,  2,  2,  2}, // e    error
                          {1,  3, -1, -1, -1, -1, -1}, // v    void
                          {1, -1,  4, -1, -1, -1, -1}, // n    numero
                          {1, -1, -1,  5, -1, -1, -1}, // l    letter
                          {1, -1, -1, -1,  6, -1, -1}, // b    binary 
                          {1, -1, -1, -1, -1,  7, -1}, // s    struct
                          {1, -1, -1, -1, -1, -1,  8}};// g    group

    @Override
    public abstract String toString();
}

class Var {

    String text;
    T type;

    Var(String text, T type) {
        this.text = text;
        this.type = type;
    }

    @Override
    public String toString() {
        return text + ": " + type;
    }
}

class Err extends T {

    java.util.Map<Elem, String> err;

    Err() {
        super(T.e, 0);
        err = new java.util.LinkedHashMap<Elem, String>();
    }

    @Override
    public String toString() {
        return "err";
    }
}

class Void extends T {

    Void() {
        super(T.v, 0);
    }

    @Override
    public String toString() {
        return "void";
    }
}

class Number extends T {

    Number() {
        super(T.n, 0);
    }

    @Override
    public String toString() {
        return "int";
    }
}

class Letter extends T {

    Letter() {
        super(T.l, 0);
    }

    @Override
    public String toString() {
        return "char";
    }
}

class Binary extends T {

    Binary() {
        super(T.b, 0);
    }

    @Override
    public String toString() {
        return "boolean";
    }
}

class Struct extends T {

    java.util.Map<String, Var> var;
    String val;

    Struct(String val) {
        super(T.s, 0);
        this.val = val;
    }

    @Override
    public String toString() {
        return "struct " + val;
    }
}

class Group extends T {

    int length;
    T gtype;

    Group(T gtype, String length) {
        super(T.g, 0);
        this.gtype = gtype;
        this.length = Integer.parseInt(length);
        if (this.length == 0) {
            throw new RuntimeException("arrays length must be > 0");
        }
    }

    @Override
    public String toString() {
        return gtype + "[" + length + "]";
    }
}

class Func extends T {

    java.util.Map<String, T> firm;
    java.util.Map<String, T> var; // null sino se ha inicializado completamente
    String text;
    T ftype;

    Func(String text, T ftype) {
        super(T.f, 0);
        this.text = text;
        this.ftype = ftype;
        firm = new java.util.LinkedHashMap<String, T>();
    }

    @Override
    public String toString() {
        String t = text + "(";
        for (T i : firm.values()) {
            t += i + ", ";
        }
        if (t.endsWith(", ")) {
            t = t.substring(0, t.length() - 2);
        }
        return t + ")";
    }
}
