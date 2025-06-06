# Documentation Corpus Viewer - Anthony LELCHAT

## Compilation et Execution

### Prérequis :
- Avoir téléchargé la bonne version de l'openJDK de JavaFX disponible sur Gluon et correspondant à votre version de Java.
- Avoir déplacé ce fichier dans le dossier openjdk.
- Se placer dans le dossier ProjetJava2

### Compilation
`javac -d bin --module-path ./openjdk/<CHEMIN_OPENJDK> --add-modules javafx.controls,javafx.fxml $(find src -name "*.java")`

### Execution 

`java -cp bin --module-path ./openjdk/<CHEMIN_OPENJDK> --add-modules javafx.controls,javafx.fxml App`

## Modélisation

### Pattern
- MVC : Avoir une architecture pour le projet et séparer les responsabilités (La vue pour ce qui concerne l'interface utilisateur, le modèle pour stocker les donnée et le controlleur pour gérer les interactions)

- Strategy : Encapsuler des algorithmes de chargement différents et permettre au selecteur de chapitre de switcher dynamiquement entre ces stratégies.

- Observer : Permettre de changer dynamiquement certains labels (la distance de Levensthein, les mots en communs) lorsqu'un changement est observé dans les panneaux (on enverra une notification).

L'application est affiché par le fichier `App.java` qui utilise les autres fichiers et les designs patterns.

## Fonctionnement de l'application

Voici tous ce que le l'application peut faire (n'hésitez pas à la tester).

- Au départ de l'application, aucun texte n'est chargé, le nombre de mot en commun et la distance de levensthein sont à 0, il n'est pas possible de modifier les panneaux, il n'est pas possible de sauvegarder de fichier (rien n'a été chargé.).
- Charger un fichier (en cliquant sur `Charger`) dans un panneau doit faire apparaitre du texte. Lorsque cela est fait, la distance de levensthein change (si le fichier n'est pas vide).
- Charger un fichier (en cliquant sur `Charger`) dans l'autre panneau doit faire apparaitre du texte. Lorsque cela est fait, le nombre de mots en commun change (si il y a des mots en commun)
- Cliquez sur `Modifier` permet à l'utilisateur de modifier le panneau concerné. Il suffira de cliquer sur `Enregistrer` pour que le nombre de mots en communs et la distance de levensthein soient actualisés (le fichier ne sera plus modifiable à moins de recliquer sur `Modifier`).
- Cliquez sur `Sauvegarder` permet à l'utilisateur de sauvegarder son fichier modifié. Le nom du fichier est changé automatiquement pour ne pas écraser le fichier initial, mais l'utilisateur peut modifier le nom du fichier à sa guise.
- L'utilisateur peut sélectionner le chapitre qu'il souhaite. Par défaut, le chapitre I est affiché.
- L'utilisateur peut obtenir de l'aide en allant dans `Aide` et en cliquant sur `Besoin d'aide ?`
- L'utilisateur peut afficher les mots en communs en cliquant sur `Outils` puis `Afficher les mots communs`. Il peut aussi décider d'enlever la distance de levensthein et les mots en communs en cliquant sur `Afficher les statistiques` (coché par défaut)

