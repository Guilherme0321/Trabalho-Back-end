package sistemaInteligente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ConnectorInteligenteSystem {
    public static String sendToSystemInteligente(String[] x, int y) {
        String output = "";
        try {
            String pythonExecutable = "python";
            String pythonScript = "\\Back_end_THELDO\\src\\main\\java\\sistemaInteligente\\SistemaIntegente.py";

            // Construa o comando completo
            String[] command = new String[]{pythonExecutable, pythonScript};

            // Adicione os parâmetros ao comando
            for (String param : x) {
                command = Arrays.copyOf(command, command.length + 1);
                command[command.length - 1] = param;
            }
            command = Arrays.copyOf(command, command.length + 1);
            command[command.length - 1] = Integer.toString(y);

            // Execute o processo
            Process process = Runtime.getRuntime().exec(command);

            // Capture a saída do processo
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                output += line;
                System.out.println(line);
            }

            // Aguarde o término do processo
            int exitCode = process.waitFor();
            System.out.println("Script Python concluído com código de saída: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return output;
    }
}
