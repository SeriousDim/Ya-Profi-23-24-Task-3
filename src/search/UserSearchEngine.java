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
        users.sort((o1, o2) -> {
            if (o1.exactMatches + o1.substringMatches < o2.exactMatches + o2.substringMatches) {
                return -1;
            } else if (o1.exactMatches + o1.substringMatches == o2.exactMatches + o2.substringMatches) {
                if (o1.exactMatches < o2.exactMatches) {
                    return-1;
                } else if (o1.exactMatches == o2.exactMatches) {
                    if (o1.substringMatches < o2.substringMatches)
                        return -1;
                    else if (o1.substringMatches == o2.substringMatches)
                        return 0;
                }
            }

            return 1;
        });
        Collections.reverse(users);

        var result = new HashSet<User>();

        for (var ob: users) {
            result.addAll(ob.users);
        }

        return result.stream().toList();
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
