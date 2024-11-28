package controller;

import model.ParametersModel;
import view.ParametersView;

public class ParametersController {
    // __CAMILLE + ESTEBAN__
    private ParametersModel model;
    private ParametersView view;

    public ParametersController(ParametersModel model, ParametersView view) {
        this.model = model;
        this.view = view;
    }

    public void resetToDefaultValues() {
        // __CAMILLE__
        view.getScreenWidthField().setText(String.valueOf(model.getScreenWidth()));
        view.getScreenHeightField().setText(String.valueOf(model.getScreenHeight()));
        view.getTargetFPSField().setText(String.valueOf(model.getTargetFPS()));
        view.getPopulationSizeField().setText(String.valueOf(model.getPopulationSize()));
        view.getInitialSizeField().setText(String.valueOf(model.getInitialSize()));
        view.getInitialFoodSizeField().setText(String.valueOf(model.getInitialFoodSize()));
        view.getFoodGenRateField().setText(String.valueOf(model.getFoodGenRate()));
        view.getDumbMovLinField().setText(String.valueOf(model.getDumbMovLin()));
        view.getPreyDeviationField().setText(String.valueOf(model.getPreyDeviation()));
        view.getSeedField().setText(String.valueOf(model.getSeed()));
    }

    // PRESETS
    public void uploadValues(int n) {
        // __CAMILLE + ESTEBAN__
        switch (n) {
            // Vlauers par défaut
            case 1:
                view.screenWidthField.setText("1920");
                view.screenHeightField.setText("1080");
                view.targetFPSField.setText("120");
                view.populationSizeField.setText("300");
                view.initialSizeField.setText("5");
                view.initialFoodSizeField.setText("4");
                view.foodGenRateField.setText("10");
                view.dumbMovLinField.setText("50.0");
                view.preyDeviationField.setText("1.2");
                view.seedField.setText("1");
                break;
            // Haute génération de nourriture
            case 2:
                view.screenWidthField.setText("1920");
                view.screenHeightField.setText("1080");
                view.targetFPSField.setText("120");
                view.populationSizeField.setText("300");
                view.initialSizeField.setText("5");
                view.initialFoodSizeField.setText("4");
                view.foodGenRateField.setText("100");
                view.dumbMovLinField.setText("50.0");
                view.preyDeviationField.setText("1.2");
                view.seedField.setText("1");
                break;
            // Haute déviation des proies
            case 3:
                view.screenWidthField.setText("1920");
                view.screenHeightField.setText("1080");
                view.targetFPSField.setText("120");
                view.populationSizeField.setText("300");
                view.initialSizeField.setText("5");
                view.initialFoodSizeField.setText("4");
                view.foodGenRateField.setText("10");
                view.dumbMovLinField.setText("50.0");
                view.preyDeviationField.setText("1.9");
                view.seedField.setText("1");
                break;

                // etc.
        }
    }

    public void updateModelValues() {
        // __CAMILLE__
        model.setScreenWidth(Integer.parseInt(view.getScreenWidthField().getText()));
        model.setScreenHeight(Integer.parseInt(view.getScreenHeightField().getText()));
        model.setTargetFPS(Integer.parseInt(view.getTargetFPSField().getText()));
        model.setPopulationSize(Integer.parseInt(view.getPopulationSizeField().getText()));
        model.setInitialSize(Integer.parseInt(view.getInitialSizeField().getText()));
        model.setInitialFoodSize(Integer.parseInt(view.getInitialFoodSizeField().getText())); // New line
        model.setFoodGenRate(Integer.parseInt(view.getFoodGenRateField().getText()));
        model.setDumbMovLin(Double.parseDouble(view.getDumbMovLinField().getText()));
        model.setPreyDeviation(Double.parseDouble(view.getPreyDeviationField().getText()));
        model.setSeed(Long.parseLong(view.getSeedField().getText()));
    }
}
