<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.clientsProjects.home.createOrEditLabel" v-text="$t('kompetitors2App.clientsProjects.home.createOrEditLabel')">Create or edit a ClientsProjects</h2>
                <div>
                    <div class="form-group" v-if="clientsProjects.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="clientsProjects.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.clientsProjects.status')" for="clients-projects-status">Status</label>
                        <input type="text" class="form-control" name="status" id="clients-projects-status"
                            :class="{'valid': !$v.clientsProjects.status.$invalid, 'invalid': $v.clientsProjects.status.$invalid }" v-model="$v.clientsProjects.status.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.clientsProjects.projectType')" for="clients-projects-projectType">Project Type</label>
                        <select class="form-control" id="clients-projects-projectType" name="projectType" v-model="clientsProjects.projectType">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="clientsProjects.projectType && listClientsProjectTypesOption.id === clientsProjects.projectType.id ? clientsProjects.projectType : listClientsProjectTypesOption" v-for="listClientsProjectTypesOption in listClientsProjectTypes" :key="listClientsProjectTypesOption.id">{{listClientsProjectTypesOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.clientsProjects.clients')" for="clients-projects-clients">Clients</label>
                        <select class="form-control" id="clients-projects-clients" name="clients" v-model="clientsProjects.clients">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="clientsProjects.clients && clientsOption.id === clientsProjects.clients.id ? clientsProjects.clients : clientsOption" v-for="clientsOption in clients" :key="clientsOption.id">{{clientsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.clientsProjects.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./clients-projects-update.component.ts">
</script>
