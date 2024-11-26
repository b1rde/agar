import model.ParametersModel;
import view.ParametersView;
import view.AgentView;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) {
        ParametersModel model = new ParametersModel();

        CountDownLatch latch = new CountDownLatch(1);

        ParametersView view = new ParametersView(model, latch);

        view.displayParameters();

        // Attendre que le bouton "Lancer" soit cliqué
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AgentView agentView = new AgentView();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    agentView.updateAgents();
                    try {
                        Thread.sleep(1000 / model.getTargetFPS());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                displayGUI(agentView, model);
            }
        });
    }

    private static void displayGUI(AgentView agentView, ParametersModel model) {
        JFrame frame = new JFrame("Agar");

        int screenWidth = model.getScreenWidth();
        int screenHeight = model.getScreenHeight();

        frame.setSize(screenWidth, screenHeight);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(agentView, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
