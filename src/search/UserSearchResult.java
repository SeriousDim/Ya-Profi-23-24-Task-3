package search;

import model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserSearchResult {
    public Set<User> users;
    public int exactMatches;
    public int substringMatches;

    public UserSearchResult() {
        this.users = new HashSet<>();
    }
}
