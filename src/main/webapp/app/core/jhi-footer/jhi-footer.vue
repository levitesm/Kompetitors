<template>
    <div id="footer" class="footer">
       <div class="footer__items">
           <span>&copy; 2019 Kompetitors Inc.</span>
           <span  class="footer-item" @click=termsPage()>{{ $t('footer.terms') }}</span>
           <span  class="footer-item" @click=privacyPage()>{{ $t('footer.privacy') }}</span>
           <span  class="footer-item" @click=securityPage()>{{ $t('footer.security') }}</span>
           <span  class="footer-item" @click=helpPage()>{{ $t('footer.help') }}</span>
       </div>

        <div id="lang-switch">
            <img src="/content/icons/united-states.png" @click="onLanguageChange('en')"/>
            <img src="/content/icons/flag-fr.png" @click="onLanguageChange('fr')"/>
            <!-- <img src="/content/icons/flag-ru.png" @click="onLanguageChange('ru')"/> -->
        </div>

    </div>
</template>

<script lang="ts" src="./jhi-footer.component.ts">
</script>

<style scoped>
    .footer {
        text-align: left;
        height: 300px;
        vertical-align: center !important;
        padding-top: 20px;
        padding-left: 7.5em;
        margin-top: 10em;
        display: flex;
        flex-direction: row;
        flex-flow: row wrap;
    }

    .footer__items {
        max-width: 80%;
        display: flex;
        flex-direction: row;
        flex-flow: row wrap;
        height: min-content;
    }
    
    .footer-item{
        margin-left: 30px;
        color: #6b48ff;
    }

    .footer-item:hover{
        cursor: pointer;
        text-decoration: underline;
    }

    #lang-switch {
        cursor: pointer;
        text-decoration: none;
        margin-right: 1.5em;
        margin-left: auto;
    }

    #lang-switch img {
        width: 1.5rem;
    }

    @media screen and (max-width: 1300px) {
        .footer {
            margin-top: 2rem;
            padding-left: 1.5rem;
        }
    }
</style>
