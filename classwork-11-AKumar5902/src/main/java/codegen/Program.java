package codegen;

import java.util.*;
import java.io.*;

/**
 * This class will hold the generated program bytecodes
 */
public class Program {

    private List<Code> program = new ArrayList<>();

    /**
     * store the new bytecode in the program vector
     *
     * @param code is the bytecode to store
     */
    public void storeop(Code code) {
        program.add(code);
    }

    /**
     * print all of the bytecodes that have been generated
     */
    public void printCodes() {
        for (Code nextCode : program) {
            System.out.println(nextCode.toString());
        }
    }
}