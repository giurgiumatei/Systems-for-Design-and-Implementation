package ro.ubb.catalog.core.repository;

import org.springframework.data.jpa.repository.Query;
import ro.ubb.catalog.core.model.GunType;
import ro.ubb.catalog.core.model.Rental;

public interface RentalRepository extends Repository<Rental, Long> {

    @Query(value = "select gt from guntype gt where gt.id in (select r.guntypeid from rental r group by (r.guntypeid) order by count(r) desc limit 1)", nativeQuery = true)
    GunType getMostRentedGunType();

    //org.springframework.orm.hibernate5.HibernateSystemException: No Dialect mapping for JDBC type: 2002
}
