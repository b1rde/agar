package model;

import java.awt.*;
import java.util.List;

public class PreyAgent extends GlobalAgent {
    private List<GlobalAgent> allAgents;

    private int mapSizeX = ParametersModel.getScreenWidth();
    private int mapSizeY = ParametersModel.getScreenHeight();

    public PreyAgent(double posX, double posY, double size, double targetX, double targetY, List<GlobalAgent> allAgents) {
        super(posX, posY, size);
        this.color = Color.BLUE;
        this.allAgents = allAgents;
    }

    @Override
    public void move() {
        if (allAgents == null) {
            System.out.println("allAgents is null");
            return;
        }

        double dirX = 0;
        double dirY = 0;

        double speed = Math.max(1.0, 10.0 / Math.log(size + 2));

        GlobalAgent nearestDanger = null;
        double minDistanceToDanger = ParametersModel.getScreenWidth();

        GlobalAgent nearestFood = null;
        double minDistanceToFood = ParametersModel.getScreenWidth();

        boolean hasDanger = false;
        boolean hasFood = false;

        // Détermine l'agent plus gros le plus proche et l'agent Dumb ou food plus petit le plus proche
        for (GlobalAgent otherAgent : allAgents) {
            if (otherAgent != null && otherAgent != this) {
                if (otherAgent.getSize() > this.size) {
                    hasDanger = true;
                    double distance = distanceWith(otherAgent);
                    if (distance < minDistanceToDanger) {
                        nearestDanger = otherAgent;
                        minDistanceToDanger = distance;
                    }
                }
            }
            else {
                hasDanger = false;
            }

            if (otherAgent != null && otherAgent != this) {
                if (otherAgent.getSize() < this.size && (otherAgent instanceof Food || otherAgent instanceof DumbAgent)) {
                    hasFood = true;
                    double distance = distanceWith(otherAgent);
                    if (distance < minDistanceToFood) {
                        nearestFood = otherAgent;
                        minDistanceToFood = distance;
                    }
                }
            }
            else {
                hasFood = false;
            }
        }

        // Détermine la direction en fonction de la position des autres agents
        if (hasDanger && hasFood) {
            double distanceToFood = distanceWith(nearestFood);
            double distanceToLargerAgent = distanceWith(nearestDanger);

            if (distanceToFood / distanceToLargerAgent <= ParametersModel.getPreyDeviation()) {
                double deltaX = nearestFood.getPosX() - posX;
                double deltaY = nearestFood.getPosY() - posY;
                double distance = distanceWith(nearestFood);
                dirX += deltaX / distance;
                dirY += deltaY / distance;
            } else {
                double deltaX = nearestDanger.getPosX() - posX;
                double deltaY = nearestDanger.getPosY() - posY;
                double distance = distanceWith(nearestDanger);
                dirX -= deltaX / distance;
                dirY -= deltaY / distance;
            }
        } else if (hasDanger) {
            double deltaX = nearestDanger.getPosX() - posX;
            double deltaY = nearestDanger.getPosY() - posY;
            double distance = distanceWith(nearestDanger);
            dirX -= deltaX / distance;
            dirY -= deltaY / distance;
        } else if (hasFood) {
            double deltaX = nearestFood.getPosX() - posX;
            double deltaY = nearestFood.getPosY() - posY;
            double distance = distanceWith(nearestFood);
            dirX += deltaX / distance;
            dirY += deltaY / distance;
        } else {
            // Mouvement aléatoire
            double randomAngle = Math.random() * 2 * Math.PI;
            this.setPosition(this.getPosX() + Math.cos(randomAngle) * speed, this.getPosY() + Math.sin(randomAngle) * speed);
            return; // Sortir de la méthode après le mouvement aléatoire
        }

        double distance = Math.sqrt(dirX * dirX + dirY * dirY);

        if (distance > 0) {
            dirX = dirX / distance;
            dirY = dirY / distance;
        }

        posX += dirX * speed;
        posY += dirY * speed;

        // Vérifie que la cible se trouve dans les limites de la map
        posX = Math.max(0, Math.min(mapSizeX, posX));
        posY = Math.max(0, Math.min(mapSizeY, posY));
    }

}
