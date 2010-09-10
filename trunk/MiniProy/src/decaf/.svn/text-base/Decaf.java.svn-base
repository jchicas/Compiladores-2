package decaf;

import goldengine.java.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Clase principal del compilador.
 */
public class Decaf implements GPMessageConstants {

    /** 
     * ruta de la tabla LALR
     */
    private static final String CGT = "./DecafGrammar.cgt";

    /**
     * Lee el archivo indicado y ejecuta el motor de Gold Parser para
     * compilar.
     * @param src manejador del archivo fuente.
     */
    void parse(Source src) {
        GOLDParser gold = new GOLDParser();
        gold.setTrimReductions(false);
        try {
            gold.loadCompiledGrammar(CGT);
            gold.openFile(src.io.getAbsolutePath());
        } catch (Exception e) {
            src.msg.add("-- " + e.toString());
            return;
        }
        
        java.util.Stack<DefaultMutableTreeNode> s = new java.util.Stack<DefaultMutableTreeNode>();
        DefaultMutableTreeNode l = null;
        boolean done = false;
        int response = -1;

        while (!done) {
            try {
                response = gold.parse();
            } catch (ParserException e) {
                src.msg.add("-- " + e.toString());
                return;
            }

            switch (response) {
                case gpMsgTokenRead:
                    if (l != null) {
                        s.push(l);
                    }
                    l = new DefaultMutableTreeNode((String) gold.currentToken().getData());
                    break;
                case gpMsgReduction:
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(
                            gold.currentReduction().getParentRule().name());
                    DefaultMutableTreeNode[] cs = new DefaultMutableTreeNode[gold.currentReduction().getParentRule().getSymbolCount()];
                    for (int i = cs.length - 1; i >= 0; i--) {
                        cs[i] = s.pop();
                    }
                    for (DefaultMutableTreeNode n : cs) {
                        node.add(n);
                    }
                    s.push(node);

                    switch (gold.currentReduction().getParentRule().getTableIndex()) {
                        case RuleConstants.RULE_PROGRAM_CLASS_IDENTIFIER_LBRACE_RBRACE:
                            //<Program> ::= class Identifier '{' <Declaration_k> '}'
                            break;
                        case RuleConstants.RULE_DECLARATION_K:
                            //<Declaration_k> ::= <VarDeclaration> <Declaration_k>
                            break;
                        case RuleConstants.RULE_DECLARATION_K2:
                            //<Declaration_k> ::= <MethodDeclaration> <Declaration_k>
                            break;
                        case RuleConstants.RULE_DECLARATION_K_SEMI:
                            //<Declaration_k> ::= <StructDeclaration> ';' <Declaration_k>
                            break;
                        case RuleConstants.RULE_DECLARATION_K3:
                            //<Declaration_k> ::=
                            break;
                        case RuleConstants.RULE_STRUCTDECLARATION_STRUCT_IDENTIFIER_LBRACE_RBRACE:
                            //<StructDeclaration> ::= struct Identifier '{' <VarDeclaration_k> '}'
                            break;
                        case RuleConstants.RULE_VARDECLARATION_INT_IDENTIFIER_SEMI:
                            //<VarDeclaration> ::= int Identifier ';'
                            break;
                        case RuleConstants.RULE_VARDECLARATION_CHAR_IDENTIFIER_SEMI:
                            //<VarDeclaration> ::= char Identifier ';'
                            break;
                        case RuleConstants.RULE_VARDECLARATION_BOOLEAN_IDENTIFIER_SEMI:
                            //<VarDeclaration> ::= boolean Identifier ';'
                            break;
                        case RuleConstants.RULE_VARDECLARATION_VOID_IDENTIFIER_SEMI:
                            //<VarDeclaration> ::= void Identifier ';'
                            break;
                        case RuleConstants.RULE_VARDECLARATION_STRUCT_IDENTIFIER_IDENTIFIER_SEMI:
                            //<VarDeclaration> ::= struct Identifier Identifier ';'
                            break;
                        case RuleConstants.RULE_VARDECLARATION_IDENTIFIER_SEMI:
                            //<VarDeclaration> ::= <StructDeclaration> Identifier ';'
                            break;
                        case RuleConstants.RULE_VARDECLARATION_INT_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI:
                            //<VarDeclaration> ::= int Identifier '[' Number ']' ';'
                            break;
                        case RuleConstants.RULE_VARDECLARATION_CHAR_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI:
                            //<VarDeclaration> ::= char Identifier '[' Number ']' ';'
                            break;
                        case RuleConstants.RULE_VARDECLARATION_BOOLEAN_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI:
                            //<VarDeclaration> ::= boolean Identifier '[' Number ']' ';'
                            break;
                        case RuleConstants.RULE_VARDECLARATION_VOID_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI:
                            //<VarDeclaration> ::= void Identifier '[' Number ']' ';'
                            break;
                        case RuleConstants.RULE_VARDECLARATION_STRUCT_IDENTIFIER_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI:
                            //<VarDeclaration> ::= struct Identifier Identifier '[' Number ']' ';'
                            break;
                        case RuleConstants.RULE_VARDECLARATION_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI:
                            //<VarDeclaration> ::= <StructDeclaration> Identifier '[' Number ']' ';'
                            break;
                        case RuleConstants.RULE_VARDECLARATION_K:
                            //<VarDeclaration_k> ::= <VarDeclaration> <VarDeclaration_k>
                            break;
                        case RuleConstants.RULE_VARDECLARATION_K2:
                            //<VarDeclaration_k> ::=
                            break;
                        case RuleConstants.RULE_METHODDECLARATION:
                            //<MethodDeclaration> ::= <MethodFirm> <Block>
                            break;
                        case RuleConstants.RULE_METHODFIRM_INT_IDENTIFIER_LPARAN_RPARAN:
                            //<MethodFirm> ::= int Identifier '(' <FormalParameters> ')'
                            break;
                        case RuleConstants.RULE_METHODFIRM_CHAR_IDENTIFIER_LPARAN_RPARAN:
                            //<MethodFirm> ::= char Identifier '(' <FormalParameters> ')'
                            break;
                        case RuleConstants.RULE_METHODFIRM_BOOLEAN_IDENTIFIER_LPARAN_RPARAN:
                            //<MethodFirm> ::= boolean Identifier '(' <FormalParameters> ')'
                            break;
                        case RuleConstants.RULE_METHODFIRM_VOID_IDENTIFIER_LPARAN_RPARAN:
                            //<MethodFirm> ::= void Identifier '(' <FormalParameters> ')'
                            break;
                        case RuleConstants.RULE_METHODFIRM_INT_IDENTIFIER_LPARAN_RPARAN2:
                            //<MethodFirm> ::= int Identifier '(' ')'
                            break;
                        case RuleConstants.RULE_METHODFIRM_CHAR_IDENTIFIER_LPARAN_RPARAN2:
                            //<MethodFirm> ::= char Identifier '(' ')'
                            break;
                        case RuleConstants.RULE_METHODFIRM_BOOLEAN_IDENTIFIER_LPARAN_RPARAN2:
                            //<MethodFirm> ::= boolean Identifier '(' ')'
                            break;
                        case RuleConstants.RULE_METHODFIRM_VOID_IDENTIFIER_LPARAN_RPARAN2:
                            //<MethodFirm> ::= void Identifier '(' ')'
                            break;
                        case RuleConstants.RULE_FORMALPARAMETERS_COMMA:
                            //<FormalParameters> ::= <FormalParameters> ',' <Parameter>
                            break;
                        case RuleConstants.RULE_FORMALPARAMETERS:
                            //<FormalParameters> ::= <Parameter>
                            break;
                        case RuleConstants.RULE_PARAMETER_INT_IDENTIFIER:
                            //<Parameter> ::= int Identifier
                            break;
                        case RuleConstants.RULE_PARAMETER_CHAR_IDENTIFIER:
                            //<Parameter> ::= char Identifier
                            break;
                        case RuleConstants.RULE_PARAMETER_BOOLEAN_IDENTIFIER:
                            //<Parameter> ::= boolean Identifier
                            break;
                        case RuleConstants.RULE_PARAMETER_VOID_IDENTIFIER:
                            //<Parameter> ::= void Identifier
                            break;
                        case RuleConstants.RULE_PARAMETER_INT_IDENTIFIER_LBRACKET_NUMBER_RBRACKET:
                            //<Parameter> ::= int Identifier '[' Number ']'
                            break;
                        case RuleConstants.RULE_PARAMETER_CHAR_IDENTIFIER_LBRACKET_NUMBER_RBRACKET:
                            //<Parameter> ::= char Identifier '[' Number ']'
                            break;
                        case RuleConstants.RULE_PARAMETER_BOOLEAN_IDENTIFIER_LBRACKET_NUMBER_RBRACKET:
                            //<Parameter> ::= boolean Identifier '[' Number ']'
                            break;
                        case RuleConstants.RULE_PARAMETER_VOID_IDENTIFIER_LBRACKET_NUMBER_RBRACKET:
                            //<Parameter> ::= void Identifier '[' Number ']'
                            break;
                        case RuleConstants.RULE_BLOCK_LBRACE_RBRACE:
                            //<Block> ::= '{' <BlockDefinition_k> '}'
                            break;
                        case RuleConstants.RULE_BLOCKDEFINITION_K:
                            //<BlockDefinition_k> ::= <VarDeclaration> <BlockDefinition_k>
                            break;
                        case RuleConstants.RULE_BLOCKDEFINITION_K2:
                            //<BlockDefinition_k> ::= <Statement> <BlockDefinition_k>
                            break;
                        case RuleConstants.RULE_BLOCKDEFINITION_K3:
                            //<BlockDefinition_k> ::=
                            break;
                        case RuleConstants.RULE_STATEMENT_IF_LPARAN_RPARAN_ELSE:
                            //<Statement> ::= if '(' <Expression> ')' <Block> else <Block>
                            break;
                        case RuleConstants.RULE_STATEMENT_IF_LPARAN_RPARAN:
                            //<Statement> ::= if '(' <Expression> ')' <Block>
                            break;
                        case RuleConstants.RULE_STATEMENT_WHILE_LPARAN_RPARAN:
                            //<Statement> ::= while '(' <Expression> ')' <Block>
                            break;
                        case RuleConstants.RULE_STATEMENT_RETURN_SEMI:
                            //<Statement> ::= return <Expression> ';'
                            break;
                        case RuleConstants.RULE_STATEMENT_RETURN_SEMI2:
                            //<Statement> ::= return ';'
                            break;
                        case RuleConstants.RULE_STATEMENT_EQ_SEMI:
                            //<Statement> ::= <Location> '=' <Expression> ';'
                            break;
                        case RuleConstants.RULE_STATEMENT_SEMI:
                            //<Statement> ::= <Expression> ';'
                            break;
                        case RuleConstants.RULE_LOCATION_IDENTIFIER:
                            //<Location> ::= Identifier
                            break;
                        case RuleConstants.RULE_LOCATION_IDENTIFIER_LBRACKET_RBRACKET:
                            //<Location> ::= Identifier '[' <Expression> ']'
                            break;
                        case RuleConstants.RULE_LOCATION_IDENTIFIER_DOT:
                            //<Location> ::= Identifier '.' <Location>
                            break;
                        case RuleConstants.RULE_LOCATION_IDENTIFIER_LBRACKET_RBRACKET_DOT:
                            //<Location> ::= Identifier '[' <Expression> ']' '.' <Location>
                            break;
                        case RuleConstants.RULE_EXPRESSION_AMPAMP:
                            //<Expression> ::= <Expression> '&&' <AddExpression>
                            break;
                        case RuleConstants.RULE_EXPRESSION_PIPEPIPE:
                            //<Expression> ::= <Expression> '||' <AddExpression>
                            break;
                        case RuleConstants.RULE_EXPRESSION_GT:
                            //<Expression> ::= <Expression> '>' <AddExpression>
                            break;
                        case RuleConstants.RULE_EXPRESSION_LT:
                            //<Expression> ::= <Expression> '<' <AddExpression>
                            break;
                        case RuleConstants.RULE_EXPRESSION_LTEQ:
                            //<Expression> ::= <Expression> '<=' <AddExpression>
                            break;
                        case RuleConstants.RULE_EXPRESSION_GTEQ:
                            //<Expression> ::= <Expression> '>=' <AddExpression>
                            break;
                        case RuleConstants.RULE_EXPRESSION_EQEQ:
                            //<Expression> ::= <Expression> '==' <AddExpression>
                            break;
                        case RuleConstants.RULE_EXPRESSION_EXCLAMEQ:
                            //<Expression> ::= <Expression> '!=' <AddExpression>
                            break;
                        case RuleConstants.RULE_EXPRESSION:
                            //<Expression> ::= <AddExpression>
                            break;
                        case RuleConstants.RULE_ADDEXPRESSION_PLUS:
                            //<AddExpression> ::= <AddExpression> '+' <MultExpression>
                            break;
                        case RuleConstants.RULE_ADDEXPRESSION_MINUS:
                            //<AddExpression> ::= <AddExpression> '-' <MultExpression>
                            break;
                        case RuleConstants.RULE_ADDEXPRESSION:
                            //<AddExpression> ::= <MultExpression>
                            break;
                        case RuleConstants.RULE_MULTEXPRESSION_TIMES:
                            //<MultExpression> ::= <MultExpression> '*' <NegateExpression>
                            break;
                        case RuleConstants.RULE_MULTEXPRESSION_DIV:
                            //<MultExpression> ::= <MultExpression> '/' <NegateExpression>
                            break;
                        case RuleConstants.RULE_MULTEXPRESSION_PERCENT:
                            //<MultExpression> ::= <MultExpression> '%' <NegateExpression>
                            break;
                        case RuleConstants.RULE_MULTEXPRESSION:
                            //<MultExpression> ::= <NegateExpression>
                            break;
                        case RuleConstants.RULE_NEGATEEXPRESSION_MINUS:
                            //<NegateExpression> ::= '-' <Value>
                            break;
                        case RuleConstants.RULE_NEGATEEXPRESSION_EXCLAM:
                            //<NegateExpression> ::= '!' <Value>
                            break;
                        case RuleConstants.RULE_NEGATEEXPRESSION:
                            //<NegateExpression> ::= <Value>
                            break;
                        case RuleConstants.RULE_VALUE:
                            //<Value> ::= <Location>
                            break;
                        case RuleConstants.RULE_VALUE_LPARAN_RPARAN:
                            //<Value> ::= '(' <Expression> ')'
                            break;
                        case RuleConstants.RULE_VALUE_LPARAN_INT_RPARAN:
                            //<Value> ::= '(' int ')' <Value>
                            break;
                        case RuleConstants.RULE_VALUE_LPARAN_CHAR_RPARAN:
                            //<Value> ::= '(' char ')' <Value>
                            break;
                        case RuleConstants.RULE_VALUE_LPARAN_BOOLEAN_RPARAN:
                            //<Value> ::= '(' boolean ')' <Value>
                            break;
                        case RuleConstants.RULE_VALUE_IDENTIFIER_LPARAN_RPARAN:
                            //<Value> ::= Identifier '(' <ActualParameters> ')'
                            break;
                        case RuleConstants.RULE_VALUE_IDENTIFIER_LPARAN_RPARAN2:
                            //<Value> ::= Identifier '(' ')'
                            break;
                        case RuleConstants.RULE_VALUE_NUMBER:
                            //<Value> ::= Number
                            break;
                        case RuleConstants.RULE_VALUE_CHARACTER:
                            //<Value> ::= Character
                            break;
                        case RuleConstants.RULE_VALUE_SCAPESEQ:
                            //<Value> ::= ScapeSeq
                            break;
                        case RuleConstants.RULE_VALUE_TRUE:
                            //<Value> ::= true
                            break;
                        case RuleConstants.RULE_VALUE_FALSE:
                            //<Value> ::= false
                            break;
                        case RuleConstants.RULE_ACTUALPARAMETERS_COMMA:
                            //<ActualParameters> ::= <ActualParameters> ',' <Expression>
                            break;
                        case RuleConstants.RULE_ACTUALPARAMETERS:
                            //<ActualParameters> ::= <Expression>
                            break;
                    }

                    //Parser.Reduction = //Object you created to store the rule
                    
                    // ************************************** log file
                    //System.out.println("gpMsgReduction");
                    //Reduction myRed = gold.currentReduction();
                    //System.out.println(myRed.getParentRule().getText());
                    // ************************************** end log
                    break;
                case gpMsgAccept:
                    done = true;
                    src.model = new javax.swing.tree.DefaultTreeModel(s.pop());
                    break;
                case gpMsgLexicalError:
                    //gold.popInputToken();
                    src.msg.add("-- lexical error");
                    break;
                case gpMsgNotLoadedError:
                    done = true;
                    src.msg.add("-- compiled grammar table not loaded");
                    break;
                case gpMsgSyntaxError:
                    done = true;
                    Token theTok = gold.currentToken();
                    src.msg.add("-- token not expected: " + (String) theTok.getData());
                    break;
                case gpMsgCommentError:
                    done = true;
                    break;
                case gpMsgInternalError:
                    done = true;
                    src.msg.add("-- gold parser internal error");
                    break;
            }
        }

        try {
            gold.closeFile();
        } catch (ParserException parse) {
            src.msg.add("-- " + parse.toString());
        }
    }
}

