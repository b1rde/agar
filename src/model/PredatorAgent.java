package model;

import java.awt.*;
import java.util.List;

public class PredatorAgent extends GlobalAgent {
    // __ESTEBAN__
    private List<GlobalAgent> allAgents;
    private int mapSizeX = ParametersModel.getScreenWidth();
    private int mapSizeY = ParametersModel.getScreenHeight();

    public PredatorAgent(double posX, double posY, double size, List<GlobalAgent> allAgents) {
        super(posX, posY, size);
        this.color = Color.RED;
        this.allAgents = allAgents;
    }

    @Override
    public void move() {
        double speed = Math.max(1.0, 10.0 / Math.log(size + 2));

        GlobalAgent closestPrey = null;

        double minDistance = Double.MAX_VALUE;

        // Trouver la proie la plus proche
        for (GlobalAgent agent : allAgents) {
            // Vérifie si l'agent est de type Prey ou Dumb
            if (agent instanceof PreyAgent || agent instanceof DumbAgent) {
                double distance = distanceWith(agent);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPrey = agent;
                }
            }
        }

        // Déplacer vers l'agent cible le plus proche s'il existe
        if (closestPrey != null) {
            double directionX = closestPrey.getPosX() - this.getPosX();
            double directionY = closestPrey.getPosY() - this.getPosY();
            double length = Math.sqrt(directionX * directionX + directionY * directionY);

            // Normaliser la direction
            directionX /= length;
            directionY /= length;

            // Mettre à jour la position
            this.setPosition(this.getPosX() + directionX * speed, this.getPosY() + directionY * speed);
        } else {
            // Mouvement aléatoire si aucune proie n'est trouvée
            double randomAngle = ParametersModel.randomDouble() * 2 * Math.PI;

            this.setPosition(this.getPosX() + Math.cos(randomAngle) * speed, this.getPosY() + Math.sin(randomAngle) * speed);
        }

        // Vérifie que l'agent ne quitte pas la map (__CAMILLE__)
        targetX = Math.max(0, Math.min(mapSizeX, targetX));
        targetY = Math.max(0, Math.min(mapSizeY, targetY));
    }
}