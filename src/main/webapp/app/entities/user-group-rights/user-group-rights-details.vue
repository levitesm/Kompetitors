<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="userGroupRights">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.userGroupRights.detail.title')">UserGroupRights</span> {{userGroupRights.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.userGroupRights.userGroupName')">User Group Name</span>
                    </dt>
                    <dd>
                        <span>{{userGroupRights.userGroupName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.userGroupRights.accessKey')">Access Key</span>
                    </dt>
                    <dd>
                        <div v-if="userGroupRights.accessKey">
                            <router-link :to="{name: 'AccessKeyView', params: {accessKeyId: userGroupRights.accessKey.id}}">{{userGroupRights.accessKey.name}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="userGroupRights.id" :to="{name: 'UserGroupRightsEdit', params: {userGroupRightsId: userGroupRights.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./user-group-rights-details.component.ts">
</script>
