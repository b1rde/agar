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
    }
}
