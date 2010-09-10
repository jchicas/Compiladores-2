package decaf;

import goldengine.java.*;

public class Decaf implements GPMessageConstants, SymbolConstants, RuleConstants {

    static final String CGT = "./DecafGrammar.cgt";
    java.util.Stack<javax.swing.tree.DefaultMutableTreeNode> s1;
    java.util.LinkedList<java.util.Map<String, Var>> scope;
    java.util.Map<String, Struct> typedef;
    java.util.Map<String, Func> func;
    java.util.Stack<Elem> s;
    Func declf; // función en definición

    public static void main(String[] args) {
        Decaf decaf = new Decaf();
        Elem elem = decaf.parse("./ardilla.txt");
        if (elem.type.type == T.e) {
            for (Elem e : ((Err) elem.type).err.keySet()) {
                System.out.println("-- line " + e.line + ": " + ((Err) elem.type).err.get(e));
            }
        } else {
            System.out.println(elem.type);
        }
    }

    void init() {
        scope = new java.util.LinkedList<java.util.Map<String, Var>>();
        typedef = new java.util.LinkedHashMap<String, Struct>();
        func = new java.util.LinkedHashMap<String, Func>();
    }

    boolean declare(String key, T type) {
        switch (type.type) {
            case T.f:
                for (String k : declf.firm.keySet()) {
                    scope.peek().put(k, new Var(k, declf.firm.get(k)));
                }
                if (!func.containsKey(type.toString())) {
                    func.put(type.toString(), (Func) type);
                    return true;
                } else {
                    err("function " + key + " already defined");
                }
            case T.s:
                if (!typedef.containsKey(key)) {
                    typedef.put(key, (Struct) type);
                    return true;
                } else {
                    err("struct " + key + " already defined");
                }
            default:
                if (!scope.peekFirst().containsKey(key)) {
                    scope.peekFirst().put(key, new Var(key, type));
                    return true;
                } else {
                    err("variable " + key + " already defined as " + call(key));
                }
        }
        return false;
    }

    boolean callable(String key) {
        for (java.util.Map var : scope) {
            if (var.containsKey(key)) {
                return true;
            }
        }
        return false;
    }

    Var call(String key) {
        for (java.util.Map<String, Var> var : scope) {
            if (var.containsKey(key)) {
                return var.get(key);
            }
        }
        return new Var("err", new Err());
    }

    Err err(String msg) {
        Err err = new Err();
        err.err.put(s.peek(), msg);
        s.peek().type = err;
        return err;
    }

    Elem parse(String src) {
        init();
        GOLDParser gold = new GOLDParser();
        gold.setTrimReductions(false);
        try {
            gold.loadCompiledGrammar(CGT);
            gold.openFile(src);
        } catch (Exception e) {
            err("error initilizing the gold parser engine");
            return s.pop();
        }
        javax.swing.tree.DefaultMutableTreeNode e1 =
                new javax.swing.tree.DefaultMutableTreeNode("class");
        Elem e = new Elem(null, 0);
        s1 = new java.util.Stack<javax.swing.tree.DefaultMutableTreeNode>();
        s = new java.util.Stack<Elem>();
        boolean done = false;
        int response = -1;

        while (!done) {
            try {
                response = gold.parse();
            } catch (ParserException ex) {
                err("gold parser engine internal error");
                return s.pop();
            }

            switch (response) {
                case gpMsgTokenRead:
                    // arbol sintáctico
                    s.push(e);
                    e = new Elem(gold.currentToken().getName(), -1);
                    e.val = (String) gold.currentToken().getData();
                    e.line = gold.currentLineNumber();
                    e.type = new Void();
                    e.index = gold.currentToken().getTableIndex();
                    if (e.index == 17) { // '{'
                        scope.addFirst(new java.util.LinkedHashMap<String, Var>());
                    }
                    // arbol de despliegue
                    s1.push(e1);
                    e1 = new javax.swing.tree.DefaultMutableTreeNode(e.val);
                    break;
                case gpMsgReduction:
                    // arbol sintáctico
                    Elem p = new Elem(gold.currentReduction().getParentRule().name(),
                            gold.currentReduction().getParentRule().getSymbolCount());
                    p.val = gold.currentReduction().getParentRule().name();
                    p.type = new Void();
                    p.index = gold.currentReduction().getParentRule().getTableIndex();
                    for (int i = p.child.length - 1; i >= 0; i--) {
                        p.child[i] = s.pop();
                    }
                    if (p.child.length > 0) {
                        p.line = p.child[0].line;
                    } else {
                        p.line = gold.currentLineNumber();
                    }
                    s.push(p);
                    type(p);
                    // árbol de despliegue
                    javax.swing.tree.DefaultMutableTreeNode p1 =
                            new javax.swing.tree.DefaultMutableTreeNode(p.val);
                    javax.swing.tree.DefaultMutableTreeNode[] pa =
                            new javax.swing.tree.DefaultMutableTreeNode[p.child.length];
                    for (int i = 0; i < p.child.length; i++) {
                        pa[i] = s1.pop();
                    }
                    for (int i = p.child.length - 1; i >= 0; i--) {
                        p1.add(pa[i]);
                    }
                    s1.push(p1);
                    break;
                case gpMsgAccept:
                    done = true;
                    break;
                case gpMsgLexicalError:
                    err("invalid token " + (String) gold.popInputToken().getData());
                    break;
                case gpMsgNotLoadedError:
                    err("compiled grammar table not loaded");
                    break;
                case gpMsgSyntaxError:
                    done = true;
                    err("token " + (String) gold.popInputToken().getData() + " not expected");
                    break;
                case gpMsgCommentError:
                    done = true;
                    err("unclosed comment");
                    break;
                case gpMsgInternalError:
                    done = true;
                    err("gold parser engine internal error");
                    break;
            }
        }

        try {
            gold.closeFile();
        } catch (ParserException ex) {
            err("gold parser engine internal error");
        }
        return s.pop();
    }

