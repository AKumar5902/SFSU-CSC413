package compiler;

import ast.*;
import parser.Parser;
import constrain.Constrainer;
import codegen.*;
import visitor.*;

/**
 * The Compiler class contains the main program for compiling
 * a source program to bytecodes
 */
public class Compiler {

    /**
     * The Compiler class reads and compiles a source program
     */

    String sourceFile;

    public Compiler(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    void compileProgram() throws Exception {
        System.out.println("---------------TOKENS-------------");
        Parser parser = new Parser(sourceFile);
        AST t = parser.execute();
        System.out.println("---------------AST-------------");
        PrintVisitor pv = new PrintVisitor();
        t.accept(pv);
//  COMMENT CODE FROM HERE UNTIL THE CATCH CLAUSE WHEN TESTING PARSER */
//        Constrainer con = new Constrainer(t);
//        con.execute();
//        System.out.println("---------------DECORATED AST-------------");
//        t.accept(pv);
//  COMMENT CODE FROM HERE UNTIL THE CATCH CLAUSE WHEN TESTING CONSTRAINER
//        Codegen generator = new Codegen(t);
//        Program program = generator.execute();
//        System.out.println("---------------AST AFTER CODEGEN-------------");
//        t.accept(pv);
//        program.printCodes();
    }

    public static void main(String args[]) throws Exception {
        (new Compiler("src/simple.x")).compileProgram();
    }
}
