<template>
    <div>
        <div class="icon-edit suggest" :class="{'no-access': !hasAccess(LEGAL_EDIT)}"
             @click="openEditModal">&nbsp;&nbsp;{{ $t('legal-tab.locations.suggest-changes') }}
        </div>
        <div class="country-container">
            <div v-for="con in groupCountries" class="country-block" @click="selectedCountry=con"
                 :style="(con===selectedCountry) ? 'border-color: #1ee3cf':'border-color: transparent'">
                <div class="flag-container">
                    <img class="country" :src="getFlag(con)" />
                </div>
                <span class="country-block__name">{{ con }}</span>
            </div>
        </div>
        <div>
            <div v-for="c in competitorsInGroup">
                <div v-if="c.country.value === selectedCountry || selectedCountry === ''">
                    <div class="name"> {{c.name}}</div>
                    <div class="offices">
                        <legal-agency
                            v-for="office in c.offices"
                            :key="office.id"
                            :office="office"
                            :country="selectedCountry">
                        </legal-agency>
                    </div>
                    <hr>
                </div>
            </div>
        </div>
        <edit-locations></edit-locations>
    </div>
</template>

<script lang="ts" src="./legal-agencies.component.ts">
</script>

<style scoped lang="scss">
    .offices {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        width: auto;
        padding-top: 27px;
        padding-left: 0;
    }
    
    .flag-container {
        width: 30px;
        height: 30px;
    }
    
    .country {
        width: 100%;
        height: 100%;
        object-fit: contain;
    }
    
    .country-container {
        display: flex;
        margin-bottom: 3rem;
    }
    
    .country-block {
        display: flex;
        flex-direction: column;
        cursor: pointer;
        margin-left: 2rem;
        border-bottom: 3px solid transparent;
        align-items: center;
        
        &__name {
            font-size: .6rem;
            text-transform: capitalize;
        }
    }
    
    .name {
        font-weight: 600;
        font-size: 20px;
        margin-left: 45px;
        text-decoration: underline;
    }
    
    .suggest {
        color: #6b48ff;
        font-size: 14px;
        display: inline-block;
        text-align: right;
        width: 97%;
        cursor: pointer;
    }
    
    .suggest:hover {
        text-decoration: underline;
    }
</style>
