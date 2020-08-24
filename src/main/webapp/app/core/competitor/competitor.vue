<template>
    <div>
        <div class="phone">
            <div class="mini_dialog">
                <b-form-group>
                    <div class="dia-txt">{{$t('competitor.bottom-menu.dialogs.new-comment') + ' ' + $t('competitor.bottom-menu.dialogs.for')}}:</div>
                    <select class="form-control comp_list_small" id="competitors" name="competitors" v-model="selectedCompetitor" @change="showCompetitor(selectedCompetitor.id)">
                        <option v-bind:value="competitorsOption" v-for="competitorsOption in competitorsInGroup" :key="competitorsOption.id">{{competitorsOption.name}}</option>
                    </select>
                    <b-form-textarea v-model="miniMessage" autofocus></b-form-textarea>
                    <b-button type="submit" variant="primary" class="save" @click="saveMiniDialog">Save</b-button>
                </b-form-group>
            </div>
            <br>
           <div>
               <img src="../../../content/images/turn-phone.png" style="width: 100%; filter: invert()">
           </div>
        </div>

        <div class="competitor">
        <div class="floatback" @click="navigateBack()" v-if="showBack" >
            <div class="icon-back back-ico">
              <span class="backtext">
                Back
              </span>
            </div>
        </div>
        <div class="backbutton" @click="navigateBack()" v-if="!isReference" >
            <div class="icon-back back-ico">
              <span class="backtext">
                {{ $t('competitor.back-button') }}
              </span>
            </div>
        </div>
        <div class="competitor_body" :class="{no_back: isReference}">
            <div class="competitor_upper_part">
                <div class="group_part">
                    <div class="logo_container"><img :src="getLogo()" alt="logo" class="competitor_logo_img" ></div>

                    <div class="group_section1">
                        <div class="competitor_title">{{ groupTitle }}</div>
                    </div>
                    <div class="group_site" :class="{'grey': groupSite.length === 0}">
                        <a v-if="groupSite.length !== 0" :href="groupSite" target="_blank" >
                            {{ $t('competitor.top-main-section.visit-group-website') }}
                        </a>
                        <span v-else>{{ $t('competitor.top-main-section.visit-group-website') }}</span>
                    </div>
                    <hr>
                    <div class="group_section_2">
                        <div class="group_numbers_part">
                            <span class="numbers_titles">{{ $t('competitor.bottom-main-section.revenue') }}</span>
                            <br>
                            <span v-if="groupRevenue > 0" class="numbers_values" >{{ formatNumber(groupRevenue) }}</span>
                            <span class="numbers_values" :class="{'grey': groupRevenue <= 0}" >&#8364;</span>
                        </div>
                        <div class="group_numbers_part ">
                            <span class="numbers_titles">{{ $t('competitor.bottom-main-section.employees') }}</span>
                            <br>
                            <span class="numbers_values" :title="employees">{{ formatNumber(groupEmployees) }}</span>
                        </div>
                        <div class="group_numbers_part ">
                            <span class="numbers_titles">{{ $t('competitor.bottom-main-section.consultants') }}</span>
                            <br>
                            <span class="numbers_values" :title="consultants">{{ formatNumber(groupConsultants) }}</span>
                        </div>
                    </div>
                    <hr>
                    <br>
                    <div class="group_section-3" v-if="competitor && competitor.globalGroups">
                        <div class="companes_number">{{competitor.globalGroups.name}} {{ $t('has') }}
                            <span style="color: #6b48ff">{{competitorsInGroup.length}}</span>
                            {{(competitorsInGroup.length === 1)?$t('company'):$t('companies')}}:
                        </div>
                        <select class="form-control comp_list" id="offices-competitors" name="competitors" v-model="selectedCompetitor" @change="showCompetitor(selectedCompetitor.id)">
                            <option v-bind:value="competitorsOption" v-for="competitorsOption in competitorsInGroup" :key="competitorsOption.id">{{competitorsOption.name}}</option>
                        </select>
                        <div class="competitor__delete" v-if="hasAccess(DELETE_COMPANY)" @click="showDeleteCompetitorModal">
                            <img src="../../../content/icons/delete.svg" alt="delete">
                        </div>
                        <div class="add_company" @click="addCompany()">
                            {{$t('competitor.add-company')}}
                        </div>
                    </div>
                </div>
                <div class="competitor_main_info">
                    <div class="top-top">
                        <div class="section1">
                            <span class="competitor_title">{{ competitor.name }}</span>
                            <br style="font-size: 25px">
                            <span class="competitor_founded">{{ founded }}</span>
                            <br style="font-size: 25px">
                            <div class="competitor_address" v-html="address"></div>
                        </div>
                        <div class="section2">
                            <div class="checkbox_section">
                            <div class="checkbox_class" v-if="competitorIsListed">{{$t('competitor.chips.listed')}}</div>
                            <div class="checkbox_class independent" v-if="competitorIsIndependent">{{$t('competitor.chips.independent')}}</div>
                            <div class="checkbox_class private_eq" v-if="competitorHasPrivateCapital">{{$t('competitor.chips.private-equity')}}</div>
                               </div>
                            <div class="transparency_green" v-if="(competitor.infogreffe && competitor.infogreffe.length > 0) || competitorIsListed">{{$t('competitor.chips.transparency')}}</div>
                            <div class="transparency_red" v-else>{{$t('competitor.chips.no-transparency')}}</div>
                            <div class="rate" title="Glassdoor rating">
                              <div class="rate-text-glassdoor" :class="{blocked : !hrInfo.glassdoorRate}">
                                  {{ hrInfo.glassdoorRate ? hrInfo.glassdoorRate.toFixed(1) : 0 }}
                              </div>
                              <img src="../../../content/images/glassdoor-logo.svg" class="rate-img">
                            </div>
                            <div class="rate" title="Viadeo rating">
                                <div class="rate-text-viadeo" :class="{blocked : !hrInfo.viadeoRate}">
                                    {{ hrInfo.viadeoRate ? hrInfo.viadeoRate.toFixed(1) : 0 }}
                                </div>
                                <img src="../../../content/images/viadeo-logo.svg" class="rate-img">
                            </div>
                            <div class="competitor_phone">
                                <img src="../../../content/icons/phone-call.svg" class="phone_logo_pic"
                                     :class="{'not-found': phone.length === 0}" alt="phone">
                                {{ phone.replace(/ /g,'') }}
                            </div>
                            <br>
                            <div class="competitor_site" :class="{'grey': site.length === 0}">
                                <a v-if="site.length !== 0" :href="site" target="_blank" >
                                    {{ $t('competitor.top-main-section.visit-company-website') }}
                                </a>
                                <span v-else>{{ $t('competitor.top-main-section.visit-company-website') }}</span>
                            </div>
                        </div>
                    </div>

                    <hr class="hr_line">

                    <div class="lower_main_section">

                        <div class="Competitor_numbers_part">
                            <span class="numbers_titles">{{ $t('competitor.bottom-main-section.revenue') }}</span>
                            <br>
                            <span v-if="revenue > 0" class="numbers_values" >{{ formatNumber(revenue) }}</span>
                            <span class="numbers_values" :class="{'grey': revenue <= 0}" >&#8364;</span>
                        </div>
                        <div class="Competitor_numbers_part special_number">
                            <span class="numbers_titles">{{ $t('competitor.bottom-main-section.employees') }}</span>
                            <br>
                            <span class="numbers_values" :title="employees">{{ formatNumber(employees) }}</span>
                        </div>
                        <div class="Competitor_numbers_part special special_number">
                            <span class="numbers_titles">{{ $t('competitor.bottom-main-section.consultants') }}</span>
                            <br>
                            <span class="numbers_values" :title="consultants">{{ formatNumber(consultants) }}</span>
                        </div>
                    </div>
                    <br>

                </div>
                <div class="competitor_upper_right_part">
                    <div class="competitor_rate_part">
                        <div class="competitor_rate_text">
                            <div class="rate_text">{{ $t('competitor.competitive-rate') }}&nbsp;&nbsp;</div><br class="secret">
                            <div v-if="rates.totalRate > 0" v-for="i in Array(getStars()).keys()"  class="icon-note star_ico_on"></div>
                            <div v-for="i in Array(5 - getStars()).keys()"  class="icon-note star_ico_off"></div>
                        </div>
                    </div>
                    <div class="competitor_map_part">
                        <div id="map-container-google-2" class="map-container" style="height: 315px">
                            <iframe :src="'https://maps.google.com/maps?q=' + mapAddress + '&ie=UTF8&iwloc=&output=embed'"
                                    frameborder="0"
                                    style="border:0" allowfullscreen>
                            </iframe>
                        </div>
                    </div>
                </div>
                <hr>
                    <div class="icon-edit suggest-changes"  @click="showEditMain()">&nbsp;&nbsp;{{ $t('competitor.bottom-main-section.suggest-changes') }}</div>
                <span class="clear-block"></span>
            </div>

            <br>
            <div class="offices">
                <div id="menu1">
                    <div v-if="!countrySelected" class="competitor_top_menu_item d" @click="selectCountry()">{{ $t('competitor.all-agencies') }}</div>
                    <div v-if="countrySelected" class="competitor_top_menu_item selection d" @click="selectCountry()">{{ $t('competitor.all-agencies') }}</div>
                    <div v-if="hasLeft" class="competitor_top_menu_left_arrow d" @click="moveLeft()"><</div>
                    <div v-for="( office, i) in shownOffices" class="d">
                        <div v-if="i!=selectedOfficeNum" class="competitor_top_menu_item d" @click="selectCity(office.id)">{{getCity(office)}}</div>
                        <div v-if="i==selectedOfficeNum" class="competitor_top_menu_item selection d" @click="selectCity(office.id)">{{getCity(office)}}</div>
                    </div>
                    <div v-if="hasRight" class="competitor_top_menu_right_arrow d" @click="moveRight()">></div>
                </div>
                <div class="add_agency" @click="addAgency()">{{ $t('competitor.add-agency') }}</div>
            </div>

            <div class="competitor_lower_part">
                <div class="competitor_bottom_menu" id="menu2">
                    <div class="flags" :class="flagColors(fillFlags.legal)">
                      <span class="lower_menu_item" @click="setSection(1)"  :class="{selection : isSelected(1), blocked : !hasAccess(LEGAL_VIEW)}">
                        {{ $t('competitor.bottom-menu.legal') }}
                    </span>
                    </div>
                    <div class="flags" :class="flagColors(fillFlags.finance)">
                    <span class="lower_menu_item" @click="setSection(2)" :class="{selection : isSelected(2), blocked : !hasAccess(FINANCE_VIEW)}">
                        {{ $t('competitor.bottom-menu.finance') }}
                    </span>
                    </div>
                    <div class="flags" :class="flagColors(fillFlags.clients)">
                    <span class="lower_menu_item" @click="setSection(3)" :class="{selection : isSelected(3), blocked : !hasAccess(CLIENT_VIEW)}">
                        {{ $t('competitor.bottom-menu.clients') }}
                    </span>
                    </div>
                    <div class="flags" :class="flagColors(fillFlags.technologies)">
                    <span class="lower_menu_item" @click="setSection(4)" :class="{selection : isSelected(4), blocked : !hasAccess(TECH_VIEW)}">
                        {{ $t('competitor.bottom-menu.technologies') }}
                    </span>
                    </div>
                    <div class="flags" :class="flagColors(fillFlags.hr)">
                    <span class="lower_menu_item" @click="setSection(5)" :class="{selection : isSelected(5), blocked : !hasAccess(HR_VIEW)}">
                        {{ $t('competitor.bottom-menu.hr') }}
                    </span>
                    </div>
                    <div class="flags" :class="flagColors(fillFlags.pr)">
                    <span class="lower_menu_item" @click="setSection(6)" :class="{selection : isSelected(6), blocked : !hasAccess(PR_VIEW)}">
                        {{ $t('competitor.bottom-menu.networks') }}
                    </span>
                    </div>
                    <div class="flags" :style="'border-color: ' + ((dialogCount>0)?'#6b48ff':'#a7a7a7')">
                    <span class="lower_menu_item" @click="setSection(7)" :class="{selection : isSelected(7)}">
                        {{ $t('competitor.bottom-menu.dialogs.menu') }}
                    </span>
                    </div>
                </div>
                <div class="dia_info_sec">
                    <div class="dia_cover">
                        <div class="competitor_dialogs_part" id="menu3">
                            <div class="dia_tab" :class="{diaselection : isSubsectionShown(1), blocked: selectedSection === -1}" @click="setSubsection(1)">{{ $t('competitor.bottom-menu.info.menu') }}</div>
                            <div v-if="isSelected(1)" class="dia_tab" :class="{diaselection : isSubsectionShown(3), blocked: selectedSection === -1}" @click="selectRepresentatives()">{{ $t('legal-tab.representatives.menu') }}</div>
                            <div v-if="isSelected(1)" class="dia_tab" :class="{diaselection : isSubsectionShown(4), blocked: selectedSection === -1}" @click="selectShareholders()">{{ $t('legal-tab.shareholders.menu') }}</div>
                            <div v-if="isSelected(1)" class="dia_tab" :class="{diaselection : isSubsectionShown(5), blocked: selectedSection === -1}" @click="setSubsection(5)">
                                <div>{{ $t('legal-tab.locations.menu') }}</div>
                                <div class="dia_tab_count">{{ getLocationsNumber() }}</div>
                            </div>
                            <div v-if="isSelected(2)" class="dia_tab" :class="{diaselection : isSubsectionShown(3), blocked: selectedSection === -1}" @click="selectFinanceRatios()">{{ $t('finance-tab.ratios.menu') }}</div>
                            <div v-if="isSelected(3)" class="dia_tab" :class="{diaselection : isSubsectionShown(3), blocked: selectedSection === -1}" @click="setSubsection(3)">{{ $t('clients-tab.pricing.menu') }}</div>
                            <div v-if="isSelected(3)" class="dia_tab" :class="{diaselection : isSubsectionShown(4), blocked: selectedSection === -1}" @click="setSubsection(4)">{{ $t('clients-tab.industries.menu') }}</div>
                            <div v-if="isSelected(5)" class="dia_tab" :class="{diaselection : isSubsectionShown(3), blocked: selectedSection === -1}" @click="setSubsection(3)">{{ $t('hr-tab.recruiters.menu') }}</div>
                            <div v-if="isSelected(5)" class="dia_tab" :class="{diaselection : isSubsectionShown(4), blocked: selectedSection === -1}" @click="setSubsection(4)">{{ $t('hr-tab.typologies.menu') }}</div>
                            <div v-if="isSelected(5)" class="dia_tab" :class="{diaselection : isSubsectionShown(5), blocked: selectedSection === -1}" @click="setSubsection(5)">{{ $t('hr-tab.employees.menu') }}</div>
                            <div v-if="isSelected(6)" class="dia_tab" :class="{diaselection : isSubsectionShown(3), blocked: selectedSection === -1}" @click="setSubsection(3)">{{ $t('pr-tab.google-alerts.tab-name') }}</div>
                            <div v-if="isSelected(6)" class="dia_tab" :class="{diaselection : isSubsectionShown(4), blocked: selectedSection === -1}" @click="setSubsection(4)">{{ $t('pr-tab.statistics.tab-name') }}</div>
                            <!--div class="dia_tab" :class="{diaselection : isSubsectionShown(2), blocked: selectedSection === -1}" @click="setSubsection(2)">
                                <div>{{ $t('competitor.bottom-menu.dialogs.menu') }}</div>
                                <div class="dia_tab_count">{{ dialogCount }}</div>
                            </div-->
                        </div>
                    </div>

                    <div class="competitor_section_info" id="sections">

                        <div class="info_section" v-if="isSelectedSubSection(1, 1)" id="legal">
                            <legal-information
                                v-if="competitor && competitor.infogreffe && competitor.legal"
                                :infogreffe="competitor.infogreffe" :legal="competitor.legal">
                            </legal-information>
                        </div>
                        <div class="info_section" style="padding-top: 1em!important;" v-if="isSelectedSubSection(1, 3)" id="represents">
                            <representatives v-if="competitor && competitor.legal && this.competitor.legal.length > 0" :siren="this.competitor.legal[0].siren" :mountFlag="true"></representatives>
                        </div>
                        <div class="info_section" style="padding-top: 1em!important;" v-if="isSelectedSubSection(1, 4)" id="shareholders">
                            <shareholders v-if="this.competitor && this.competitor.legal && this.competitor.legal.length > 0" :siren="this.competitor.legal[0].siren"></shareholders>
                        </div>
                        <div class="info_section" v-if="isSelectedSubSection(1,5)">
                            <legal-agencies></legal-agencies>
                        </div>
                        <div class="info_section" v-if="isSelectedSubSection(2, 1)" id="finance">
                            <finance-general
                                v-if="competitor && competitor.legal"
                                :siren="competitor.legal.length > 0 ? competitor.legal[0].siren : ''"
                            ></finance-general>
                        </div>
                        <div class="info_section" v-if="isSelectedSubSection(2, 3)">
                            <finance-ratios
                                v-if="competitor && competitor.legal"
                                :siren="competitor.legal.length > 0 ? competitor.legal[0].siren : ''"
                            ></finance-ratios>
                        </div>
                        <div class="info_section" v-if="isSelectedSubSection(3, 1)" id="clients">
                            <clients v-if="competitor && competitor.id" :compId="competitor.id"></clients>
                        </div>
                        <div class="info_section" v-if="isSelectedSubSection(3, 3)">
                            <clients-pricing v-if="competitor && competitor.id"></clients-pricing>
                        </div>
                        <div class="info_section" v-if="isSelectedSubSection(3, 4)">
                            <clients-industry v-if="competitor && competitor.id"></clients-industry>
                        </div>
                        <div class="info_section" v-if="isSelectedSubSection(4, 1)" id="techno">
                            <technology-general v-if="competitor && competitor.id"></technology-general>
                        </div>
                        <div class="info_section" v-if="isSelectedSubSection(5, 1)" id="hr">
                            <hr-information></hr-information>
                        </div>
                        <div class="info_section" v-if="isSelectedSubSection(5, 3)" id="recruiters">
                           <recruiters></recruiters>
                        </div>
                        <div class="info_section" v-if="isSelectedSubSection(5, 4)" id="typology">
                            <typology></typology>
                        </div>
                        <div class="info_section" v-if="isSelectedSubSection(5, 5)" id="employees">
                            <employees></employees>
                        </div>
                        <div class="info_section" v-if="isSelectedSubSection(6, 1)" id="pr">
                            <pr-networks></pr-networks>
                        </div>
                        <div class="info_section" v-if="isSelectedSubSection(6, 3)" id="google-alerts">
                            <google-alerts></google-alerts>
                        </div>
                        <div class="info_section" v-if="isSelectedSubSection(6, 4)" id="pr-statistics">
                            <pr-statistics></pr-statistics>
                        </div>
                        <div class="info_section" v-if="isSelected(7)" id="dialogs">
                            <dialogs  v-if="competitor && competitor.id && selectedSection > 0"
                                      :compid="competitor.id"
                                      :section="selectedSection">
                            </dialogs>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
        <add-agency-modal :selectedOffice="office" :editOffice="editOffice"></add-agency-modal>
        <edit-competitor-main :revenue="revenue"></edit-competitor-main>
        <confirm-modal :onConfirm="deleteCompetitor" :message="$t('competitor.confirm-delete')"></confirm-modal>
    </div>
