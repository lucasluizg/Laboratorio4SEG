package ads.seg;

import ads.seg.exceptions.InvalidPasswordException;
import ads.seg.exceptions.UserAlreadyExistsException;
import ads.seg.exceptions.UserNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class App {

    static void main() throws UserAlreadyExistsException, NoSuchAlgorithmException, InvalidKeySpecException, UserNotFoundException, InvalidPasswordException {

        Scanner sc = new Scanner(System.in);

        System.out.println("""
                           SISTEMA DE CADASTRO DE USUÁRIOS:
                           Escolha qual encriptamento vai utilizar: BCrypt ou PBKDF2:
                           """);
        String encriptamento = sc.nextLine();
        UserService bancoDeDados = new UserService(encriptamento);

        menu();
        int opcoes = sc.nextInt();

        while (opcoes != 0) {
            if (opcoes == 1) {
                sc.nextLine();
                System.out.println("Digite o login a ser cadastrado: ");
                String login = sc.nextLine();

                System.out.println("Digite a senha do usuário a ser cadastrado: ");
                String password = sc.nextLine();

                if (bancoDeDados.register(login, password)) {
                    System.out.println("O usuário " + login + " foi registrado");
                } else {
                    System.out.println("O usuário " + login + " não foi registrado!");
                }

            } else if (opcoes == 2) {
                sc.nextLine();
                System.out.println("Digite o login do usuário a ser atualizado: ");
                String login = sc.nextLine();

                System.out.println("Digite a senha antiga do usuário: ");
                String oldPassword = sc.nextLine();

                System.out.println("Digite a senha nova do usuário: ");
                String newPassword = sc.nextLine();

                if (bancoDeDados.updatePassword(login, oldPassword, newPassword)) {
                    System.out.println("O usuário " + login + " foi atualizado!");
                } else {
                    System.out.println("O usuário " + login + " não foi atualizado!");
                }

            } else if (opcoes == 3) {
                sc.nextLine();
                System.out.println("Digite o login do usuário a ser autenticado: ");
                String login = sc.nextLine();

                System.out.println("Digite a senha do usuário a ser autenticado: ");
                String password = sc.nextLine();

                if (bancoDeDados.authenticate(login, password)) {
                    System.out.println("O usuário " + login + " foi autenticado!");
                } else {
                    System.out.println("O usuário " + login + " não foi autenticado!");
                }

            }
            menu();
            opcoes = sc.nextInt();
        }

    }

    public static void menu() {
        System.out.println("""
                Escolha as opções:
                1 - Registrar usuário.
                2 - Atualizar usuário.
                3 - Autenticar usuário.
                0 - Sair.
                """);
    }
}