
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import java.util.Scanner;

public class teste {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Bem vindo ao gerador e validador de assinaturas digitais.");
        System.out.println("O que voce quer fazer?");
        System.out.println("1. Gerar uma assinatura.");
        System.out.println("2. Verificar uma assinatura. (em desenvolvimento)");

        int answer = 0;

        while(true){
            try {
        
                while (!sc.hasNextInt()) {
                    System.out.println("Por favor, insira um número:");
                    sc.next();
                }

                answer = sc.nextInt();
                sc.nextLine();

                if (answer == 1 || answer == 2){
                    break;
                }else{
                    System.out.println("Por favor, insira um número válido (1 ou 2):");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (answer == 1){
           
            String message = "";

            System.out.println("Entre com a mensagem:");

            while(true){
                try {
                    message = sc.nextLine();
                    break;
    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            KeyPair keyPair = null;
            try {
                keyPair = generateKeyPair();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            byte[] signature = null;
            try {
                signature = sign(message, keyPair.getPrivate());
            } catch (Exception e) {
                e.printStackTrace();
            }

            boolean isValid = false;
            try {
                isValid = verify(message, signature, keyPair.getPublic());
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Mensagem: " + message);
            System.out.println("Assinatura digital: " + Base64.getEncoder().encodeToString(signature));
            if(isValid){
                System.out.println("Validado.");
            }else{
                System.out.println("Erro na validacao");
            }

        }else if(answer == 2){
            //todo
        }

        sc.close();
    }

    private static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // Key size
        return keyPairGenerator.generateKeyPair();
    }

    private static byte[] sign(String message, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(message.getBytes());
        return signature.sign();
    }

    private static boolean verify(String message, byte[] signature, PublicKey publicKey) throws Exception {
        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(publicKey);
        verifier.update(message.getBytes());
        return verifier.verify(signature);
    }
    
}