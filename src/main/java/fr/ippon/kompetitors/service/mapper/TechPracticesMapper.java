package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.TechPractices;
import fr.ippon.kompetitors.service.dto.TechPracticesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link TechPractices} and its DTO {@link TechPracticesDTO}.
 */
@Mapper(componentModel = "spring", uses = {ListPracticesMapper.class, CompetitorMapper.class})
public interface TechPracticesMapper extends EntityMapper<TechPracticesDTO, TechPractices> {

    @Mapping(source = "value.id", target = "valueId")
    @Mapping(source = "competitor.id", target = "competitorId")
    TechPracticesDTO toDto(TechPractices techPractices);

    @Mapping(source = "valueId", target = "value")
    @Mapping(source = "competitorId", target = "competitor")
    TechPractices toEntity(TechPracticesDTO techPracticesDTO);

    default TechPractices fromId(Long id) {
        if (id == null) {
            return null;
        }
        TechPractices techPractices = new TechPractices();
        techPractices.setId(id);
        return techPractices;
    }
}