</template>

<script lang="ts" src="./competitor.component.ts">
</script>

<style>
    .clear-block {
        clear: both;
        display: block;
    }
.backbutton {
    cursor: pointer;
    font-size: large;
    margin-left: 110px;
    margin-top: 30px;
    margin-bottom: 40px;
}
.backbutton:hover{
    text-decoration: underline;
}
.floatback {
    cursor: pointer;
    font-size: large;
    position:fixed;
    top: 20px;
    left: 20px;
}
.floatback:hover{
    text-decoration: underline;
}
.backtext {
    font-size: medium;
    margin-left: 1em;
}
.competitor_body {
    margin-left: 110px;
    margin-top: 0;
    min-width: 896px;
}
.competitor_upper_part {
    background-color: #ffffff;
    margin-right: 7%;
}
.competitor_main_info {
    background-color: #ffffff;
    width: 763px;
    margin-top: 0px;
    display: inline-block;
    margin-left: 1.5em;
    padding: 1em;
    padding-top: 1em;
    padding-bottom: 0;
    border-left-style: solid;
    border-color: #6b48ff;
    border-width: 3px;
}
.competitor_upper_right_part {
    background-color: #ffffff;
    display: inline-block;
    vertical-align: top;
    margin-top: 0;
    width: 30%;
}
.competitor_top_menu_item {
    cursor: pointer;
    color: #8c8c8c;
    padding-left: 1.5em;
    padding-right: 1.5em;
    padding-top: 0.8em;
    padding-bottom: 0.8em;
    border-radius: 2.5em;
    font-size: 14px;
    font-weight: 600;
    margin-right: 1em;
    border-style: solid;
    border-width: 2px;
    border-color: #ecf0f3;
}
.competitor_top_menu_item:hover {
    border-color: #dbe2e8;
}
.competitor_top_menu_left_arrow {
    cursor: pointer;
    color: #8c8c8c;
    padding-left: 1.5em;
    padding-right: 1.5em;
    padding-top: 0.8em;
    padding-bottom: 0.8em;
    border-radius: 2.5em;
    font-size: 14px;
    font-weight: 600;
    margin-left: -1em;
    border-style: solid;
    border-width: 2px;
    border-color: #ecf0f3;
}
.competitor_top_menu_left_arrow:hover {
    border-color: #dbe2e8;
}
.competitor_top_menu_right_arrow {
    cursor: pointer;
    color: #8c8c8c;
    padding-left: 1.5em;
    padding-right: 1.5em;
    padding-top: 0.8em;
    padding-bottom: 0.8em;
    border-radius: 2.5em;
    font-size: 14px;
    font-weight: 600;
    margin-left: -1em;
    border-style: solid;
    border-width: 2px;
    border-color: #ecf0f3;
}
.competitor_top_menu_right_arrow:hover {
    border-color: #dbe2e8;
}
.offices {
    display: flex;
    flex-direction: row;
    width: 100%;
    padding-right: 7%;
}
.add_agency {
    display: inline-block;
    cursor: pointer;
    color: #6b48ff;
    padding-left: 1.5em;
    padding-top: 0.8em;
    padding-bottom: 0.8em;
    border-radius: 2.5em;
    font-size: 14px;
    font-weight: 600;
    margin-left: auto;
    border-style: solid;
    border-width: 2px;
    border-color: #ecf0f3;
    vertical-align: top;
    white-space: nowrap;
}
.add_agency:hover {
    text-decoration: underline;
}
.add_company {
    white-space: nowrap;
    cursor: pointer;
    color: #6b48ff;
    padding-left: 1.5em;
    padding-right: 0.5em;
    padding-top: 0.8em;
    padding-bottom: 0.8em;
    border-radius: 2.5em;
    font-size: 14px;
    font-weight: 600;
    margin-left: auto;
    float: right;
}
.add_company:hover {
    text-decoration: underline;
}
.selection {
    color: #ffffff!important;
    background-color: #6b48ff;
    border-color: #6b48ff!important;
    cursor: default!important;
}
.competitor_rate_part {
    height: 70px;
    padding-left: 0em;
}
.competitor_rate_text {
    font-weight: 600;
    padding-top: 19px;
}
.competitor_logo_img {
    height: 100%;
    width: 100%;
    object-fit: contain;
}
.top-top {
    height: 193px;
    vertical-align: top;
    padding-left: 20px;
    margin-top: 2px;
}
.section1 {
    display: inline-block;
    width: 390px;
    vertical-align: top;
}
.section2 {
    display: inline-block;
    text-align: right;
    width: 300px;
    vertical-align: bottom;
    margin-top: 12px;
}
.main_agency{
    display: inline-block;
    vertical-align: top;
    margin-top: -93px;
    margin-left: 21px;
    color: #ffffff;
    background-color: #1ee3cf;
    border-color: #1ee3cf;
    border-width: 2px;
    border-style: dashed;
    padding-left: 1em;
    padding-right: 1em;
    padding-top: 0.5em;
    padding-bottom: 0.5em;
    border-radius: 2.5em;
    cursor: default;
    font-size: 10px;
    font-weight: 600;
}
.checkbox_section {
    height: 38px;
    width: 100%;
}
.checkbox_class {
    display: inline-block;
    vertical-align: top;
    margin-top: 0px;
    margin-bottom: 10px;
    margin-left: 0px;
    color: #ffffff;
    background-color: #6b48ff;
    border-color: #6b48ff;
    border-width: 2px;
    border-style: dashed;
    padding-left: 1em;
    padding-right: 1em;
    padding-top: 0.5em;
    padding-bottom: 0.5em;
    border-radius: 2.5em;
    cursor: default;
    font-size: 10px;
    font-weight: 600;
}
.independent {
    background-color: #666666!important;
    border-color: #666666!important;
}
.private_eq {
    background-color: #0d3f67!important;
    border-color: #0d3f67!important;
}
.transparency_green{
    display: inline-block;
    vertical-align: top;
    margin-top: 0px;
    margin-bottom: 10px;
    margin-left: 20px;
    color: #ffffff;
    background-color: #1ee3cf;
    border-color: #1ee3cf;
    border-width: 2px;
    border-style: dashed;
    padding-left: 1em;
    padding-right: 1em;
    padding-top: 0.5em;
    padding-bottom: 0.5em;
    border-radius: 2.5em;
    cursor: default;
    font-size: 10px;
    font-weight: 600;
}
.transparency_red{
    display: inline-block;
    vertical-align: top;
    margin-top: 0px;
    margin-bottom: 10px;
    margin-left: 12px;
    color: #ffffff;
    background-color: #e60027;
    border-color: #e60027;
    border-width: 2px;
    border-style: dashed;
    padding-left: 1em;
    padding-right: 1em;
    padding-top: 0.5em;
    padding-bottom: 0.5em;
    border-radius: 2.5em;
    cursor: default;
    font-size: 10px;
    font-weight: 600;
}
.competitor_title {
    font-size: 34px;
    font-weight: 600;
    margin-top: 20px;
    text-align: center;
}
.competitor_site {
    alignment: right;
    margin-top: -0.5em;
}
.group_site {
    alignment: right;
    display: inline-block;
    vertical-align: top;
    width: 22%;
    text-align: right;
}
.competitor_founded {
    color: #8c8c8c;
}
.competitor_address {
    width: 100%;
}
.lower_main_section {
    margin-top: 20px;
}
.Competitor_numbers_part {
    display: inline-block;
    margin-left: 0px;
    margin-right: 0px;
    width: 33%;
}
.Competitor_numbers_part_left {
    display: inline-block;
    margin-left: 20px;
    margin-right: 0px;
    width: 150px;
}
.special_number {
    width: 32%;
}
.group_numbers_part {
    display: inline-block;
    margin-left: 0px;
    margin-right: 0px;
    width: 22%;
}
.group_numbers_part_left {
    display: inline-block;
    margin-left: 0px;
    margin-right: 0px;
    width: 24%;
}
.numbers_titles {
    color: #909090;
    font-size: 12px;
    font-weight: normal;
    white-space: nowrap;
}
.numbers_values {
    color: #262626;
    font-size: 56px;
}
.mini_m {
    font-size: 48px!important;
    font-weight: 200;
}
.special {
    margin-right: 0!important;
}
.competitor__delete {
    margin-left: 1rem;
    cursor: pointer;
}
.competitor_phone {
    margin-right: -4px;
    margin-top: 10px;
}
.competitor_bottom_menu {
    background-color: #ffffff;
    margin-top: 30px;
    padding-top: 30px;
    padding-bottom: 30px;
    padding-left: 0px;
    font-size: 14px;
    font-weight: 600;
    line-height: 50px;
}
.competitor_dialogs_part {
    background-color: #f3f5f7;
    width: 195px;
    height: 100%;
    display: inline;
    vertical-align: top;
}
.competitor_section_info {
    background-color: #ffffff;
    width: 100%;
    display: inline-block;
    margin-left: -3px!important;
}
.dia_tab {
    cursor: pointer;
    height: 57px;
    padding-left: 1em;
    padding-top: 18px;
    border-bottom-style: solid;
    border-bottom-width: 1px;
    border-bottom-color: #cccccc;
    border-left-style: none;
    border-left-style: solid;
    border-left-color: #f3f5f7;
    border-left-width: 3px;
    font-size: 14px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}
