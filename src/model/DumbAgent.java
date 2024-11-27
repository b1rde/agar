package model;

import java.awt.*;

public class DumbAgent extends GlobalAgent {
    private int mapSizeX = ParametersModel.getScreenWidth();
    private int mapSizeY = ParametersModel.getScreenHeight();

    public DumbAgent(double posX, double posY, double size, double targetX, double targetY) {
        super(posX, posY, size);
        this.targetX = targetX;
        this.targetY = targetY;
        this.color = Color.ORANGE;
    }

    @Override
    public void move() {
        // Vitesse dépend de la taille
        double speed = Math.max(1.0, 10.0 / Math.log(size + 2));

        if (hasReachedTarget()) {
            selectRandomTarget();
        }

        // Direction vers la cible
        double dirX = targetX - posX;
        double dirY = targetY - posY;

        double distance = Math.sqrt(dirX * dirX + dirY * dirY);

        if (distance > 0) {
            dirX = dirX / distance;
            dirY = dirY / distance;
        }

        posX += dirX * speed;
        posY += dirY * speed;
    }

    private void selectRandomTarget() {


        // Ecart type de la distribution (linéarité des mouvements)
        double stdDev = ParametersModel.getDumbMovLin();

        // Cible choisie aléatoirement sur une distribution gaussienne
        targetX = posX + ParametersModel.randomGaussian() * stdDev;
        targetY = posY + ParametersModel.randomGaussian() * stdDev;

        // Vérifier que la cible se trouve dans les limites de la map
        targetX = Math.max(0, Math.min(mapSizeX, targetX));
        targetY = Math.max(0, Math.min(mapSizeY, targetY));
    }

    private boolean hasReachedTarget() {
        // Flexibilité du calcul dépend de la vitesse
        double threshold = Math.max(1.0, 10.0 / Math.log(size + 2));

        return Math.abs(posX - targetX) <= threshold && Math.abs(posY - targetY) <= threshold;
    }
}