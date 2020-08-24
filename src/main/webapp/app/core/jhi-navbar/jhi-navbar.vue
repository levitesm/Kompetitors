<template>
    <div>
        <div>
            <b-navbar toggleable="xl" type="dark" variant="info">

                <b-navbar-brand class="home" b-link to="/results">
                    <img class="navbar-logo" src="../../../content/images/logo1.svg">
                    <span  class="navbar-title">KOMPETITOR</span>
                </b-navbar-brand>

                <b-navbar-toggle target="nav-collapse"/>

                <b-collapse id="nav-collapse" is-nav>
                    <b-navbar-nav class="ml-auto">
                        <b-nav-item active-class="active" to="/results" exact v-if="authenticated" >
                            <span @click="hideSearch()">{{ $t('navbar.all-competitors') }}</span>
                        </b-nav-item>
                        <b-nav-item active-class="active" :to="referenceCompetitorPath" exact v-if="authenticated && referenceCompetitorPath">
                                <span>{{ $t('navbar.ippon-profile') }}</span>
                        </b-nav-item>
                        <b-nav-item active-class="active" :to="referenceCompetitorDashboard" exact v-if="authenticated && referenceCompetitorDashboard" >
                            <span>{{ $t('navbar.dashboard') }}</span>
                        </b-nav-item>
                        <b-nav-item active-class="active" exact v-if="authenticated" >
                            <div class="add" @click="addCompetitor()">
                                <span class="icon-add"/>
                                {{ $t('navbar.competitor.add') }}
                            </div>
                        </b-nav-item>
                        <b-nav-item exact v-if="authenticated" @click="showSearch = !showSearch">
                            <span class="icon-search search-icon"></span>
                            <span class="search-text">{{ $t('navbar.search') }}</span>
                        </b-nav-item>
                        <b-nav-item-dropdown
                            id="entity-menu"
                            v-if="hasAnyAuthority('ROLE_ADMIN')"
                            :class="{'router-link-active': subIsActive('/entity')}"
                            active-class="active" class="pointer">
                            <span slot="button-content" class="navbar-dropdown-menu admin-menu">
                                <font-awesome-icon icon="th-list" />
                                <span v-text="$t('global.menu.entities.main')">Entities</span>
                            </span>
                            <b-dropdown-item to="/entity/clients">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.clients')">Clients</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/clients-projects">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.clientsProjects')">ClientsProjects</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/competitive-rates">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.competitiveRates')">CompetitiveRates</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/competitors">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.competitors')">Competitors</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/dialogs">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.dialogs')">Dialogs</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/finance">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.finance')">Finance</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/global-groups">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.globalGroups')">GlobalGroups</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/hr-info">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.hrInfo')">HrInfo</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/legal">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.legal')">Legal</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/list-activities">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.listActivities')">ListActivities</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/list-cities">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.listCities')">ListCities</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/list-city-countries">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.listCityCountries')">ListCityCountries</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/list-clients-project-types">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.listClientsProjectTypes')">ListClientsProjectTypes</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/list-competancies">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.listCompetancies')">ListCompetancies</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/list-countries">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.listCountries')">ListCountries</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/list-industries">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.listIndustries')">ListIndustries</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/list-ownerships">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.listOwnerships')">ListOwnerships</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/list-pricings">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.listPricings')">ListPricings</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/list-project-types">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.listProjectTypes')">ListProjectTypes</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/list-services">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.listServices')">ListServices</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/list-tech-partners">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.listTechPartners')">ListTechPartners</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/list-tools">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.listTools')">ListTools</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/offices">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.offices')">Offices</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/people">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.people')">People</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/pr-info">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.prInfo')">PrInfo</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/societe-main">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.societeMain')">SocieteMain</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/tech-competancies">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.techCompetancies')">TechCompetancies</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/tech-partners">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.techPartners')">TechPartners</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/tech-projects">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.techProjects')">TechProjects</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/tech-services">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.techServices')">TechServices</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/tech-tools">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.techTools')">TechTools</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/user-group-rights">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.userGroupRights')">UserGroupRights</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/access-key">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.accessKey')">AccessKey</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/competitors">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.competitors')">Competitors</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/representatives">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.representatives')">Representatives</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/annual-account">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.annualAccount')">AnnualAccount</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/annual-account-statistics">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.annualAccountStatistics')">AnnualAccountStatistics</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/capital">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.capital')">Capital</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/share-holders">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.shareHolders')">ShareHolders</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/updatehistory">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.updatehistory')">Updatehistory</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/employee-role">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.employeeRole')">EmployeeRole</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/employee-pricing">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.employeePricing')">EmployeePricing</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/region-list">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.regionList')">RegionList</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/region-zip-list">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.regionZipList')">RegionZipList</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/competitor-industry">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.competitorIndustry')">CompetitorIndustry</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/dashboard-finance">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.dashboardFinance')">DashboardFinance</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/tech-info">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.techInfo')">TechInfo</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/workforce">
                                <font-awesome-icon icon="asterisk" />
                                <span v-text="$t('global.menu.entities.workforce')">Workforce</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/entity/employee-type">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.employeeType')">EmployeeType</span>
                    </b-dropdown-item>
                            <b-dropdown-item to="/entity/employees-typology">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.employeesTypology')">EmployeesTypology</span>
                    </b-dropdown-item>
                            <b-dropdown-item to="/entity/external-urls">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.externalUrls')">ExternalUrls</span>
                    </b-dropdown-item>
                            <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
                        </b-nav-item-dropdown>
                        <b-nav-item-dropdown
                            id="admin-menu"
                            v-if="hasAnyAuthority('ROLE_ADMIN')"
                            :class="{'router-link-active': subIsActive('/admin')}"
                            active-class="active"
                            class="pointer admin-menu">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon icon="user-plus" />
                        <span v-text="$t('global.menu.admin.main')">Administration</span>
                    </span>
                            <b-dropdown-item  to="/admin/jhi-metrics">
                                <font-awesome-icon icon="tachometer-alt" />
                                <span v-text="$t('global.menu.admin.metrics')">Metrics</span>
                            </b-dropdown-item>
                            <b-dropdown-item to="/admin/jhi-health">
                                <font-awesome-icon icon="heart" />
                                <span v-text="$t('global.menu.admin.health')">Health</span>
                            </b-dropdown-item>
                            <b-dropdown-item  to="/admin/jhi-configuration">
                                <font-awesome-icon icon="list" />
                                <span v-text="$t('global.menu.admin.configuration')">Configuration</span>
                            </b-dropdown-item>
                            <b-dropdown-item  to="/admin/audits">
                                <font-awesome-icon icon="bell" />
                                <span v-text="$t('global.menu.admin.audits')">Audits</span>
                            </b-dropdown-item>
                            <b-dropdown-item  to="/admin/logs">
                                <font-awesome-icon icon="tasks" />
                                <span v-text="$t('global.menu.admin.logs')">Logs</span>
                            </b-dropdown-item>
                            <b-dropdown-item v-if="swaggerEnabled"  to="/admin/docs">
                                <font-awesome-icon icon="book" />
                                <span v-text="$t('global.menu.admin.apidocs')">API</span>
                            </b-dropdown-item>
                        </b-nav-item-dropdown>
                        <b-nav-item-dropdown
                            right
                            href="javascript:void(0);"
                            id="account-menu"
                            :class="{'router-link-active': subIsActive('/account')}"
                            active-class="active"
                            class="pointer pointerX">
                            <span slot="button-content" class="navbar-dropdown-menu">
                                <span class="icon-profile user-icon" ></span>
                                <span class="user-text">{{ username ? username : ''}}</span>
                            </span>
                            <b-dropdown-item v-if="authenticated"  v-on:click="logout()" id="logout">
                                <font-awesome-icon icon="sign-out-alt" class="drop-menu-item" />
                                <span v-text="$t('global.menu.account.logout')" class="drop-menu-item">Sign out</span>
                            </b-dropdown-item>
                            <b-dropdown-item v-if="!authenticated"  v-on:click="openLogin()" id="login">
                                <font-awesome-icon icon="sign-in-alt" class="drop-menu-item" />
                                <span v-text="$t('global.menu.account.login')" class="drop-menu-item">Sign in</span>
                            </b-dropdown-item>
                        </b-nav-item-dropdown>
                    </b-navbar-nav>
                </b-collapse>
            </b-navbar>
        </div>
        <div v-show="showSearch">
            <jhi-search></jhi-search>
        </div>
    </div>
