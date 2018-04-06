package fadep.android.pos.trabalhoapp.WS;

/**
 * Created by Jean on 02/04/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PordutoRetrofitModel implements Serializable {

    private int id;
    private String nome;
    private Double preco;
    private String descricao;
    private String dataCadastro;
    private String dataAlteracao;
    private Long versao;
    private Boolean deletado = false;
    private List<ImagemProduto> produtoImagem;

    public List<ImagemProduto> getImagens() {
        return produtoImagem;
    }

    public void setImagens(List<ImagemProduto> imagens) {
        this.produtoImagem = imagens;
    }

    public PordutoRetrofitModel() {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.dataAlteracao = dataAlteracao;
        this.dataCadastro = dataCadastro;
        this.deletado = deletado;
        this.versao = versao;
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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

