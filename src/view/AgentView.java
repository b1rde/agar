package view;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AgentView extends JPanel {
    private List<GlobalAgent> allAgents;
    private List<DumbAgent> allDumbs = new CopyOnWriteArrayList<>();
    private List<PredatorAgent> allPredators = new CopyOnWriteArrayList<>();
    private List<PreyAgent> allPreys = new CopyOnWriteArrayList<>();
    private List<PathfindingAgent> allPathfindings = new CopyOnWriteArrayList<>();
    private List<GlobalAgent> agentsToRemove = new CopyOnWriteArrayList<>();

    private double checkSum = 0;

    int mapSizeX = ParametersModel.getScreenWidth();
    int mapSizeY = ParametersModel.getScreenHeight();

    public AgentView() {
        allAgents = new CopyOnWriteArrayList<>();
        initializeAgents();
    }

    private void initializeAgents() {
        int numAgents = ParametersModel.getPopulationSize(); // Nombre d'agents de chaque type

        for (int i = 0; i < numAgents; i++) {
            double dumbX = ParametersModel.randomDouble() * mapSizeX;
            double dumbY = ParametersModel.randomDouble() * mapSizeY;

            double predatorX = ParametersModel.randomDouble() * mapSizeX;
            double predatorY = ParametersModel.randomDouble() * mapSizeY;

            double preyX = ParametersModel.randomDouble() * mapSizeX;
            double preyY = ParametersModel.randomDouble() * mapSizeY;

            double pathfindingX = ParametersModel.randomDouble() * mapSizeX;
            double pathfindingY = ParametersModel.randomDouble() * mapSizeY;

            double check = ParametersModel.randomDouble();
            checkSum += check;

            double size = 20; // Taille minimale = 5 car taille de la nourriture = 4

            DumbAgent dumbAgent = new DumbAgent(dumbX, dumbY, size, dumbX, dumbY);
            allAgents.add(dumbAgent);
            allDumbs.add(dumbAgent);

            PredatorAgent predatorAgent = new PredatorAgent(predatorX, predatorY, size, allAgents);
            allAgents.add(predatorAgent);
            allPredators.add(predatorAgent);

            PreyAgent preyAgent = new PreyAgent(preyX, preyY, size, preyX, preyY, allAgents);
            allAgents.add(preyAgent);
            allPreys.add(preyAgent);

            PathfindingAgent pathfindingAgent = new PathfindingAgent(pathfindingX, pathfindingY, size, allAgents);
            allAgents.add(pathfindingAgent);
            allPathfindings.add(pathfindingAgent);
        }
        System.out.println(checkSum);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        for (GlobalAgent agent : allAgents) {
            int x = (int) (agent.getPosX() - agent.getSize() / 2);
            int y = (int) (agent.getPosY() - agent.getSize() / 2);
            graphics.setColor(agent.getColor());
            graphics.fillOval(x, y, (int) agent.getSize(), (int) agent.getSize());
        }
    }

    public void updateAgents() {
        // Déplace les agents
        System.out.println("Random Double: " + ParametersModel.randomDouble());

        // Ensure deterministic order of updates
        for (DumbAgent agent : allDumbs) {
            agent.move();
        }

        for (PredatorAgent agent : allPredators) {
            agent.move();
        }

        for (PreyAgent agent : allPreys) {
            agent.move();
        }

        for (PathfindingAgent agent : allPathfindings) {
            agent.move();
        }

        // Vérifie et traite les collisions entre tous les agents
        for (GlobalAgent agent : allAgents) {
            for (GlobalAgent otherAgent : allAgents) {
                if (agent != otherAgent && agent.collidesWith(otherAgent)) {
                    agent.setSize(agent.getSize() + otherAgent.getSize() / 4);
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
            } else if (agent instanceof PathfindingAgent) {
                allPathfindings.remove(agent);
            }
        }

        // Génère la nourriture
        for (int i = 0; i < ParametersModel.getFoodGenRate(); i++) {
            if (ParametersModel.randomDouble() < 0.1) {
                double foodX = ParametersModel.randomDouble() * mapSizeX;
                double foodY = ParametersModel.randomDouble() * mapSizeY;
                Food food = new Food(foodX, foodY, 4);
                allAgents.add(food);
            }
        }

        repaint();
    }
}
