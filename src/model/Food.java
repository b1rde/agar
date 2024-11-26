package model;

import java.awt.*;

public class Food extends GlobalAgent{

    public Food(double posX, double posY, double size) {
        super(posX, posY, size);
        this.color = Color.GREEN;
    }

    @Override
    public void move() {
        // Rien ici
    }
}
