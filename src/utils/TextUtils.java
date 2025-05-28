package utils;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TextUtils {
    // Permet d'extraire les mots dans les panneaux de texte 
    public static Set<String> extractWords(String text) {
        Set<String> words = new HashSet<>();
        if (text == null || text.trim().isEmpty()) {
            return words;
        }
        
        Scanner scanner = new Scanner(text.toLowerCase());
        scanner.useDelimiter("[^\\p{L}]+");
        
        while (scanner.hasNext()) {
            String word = scanner.next().trim();
            if (!word.isEmpty()) {
                words.add(word);
            }
        }
        scanner.close();
        return words;
    }
}
