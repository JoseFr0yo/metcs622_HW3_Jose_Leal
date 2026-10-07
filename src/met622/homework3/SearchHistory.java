package met622.homework3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

// The SearchHistory class notes every search term, and when it was searched.
public class SearchHistory {

    // Constant for the date format used.
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm:ss");

    // key = search term, value = date and time searched.
    private Map<String, LocalDateTime> history = new HashMap<>();

    // This records a search term with the date and time.
    public void addSearch(String term) {
        history.put(term, LocalDateTime.now());
    }

    // Prints search terms with timestamps
    public void printHistory() {
        System.out.println("Search History:");
        if (history.isEmpty()){
            System.out.println("No searches have been made.");
            return;
        }
        for (String term : history.keySet()) {
            LocalDateTime searchedAt = history.get(term);
            System.out.println(term + ": " + searchedAt.format(DATE_FORMAT));
        }
    }
}