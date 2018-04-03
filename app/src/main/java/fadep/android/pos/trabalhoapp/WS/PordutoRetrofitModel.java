package fadep.android.pos.trabalhoapp.WS;

/**
 * Created by Jean on 02/04/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PordutoRetrofitModel {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("nome")
    @Expose
    private String nome;

    @SerializedName("valor")
    @Expose
    private Double valor;

    @SerializedName("descricao")
    @Expose
    private String descricao;

    @SerializedName("imagem1")
    @Expose
    private String imagem1;

    @SerializedName("imagem1")
    @Expose
    private String imagem2;

    @SerializedName("imagem1")
    @Expose
    private String imagem3;


    public String getNome() {
        return nome;
    }

    public void setNome(String value) {
        this.nome = value;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String value) {
        this.descricao = value;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double value) {
        this.valor = value;
    }
    public String getImagem1() {
        return imagem1;
    }

    public void setImagem1(String value) {
        this.imagem1 = value;
    }
    public String getImagem2() {
        return imagem2;
    }

    public void setImagem2(String value) {
        this.imagem2 = value;
    }
    public String getImagem3() {
        return imagem3;
    }

    public void setImagem3(String value) {
        this.imagem3 = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + nome + '\'' +
                ", body='" + descricao + '\'' +
                ", userId=" + valor +
                ", id=" + id +
                '}';
    }
}

