package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day3 {
    static String EXTRACT_MULTIPLY_EXPR_REGEX = "mul\\(\\d{1,5},\\d{1,5}\\)";
    static String EXTRACT_MULTIPLICATION_OPERANDS_FROM_MULTIPLY_EXPR = "\\d+";

    public static void main(String[] args) {

        day3a();
        day3b();


    }

    private static void day3a() {
        List<String> inputLines;
        Pattern mulExprPattern = Pattern.compile(EXTRACT_MULTIPLY_EXPR_REGEX);
        List<String> multiplyExprLines = new ArrayList<>();
        try {
            inputLines = Files.readAllLines(Paths.get("src/main/java/day3/input.txt"));

            inputLines.forEach(line -> {
                if (!line.startsWith("don't")) {
                    Matcher matcher = mulExprPattern.matcher(line);
                    while (matcher.find()) {
                        multiplyExprLines.add(matcher.group());
                    }
                }
            });

            System.out.println("Sum of all multiply expressions: " + calculateSumOfMultiplyExpressions(multiplyExprLines));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static void day3b() {
        String inputLines;
        Pattern mulExprPattern = Pattern.compile(EXTRACT_MULTIPLY_EXPR_REGEX);
        List<String> enabledMultipleExprRegexMatches = new ArrayList<>();
        try {
            //Day3b Approach
            inputLines = Files.readString(Paths.get("./src/main/java/day3/input.txt")); // Reading as single string to mimic Rust approach. Reading lines seems to leave a chunk of don't expressions
            inputLines = inputLines.replaceAll("\\n", " ");
            inputLines = inputLines.replaceAll("don't\\(\\)", "\ndon't()");
            inputLines = inputLines.replaceAll("do\\(\\)", "\ndo()");

            List<String> splitInputLines = new ArrayList<>(Arrays.stream(inputLines.split("\\n")).toList());

            splitInputLines.forEach(line -> {
                if (!line.startsWith("don't")) {
                    Matcher matcher = mulExprPattern.matcher(line);
                    while (matcher.find()) {
                        enabledMultipleExprRegexMatches.add(matcher.group());
                    }
                }
            });

            System.out.println("Sum of enabled multiply expressions: " + calculateSumOfMultiplyExpressions(enabledMultipleExprRegexMatches));

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static int calculateSumOfMultiplyExpressions(List<String> inputList) {
        int sum = 0;
        Pattern mulOperandsPattern = Pattern.compile(EXTRACT_MULTIPLICATION_OPERANDS_FROM_MULTIPLY_EXPR);
        for (String val : inputList) {
            List<Integer> operandsList = new ArrayList<>();
            Matcher matcher = mulOperandsPattern.matcher(val);
            while (matcher.find()) {
                operandsList.add(Integer.parseInt(matcher.group()));
            }
            sum += operandsList.get(0) * operandsList.get(1);
        }
        return sum;
    }
}
