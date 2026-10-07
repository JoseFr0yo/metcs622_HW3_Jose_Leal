package met622.homework3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * SearchComparison compares the word frequency in data.json.
 * 1. Inverted index, builds once and then searches per word.
 * 2. Regex search, searches the whole text for each word.
 */
public class SearchComparison {

    /**
     * Each operation is repeated x many times to find the average,
     * since I am working with a small file, it is difficult to measure averages accurately.
     */
    private static final int REPETITIONS = 1000;

    public static void main(String[] args) {
        // Reads saved API response from project root
        ResponseFileReader fileReader = new ResponseFileReader();
        String savedText = fileReader.readFile("data.json");

        // readFile() printed fail, stop running
        if (savedText == null) {
            return;
        }

        // Searching same 3 words from HW2
        String[] wordsToFind = {"learn", "hours", "language"};

        // Record search with timestamp like in HW2
        SearchHistory searchHistory = new SearchHistory();
        for (String word : wordsToFind) {
            searchHistory.addSearch(word);
        }

        WordSearcher wordSearcher = new WordSearcher();
        InvertedIndex invertedIndex = new InvertedIndex();

        // Regex Search
        Map<String, Integer> regexCounts = wordSearcher.searchWords(savedText, wordsToFind);

        // Inverted Index Search
        invertedIndex.buildIndex(savedText);
        Map<String, Integer> indexCounts = invertedIndex.searchWords(wordsToFind);

        /**
         * Regex and Inverted searches must have the same results, otherwise there is an error
         * and comparison is useless
         */
        if (regexCounts.equals(indexCounts)) {
            System.out.println("Both Regex and Index found same counts.");
        } else {
            System.out.println("Error: different number of counts found");
        }

        System.out.println("Regex counts: " + regexCounts);
        System.out.println("Inverted index counts and positions:");
        // Lambda function to print each word, count, and char number where appeared
        indexCounts.forEach((word, count) ->
                System.out.println(word + ": " + count + " times, at word positions "
                + invertedIndex.getPositions(word)));

        /**
         * Time results, measures the average time of each run.
         * total is divided by 1000 (REPETITIONS) to find the average time of a single run
         */
        long start = System.nanoTime();
        for (int i = 0; i < REPETITIONS; i++) {
            wordSearcher.searchWords(savedText, wordsToFind);
        }
        long regexSearchTime = (System.nanoTime() - start) / REPETITIONS;

        start = System.nanoTime();
        for (int i = 0; i < REPETITIONS; i++) {
            invertedIndex.buildIndex(savedText);
        }
        long indexBuildTime = (System.nanoTime() - start) / REPETITIONS;

        start = System.nanoTime();
        for (int i = 0; i < REPETITIONS; i++) {
            invertedIndex.searchWords(wordsToFind);
        }
        long indexSearchTime = (System.nanoTime() - start) / REPETITIONS;

        System.out.println();
        System.out.println("Average time over " + REPETITIONS + " runs (nanoseconds):");
        System.out.println("Regex search (3 words): " + regexSearchTime);
        System.out.println("Inverted index build: " + indexBuildTime);
        System.out.println("Inverted index search (3 words): " + indexSearchTime);

        writeResults(regexSearchTime, indexBuildTime, indexSearchTime);

        System.out.println();
        searchHistory.printHistory();
    }

    // Saves results as CSV file to make the chart
    private static void writeResults(long regexSearchTime, long indexBuildTime, long indexSearchTime) {
        String fileName = "timing_results.csv";
        // Writer closes automatically when block ends
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Approach,Average time (ns)\n");
            writer.write("Regex search," + regexSearchTime + "\n");
            writer.write("Inverted index search," + indexSearchTime + "\n");
            writer.write("Inverted index build + search," + (indexBuildTime + indexSearchTime) + "\n");
            System.out.println("Timing results saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Could not write " + fileName + ": " + e);
        }
    }
}
