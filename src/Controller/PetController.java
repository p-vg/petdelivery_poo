package Controller;

import DAO.PetDAO;
import DAO.UsuarioDAO;
import Model.Pet;
import Model.Usuario;
import View.PetView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetController {

    private final PetView view;
    private final PetDAO petDao = new PetDAO();
    private final UsuarioDAO usuarioDao = new UsuarioDAO();
    private List<Pet> pets = new ArrayList<>();

    public PetController(PetView view) {
        this.view = view;
        ligarEventos();
        carregarDonos();
        carregarTabela();
    }

    private void ligarEventos() {
        view.getBtnSalvar().addActionListener(e -> salvar());
        view.getBtnExcluir().addActionListener(e -> excluir());
        view.getTabela().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) selecionarLinha();
        });
        // Quando a janela volta ao foco, recarrega os donos (caso tenha cadastrado um novo)
        view.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                carregarDonos();
            }
        });
    }

    private void carregarDonos() {
        try {
            view.setDonos(usuarioDao.listar());
        } catch (SQLException ex) {
            view.mostrarMensagem("Erro ao carregar donos: " + ex.getMessage());
        }
    }

    private void carregarTabela() {
        try {
            pets = petDao.listar();
            view.getModeloTabela().setRowCount(0);
            for (Pet p : pets) {
                view.getModeloTabela().addRow(new Object[]{
                        p.getIdPet(), p.getNome(), p.getEspecie(), p.getRaca(),
                        p.getPeso() == null ? "" : p.getPeso().toPlainString(), p.getDonoNome()});
            }
        } catch (SQLException ex) {
            view.mostrarMensagem("Erro ao listar pets: " + ex.getMessage());
        }
    }

    private void selecionarLinha() {
        int linha = view.getTabela().getSelectedRow();
        if (linha >= 0) {
            Pet p = pets.get(linha);
            view.preencherFormulario(p.getIdPet(), p.getNome(), p.getEspecie(),
                    p.getRaca() == null ? "" : p.getRaca(),
                    p.getPeso() == null ? "" : p.getPeso().toPlainString(),
                    p.getIdUsuario());
        }
    }

    private void salvar() {
        String nome = view.getNome().trim();
        String especie = view.getEspecie().trim();
        Usuario dono = view.getDonoSelecionado();

        if (nome.isEmpty() || especie.isEmpty()) {
            view.mostrarMensagem("Nome e espécie são obrigatórios.");
            return;
        }
        if (dono == null) {
            view.mostrarMensagem("Cadastre um usuário antes de cadastrar um pet.");
            return;
        }


        BigDecimal peso = null;
        String textoPeso = view.getPeso().trim().replace(",", ".");
        if (!textoPeso.isEmpty()) {
            try {
                peso = new BigDecimal(textoPeso);
                if (peso.signum() < 0 || peso.compareTo(new BigDecimal("999.99")) > 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                view.mostrarMensagem("Informe um peso válido (entre 0 e 999,99).");
                return;
            }
        }

        try {
            Pet p = new Pet();
            p.setNome(nome);
            p.setEspecie(especie);
            p.setRaca(view.getRaca().trim());
            p.setPeso(peso);
            p.setIdUsuario(dono.getIdUsuario());

            if (view.getId().isEmpty()) {
                petDao.inserir(p);
                view.mostrarMensagem("Pet cadastrado com sucesso!");
            } else {
                p.setIdPet(Integer.parseInt(view.getId()));
                petDao.atualizar(p);
                view.mostrarMensagem("Pet atualizado com sucesso!");
            }
            carregarTabela();
            view.limparFormulario();
        } catch (SQLException ex) {
            view.mostrarMensagem("Erro ao salvar pet: " + ex.getMessage());
        }
    }

    private void excluir() {
        if (view.getId().isEmpty()) {
            view.mostrarMensagem("Selecione um pet na tabela para excluir.");
            return;
        }
        if (!view.confirmar("Excluir este pet?")) return;
        try {
            petDao.excluir(Integer.parseInt(view.getId()));
            carregarTabela();
            view.limparFormulario();
            view.mostrarMensagem("Pet excluído com sucesso!");
        } catch (SQLException ex) {
            view.mostrarMensagem("Erro ao excluir pet: " + ex.getMessage());
        }
    }
}
