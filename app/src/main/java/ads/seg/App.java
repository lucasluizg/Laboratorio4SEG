package ads.seg;

import ads.seg.exceptions.InvalidPasswordException;
import ads.seg.exceptions.UserAlreadyExistsException;
import ads.seg.exceptions.UserNotFoundException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class App {

    static void main() throws UserAlreadyExistsException, NoSuchAlgorithmException, InvalidKeySpecException, UserNotFoundException, InvalidPasswordException {

        UserService bancoDeDados = new UserService();

        bancoDeDados.register("Lucas", "senha");
        System.out.println(bancoDeDados.authenticate("Lucas", "senha"));
        System.out.println(bancoDeDados.updatePassword("Lucas", "senha", "chave"));

    }
}