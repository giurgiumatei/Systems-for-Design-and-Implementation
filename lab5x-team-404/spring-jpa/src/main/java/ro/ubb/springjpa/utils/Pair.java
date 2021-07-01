package ro.ubb.springjpa.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Pair<A, B> implements Serializable {
    private A first;
    private B second;

    public Pair(A first, B second) {
        super();
        this.first = first;
        this.second = second;
    }

    public String toString()
    {
        return "(" + first + "," + second + ")";
    }
}