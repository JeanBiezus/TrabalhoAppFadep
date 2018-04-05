package fadep.android.pos.trabalhoapp.WS;

import java.io.Serializable;

/**
 * Created by Jean on 04/04/2018.
 */

public class ImagemProduto implements Serializable {

    private int id;
    private int idProduto;
    private String imagem;
    private String dataCadastro;
    private String dataAlteracao;
    private Long versao;
    private Boolean deletado = false;

    public ImagemProduto() {
        this.id = id;
        this.idProduto = idProduto;
        this.imagem = imagem;
        this.dataCadastro = dataCadastro;
        this.dataAlteracao = dataAlteracao;
        this.versao = versao;
        this.deletado = deletado;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
