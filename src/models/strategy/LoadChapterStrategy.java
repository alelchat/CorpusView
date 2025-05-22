package models.strategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LoadChapterStrategy implements LoadStrategy {
    @Override
    public Map<String, String> load(File file) {
        Map<String, String> chapters = new LinkedHashMap<>();
        String currentChapter = null;
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(
                new FileInputStream(file), 
                StandardCharsets.UTF_8
            )
        )) {
            String line;
            boolean firstLineBOM = true; // Gestion du BOM UTF-8
            
            while ((line = br.readLine()) != null) {
                // Correction du BOM sur la première ligne
                if (firstLineBOM) {
                    line = removeUTF8BOM(line);
                    firstLineBOM = false;
                }
                
                // Détection des chapitres avec regex améliorée
                if (line.matches("^\\s*[IVXLCDM]+\\s*[\\.\\:]?\\s*$")) {
                    if (currentChapter != null) {
                        chapters.put(currentChapter, content.toString().trim());
                    }
                    currentChapter = line.replaceAll("[^IVXLCDM]", "").trim();
                    content = new StringBuilder();
                } else if (!line.trim().isEmpty()) {
                    content.append(line).append("\n");
                }
            }
            
            if (currentChapter != null) {
                chapters.put(currentChapter, content.toString().trim());
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier " + file.getName());
            e.printStackTrace();
        }
        return chapters;
    }

    private String removeUTF8BOM(String line) {
        // Supprime le BOM (Byte Order Mark) si présent
        return line.startsWith("\uFEFF") ? line.substring(1) : line;
    }
}