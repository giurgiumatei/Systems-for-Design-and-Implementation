package ro.ubb.remoting.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ro.ubb.remoting.common.utils.Pair;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class Rental extends BaseEntity<Long> {

    private int price;
    private long clientId;
    private long gunTypeId;

    public Rental( long clientId, long gunTypeId, int price)
    {
        super();
        this.clientId = clientId;
        this.gunTypeId = gunTypeId;
        this.price = price;

    }

    public Rental(long id, long clientId, long gunTypeId, int price)
    {
        super();
        super.setId(id);
        this.clientId = clientId;
        this.gunTypeId = gunTypeId;
        this.price = price;
    }


    public Rental(long id, int price) {
        super();
        super.setId(id);
        this.price = price;
    }
}
