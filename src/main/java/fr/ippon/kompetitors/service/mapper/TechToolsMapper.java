package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.TechToolsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TechTools} and its DTO {@link TechToolsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ListToolsMapper.class, CompetitorMapper.class})
public interface TechToolsMapper extends EntityMapper<TechToolsDTO, TechTools> {

    @Mapping(source = "value.id", target = "valueId")
    @Mapping(source = "competitor.id", target = "competitorId")
    TechToolsDTO toDto(TechTools techTools);

    @Mapping(source = "valueId", target = "value")
    @Mapping(source = "competitorId", target = "competitor")
    TechTools toEntity(TechToolsDTO techToolsDTO);

    default TechTools fromId(Long id) {
        if (id == null) {
            return null;
        }
        TechTools techTools = new TechTools();
        techTools.setId(id);
        return techTools;
    }
}