    void type(Elem e) {
        switch (e.index) {
            case RuleConstants.RULE_PROGRAM_CLASS_IDENTIFIER_LBRACE_RBRACE:
                //<Program> ::= class Identifier '{' <Declaration_k> '}'
                e.type = e.child[3].type;
                e.attr.put("var", scope.pollFirst());
                break;
            case RuleConstants.RULE_DECLARATION_K:
                //<Declaration_k> ::= <VarDeclaration> <Declaration_k>
                e.type = T.merge(e.child[0].type, e.child[1].type, "err", e);
                break;
            case RuleConstants.RULE_DECLARATION_K2:
                //<Declaration_k> ::= <MethodDeclaration> <Declaration_k>
                e.type = T.merge(e.child[0].type, e.child[1].type, "err", e);
                break;
            case RuleConstants.RULE_DECLARATION_K_SEMI:
                //<Declaration_k> ::= <StructDeclaration> ';' <Declaration_k>
                e.type = T.merge(e.child[0].type, e.child[2].type, "err", e);
                break;
            case RuleConstants.RULE_DECLARATION_K3:
                //<Declaration_k> ::=
                break;
            case RuleConstants.RULE_STRUCTDECLARATION_STRUCT_IDENTIFIER_LBRACE_RBRACE:
                //<StructDeclaration> ::= struct Identifier '{' <VarDeclaration_k> '}'
                Struct t = new Struct(e.child[1].val);
                t.var = scope.pollFirst();
                declare(t.val, t);
                e.type = T.merge(e.type, e.child[3].type, "err", e);
                break;
            case RuleConstants.RULE_VARDECLARATION_INT_IDENTIFIER_SEMI:
                //<VarDeclaration> ::= int Identifier ';'
                declare(e.child[1].val, new Number());
                break;
            case RuleConstants.RULE_VARDECLARATION_CHAR_IDENTIFIER_SEMI:
                //<VarDeclaration> ::= char Identifier ';'
                declare(e.child[1].val, new Letter());
                break;
            case RuleConstants.RULE_VARDECLARATION_BOOLEAN_IDENTIFIER_SEMI:
                //<VarDeclaration> ::= boolean Identifier ';'
                declare(e.child[1].val, new Binary());
                break;
            case RuleConstants.RULE_VARDECLARATION_VOID_IDENTIFIER_SEMI:
                //<VarDeclaration> ::= void Identifier ';'
                declare(e.child[1].val, new Void());
                break;
            case RuleConstants.RULE_VARDECLARATION_STRUCT_IDENTIFIER_IDENTIFIER_SEMI:
                //<VarDeclaration> ::= struct Identifier Identifier ';'
                if (!typedef.containsKey(e.child[1].val)) {
                    err("struct " + e.child[1].val + " not defined");
                } else if (scope.peek().containsKey(e.child[2].val)) {
                    err("variable " + e.child[2].val + " already defined as " + call(e.child[2].val));
                } else {
                    scope.peek().put(e.child[2].val, new Var(e.child[2].val, typedef.get(e.child[1].val)));
                }
                break;
            case RuleConstants.RULE_VARDECLARATION_IDENTIFIER_SEMI:
                //<VarDeclaration> ::= <StructDeclaration> Identifier ';'
                if (e.child[0].type.type == T.e) {
                    e.type = e.child[0].type;
                } else if (scope.peek().containsKey(e.child[2].val)) {
                    err("variable " + e.child[2].val + " already defined as " + call(e.child[2].val));
                } else {
                    scope.peek().put(e.child[2].val, new Var(e.child[2].val, typedef.get(e.child[1].val)));
                }
                break;
            case RuleConstants.RULE_VARDECLARATION_INT_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI:
                //<VarDeclaration> ::= int Identifier '[' Number ']' ';'
                try {
                    declare(e.child[1].val, new Group(new Number(), e.child[3].val));
                } catch (RuntimeException ex) {
                    err(ex.getMessage());
                }
                break;
            case RuleConstants.RULE_VARDECLARATION_CHAR_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI:
                //<VarDeclaration> ::= char Identifier '[' Number ']' ';'
                try {
                    declare(e.child[1].val, new Group(new Number(), e.child[3].val));
                } catch (RuntimeException ex) {
                    err(ex.getMessage());
                }
                break;
            case RuleConstants.RULE_VARDECLARATION_BOOLEAN_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI:
                //<VarDeclaration> ::= boolean Identifier '[' Number ']' ';'
                try {
                    declare(e.child[1].val, new Group(new Binary(), e.child[3].val));
                } catch (RuntimeException ex) {
                    err(ex.getMessage());
                }
                break;
            case RuleConstants.RULE_VARDECLARATION_VOID_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI:
                //<VarDeclaration> ::= void Identifier '[' Number ']' ';'
                try {
                    declare(e.child[1].val, new Group(new Void(), e.child[3].val));
                } catch (RuntimeException ex) {
                    err(ex.getMessage());
                }
                break;
            case RuleConstants.RULE_VARDECLARATION_STRUCT_IDENTIFIER_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI:
                //<VarDeclaration> ::= struct Identifier Identifier '[' Number ']' ';'
                if (!typedef.containsKey(e.child[1].val)) {
                    err("struct " + e.child[1].val + " not defined");
                } else {
                    try {
                        declare(e.child[2].val, new Group(typedef.get(e.child[1].val), e.child[4].val));
                    } catch (RuntimeException ex) {
                        err(ex.getMessage());
                    }
                }
                break;
            case RuleConstants.RULE_VARDECLARATION_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI:
                //<VarDeclaration> ::= <StructDeclaration> Identifier '[' Number ']' ';'
                if (e.child[0].type.type == T.e) {
                    e.type = e.child[0].type;
                    try {
                        new Group(new Void(), e.child[3].val).toString();
                    } catch (RuntimeException ex) {
                        err(ex.getMessage());
                    }
                } else {
                    declare(e.child[1].val, new Group(typedef.get(e.child[0].child[1].val), e.child[3].val));
                }
                break;
            case RuleConstants.RULE_VARDECLARATION_K:
                //<VarDeclaration_k> ::= <VarDeclaration> <VarDeclaration_k>
                e.type = T.merge(e.child[0].type, e.child[1].type, "err", e);
                break;
            case RuleConstants.RULE_VARDECLARATION_K2:
                //<VarDeclaration_k> ::=
                break;
            case RuleConstants.RULE_METHODDECLARATION:
                //<MethodDeclaration> ::= <MethodFirm> <Block>
                if (func.containsKey(e.child[0].child[1].val) && func.get(e.child[0].child[1].val).var == null) {
                    func.get(e.child[0].child[1].val).var = (java.util.Map<String, T>) e.child[1].attr.get("var");
                }
                e.type = T.merge(e.child[0].type, e.child[1].type, "err", e);
                break;
            case RuleConstants.RULE_METHODFIRM_INT_IDENTIFIER_LPARAN_RPARAN:
                //<MethodFirm> ::= int Identifier '(' <FormalParameters> ')'
                declf = new Func(e.child[1].val, new Number());
                declf.firm = (java.util.Map<String, T>) e.child[3].attr.get("params");
                declf.ftype = new Number();
                declare(e.child[1].val, declf);
                break;
            case RuleConstants.RULE_METHODFIRM_CHAR_IDENTIFIER_LPARAN_RPARAN:
                //<MethodFirm> ::= char Identifier '(' <FormalParameters> ')'
                declf = new Func(e.child[1].val, new Letter());
                declf.firm = (java.util.Map<String, T>) e.child[3].attr.get("params");
                declf.ftype = new Letter();
                declare(e.child[1].val, declf);
                break;
            case RuleConstants.RULE_METHODFIRM_BOOLEAN_IDENTIFIER_LPARAN_RPARAN:
                //<MethodFirm> ::= boolean Identifier '(' <FormalParameters> ')'
                declf = new Func(e.child[1].val, new Letter());
                declf.firm = (java.util.Map<String, T>) e.child[3].attr.get("params");
                declf.ftype = new Binary();
                declare(e.child[1].val, declf);
                break;
            case RuleConstants.RULE_METHODFIRM_VOID_IDENTIFIER_LPARAN_RPARAN:
                //<MethodFirm> ::= void Identifier '(' <FormalParameters> ')'
                declf = new Func(e.child[1].val, new Letter());
                declf.firm = (java.util.Map<String, T>) e.child[3].attr.get("params");
                declf.ftype = new Void();
                declare(e.child[1].val, declf);
                break;
            case RuleConstants.RULE_METHODFIRM_INT_IDENTIFIER_LPARAN_RPARAN2:
                //<MethodFirm> ::= int Identifier '(' ')'
                declf = new Func(e.child[1].val, new Number());
                declf.ftype = new Number();
                declare(e.child[1].val, declf);
                break;
            case RuleConstants.RULE_METHODFIRM_CHAR_IDENTIFIER_LPARAN_RPARAN2:
                //<MethodFirm> ::= char Identifier '(' ')'
                declf = new Func(e.child[1].val, new Letter());
                declf.ftype = new Letter();
                declare(e.child[1].val, declf);
                break;
            case RuleConstants.RULE_METHODFIRM_BOOLEAN_IDENTIFIER_LPARAN_RPARAN2:
                //<MethodFirm> ::= boolean Identifier '(' ')'
                declf = new Func(e.child[1].val, new Letter());
                declf.ftype = new Binary();
                declare(e.child[1].val, declf);
                break;
            case RuleConstants.RULE_METHODFIRM_VOID_IDENTIFIER_LPARAN_RPARAN2:
                //<MethodFirm> ::= void Identifier '(' ')'
                declf = new Func(e.child[1].val, new Letter());
                declf.ftype = new Void();
                declare(e.child[1].val, declf);
                break;
            case RuleConstants.RULE_FORMALPARAMETERS_COMMA:
                //<FormalParameters> ::= <FormalParameters> ',' <Parameter>
                if (((java.util.Map) e.child[0].attr.get("params")).containsKey(e.child[2].child[1].val)) {
                    err("parameter " + e.child[2].child[1].val + " already defined");
                } else {
                    ((java.util.Map) e.child[0].attr.get("params")).put(
                            e.child[2].child[1].val, (T) e.child[2].attr.get("paramt"));
                    e.attr.put("params", e.child[0].attr.get("params"));
                    e.type = e.child[0].type;
                }
                break;
            case RuleConstants.RULE_FORMALPARAMETERS:
                //<FormalParameters> ::= <Parameter>
                java.util.LinkedHashMap<String, T> param =
                        new java.util.LinkedHashMap<String, T>();
                param.put(e.child[0].child[1].val, (T) e.child[0].attr.get("paramt"));
                e.attr.put("params", param);
                e.type = e.child[0].type;
                break;
            case RuleConstants.RULE_PARAMETER_INT_IDENTIFIER:
                //<Parameter> ::= int Identifier
                e.attr.put("paramt", new Number());
                e.type = new Void();
                break;
            case RuleConstants.RULE_PARAMETER_CHAR_IDENTIFIER:
                //<Parameter> ::= char Identifier
                e.attr.put("paramt", new Letter());
                e.type = new Void();
                break;
            case RuleConstants.RULE_PARAMETER_BOOLEAN_IDENTIFIER:
                //<Parameter> ::= boolean Identifier
                e.attr.put("paramt", new Binary());
                e.type = new Void();
                break;
            case RuleConstants.RULE_PARAMETER_VOID_IDENTIFIER:
                //<Parameter> ::= void Identifier
                e.attr.put("paramt", new Void());
                e.type = new Void();
                break;
            case RuleConstants.RULE_PARAMETER_INT_IDENTIFIER_LBRACKET_NUMBER_RBRACKET:
                //<Parameter> ::= int Identifier '[' Number ']'
                e.attr.put("paramt", new Group(new Number(), e.child[3].val));
                e.type = new Void();
                break;
            case RuleConstants.RULE_PARAMETER_CHAR_IDENTIFIER_LBRACKET_NUMBER_RBRACKET:
                //<Parameter> ::= char Identifier '[' Number ']'
                e.attr.put("paramt", new Group(new Letter(), e.child[3].val));
                e.type = new Void();
                break;
            case RuleConstants.RULE_PARAMETER_BOOLEAN_IDENTIFIER_LBRACKET_NUMBER_RBRACKET:
                //<Parameter> ::= boolean Identifier '[' Number ']'
                e.attr.put("paramt", new Group(new Binary(), e.child[3].val));
                e.type = new Void();
                break;
            case RuleConstants.RULE_PARAMETER_VOID_IDENTIFIER_LBRACKET_NUMBER_RBRACKET:
                //<Parameter> ::= void Identifier '[' Number ']'
                e.attr.put("paramt", new Group(new Void(), e.child[3].val));
                e.type = new Void();
                break;
            case RuleConstants.RULE_BLOCK_LBRACE_RBRACE:
                //<Block> ::= '{' <BlockDefinition_k> '}'
                e.attr.put("var", scope.pollFirst());
                e.type = e.child[1].type;
                break;
            case RuleConstants.RULE_BLOCKDEFINITION_K:
                //<BlockDefinition_k> ::= <VarDeclaration> <BlockDefinition_k>
                e.type = T.merge(e.child[0].type, e.child[1].type, "err", e);
                break;
            case RuleConstants.RULE_BLOCKDEFINITION_K2:
                //<BlockDefinition_k> ::= <Statement> <BlockDefinition_k>
                e.type = T.merge(e.child[0].type, e.child[1].type, "err", e);
                break;
            case RuleConstants.RULE_BLOCKDEFINITION_K3:
                //<BlockDefinition_k> ::=
                break;
            case RuleConstants.RULE_STATEMENT_IF_LPARAN_RPARAN_ELSE:
                //<Statement> ::= if '(' <Expression> ')' <Block> else <Block>
                if (e.child[2].type.type == T.e) {
                    e.type = e.child[2].type;
                } else if (e.child[2].type.type != T.b) {
                    err("if expressions can't be " + e.child[2].type + " type");
                }
                T ty = T.merge(e.child[4].type, e.child[6].type, "err", e);
                e.type = T.merge(e.type, ty, "err", e);
                break;
            case RuleConstants.RULE_STATEMENT_IF_LPARAN_RPARAN:
                //<Statement> ::= if '(' <Expression> ')' <Block>
                if (e.child[2].type.type == T.e) {
                    e.type = e.child[2].type;
                } else if (e.child[2].type.type != T.b) {
                    err("if expressions can't be " + e.child[2].type + " type");
                }
                e.type = T.merge(e.type, e.child[4].type, "err", e);
                break;
            case RuleConstants.RULE_STATEMENT_WHILE_LPARAN_RPARAN:
                //<Statement> ::= while '(' <Expression> ')' <Block>
                if (e.child[2].type.type == T.e) {
                    e.type = e.child[2].type;
                } else if (e.child[2].type.type != T.b) {
                    err("while expressions can't be " + e.child[2].type + " type");
                }
                e.type = T.merge(e.type, e.child[4].type, "err", e);
                break;
            case RuleConstants.RULE_STATEMENT_RETURN_SEMI:
                //<Statement> ::= return <Expression> ';'
                if (e.child[1].type.type == T.e) {
                    e.type = e.child[1].type;
                } else if (!declf.ftype.equals(e.child[1].type)) {
                    err("incompatible return type " + e.child[1].type);
                }
                break;
            case RuleConstants.RULE_STATEMENT_RETURN_SEMI2:
                //<Statement> ::= return ';'
                if (declf.ftype.type != T.v) {
                    err("incompatible return type " + new Void());
                }
                break;
            case RuleConstants.RULE_STATEMENT_EQ_SEMI:
                //<Statement> ::= <Location> '=' <Expression> ';'
                e.type = T.merge(loc(e.child[0]), e.child[2].type, "=", e);
                break;
            case RuleConstants.RULE_STATEMENT_SEMI:
                //<Statement> ::= <Expression> ';'
                e.type = e.child[0].type;
                break;
            case RuleConstants.RULE_LOCATION_IDENTIFIER:
                //<Location> ::= Identifier
                break;
            case RuleConstants.RULE_LOCATION_IDENTIFIER_LBRACKET_RBRACKET:
                //<Location> ::= Identifier '[' <Expression> ']'
                if (e.child[2].type.type != T.n && e.child[2].type.type != T.e) {
                    err("array index can't be " + e.child[2].type + " type");
                }
                break;
            case RuleConstants.RULE_LOCATION_IDENTIFIER_DOT:
                //<Location> ::= Identifier '.' <Location>
                break;
            case RuleConstants.RULE_LOCATION_IDENTIFIER_LBRACKET_RBRACKET_DOT:
                //<Location> ::= Identifier '[' <Expression> ']' '.' <Location>
                if (e.child[2].type.type != T.n && e.child[2].type.type != T.e) {
                    err("array index can't be " + e.child[2].type + " type");
                }
                break;
            case RuleConstants.RULE_EXPRESSION_AMPAMP:
                //<Expression> ::= <Expression> '&&' <AddExpression>
                e.type = T.merge(e.child[0].type, e.child[2].type, "&&", e);
                break;
            case RuleConstants.RULE_EXPRESSION_PIPEPIPE:
                //<Expression> ::= <Expression> '||' <AddExpression>
                e.type = T.merge(e.child[0].type, e.child[2].type, "||", e);
                break;
            case RuleConstants.RULE_EXPRESSION_GT:
                //<Expression> ::= <Expression> '>' <AddExpression>
                e.type = T.merge(e.child[0].type, e.child[2].type, ">", e);
                break;
            case RuleConstants.RULE_EXPRESSION_LT:
                //<Expression> ::= <Expression> '<' <AddExpression>
                e.type = T.merge(e.child[0].type, e.child[2].type, "<", e);
                break;
            case RuleConstants.RULE_EXPRESSION_LTEQ:
                //<Expression> ::= <Expression> '<=' <AddExpression>
                e.type = T.merge(e.child[0].type, e.child[2].type, "<=", e);
                break;
            case RuleConstants.RULE_EXPRESSION_GTEQ:
                //<Expression> ::= <Expression> '>=' <AddExpression>
                e.type = T.merge(e.child[0].type, e.child[2].type, ">=", e);
                break;
            case RuleConstants.RULE_EXPRESSION_EQEQ:
                //<Expression> ::= <Expression> '==' <AddExpression>
                e.type = T.merge(e.child[0].type, e.child[2].type, "==", e);
                break;
            case RuleConstants.RULE_EXPRESSION_EXCLAMEQ:
                //<Expression> ::= <Expression> '!=' <AddExpression>
                e.type = T.merge(e.child[0].type, e.child[2].type, "!=", e);
                break;
            case RuleConstants.RULE_EXPRESSION:
                //<Expression> ::= <AddExpression>
                e.type = e.child[0].type;
                break;
            case RuleConstants.RULE_ADDEXPRESSION_PLUS:
                //<AddExpression> ::= <AddExpression> '+' <MultExpression>
                e.type = T.merge(e.child[0].type, e.child[2].type, "+", e);
                break;
            case RuleConstants.RULE_ADDEXPRESSION_MINUS:
                //<AddExpression> ::= <AddExpression> '-' <MultExpression>
                e.type = T.merge(e.child[0].type, e.child[2].type, "-", e);
                break;
            case RuleConstants.RULE_ADDEXPRESSION:
                //<AddExpression> ::= <MultExpression>
                e.type = e.child[0].type;
                break;
            case RuleConstants.RULE_MULTEXPRESSION_TIMES:
                //<MultExpression> ::= <MultExpression> '*' <NegateExpression>
                e.type = T.merge(e.child[0].type, e.child[2].type, "*", e);
                break;
            case RuleConstants.RULE_MULTEXPRESSION_DIV:
                //<MultExpression> ::= <MultExpression> '/' <NegateExpression>
                e.type = T.merge(e.child[0].type, e.child[2].type, "/", e);
                break;
            case RuleConstants.RULE_MULTEXPRESSION_PERCENT:
                //<MultExpression> ::= <MultExpression> '%' <NegateExpression>
                e.type = T.merge(e.child[0].type, e.child[2].type, "%", e);
                break;
            case RuleConstants.RULE_MULTEXPRESSION:
                //<MultExpression> ::= <NegateExpression>
                e.type = e.child[0].type;
                break;
            case RuleConstants.RULE_NEGATEEXPRESSION_MINUS:
                //<NegateExpression> ::= '-' <Value>
                if (e.child[1].type.type != T.n && e.child[1].type.type != T.e) {
                    err("invalid operation '- " + e.child[1].type);
                } else {
                    e.type = e.child[1].type;
                }
                break;
            case RuleConstants.RULE_NEGATEEXPRESSION_EXCLAM:
                //<NegateExpression> ::= '!' <Value>
                if (e.child[1].type.type != T.b && e.child[1].type.type != T.e) {
                    err("invalid operation '! " + e.child[1].type);
                } else {
                    e.type = e.child[1].type;
                }
                break;
            case RuleConstants.RULE_NEGATEEXPRESSION:
                //<NegateExpression> ::= <Value>
                e.type = e.child[0].type;
                break;
            case RuleConstants.RULE_VALUE:
                //<Value> ::= <Location>
                e.type = loc(e.child[0]);
                break;
            case RuleConstants.RULE_VALUE_LPARAN_RPARAN:
                //<Value> ::= '(' <Expression> ')'
                e.type = e.child[1].type;
                break;
            case RuleConstants.RULE_VALUE_LPARAN_INT_RPARAN:
                //<Value> ::= '(' int ')' <Value>
                switch (e.child[3].type.type) {
                    case T.b:
                    case T.n:
                    case T.l:
                        e.type = new Number();
                        break;
                    case T.e:
                        e.type = e.child[3].type;
                        break;
                    default:
                        err("invalid operation (int) " + e.child[3].type);
                        break;
                }
                break;
            case RuleConstants.RULE_VALUE_LPARAN_CHAR_RPARAN:
                //<Value> ::= '(' char ')' <Value>
                switch (e.child[3].type.type) {
                    case T.b:
                    case T.n:
                    case T.l:
                        e.type = new Letter();
                        break;
                    case T.e:
                        e.type = e.child[3].type;
                        break;
                    default:
                        err("invalid operation (letter) " + e.child[3].type);
                        break;
                }
                break;
            case RuleConstants.RULE_VALUE_LPARAN_BOOLEAN_RPARAN:
                //<Value> ::= '(' boolean ')' <Value>
                switch (e.child[3].type.type) {
                    case T.b:
                    case T.n:
                    case T.l:
                        e.type = new Binary();
                        break;
                    case T.e:
                        e.type = e.child[3].type;
                        break;
                    default:
                        err("invalid operation (boolean) " + e.child[3].type);
                        break;
                }
                break;
            case RuleConstants.RULE_VALUE_IDENTIFIER_LPARAN_RPARAN:
                //<Value> ::= Identifier '(' <ActualParameters> ')'
                String fcall = e.child[0].val + "(";
                for (T pt : (java.util.List<T>) e.child[2].attr.get("aparams")) {
                    fcall += pt + ", ";
                }
                fcall = fcall.substring(0, fcall.length() - 2) + ")";
                if (e.child[2].type.type == T.e) {
                    e.type = e.child[2].type;
                } else if (!func.containsKey(fcall)) {
                    err("funcion " + fcall + " not defined");
                } else {
                    e.type = func.get(fcall).ftype;
                }
                break;
            case RuleConstants.RULE_VALUE_IDENTIFIER_LPARAN_RPARAN2:
                //<Value> ::= Identifier '(' ')'
                if (!func.containsKey(e.child[0].val + "()")) {
                    err("function " + e.child[0].val + "() not defined");
                } else {
                    e.type = func.get(e.child[0].val + "()").ftype;
                }
                break;
            case RuleConstants.RULE_VALUE_NUMBER:
                //<Value> ::= Number
                e.type = new Number();
                break;
            case RuleConstants.RULE_VALUE_CHARACTER:
                //<Value> ::= Character
                e.type = new Letter();
                break;
            case RuleConstants.RULE_VALUE_SCAPESEQ:
                //<Value> ::= ScapeSeq
                e.type = new Letter();
                break;
            case RuleConstants.RULE_VALUE_TRUE:
            //<Value> ::= true
            //break;
            case RuleConstants.RULE_VALUE_FALSE:
                //<Value> ::= false
                e.type = new Binary();
                break;
            case RuleConstants.RULE_ACTUALPARAMETERS_COMMA:
                //<ActualParameters> ::= <ActualParameters> ',' <Expression>
                if (e.child[0].type.type == T.e) {
                    e.type = T.merge(e.type, e.child[0].type, "err", e);
                }
                if (e.child[2].type.type == T.e) {
                    e.type = T.merge(e.type, e.child[2].type, "err", e);
                }
                e.attr.put("aparams", e.child[0].attr.get("aparams"));
                ((java.util.List<T>) e.attr.get("aparams")).add(e.child[2].type);
                break;
            case RuleConstants.RULE_ACTUALPARAMETERS:
                //<ActualParameters> ::= <Expression>
                e.type = e.child[0].type;
                java.util.List<T> aparams = new java.util.LinkedList<T>();
                aparams.add(e.child[0].type);
                e.attr.put("aparams", aparams);
                break;
        }
        //System.out.println(e.val + ": " + e.type);
    }