</template>

<script lang="ts" src="./jhi-navbar.component.ts">
</script>

<style scoped>
.navbar {
    padding: 0em 1.5em;
    min-height: 80px;
}
.navbar-title {
    color: white;
    font-weight: 600;
    font-size: 14px!important;
    margin-left: 20px;
}
.home{
    cursor: pointer !important;
}
.drop-menu-item {
    color: #6b48ff;
}
.nav-item {
    white-space: nowrap;
}

/* When navbar is not collapsed */
@media only screen and (min-width : 1200px) {
    .navbar-nav {
        align-items: center;
    }
    .nav-item {
        margin-left: 1.5rem;
    }
    .nav-link.active {
        border-bottom: 3px solid #1ee3cf;
        line-height: 80px;
    }
    .add {
        background-color: #6b48ff;
        padding: 0.8em 1em;
        border-radius: 2em;
        color: #ffffff;
        border: 2px solid #a7a7a7;
        margin-left: 2.5em;
        margin-right: 0!important;
        font-size: 14px !important;
        font-weight: 700 !important;
    }
    .add:hover {
        border-color: #ccc;
    }
    .user-icon,
    .search-icon {
        font-size: 24px;
    }
    .user-text {
        margin-left: 7px !important;
        margin-top: 4px;
        display: inline-block;
        vertical-align: top;
        font-size: 14px;
    }
    .search-text {
        display: none;
    }
}

.admin-menu {
    margin: 0;
}
/* When navbar is collapsed */
@media only screen and (max-width : 1199px) {
    .navbar {
        padding: 1em 1.5em;
        min-height: 80px;
    }
    .nav-link.active span {
        border-left: 3px solid #1ee3cf;
        padding-left: 4px;
    }
    .user-icon,
    .search-icon {
        vertical-align: middle;
    }
}

</style>
