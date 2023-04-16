package pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PokemonPojo {
    private int count;
    private String next;
    private String previous;
    private List<PokemonResultPojo> results;


}
