<template>
    <div class="modal-body sign">
        <div class="">
            <div class="">
                <div class="title">
                    LinkedIn URLs:
                </div>
                <hr><br>
                <b-form role="form" v-on:submit.prevent="saveChanges()">
                    <b-form-group v-for="r in this.representatives" :key="r.id" v-if="!r.old && r.type === 'PP'">
                        <div class="itm-txt">{{r.prenom + ' ' + r.nom + ' ' + (r.nomUsage ? r.nomUsage : '')}}</div>
                        <b-form-input class="edit_pr_input" id="facebook" type="text" name="facebook" v-model="r.linkedInUrl">
                        </b-form-input>
                    </b-form-group>

                    <div class="">
                        <b-alert show variant="danger" v-if="savingError">
                            <strong>{{ $t('edit-pr.failed-save') }}</strong>
                            <p>{{savingError}}</p>
                        </b-alert>
                    </div>
                    <div>
                        <b-button type="submit" variant="primary" class="login">{{ $t('edit-pr.save') }}</b-button>
                    </div>
                </b-form>
                <p></p>
            </div>
        </div>
    </div>
</template>
<script lang="ts" src="./edit-reps.component.ts">
</script>
<style scoped>
.edit_pr_input {
    height: 48px!important;
    display: inline-block!important;
}
.login {
    margin-top: 35px;
    background-color: #6b48ff;
    color: #ffffff;
    width: 100%;
    height: 40px;
    border-radius: 3px;
    border-color: #6b48ff;
}
.itm-txt {
    font-weight: 600;
}
.title {
    font-size: 20px;
    font-weight: 600;
}
</style>
