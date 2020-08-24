<template>
    <div>
        <div class="hr_main_left">
            <div class="top-block">
                <competitive-rate :title="$t('hr-tab.general.hr-rate')" :rate="rate"></competitive-rate>
                <div class="vertical-line"></div>
                <div class="hr_numbers_part">
                    <span class="numbers_titles" v-text="$t('hr-tab.general.hr-spec')"></span>
                    <br>
                    <span class="numbers_values">{{ hrInfo.hrSpecialistsNumber || 0 }}</span>
                </div>
                <div class="vertical-line"></div>
                <head-of
                    :title="$t('hr-tab.general.hr-head')" :name="headHRDisplayName"
                    :linkedinUrl="head_hr && head_hr.linkedPage ? head_hr.linkedPage : ''">
                </head-of>
                <div class="vertical-line"></div>
                <head-of
                    :title="$t('hr-tab.general.recr-head')" :name="headRECRDisplayName"
                    :linkedinUrl="head_recr && head_recr.linkedPage ? head_recr.linkedPage : ''">
                </head-of>
            </div>
            <div class="hr_h2" v-text="$t('hr-tab.general.recruitment')"></div>
            <hr class="societe_main_hr">
            <div class="hr-info-unit">
                <div class="hr-info-label" v-text="$t('hr-tab.general.recruiters-number')"></div>
                <div class="hr-info-value">{{ hrInfo.recruitersNumber || 0 }}</div>
            </div>
            <hr class="societe_main_hr">
            <div class="hr-info-unit" >
                <div class="hr-info-label" v-text="$t('hr-tab.general.employees-number') + ' (' + getLatestYearWithWorkforce() + ')'"></div>
                <div class="hr-info-value">{{ lastWorkforce }}</div>
            </div>
            <hr class="societe_main_hr">
            <div class="hr-info-unit" >
                <div class="hr-info-label" v-text="$t('hr-tab.general.new-employees-number') + ' (' + getLatestYearWithWorkforce() + ')'"></div>
                <div class="hr-info-value">{{ lastWorkforceGap }}</div>
            </div>
            <hr class="societe_main_hr">
            <div class="hr-info-unit">
                <div class="hr-info-label" v-text="$t('hr-tab.general.recruiters-efficiency')"></div>
                <div class="hr-info-value">{{ hrInfo.recruitersNumber?(lastWorkforceGap / hrInfo.recruitersNumber).toFixed(2) : 0 }}</div>
            </div>
            <hr class="societe_main_hr">
            <div class="hr-info-unit">
                <div class="hr-info-label" v-text="$t('hr-tab.general.cooptation-premium-amount')"></div>
                <div class="hr-info-value">{{ hrInfo.cooptationPremiumAmount || '???' }} €</div>
            </div>
            <hr class="societe_main_hr">
            <div class="hr-info-unit">
                <div class="hr-info-label" v-text="$t('hr-tab.general.junior-salary')"></div>
                <div class="hr-info-value">{{ hrInfo.juniorSalary || '???' }} €</div>
            </div>
            <hr class="societe_main_hr">
            <div class="hr-info-unit">
                <div class="hr-info-label" v-text="$t('hr-tab.general.average-salary')"></div>
                <div class="hr-info-value">{{ hrInfo.averageSalary || '???' }} €</div>
            </div>
            <hr class="societe_main_hr">
            <div class="hr-info-unit">
                <div class="hr-info-label" v-text="$t('hr-tab.general.average-contract-duration')"></div>
                <div class="hr-info-value">{{ hrInfo.averageContractDuration || '???' }}</div>
            </div>
            <hr class="societe_main_hr">
            <div class="hr_h2" v-text="$t('hr-tab.general.hr-flow')"></div>
            <hr class="societe_main_hr">
            <div class="hr-info-unit">
                <div class="hr-info-label" v-text="$t('hr-tab.general.hr-interview')"></div>
                <div class="hr-info-value">{{ hrInfo.interviewsNumber || 0 }}</div>
            </div>
            <hr class="societe_main_hr">
            <div class="hr-info-unit">
                <div class="hr-info-label" v-text="$t('hr-tab.general.hr-time')"></div>
                <div class="hr-info-value">{{ hrInfo.recrutmentTime || 0 }}</div>
            </div>
            <hr class="societe_main_hr">
            <br>
            <div>
                <div class="CBSection1">
                    <span class="checkB">{{$t('hr-tab.general.technical-test') + ': '}}</span>
                    <span class="labl" :style="'background-color: ' + (hrInfo.technicalTest===null?'#6b48ff':hrInfo.technicalTest?'#1ee3cf':'#e60027')">{{hrInfo.technicalTest===null?'N/A':hrInfo.technicalTest?'Yes':'No'}}</span>
                </div>
                <div class="CBSection2">
                    <span class="checkB">{{$t('hr-tab.general.personality-test') + ': '}}</span>
                    <span class="labl" :style="'background-color: ' + (hrInfo.personalityTest===null?'#6b48ff':hrInfo.personalityTest?'#1ee3cf':'#e60027')">{{hrInfo.personalityTest===null?'N/A':hrInfo.personalityTest?'Yes':'No'}}</span>
                </div>
                <div class="CBSection3">
                    <span class="checkB">{{$t('hr-tab.general.english-test') + ': '}}</span>
                    <span class="labl" :style="'background-color: ' + (hrInfo.englishTest===null?'#6b48ff':hrInfo.englishTest?'#1ee3cf':'#e60027')">{{hrInfo.englishTest===null?'N/A':hrInfo.englishTest?'Yes':'No'}}</span>
                </div>
            </div>
            <div class="details_block">
                <div class="hr_h1" v-text="$t('hr-tab.general.hr-details')"></div>
                <div class="hr_details">
                    {{ hrInfo.hrDetails || ''}}
                </div>
            </div>
            <div class="details_block">
                <div class="hr_h1" v-text="$t('hr-tab.general.hr-signing-incentives')"></div>
                <div class="hr_signing_incentives_hint" v-text="$t('hr-tab.general.hr-signing-incentives-hint')"></div>
                <div class="hr_signing_incentives">
                    {{ hrInfo.signingIncentives || ''}}
                </div>
            </div>
        </div>
        <div class="hr_main_right">
            <div class="hr_h1" v-text="$t('hr-tab.general.hr-more-options')"></div>
            <hr>
            <div class="hr-link ratings">
                <a v-if="hrInfo.vacanciesUrl && hrInfo.vacanciesUrl !== '\\\\'"
                   :href="hrInfo.vacanciesUrl"
                   target="_blank"
                   class="competitor_site"
                >{{ $t('hr-tab.general.hr-open-jobs') }}</a>
                <span v-if="!hrInfo.vacanciesUrl || hrInfo.vacanciesUrl === '\\\\'"
                      class="competitor_site grey"
                      v-text="$t('hr-tab.general.hr-open-jobs')"
                ></span>
            </div>
            <hr>
            <div class="hr-link ratings">
                <div class="rate" title="Glassdoor rating">
                    <div class="rate-text-glassdoor" :class="{blocked : !hrInfo.glassdoorRate}">
                        {{ hrInfo.glassdoorRate ? hrInfo.glassdoorRate.toFixed(1) : 0 }}
                    </div>
                    <img src="../../../../content/images/glassdoor-logo.svg" class="rate-img">
                </div>
                <a v-if="hrInfo.glassdoorUrl && hrInfo.glassdoorUrl !== '\\\\'"
                   :href="hrInfo.glassdoorUrl.includes('http') ? hrInfo.glassdoorUrl : '//' + hrInfo.glassdoorUrl"
                   target="_blank"
                   class="competitor_site"
                >{{ $t('hr-tab.general.hr-glassdoor-reviews') }}</a>
                <span v-if="!hrInfo.glassdoorUrl || hrInfo.glassdoorUrl === '\\\\'"
                      class="competitor_site grey"
                      v-text="$t('hr-tab.general.hr-glassdoor-reviews')">
                </span>

            </div>
            <hr>
            <div class="hr-link ratings">
                <div class="rate" title="Viadeo rating">
                    <div class="rate-text-viadeo" :class="{blocked : !hrInfo.viadeoRate}">
                        {{ hrInfo.viadeoRate ? hrInfo.viadeoRate.toFixed(1) : 0 }}
                    </div>
                    <img src="../../../../content/images/viadeo-logo.svg" class="rate-img">
                </div>
                <a v-if="hrInfo.viadeoUrl && hrInfo.viadeoUrl !== '\\\\'"
                   :href="hrInfo.viadeoUrl.includes('http') ? hrInfo.viadeoUrl :'//' + hrInfo.viadeoUrl"
                   target="_blank"
                   class="competitor_site">
                    {{ $t('hr-tab.general.hr-viadeo-reviews') }}
                </a>
                <span v-if="!hrInfo.viadeoUrl || hrInfo.viadeoUrl === '\\\\'"
                      class="competitor_site grey"
                      v-text="$t('hr-tab.general.hr-viadeo-reviews')"
                ></span>
            </div>
            <hr>
            <div>
                <div class="icon-edit suggest-changes"
                     :class="{'no-access': !hasAccess(HR_EDIT)}"
                     @click="showEditHr()">&nbsp;&nbsp;{{ $t('competitor.bottom-main-section.suggest-changes') }}
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./hr-information.component.ts">
</script>

