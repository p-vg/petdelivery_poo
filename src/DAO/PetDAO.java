package DAO;

import Model.Pet;
import Util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAO {

    public void inserir(Pet p) throws SQLException {
        String sql = "INSERT INTO pet (id_usuario, nome, especie, raca, peso) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getIdUsuario());
            ps.setString(2, p.getNome());
            ps.setString(3, p.getEspecie());
            ps.setString(4, p.getRaca());
            ps.setBigDecimal(5, p.getPeso());
            ps.executeUpdate();
        }
    }

    public List<Pet> listar() throws SQLException {
        List<Pet> lista = new ArrayList<>();
        String sql = "SELECT p.id_pet, p.id_usuario, p.nome, p.especie, p.raca, p.peso, u.nome AS dono "
                   + "FROM pet p JOIN usuario u ON u.id_usuario = p.id_usuario ORDER BY p.id_pet";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pet p = new Pet();
                p.setIdPet(rs.getInt("id_pet"));
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setNome(rs.getString("nome"));
                p.setEspecie(rs.getString("especie"));
                p.setRaca(rs.getString("raca"));
                p.setPeso(rs.getBigDecimal("peso"));
                p.setDonoNome(rs.getString("dono"));
                lista.add(p);
            }
        }
        return lista;
    }

    public void atualizar(Pet p) throws SQLException {
        String sql = "UPDATE pet SET id_usuario = ?, nome = ?, especie = ?, raca = ?, peso = ? WHERE id_pet = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getIdUsuario());
            ps.setString(2, p.getNome());
            ps.setString(3, p.getEspecie());
            ps.setString(4, p.getRaca());
            ps.setBigDecimal(5, p.getPeso());
            ps.setInt(6, p.getIdPet());
            ps.executeUpdate();
        }
    }

    public void excluir(int idPet) throws SQLException {
        String sql = "DELETE FROM pet WHERE id_pet = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPet);
            ps.executeUpdate();
        }
    }
}
