<template>
    <div class="home row">

        <div class="part1">
            <h2 class="home-text1" v-if="username" v-text="$t('home.title', { 'username': username})">Welcome, xxx!</h2>
            <h2 class="home-text1"  v-if="!username">{{ $t('home.sign-in') }}</h2>
            <h2 class="home-text1"  v-if="username">{{ $t('home.business.find') }}</h2>



            <form v-if="username" class="form-class" @submit.prevent="search()" >
                <div class="form-div-block">
                    <label for="what">{{ $t('home.business.what') }}</label >
                    <br><input id="what" class="form-input" :placeholder="$t('home.business.what-field')" v-model="what_field">
                </div>
                <div class="form-div-block">
                    <label for="where">{{ $t('home.business.where') }}</label>
                    <br><input id="where" class="form-input" :placeholder="$t('home.business.where-field')" v-model="where_field">
                </div>


                <br>

                <input type="submit" class="submit-button" :value="$t('home.business.search')">

            </form>


        </div>
    </div>
</template>

<script lang="ts" src="./home.component.ts">
</script>

<style scoped>
    .home {
        background-color: #ecf0f3;
        margin-left: 30px;
        margin-right: 30px;
    }
    .part1{
        padding-left: 20%!important;
        padding-top: 2em;
    }
    .home-text1{
        font-weight: 700;
    }

    .form-class {
        width: 70%;
        margin-top: 2em;
    }
    .form-div-block {
        display: inline-block;
        width: 370px;
        margin-right: 20px !important;
        margin-left: 0!important;
    }
    .form-input {
        width: 100%;
        height: 3em;
        padding-left: 1em;
        background-color: #ecf0f3;
        border-style: solid;
        border-radius: 3px;
        border-color: #cccccc;
    }

    .submit-button {
        background-color: #6b48ff;
        color: #ffffff;
        margin-top: 1em;
        border-style: solid;
        border-radius: 3px;
        border-color: #6b48ff;
        height: 3em;
        width: 15%;
        font-weight: bolder;
    }
</style>
