package ads.seg;

import ads.seg.exceptions.UserAlreadyExistsException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class App {

    static void main() throws UserAlreadyExistsException, NoSuchAlgorithmException, InvalidKeySpecException {

        UserService bancoDeDados = new UserService();

        bancoDeDados.register("Lucas", "hsahik");

        IO.println("fim");


    }

}
