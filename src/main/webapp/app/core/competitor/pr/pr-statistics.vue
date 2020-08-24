<template>
    <div>
        <div v-if="loading">
            <custom-spinner></custom-spinner>
        </div>
        <div v-else>
            <div class="head-container">
                <h3>{{ $t('pr-tab.statistics.marketing') }}</h3>
                <div class="icon-edit head-container__link"
                     :class="{'no-access': !hasAccess(PR_EDIT)}"
                     @click="showEditPr()">&nbsp;&nbsp;{{ $t('competitor.bottom-main-section.suggest-changes') }}
                </div>
            </div>
            <list-item :name="$t('pr-tab.statistics.marketing-workforce')" :value="prInfo.marketingWorkforce"></list-item>
            <list-item :name="$t('pr-tab.statistics.marketing-budget')" :value="prInfo.marketingBudget" :unit="'€'"></list-item>
            <list-item :name="$t('pr-tab.statistics.experience-feedback')" :value="prInfo.experienceFeedback"></list-item>
            <br>
            <h3>{{ $t('pr-tab.statistics.linkedin') }}</h3>
            <list-item :name="$t('pr-tab.statistics.linkedin-subs')" :value="prInfo.linkedInSubscribers"></list-item>
            <list-item :name="$t('pr-tab.statistics.linkedin-engagement')" :value="prInfo.linkedInEngageRate"></list-item>
            <list-item :name="$t('pr-tab.statistics.linkedin-post-week')" :value="prInfo.linkedInPostWeek"></list-item>
            <list-item :name="$t('pr-tab.statistics.linkedin-post-day')" :value="prInfo.linkedInPostDay"></list-item>
            <br>
            <h3>{{ $t('pr-tab.statistics.twitter') }}</h3>
            <list-item :name="$t('pr-tab.statistics.twitter-followers')" :value="prInfo.twitterFollowers"></list-item>
            <list-item :name="$t('pr-tab.statistics.twitter-posts-week')" :value="prInfo.twitterPostWeek"></list-item>
            <list-item :name="$t('pr-tab.statistics.twitter-posts-day')" :value="prInfo.twitterPostDay"></list-item>
            <br>
            <h3>{{ $t('pr-tab.statistics.instagram') }}</h3>
            <list-item :name="$t('pr-tab.statistics.instagram-followers')" :value="prInfo.instagramFollowers"></list-item>
        </div>
        <edit-pr-statistics-modal :prInfo="prInfo"></edit-pr-statistics-modal>
    </div>
</template>

<script lang="ts" src="./pr-statistics.component.ts">
</script>

<style scoped lang="scss">
    .head-container {
        display: flex;
        align-items: center;
        justify-content: space-between;
        
        &__link {
            color: #6b48ff;
            font-size: 14px;
            cursor: pointer;
        }
    }
</style>
