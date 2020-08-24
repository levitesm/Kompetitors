package fr.ippon.kompetitors.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import fr.ippon.kompetitors.domain.Offices;
import fr.ippon.kompetitors.repository.OfficesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoCodingService {

    private final Logger log = LoggerFactory.getLogger(GeoCodingService.class);

    @Value("${google-geocoding-api-key}")
    private String apiKey;

    private final OfficesRepository officesRepository;

    public GeoCodingService(OfficesRepository officesRepository) {
        this.officesRepository = officesRepository;
    }

    public Offices getCoordinates(Offices office) {
        GeoApiContext context = new GeoApiContext.Builder()
            .apiKey(apiKey)
            .build();
        try {
            String fillAddress = (office.getCityAsText() != null ? office.getCityAsText() + " " : "") + (office.getAddress() != null ? office.getAddress() : "");
            GeocodingResult[] results = GeocodingApi.geocode(context, fillAddress).await();
            if (results.length > 0) {
                office.setLatitude(results[0].geometry.location.lat);
                office.setLongitude(results[0].geometry.location.lng);
                log.info("Google geocoding API call success! Address: {}, geometry: {}",
                    fillAddress,
                    results[0].geometry);
            } else {
                log.info("Google geocoding data not found for address: {}", fillAddress);
            }
        } catch (Exception e) {
            log.error("Google geocoding API call failed!", e);
        } finally {
            context.shutdown();
        }
        return office;
    }

    public void updateEmptyCoordinates() {
        List<Offices> offices = this.officesRepository.findAllByLatitudeIsNullAndLongitudeIsNull();
        offices.forEach(office -> {
            office = this.getCoordinates(office);
            this.officesRepository.save(office);
        });
    }
}
