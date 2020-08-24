<template>
    <b-modal id="edit-locations" scrollable lazy>
        <span slot="modal-title" class="edit-locations-header">
            <img class="title-img" src="../../../../content/images/logo2.svg" alt="K">
            <span>{{ $t('legal-tab.locations.edit-locations-header') }}</span>
        </span>
        <div v-for="competitor in competitorsInGroup">
            <div class="offices">
                <table height="100%">
                    <tr v-for="office in competitor.offices" class="office">
                        <td class="icon-cell">
                            <font-awesome-icon v-bind:id="'icon-' + office.id" v-if="displayDeleteIconFor(office) && !hasClients(office)" icon="times" class="icon icon-close"
                                               @click="addOfficeToBeDeleted(office)"/>

                            <font-awesome-icon v-bind:id="'icon-disabled-' + office.id" v-if="displayDeleteIconFor(office) && hasClients(office)" icon="times" class="icon icon-close icon-disabled"/>

                            <font-awesome-icon v-bind:id="'icon-' + office.id" v-if="isToBeDeleted(office)" icon="undo" class="icon icon-undo"
                                               @click="removeOfficeToBeDeleted(office)"/>

                            <b-tooltip :target="'icon-disabled-' + office.id" variant="danger" placement="topright">{{ $t('legal-tab.locations.edit-locations-delete-tooltip', {clientsNumber:office.clients.length}) }}</b-tooltip>
                        </td>
                        <td>
                            <span v-bind:class="isToBeDeleted(office) ? 'to-be-deleted' : ''">
                                <b>{{competitor.name}}</b>: {{office.post}} {{office.cityAsText}}
                            </span>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <b-button slot="modal-footer" variant="primary" class="login" v-on:click="save">
            {{ $t('edit-pr.save') }}
        </b-button>
    </b-modal>
</template>

<script lang="ts" src="./edit-locations.component.ts">
</script>

<style scoped>
    .icon-close {
        font-size: 24px;
        vertical-align: sub;
    }

    .icon-undo {
        font-size: 16px;
    }

    .icon:hover {
        cursor: pointer;
        color: #6b48ff;
    }

    .icon:focus {
        outline:0;
    }

    .icon-disabled {
        color: #8c8c8c;
    }

    .icon-disabled:hover {
        color: #8c8c8c;
    }

    .offices {
        display: block;
        width: auto;
        font-size: 20px;
    }

    .icon-cell {
        width: 30px;
    }

    .office {
        display: flex;
        align-items: center;
    }

    .to-be-deleted {
        color: #8c8c8c;
    }

</style>
