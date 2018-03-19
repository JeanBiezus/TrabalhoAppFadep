package fadep.android.pos.trabalhoapp.Rom;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Jean on 19/03/2018.
 */
@Dao
public interface ProdutoDAO {

    @Insert
    public void create(Produto produto);

    @Query(" SELECT * FROM produto")
    public List<Produto> read();

    @Update
    public void update(Produto produto);


    @Delete
    public void delete(Produto produto);
}