.dia_tab_count {
    margin-right: 20px;
}
.dia_tab:hover {
    color: #8c8c8c;
}
.diaselection {
    border-left-color: #6b48ff;
    color: #6b48ff!important;
    border-bottom-color: #cccccc;
    cursor: default!important;
}
.dia_info_sec {
    display: flex!important;
}
.dia_cover {
    background-color: #f3f5f7;
    width: 195px;
}
.lower_menu_item {
    cursor: pointer;
    color: #8c8c8c;
    padding-left: 1.5em;
    padding-right: 1.5em;
    padding-top: 0.8em;
    padding-bottom: 0.8em;
    border-radius: 2.5em;
    border-style: solid;
    border-width: 2px;
    border-color: #ffffff;
    white-space: nowrap;
}
.lower_menu_item:hover {
    border-color: #dbe2e8;
}
.info_section {
    padding: 2em;
    width: 100%;
}
.current_section {
    display: block!important;
}
.star_ico_off {
    margin-left: 2px;
    color: #dbe2e8;
    font-size: 24px;
    display: inline-block;
}
.star_ico_on {
    margin-left: 2px;
    color: #1ee3cf;
    font-size: 24px;
    display: inline-block;
}
.blocked {
    cursor: default!important;
    color: #dbe2e8!important;
    border-color: #ffffff!important;
    pointer-events: none;
}
.rate_text {
    display: inline-block;
    vertical-align: top;
    margin-top: 6px;
}
.back-ico {
    display: inline-block;
}
.suggest-changes {
    color: #6b48ff;
    font-size: 14px;
    float: right;
    margin-bottom: 16px;
    margin-right: 32px;
    cursor: pointer;
}
.suggest-changes:hover{
    text-decoration: underline;
}
.grey {
    color: #cccccc!important;
    cursor: default;
}
.map-container{
    overflow:hidden;
    padding-bottom:56.25%;
    position:relative;
    height:0;
}
.map-container iframe{
    left:0;
    top:0;
    height:100%;
    width:100%;
    position:absolute;
}
.competitor_map_part {
    width: 96%;
}
.d {
    display: inline-block;
}
.rate {
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    margin-bottom: 3px;
}
.rate-text-glassdoor {
    font-weight: bold;
    color: #0caa41;
    line-height: 18px;
}
.rate-text-viadeo {
    font-weight: bold;
    color: #f49d22;
    line-height: 18px;
}
.rate-img {
    width: 18px;
    height: 18px;
    margin-left: 3px;
}
.phone_logo_pic {
    width: 20px;
    height: 20px;
    margin-right: 5px;
}
.group_part {
    padding: 2em;
    vertical-align: top;
}
.group_section1 {
    display: inline-block;
    width: 65%;
}
.comp_list {
    width: 40%;
}
.group_section-3 {
    display: flex;
    flex-direction: row;
    align-items: center;
}
.companes_number {
    white-space: nowrap;
    font-size: 18px;
    font-weight: 600;
    margin-right: 5px;
}
.no_back {
    margin-top: 95px;
}
.logo_container {
    width: 100px;
    height: 100px;
    display: inline-block;
    margin-left: 0em;
    margin-right: 1em;
    margin-bottom: 0px;
    vertical-align: top;
}
.phone {
    background-color: #6B48FF;
    display: none;
}
.not-found {
    filter: invert(70%);
}
.competitor_lower_part {
    margin-right: 7%;
}
@media screen and (max-width: 500px) {
    .competitor  {
        display: none!important;
    }
    .phone {
        display: block;
        padding: 3em;
    }
}
@media screen and (max-width: 1364px) {
    .backbutton {
        margin-left: 0px;
    }
    .competitor_body {
        margin-left: 0px;
    }
    .competitor_upper_part {
        margin-right: 0px;
    }
    .competitor_lower_part {
        margin-right: 0px;
    }
    .floatback {
        font-weight: 600;
        border-style: solid;
        padding: 0 1em 0 1em;
        background-color: #ffffff;
        border-radius: 1em;
    }
}
@media screen and (max-width: 1210px) {
    .competitor_rate_part {
        height: 85px;
    }
    .secret {
        display: block!important;
    }
    .competitor_upper_right_part {
        width: 25%;
    }
}
@media screen and (max-width: 1020px) {
    .competitor_upper_right_part {
        display: none;
    }
    .competitor_main_info {
          margin-left: 10%;
      }
}
@media screen and (max-width: 1070px) {
    .competitor_upper_right_part {
        width: 210px;
    }
    .group_site {
        width: 15%;
    }

}
@media screen and (max-width: 870px) {

    .competitor_upper_part {
        margin-right: 0px;
    }
    .competitor_main_info {
        width: 743px;
        padding-right: 0;
    }
    .competitor_main_info {
        margin-left: 1.5em;
    }
}
    .secret {
        display: none;
    }

    .flags {
        padding-top: 10px;
        display: inline-block;
        border-top-style: solid;
        border-width: 3px;
        margin-left: 1em;
    }

    .red {
        border-color: indianred;
    }
    .yellow {
        border-color: #ffc107;
    }
    .green {
        border-color: darkseagreen;
    }
    .mini_dialog {
        background-color: white;
        padding: 1rem;
    }
    .dia-txt {
        font-weight: 600;
        display: inline-block;
    }
    .save {
        margin-top: 0.5rem;
        background-color: #6b48ff;
        color: #ffffff;
        width: 100%;
        height: 40px;
        border-radius: 3px;
        border-color: #6b48ff;
    }
    .comp_list_small {
        display: inline-block;
        margin-bottom: 0.5rem;
        font-weight: 600;
    }
    .group_section_2 {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }
</style>
