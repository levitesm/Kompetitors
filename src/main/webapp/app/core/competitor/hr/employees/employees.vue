<template>
    <div>
        <div class="legend-el">
            <div>{{ $t('finance-tab.general.workforce') + " " + $t('finance-tab.general.chart') }}<div class="legend-color grey-bg"></div></div>
            <div class="icon-edit suggest-changes" @click="showEditEmployeesNumber()">&nbsp;&nbsp;{{ $t('hr-tab.general.suggest-changes-employees') }}</div>
        </div>
        <div>
            <employees-chart></employees-chart>
        </div>
        <br>
        <div>
            <div class="title">{{ $t('finance-tab.general.salaries')}}</div>
            <br>
            <table>
                <tr>
                    <th>{{ $t('finance-tab.salaries.seniority') }}</th>
                    <th>{{ $t('finance-tab.salaries.salary') }}</th>
                    <th>{{ $t('finance-tab.salaries.update-by') }}</th>
                    <th>{{ $t('finance-tab.salaries.update-date') }}</th>
                    <th></th>
                </tr>
                <tr class="t-body" v-for="(item, index) in salaries">
                    <td>{{ $t('finance-tab.salaries.seniority-' + item.seniority) }}</td>
                    <td>
                        <span v-if="!getEditFlags(index)">{{ item.salary }}</span>
                        <span v-if="getEditFlags(index)">
                        <b-form-input class="input" id="input" type="text" name="in" v-model="item.salary"></b-form-input>
                        <b-button class="but" @click="save(index)">Save</b-button>
                        </span>
                    </td>
                    <td>{{ item.updatedBy }}</td>
                    <td>{{ item.updateDate?getDate(item.updateDate):$t('finance-tab.salaries.never') }}</td>
                    <td>
                        <span class="icon-edit ico" v-if="hasAccess(HR_EDIT)"
                              @click="edit(index)">
                        </span>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</template>

<script lang="ts" src="./employees.component.ts">
</script>

<style scoped>
    .legend-el {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: baseline;
    }
    .legend-color {
        width: 10px;
        height: 10px;
        margin-left: 5px;
        display: inline-block;
    }
    .red-bg {
        background-color: #ff0000;
    }
    .grey-bg {
        background-color: #a6a6a6;
    }
    .suggest-changes {

    }
    .title {
        font-size: 20px;
        font-weight: 600;
    }
    table {
        width: 100%;
    }
    .t-body {
        border-top: 1px solid rgba(0, 0, 0, 0.1);
    }
    td {
        padding-top: 0.5rem!important;
        padding-bottom: 0.5rem!important;
        font-size: 12px;
    }
    th {
        font-weight: 600;
        font-size: 12px;
    }

    .input {
        width: 50%;
        height: 30px;
        font-size: 12px;
        display: inline-block;
    }
    .but {
        background-color: #6b48ff;
        color: #ffffff;
        width: 39%;
        height: 30px;
        border-radius: 3px;
        border-color: #6b48ff;
        border-width: 0;
        font-size: 14px;
        font-weight: 600;
        margin-top: 0px;
    }
    .but:hover{
        background-color: #6242eb;
    }
    .ico {
        cursor: pointer;
    }

</style>
