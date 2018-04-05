package fadep.android.pos.trabalhoapp;

/**
 * Created by jean on 04/04/18.
 */

public class ProdutoImagem {
    private int id;
    private String imagem;
    private int idPublicacao;

    public ProdutoImagem(String imagem, int idPublicacao) {
        this.imagem = imagem;
        this.idPublicacao = idPublicacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getIdPublicacao() {
        return idPublicacao;
    }

    public void setIdPublicacao(int idPublicacao) {
        this.idPublicacao = idPublicacao;
    }
}
