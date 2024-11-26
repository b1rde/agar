package view;

import model.ParametersModel;
import controller.ParametersController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class ParametersView {
    private ParametersModel model;
    private ParametersController controller;
    private JTextField screenWidthField;
    private JTextField screenHeightField;
    private JTextField populationSizeField;
    private JTextField foodGenRateField;
    private JTextField targetFPSField;
    private JTextField dumbMovLinField;
    private JTextField preyDeviationField;
    private JTextField predatorDeviationField;
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
        frame.setLayout(new GridLayout(10, 2));

        screenWidthField = new JTextField(String.valueOf(model.getScreenWidth()));
        screenHeightField = new JTextField(String.valueOf(model.getScreenHeight()));
        populationSizeField = new JTextField(String.valueOf(model.getPopulationSize()));
        foodGenRateField = new JTextField(String.valueOf(model.getFoodGenRate()));
        targetFPSField = new JTextField(String.valueOf(model.getTargetFPS()));
        dumbMovLinField = new JTextField(String.valueOf(model.getDumbMovLin()));
        preyDeviationField = new JTextField(String.valueOf(model.getPreyDeviation()));
        predatorDeviationField = new JTextField(String.valueOf(model.getPredatorDeviation()));

        frame.add(new JLabel("Largeur de l'écran :"));
        frame.add(screenWidthField);
        frame.add(new JLabel("Hauteur de l'écran :"));
        frame.add(screenHeightField);
        frame.add(new JLabel("Taille de la population :"));
        frame.add(populationSizeField);
        frame.add(new JLabel("Taux de génération de la nourriture :"));
        frame.add(foodGenRateField);
        frame.add(new JLabel("FPS souhaités :"));
        frame.add(targetFPSField);
        frame.add(new JLabel("Linéarité des mouvements de Dumb :"));
        frame.add(dumbMovLinField);
        frame.add(new JLabel("Déviation d'itinéraire de Proie :"));
        frame.add(preyDeviationField);
        frame.add(new JLabel("Déviation d'itinéraire de Prédateur :"));
        frame.add(predatorDeviationField);

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

    public JTextField getPopulationSizeField() {
        return populationSizeField;
    }

    public JTextField getFoodGenRateField() {
        return foodGenRateField;
    }

    public JTextField getTargetFPSField() {
        return targetFPSField;
    }

    public JTextField getDumbMovLinField() {
        return dumbMovLinField;
    }

    public JTextField getPreyDeviationField() {
        return preyDeviationField;
    }

    public JTextField getPredatorDeviationField() {
        return predatorDeviationField;
    }
}
