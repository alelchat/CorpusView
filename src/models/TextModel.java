package models;

import java.util.*;
import models.strategy.LoadStrategy;
import observer.Observer;
import java.io.File;

public class TextModel {
    private String currentText;
    private Map<String, String> chapters = new LinkedHashMap<>();
    private List<Observer> observers = new ArrayList<>();
    private LoadStrategy loadStrategy;
    private File currentFile;

    public void setLoadStrategy(LoadStrategy strategy) {
        this.loadStrategy = strategy;
    }

    public void loadText(File file) {
        if (loadStrategy != null) {
            chapters = loadStrategy.load(file);
            setCurrentFile(file); // Ajouter cette ligne
            notifyObservers();
        }
    }
    public void setCurrentFile(File file) {
        this.currentFile = file;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void setCurrentText(String text) {
        this.currentText = text;
        notifyObservers();
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }

    // Getters
    public Map<String, String> getChapters() { return chapters; }
    public String getCurrentText() { return currentText; }
}
