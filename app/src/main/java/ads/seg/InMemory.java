package ads.seg;

import java.util.HashMap;
import java.util.Map;

public class InMemory implements UserRepository {

    private Map<String, User> repository = new HashMap<>();

    @Override
    public boolean save(User user) {
        if (findByLogin(user.getLogin()) != null) {
            return false;
        } else {
            repository.put(user.getLogin(), user);
            return true;
        }
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User findByLogin(String login) {

        return this.repository.get(login);

    }
}
