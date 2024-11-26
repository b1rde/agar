package view;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AgentView extends JPanel {
    private List<GlobalAgent> allAgents;
    private List<DumbAgent> allDumbs = new CopyOnWriteArrayList<>();
    private List<PredatorAgent> allPredators = new CopyOnWriteArrayList<>();
    private List<PreyAgent> allPreys = new CopyOnWriteArrayList<>();
    private List<PathfindingAgent> allPathfindings = new CopyOnWriteArrayList<>();
    private List<GlobalAgent> agentsToRemove = new CopyOnWriteArrayList<>();

    int mapSizeX = ParametersModel.getScreenWidth();
    int mapSizeY = ParametersModel.getScreenHeight();

    public AgentView() {
        allAgents = new CopyOnWriteArrayList<>();
        initializeAgents();
    }

    private void initializeAgents() {

        int numAgents = ParametersModel.getPopulationSize(); // Nombre d'agents de chaque type

        for (int i = 0; i < numAgents; i++) {
            double dumbX = Math.random() * mapSizeX;
            double dumbY = Math.random() * mapSizeY;

            double predatorX = Math.random() * mapSizeX;
            double predatorY = Math.random() * mapSizeY;

            double preyX = Math.random() * mapSizeX;
            double preyY = Math.random() * mapSizeY;

            double pathfindingX = Math.random() * mapSizeX;
            double pathfindingY = Math.random() * mapSizeY;

            double size = 5; // Taille minimale = 5 car taille de la nourriture = 4

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
        Thread dumbThread = new Thread() {
            public void run() {
                if (!allDumbs.isEmpty()) {
                    for (DumbAgent agent : allDumbs) {
                        agent.move();
                    }
                }
            }
        };
        dumbThread.start();

        Thread predatorThread = new Thread() {
            public void run() {
                if (!allPredators.isEmpty()) {
                    for(PredatorAgent agent :allPredators) {
                        agent.move();
                    }
                }
            }
        };
        predatorThread.start();

        Thread preyThread = new Thread() {
            public void run() {
                if (!allPreys.isEmpty()) {
                    for (PreyAgent agent : allPreys) {
                        agent.move();
                    }
                }
            }
        };
        preyThread.start();

        Thread pathfindingThread = new Thread() {
            public void run() {
                if (!allPathfindings.isEmpty()) {
                    for (PathfindingAgent agent : allPathfindings) {
                        agent.move();
                    }
                }
            }
        };
        pathfindingThread.start();

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
            }
            else if (agent instanceof PathfindingAgent) {
                allPathfindings.remove(agent);
            }
        }

        // Génère la nourriture
        for (int i = 0; i < ParametersModel.getFoodGenRate(); i++) {
            if (Math.random() < 0.1) {
                double foodX = Math.random() * mapSizeX;
                double foodY = Math.random() * mapSizeY;
                Food food = new Food(foodX, foodY, 4);
                allAgents.add(food);
            }
        }

        repaint();
    }
}
