package ads.seg;

import ads.seg.exceptions.InvalidPasswordException;
import ads.seg.exceptions.UserAlreadyExistsException;
import ads.seg.exceptions.UserNotFoundException;

public class UserService {

    private UserRepository repository = new InMemory();

    public boolean register(String login, String password) throws UserAlreadyExistsException {
        if (repository.findByLogin(login) != null) {
            throw new UserAlreadyExistsException("O usuário já existe no banco de dados!");
        } else {
            User user = new User(login, password);
            repository.save(user);
            return true;
        }
    }

    public boolean updatePassword(String login, String oldPassword, String newPassword) throws UserNotFoundException, InvalidPasswordException {
        if (repository.findByLogin(login) == null) {
            throw new UserNotFoundException("O usuário não foi encontrado!");
        } else {
            User user = repository.findByLogin(login);
            if (!oldPassword.equals(user.getPassword())) {
                throw new InvalidPasswordException("A senha está equivocada!");
            } else {
                user.setPassword(newPassword);
                return true;
            }
        }
    }

    public boolean authenticate(String login, String password) {

    }


}
