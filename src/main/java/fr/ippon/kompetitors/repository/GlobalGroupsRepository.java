package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.GlobalGroups;
import fr.ippon.kompetitors.service.dto.GroupSearchDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the GlobalGroups entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GlobalGroupsRepository extends JpaRepository<GlobalGroups, Long> {


    @Query(
        value = "WITH fin_last_year(competitor_id, year) as (\n" +
            "    select c.id, coalesce(max(f.year), 0) as year\n" +
            "    from competitors c\n" +
            "             inner join finance f on c.id = f.competitors_id\n" +
            "    group by c.id\n" +
            "\n" +
            "), fin_revenue(competitor_id, revenue) as (\n" +
            "    select c.id, f.revenue as revenue\n" +
            "    from competitors c\n" +
            "        inner join fin_last_year on c.id = fin_last_year.competitor_id\n" +
            "        inner join finance f on c.id = f.competitors_id\n" +
            "            and f.year = fin_last_year.year\n" +
            "\n" +
            "), revenue(group_id, competitor_id, revenue) as (\n" +
            "    select c.global_groups_id, c.id, coalesce(f.revenue, cast(i.ca_1 as double precision), 0) as revenue\n" +
            "    from competitors c\n" +
            "             left join fin_revenue f on c.id = f.competitor_id\n" +
            "             left join infogreffe i on c.id = i.competitor_id\n" +
            "\n" +
            "), max_group_revenue(group_id, revenue) as (\n" +
            "    select c.global_groups_id, max(revenue) as revenue\n" +
            "    from competitors c\n" +
            "        left join revenue on revenue.competitor_id = c.id\n" +
            "    group by c.global_groups_id\n" +
            "\n" +
            "), max_revenue_competitor(group_id, competitor_id, country_id, infogreffe, listed, independent, private_capital) as (\n" +
            "    select DISTINCT ON (c.global_groups_id) c.global_groups_id, c.id, c.country_id, i2.competitor_id, coalesce(cap.listed, false), coalesce(cap.independent_c, false), coalesce(cap.private_capital, false)\n" +
            "    from max_group_revenue mgr\n" +
            "        join revenue on revenue.group_id = mgr.group_id and revenue.revenue = mgr.revenue\n" +
            "        join competitors c on revenue.competitor_id = c.id\n" +
            "        left join infogreffe i2 on c.id = i2.competitor_id\n" +
            "        left join legal l on c.id = l.competitor_id\n" +
            "        left join capital cap on cap.competitor_siren = l.siren\n" +
            "), zips (zip) as (\n" +
            "    select zip\n" +
            "    from region_zip_list\n" +
            "    where region = ?3\n" +
            "), all_agencies(global_groups_id, agencies, employees) as (\n" +
            "    select c.global_groups_id, count(o.id), sum(o.number_employees)\n" +
            "    from offices o\n" +
            "    join competitors c on o.competitors_id = c.id" +
            "    group by c.global_groups_id\n" +
            "), region_agencies(global_groups_id, agencies, employees) as (\n" +
            "    select c.global_groups_id, count(o.id), sum(o.number_employees)\n" +
            "    from offices o\n" +
            "    left join zips on substr(o.post, 0, 2) = zips.zip or substr(o.post, 0, 3) = zips.zip\n" +
            "    join competitors c on o.competitors_id = c.id" +
            "    where (zips.zip is not null or ?3 = '')\n" +
            "    group by c.global_groups_id\n" +
            "), all_comps(group_id, competitors) as (\n" +
            "select global_groups_id, count(id)" +
            "from competitors group by global_groups_id\n" +
            "), agencies(competitor_id) as (\n" +
            "    select o.competitors_id\n" +
            "    from offices o\n" +
            "        left join zips on substr(o.post, 0, 2) = zips.zip or substr(o.post, 0, 3) = zips.zip\n" +
            "        join competitors c on c.id = o.competitors_id" +
            "        join list_countries lc on c.country_id = lc.id" +
            "    where (lower(o.city_as_text) like %?2% or lower(o.address) like %?2%)\n" +
            "      and (zips.zip is not null or ?3 = '') and lc.value like %?4%\n" +
            "    group by competitors_id\n" +
            ")\n" +
            "SELECT g.id as id, g.logo as logo, g.logo_content_type as logocontenttype, " +
            "       g.name as \"name\", sum(revenue.revenue) as revenue, coalesce(hr_info.glassdoor_rate, 0) as glassdoor, coalesce(hr_info.viadeo_rate, 0) as viadeo,\n" +
            "       lc.value as countryname,\n" +
            "       case when max_comp.infogreffe is null then false else true end as transparency,\n" +
            "       max_comp.competitor_id as maxcompetitorid,\n" +
            "       alc.competitors as competitorscount,\n" +
            "       ala.agencies as agencies, ala.employees as employees," +
            "       rga.agencies as regionagencies, rga.employees as regionemployees," +
            "       cr.total_rate as totalrate, cr.tech_rate as techrate," +
            "       coalesce(g.reference, false) as reference, max_comp.listed as listed, max_comp.independent as independent, max_comp.private_capital as privatecapital\n" +
            "FROM global_groups g\n" +
            "    join competitors c on g.id = c.global_groups_id\n" +
            "    join revenue on c.id = revenue.competitor_id\n" +
            "    left join max_revenue_competitor max_comp on max_comp.group_id = g.id\n" +
            "    left join hr_info on max_comp.competitor_id = hr_info.competitor_id\n" +
            "    left join list_countries lc on max_comp.country_id = lc.id\n" +
            "    left join all_comps alc on alc.group_id = g.id\n" +
            "    left join all_agencies ala on ala.global_groups_id = g.id\n" +
            "    left join region_agencies rga on rga.global_groups_id = g.id\n" +
            "    join agencies a on a.competitor_id = c.id\n" +
            "    join competitive_rates cr on cr.competitor_id = max_comp.competitor_id\n" +
            "where lower(g.name) like %?1%\n" +
            "group by g.id, g.name, max_comp.infogreffe, lc.value, max_comp.competitor_id, max_comp.listed, max_comp.independent, max_comp.private_capital, ala.agencies , ala.employees, rga.agencies , rga.employees, alc.competitors, hr_info.glassdoor_rate, hr_info.viadeo_rate, cr.total_rate, cr.tech_rate\n" +
            "order by g.name\n",
        nativeQuery = true
    )
    List<GroupSearchDTO> findByWhatAndWhereAndRegion(String what, String where, String region, String country);
}
