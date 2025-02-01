package day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class day2 {
    static int MAX_LEVEL_DIFFERENCE = 3;
    static int MIN_LEVEL_DIFFERENCE = 1;

    public static void main(String[] args) {
        List<String> rawReportListAsStrings;
        List<List<Integer>> reportList = new ArrayList<>();

        try (BufferedReader inputFileReader = Files.newBufferedReader(Paths.get("src/main/java/day2/input.txt"))) {
            rawReportListAsStrings = inputFileReader.lines().toList();

            for (String line : rawReportListAsStrings) {
                String[] tmpStringLineSplit = line.split("\\s+");
                List<Integer> tmpIntLine = new ArrayList<>();
                for (String str : tmpStringLineSplit) {
                    tmpIntLine.add(Integer.parseInt(str));
                }
                reportList.add(tmpIntLine);
            }

            System.out.println("Safe Level count: " + day2a(reportList));
            System.out.println("Safe Level count with Damper: " + day2b(reportList));

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static Integer day2a(List<List<Integer>> inputList) {
        int safeLevelCount = 0;
        for (List<Integer> currentLevel : inputList) {
            if (doSafetyCheck(currentLevel)) {
                safeLevelCount++;
            }
        }
        return safeLevelCount;
    }

    private static Integer day2b(List<List<Integer>> inputList) {
        int safeLevelCount = 0;
        for (List<Integer> currentLevel : inputList) {
            if (doSafetyCheckWithDamper(currentLevel)) {
                safeLevelCount++;
            }
        }
        return safeLevelCount;
    }

    private static boolean doSafetyCheck(List<Integer> currentLevel) {
        return (isAllIncreasingLevels(currentLevel) || isAllDecreasingLevels(currentLevel)) && isWithinAcceptableRange(currentLevel);
    }

    private static boolean doSafetyCheckWithDamper(List<Integer> currentLevel) {
        for (int index = 0; index < currentLevel.size(); index++) {
            List<Integer> tmpSplitList1 = currentLevel.subList(0, index);
            List<Integer> tmpSplitList2 = currentLevel.subList(index + 1, currentLevel.size());
            List<Integer> tmpDampedList = Stream.concat(tmpSplitList1.stream(), tmpSplitList2.stream()).toList();
            if (doSafetyCheck(tmpDampedList)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAllIncreasingLevels(List<Integer> currentLevel) {
        for (int i = 0; i < currentLevel.size() - 1; i++) {
            if ((currentLevel.get(i) > currentLevel.get(i + 1))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAllDecreasingLevels(List<Integer> currentLevel) {
        for (int i = 0; i < currentLevel.size() - 1; i++) {
            if ((currentLevel.get(i) < currentLevel.get(i + 1))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isWithinAcceptableRange(List<Integer> currentLevel) {
        for (int i = 0; i < currentLevel.size() - 1; i++) {
            if ((Math.abs(currentLevel.get(i) - currentLevel.get(i + 1)) > MAX_LEVEL_DIFFERENCE) || (Math.abs(currentLevel.get(i) - currentLevel.get(i + 1)) < MIN_LEVEL_DIFFERENCE)) {
                return false;
            }
        }
        return true;
    }
}
