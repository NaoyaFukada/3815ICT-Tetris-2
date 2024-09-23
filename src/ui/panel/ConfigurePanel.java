package ui.panel;

import model.ConfigObserver;
import model.MetaConfig;
import model.PlayerType;
import ui.MainFrame;
import util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Lambda Function Used
 */
public class ConfigurePanel extends AbstractPanel implements ConfigObserver {
    private MetaConfig config;
    private JLabel widthValueLabel;
    private JLabel heightValueLabel;
    private JLabel levelValueLabel;
    private JCheckBox musicCheckBox;
    private JCheckBox soundCheckBox;
    private JCheckBox extendCheckBox;
    private JRadioButton playerOneHuman;
    private JRadioButton playerOneAI;
    private JRadioButton playerOneExternal;
    private JRadioButton playerTwoHuman;
    private JRadioButton playerTwoAI;
    private JRadioButton playerTwoExternal;
    private ButtonGroup playerOneGroup;
    private ButtonGroup playerTwoGroup;

    /**
     * Constructor for ConfigurePanel.
     * Initializes UI components and registers as an observer.
     */
    public ConfigurePanel() {
        super("Configuration");
        config = MetaConfig.getInstance();

        // Initialize the panel's content first
        initContentPanel();

        // Then add as observer to ensure all UI components are initialized
        config.addObserver(this);
    }

