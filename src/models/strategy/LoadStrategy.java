package models.strategy;

import java.io.File;
import java.util.Map;

public interface LoadStrategy {
    Map<String, String> load(File file);
}
