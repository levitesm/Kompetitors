<template>
    <div>
        <div v-if="!this.selectedOffice">
            <div v-for="con in groupCountries">
                <img class="country" :src="getFlag(con)" @click="selectedCountry=con"/>
            </div>
            <div>
                <div v-for="c in competitorsInGroup">
                    <div v-if="c.country.value === selectedCountry || selectedCountry === ''">
                        <div class="name"> {{c.name}}</div>
                        <div class="offices">
                            <div class="block" v-for="office in c.offices">
                                <div v-text="office.cityAsText" class="city"></div>
                                <div v-text="(office.numberRecruiters?office.numberRecruiters:0) + ' ' + $t('hr-tab.recruiters.menu')"></div>
                                <span class="link" @click="selectOffice(office, c)">{{$t('hr-tab.recruiters.list')}}</span>
                            </div>
                        </div>
                    </div><hr>
                </div>
            </div>
        </div>
        <div v-if="this.selectedOffice">
            <div class="link" @click="back()">
                {{$t('competitor.bottom-menu.dialogs.back')}}
            </div>
            <div class="add_recr"
                 :class="{'no-access': !hasAccess(HR_EDIT)}"
                 @click="showAddRecruiter()">
                &nbsp;&nbsp;+ {{ $t('hr-tab.recruiters.add') }}
            </div>
            <br>
            <div class="name"> {{selectedOffice.cityAsText}}</div>
            <div class="recs" v-text="(selectedOffice.numberRecruiters?selectedOffice.numberRecruiters:0) + ' ' + $t('hr-tab.recruiters.menu')"></div>
            <div class="icon-edit edit-ico" @click="showEdit()"></div>
            <b-form style="display: inline-block">
                <b-form-input v-if="show" class="rec_inp" v-model="selectedOffice.numberRecruiters" :placeholder="'0'"></b-form-input>
                <b-button v-if="show" type="submit" variant="primary" class="recr_but" @click="save()">Save</b-button>
            </b-form>
           <div class="updated" v-if="!show">Updated: {{selectedOffice.recruitersUpdate?(selectedOffice.recruitersUpdate.toString().length>10?selectedOffice.recruitersUpdate.toLocaleDateString():selectedOffice.recruitersUpdate):'Never'}}</div>
            <br><br>
                <div class="block" style="border-style: none" v-for="recruiter in recruitersList">
                    <div class="icon-close del-ico" @click="deleteRecruiter(recruiter)" style="vertical-align: center"></div>
                    <div class="inner_block">
                        <div v-text="recruiter.lName" class="city"></div>
                        <div v-text="recruiter.fName"></div>
                        <a :href="recruiter.linkedPage!==''?recruiter.linkedPage:null" target="_blank" :style="recruiter.linkedPage===''?'color: #a7a7a7; cursor: auto':''">LinkedIn</a>
                    </div>

                </div>
        </div>
        <b-modal id="add-recruiter-page" hide-footer lazy>
            <span slot="modal-title" id="add-recruiter-title" class="sign-in-title">
                <img class="logo-img" src="../../../../content/images/logo2.svg" alt="K">
                <span class="tit">{{ $t('hr-tab.recruiters.add') }}</span></span>
            <Question />
            <add-recruiter-modal :selectedOffice="selectedOffice" ></add-recruiter-modal>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./recruiters.component.ts">
</script>

<style scoped>
    .offices {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        width: auto;
        padding-top: 27px;
        padding-left: 0px;
    }
    .country {
        width: 30px;
        margin-bottom: 20px;
        margin-left: 45px;
        margin-right: 1em;
    }
    .name {
        font-weight: 600;
        font-size: 20px;
        margin-left: 45px;
        text-decoration: underline;
    }
    .block {
        width: 28%;
        margin-left: 45px;
        margin-bottom: 70px;
        padding-left: 10px;
        border-left-style: solid;
        border-width: 1px;
        border-color: #cccccc;
        display: inline-block;
    }
    .inner_block {
        display: inline-block;
        padding-left: 10px;
        border-left-style: solid;
        border-width: 1px;
        border-color: #cccccc;
    }
    .city {
        font-weight: bold;
    }
    .link {
        color: #6B48FF;
    }
    .link:hover {
        text-decoration: underline;
        cursor: pointer;
    }
    .recs {
        margin-left: 45px;
        display: inline-block;
    }
    .edit-ico {
        display: inline-block;
        margin-top: 10px;
        cursor: pointer;
    }
    .del-ico {
        display: inline-block;
        vertical-align: top;
        margin-top: 25px;
        cursor: pointer;
    }
    .rec_inp {
        width: 65px;
        display: inline-block;
    }
    .recr_but {
        background-color: #6B48FF;
        margin-top: -2px;
        margin-left: -2px;
    }
    .updated {
        display: inline-block;
        margin-left: 10px;
        color: #6B48FF;

    }
    .add_recr {
        text-align: right;
        color: #6B48FF;
        cursor: pointer;
    }
    .add_recr:hover {
        text-decoration: underline;
    }
</style>
