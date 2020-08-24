<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="dialogs">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.dialogs.detail.title')">Dialogs</span> {{dialogs.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.dialogs.section')">Section</span>
                    </dt>
                    <dd>
                        <span>{{dialogs.section}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.dialogs.topic')">Topic</span>
                    </dt>
                    <dd>
                        <span>{{dialogs.topic}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.dialogs.message')">Message</span>
                    </dt>
                    <dd>
                        <span>{{dialogs.message}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.dialogs.author')">Author</span>
                    </dt>
                    <dd>
                        <span>{{dialogs.author}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.dialogs.date')">Date</span>
                    </dt>
                    <dd>
                        <span>{{dialogs.date}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.dialogs.competitors')">Competitors</span>
                    </dt>
                    <dd>
                        <div v-if="dialogs.competitors">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorsId: dialogs.competitors.id}}">{{dialogs.competitors.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="dialogs.id" :to="{name: 'DialogsEdit', params: {dialogsId: dialogs.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./dialogs-details.component.ts">
</script>
