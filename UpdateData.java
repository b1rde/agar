package fr.um3.agar;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Toolkit;

public class UpdateData implements Runnable {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screen_height = screenSize.height;
    private int screen_width = screenSize.width;
    private JLabel label;
    private int number = 1;
    private JButton button;
    private int button_pos_x = 50;
    private int button_pos_y = 50;
    private int button_dir_x = 1;
    private int button_dir_y = 1;

    public UpdateData(JLabel test_label, JButton test_button) {
        this.label = test_label;
        this.button = test_button;
    }

    @Override
    public void run() {
        while (true) {
            number = updateLabel(number);
            int[] button_new_data = updatePosButton(button_pos_x, button_pos_y, button_dir_x, button_dir_y, screen_width, screen_height);
            button_pos_x = button_new_data[0];
            button_pos_y = button_new_data[1];
            button_dir_x = button_new_data[2];
            button_dir_y = button_new_data[3];


            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    updateGUI(number, button_pos_x, button_pos_y);
                }
            });

            try {
                Thread.sleep(16); // en ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int updateLabel(int num) {
        if (num == 1) {
            return 2;
        }
        else {
            return 1;
        } 
    }
    
    private int[] updatePosButton(int posX, int posY, int dirX, int dirY, int screen_width, int screen_height) {
        int newPosX;
        int newPosY;
        int newDirX;
        int newDirY;
        int stepsX = (int)(Math.random() * 4);
        int stepsY = (int)(Math.random() * 4);
        int buttonWidth = 100; 
        int buttonHeight = 30;
        int nextStepX = posX + buttonWidth + stepsX * dirX;
        int nextStepY = posY + buttonHeight + stepsY * dirY;

        if (nextStepX < screen_width && nextStepX > 0) {
            newPosX = posX + stepsX * dirX;
            newDirX = dirX;
        } else {
            newDirX = dirX * -1;
            newPosX = posX + stepsX * newDirX;
        }

        if (nextStepY < screen_height && nextStepY > 0) {
            newPosY = posY + stepsY * dirY;
            newDirY = dirY;
        } else {
            newDirY = dirY * -1;
            newPosY = posY - stepsY * newDirY;
        }

        return new int[] { newPosX, newPosY, newDirX, newDirY };
    }

    private void updateGUI(int num, int posX, int posY) {
        if (num == 1) {
            label.setText("ca");
        }
        else {
            label.setText("clignote");
        }
        button.setBounds(posX, posY, 100, 30);
    }
}
