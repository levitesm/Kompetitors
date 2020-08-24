package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.TechServicesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TechServices} and its DTO {@link TechServicesDTO}.
 */
@Mapper(componentModel = "spring", uses = {ListServicesMapper.class, CompetitorMapper.class})
public interface TechServicesMapper extends EntityMapper<TechServicesDTO, TechServices> {

    @Mapping(source = "value.id", target = "valueId")
    @Mapping(source = "competitor.id", target = "competitorId")
    TechServicesDTO toDto(TechServices techServices);

    @Mapping(source = "valueId", target = "value")
    @Mapping(source = "competitorId", target = "competitor")
    TechServices toEntity(TechServicesDTO techServicesDTO);

    default TechServices fromId(Long id) {
        if (id == null) {
            return null;
        }
        TechServices techServices = new TechServices();
        techServices.setId(id);
        return techServices;
    }
}
