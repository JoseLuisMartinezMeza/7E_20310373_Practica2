import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AcomodoPokemon {
    public static void main(String[] args) {
        String resultadosFile = "result.txt";
        String respuestasFile = "basededato.txt";
        String soloFile = "solo.txt";

        try {
            BufferedReader resultadosReader = new BufferedReader(new FileReader(resultadosFile));
            BufferedReader respuestasReader = new BufferedReader(new FileReader(respuestasFile));
            BufferedWriter soloWriter = new BufferedWriter(new FileWriter(soloFile));

            String nombrePokemon;
            String respuestasLine;
            List<String> respuestas = new ArrayList<>();

            while ((nombrePokemon = resultadosReader.readLine()) != null) {
                // Leer el nombre del Pokémon
                if (nombrePokemon.startsWith("Resultado: ")) {
                    nombrePokemon = nombrePokemon.replace("Resultado: ", "");
                }

                soloWriter.write(nombrePokemon + ":");

                // Leer las respuestas del archivo "respuestas.txt"
                for (int i = 0; i < 10; i++) {
                    respuestasLine = respuestasReader.readLine();
                    if (respuestasLine != null) {
                        String respuesta = respuestasLine.endsWith("Si") ? "Si" : "No";
                        respuestas.add(respuesta);
                    }
                }

                // Escribir las respuestas en el archivo "solo.txt"
                for (String respuesta : respuestas) {
                    soloWriter.write(respuesta);
                }

                soloWriter.newLine();
                respuestas.clear();
            }

            resultadosReader.close();
            respuestasReader.close();
            soloWriter.close();

            System.out.println("Acomodo de Pokémon y respuestas guardado en " + soloFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}