    /**
     * Initializes the content pane with UI components.
     */
    @Override
    protected void initContentPanel() {
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = UIUtils.getInstance().createGridBagConstraints();

        // Field Width
        JLabel widthLabel = UIUtils.getInstance().createConfigureLabel("Field Width (No of cells):", null);
        JSlider widthSlider = UIUtils.getInstance().createSlider(5, 15, config.getFieldWidth());
        widthValueLabel = UIUtils.getInstance().createValueLabel(widthSlider.getValue());
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(widthLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(widthSlider, gbc);
        gbc.gridx = 2;
        contentPanel.add(widthValueLabel, gbc);

        widthSlider.addChangeListener(e -> {
            int value = widthSlider.getValue();
            widthValueLabel.setText(String.valueOf(value));
            config.setFieldWidth(value);
        });

        // Field Height
        JLabel heightLabel = UIUtils.getInstance().createConfigureLabel("Field Height (No of cells):", null);
        JSlider heightSlider = UIUtils.getInstance().createSlider(15, 30, config.getFieldHeight());
        heightValueLabel = UIUtils.getInstance().createValueLabel(heightSlider.getValue());
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(heightLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(heightSlider, gbc);
        gbc.gridx = 2;
        contentPanel.add(heightValueLabel, gbc);

        heightSlider.addChangeListener(e -> {
            int value = heightSlider.getValue();
            heightValueLabel.setText(String.valueOf(value));
            config.setFieldHeight(value);
        });

        // Game Level
        JLabel levelLabel = UIUtils.getInstance().createConfigureLabel("Game Level:", null);
        JSlider levelSlider = UIUtils.getInstance().createSlider(1, 10, config.getGameLevel());
        levelValueLabel = UIUtils.getInstance().createValueLabel(levelSlider.getValue());
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPanel.add(levelLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(levelSlider, gbc);
        gbc.gridx = 2;
        contentPanel.add(levelValueLabel, gbc);

        levelSlider.addChangeListener(e -> {
            int value = levelSlider.getValue();
            levelValueLabel.setText(String.valueOf(value));
            config.setGameLevel(value);
        });

        // Music On/Off
        JLabel musicLabel = UIUtils.getInstance().createConfigureLabel("Music (On|Off):", null);
        musicCheckBox = UIUtils.getInstance().createCheckBox(config.isMusicEnabled());
        JLabel musicValueLabel = UIUtils.getInstance().createValueLabel(musicCheckBox.isSelected() ? "On" : "Off");
        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPanel.add(musicLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(musicCheckBox, gbc);
        gbc.gridx = 2;
        contentPanel.add(musicValueLabel, gbc);

        musicCheckBox.addActionListener(e -> {
            boolean isSelected = musicCheckBox.isSelected();
            musicValueLabel.setText(isSelected ? "On" : "Off");
            config.setMusicEnabled(isSelected);
        });

        // Sound Effect On/Off
        JLabel soundLabel = UIUtils.getInstance().createConfigureLabel("Sound Effect (On|Off):", null);
        soundCheckBox = UIUtils.getInstance().createCheckBox(config.isSoundEnabled());
        JLabel soundValueLabel = UIUtils.getInstance().createValueLabel(soundCheckBox.isSelected() ? "On" : "Off");
        gbc.gridx = 0;
        gbc.gridy = 5;
        contentPanel.add(soundLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(soundCheckBox, gbc);
        gbc.gridx = 2;
        contentPanel.add(soundValueLabel, gbc);

        soundCheckBox.addActionListener(e -> {
            boolean isSelected = soundCheckBox.isSelected();
            soundValueLabel.setText(isSelected ? "On" : "Off");
            config.setSoundEnabled(isSelected);
        });

        // Extend Mode On/Off
        JLabel extendLabel = UIUtils.getInstance().createConfigureLabel("Extend Mode (On|Off):", null);
        extendCheckBox = UIUtils.getInstance().createCheckBox(config.isExtendModeEnabled());
        JLabel extendValueLabel = UIUtils.getInstance().createValueLabel(extendCheckBox.isSelected() ? "On" : "Off");
        gbc.gridx = 0;
        gbc.gridy = 6;
        contentPanel.add(extendLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(extendCheckBox, gbc);
        gbc.gridx = 2;
        contentPanel.add(extendValueLabel, gbc);

        extendCheckBox.addActionListener(e -> {
            boolean isSelected = extendCheckBox.isSelected();
            extendValueLabel.setText(isSelected ? "On" : "Off");
            config.setExtendModeEnabled(isSelected);
            togglePlayerTwoOptions(isSelected); // Toggle player two controls
        });

        // Player One Type
        JLabel playerOneLabel = UIUtils.getInstance().createConfigureLabel("Player One Type:", null);
        playerOneHuman = new JRadioButton("Human", config.getPlayerOneType() == PlayerType.HUMAN);
        playerOneAI = new JRadioButton("AI", config.getPlayerOneType() == PlayerType.AI);
        playerOneExternal = new JRadioButton("External", config.getPlayerOneType() == PlayerType.EXTERNAL);
        playerOneGroup = new ButtonGroup();
        playerOneGroup.add(playerOneHuman);
        playerOneGroup.add(playerOneAI);
        playerOneGroup.add(playerOneExternal);

        JPanel playerOnePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        playerOnePanel.add(playerOneHuman);
        playerOnePanel.add(playerOneAI);
        playerOnePanel.add(playerOneExternal);
        gbc.gridx = 0;
        gbc.gridy = 7;
        contentPanel.add(playerOneLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        contentPanel.add(playerOnePanel, gbc);
        gbc.gridwidth = 1;

        // Add ActionListeners to Player One Radio Buttons
        ActionListener playerOneListener = e -> {
            if (playerOneHuman.isSelected()) {
                config.setPlayerOneType(PlayerType.HUMAN);
            } else if (playerOneAI.isSelected()) {
                config.setPlayerOneType(PlayerType.AI);
            } else if (playerOneExternal.isSelected()) {
                config.setPlayerOneType(PlayerType.EXTERNAL);
            }
        };
        playerOneHuman.addActionListener(playerOneListener);
        playerOneAI.addActionListener(playerOneListener);
        playerOneExternal.addActionListener(playerOneListener);

        // Player Two Type
        JLabel playerTwoLabel = UIUtils.getInstance().createConfigureLabel("Player Two Type:", null);
        playerTwoHuman = new JRadioButton("Human", config.getPlayerTwoType() == PlayerType.HUMAN);
        playerTwoAI = new JRadioButton("AI", config.getPlayerTwoType() == PlayerType.AI);
        playerTwoExternal = new JRadioButton("External", config.getPlayerTwoType() == PlayerType.EXTERNAL);
        playerTwoGroup = new ButtonGroup();
        playerTwoGroup.add(playerTwoHuman);
        playerTwoGroup.add(playerTwoAI);
        playerTwoGroup.add(playerTwoExternal);

        JPanel playerTwoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        playerTwoPanel.add(playerTwoHuman);
        playerTwoPanel.add(playerTwoAI);
        playerTwoPanel.add(playerTwoExternal);
        gbc.gridx = 0;
        gbc.gridy = 8;
        contentPanel.add(playerTwoLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        contentPanel.add(playerTwoPanel, gbc);
        gbc.gridwidth = 1;

        // Add ActionListeners to Player Two Radio Buttons
        ActionListener playerTwoListener = e -> {
            if (playerTwoHuman.isSelected()) {
                config.setPlayerTwoType(PlayerType.HUMAN);
            } else if (playerTwoAI.isSelected()) {
                config.setPlayerTwoType(PlayerType.AI);
            } else if (playerTwoExternal.isSelected()) {
                config.setPlayerTwoType(PlayerType.EXTERNAL);
            }
        };
        playerTwoHuman.addActionListener(playerTwoListener);
        playerTwoAI.addActionListener(playerTwoListener);
        playerTwoExternal.addActionListener(playerTwoListener);

        // Initially disable player two options if extend mode is not selected
        togglePlayerTwoOptions(config.isExtendModeEnabled());
    }

    /**
     * Toggles enabling/disabling Player Two options based on Extend Mode.
     */
    private void togglePlayerTwoOptions(boolean isExtendMode) {
        if (playerTwoHuman != null && playerTwoAI != null && playerTwoExternal != null) {
            // setEnabled is part of the JComponent class in the Java Swing library
            playerTwoHuman.setEnabled(isExtendMode);
            playerTwoAI.setEnabled(isExtendMode);
            playerTwoExternal.setEnabled(isExtendMode);
        }
    }

    /**
     * This would be called when subscriber publishes
     */
    @Override
    public void update() {
        if (widthValueLabel != null) widthValueLabel.setText(String.valueOf(config.getFieldWidth()));
        if (heightValueLabel != null) heightValueLabel.setText(String.valueOf(config.getFieldHeight()));
        if (levelValueLabel != null) levelValueLabel.setText(String.valueOf(config.getGameLevel()));
        if (musicCheckBox != null) musicCheckBox.setSelected(config.isMusicEnabled());
        if (soundCheckBox != null) soundCheckBox.setSelected(config.isSoundEnabled());
        if (extendCheckBox != null) {
            extendCheckBox.setSelected(config.isExtendModeEnabled());
            togglePlayerTwoOptions(config.isExtendModeEnabled());
        }

        // Update Player One Selection
        if (playerOneHuman != null && playerOneAI != null && playerOneExternal != null) {
            playerOneHuman.setSelected(config.getPlayerOneType() == PlayerType.HUMAN);
            playerOneAI.setSelected(config.getPlayerOneType() == PlayerType.AI);
            playerOneExternal.setSelected(config.getPlayerOneType() == PlayerType.EXTERNAL);
        }

        // Update Player Two Selection
        if (playerTwoHuman != null && playerTwoAI != null && playerTwoExternal != null) {
            playerTwoHuman.setSelected(config.getPlayerTwoType() == PlayerType.HUMAN);
            playerTwoAI.setSelected(config.getPlayerTwoType() == PlayerType.AI);
            playerTwoExternal.setSelected(config.getPlayerTwoType() == PlayerType.EXTERNAL);
        }
    }

    // Overriding the backFunction defined in AbstractPanel
    @Override
    protected void backFunction() {
        MainFrame.MAIN_FRAME.showScreen("Main");
        config.saveConfig();
        System.out.println("Back button pressed. Configuration saved.");
    }
}