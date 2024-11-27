package controller;

import model.ParametersModel;
import view.ParametersView;

public class ParametersController {
    private ParametersModel model;
    private ParametersView view;

    public ParametersController(ParametersModel model, ParametersView view) {
        this.model = model;
        this.view = view;
    }

    public void resetToDefaultValues() {
        view.getScreenWidthField().setText(String.valueOf(model.getScreenWidth()));
        view.getScreenHeightField().setText(String.valueOf(model.getScreenHeight()));
        view.getPopulationSizeField().setText(String.valueOf(model.getPopulationSize()));
        view.getFoodGenRateField().setText(String.valueOf(model.getFoodGenRate()));
        view.getTargetFPSField().setText(String.valueOf(model.getTargetFPS()));
        view.getDumbMovLinField().setText(String.valueOf(model.getDumbMovLin()));
        view.getPreyDeviationField().setText(String.valueOf(model.getPreyDeviation()));
        view.getPredatorDeviationField().setText(String.valueOf(model.getPredatorDeviation()));
        view.getSeedField().setText(String.valueOf(model.getSeed()));
    }

    // Valeurs des presets
    public void uploadValues(int n) {
        switch (n) {
            // Preset standard
            case 1:
                view.screenWidthField.setText("1920");
                view.screenHeightField.setText("1080");
                view.populationSizeField.setText("300");
                view.foodGenRateField.setText("10");
                view.targetFPSField.setText("120");
                view.dumbMovLinField.setText("50.0");
                view.preyDeviationField.setText("1.2");
                view.predatorDeviationField.setText("1.2");
                view.seedField.setText("1");
                break;
            // Haute génération de nourriture
            case 2:
                view.screenWidthField.setText("1920");
                view.screenHeightField.setText("1080");
                view.populationSizeField.setText("300");
                view.foodGenRateField.setText("100");
                view.targetFPSField.setText("120");
                view.dumbMovLinField.setText("50.0");
                view.preyDeviationField.setText("1.2");
                view.predatorDeviationField.setText("1.2");
                view.seedField.setText("1");
                break;
            // Haute déviation des proies
            case 3:
                view.screenWidthField.setText("1920");
                view.screenHeightField.setText("1080");
                view.populationSizeField.setText("300");
                view.foodGenRateField.setText("10");
                view.targetFPSField.setText("120");
                view.dumbMovLinField.setText("50.0");
                view.preyDeviationField.setText("1.9");
                view.predatorDeviationField.setText("1.2");
                view.seedField.setText("1");
                break;
        }
    }

    public void updateModelValues() {
        model.setScreenWidth(Integer.parseInt(view.getScreenWidthField().getText()));
        model.setScreenHeight(Integer.parseInt(view.getScreenHeightField().getText()));
        model.setPopulationSize(Integer.parseInt(view.getPopulationSizeField().getText()));
        model.setFoodGenRate(Integer.parseInt(view.getFoodGenRateField().getText()));
        model.setTargetFPS(Integer.parseInt(view.getTargetFPSField().getText()));
        model.setDumbMovLin(Double.parseDouble(view.getDumbMovLinField().getText()));
        model.setPreyDeviation(Double.parseDouble(view.getPreyDeviationField().getText()));
        model.setPredatorDeviation(Double.parseDouble(view.getPredatorDeviationField().getText()));
        model.setSeed(Long.parseLong(view.getSeedField().getText()));
    }
}
