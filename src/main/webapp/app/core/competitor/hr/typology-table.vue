<template>
    <div>
        <div class="edit">
            <h3>{{ $t('hr-tab.typologies.table-header') }}</h3>
            <div v-if="editMode" class="edit__buttons">
                <span v-if="saving">Saving...</span>
                <button v-else class="edit__btn edit__btn--save" @click="save">{{ $t('add-agency.save') }}</button>
                <button class="edit__btn edit__btn--cancel" @click="editMode = false">{{ $t('competitor.bottom-menu.dialogs.add-cancel') }}</button>
            </div>
            <div v-else class="icon-edit edit__link" :class="{'no-access': !hasAccess(HR_EDIT)}" @click="prepareModel">&nbsp; {{ $t('competitor.bottom-main-section.suggest-changes') }}</div>
        </div>
        <div class="table">
            <div class="table__row table__header">
                <div class="table__column">{{ $t('hr-tab.typologies.name') }}</div>
                <div class="table__column" v-for="year in years">{{ year + ' ' + $t('hr-tab.typologies.year') }}</div>
            </div>
            <div v-for="name in typologyNames">
                <hr class="table__hr">
                <div class="table__row">
                    <div class="table__column">{{ localisationFromName(name) }}</div>
                    <div class="table__column" v-for="year in years">
                        <the-mask v-if="editMode" class="table__input" type="text"
                                  v-model="modelByNameAndYear(name, year).value" mask="#########">
                        </the-mask>
                        <span v-else>{{ valueByNameAndYear(name, year) }}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./typology-table.component.ts">
</script>

<style scoped lang="scss">
    .edit {
        width: 100%;
        display: flex;
        align-items: self-end;
        min-height: 40px;
        
        &__link {
            color: #6b48ff;
            margin-left: auto;
            margin-right: 3rem;
            font-size: 14px;
            cursor: pointer;
            
            &:hover {
                text-decoration: underline;
            }
        }
        
        &__buttons {
            margin-left: auto;
            margin-right: 2rem;
            display: flex;
            align-items: center;
        }
        
        &__btn {
            min-width: 80px;
            max-width: 130px;
            height: 40px;
            border-radius: 3px;
            line-height: 22px;
            font-size: 14px;
            font-weight: bold;
            text-transform: uppercase;
            
            &--save {
                background-color: #6b48ff;
                color: #ffffff;
                border: 2px solid #6b48ff;
            }
            
            &--cancel {
                background-color: #ffffff;
                color: #6b48ff;
                border: 2px solid #B6C5D1;
                margin-left: 1rem;
            }
        }
    }
    
    .table {
        margin-top: 1rem;
        
        &__row {
            display: flex;
            flex-direction: row;
            font-size: 12px;
        }
        
        &__header {
            font-weight: 600;
        }
        
        &__column {
            flex: 1;
            text-align: center;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        
        &__hr {
            margin: .5rem 0;
        }
        
        &__input {
            width: 80%;
            font-size: 1rem;
            font-weight: 400;
            line-height: 1.5;
            color: #495057;
            background-color: #fff;
            background-clip: padding-box;
            border: 1px solid #ced4da;
            border-radius: 0.15rem;
        }
    }
</style>
