import adapter.scriptGenerator.ScriptGenerator;
import adapter.scriptGenerator.ScriptGeneratorFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ScriptMain {

    private static final int EXECUTE_INSTRUCTION = 1;
    private static final int EXECUTE_BATCH_INSTRUCTION = 2;
    private static final int EXIT_PROGRAM = 3;

    private static void displayFunctionality() {
        System.out.println("1. enter script");
        System.out.println("2. enter batch script");
        System.out.println("3. stop enter");
        System.out.print("enter choice: ");
    }

    public static void main(String[] args) throws Exception {

        ScriptGenerator scriptGenerator = ScriptGeneratorFactory.createScriptGenerator();

        while (true) {
            displayFunctionality();

            int choice = enterChoice();

            if (choice == EXIT_PROGRAM)
                break;

            if (choice == EXECUTE_INSTRUCTION) {
                handleExecuteInstruction(scriptGenerator);
            }
            else if (choice == EXECUTE_BATCH_INSTRUCTION) {
                handleExecuteBatchInstruction(scriptGenerator);
            }
            else {
                System.out.println("This Choice has no functionality, input again!");
            }
        }

        handleInstructionSaving(scriptGenerator);
    }

    private static void handleExecuteInstruction(ScriptGenerator scriptGenerator) {

        scriptGenerator.switchMode(ScriptGenerator.ExecuteMode.Single);

        System.out.print("enter script: ");
        String instruction = enterString();

        System.out.println(instruction);

        try {
            scriptGenerator.executeInstruction(instruction);
        } catch (Exception e) {
            e.printStackTrace();
        }

        storeInstruction(scriptGenerator);
    }

    private static void handleExecuteBatchInstruction(ScriptGenerator scriptGenerator) {

        scriptGenerator.switchMode(ScriptGenerator.ExecuteMode.Batch);

        System.out.println("Start Enter Batch Instruction: ");
        System.out.println("Enter exit to stop");

        while (true) {
            String instruction = enterString();
            if (instruction.equalsIgnoreCase("stop"))
                break;

            try {
                scriptGenerator.executeInstruction(instruction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        storeInstruction(scriptGenerator);
    }

    private static void storeInstruction(ScriptGenerator scriptGenerator) {
        while (true) {
            String storeChoice = enterStoreChoice();
            if (storeChoice.equalsIgnoreCase("Y")) {
                break;
            }
            else if (storeChoice.equalsIgnoreCase("N")) { // checking content of string, not reference.
                System.out.println("Remove Success");
                scriptGenerator.removeInstruction();
                break;
            } else {
                System.out.print("Invalid input, enter again!: ");
            }
        }
    }

    private static String enterString() {
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        String instruction = "";
        try {
            instruction = buf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instruction;
    }

    private static int enterChoice() {
        int choice;
        while (true) {
            Scanner in = new Scanner(System.in);

            try {
                choice = in.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input, enter again!: ");
            }
        }
        return choice;
    }

    private static String enterStoreChoice() {
        System.out.print("Did you want to store instruction? (Y/N): ");
        Scanner s = new Scanner(System.in);
        String storeChoice = s.next();
        return storeChoice;
    }

    private static void handleInstructionSaving(ScriptGenerator scriptGenerator) {
        System.out.print("Did you wanna save file? (Y/N)");
        String choice = enterString();

        if (choice.equalsIgnoreCase("N"))
            return;

        System.out.print("enter script name which you want to save: ");
        String scriptName = enterString();
        scriptGenerator.writeScriptFile(scriptName);
    }

}
