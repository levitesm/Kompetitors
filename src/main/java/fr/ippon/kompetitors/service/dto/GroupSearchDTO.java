package fr.ippon.kompetitors.service.dto;

public interface GroupSearchDTO {

    Long getId();

    byte[] getLogo();

    String getLogocontenttype();

    String getName();

    Double getRevenue();

    Double getGlassdoor();

    Double getViadeo();

    String getCountryname();

    Boolean getTransparency();

    Long getMaxcompetitorid();

    Integer getCompetitorscount();

    Integer getAgencies();

    Integer getEmployees();

    Integer getRegionagencies();

    Integer getRegionemployees();

    Boolean getReference();

    Boolean getListed();

    Boolean getIndependent();

    Boolean getPrivatecapital();

    Double getTotalrate();

    Double getTechrate();

}
