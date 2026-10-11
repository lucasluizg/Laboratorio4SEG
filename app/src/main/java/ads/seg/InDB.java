package ads.seg;

public class InDB implements UserRepository{
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
