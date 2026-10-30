package cva;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
This class is used for sorting a list of country objects using user-selected criteria.
It uses a divide-and-conquer algorithm to sort lists.
A comparator function exists to handle different sorting rules.
 */
public class SortUtil {

    public static List<Country> sort(List<Country> list, String sortType) {

        Comparator<Country> comparator = getComparator(sortType);
        if (comparator == null || list == null) return list;

        return mergeSort(list, comparator);
    }

    /*
    A recursive merge sort function that breaks the list down into smaller parts,
    sorts them and merges them back together.
     */
    private static List<Country> mergeSort(List<Country> list, Comparator<Country> c) {

        if (list.size() <= 1) return list;

        int mid = list.size() / 2;

        List<Country> left = mergeSort(new ArrayList<>(list.subList(0, mid)), c);
        List<Country> right = mergeSort(new ArrayList<>(list.subList(mid, list.size())), c);

        return merge(left, right, c);
    }

    // This function merges two already sorted lists into a new singular sorted list.
    private static List<Country> merge(List<Country> left, List<Country> right, Comparator<Country> c) {

        List<Country> result = new ArrayList<>();

        int i = 0, j = 0;
        // This while loop compares elements from both lists and merges them in order.
        while (i < left.size() && j < right.size()) {

            if (c.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i++));
            }
            else {
                result.add(right.get(j++));
            }
        }

        // Adds any remaining elements - if they exist.
        while (i < left.size()) {
            result.add(left.get(i++));
        }
        while (j < right.size()) {
            result.add(right.get(j++));
        }

        return result;
    }

    // This function returns the correct comparator based on what filtering option the user has selected.
    private static Comparator<Country> getComparator(String type) {

        // Returns null if the string is null.
        if (type == null) {
            return null;
        }

        // Switch/Case loop that finds the correct comparator.
        return switch (type) {

            case "Name A-Z" -> Comparator.comparing(Country::getCommonName, String.CASE_INSENSITIVE_ORDER);
            case "Name Z-A" -> Comparator.comparing(Country::getCommonName, String.CASE_INSENSITIVE_ORDER).reversed();
            case "Population High → Low" -> Comparator.comparingLong(Country::getPopulation).reversed();
            case "Population Low → High" -> Comparator.comparingLong(Country::getPopulation);
            case "Area High → Low" -> Comparator.comparingDouble(Country::getArea).reversed();
            case "Area Low → High" -> Comparator.comparingDouble(Country::getArea);

            default -> null; // Null if no cases match.
        };
    }
}