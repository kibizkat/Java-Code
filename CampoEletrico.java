import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CampoEletrico extends JFrame {

    private JTextField txtRaio, txtCarga, txtAngulo;
    private JLabel lblResultado;
    private JPanel panelGrafico;

    public CampoEletrico() {
        setTitle("Cálculo do Campo Elétrico");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        // Painel de entrada de dados
        JPanel panelInput = new JPanel();
        panelInput.setLayout(new GridLayout(4, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelInput.add(new JLabel("Raio de Curvatura (cm):"));
        txtRaio = new JTextField();
        panelInput.add(txtRaio);

        panelInput.add(new JLabel("Carga (pC):"));
        txtCarga = new JTextField();
        panelInput.add(txtCarga);

        panelInput.add(new JLabel("Ângulo (rad):"));
        txtAngulo = new JTextField();
        panelInput.add(txtAngulo);

        JButton btnCalcular = new JButton("Calcular");
        panelInput.add(btnCalcular);

        lblResultado = new JLabel("Resultado: ");
        panelInput.add(lblResultado);

        add(panelInput, BorderLayout.NORTH);

        // Painel gráfico
        panelGrafico = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraphics(g);
            }
        };
        panelGrafico.setPreferredSize(new Dimension(500, 300));
        panelGrafico.setBackground(Color.WHITE); 
        add(panelGrafico, BorderLayout.CENTER);

        // Ação do botão Calcular
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularCampoEletrico();
            }
        });
    }

    private void calcularCampoEletrico() {
        try {
            double R = Double.parseDouble(txtRaio.getText()) / 100.0;  // Converter para metros
            double Q = Double.parseDouble(txtCarga.getText()) * 1e-12;  // Converter para coulombs
            double theta = Double.parseDouble(txtAngulo.getText());

            double epsilon_0 = 8.85e-12;  // Permissividade elétrica no vácuo (C^2/N·m^2)
            double E = (1 / (4 * Math.PI * epsilon_0)) * (Q / Math.pow(R, 2)) * (theta / (2 * Math.PI));

            lblResultado.setText(String.format("Resultado: E = %.3f N/C", E));
            panelGrafico.repaint();  // Redesenha o painel gráfico
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void drawGraphics(Graphics g) {
        try {
            int width = panelGrafico.getWidth();
            int height = panelGrafico.getHeight();
            int centerX = width / 2;
            int centerY = height / 2;

            // Limpar o painel gráfico
            g.clearRect(0, 0, width, height);

            // Desenhar o arco representando a barra circular
            g.setColor(Color.BLUE);
            int arcWidth = 100;
            int arcHeight = 100;
            int startAngle = 0;
            double angle = Double.parseDouble(txtAngulo.getText());
            int arcAngle = (int) Math.toDegrees(angle);
            g.drawArc(centerX - arcWidth / 2, centerY - arcHeight / 2, arcWidth, arcHeight, startAngle, arcAngle);

            // Desenhar o ponto central de curvatura
            g.setColor(Color.RED);
            g.fillOval(centerX - 5, centerY - 5, 10, 10); // Representa o ponto central

        } catch (NumberFormatException ex) {
            // Se houver erro na leitura dos valores
            System.out.println("Erro ao desenhar o gráfico: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CampoEletrico frame = new CampoEletrico();
            frame.setVisible(true);
        });
    }
}
