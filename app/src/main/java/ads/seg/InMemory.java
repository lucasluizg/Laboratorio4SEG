package ads.seg;

import java.util.HashMap;
import java.util.Map;

public class InMemory implements UserRepository {

    private Map<String, String> repository = new HashMap<>();

    public Map<String, String> getRepository() {
        return repository;
    }

    public void setRepository(Map<String, String> repository) {
        this.repository = repository;
    }

    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User findByLogin(String login) {
        return null;
    }
}
