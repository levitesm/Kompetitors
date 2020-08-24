<template>
  <div id="app" class="app-class">
    <ribbon></ribbon>
    <div id="app-header" class="app-header">
      <jhi-navbar></jhi-navbar>
    </div>

      <div v-show="loading">
          <CustomSpinner />
      </div>
      <div v-show="!loading">
          <div class="container-fluid">
              <div class="card jh-card">
                  <router-view></router-view>
              </div>
              <b-modal id="login-page" hide-footer lazy>
                  <span slot="modal-title" id="login-title" class="sign-in-title">
                      <img class="logo-img" src="../content/images/logo2.svg" alt="K">
                      <span class="tit">{{ $t('app.signin') }}</span>
                  </span>
                  <login-form></login-form>
              </b-modal>
              <b-modal id="edit-reps-page" hide-footer lazy>
                  <span slot="modal-title" id="edit-reps-title" class="sign-in-title">
                      <img class="logo-img" src="../content/images/logo2.svg" alt="K">
                      <span class="tit">Edit Representatives info</span>
                  </span>
                  <Question />
                  <edit-reps></edit-reps>
              </b-modal>
              <b-modal id="edit-hr-page" hide-footer lazy>
                  <span slot="modal-title" id="edit-hr-title" class="sign-in-title">
                      <img class="logo-img" src="../content/images/logo2.svg" alt="K">
                      <span class="tit">Edit HR Info</span>
                  </span>
                  <Question />
                  <edit-hr></edit-hr>
              </b-modal>
              <b-modal id="edit-hr-employees" hide-footer lazy>
                  <span slot="modal-title" id="edit-hr-employees" class="sign-in-title">
                      <img class="logo-img" src="../content/images/logo2.svg" alt="K">
                      <span class="tit">Edit employees number</span>
                  </span>
                  <Question />
                  <edit-hr-employees></edit-hr-employees>
              </b-modal>
              <b-modal id="add-agency" hide-footer lazy>
                  <span slot="modal-title" id="add-agency" class="sign-in-title">
                      <img class="logo-img" src="../content/images/logo2.svg" alt="K">
                      <span class="tit">{{ $t('app.new-agency') }}</span>
                  </span>
                  <Question />
                  <add-agency></add-agency>
              </b-modal>
              <b-modal id="add-competitor" hide-footer lazy>
                  <span slot="modal-title" id="add-competitor" class="sign-in-title">
                      <img class="logo-img" src="../content/images/logo2.svg" alt="K">
                      <span class="tit">{{ $t('app.new-competitor') }}</span>
                  </span>
                  <Question />
                  <add-competitor></add-competitor>
              </b-modal>
              <jhi-footer></jhi-footer>
          </div>
      </div>

  </div>
</template>

<script lang="ts" src="./app.component.ts">
</script>

<style scoped>
.app-header {
    margin: 0;
    padding: 0;
}
.app-class {
    max-width:1360px;
    margin: auto;
}
.container-fluid {
    background-color: #ecf0f3 !important;
    padding: 0!important;
    margin-top: 0!important;
}
.jh-card {
    background-color: #ecf0f3 !important;
    margin: 0;
    padding: 0;
    border: none!important;
}
.sign-in-title {
    font-size: 24px;
    font-weight: 600;
    padding-left: 20px;
}
.logo-img {
    width: 40px;
    height: 40px;
    margin-bottom: 30px;
    margin-top: 30px;
}
.tit {
    margin-left: 20px;
}
</style>
