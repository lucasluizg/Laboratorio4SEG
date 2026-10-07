package ads.seg;

import ads.seg.exceptions.InvalidLoginException;
import ads.seg.exceptions.InvalidPasswordException;
import ads.seg.exceptions.UserAlreadyExistsException;
import ads.seg.exceptions.UserNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class UserService {

    private UserRepository repository = new InMemory();
    private static final SecureRandom S_RANDOM = new SecureRandom();
    private int iterations = 600000;
    private int keyLength = 128;
    private Base64.Encoder encoder = Base64.getEncoder();
    private Base64.Decoder decoder = Base64.getDecoder();

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

            // Concatena o salt e o hashedPassword em um único String convertendo pra String.
            String saltBase64 = encoder.encodeToString(salt);
            String hashedBase64 = encoder.encodeToString(hashedPassword != null ? hashedPassword : new byte[0]);
            // O password recebe o salt mais o cifrão mais o hashedPassword concatenados convertidos em base 64.
            password = saltBase64 + '$' + hashedBase64;
            User user = new User(login, password);
            repository.save(user);
            return true;
        }
    }

    public boolean updatePassword(String login, String oldPassword, String newPassword) throws UserNotFoundException, InvalidPasswordException, UserAlreadyExistsException, NoSuchAlgorithmException, InvalidKeySpecException {
        User user = repository.findByLogin(login);
        if (user == null) {
            throw new UserNotFoundException("O usuário não foi encontrado!");
        } else {
            if (authenticate(login, oldPassword)) {
                // TODO Converter newPassword para Hash.
                user.setPassword(newPassword);
                repository.update(user);
            }
        }
        return false;
    }

    public boolean authenticate(String login, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = repository.findByLogin(login);
        if (user == null) {
            throw new InvalidLoginException("O login ou a senha estão equivocados!");
        } else {
            String[] sp = user.getPassword().split("\\$");

            byte[] hashedPassword = decoder.decode(sp[1]);
            byte[] salt = decoder.decode(sp[0]);
            char[] passwordChar = password.toCharArray();

            return PasswordHashing.verifyPasswordWithPBKDF2(passwordChar, salt, "PBKDF2WithHmacSHA512", iterations, keyLength, hashedPassword);
        }
    }


}
