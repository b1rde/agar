package view;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AgentView extends JPanel {
    // __CAMILLE + ESTEBAN__
    private List<GlobalAgent> allAgents;
    private List<DumbAgent> allDumbs = new CopyOnWriteArrayList<>();
    private List<PredatorAgent> allPredators = new CopyOnWriteArrayList<>();
    private List<PreyAgent> allPreys = new CopyOnWriteArrayList<>();
    private List<GlobalAgent> agentsToRemove = new CopyOnWriteArrayList<>();

    private double checkSum = 0;

    int mapSizeX = ParametersModel.getScreenWidth();
    int mapSizeY = ParametersModel.getScreenHeight();

    public AgentView() {
        allAgents = new CopyOnWriteArrayList<>();
        initializeAgents();
    }

    private void initializeAgents() {
        // __CAMILLE__
        int numAgents = ParametersModel.getPopulationSize(); // Nombre d'agents de chaque type
        double globalSize = ParametersModel.getInitialSize(); // Taille initiale des agents
        System.out.println(globalSize);

        for (int i = 0; i < numAgents; i++) {
            double dumbX = ParametersModel.randomDouble() * mapSizeX;
            double dumbY = ParametersModel.randomDouble() * mapSizeY;

            double predatorX = ParametersModel.randomDouble() * mapSizeX;
            double predatorY = ParametersModel.randomDouble() * mapSizeY;

            double preyX = ParametersModel.randomDouble() * mapSizeX;
            double preyY = ParametersModel.randomDouble() * mapSizeY;

            double check = ParametersModel.randomDouble();
            checkSum += check;

            DumbAgent dumbAgent = new DumbAgent(dumbX, dumbY, globalSize, dumbX, dumbY);
            allAgents.add(dumbAgent);
            allDumbs.add(dumbAgent);

            PredatorAgent predatorAgent = new PredatorAgent(predatorX, predatorY, globalSize, allAgents);
            allAgents.add(predatorAgent);
            allPredators.add(predatorAgent);

            PreyAgent preyAgent = new PreyAgent(preyX, preyY, globalSize, preyX, preyY, allAgents);
            allAgents.add(preyAgent);
            allPreys.add(preyAgent);
        }
        System.out.println(checkSum);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        // __ESTEBAN__
        super.paintComponent(graphics);
        for (GlobalAgent agent : allAgents) {
            int x = (int) (agent.getPosX() - agent.getSize() / 2);
            int y = (int) (agent.getPosY() - agent.getSize() / 2);
            graphics.setColor(agent.getColor());
            graphics.fillOval(x, y, (int) agent.getSize(), (int) agent.getSize());
        }
    }

    public void updateAgents() {
        // __CAMILLE__
        System.out.println("Random Double: " + ParametersModel.randomDouble());

        for (DumbAgent agent : allDumbs) {
            agent.move();
        }

        for (PredatorAgent agent : allPredators) {
            agent.move();
        }

        for (PreyAgent agent : allPreys) {
            agent.move();
        }

        // Vérifie et traite les collisions entre tous les agents
        for (GlobalAgent agent : allAgents) {
            for (GlobalAgent otherAgent : allAgents) {
                if (agent != otherAgent && agent.collidesWith(otherAgent)) {
                    agent.setSize(agent.getSize() + otherAgent.getSize() / ParametersModel.getInitialFoodSize());
                    agentsToRemove.add(otherAgent);
                }
            }
        }

        // Supprime les agents qui ont été capturés de toutes les listes
        for (GlobalAgent agent : agentsToRemove) {
            allAgents.remove(agent);
            if (agent instanceof DumbAgent) {
                allDumbs.remove(agent);
            } else if (agent instanceof PredatorAgent) {
                allPredators.remove(agent);
            } else if (agent instanceof PreyAgent) {
                allPreys.remove(agent);
            }
        }

        // Génère la nourriture
        for (int i = 0; i < ParametersModel.getFoodGenRate(); i++) {
            if (ParametersModel.randomDouble() < 0.1) {
                double foodX = ParametersModel.randomDouble() * mapSizeX;
                double foodY = ParametersModel.randomDouble() * mapSizeY;
                Food food = new Food(foodX, foodY, ParametersModel.getInitialFoodSize());
                allAgents.add(food);
            }
        }

        repaint();
    }

    public int getWinner() {
        // __CAMILLE__
        int winner = 0; // 0 = pas de winner, 1 = dumb, 2 = predator, 3 = prey

        if (allPredators.isEmpty() && allPreys.isEmpty() && !allDumbs.isEmpty()) {
            winner = 1;
        } else if (allPredators.isEmpty() && !allPreys.isEmpty() && allDumbs.isEmpty()) {
            winner = 3;
        } else if (!allPredators.isEmpty() && allPreys.isEmpty() && allDumbs.isEmpty()) {
            winner = 2;
        }

        return winner;
    }
}
