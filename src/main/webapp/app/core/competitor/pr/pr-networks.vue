<template>
    <div>
        <div class="item">
            <div class="pic_content">
                <a v-if="externalUrls.facebookUrl" :href="formatUrl(externalUrls.facebookUrl)" target="_blank" >
                    <img src="../../../../content/images/facebook.svg" class="pic">
                </a>
                <img v-if="!this.externalUrls.facebookUrl" src="../../../../content/images/facebook.svg" class="pic_d" >
            </div>
        </div>
        <div class="item">
            <div class="pic_content">
                <a v-if="externalUrls.twitterUrl" :href="formatUrl(externalUrls.twitterUrl)" target="_blank" >
                    <img src="../../../../content/images/twitter.svg" class="pic">
                </a>
                <img v-if="!externalUrls.twitterUrl" src="../../../../content/images/twitter.svg" class="pic_d" >
            </div>
        </div>
        <div class="item">
            <div class="pic_content">
                <a v-if="externalUrls.linkedinUrl" :href="formatUrl(externalUrls.linkedinUrl)" target="_blank" >
                    <img src="../../../../content/images/linkedin.svg" class="pic">
                </a>
                <img v-if="!externalUrls.linkedinUrl" src="../../../../content/images/linkedin.svg" class="pic_d" >
            </div>
        </div>
        <div class="item">
            <div class="pic_content">
                <a v-if="externalUrls.instagramUrl" :href="formatUrl(externalUrls.instagramUrl)" target="_blank" >
                    <img src="../../../../content/images/instagram.svg" class="pic">
                </a>
                <img v-if="!externalUrls.instagramUrl" src="../../../../content/images/instagram.svg" class="pic_d" >
            </div>
        </div>
        <div class="item">
            <div class="pic_content">
                <a v-if="externalUrls.youtubeUrl" :href="formatUrl(externalUrls.youtubeUrl)" target="_blank" >
                    <img src="../../../../content/images/youtube.svg" class="pic">
                </a>
                <img v-if="!externalUrls.youtubeUrl" src="../../../../content/images/youtube.svg" class="pic_d" >
            </div>
        </div>
        <div class="item">
            <div class="pic_content">
                <a v-if="externalUrls.githubUrl" :href="formatUrl(externalUrls.githubUrl)" target="_blank" >
                    <img src="../../../../content/images/github.svg" class="pic"></a>
                <img v-if="!externalUrls.githubUrl" src="../../../../content/images/github.svg" class="pic_d" >
            </div>
        </div>
        <br>
        <div class="icon-edit suggest-changes"
             :class="{'no-access': !hasAccess(PR_EDIT)}"
             @click="showEditPr()">
            &nbsp;&nbsp;{{ $t('competitor.bottom-main-section.suggest-changes') }}
        </div>
        <span class="clear-block"></span>
        <hr>
        <br>
        <div class="blog-header">{{competitor.name}} blog</div>
        <div v-if="BLOG_STATUS === 'OK'" class="blog-desc">
            {{BLOG.description}}
            <br>
            <br>
        </div>
        <div v-if="BLOG_STATUS === 'NOTSET'" class="blog-desc">
            {{ $t('competitor.bottom-menu.info.blog-not-set') }}
            <br>
            <br>
        </div>
        <div v-if="BLOG_STATUS === 'ERROR'" class="blog-desc">
            {{ $t('competitor.bottom-menu.info.blog-error') }}
            <br>
            <br>
        </div>
        <div class="blog-feed" v-if="BLOG_STATUS === 'OK'">
            <div class="blog-item" v-for="(item, i) in BLOG.items" v-if="i<5">
                <div class="blog-item-title">
                    <a target="_blank" :href="item.link">{{item.title}}</a>
                </div>
                <div class="blog-item-by">
                    by {{item.creator}} on {{item.pubDate.substring(0,item.pubDate.indexOf('+'))}}
                </div>
                <br>
                <div class="blog-item-body">{{item.contentSnippet}}</div>
                <hr>
                <br>
            </div>
        </div>
        <edit-pr></edit-pr>
    </div>
</template>

<script lang="ts" src="./pr-networks.component.ts">
</script>

<style scoped>
    .pic {
        height: 50px;
        width: 50px;
    }
    .pic_d {
        height: 50px;
        width: 50px;
        filter: invert(50%);
    }
    .item {
        display: inline-block;
        width: 15%;
    }
    .pic_content {
        text-align: center;
    }
    .suggest-changes {
        margin-top: 16px;
        margin-bottom: 0px;
    }
    .blog-header {
        font-size: 30px;
        font-weight: 600;
        text-decoration: underline;
    }
    .blog-desc {
        font-size: 20px;
    }
    .blog-feed {

    }
    .blog-item {
        padding-left: 20px;
    }
    .blog-item-title {
        font-size: 25px;
        font-weight: 600;
    }
    .blog-item-by {
        text-decoration: underline;
    }
</style>
