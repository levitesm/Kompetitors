package fr.ippon.kompetitors.service.dto;

public interface OfficeSearchDTO {
    Long getId();
    String getCompetitorname();
    String getGroupname();
    String getCountry();
    String getCity();
    String getPost();
    String getAddress();
    String getRegionzip();
    Double getLatitude();
    Double getLongitude();
}
