import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner calcInput = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter your expression");
        int result;
        int[] numIntArray = new int[2]; // For integer number values
        boolean isRoman = false, isArabic = false;

        String[] operator;
        int[] operands = new int[2];

        String input = calcInput.nextLine();  // Read user input
        input = input.replaceAll("\\s",""); // Stripping from whitespaces
        input = input.toUpperCase(); // In case of Roman - to uppercase
        int len = input.length();

        // If Arabic and Roman at the same time throws exception
        if (input.matches(".*\\d.*")) {
            isArabic = true;
        }
        if (input.contains("I") || input.contains("V") || input.contains("X")) {
            isRoman = true;
        }
        if (isRoman && isArabic) {
            throw new IOException();
        }

        if (isRoman) {
            operator = input.split("[IVX]");
            Arrays.sort(operator);
            String[] operandsRoman = input.split("[-+*/]");
            for (int i = 0; i < operandsRoman.length; i++) {
                operands[i] = convertFromRoman(operandsRoman[i]);
            }
        } else {
            operator = input.split("[0-9]+");
            Arrays.sort(operator);
            String[] operandsString = input.split("[-+*/]");
            for (int i = 0; i < operandsString.length; i++) {
                operands[i] = Integer.parseInt(operandsString[i]);
            }
        }

        // Only numbers between 1 and 10 accepted
        for (int operand : operands) {
            if ((operand > 10) || (operand < 1)) {
                throw new IOException();
            }
        }

        result = switch (operator[2]) {
            case "-" -> Operation.SUBTRACT(operands);
            case "+" -> Operation.ADD(operands);
            case "/" -> Operation.DIVIDE(operands);
            case "*" -> Operation.MULTIPLY(operands);
            default -> throw new IOException();
        };

        // For Roman can only be positive result
        if (isRoman) {
            if (result < 0) {
                throw new IOException();
            } else {
                System.out.println(convertToRoman(result));
            }
        } else {
            System.out.println(result);
        }

    }

    public static int convertFromRoman(String romanNumber) {
        int number = 0;

        for (int i = 0; i < romanNumber.length(); i++)
        {
            switch (romanNumber.charAt(i))
            {
                case 'X':
                    number += 10;
                    break;
                case 'V':
                    number += 5;
                    break;
                case 'I':
                    number += 1;
                    break;
                default:
                    number = -100;
                    break;
            }
        }

        return number;
    }

    public static String convertToRoman(int value){
        String romanNum = "";
        while (value > 0) {
            while (value >= 100) {
                romanNum = romanNum + "C";
                value -= 100;
            }
            while (value >= 90) {
                romanNum = romanNum + "XC";
                value -= 90;
            }
            while (value >= 50) {
                romanNum = romanNum + "L";
                value -= 50;
            }
            while (value >= 40) {
                romanNum = romanNum + "XL";
                value -= 40;
            }
            while (value >= 10) {
                romanNum = romanNum + "X";
                value -= 10;
            }
            while (value >= 9) {
                romanNum = romanNum + "IX";
                value -= 9;
            }
            while (value >= 5) {
                romanNum = romanNum + "V";
                value -= 5;
            }
            while (value >= 4) {
                romanNum = romanNum + "IV";
                value -= 4;
            }
            while (value >= 1) {
                romanNum = romanNum + "I";
                value -= 1;
            }

        }
        return romanNum;
    }
}