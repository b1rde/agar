import model.ParametersModel;
import view.ParametersView;
import view.AgentView;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) {
        // __CAMILLE__
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
                while (!ParametersModel.hasEnded) {

                    agentView.updateAgents();

                    if (agentView.getWinner() > 0) {
                        // Fin de la simulation
                        ParametersModel.hasEnded = true;

                        switch (agentView.getWinner()) {
                            case 1:
                                displayVictoryMessage(agentView, "Les agents Dumb gagnent");
                                break;
                            case 2:
                                displayVictoryMessage(agentView, "Les prédateurs gagnent");
                                break;
                            case 3:
                                displayVictoryMessage(agentView, "Les proies gagnent");
                                break;
                        }
                    }

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
        // __ESTEBAN__
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

    private static void displayVictoryMessage(AgentView agentView, String message) {
        // __CAMILLE__
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(agentView);
                if (frame != null) {
                    JDialog dialog = new JDialog(frame, "Victoire", Dialog.ModalityType.APPLICATION_MODAL);
                    dialog.setLayout(new BorderLayout());

                    JLabel victoryLabel = new JLabel(message, SwingConstants.CENTER);
                    victoryLabel.setFont(new Font("Arial", Font.BOLD, 24));
                    victoryLabel.setForeground(Color.BLACK);

                    JTextField seedField = new JTextField("Seed : " + String.valueOf(ParametersModel.getSeed()));
                    seedField.setEditable(false);
                    seedField.setHorizontalAlignment(JTextField.CENTER);
                    seedField.setFont(new Font("Arial", Font.PLAIN, 18));
                    seedField.setBorder(BorderFactory.createEmptyBorder());

                    JPanel panel = new JPanel(new BorderLayout());
                    panel.add(victoryLabel, BorderLayout.CENTER);
                    panel.add(seedField, BorderLayout.SOUTH);

                    dialog.add(panel, BorderLayout.CENTER);
                    dialog.setSize(400, 200);
                    dialog.setLocationRelativeTo(frame);
                    dialog.setVisible(true);
                }
            }
        });
    }
}
