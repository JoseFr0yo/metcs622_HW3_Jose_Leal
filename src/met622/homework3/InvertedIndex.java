package met622.homework3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An inverted index maps each word to the list of positions, where the words appear in the text
 * Ex: "learn" [x, xx, xxx]
 */
public class InvertedIndex {

    /**
     * key = word
     * value = positions of word in text
     */
    private Map<String, List<Integer>> index = new HashMap<>();

    /**
     * Splits text into words and saves positions
     * Runs once resulting in O(n) where n is the number of words.
     */
    public void buildIndex(String text) {
        // clears so that positions are not doubled
        index.clear();

        /**
         * Made lowercase so words like "Learn" and "learn" are the same key.
         * Words are split by non-word characters.
         */
        String[] words = text.toLowerCase().split("\\W+");

        // Empty strings are skipped, not counted
        int position = 0;
        for (String word : words) {
            /**
             * split() makes an empty string when text starts with a non-word character
             */
            if (!word.isEmpty()) {
                /**
                 * Using a lambda function to create an empty list if there is no list for that word,
                 * and add the its position to the list
                 */
                index.computeIfAbsent(word, key -> new ArrayList<>()).add(position);
                position++;
            }
        }
    }

    /**
     * Returns position of a word, or empty list if not in text.
     * A hash lookup is O(1)
     */
    public List<Integer> getPositions(String word) {
        return index.getOrDefault(word.toLowerCase(), new ArrayList<>());
    }

    /**
     * The frequency of a word is the number of positions found at
     */
    public int countOccurrences(String word) {
        return getPositions(word).size();
    }

    /**
     * Searches the index for every word and returns word:count,
     * using the same format as WordSearcher.searchWords() from HW2 to compare results
     */
    public Map<String, Integer> searchWords(String[] words) {
        Map<String, Integer> wordCounts = new HashMap<>();
        // Lambda function to store each words count in the map
        Arrays.asList(words).forEach(word -> wordCounts.put(word, countOccurrences(word)));
        return wordCounts;
    }
}
