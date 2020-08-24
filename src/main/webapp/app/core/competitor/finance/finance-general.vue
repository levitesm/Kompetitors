<template>
    <div>
        <div class="finance-container">
            <div class="finance-ratings">
                <competitive-rate :title="$t('finance-tab.general.finance-rate')" :rate="rate"></competitive-rate>
                <div class="vertical-line"></div>
                <head-of
                    :title="$t('finance-tab.general.finance-head')" :name="headDisplayName"
                    :linkedinUrl="headFinance && headFinance.linkedPage ? headFinance.linkedPage : ''">
                </head-of>
                <div class="icon-edit suggest_link"
                     :class="{'no-access': !hasAccess(FINANCE_EDIT)}"
                     @click="showEditFinance()">
                    &nbsp;&nbsp;{{ $t('competitor.bottom-main-section.suggest-changes') }}
                </div>
            </div>
            <div v-if="annualAccounts.length > 0">
                <div class="legend-el">
                    <div>{{ $t('finance-tab.general.gross-sales') + " " + $t('finance-tab.general.chart') }}</div>
                    <div class="legend-color red-bg"></div>
                </div>
                <div class="legend-el">
                    <div>{{ $t('finance-tab.general.workforce') + " " + $t('finance-tab.general.chart') }}</div>
                    <div class="legend-color grey-bg"></div>
                </div>
                <div>
                    <finance-chart></finance-chart>
                </div>
                <div class="unit">
                    <div class="title">{{ $t('finance-tab.general.accounts') }}</div>
                    <div>{{ `${prevYear} year` }}</div>
                </div>
                <hr class="hr">
                <div class="unit">
                    <div class="bold">{{ $t('finance-tab.general.gross-sales') }}:</div>
                    <div class="unit__tooltip">
                        <img class="unit__img" src="../../../../content/images/info-icon.jpg" :title="$t('finance-tab.general.gross-sales-tooltip')" alt="tooltip">
                    </div>
                    <div v-text="grossSales(prevYear).toLocaleString('fr-FR') + ' €'" class="money bold"></div>
                </div>
                <hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.payroll') }}:</div>
                    <div class="unit__tooltip">
                        <img class="unit__img" src="../../../../content/images/info-icon.jpg" :title="$t('finance-tab.general.payroll-tooltip')" alt="tooltip">
                    </div>
                    <div v-text="payroll(prevYear).toLocaleString('fr-FR') + ' €'" class="money"></div>
                </div>
                <hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.workforce') }}:</div>
                    <div class="unit__tooltip">
                        <img class="unit__img" src="../../../../content/images/info-icon.jpg" :title="$t('finance-tab.general.workforce-tooltip')" alt="tooltip">
                    </div>
                    <div v-text="workForce(prevYear)"></div>
                </div>
                <hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.operating-result') }}:</div>
                    <div class="unit__tooltip">
                        <img class="unit__img" src="../../../../content/images/info-icon.jpg" :title="$t('finance-tab.general.operating-result-tooltip')" alt="tooltip">
                    </div>
                    <div v-text="operatingResult(prevYear).toLocaleString('fr-FR') + ' €'" class="money"></div>
                </div>
                <hr class="hr">
                <div class="unit">
                    <div class="bold">{{ $t('finance-tab.general.net-result') }}:</div>
                    <div class="unit__tooltip">
                        <img class="unit__img" src="../../../../content/images/info-icon.jpg" :title="$t('finance-tab.general.net-result-tooltip')" alt="tooltip">
                    </div>
                    <div v-text="netResult(prevYear).toLocaleString('fr-FR') + ' €'" class="money bold"></div>
                </div><hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.subcontracting') }}:</div>
                    <div class="unit__tooltip">
                        <img class="unit__img" src="../../../../content/images/info-icon.jpg" :title="$t('finance-tab.general.subcontracting-tooltip')" alt="tooltip">
                    </div>
                    <div v-text="subcontracting(prevYear).toLocaleString('fr-FR') + ' €'" class="money"></div>
                </div>

                <br>
                <br>
                <div class="title">{{ $t('finance-tab.general.funding') }}</div>
                <hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.equity') }}:</div>
                    <div class="unit__tooltip">
                        <img class="unit__img" src="../../../../content/images/info-icon.jpg" :title="$t('finance-tab.general.equity-tooltip')" alt="tooltip">
                    </div>
                    <div v-text="equity(prevYear).toLocaleString('fr-FR') + ' €'" class="money"></div>
                </div>
                <hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.share-capital') }}:</div>
                    <div class="unit__tooltip">
                        <img class="unit__img" src="../../../../content/images/info-icon.jpg" :title="$t('finance-tab.general.share-capital-tooltip')" alt="tooltip">
                    </div>
                    <div v-text="shareCapital(prevYear).toLocaleString('fr-FR') + ' €'"></div>
                </div>
                <hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.retained-earnings') }}:</div>
                    <div class="unit__tooltip">
                        <img class="unit__img" src="../../../../content/images/info-icon.jpg" :title="$t('finance-tab.general.retained-earnings-tooltip')" alt="tooltip">
                    </div>
                    <div v-text="retainedEarnings(prevYear).toLocaleString('fr-FR') + ' €'" class="money"></div>
                </div>
                <hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.dividends') }}:</div>
                    <div class="unit__tooltip">
                        <img class="unit__img" src="../../../../content/images/info-icon.jpg" :title="$t('finance-tab.general.dividends-tooltip')" alt="tooltip">
                    </div>
                    <div v-text="dividends(prevYear).toLocaleString('fr-FR') + ' €'" class="money"></div>
                </div>
                <hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.deficit') }}:</div>
                    <div class="unit__tooltip">
                        <img class="unit__img" src="../../../../content/images/info-icon.jpg" :title="$t('finance-tab.general.deficit-tooltip')" alt="tooltip">
                    </div>
                    <div v-text="deficit(prevYear).toLocaleString('fr-FR') + ' €'" class="money"></div>
                </div>
                <hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.availability') }}:</div>
                    <div class="unit__tooltip">
                        <img class="unit__img" src="../../../../content/images/info-icon.jpg" :title="$t('finance-tab.general.availability-tooltip')" alt="tooltip">
                    </div>
                    <div v-text="availability(prevYear).toLocaleString('fr-FR') + ' €'" class="money"></div>
                </div>
                <br>
                <br>
                <div class="title">{{ $t('finance-tab.general.people') }}</div>
                <hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.share') }}:</div>
                    <div v-text="share(prevYear).toLocaleString('fr-FR') + ' €'" class="money"></div>
                </div>
                <hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.participation') }}:</div>
                    <div v-text="participation(prevYear).toLocaleString('fr-FR') + ' €'" class="money"></div>
                </div>
                <hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.profit') }}:</div>
                    <!--                //TODO Not found. Remove?-->
                    <div>-</div>
                </div>
                <br>
                <br>
                <div class="title">{{ $t('finance-tab.general.external-growth') }}</div>
                <hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.goodwill') }}:</div>
                    <div class="unit__tooltip">
                        <img class="unit__img" src="../../../../content/images/info-icon.jpg" :title="$t('finance-tab.general.goodwill-tooltip')" alt="tooltip">
                    </div>
                    <div v-text="goodwill(prevYear) ? $t('finance-tab.general.yes') : $t('finance-tab.general.no')"
                         :class="{green: goodwill(prevYear), red: !goodwill(prevYear)}">
                    </div>
                </div>
                <hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.badwill') }}:</div>
                    <div class="unit__tooltip">
                        <img class="unit__img" src="../../../../content/images/info-icon.jpg" :title="$t('finance-tab.general.badwill-tooltip')" alt="tooltip">
                    </div>
                    <div v-text="!goodwill(prevYear) ? $t('finance-tab.general.yes') : $t('finance-tab.general.no')"
                         :class="{green: !goodwill(prevYear), red: goodwill(prevYear)}">
                    </div>
                </div>
                <br>
                <br>
                <div class="title">{{ $t('finance-tab.general.rnd') }}</div>
                <hr class="hr">
                <div class="unit">
                    <div>{{ $t('finance-tab.general.research-tax-credit') }}:</div>
                    <div class="unit__tooltip">
                        <img class="unit__img" src="../../../../content/images/info-icon.jpg" :title="$t('finance-tab.general.research-tax-credit-tooltip')" alt="tooltip">
                    </div>
                    <div v-text="researchTax(prevYear) < 0 ? researchTax(prevYear).toLocaleString('fr-FR') + ' €' : $t('finance-tab.general.no')" class="money"></div>
                </div>
                <br>
            </div>
            <div v-else>{{ $t('competitor.bottom-menu.info.no-finance') }}</div>
        </div>
        <b-modal id="edit-finance-page" hide-footer lazy>
            <span slot="modal-title" id="edit-finance-title" class="sign-in-title">
                <img class="logo-img" src="../../../../content/images/logo2.svg" alt="K">
                <span class="tit">{{ $t('finance-tab.general.finance-head-edit') }}</span></span>
            <Question />
            <edit-finance-modal :headFinance="headFinance" ></edit-finance-modal>
        </b-modal>
    </div>
