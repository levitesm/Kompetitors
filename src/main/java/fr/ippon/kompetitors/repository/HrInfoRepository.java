package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.HrInfo;
import fr.ippon.kompetitors.service.dto.HRDashboardDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the HrInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HrInfoRepository extends JpaRepository<HrInfo, Long> {

    @Query(
        value = "WITH max_years(competitor_id, max_year) AS (SELECT competitor_id, MAX(year) as max_year FROM dashboard_finance WHERE workforce > 0 GROUP BY competitor_id), " +
            "workforce1 AS (SELECT df.competitor_id, df.workforce FROM dashboard_finance df join max_years my on df.competitor_id = my.competitor_id WHERE df.year = my.max_year), " +
            "workforce2 AS (SELECT df.competitor_id, df.workforce FROM dashboard_finance df join max_years my on df.competitor_id = my.competitor_id WHERE df.year = (my.max_year-1)) " +
            "SELECT DISTINCT hr.glassdoor_rate as glassdoor, hr.viadeo_rate as viadeo, ROUND(1.0*(w1.workforce - w2.workforce)/NULLIF(hr.recruiters_number,0),2) as recruitersefficiency, hr.interviews_number as interviewsnumber, hr.recruiters_number as recruitersnumber, c.id as competitorid, c.name as competitorname, g.logo as grouplogo, g.logo_content_type as grouplogocontenttype, hr.cooptation_premium_amount as cooptationpremiumamount, hr.junior_salary as juniorsalary " +
            "FROM competitors c join global_groups g on c.global_groups_id = g.id join hr_info hr on c.id = hr.competitor_id join workforce1 w1 on c.id = w1.competitor_id left join workforce2 w2 on c.id = w2.competitor_id" +
            " WHERE c.id = ?1 ORDER BY competitorname",
        nativeQuery = true
    )
    Optional<HRDashboardDTO> getHRDashboardByCompetitorId(Long id);

    @Query(
        value = "WITH max_years(competitor_id, max_year) AS (SELECT competitor_id, MAX(year) as max_year FROM dashboard_finance WHERE workforce > 0 GROUP BY competitor_id), " +
            "workforce1 AS (SELECT df.competitor_id, df.workforce FROM dashboard_finance df join max_years my on df.competitor_id = my.competitor_id WHERE df.year = my.max_year), " +
            "workforce2 AS (SELECT df.competitor_id, df.workforce FROM dashboard_finance df join max_years my on df.competitor_id = my.competitor_id WHERE df.year = (my.max_year-1)) " +
            "SELECT DISTINCT hr.glassdoor_rate as glassdoor, hr.viadeo_rate as viadeo, ROUND(1.0*(w1.workforce - w2.workforce)/NULLIF(hr.recruiters_number,0),2) as recruitersefficiency, hr.interviews_number as interviewsnumber, hr.recruiters_number as recruitersnumber, c.id as competitorid, c.name as competitorname, g.logo as grouplogo, g.logo_content_type as grouplogocontenttype, hr.cooptation_premium_amount as cooptationpremiumamount, hr.junior_salary as juniorsalary " +
            "FROM competitors c join global_groups g on c.global_groups_id = g.id join hr_info hr on c.id = hr.competitor_id join workforce1 w1 on c.id = w1.competitor_id left join workforce2 w2 on c.id = w2.competitor_id ORDER BY competitorname",
        nativeQuery = true
    )
    List<HRDashboardDTO> getHRDashboardAll();


}