<style scoped>
    .rate {

    }
    .rate-img {
        width: 18px;
        height: 18px;
        margin-left: 3px;
        margin-right: 3px;
    }
    .ratings {
        display: -webkit-box;
        -webkit-box-align: baseline;
    }
    .top-block {
        display: flex;
        flex-direction: row;
        align-items: flex-start;
    }

    .vertical-line {
        height: 50px;
        border: 1px solid #D6DADF;
        margin: 30px 5% 0 50px;
    }

    .societe_main_hr {
        margin-top: 0.5rem !important;
        margin-bottom: 0.5rem !important;
    }

    .hr-info-unit {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        width: 100%;
    }

    .hr-info-label {
        font-size: 12px;
    }

    .hr-info-value {
        font-size: 12px;
    }

    .hr_h2 {
        font-size: 24px;
        font-weight: 600;
        margin-top: 50px;
    }

    .hr_main_left {
        display: inline-block;
        width: 73%;
        vertical-align: top;
        /*padding-left: 30px;*/
        padding-right: 55px;
        padding-top: 6px;
    }

    .hr_main_right {
        display: inline-block;
        width: 22%;
        margin-left: 2%;
    }

    .numbers_values {
        color: #262626;
        font-size: 56px;
    }

    .suggest-changes {
        margin-right: 0px;
        text-align: end;
    }

    .hr_numbers_part {
        margin-left: 0px;
        margin-right: 0px;
        vertical-align: top;
    }
    .hr_signing_incentives,
    .hr_details {
        width: 100%;
        height: auto;
        border: 2px solid #DBE2E8;
        border-radius: 3px;
        padding: 20px;
        font-size: 12px;
        margin-top: 10px;
        white-space: pre-line;
    }
    .hr_signing_incentives_hint {
        font-size: 12px;
        font-weight: 400;
    }
    .details_block {
        margin-top: 20px;
        margin-bottom: 10px;
    }
    .hr_h1 {
        font-size: 20px;
        font-weight: 600;
    }

    .CBSection1 {
        display: inline-block;
        width: 32%;
        text-align: left;
    }

    .CBSection2 {
        display: inline-block;
        width: 33%;
        text-align: center;
    }

    .CBSection3 {
        display: inline-block;
        width: 33%;
        text-align: right;
    }

    .labl {
        border-radius: 1rem;
        color: white;
        font-weight: 600;
        padding: 0.2rem 0.4rem;
    }

    @media screen and (max-width: 1100px) {
        .hr_main_right {
            width: 90%;
        }
    }
</style>
