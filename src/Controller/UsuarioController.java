package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;
import View.UsuarioView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {

    private final UsuarioView view;
    private final UsuarioDAO dao = new UsuarioDAO();
    private List<Usuario> usuarios = new ArrayList<>();

    public UsuarioController(UsuarioView view) {
        this.view = view;
        ligarEventos();
        carregarTabela();
    }

    private void ligarEventos() {
        view.getBtnSalvar().addActionListener(e -> salvar());
        view.getBtnExcluir().addActionListener(e -> excluir());
        view.getTabela().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) selecionarLinha();
        });
    }

    private void carregarTabela() {
        try {
            usuarios = dao.listar();
            view.getModeloTabela().setRowCount(0);
            for (Usuario u : usuarios) {
                view.getModeloTabela().addRow(new Object[]{
                        u.getIdUsuario(), u.getNome(), u.getTelefone(), u.getEmail(), u.getTipo()});
            }
        } catch (SQLException ex) {
            view.mostrarMensagem("Erro ao listar usuários: " + ex.getMessage());
        }
    }

    private void selecionarLinha() {
        int linha = view.getTabela().getSelectedRow();
        if (linha >= 0) {
            Usuario u = usuarios.get(linha);
            view.preencherFormulario(u.getIdUsuario(), u.getNome(), u.getTelefone(),
                    u.getEmail(), u.getSenha(), u.getTipo());
        }
    }

    private void salvar() {
        String nome = view.getNome().trim();
        String email = view.getEmail().trim();
        String senha = view.getSenha();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            view.mostrarMensagem("Nome, e-mail e senha são obrigatórios.");
            return;
        }
        if (!email.contains("@")) {
            view.mostrarMensagem("Informe um e-mail válido.");
            return;
        }
        try {
            Usuario u = new Usuario();
            u.setNome(nome);
            u.setTelefone(view.getTelefone().trim());
            u.setEmail(email);
            u.setSenha(senha);
            u.setTipo(view.getTipo());

            if (view.getId().isEmpty()) {
                dao.inserir(u);
                view.mostrarMensagem("Usuário cadastrado com sucesso!");
            } else {
                u.setIdUsuario(Integer.parseInt(view.getId()));
                dao.atualizar(u);
                view.mostrarMensagem("Usuário atualizado com sucesso!");
            }
            carregarTabela();
            view.limparFormulario();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) { // chave UNIQUE duplicada
                view.mostrarMensagem("Já existe um usuário com este e-mail.");
            } else {
                view.mostrarMensagem("Erro ao salvar usuário: " + ex.getMessage());
            }
        }
    }

    private void excluir() {
        if (view.getId().isEmpty()) {
            view.mostrarMensagem("Selecione um usuário na tabela para excluir.");
            return;
        }
        if (!view.confirmar("Excluir este usuário? Os pets e endereços dele também serão excluídos.")) return;
        try {
            dao.excluir(Integer.parseInt(view.getId()));
            carregarTabela();
            view.limparFormulario();
            view.mostrarMensagem("Usuário excluído com sucesso!");
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1451) { // violação de chave estrangeira (ex.: tem pedidos)
                view.mostrarMensagem("Não é possível excluir: este usuário possui pedidos cadastrados.");
            } else {
                view.mostrarMensagem("Erro ao excluir usuário: " + ex.getMessage());
            }
        }
    }
}
