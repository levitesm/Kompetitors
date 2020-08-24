package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.TechProjectsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TechProjects} and its DTO {@link TechProjectsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ListProjectTypesMapper.class, CompetitorMapper.class})
public interface TechProjectsMapper extends EntityMapper<TechProjectsDTO, TechProjects> {

    @Mapping(source = "value.id", target = "valueId")
    @Mapping(source = "competitor.id", target = "competitorId")
    TechProjectsDTO toDto(TechProjects techProjects);

    @Mapping(source = "valueId", target = "value")
    @Mapping(source = "competitorId", target = "competitor")
    TechProjects toEntity(TechProjectsDTO techProjectsDTO);

    default TechProjects fromId(Long id) {
        if (id == null) {
            return null;
        }
        TechProjects techProjects = new TechProjects();
        techProjects.setId(id);
        return techProjects;
    }
}
