<template>
    <div>
        <div class="icon-edit suggest_ga"
             :class="{'no-access': !hasAccess(PR_EDIT)}"
             @click="showAddFeed()">
            &nbsp;&nbsp;{{ $t('pr-tab.google-alerts.set-rss') }}
        </div>
        <div v-if="loading">
            <custom-spinner></custom-spinner>
        </div>
        <div v-else>
            <div v-if="externalUrls.googleAlertsFeed && title">
                <div class="alerts-title">{{ title }}</div>
                <div class="alerts-date" v-if="buildDate">{{ $t('pr-tab.google-alerts.build-date') + getDate(buildDate) }}</div>
                <div v-if="items && items.length > 0">
                    <br>
                    <div v-for="item in items" :key="item.id">
                        <a :href="item.link" v-html="item.title" target="_blank"></a>
                        <div v-html="item.content"></div>
                        <div class="alerts-date">{{ getDate(item.pubDate) }}</div>
                        <br>
                    </div>
                </div>
                <div v-else>
                    <div>{{ $t('pr-tab.google-alerts.not-found') }}</div>
                </div>
            </div>
            <div v-else>
                <div>{{ $t('pr-tab.google-alerts.not-set') }}</div>
            </div>
        </div>
        <google-alerts-edit-modal @saved="parseFeed"></google-alerts-edit-modal>
    </div>
</template>

<script lang="ts" src="./google-alerts.component.ts">
</script>

<style scoped>
    .suggest_ga {
        color: #6b48ff;
        font-size: 14px;
        text-align: right;
        width: 100%;
        cursor: pointer;
    }
    .suggest_ga:hover {
        text-decoration: underline;
    }
    .alerts-title {
        font-weight: 600;
        font-size: 20px;
    }
    .alerts-date {
        color: #909090;
        font-size: 12px;
        font-weight: normal;
    }
</style>
