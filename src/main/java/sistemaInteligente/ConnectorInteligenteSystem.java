package sistemaInteligente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConnectorInteligenteSystem {

    public static String send(String x, int i) {
        // Caminho para o script Python (caminho relativo)
        String scriptPython = "src/main/java/sistemaInteligente/SistemaIntegente.py";

        // Parâmetros a serem passados para o script Python
        String parametroString = x;
        String second = i + "";

        // Chama a função para executar o script Python e obter a saída
        String saidaPython = "";
        try {
            saidaPython = executarScriptPython(scriptPython, parametroString, second);
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return saidaPython;
    }

    public static String executarScriptPython(String scriptPath, String parametroString, String second) throws IOException, InterruptedException {
        // Obtém o diretório atual
        String diretorioAtual = System.getProperty("user.dir");

        // Constrói o caminho completo para o script Python
        String caminhoCompleto = diretorioAtual + "/" + scriptPath;

        // Constrói a lista de comandos para executar o script Python
        String[] comandos = {"python", caminhoCompleto, parametroString, second};

        // Executa o script Python a partir do programa Java
        Process processo = new ProcessBuilder(comandos)
                .directory(new java.io.File(diretorioAtual))  // Define o diretório de trabalho
                .redirectErrorStream(true)
                .start();

        // Lê a saída do script Python
        BufferedReader leitor = new BufferedReader(new InputStreamReader(processo.getInputStream()));
        StringBuilder resultado = new StringBuilder();
        String linha;
        while ((linha = leitor.readLine()) != null) {
            resultado.append(linha).append("\n");
        }

        // Aguarda o término do processo
        int status = processo.waitFor();
        System.out.println("Script Python retornou: " + status);

        return resultado.toString();
    }
}
