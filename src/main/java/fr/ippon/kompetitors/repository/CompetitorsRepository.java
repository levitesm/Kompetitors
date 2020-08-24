package fr.ippon.kompetitors.repository;

import fr.ippon.kompetitors.domain.Competitors;
import fr.ippon.kompetitors.service.dto.FillFlagsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Competitors entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitorsRepository extends JpaRepository<Competitors, Long> {

    @SuppressWarnings("all")
    @Query(value =
        "with cids (id) as (" +
//          Not Needed when we only have France and its regions
//            "select c.id from competitors c " +
//            "inner join list_countries countries on countries.id = c.country_id and lower(countries.value) like %?2% " +
//            "union " +
            "select distinct offices.competitors_id from offices where lower(offices.city_as_text) like %?2% or lower(offices.post) like %?2% or lower(offices.address) like %?2%" +
            ")" +
        "select * from competitors c " +
            "inner join cids on cids.id = c.id " +
            "inner join global_groups on global_groups.id = c.global_groups_id " +
            "where lower(global_groups.name) like %?1%",
        nativeQuery = true)
    List<Competitors> findByWhatAndWhere(String what, String where);

    @Query(value =
        "with cids (id) as (" +
            "select distinct offices.competitors_id from offices " +
            "where (SUBSTRING(offices.post,0,2) in (select zip from region_zip_list where region = ?3) " +
            "OR SUBSTRING(offices.post,0,3) in (select zip from region_zip_list where region = ?3)) AND " +
            "(lower(offices.city_as_text) like %?2% or lower(offices.post) like %?2% or lower(offices.address) like %?2%)" +
            ")" +
            "select * from competitors c " +
            "inner join cids on cids.id = c.id " +
            "inner join global_groups on global_groups.id = c.global_groups_id " +
            "where lower(global_groups.name) like %?1%",
        nativeQuery = true)
    List<Competitors> findByWhatAndWhereAndRegion(String what, String where, String region);

    List<Competitors> findByGlobalGroupsId(Long groupId);

    Competitors findTopByGlobalGroupsReferenceIsTrueOrderById();

    List<Competitors> findByName(String name);

    @Query(value =
        "WITH infgr(num) as (SELECT count(*)>0 FROM infogreffe WHERE competitor_id = ?1),\n" +
            "     reps(num) as (SELECT count(*)>0 FROM representatives WHERE competitor_siren = ?2),\n" +
            "     shar(num) as (SELECT count(*)>0 FROM share_holders WHERE competitor_siren = ?2),\n" +
            "     legal(num) as (SELECT (CAST(infgr.num AS int) + CAST(reps.num AS int) + CAST(shar.num AS int))  FROM infgr, reps, shar),\n" +
            "     fin(num) as (SELECT count(*)>0 FROM annual_account WHERE siren = ?2),\n" +
            "     head_fin(num) as (SELECT count(*)>0 FROM people WHERE competitors_id = ?1 AND title='HEAD_FINANCE' AND f_name != ''),\n" +
            "     finance(num) as (SELECT (CAST(fin.num AS int) + CAST(head_fin.num AS int))  FROM fin, head_fin),\n" +
            "     client(num) as (SELECT count(*)>0 FROM clients WHERE offices_id IN (SELECT id FROM offices WHERE competitors_id = ?1)),\n" +
            "     pricing(num) as (SELECT count(*)>0 FROM employee_pricing WHERE competitor_id = ?1),\n" +
            "     head_sales(num) as (SELECT count(*)>0 FROM people WHERE competitors_id = ?1 AND title='HEAD_SALES' AND f_name != ''),\n" +
            "     clients(num) as (SELECT (CAST(client.num AS int) + CAST(head_sales.num AS int) + CAST(pricing.num AS int))  FROM client, head_sales, pricing),\n" +
            "     tech_c(num) as (SELECT count(*)>0 FROM tech_competancies WHERE competitor_id = ?1),\n" +
            "     tech_s(num) as (SELECT count(*)>0 FROM tech_services WHERE competitor_id = ?1),\n" +
            "     tech_to(num) as (SELECT count(*)>0 FROM tech_tools WHERE competitor_id = ?1),\n" +
            "     tech_p(num) as (SELECT count(*)>0 FROM tech_partners WHERE competitor_id = ?1),\n" +
            "     tech_pr(num) as (SELECT count(*)>0 FROM tech_projects WHERE competitor_id = ?1),\n" +
            "     head_tech(num) as (SELECT count(*)>0 FROM people WHERE competitors_id = ?1 AND title='CTO' AND f_name != ''),\n" +
            "     technologies(num) as (SELECT (CAST(tech_c.num AS int) + CAST(tech_s.num AS int) + CAST(tech_to.num AS int) + CAST(tech_p.num AS int) + CAST(tech_pr.num AS int) + CAST(head_tech.num AS int))  FROM tech_c,tech_s, tech_p, tech_pr, tech_to, head_tech),\n" +
            "     head_hr(num) as (SELECT count(*)>0 FROM people WHERE competitors_id = ?1 AND title='HEAD_HR' AND f_name != ''),\n" +
            "     head_recr(num) as (SELECT count(*)>0 FROM people WHERE competitors_id = ?1 AND title='HEAD_RECR' AND f_name != ''),\n" +
            "     hr_info(num) as (SELECT count(*)>0 FROM hr_info WHERE competitor_id = ?1 AND interviews_number>0 AND recruiters_number>0 AND glassdoor_rate>0 AND viadeo_rate>0),\n" +
            "     typology(num) as (SELECT count(*)>0 FROM employees_typology WHERE competitor_id = ?1),\n" +
            "     hr(num) as (SELECT (CAST(hr_info.num AS int) + CAST(head_hr.num AS int) + CAST(head_recr.num AS int) + CAST(typology.num AS int))  FROM hr_info, head_recr, head_hr, typology),\n" +
            "     prurl(num) as (SELECT (CAST(((twitter_url notnull AND twitter_url != '') OR (facebook_url notnull AND facebook_url != '') OR (instagram_url notnull AND instagram_url != '') OR (linkedin_url notnull AND linkedin_url != '') OR (youtube_url notnull AND youtube_url != '') OR (github_url notnull AND github_url != '') OR (blog_feed notnull AND blog_feed != '') OR (google_alerts_feed notnull AND google_alerts_feed != '')) AS INT) +\n" +
            "                                 CAST(((twitter_url notnull AND twitter_url != '') AND (facebook_url notnull AND facebook_url != '') AND (instagram_url notnull AND instagram_url != '') AND (linkedin_url notnull AND linkedin_url != '') AND (youtube_url notnull AND youtube_url != '') AND (github_url notnull AND github_url != '') AND (blog_feed notnull AND blog_feed != '') AND (google_alerts_feed notnull AND google_alerts_feed != '')) AS INT)) \n" +
            "     FROM external_urls WHERE competitor_id = ?1),\n" +
            "     prinfo(num) as (SELECT count(*)>0 FROM pr_info WHERE competitors_id = ?1),\n" +
            "     pr(num) as (SELECT (CAST(prurl.num AS int) + CAST(prinfo.num AS int)) FROM prurl, prinfo)\n" +
            "SELECT\n" +
            "       ?1,\n" +
            "       CASE WHEN legal.num = 0 THEN 0 WHEN legal.num = 3 THEN 2 ELSE 1 END AS legal,\n" +
            "       finance.num as finance,\n" +
            "       CASE WHEN clients.num = 0 THEN 0 WHEN clients.num = 3 THEN 2 ELSE 1 END AS clients,\n" +
            "       CASE WHEN technologies.num = 0 THEN 0 WHEN technologies.num = 6 THEN 2 ELSE 1 END AS technologies,\n" +
            "       CASE WHEN hr.num = 0 THEN 0 WHEN hr.num = 4 THEN 2 ELSE 1 END AS hr,\n" +
            "       CASE WHEN pr.num = 0 THEN 0 WHEN pr.num = 3 THEN 2 ELSE 1 END AS pr \n" +
            "FROM legal, finance, clients, technologies, hr, pr",
        nativeQuery = true)
    FillFlagsDTO getFillFlags(Long id, String siren);
}
