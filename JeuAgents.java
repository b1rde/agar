import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class JeuAgents {

    public static void main(String[] args) {
        // Création de la fenêtre principale (JFrame)
        JFrame fenetre = new JFrame("Jeu d'agents avec nourriture et obstacles");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(1200, 800);  // Taille de la fenêtre augmentée

        // Création du panneau pour dessiner et animer les agents
        PanneauAgents panneauAgents = new PanneauAgents();
        fenetre.add(panneauAgents);

        // Timer pour mettre à jour les positions des agents toutes les 100 ms (ralentit le jeu)
        Timer timer = new Timer(100, e -> {
            if (!panneauAgents.verifierFinJeu()) {  // Vérifie si le jeu est terminé
                panneauAgents.mettreAJourAgents();
                panneauAgents.verifierCollisions();
                panneauAgents.repaint();
            }
        });
        timer.start();

        // Rendre la fenêtre visible
        fenetre.setVisible(true);
    }
}

class Agent {
    int x, y, taille;
    int vitesseX, vitesseY;  // Vitesse en X et Y pour gérer le rebond et l'évitement
    Color couleur;

    public Agent(int x, int y, int taille, int vitesseX, int vitesseY, Color couleur) {
        this.x = x;
        this.y = y;
        this.taille = taille;
        this.vitesseX = vitesseX;
        this.vitesseY = vitesseY;
        this.couleur = couleur;
    }

    // Vérifie si cet agent entre en collision avec un autre agent
    public boolean estEnCollision(Agent autre) {
        int dx = this.x - autre.x;
        int dy = this.y - autre.y;
        int distance = (int) Math.sqrt(dx * dx + dy * dy);

        return distance < (this.taille / 2 + autre.taille / 2); // collision si la distance est inférieure à la somme des rayons
    }

    // Absorbe l'autre agent (augmente la taille)
    public void absorber(Agent autre) {
        this.taille += autre.taille / 4;  // La taille augmente moins rapidement
    }

    // Vérifie si cet agent devient trop gros
    public boolean estTropGros() {
        return this.taille > 200;  // La taille maximale est augmentée pour prolonger le jeu
    }

    // Vérifie si cet agent est en collision avec un obstacle
    public boolean estEnCollisionAvecObstacle(ObstacleCirculaire obstacle) {
        int dx = this.x - obstacle.x;
        int dy = this.y - obstacle.y;
        int distance = (int) Math.sqrt(dx * dx + dy * dy);

        return distance < (this.taille / 2 + obstacle.rayon);  // collision si l'agent touche l'obstacle
    }

    // Vérifie si cet agent est en collision avec un élément de nourriture
    public boolean estEnCollisionAvecNourriture(Nourriture nourriture) {
        int dx = this.x - nourriture.x;
        int dy = this.y - nourriture.y;
        int distance = (int) Math.sqrt(dx * dx + dy * dy);

        return distance < (this.taille / 2 + nourriture.taille / 2);  // collision si l'agent touche la nourriture
    }
    // Réduire la taille de l'agent lorsqu'il touche un obstacle
    public void diminuerTaille(int reduction) {
        this.taille = Math.max(10, this.taille - reduction);  // Réduit la taille mais empêche que la taille descende en dessous de 10
    }

    public void miseAJourPosition(int largeurFenetre, int hauteurFenetre, ArrayList<ObstacleCirculaire> obstacles) {
        x += vitesseX;
        y += vitesseY;

        // Rebondir sur les côtés horizontaux
        if (x - taille / 2 <= 0 || x + taille / 2 >= largeurFenetre) {
            vitesseX = -vitesseX;  // Inverser la direction sur l'axe X
        }

        // Rebondir sur les côtés verticaux
        if (y - taille / 2 <= 0 || y + taille / 2 >= hauteurFenetre) {
            vitesseY = -vitesseY;  // Inverser la direction sur l'axe Y
        }

        // Éviter les obstacles et réduire la taille s'ils sont touchés
        for (ObstacleCirculaire obstacle : obstacles) {
            if (estEnCollisionAvecObstacle(obstacle)) {
                eviterObstacle(obstacle);  // Changer la direction pour éviter l'obstacle
                diminuerTaille(5);  // Réduire la taille de l'agent de 5 lorsqu'il touche un obstacle
            }
        }
    }


    // Change la direction pour éviter un obstacle
    private void eviterObstacle(ObstacleCirculaire obstacle) {
        // Si l'agent est à gauche de l'obstacle, se déplacer vers la droite, sinon vers la gauche
        if (x < obstacle.x) {
            vitesseX = -Math.abs(vitesseX);
        } else {
            vitesseX = Math.abs(vitesseX);
        }

        // Si l'agent est au-dessus de l'obstacle, se déplacer vers le bas, sinon vers le haut
        if (y < obstacle.y) {
            vitesseY = -Math.abs(vitesseY);
        } else {
            vitesseY = Math.abs(vitesseY);
        }
    }
}