interface SymbolConstants {

    final int SYMBOL_EOF = 0;  // (EOF)
    final int SYMBOL_ERROR = 1;  // (Error)
    final int SYMBOL_WHITESPACE = 2;  // (Whitespace)
    final int SYMBOL_MINUS = 3;  // '-'
    final int SYMBOL_EXCLAM = 4;  // '!'
    final int SYMBOL_EXCLAMEQ = 5;  // '!='
    final int SYMBOL_PERCENT = 6;  // '%'
    final int SYMBOL_AMPAMP = 7;  // '&&'
    final int SYMBOL_LPARAN = 8;  // '('
    final int SYMBOL_RPARAN = 9;  // ')'
    final int SYMBOL_TIMES = 10;  // '*'
    final int SYMBOL_COMMA = 11;  // ','
    final int SYMBOL_DOT = 12;  // '.'
    final int SYMBOL_DIV = 13;  // '/'
    final int SYMBOL_SEMI = 14;  // ';'
    final int SYMBOL_LBRACKET = 15;  // '['
    final int SYMBOL_RBRACKET = 16;  // ']'
    final int SYMBOL_LBRACE = 17;  // '{'
    final int SYMBOL_PIPEPIPE = 18;  // '||'
    final int SYMBOL_RBRACE = 19;  // '}'
    final int SYMBOL_PLUS = 20;  // '+'
    final int SYMBOL_LT = 21;  // '<'
    final int SYMBOL_LTEQ = 22;  // '<='
    final int SYMBOL_EQ = 23;  // '='
    final int SYMBOL_EQEQ = 24;  // '=='
    final int SYMBOL_GT = 25;  // '>'
    final int SYMBOL_GTEQ = 26;  // '>='
    final int SYMBOL_BOOLEAN = 27;  // boolean
    final int SYMBOL_CLASS = 28;  // class
    final int SYMBOL_CHAR = 29;  // char
    final int SYMBOL_CHARACTER = 30;  // Character
    final int SYMBOL_ELSE = 31;  // else
    final int SYMBOL_FALSE = 32;  // false
    final int SYMBOL_IDENTIFIER = 33;  // Identifier
    final int SYMBOL_IF = 34;  // if
    final int SYMBOL_INT = 35;  // int
    final int SYMBOL_NUMBER = 36;  // Number
    final int SYMBOL_RETURN = 37;  // return
    final int SYMBOL_SCAPESEQ = 38;  // ScapeSeq
    final int SYMBOL_STRUCT = 39;  // struct
    final int SYMBOL_TRUE = 40;  // true
    final int SYMBOL_VOID = 41;  // void
    final int SYMBOL_WHILE = 42;  // while
    final int SYMBOL_ACTUALPARAMETERS = 43;  // <ActualParameters>
    final int SYMBOL_ADDEXPRESSION = 44;  // <AddExpression>
    final int SYMBOL_BLOCK = 45;  // <Block>
    final int SYMBOL_BLOCKDEFINITION_K = 46;  // <BlockDefinition_k>
    final int SYMBOL_DECLARATION_K = 47;  // <Declaration_k>
    final int SYMBOL_EXPRESSION = 48;  // <Expression>
    final int SYMBOL_FORMALPARAMETERS = 49;  // <FormalParameters>
    final int SYMBOL_LOCATION = 50;  // <Location>
    final int SYMBOL_METHODDECLARATION = 51;  // <MethodDeclaration>
    final int SYMBOL_METHODFIRM = 52;  // <MethodFirm>
    final int SYMBOL_MULTEXPRESSION = 53;  // <MultExpression>
    final int SYMBOL_NEGATEEXPRESSION = 54;  // <NegateExpression>
    final int SYMBOL_PARAMETER = 55;  // <Parameter>
    final int SYMBOL_PROGRAM = 56;  // <Program>
    final int SYMBOL_STATEMENT = 57;  // <Statement>
    final int SYMBOL_STRUCTDECLARATION = 58;  // <StructDeclaration>
    final int SYMBOL_VALUE = 59;  // <Value>
    final int SYMBOL_VARDECLARATION = 60;  // <VarDeclaration>
    final int SYMBOL_VARDECLARATION_K = 61;  // <VarDeclaration_k>
};

