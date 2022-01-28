package main;

import java.io.*;
import java.util.Formatter;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, InterruptedException {
        String code = readConsole();

        String fileName = UUID.randomUUID().toString();
        String pathToFiles = "C:\\Users\\123\\Downloads\\pyCompiler\\compiler\\";
        File pythonFile = new File(pathToFiles + fileName + ".txt");
        pythonFile.createNewFile();
        Formatter formatter = new Formatter(pathToFiles + fileName + ".txt");
        formatter.format("%s", code);
        formatter.close();
        pythonFile.renameTo(new File(pathToFiles + fileName + ".py"));

        File bat = new File(pathToFiles + fileName + ".txt");
        bat.createNewFile();
        Formatter formatter2 = new Formatter(pathToFiles + fileName + ".txt");
        String command = "python  " + pathToFiles + fileName + ".py \n" +
                "exit";
        formatter2.format("%s", command);
        formatter2.close();
        bat.renameTo(new File(pathToFiles + fileName + ".bat"));

        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "call", pathToFiles + fileName + ".bat");
        pb.redirectErrorStream(true);
        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
        Process process = pb.start();
        printStream(process.getErrorStream(), process.getOutputStream());
        printStream(process.getInputStream(), process.getOutputStream());
    }

    public static String readFile(File outTextFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(outTextFile);
        StringBuilder out = new StringBuilder();
        while (scanner.hasNextLine()) {
            out.append(scanner.nextLine());
        }
        scanner.close();
        return out.toString();
    }

    public static String readConsole() throws IOException {
        System.out.println("----------------------------");
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String s = reader.readLine();
            if (s.equals("stop"))
                break;
            stringBuilder.append(s).append("\n");
        }
        return stringBuilder.toString();
    }

    public static void printToConsole(OutputStream out, String s) throws IOException {
        OutputStreamWriter outr = new OutputStreamWriter(out);
        BufferedWriter bw = new BufferedWriter(outr);
        bw.write(s);
    }

    public static void printStream(InputStream is, OutputStream out) throws IOException {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        int line;
        while ((line = br.read()) != -1) {
            System.out.print((char) line);
        }
    }

}