// Classe pour représenter les obstacles circulaires
class ObstacleCirculaire {
    int x, y, rayon;

    public ObstacleCirculaire(int x, int y, int rayon) {
        this.x = x;
        this.y = y;
        this.rayon = rayon;
    }

    // Méthode pour dessiner l'obstacle
    public void dessiner(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        g2d.fillOval(x - rayon, y - rayon, rayon * 2, rayon * 2);  // Dessine un cercle vert
    }
}

// Classe pour représenter les éléments de nourriture
class Nourriture {
    int x, y, taille;

    public Nourriture(int x, int y, int taille) {
        this.x = x;
        this.y = y;
        this.taille = taille;
    }

    // Méthode pour dessiner la nourriture
    public void dessiner(Graphics2D g2d) {
        g2d.setColor(Color.ORANGE);
        g2d.fillOval(x - taille / 2, y - taille / 2, taille, taille);  // Dessine un petit cercle orange
    }
}

class PanneauAgents extends JPanel {
    private final ArrayList<Agent> agents = new ArrayList<>();
    private final ArrayList<ObstacleCirculaire> obstacles = new ArrayList<>();  // Liste des obstacles circulaires
    private final ArrayList<Nourriture> nourritures = new ArrayList<>();  // Liste des éléments de nourriture

    public PanneauAgents() {
        // Initialisation des agents
        Random aleatoire = new Random();
        for (int i = 0; i < 3; i++) {
            // Prédateurs
            agents.add(new Agent(aleatoire.nextInt(1200), aleatoire.nextInt(800), 50, 5, 5, Color.RED));

            // Agents aléatoires
            agents.add(new Agent(aleatoire.nextInt(1200), aleatoire.nextInt(800), 80, 3, 3, Color.GREEN));

            // Agents fuyants
            agents.add(new Agent(aleatoire.nextInt(1200), aleatoire.nextInt(800), 60, 2, 2, Color.BLUE));
        }

        // Initialisation des obstacles
        for (int i = 0; i < 10; i++) {
            int rayon = aleatoire.nextInt(10) + 10;  // Petits cercles entre 10 et 20 pixels
            obstacles.add(new ObstacleCirculaire(aleatoire.nextInt(1200), aleatoire.nextInt(800), rayon));
        }

        // Initialisation de la nourriture
        for (int i = 0; i < 50; i++) {
            int taille = 10;  // Chaque élément de nourriture est un petit cercle de 10 pixels
            nourritures.add(new Nourriture(aleatoire.nextInt(1200), aleatoire.nextInt(800), taille));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dessiner les obstacles circulaires
        for (ObstacleCirculaire obstacle : obstacles) {
            obstacle.dessiner(g2d);
        }

        // Dessiner la nourriture
        for (Nourriture nourriture : nourritures) {
            nourriture.dessiner(g2d);
        }

        // Dessiner chaque agent
        for (Agent agent : agents) {
            g2d.setColor(agent.couleur);
            g2d.fillOval(agent.x - agent.taille / 2, agent.y - agent.taille / 2, agent.taille, agent.taille);
        }
    }

    // Mise à jour des agents avec différents comportements
    public void mettreAJourAgents() {
        for (Agent agent : agents) {
            // Mise à jour de la position de l'agent avec rebond sur les côtés et évitement des obstacles
            agent.miseAJourPosition(getWidth(), getHeight(), obstacles);

            // Vérifier les collisions avec la nourriture
            verifierCollisionsNourriture(agent);
        }
    }

    // Vérifie si un agent entre en collision avec la nourriture
    private void verifierCollisionsNourriture(Agent agent) {
        for (int i = 0; i < nourritures.size(); i++) {
            Nourriture nourriture = nourritures.get(i);
            if (agent.estEnCollisionAvecNourriture(nourriture)) {
                agent.taille += 2;  // L'agent grandit lorsqu'il mange la nourriture
                nourritures.remove(nourriture);  // Supprime la nourriture mangée
                i--;  // Ajuster l'index après suppression
            }
        }
    }

    // Vérifie les collisions entre agents et absorptions
    public void verifierCollisions() {
        for (int i = 0; i < agents.size(); i++) {
            Agent agent1 = agents.get(i);

            for (int j = i + 1; j < agents.size(); j++) {
                Agent agent2 = agents.get(j);

                if (agent1.estEnCollision(agent2)) {
                    if (agent1.taille > agent2.taille) {
                        agent1.absorber(agent2);
                        agents.remove(agent2);
                        j--;
                    } else if (agent2.taille > agent1.taille) {
                        agent2.absorber(agent1);
                        agents.remove(agent1);
                        i--;
                        break;
                    }
                }
            }
        }
    }

    // Vérifie si un agent est devenu trop gros et arrête le jeu
    public boolean verifierFinJeu() {
        for (Agent agent : agents) {
            if (agent.estTropGros()) {
                JOptionPane.showMessageDialog(this, "Le jeu est terminé ! Un agent est devenu trop gros !");
                return true;  // Le jeu est terminé
            }
        }
        return false;
    }
}
