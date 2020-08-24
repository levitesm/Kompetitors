<template>
    <div>
        <b-form role="form" v-on:submit.prevent="saveEmployeesNumber()">
            <b-form-group>
                <span class="year-label">{{currentYear-1}} :</span>
                <b-form-input :disabled="hasInfogreffeForYear(currentYear-1)" class="employee-number-textfield" type="number" v-model="lastYearNumber"></b-form-input>
            </b-form-group>
            <b-form-group>
                <span class="year-label">{{currentYear-2}} :</span>
                <b-form-input :disabled="hasInfogreffeForYear(currentYear-2)" class="employee-number-textfield" type="number" v-model="secondToLastYearNumber"></b-form-input>
            </b-form-group>
            <b-form-group>
                <span class="year-label">{{currentYear-3}} :</span>
                <b-form-input :disabled="hasInfogreffeForYear(currentYear-3)" class="employee-number-textfield" type="number" v-model="thirdToLastYearNumber"></b-form-input>
            </b-form-group>
            <b-form-group>
                <span class="year-label">{{currentYear-4}} :</span>
                <b-form-input :disabled="hasInfogreffeForYear(currentYear-4)" class="employee-number-textfield" type="number" v-model="fourthToLastYearNumber"></b-form-input>
            </b-form-group>
            <b-form-group>
                <span class="year-label">{{currentYear-5}} :</span>
                <b-form-input :disabled="hasInfogreffeForYear(currentYear-5)" class="employee-number-textfield" type="number" v-model="fifthToLastYearNumber"></b-form-input>
            </b-form-group>
            <div>
                <b-button type="submit" variant="primary" class="login">{{ $t('edit-competitor-main.save') }}</b-button>
            </div>
        </b-form>
    </div>
</template>
<script lang="ts" src="./edit-hr-employees.component.ts">
</script>
<style scoped>
    .year-label {
        font-weight: 600;
    }
    .employee-number-textfield {
        height: 48px !important;
        display: inline-block !important;
    }

</style>
