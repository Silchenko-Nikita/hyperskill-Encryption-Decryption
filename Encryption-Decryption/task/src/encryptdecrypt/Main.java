package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static String encrypt(String str, int key, String alg) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            char encryptedCh = ch;
            if (alg.equals("shift")) {
                if (ch >= 'a' && ch <= 'z') {
                    if ((ch + key) > 'z') {
                        encryptedCh = (char) ((ch + key) - 'z' + 'a' - 1);
                    } else {
                        encryptedCh = (char)  (ch + key);
                    }
                } else if (ch >= 'A' && ch <= 'Z') {
                    if ((ch + key) > 'Z') {
                        encryptedCh = (char) ((ch + key) - 'z' + 'a' - 1);
                    } else {
                        encryptedCh = (char)  (ch + key);
                    }
                }
            } else if (alg.equals("unicode")) {
                encryptedCh = (char)  (ch + key);
            }

            res.append(encryptedCh);
        }
        return res.toString();
    }

    private static String decrypt(String str, int key, String alg) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            char encryptedCh = ch;
            if (alg.equals("shift")) {
                if (ch >= 'a' && ch <= 'z') {
                    if ((ch - key) < 'a') {
                        encryptedCh = (char) ((ch - key) + 'z' - 'a' + 1);
                    } else {
                        encryptedCh = (char)  (ch - key);
                    }
                } else if (ch >= 'A' && ch <= 'Z') {
                    if ((ch - key) < 'A') {
                        encryptedCh = (char) ((ch - key) + 'z' - 'a' + 1);
                    } else {
                        encryptedCh = (char)  (ch - key);
                    }
                }
            } else if (alg.equals("unicode")) {
                encryptedCh = (char)  (ch - key);
            }

            res.append(encryptedCh);
        }
        return res.toString();
    }


    public static void main(String[] args) {
        String mode = "enc";
        int key = 0;
        String data = "";
        String in = null;
        String out = null;
        String alg = "shift";
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    mode = args[i + 1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    data = args[i + 1];
                    break;
                case "-in":
                    in = args[i + 1];
                    break;
                case "-out":
                    out = args[i + 1];
                    break;
                case "-alg":
                    alg = args[i + 1];
                    break;
            }
        }

        if (in != null) {
            File file = new File(in);
            try (Scanner scanner = new Scanner(file)) {
                data = scanner.nextLine();
            } catch (FileNotFoundException e) {
                System.out.println();
            }
        }

        String res = "";
        switch (mode) {
            case "enc":
                res = encrypt(data, key, alg);
                break;
            case "dec":
                res = decrypt(data, key, alg);
                break;
        }

        if (out == null) {
            System.out.println(res);
        } else {
            File file = new File(out);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(res);
            } catch (IOException e) {
                System.out.println();
            }
        }
    }
}