interface RuleConstants {

    final int RULE_PROGRAM_CLASS_IDENTIFIER_LBRACE_RBRACE = 0;  // <Program> ::= class Identifier '{' <Declaration_k> '}'
    final int RULE_DECLARATION_K = 1;  // <Declaration_k> ::= <VarDeclaration> <Declaration_k>
    final int RULE_DECLARATION_K2 = 2;  // <Declaration_k> ::= <MethodDeclaration> <Declaration_k>
    final int RULE_DECLARATION_K_SEMI = 3;  // <Declaration_k> ::= <StructDeclaration> ';' <Declaration_k>
    final int RULE_DECLARATION_K3 = 4;  // <Declaration_k> ::=
    final int RULE_STRUCTDECLARATION_STRUCT_IDENTIFIER_LBRACE_RBRACE = 5;  // <StructDeclaration> ::= struct Identifier '{' <VarDeclaration_k> '}'
    final int RULE_VARDECLARATION_INT_IDENTIFIER_SEMI = 6;  // <VarDeclaration> ::= int Identifier ';'
    final int RULE_VARDECLARATION_CHAR_IDENTIFIER_SEMI = 7;  // <VarDeclaration> ::= char Identifier ';'
    final int RULE_VARDECLARATION_BOOLEAN_IDENTIFIER_SEMI = 8;  // <VarDeclaration> ::= boolean Identifier ';'
    final int RULE_VARDECLARATION_VOID_IDENTIFIER_SEMI = 9;  // <VarDeclaration> ::= void Identifier ';'
    final int RULE_VARDECLARATION_STRUCT_IDENTIFIER_IDENTIFIER_SEMI = 10;  // <VarDeclaration> ::= struct Identifier Identifier ';'
    final int RULE_VARDECLARATION_IDENTIFIER_SEMI = 11;  // <VarDeclaration> ::= <StructDeclaration> Identifier ';'
    final int RULE_VARDECLARATION_INT_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI = 12;  // <VarDeclaration> ::= int Identifier '[' Number ']' ';'
    final int RULE_VARDECLARATION_CHAR_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI = 13;  // <VarDeclaration> ::= char Identifier '[' Number ']' ';'
    final int RULE_VARDECLARATION_BOOLEAN_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI = 14;  // <VarDeclaration> ::= boolean Identifier '[' Number ']' ';'
    final int RULE_VARDECLARATION_VOID_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI = 15;  // <VarDeclaration> ::= void Identifier '[' Number ']' ';'
    final int RULE_VARDECLARATION_STRUCT_IDENTIFIER_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI = 16;  // <VarDeclaration> ::= struct Identifier Identifier '[' Number ']' ';'
    final int RULE_VARDECLARATION_IDENTIFIER_LBRACKET_NUMBER_RBRACKET_SEMI = 17;  // <VarDeclaration> ::= <StructDeclaration> Identifier '[' Number ']' ';'
    final int RULE_VARDECLARATION_K = 18;  // <VarDeclaration_k> ::= <VarDeclaration> <VarDeclaration_k>
    final int RULE_VARDECLARATION_K2 = 19;  // <VarDeclaration_k> ::=
    final int RULE_METHODDECLARATION = 20;  // <MethodDeclaration> ::= <MethodFirm> <Block>
    final int RULE_METHODFIRM_INT_IDENTIFIER_LPARAN_RPARAN = 21;  // <MethodFirm> ::= int Identifier '(' <FormalParameters> ')'
    final int RULE_METHODFIRM_CHAR_IDENTIFIER_LPARAN_RPARAN = 22;  // <MethodFirm> ::= char Identifier '(' <FormalParameters> ')'
    final int RULE_METHODFIRM_BOOLEAN_IDENTIFIER_LPARAN_RPARAN = 23;  // <MethodFirm> ::= boolean Identifier '(' <FormalParameters> ')'
    final int RULE_METHODFIRM_VOID_IDENTIFIER_LPARAN_RPARAN = 24;  // <MethodFirm> ::= void Identifier '(' <FormalParameters> ')'
    final int RULE_METHODFIRM_INT_IDENTIFIER_LPARAN_RPARAN2 = 25;  // <MethodFirm> ::= int Identifier '(' ')'
    final int RULE_METHODFIRM_CHAR_IDENTIFIER_LPARAN_RPARAN2 = 26;  // <MethodFirm> ::= char Identifier '(' ')'
    final int RULE_METHODFIRM_BOOLEAN_IDENTIFIER_LPARAN_RPARAN2 = 27;  // <MethodFirm> ::= boolean Identifier '(' ')'
    final int RULE_METHODFIRM_VOID_IDENTIFIER_LPARAN_RPARAN2 = 28;  // <MethodFirm> ::= void Identifier '(' ')'
    final int RULE_FORMALPARAMETERS_COMMA = 29;  // <FormalParameters> ::= <FormalParameters> ',' <Parameter>
    final int RULE_FORMALPARAMETERS = 30;  // <FormalParameters> ::= <Parameter>
    final int RULE_PARAMETER_INT_IDENTIFIER = 31;  // <Parameter> ::= int Identifier
    final int RULE_PARAMETER_CHAR_IDENTIFIER = 32;  // <Parameter> ::= char Identifier
    final int RULE_PARAMETER_BOOLEAN_IDENTIFIER = 33;  // <Parameter> ::= boolean Identifier
    final int RULE_PARAMETER_VOID_IDENTIFIER = 34;  // <Parameter> ::= void Identifier
    final int RULE_PARAMETER_INT_IDENTIFIER_LBRACKET_NUMBER_RBRACKET = 35;  // <Parameter> ::= int Identifier '[' Number ']'
    final int RULE_PARAMETER_CHAR_IDENTIFIER_LBRACKET_NUMBER_RBRACKET = 36;  // <Parameter> ::= char Identifier '[' Number ']'
    final int RULE_PARAMETER_BOOLEAN_IDENTIFIER_LBRACKET_NUMBER_RBRACKET = 37;  // <Parameter> ::= boolean Identifier '[' Number ']'
    final int RULE_PARAMETER_VOID_IDENTIFIER_LBRACKET_NUMBER_RBRACKET = 38;  // <Parameter> ::= void Identifier '[' Number ']'
    final int RULE_BLOCK_LBRACE_RBRACE = 39;  // <Block> ::= '{' <BlockDefinition_k> '}'
    final int RULE_BLOCKDEFINITION_K = 40;  // <BlockDefinition_k> ::= <VarDeclaration> <BlockDefinition_k>
    final int RULE_BLOCKDEFINITION_K2 = 41;  // <BlockDefinition_k> ::= <Statement> <BlockDefinition_k>
    final int RULE_BLOCKDEFINITION_K3 = 42;  // <BlockDefinition_k> ::=
    final int RULE_STATEMENT_IF_LPARAN_RPARAN_ELSE = 43;  // <Statement> ::= if '(' <Expression> ')' <Block> else <Block>
    final int RULE_STATEMENT_IF_LPARAN_RPARAN = 44;  // <Statement> ::= if '(' <Expression> ')' <Block>
    final int RULE_STATEMENT_WHILE_LPARAN_RPARAN = 45;  // <Statement> ::= while '(' <Expression> ')' <Block>
    final int RULE_STATEMENT_RETURN_SEMI = 46;  // <Statement> ::= return <Expression> ';'
    final int RULE_STATEMENT_RETURN_SEMI2 = 47;  // <Statement> ::= return ';'
    final int RULE_STATEMENT_EQ_SEMI = 48;  // <Statement> ::= <Location> '=' <Expression> ';'
    final int RULE_STATEMENT_SEMI = 49;  // <Statement> ::= <Expression> ';'
    final int RULE_LOCATION_IDENTIFIER = 50;  // <Location> ::= Identifier
    final int RULE_LOCATION_IDENTIFIER_LBRACKET_RBRACKET = 51;  // <Location> ::= Identifier '[' <Expression> ']'
    final int RULE_LOCATION_IDENTIFIER_DOT = 52;  // <Location> ::= Identifier '.' <Location>
    final int RULE_LOCATION_IDENTIFIER_LBRACKET_RBRACKET_DOT = 53;  // <Location> ::= Identifier '[' <Expression> ']' '.' <Location>
    final int RULE_EXPRESSION_AMPAMP = 54;  // <Expression> ::= <Expression> '&&' <AddExpression>
    final int RULE_EXPRESSION_PIPEPIPE = 55;  // <Expression> ::= <Expression> '||' <AddExpression>
    final int RULE_EXPRESSION_GT = 56;  // <Expression> ::= <Expression> '>' <AddExpression>
    final int RULE_EXPRESSION_LT = 57;  // <Expression> ::= <Expression> '<' <AddExpression>
    final int RULE_EXPRESSION_LTEQ = 58;  // <Expression> ::= <Expression> '<=' <AddExpression>
    final int RULE_EXPRESSION_GTEQ = 59;  // <Expression> ::= <Expression> '>=' <AddExpression>
    final int RULE_EXPRESSION_EQEQ = 60;  // <Expression> ::= <Expression> '==' <AddExpression>
    final int RULE_EXPRESSION_EXCLAMEQ = 61;  // <Expression> ::= <Expression> '!=' <AddExpression>
    final int RULE_EXPRESSION = 62;  // <Expression> ::= <AddExpression>
    final int RULE_ADDEXPRESSION_PLUS = 63;  // <AddExpression> ::= <AddExpression> '+' <MultExpression>
    final int RULE_ADDEXPRESSION_MINUS = 64;  // <AddExpression> ::= <AddExpression> '-' <MultExpression>
    final int RULE_ADDEXPRESSION = 65;  // <AddExpression> ::= <MultExpression>
    final int RULE_MULTEXPRESSION_TIMES = 66;  // <MultExpression> ::= <MultExpression> '*' <NegateExpression>
    final int RULE_MULTEXPRESSION_DIV = 67;  // <MultExpression> ::= <MultExpression> '/' <NegateExpression>
    final int RULE_MULTEXPRESSION_PERCENT = 68;  // <MultExpression> ::= <MultExpression> '%' <NegateExpression>
    final int RULE_MULTEXPRESSION = 69;  // <MultExpression> ::= <NegateExpression>
    final int RULE_NEGATEEXPRESSION_MINUS = 70;  // <NegateExpression> ::= '-' <Value>
    final int RULE_NEGATEEXPRESSION_EXCLAM = 71;  // <NegateExpression> ::= '!' <Value>
    final int RULE_NEGATEEXPRESSION = 72;  // <NegateExpression> ::= <Value>
    final int RULE_VALUE = 73;  // <Value> ::= <Location>
    final int RULE_VALUE_LPARAN_RPARAN = 74;  // <Value> ::= '(' <Expression> ')'
    final int RULE_VALUE_LPARAN_INT_RPARAN = 75;  // <Value> ::= '(' int ')' <Value>
    final int RULE_VALUE_LPARAN_CHAR_RPARAN = 76;  // <Value> ::= '(' char ')' <Value>
    final int RULE_VALUE_LPARAN_BOOLEAN_RPARAN = 77;  // <Value> ::= '(' boolean ')' <Value>
    final int RULE_VALUE_IDENTIFIER_LPARAN_RPARAN = 78;  // <Value> ::= Identifier '(' <ActualParameters> ')'
    final int RULE_VALUE_IDENTIFIER_LPARAN_RPARAN2 = 79;  // <Value> ::= Identifier '(' ')'
    final int RULE_VALUE_NUMBER = 80;  // <Value> ::= Number
    final int RULE_VALUE_CHARACTER = 81;  // <Value> ::= Character
    final int RULE_VALUE_SCAPESEQ = 82;  // <Value> ::= ScapeSeq
    final int RULE_VALUE_TRUE = 83;  // <Value> ::= true
    final int RULE_VALUE_FALSE = 84;  // <Value> ::= false
    final int RULE_ACTUALPARAMETERS_COMMA = 85;  // <ActualParameters> ::= <ActualParameters> ',' <Expression>
    final int RULE_ACTUALPARAMETERS = 86;  // <ActualParameters> ::= <Expression>
};
