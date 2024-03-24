package search;

import model.User;

import java.util.*;

public class UserSearchEngine {

    private List<User> userSource;

    public UserSearchEngine(List<User> userSource) {
        this.userSource = userSource;
    }

    private String[] splitSearchString(String searchString) {
        return searchString.split(" ");
    }

    private boolean hasWord(User user, String word) {
        return user.getName().equalsIgnoreCase(word) ||
                user.getEmail().equalsIgnoreCase(word) ||
                user.getPatronymic().equalsIgnoreCase(word) ||
                user.getSurname().equalsIgnoreCase(word);
    }

    private boolean hasSubstrings(User user, String word) {
        return user.getName().toLowerCase().contains(word.toLowerCase()) ||
                user.getEmail().toLowerCase().contains(word.toLowerCase()) ||
                user.getPatronymic().toLowerCase().contains(word.toLowerCase()) ||
                user.getSurname().toLowerCase().contains(word.toLowerCase());
    }

    private void findExactMatches(HashMap<String, UserSearchResult> results, String[] words) {
        for (var word: words) {
            for (var user: this.userSource) {
                if (hasWord(user, word)) {
                    results.get(word).users.add(user);
                    results.get(word).exactMatches++;
                }
            }
        }
    }

    private void findSubstringMatches(HashMap<String, UserSearchResult> results, String[] words) {
        for (var word: words) {
            for (var user: this.userSource) {
                if (hasSubstrings(user, word)) {
                    results.get(word).users.add(user);
                    results.get(word).substringMatches++;
                }
            }
        }
    }

    private List<User> getSearchedUsers(HashMap<String, UserSearchResult> results) {
        var users = new ArrayList<>(results.values().stream().toList());
        var totalMatches = new HashMap<User, Integer>();

        for (var ob: users) {
            for (var user: ob.users) {
                if (!totalMatches.containsKey(user)) {
                    totalMatches.put(user, 0);
                }
                totalMatches.put(user, totalMatches.get(user) + 1);
            }
        }

        var entries = new ArrayList<>(totalMatches.entrySet().stream().toList());
        entries.sort(Comparator.comparingInt(Map.Entry::getValue));
        Collections.reverse(entries);

        return entries.stream().map(Map.Entry::getKey).toList();
    }

    public List<User> search(String searchString) {
        var words = splitSearchString(searchString);

        var matches = new HashMap<String, UserSearchResult>();

        for (var word: words) {
            matches.put(word, new UserSearchResult());
        }

        findExactMatches(matches, words);
        findSubstringMatches(matches, words);

        return getSearchedUsers(matches);
    }

}
