package day3;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day3 {
    static String EXTRACT_MULTIPLY_EXPR_REGEX = "mul\\(\\d{1,5},\\d{1,5}\\)";
    static String EXTRACT_MULTIPLICATION_OPERANDS_FROM_MULTIPLY_EXPR = "\\d+";

    public static void main(String[] args) {
        List<String> inputLines = new ArrayList<>();
        List<String> cleanedInputLines = new ArrayList<>();
        Pattern mulExprPattern = Pattern.compile(EXTRACT_MULTIPLY_EXPR_REGEX);
        List<String> multiplyExprRegexMatches = new ArrayList<>();
        List<String> enabledMultipleExprRegexMatches = new ArrayList<>();
        List<String> enabledMultipleExpressions= new ArrayList<>();
        List<String> splitInputLines = new ArrayList<>();

        try (BufferedReader inputFileReader = Files.newBufferedReader(Paths.get("./src/main/java/day3/input.txt"))) {
            inputFileReader.lines().forEach(inputLines::add);

            inputLines.forEach(line->{
                line = line.trim();
                line = line.replaceAll("\\s+", "");
                line = line.replace("\n","");
                line = line.replace("\r","");
                line = line.replace("don't()","\ndon't()");
                line = line.replace("do()","\ndo()");
                cleanedInputLines.add(line);
            });

            for (String line : cleanedInputLines) {
                String[] tmpSplit = line.split("\n");
                splitInputLines.addAll(Arrays.stream(tmpSplit).toList());
            }

            splitInputLines.forEach(line->{
                if(!line.startsWith("don't")){
                    enabledMultipleExpressions.add(line);
                }
            });

            for (String line : enabledMultipleExpressions) {
                Matcher matcher = mulExprPattern.matcher(line);
                while (matcher.find()) {
                    enabledMultipleExprRegexMatches.add(matcher.group());
                }
            }

            System.out.println(calculateSumOfMultiplyExpressions(enabledMultipleExprRegexMatches));

        } catch (Exception e) {
            e.printStackTrace();
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
