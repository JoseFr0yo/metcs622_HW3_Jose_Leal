package met622.homework3;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// The WordSearcher class counts the number of times word appear using regex.
public class WordSearcher {

    // Counts case-insensitive and whole words inside the text.
    public int countOccurrences(String text, String word) {
        // \\b is a word boundary, so words like "learn" do not match "learning"
        Pattern pattern = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        int count = 0;
        // find() moves to the next word, and returns false when there are no more matches.
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    // Searches text for every word and returns word:count.
    public Map<String, Integer> searchWords(String text, String[] words) {
        // HashMap stores key:value pairs
        Map<String, Integer> wordCounts = new HashMap<>();
        for (String word : words) {
            wordCounts.put(word, countOccurrences(text, word));
        }
        return wordCounts;
    }
}