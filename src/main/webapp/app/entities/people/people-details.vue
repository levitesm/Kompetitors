<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="people">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.people.detail.title')">People</span> {{people.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.people.fName')">F Name</span>
                    </dt>
                    <dd>
                        <span>{{people.fName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.people.lName')">L Name</span>
                    </dt>
                    <dd>
                        <span>{{people.lName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.people.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{people.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.people.linkedPage')">Linked Page</span>
                    </dt>
                    <dd>
                        <span>{{people.linkedPage}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.people.isKey')">Is Key</span>
                    </dt>
                    <dd>
                        <span>{{people.isKey}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.people.competitors')">Competitors</span>
                    </dt>
                    <dd>
                        <div v-if="people.competitors">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorsId: people.competitors.id}}">{{people.competitors.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="people.id" :to="{name: 'PeopleEdit', params: {peopleId: people.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./people-details.component.ts">
</script>
