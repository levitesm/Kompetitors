<template>
    <div class="modal-body sign">
        <div class="">
            <div class="">
                <b-form role="form" v-on:submit.prevent="saveChanges()">

                    <div class="comp-tit">{{competitor.name}}</div>
                    <hr>

                    <b-form-group>
                        <div class="itm-txt">{{ $t('clients-tab.general.client-name') }}</div>
                        <b-form-input class="edit_main_input" id="city" type="text" name="city" v-model="newClient.name">
                        </b-form-input>
                    </b-form-group>

                    <b-form-group>
                        <div class="itm-txt">{{ $t('clients-tab.general.partners-since') }}</div>
                        <b-form-input class="edit_main_input" id="city" type="text" name="city" v-model="newClient.since">
                        </b-form-input>
                    </b-form-group>

                    <b-form-group>
                        <div class="itm-txt">{{ $t('clients-tab.general.agency') }}</div>
                        <select class="form-control edit_main_input" id="offices" name="competitors" v-model="selectedOffice" >
                            <option v-bind:value="officeOption" v-for="officeOption in officeList" :key="officeOption.id">
                                {{ officeOption.cityAsText || officeOption.city.value }}
                            </option>
                        </select>
                    </b-form-group>

                    <b-form-group>
                        <div class="itm-txt">{{ $t('clients-tab.general.client-industry') }}</div>
                        <select class="form-control edit_main_input" id="industries" name="competitors" v-model="selectedInd" >
                            <option v-bind:value="industryOption" v-for="industryOption in indList" :key="industryOption.id">{{getIndustry(industryOption)}}</option>
                        </select>
                    </b-form-group>

                    <b-form-group>
                        <div class="itm-txt">{{ $t('clients-tab.general.project-type') }}</div>
                        <select class="form-control edit_main_input" id="projTypes" name="competitors" v-model="selectedProjType" >
                            <option v-bind:value="projTypeOption" v-for="projTypeOption in projTypeList" :key="projTypeOption.id">{{projTypeOption.value}}</option>
                        </select>
                    </b-form-group>

                    <b-form-group>
                        <div class="itm-txt">{{ $t('clients-tab.general.status') }}</div>
                        <b-form-input class="edit_main_input" id="city" type="text" name="city" v-model="proj.status">
                        </b-form-input>
                    </b-form-group>

                    <b-form-group>
                        <b-form-checkbox id="isippon"  v-model="newClient.isIppon" >
                            <span class="checkB">{{ $t('clients-tab.general.ippons-client') }}</span>
                        </b-form-checkbox>
                    </b-form-group>

                    <div>
                        <b-alert show variant="danger" v-if="savingError">
                            <strong>{{ $t('add-agency.save-failed') }}</strong>
                            <p>{{savingError}}</p>
                        </b-alert>
                    </div>

                    <b-button type="submit" variant="primary" class="login">{{ $t('add-agency.save') }}</b-button>
                </b-form>
                <p></p>
            </div>
        </div>
    </div>
</template>
<script lang="ts" src="./add-client.component.ts">
</script>
<style scoped>
.edit_main_input {
    height: 48px!important;

    display: inline-block!important;
}
.sn_logo_pic {
    height: 40px;
    width: 40px;
}
.comp-tit {
    color: #6b48ff;
    font-size: 24px;
    font-weight: 600;
}
.itm-txt {
    font-weight: 600;
}
.login {
    margin-top: 35px;
    background-color: #6b48ff;
    color: #ffffff;
    width: 100%;
    height: 40px;
    border-radius: 3px;
    border-color: #6b48ff;
}
.modal-body {
    width: 100%;
    padding-top: 0!important;
}
.sign {
    width: 465px;
}
</style>
