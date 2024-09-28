package ui.panel;

import ui.MainFrame;
import ui.UIGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AbstractPanel extends JPanel {
    private final String title;
    protected JPanel contentPanel;
    private JPanel titlePanel;
    private JButton backButton;

    public AbstractPanel(String title) {
        this.title = title;
        initBack();
        initPanel();
    }

    protected void initPanel() {
        setLayout(new BorderLayout());

        // Title Pane with Title Label
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(UIGenerator.createTitleLabel(title), BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        // Main content pane for child class content
        contentPanel = new JPanel();
        add(contentPanel, BorderLayout.CENTER);

        // Add the back button at the bottom
        add(backButton, BorderLayout.SOUTH);
    }

    // Set up the Back button
    private void initBack() {
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backFunction();
            }
        });
    }

    // Handle the back button action
    protected void backFunction() {
        MainFrame.MAIN_FRAME.showScreen("Main"); // Return to Main Panel
    }

    // Abstract method to be implemented by subclasses to initialize their specific content
    protected abstract void initContentPanel();
}
