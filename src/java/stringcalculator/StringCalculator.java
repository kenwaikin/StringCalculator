package stringcalculator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class StringCalculator {

    static int add(String numbersString) {

        if (numbersString == null || numbersString.isBlank()) {
            return 0;
        }

        List<String> delimitedNumberStringList = getDelimitedNumberStringList(numbersString);

        List<Integer> numbersList
                = delimitedNumberStringList.stream()
                .filter(ns -> !ns.isBlank())
                .map(Integer::parseInt)
                .filter(n -> n <= 1000)
                .collect(Collectors.toList());

        List<Integer> negativeNumbers
                = numbersList.stream()
                .filter(i -> i < 0)
                .collect(Collectors.toList());

        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException("Negative numbersStrings are not allowed: " + negativeNumbers.toString());
        }

        return numbersList.stream().mapToInt(Integer::intValue).sum();
    }

    private static List<String> getDelimitedNumberStringList(String numbersString) {

        String delimiter = "[,\\n]";

        Pattern customDelimiterPattern = Pattern.compile("^//(.*\\n)(.*)");
        Matcher customerDelimiterMatcher = customDelimiterPattern.matcher(numbersString);

        if (customerDelimiterMatcher.matches()) {
            delimiter = "[" + customerDelimiterMatcher.group(1) + "]";
            numbersString = customerDelimiterMatcher.group(2);
        }

        return Arrays.asList(numbersString.split(delimiter));
    }
}
