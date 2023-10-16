import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SistemaExpertoGUI2 {

    private static int questionIndex;
    private static int questionNumber;
    private static boolean infoDisplayed = false;
    private static List<String> questions = new ArrayList<>();
    private static String[] answers;
    private static List<Integer> shuffledIndexes = new ArrayList<>();
    private static String userSelections = "";

    public static void main(String[] args) {
        cargarPreguntasDesdeArchivo("preguntas.txt");
        answers = new String[questions.size()];
        questionIndex = 0;
        questionNumber = 1;
        generarIndicesAleatorios();
        SistemaExperto_inicio();
    }

  public static void SistemaExperto_inicio() {
    String[] options = {"Iniciar sistema experto", "Salir"};

    int response = JOptionPane.showOptionDialog(null, "¡Bienvenido al sistema experto de adivinanzas de Pokemon!\n\nLa dinamica es la siguiente:\n" +
        "     * Te presentare 5 Pokemon misteriosos.\n" +
        "     * Piensa en un pokemon que te guste y yo tratare de adivinar en cuál estas pensando.\n" +
        "     * Si tu respuesta es afirmativa, haz clic en 'Si', si es negativa, haz clic en 'No'.\n\n" +
        "Elige una opcion para comenzar:", "Sistema Experto de Adivinanzas de Pokemon", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

    if (response == 0) {
        Adivinar();
    } else {
        JOptionPane.showMessageDialog(null, "\n*******-Nos vemos luego-*******");
        System.exit(0);
    }
}
 public static void Adivinar() {
    if (!infoDisplayed) {
        Informacion();
        infoDisplayed = true;
    }

    if (questionIndex < questions.size()) {
        int response = JOptionPane.showOptionDialog(null, "Pregunta #" + questionNumber + "\n" + questions.get(questionIndex), "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Si", "No"}, null);

        if (response == 0) {
            answers[questionIndex] = "Si";
            // Agrega la selección del usuario a la variable global
            userSelections += "Si";
        } else if (response == 1) {
            answers[questionIndex] = "No";
            // Agrega la selección del usuario a la variable global
            userSelections += "No";
        } else {
            JOptionPane.showMessageDialog(null, "Debes seleccionar 'Si' o 'No'.");
            Adivinar();
            return;
        }

        guardarRespuesta("Pregunta #" + questionNumber + ": " + questions.get(questionIndex) + ": " + answers[questionIndex]);
        questionIndex++;
        questionNumber++;
        Adivinar();
    } else {
        // Agrega tu código para buscar el resultado especial en el archivo
        String nombreArchivo = "solo.txt"; // Cambia esto al nombre de tu archivo
        String codigoBuscado = userSelections; // El código que deseas buscar

        boolean encontrado = false;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo));
            String linea;

            while ((linea = reader.readLine()) != null) {
                if (linea.contains(codigoBuscado)) {
                    int startIndex = linea.indexOf(codigoBuscado);
                    String nombre = linea.substring(0, startIndex);

                    // Muestra el nombre encontrado
                    JOptionPane.showMessageDialog(null, "Nombre encontrado: " + nombre);
                    encontrado = true;
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Si no se encontró una coincidencia, solicita al usuario que ingrese el nombre del Pokémon
        if (!encontrado) {
            String nombrePokemon = JOptionPane.showInputDialog("El Pokémon no se encontró en el archivo. Por favor, ingresa el nombre del Pokémon:");
            
            // Guarda el nombre del Pokémon en el archivo "result.txt"
            guardarNombrePokemon(nombrePokemon);
            
            // Guarda el registro en el archivo "solo.txt"
            guardarRespuestaSolo(nombrePokemon +":"+ userSelections);
        }

        JuguemosNuevamente();

        // Reinicia la variable global para el próximo juego
        userSelections = "";
    }
}

    // Método para guardar el nombre del Pokémon en el archivo "result.txt"
    public static void guardarNombrePokemon(String nombre) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt", true)); // Abre el archivo en modo append (agregar al final)
            writer.write(nombre); // Escribe el nombre del Pokémon
            writer.newLine(); // Nueva línea
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public static String cargarUnResultadoDesdeArchivo(String archivo) {
    try {
        BufferedReader reader = new BufferedReader(new FileReader(archivo));
        String result = reader.readLine(); // Leer solo la primera línea
        reader.close();
        return result;
    } catch (IOException e) {
        e.printStackTrace();
        return "Error al cargar el resultado";
    }
}
    public static void Informacion() {
        String informacion = cargarInformacionDesdeArchivo("informacion.txt"); // Cargamos la informacion desde el archivo
        JOptionPane.showMessageDialog(null, informacion);
    }

    public static void JuguemosNuevamente() {
        String[] options = {"Si", "No"};

        int response = JOptionPane.showOptionDialog(null, "¿Quieres jugar de nuevo?", "Reinicio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (response == JOptionPane.YES_OPTION) {
            questionIndex = 0;
            questionNumber = 1; // Restablecer el número de pregunta para el próximo juego
            generarIndicesAleatorios(); // Generamos nuevos indices aleatorios para el proximo juego
            SistemaExperto_inicio();
        } else {
            JOptionPane.showMessageDialog(null, "\n*******-Nos vemos luego-*******");
            System.exit(0);
        }
    }

    public static void guardarRespuesta(String respuesta) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("basededato.txt", true));
            writer.write(respuesta);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public static void guardarRespuestaSolo(String respuesta) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("solo.txt", true));
            writer.write(respuesta);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarResultadoEnArchivo(String result) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"));
            writer.write(result);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cargarPreguntasDesdeArchivo(String archivo) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String line;
            while ((line = reader.readLine()) != null) {
                questions.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String cargarInformacionDesdeArchivo(String archivo) {
        StringBuilder informacion = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String line;
            while ((line = reader.readLine()) != null) {
                informacion.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return informacion.toString();
        }

    public static void generarIndicesAleatorios() {
        shuffledIndexes.clear();
        for (int i = 0; i < questions.size(); i++) {
            shuffledIndexes.add(i);
        }
        Collections.shuffle(shuffledIndexes);
    }
}
