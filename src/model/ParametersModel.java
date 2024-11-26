package model;

public class ParametersModel {
    public static int screenWidth = 1920;
    public static int screenHeight = 1080;
    public static int populationSize = 300;
    public static int foodGenRate = 10;
    public static int targetFPS = 240;
    public static double dumbMovLin = 50;
    public static double preyDeviation = 1.2;
    public static double predatorDeviation = 1.2;

    // seed
    public static int getScreenWidth(){
        return screenWidth;
    }

    public static void setScreenWidth(int screenWidth){
        ParametersModel.screenWidth = screenWidth;
    }

    public static int getScreenHeight(){
        return screenHeight;
    }

    public static void setScreenHeight(int screenHeight){
        ParametersModel.screenHeight = screenHeight;
    }

    public static int getPopulationSize(){
        return populationSize;
    }

    public static void setPopulationSize(int populationSize){
        ParametersModel.populationSize = populationSize;
    }

    public static int getFoodGenRate(){
        return foodGenRate;
    }

    public static void setFoodGenRate(int foodGenRate){
        ParametersModel.foodGenRate = foodGenRate;
    }

    public static int getTargetFPS(){
        return targetFPS;
    }

    public static void setTargetFPS(int targetFPS){
        ParametersModel.targetFPS = targetFPS;
    }

    public static double getDumbMovLin(){
        return dumbMovLin;
    }

    public static void setDumbMovLin(double dumbMovLin){
        ParametersModel.dumbMovLin = dumbMovLin;
    }

    public static double getPreyDeviation(){
        return preyDeviation;
    }

    public static void setPreyDeviation(double preyDeviation){
        ParametersModel.preyDeviation = preyDeviation;
    }

    public static double getPredatorDeviation(){
        return predatorDeviation;
    }

    public static void setPredatorDeviation(double predatorDeviation){
        ParametersModel.predatorDeviation = predatorDeviation;
    }
}
