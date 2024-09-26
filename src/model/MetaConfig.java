package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class to manage application configurations.
 * Implements the Observer pattern to notify registered observers of changes.
 */
public class MetaConfig {
    private static final String CONFIG_FILE_PATH = "src/resources/data/config.json"; // Path to the configuration file
    private static MetaConfig instance; // Singleton instance

    // Transient to prevent Gson from serializing the observers list
    private transient List<ConfigObserver> observers = new ArrayList<>();

    // Configuration settings
    private int fieldWidth;
    private int fieldHeight;
    private int gameLevel;
    private boolean musicEnabled;
    private boolean soundEnabled;
    private boolean extendModeEnabled;

    // Player configurations
    private PlayerType playerOneType;
    private PlayerType playerTwoType;

    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes default configuration values.
     */
    private MetaConfig() {
        // Initialize with default values
        this.fieldWidth = 10;
        this.fieldHeight = 20;
        this.gameLevel = 1;
        this.musicEnabled = true;
        this.soundEnabled = true;
        this.extendModeEnabled = false;

        // Initialize player configurations with default types
        this.playerOneType = PlayerType.HUMAN;
        this.playerTwoType = PlayerType.HUMAN;
    }

    /**
     * Retrieves the Singleton instance of MetaConfig.
     * Loads from the configuration file if available; otherwise, uses default settings.
     *
     * @return The Singleton instance of MetaConfig.
     */
    public static synchronized MetaConfig getInstance() {
        if (instance == null) {
            instance = loadConfigFile();
            if (instance == null) {
                instance = new MetaConfig();
                System.out.println("Using default configuration.");
                instance.saveConfig(); // Optionally create the config file with default values
            }
        }
        return instance;
    }

    /**
     * Registers an observer to receive configuration updates.
     *
     * @param observer The observer to register.
     */
    public void addObserver(ConfigObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Unregisters an observer from receiving configuration updates.
     *
     * @param observer The observer to unregister.
     */
    public void removeObserver(ConfigObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers of a configuration change.
     */
    private void notifyObservers() {
        for (ConfigObserver observer : observers) {
            observer.update();
        }
    }

    /**
     * Loads the configuration from the JSON file.
     *
     * @return An instance of MetaConfig if loading is successful; otherwise, null.
     */
    private static MetaConfig loadConfigFile() {
        File configFile = new File(CONFIG_FILE_PATH);
        if (!configFile.exists()) {
            System.out.println("Config file does not exist: " + CONFIG_FILE_PATH);
            return null; // Will trigger creation of default config
        }

        try (FileReader reader = new FileReader(configFile)) {
            Gson gson = new Gson();
            MetaConfig config = gson.fromJson(reader, MetaConfig.class);
            if (config == null) {
                System.out.println("Config file is empty or malformed.");
            }
            return config;
        } catch (IOException e) {
            System.out.println("Error reading config file: " + e.getMessage());
            return null; // Will trigger creation of default config
        } catch (JsonSyntaxException e) {
            System.out.println("Error parsing JSON config file: " + e.getMessage());
            return null; // Will trigger creation of default config
        }
    }

    /**
     * Saves the current configuration to the JSON file.
     */
    public void saveConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(this);  // Convert the current instance to JSON
        File configFile = new File(CONFIG_FILE_PATH);
        File parentDir = configFile.getParentFile();
        if (parentDir != null) {
            boolean dirsCreated = parentDir.mkdirs();
            if (dirsCreated) {
                System.out.println("Created configuration directory: " + parentDir.getAbsolutePath());
            }
        }
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(jsonString);
            writer.flush();
            System.out.println("Configuration saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }

    // Getters and setters for the configuration properties

    public int getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
        notifyObservers(); // Notify observers when value changes
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
        notifyObservers();
    }

    public int getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(int gameLevel) {
        this.gameLevel = gameLevel;
        notifyObservers();
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    public void setMusicEnabled(boolean musicEnabled) {
        this.musicEnabled = musicEnabled;
        notifyObservers();
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
        notifyObservers();
    }

    public boolean isExtendModeEnabled() {
        return extendModeEnabled;
    }

    public void setExtendModeEnabled(boolean extendModeEnabled) {
        this.extendModeEnabled = extendModeEnabled;
        notifyObservers();
    }

    // Getters and setters for player configurations

    public PlayerType getPlayerOneType() {
        return playerOneType;
    }

    public void setPlayerOneType(PlayerType playerOneType) {
        if (playerOneType != null && this.playerOneType != playerOneType) {
            this.playerOneType = playerOneType;
            notifyObservers();
        }
    }

    public PlayerType getPlayerTwoType() {
        return playerTwoType;
    }

    public void setPlayerTwoType(PlayerType playerTwoType) {
        if (playerTwoType != null && this.playerTwoType != playerTwoType) {
            this.playerTwoType = playerTwoType;
            notifyObservers();
        }
    }


}