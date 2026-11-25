package View;

import Model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PetView extends JFrame {

    private static final Color ROXO = new Color(0x5B3FE0);
    private static final Color VERMELHO = new Color(0xD64545);

    private JTextField txtId, txtNome, txtEspecie, txtRaca, txtPeso;
    private JComboBox<Usuario> cmbDono;
    private JButton btnSalvar, btnExcluir, btnLimpar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public PetView() {
        setTitle("Gestão de Pets");
        setSize(860, 640);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(criarCabecalho("Gestão de Pets", "Cadastre os pets e vincule cada um ao seu dono"), BorderLayout.NORTH);


        JPanel formulario = new JPanel(new GridBagLayout());

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNome = new JTextField();
        txtEspecie = new JTextField();
        txtRaca = new JTextField();
        txtPeso = new JTextField();
        cmbDono = new JComboBox<>();

        txtId.putClientProperty("JTextField.placeholderText", "Gerado automaticamente");
        txtNome.putClientProperty("JTextField.placeholderText", "Ex: Thor");
        txtEspecie.putClientProperty("JTextField.placeholderText", "Ex: Cachorro");
        txtRaca.putClientProperty("JTextField.placeholderText", "Ex: Golden Retriever");
        txtPeso.putClientProperty("JTextField.placeholderText", "Ex: 12.5");

        adicionarLinha(formulario, 0, "Código", txtId);
        adicionarLinha(formulario, 1, "Nome", txtNome);
        adicionarLinha(formulario, 2, "Espécie", txtEspecie);
        adicionarLinha(formulario, 3, "Raça", txtRaca);
        adicionarLinha(formulario, 4, "Peso (kg)", txtPeso);
        adicionarLinha(formulario, 5, "Dono (Usuário)", cmbDono);


        btnSalvar = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");
        estilizarBotao(btnSalvar, ROXO);
        estilizarBotao(btnExcluir, VERMELHO);
        estilizarBotao(btnLimpar, null);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnSalvar);

        JPanel cartao = new JPanel(new BorderLayout());
        cartao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xDDDDDD), 1, true),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        cartao.add(criarTituloSecao("Dados do Pet"), BorderLayout.NORTH);
        cartao.add(formulario, BorderLayout.CENTER);
        cartao.add(painelBotoes, BorderLayout.SOUTH);


        String[] colunas = {"Código", "Nome", "Espécie", "Raça", "Peso (kg)", "Dono"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(32);
        tabela.setShowVerticalLines(false);
        tabela.setFillsViewportHeight(true);
        tabela.getTableHeader().setFont(tabela.getTableHeader().getFont().deriveFont(Font.BOLD));

        JScrollPane scroll = new JScrollPane(tabela);

        JPanel painelTabela = new JPanel(new BorderLayout(0, 8));
        painelTabela.add(criarTituloSecao("Pets Registrados"), BorderLayout.NORTH);
        painelTabela.add(scroll, BorderLayout.CENTER);

        JPanel corpo = new JPanel(new BorderLayout(0, 16));
        corpo.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        corpo.add(cartao, BorderLayout.NORTH);
        corpo.add(painelTabela, BorderLayout.CENTER);
        add(corpo, BorderLayout.CENTER);

        btnLimpar.addActionListener(e -> limparFormulario());
    }


    private JPanel criarCabecalho(String titulo, String subtitulo) {
        JPanel cabecalho = new JPanel(new GridLayout(2, 1));
        cabecalho.setBackground(ROXO);
        cabecalho.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel(subtitulo);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(new Color(0xE0DAFF));

        cabecalho.add(lblTitulo);
        cabecalho.add(lblSub);
        return cabecalho;
    }

    private JLabel criarTituloSecao(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        return lbl;
    }

    private void adicionarLinha(JPanel painel, int linha, String rotulo, JComponent campo) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridy = linha;
        g.insets = new Insets(5, 0, 5, 12);
        g.anchor = GridBagConstraints.WEST;

        g.gridx = 0;
        g.weightx = 0;
        painel.add(new JLabel(rotulo), g);

        g.gridx = 1;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(5, 0, 5, 0);
        painel.add(campo, g);
    }

    private void estilizarBotao(JButton botao, Color cor) {
        botao.setFont(new Font("Segoe UI", Font.BOLD, 13));
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(110, 36));
        if (cor != null) {
            botao.setBackground(cor);
            botao.setForeground(Color.WHITE);
        }
    }

    public void limparFormulario() {
        txtId.setText("");
        txtNome.setText("");
        txtEspecie.setText("");
        txtRaca.setText("");
        txtPeso.setText("");
        if (cmbDono.getItemCount() > 0) cmbDono.setSelectedIndex(0);
        tabela.clearSelection();
        txtNome.requestFocus();
    }

    public void preencherFormulario(int id, String nome, String especie, String raca, String peso, int idUsuario) {
        txtId.setText(String.valueOf(id));
        txtNome.setText(nome);
        txtEspecie.setText(especie);
        txtRaca.setText(raca);
        txtPeso.setText(peso);
        selecionarDono(idUsuario);
    }

    public void setDonos(List<Usuario> donos) {
        int idSelecionado = getDonoSelecionado() != null ? getDonoSelecionado().getIdUsuario() : -1;
        cmbDono.removeAllItems();
        for (Usuario u : donos) cmbDono.addItem(u);
        if (idSelecionado != -1) selecionarDono(idSelecionado);
    }

    public void selecionarDono(int idUsuario) {
        for (int i = 0; i < cmbDono.getItemCount(); i++) {
            if (cmbDono.getItemAt(i).getIdUsuario() == idUsuario) {
                cmbDono.setSelectedIndex(i);
                return;
            }
        }
    }

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    public boolean confirmar(String mensagem) {
        return JOptionPane.showConfirmDialog(this, mensagem, "Confirmação",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public String getId() { return txtId.getText(); }
    public String getNome() { return txtNome.getText(); }
    public String getEspecie() { return txtEspecie.getText(); }
    public String getRaca() { return txtRaca.getText(); }
    public String getPeso() { return txtPeso.getText(); }
    public Usuario getDonoSelecionado() { return (Usuario) cmbDono.getSelectedItem(); }
    public JButton getBtnSalvar() { return btnSalvar; }
    public JButton getBtnExcluir() { return btnExcluir; }
    public DefaultTableModel getModeloTabela() { return modeloTabela; }
    public JTable getTabela() { return tabela; }
}
