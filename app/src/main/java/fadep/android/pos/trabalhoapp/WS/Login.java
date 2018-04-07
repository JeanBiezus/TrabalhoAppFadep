package fadep.android.pos.trabalhoapp.WS;

import java.io.Serializable;

/**
 * Created by Jean on 06/04/2018.
 */

public class Login implements Serializable {
    public  int id;
    public String login;
    public String senha;
    public Long idUsuario;
    public Boolean adm;
    private String dataCadastro;
    private String dataAlteracao;
    private Long versao;
    private Boolean deletado = false;

    public Login() {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.idUsuario = idUsuario;
        this.adm = adm;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Boolean getAdm() {
        return adm;
    }

    public void setAdm(Boolean adm) {
        this.adm = adm;
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
