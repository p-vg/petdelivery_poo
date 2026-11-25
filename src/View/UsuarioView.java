package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UsuarioView extends JFrame {

    private static final Color ROXO = new Color(0x5B3FE0);
    private static final Color VERMELHO = new Color(0xD64545);

    private JTextField txtId, txtNome, txtTelefone, txtEmail;
    private JPasswordField txtSenha;
    private JComboBox<String> cmbTipo;
    private JButton btnSalvar, btnExcluir, btnLimpar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public UsuarioView() {
        setTitle("Gestão de Usuários (Donos)");
        setSize(860, 660);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(criarCabecalho("Gestão de Usuários", "Cadastre os donos que terão pets vinculados"), BorderLayout.NORTH);


        JPanel formulario = new JPanel(new GridBagLayout());

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNome = new JTextField();
        txtTelefone = new JTextField();
        txtEmail = new JTextField();
        txtSenha = new JPasswordField();
        cmbTipo = new JComboBox<>(new String[]{"cliente", "prestador", "admin"});

        txtId.putClientProperty("JTextField.placeholderText", "Gerado automaticamente");
        txtNome.putClientProperty("JTextField.placeholderText", "Ex: Marcos Oliveira");
        txtTelefone.putClientProperty("JTextField.placeholderText", "Ex: (42) 99999-0000");
        txtEmail.putClientProperty("JTextField.placeholderText", "Ex: marcos@email.com");
        txtSenha.putClientProperty("JTextField.placeholderText", "Digite a senha");
        txtSenha.putClientProperty("JPasswordField.showRevealButton", true);

        adicionarLinha(formulario, 0, "Código", txtId);
        adicionarLinha(formulario, 1, "Nome", txtNome);
        adicionarLinha(formulario, 2, "Telefone", txtTelefone);
        adicionarLinha(formulario, 3, "E-mail", txtEmail);
        adicionarLinha(formulario, 4, "Senha", txtSenha);
        adicionarLinha(formulario, 5, "Tipo", cmbTipo);


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
        cartao.add(criarTituloSecao("Dados do Usuário"), BorderLayout.NORTH);
        cartao.add(formulario, BorderLayout.CENTER);
        cartao.add(painelBotoes, BorderLayout.SOUTH);


        String[] colunas = {"Código", "Nome", "Telefone", "E-mail", "Tipo"};
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
        painelTabela.add(criarTituloSecao("Usuários Registrados"), BorderLayout.NORTH);
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
        txtTelefone.setText("");
        txtEmail.setText("");
        txtSenha.setText("");
        cmbTipo.setSelectedIndex(0);
        tabela.clearSelection();
        txtNome.requestFocus();
    }

    public void preencherFormulario(int id, String nome, String telefone, String email, String senha, String tipo) {
        txtId.setText(String.valueOf(id));
        txtNome.setText(nome);
        txtTelefone.setText(telefone);
        txtEmail.setText(email);
        txtSenha.setText(senha);
        cmbTipo.setSelectedItem(tipo);
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
    public String getTelefone() { return txtTelefone.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getSenha() { return new String(txtSenha.getPassword()); }
    public String getTipo() { return (String) cmbTipo.getSelectedItem(); }
    public JButton getBtnSalvar() { return btnSalvar; }
    public JButton getBtnExcluir() { return btnExcluir; }
    public DefaultTableModel getModeloTabela() { return modeloTabela; }
    public JTable getTabela() { return tabela; }
}
