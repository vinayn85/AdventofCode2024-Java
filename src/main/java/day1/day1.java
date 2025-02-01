package day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class day1 {
    static Integer totalDistance = 0;
    static Integer similarityScore = 0;

    public static void main(String[] args) {

        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        try (BufferedReader inputReader = Files.newBufferedReader(Paths.get("./src/main/java/day1/input.txt"))) {
            List<String> inputLines = inputReader.lines().toList();
            inputLines.forEach(line -> {
                String[] tmpLineSplit = line.split("\\s+");
                leftList.add(Integer.parseInt(tmpLineSplit[0]));
                rightList.add(Integer.parseInt(tmpLineSplit[1]));
            });


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        day1a(leftList, rightList);
        day1b(leftList, rightList);
    }

    private static void day1a(List<Integer> list1, List<Integer> list2) {
        Collections.sort(list1);
        Collections.sort(list2);

        for (int i = 0; i < list1.size(); i++) {
            totalDistance += Math.abs(list1.get(i) - list2.get(i));
        }

        System.out.println("Total distance: " + totalDistance);

    }

    private static void day1b(List<Integer> list1, List<Integer> list2) {
        for (int currentValue : list1) {
            int occurenceCount = 0;
            for (int otherValue : list2) {
                if (currentValue == otherValue) {
                    occurenceCount++;
                }
            }
            similarityScore += occurenceCount * currentValue;
        }
        System.out.println("Similarity score: " + similarityScore);
    }
}

