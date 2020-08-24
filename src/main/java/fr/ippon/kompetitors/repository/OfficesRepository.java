package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.Offices;
import fr.ippon.kompetitors.service.dto.OfficeSearchDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Offices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfficesRepository extends JpaRepository<Offices, Long> {

    @Query(
        value = "WITH zips (zip) AS ( \n" +
            "    SELECT zip FROM region_zip_list WHERE region = ?3 \n" +
            "    ) SELECT o.id AS id, c.name AS competitorname, gg.name AS groupname, lc.value AS country, o.city_as_text AS city, o.post AS post, o.address AS address, \n" +
            "             zips.zip AS regionzip, o.latitude AS latitude, o.longitude AS longitude \n" +
            "        FROM offices AS o \n" +
            "        LEFT JOIN competitors AS c on o.competitors_id = c.id \n" +
            "        LEFT JOIN global_groups AS gg ON c.global_groups_id = gg.id \n" +
            "        LEFT JOIN list_countries AS lc ON c.country_id = lc.id \n" +
            "        LEFT JOIN zips ON substr(o.post, 0, 2) = zips.zip OR substr(o.post, 0, 3) = zips.zip \n" +
            "        WHERE (zips.zip IS NOT NULL OR ?3 = '') AND (lower(gg.name) LIKE %?1% OR lower(c.name) LIKE %?1% ) AND lower(o.city_as_text) LIKE %?2% AND lc.value LIKE %?4% ",
        nativeQuery = true
    )
    List<OfficeSearchDTO> findByNameAndCityAndRegionAndCountry(String competitorName, String city, String region, String country);

    List<Offices> findAllByLatitudeIsNullAndLongitudeIsNull();
}
