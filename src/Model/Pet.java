package Model;

import java.math.BigDecimal;

public class Pet {
    private int idPet;
    private int idUsuario;
    private String nome;
    private String especie;
    private String raca;
    private BigDecimal peso;   // pode ser nulo
    private String donoNome;

    public Pet() {}

    public int getIdPet() { return idPet; }
    public void setIdPet(int idPet) { this.idPet = idPet; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }
    public BigDecimal getPeso() { return peso; }
    public void setPeso(BigDecimal peso) { this.peso = peso; }
    public String getDonoNome() { return donoNome; }
    public void setDonoNome(String donoNome) { this.donoNome = donoNome; }
}