    T loc(Elem e) {
        Elem c = e;
        boolean b = false;
        Struct st = null;
        while (!b) {
            switch (c.index) {
                case RuleConstants.RULE_LOCATION_IDENTIFIER:
                    //<Location> ::= Identifier
                    b = true;
                    if (st == null) {
                        e.type = callable(c.child[0].val) ? call(c.child[0].val).type : new Err();
                    } else {
                        e.type = st.var.containsKey(c.child[0].val) ? st.var.get(c.child[0].val).type : new Err();
                    }
                    break;
                case RuleConstants.RULE_LOCATION_IDENTIFIER_LBRACKET_RBRACKET:
                    //<Location> ::= Identifier '[' <Expression> ']'
                    b = true;
                    if (st == null) {
                        e.type = callable(c.child[0].val) ? call(c.child[0].val).type : new Err();
                    } else {
                        e.type = st.var.containsKey(c.child[0].val) ? st.var.get(c.child[0].val).type : new Err();
                    }
                    if (e.type.type != T.g) {
                        e.type = new Err();
                    }
                    break;
                case RuleConstants.RULE_LOCATION_IDENTIFIER_DOT:
                    //<Location> ::= Identifier '.' <Location>
                    if (st == null) {
                        e.type = callable(c.child[0].val) ? call(c.child[0].val).type : new Err();
                    } else {
                        e.type = st.var.containsKey(c.child[0].val) ? st.var.get(c.child[0].val).type : new Err();
                    }
                    if (e.type.type != T.s && e.type.type != T.e) {
                        e.type = new Err();
                    } else {
                        st = (Struct) e.type;
                        c = c.child[2];
                    }
                    break;
                case RuleConstants.RULE_LOCATION_IDENTIFIER_LBRACKET_RBRACKET_DOT:
                    //<Location> ::= Identifier '[' <Expression> ']' '.' <Location>
                    if (st == null) {
                        e.type = callable(c.child[0].val) ? call(c.child[0].val).type : new Err();
                    } else {
                        e.type = st.var.containsKey(c.child[0].val) ? st.var.get(c.child[0].val).type : new Err();
                    }
                    if (e.type.type == T.g && ((Group) e.type).gtype.type == T.s) {
                        st = (Struct) ((Group) c.type).gtype;
                        c = c.child[5];
                    } else {
                        e.type = new Err();
                    }
                    break;
            }
            if (e.type.type == T.e) {
                e.type = err("invalid location '" + e.src() + "'");
                b = true;
            }
        }
        return e.type;
    }
}
