package view;

import model.ParametersModel;
import controller.ParametersController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class ParametersView {
    // __CAMILLE__
    private ParametersModel model;
    private ParametersController controller;
    public JTextField screenWidthField;
    public JTextField screenHeightField;
    public JTextField targetFPSField;
    public JTextField populationSizeField;
    public JTextField initialSizeField;
    public JTextField initialFoodSizeField; // New field
    public JTextField foodGenRateField;
    public JTextField dumbMovLinField;
    public JTextField preyDeviationField;
    public JTextField seedField;
    private CountDownLatch latch;

    public ParametersView(ParametersModel model, CountDownLatch latch) {
        this.model = model;
        this.latch = latch;
        this.controller = new ParametersController(model, this);
    }

    public void displayParameters() {
        JFrame frame = new JFrame("Agar Parameters");
        Dimension frameSize = new Dimension(500, 400);
        frame.setSize(frameSize);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(12, 2));

        screenWidthField = new JTextField(String.valueOf(model.getScreenWidth()));
        screenHeightField = new JTextField(String.valueOf(model.getScreenHeight()));
        targetFPSField = new JTextField(String.valueOf(model.getTargetFPS()));
        populationSizeField = new JTextField(String.valueOf(model.getPopulationSize()));
        initialSizeField = new JTextField(String.valueOf(model.getInitialSize()));
        initialFoodSizeField = new JTextField(String.valueOf(model.getInitialFoodSize()));
        foodGenRateField = new JTextField(String.valueOf(model.getFoodGenRate()));
        dumbMovLinField = new JTextField(String.valueOf(model.getDumbMovLin()));
        preyDeviationField = new JTextField(String.valueOf(model.getPreyDeviation()));
        seedField = new JTextField(String.valueOf(model.getSeed()));

        frame.add(new JLabel("Largeur de l'écran :"));
        frame.add(screenWidthField);
        frame.add(new JLabel("Hauteur de l'écran :"));
        frame.add(screenHeightField);
        frame.add(new JLabel("FPS souhaités :"));
        frame.add(targetFPSField);
        frame.add(new JLabel("Taille de la population :"));
        frame.add(populationSizeField);
        frame.add(new JLabel("Taille initiale des agents :"));
        frame.add(initialSizeField);
        frame.add(new JLabel("Taille initiale de la nourriture :"));
        frame.add(initialFoodSizeField);
        frame.add(new JLabel("Taux de génération de la nourriture :"));
        frame.add(foodGenRateField);
        frame.add(new JLabel("Linéarité des mouvements de Dumb :"));
        frame.add(dumbMovLinField);
        frame.add(new JLabel("Déviation d'itinéraire de Proie :"));
        frame.add(preyDeviationField);
        frame.add(new JLabel("Seed :"));
        frame.add(seedField);

        // Menu des presets
        String[] options = {"Standard", "Haute génération de nourriture", "Haute déviation de proies"};
        JComboBox<String> dropdown = new JComboBox<>(options);
        dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) dropdown.getSelectedItem();
                int optionNumber = 1;
                switch (selectedOption) {
                    case "Valeurs par défaut":
                        optionNumber = 1;
                        break;
                    case "Haute génération de nourriture":
                        optionNumber = 2;
                        break;
                    case "Haute déviation de proies":
                        optionNumber = 3;
                        break;
                }
                controller.uploadValues(optionNumber);
            }
        });

        frame.add(new JLabel("Preset :"));
        frame.add(dropdown);

        JButton resetButton = new JButton("Valeurs par défaut");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.resetToDefaultValues();
            }
        });

        JButton launchButton = new JButton("Lancer");
        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.updateModelValues();
                latch.countDown();
                frame.dispose();
            }
        });

        frame.add(resetButton);
        frame.add(launchButton);

        frame.setVisible(true);
    }

    public JTextField getScreenWidthField() {
        return screenWidthField;
    }

    public JTextField getScreenHeightField() {
        return screenHeightField;
    }

    public JTextField getTargetFPSField() {
        return targetFPSField;
    }

    public JTextField getPopulationSizeField() {
        return populationSizeField;
    }

    public JTextField getInitialSizeField() {
        return initialSizeField;
    }

    public JTextField getInitialFoodSizeField() {
        return initialFoodSizeField;
    }

    public JTextField getFoodGenRateField() {
        return foodGenRateField;
    }

    public JTextField getDumbMovLinField() {
        return dumbMovLinField;
    }

    public JTextField getPreyDeviationField() {
        return preyDeviationField;
    }

    public JTextField getSeedField() {
        return seedField;
    }
}
