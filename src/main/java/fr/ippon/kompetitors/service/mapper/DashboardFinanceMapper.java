package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.DashboardFinanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DashboardFinance} and its DTO {@link DashboardFinanceDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompetitorMapper.class})
public interface DashboardFinanceMapper extends EntityMapper<DashboardFinanceDTO, DashboardFinance> {

    @Mapping(source = "competitor.id", target = "competitorId")
    @Mapping(source = "competitor.name", target = "competitorName")
    @Mapping(source = "competitor.globalGroups.logo", target = "groupLogo")
    @Mapping(source = "competitor.globalGroups.logoContentType", target = "groupLogoContentType")
    DashboardFinanceDTO toDto(DashboardFinance dashboardFinance);

    @Mapping(source = "competitorId", target = "competitor")
    DashboardFinance toEntity(DashboardFinanceDTO dashboardFinanceDTO);

    default DashboardFinance fromId(Long id) {
        if (id == null) {
            return null;
        }
        DashboardFinance dashboardFinance = new DashboardFinance();
        dashboardFinance.setId(id);
        return dashboardFinance;
    }
}
