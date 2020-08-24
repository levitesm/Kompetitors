package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.TechCompetanciesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TechCompetancies} and its DTO {@link TechCompetanciesDTO}.
 */
@Mapper(componentModel = "spring", uses = {ListCompetanciesMapper.class, CompetitorMapper.class})
public interface TechCompetanciesMapper extends EntityMapper<TechCompetanciesDTO, TechCompetancies> {

    @Mapping(source = "value.id", target = "valueId")
    @Mapping(source = "competitor.id", target = "competitorId")
    TechCompetanciesDTO toDto(TechCompetancies techCompetancies);

    @Mapping(source = "valueId", target = "value")
    @Mapping(source = "competitorId", target = "competitor")
    TechCompetancies toEntity(TechCompetanciesDTO techCompetanciesDTO);

    default TechCompetancies fromId(Long id) {
        if (id == null) {
            return null;
        }
        TechCompetancies techCompetancies = new TechCompetancies();
        techCompetancies.setId(id);
        return techCompetancies;
    }
}
