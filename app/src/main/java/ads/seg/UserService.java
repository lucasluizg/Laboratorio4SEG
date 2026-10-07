package ads.seg;

import ads.seg.exceptions.InvalidLoginException;
import ads.seg.exceptions.InvalidPasswordException;
import ads.seg.exceptions.UserAlreadyExistsException;
import ads.seg.exceptions.UserNotFoundException;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;


public class UserService {

    private UserRepository repository = new InMemory();
    private static final SecureRandom S_RANDOM = new SecureRandom();

    public boolean register(String login, String password) throws UserAlreadyExistsException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (repository.findByLogin(login) != null) {
            throw new UserAlreadyExistsException("O usuário já existe no banco de dados!");
        } else {
            byte[] salt = new byte[16];
            S_RANDOM.nextBytes(salt);
            int iterations = 600000;
            int keyLength = 128;
            char[] passwordChar = password.toCharArray();
            byte[] hashedPassword = PasswordHashing.hashPasswordWithPBKDF2(passwordChar, salt, "PBKDF2WithHmacSHA512", iterations,
                    keyLength);

            // Concatena o salt e o hashedPassword em um único array byte passwordByte
            byte[] passwordByte = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, passwordByte, 0, salt.length);
            System.arraycopy(hashedPassword, 0, passwordByte, salt.length, hashedPassword.length);

            // Falta colocar o $ para separar o salt e o hashedPassword.

            // O password recebe o passwordByte com o salt e o hashedPassword.
            password = new String(passwordByte, StandardCharsets.UTF_8);
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

    public boolean authenticate(String login, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (repository.findByLogin(login) == null) {
            throw new InvalidLoginException("O login ou a senha estão equivocados!");
        } else {
            User user = repository.findByLogin(login);
            if (!user.getPassword().equals(login)) {
                throw new InvalidLoginException("O login ou a senha estão equivocados!");
            } else {
                return true;
            }
        }
    }


}
