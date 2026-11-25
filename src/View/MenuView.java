package View;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JFrame {

    private static final Color ROXO = new Color(0x5B3FE0);

    private JButton btnUsuarios;
    private JButton btnPets;

    public MenuView() {
        setTitle("PetDelivery");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel cabecalho = new JPanel(new GridLayout(2, 1));
        cabecalho.setBackground(ROXO);
        cabecalho.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel titulo = new JLabel("PetDelivery");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Gerencie donos e pets em um só lugar");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(0xE0DAFF));

        cabecalho.add(titulo);
        cabecalho.add(subtitulo);
        add(cabecalho, BorderLayout.NORTH);


        JPanel painel = new JPanel(new GridLayout(2, 1, 0, 14));
        painel.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        btnUsuarios = new JButton("Gerenciar Usuários (Donos)");
        btnPets = new JButton("Gerenciar Pets");

        estilizarBotao(btnUsuarios, true);
        estilizarBotao(btnPets, false);

        painel.add(btnUsuarios);
        painel.add(btnPets);
        add(painel, BorderLayout.CENTER);
    }

    private void estilizarBotao(JButton botao, boolean principal) {
        botao.setFont(new Font("Segoe UI", Font.BOLD, 15));
        botao.setFocusPainted(false);
        if (principal) {
            botao.setBackground(ROXO);
            botao.setForeground(Color.WHITE);
        }
    }

    public JButton getBtnUsuarios() { return btnUsuarios; }
    public JButton getBtnPets() { return btnPets; }
}
