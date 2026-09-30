package ads.seg;

public interface UserRepository {

    public boolean save(User user);
    public boolean update(User user);
    public User findByLogin(String login);

}