</template>
<script lang="ts" src="./finance-general.component.ts">
</script>

<style scoped lang="scss">
    .finance-container {
        padding-top: 0;
        padding-left: 0;
    }
    .title {
        font-size: 20px;
        font-weight: 600;
    }
    .unit {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        font-size: 12px;

        &__tooltip {
            width: 15px;
            height: 16px;
            margin-right: auto;
            transform: translateY(-7px);
        }

        &__img {
            width: 100%;
            height: 100%;
            object-fit: contain;
        }
    }
    .legend-el {
        display: flex;
        flex-direction: row;
        flex-wrap: nowrap;
        align-items: baseline;
    }
    .legend-color {
        width: 10px;
        height: 10px;
        margin-left: 5px;
    }
    .red-bg {
        background-color: #ff0000;
    }
    .grey-bg {
        background-color: #a6a6a6;
    }
    .bold {
        font-weight: bold;
    }
    .hr {
        margin-top: 0.5rem!important;
        margin-bottom: 0.5rem!important;
    }
    .red {
        color: red;
    }
    .green {
        color: green;
    }
    .vertical-line {
        height: 50px;
        border: 1px solid #D6DADF;
        margin: 30px 5% 0 50px;
    }
    .finance-ratings {
        display: flex;
        flex-direction: row;
        align-items: flex-start;
        margin-bottom: 20px;
    }
    .suggest_link {
        color: #6b48ff;
        font-size: 14px;
        cursor: pointer;
        margin: auto 25px 0 auto;
        width: min-content;
        white-space: nowrap;
    }
    .suggest_link:hover{
        text-decoration: underline;
    }
    .sign-in-title {
        font-size: 24px;
        font-weight: 600;
        padding-left: 20px;
    }
    .logo-img {
        width: 40px;
        height: 40px;
        margin-bottom: 30px;
        margin-top: 30px;
    }
    .tit {
        margin-left: 20px;
        white-space: nowrap;
        font-size: 20px;
    }
    .money {
        letter-spacing: 1px;
    }
</style>
