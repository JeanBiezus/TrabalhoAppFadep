package fadep.android.pos.trabalhoapp.WS;

import java.io.Serializable;

/**
 * Created by Jean on 06/04/2018.
 */

public class Usuario  implements Serializable {

    private int id;
    private String nome;
    private String login;
    private String senha;
    private String foto;
    private String dataCadastro;
    private String dataAlteracao;
    private Long versao;
    private Boolean deletado = false;

    public Usuario() {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.foto = foto;
        this.dataCadastro = dataCadastro;
        this.dataAlteracao = dataAlteracao;
        this.versao = versao;
        this.deletado = deletado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(String dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Boolean getDeletado() {
        return deletado;
    }

    public void setDeletado(Boolean deletado) {
        this.deletado = deletado;
    }
}
