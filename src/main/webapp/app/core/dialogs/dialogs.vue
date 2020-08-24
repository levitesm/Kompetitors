<template>
   <div>
      <div v-if="!selectedDialog">

          <div class="title d_inner" style="width: 50%">
              {{$t('competitor.bottom-menu.dialogs.menu')}}
          </div>
          <div class="add_message" style="width: 49%!important;" @click="showAddTopic()">
              {{$t('competitor.bottom-menu.dialogs.add-dialog')}}
            </div>
          <br><br>
          <div class="add_topic_section d_inner" v-if="showAddDialog">
              <b-form role="form" v-on:submit.prevent="saveTopic()">
                  <b-form-group>
                      <div class="itm-txt">{{$t('competitor.bottom-menu.dialogs.new-topic')}}:</div>
                      <b-form-input class="edit_main_input" id="cometitor-name" type="text" name="facebook" v-model="newTopic"></b-form-input>
                  </b-form-group>
                  <b-form-group>
                      <div class="itm-txt">{{$t('competitor.bottom-menu.dialogs.new-comment')}}:</div>
                      <ckeditor :editor="editor" v-model="newMessage" :config="editorConfig"></ckeditor>
                  </b-form-group>

                  <div class="">
                      <b-alert show variant="danger" v-if="savingError">
                          <strong>{{$t('competitor.bottom-menu.dialogs.add-dialog-failure')}}</strong>
                          <p>{{savingError}}</p>
                      </b-alert>
                  </div>
                  <div>
                      <b-button type="submit" variant="primary" class="login">{{$t('competitor.bottom-menu.dialogs.confirm-add-dialog')}}</b-button>
                      <b-button class="cancel" @click="cancel()">{{$t('competitor.bottom-menu.dialogs.add-cancel')}}</b-button>
                      <img class="close-ico" src="../../../content/icons/attached-b.svg">
                      <div style="display: inline-block; vertical-align: top;" >
                          <b-form role="form">
                              <b-form-file style="width: 400px" size="sm" :placeholder="this.attachPlaceHolder" v-model="attachedFile" @input="attach()"></b-form-file>
                          </b-form>
                      </div>
                  </div>
              </b-form>
              <br><br>
          </div>
          <div v-for="d in shownDialogs">
              <div class="d_inner">
                  <div class="d_title" @click="selectDialog(d)">
                      {{ (d.topic === '@QUICK')?$t('competitor.bottom-menu.dialogs.quick'):d.topic  }}
                  </div>

                  <div class="d_message" v-html="d.message">
                  </div>

                  <div class="d_created" >
                      {{$t('competitor.bottom-menu.dialogs.commented-by')}} <span style="font-weight: 600;">{{d.author}}</span> {{$t('competitor.bottom-menu.dialogs.on-date')}} {{d.date.toString().substring(0,d.date.toString().lastIndexOf(':')).replace('T', $t('competitor.bottom-menu.dialogs.at-time'))}}
                  </div>
                  <div class="d_comments">
                       {{numberAttachments(d.topic)}} <img class="close-ico" style="margin-right: 5px; margin-left: 0px;" src="../../../content/icons/attached-b.svg"> {{numberComments(d.topic)}} <img src="../../../content/icons/comment.svg" class="comm_ico">
                  </div>
                  <hr class="hr">
              </div>
              <br><br>
          </div>
      </div>

       <div v-if="selectedDialog">
           <div class="back d_inner" @click="back()">
               {{$t('competitor.bottom-menu.dialogs.back')}}
           </div>
           <div class="add_message" @click="showAddMess()">
               {{$t('competitor.bottom-menu.dialogs.add-comment')}}
           </div>
           <br>
           <div class="title d_inner">
               <br>
               {{ (selectedDialog.topic === '@QUICK')?$t('competitor.bottom-menu.dialogs.quick'):selectedDialog.topic }}
               <br><br>
           </div>
<br>
           <div class="add_mess_section d_inner" v-if="showAddMessage">
               <b-form role="form" v-on:submit.prevent="saveMessage()">
                   <b-form-group>
                       <div class="itm-txt">{{$t('competitor.bottom-menu.dialogs.comment')}}:</div>
                       <ckeditor :editor="editor" v-model="newMessage" :config="editorConfig"></ckeditor>
                   </b-form-group>

                   <div class="">
                       <b-alert show variant="danger" v-if="savingError">
                           <strong>{{$t('competitor.bottom-menu.dialogs.add-comment-failure')}}</strong>
                           <p>{{savingError}}</p>
                       </b-alert>
                   </div>
                   <div>
                       <b-button type="submit" variant="primary" class="login">{{$t('competitor.bottom-menu.dialogs.confirm-add-comment')}}</b-button>
                       <b-button class="cancel" @click="cancel()">{{$t('competitor.bottom-menu.dialogs.add-cancel')}}</b-button>
                       <img class="close-ico" src="../../../content/icons/attached-b.svg">
                       <div style="display: inline-block; vertical-align: top;" >
                           <b-form role="form">
                              <b-form-file style="width: 400px" size="sm" :placeholder="this.attachPlaceHolder" v-model="attachedFile" @input="attach()"></b-form-file>
                           </b-form>
                       </div>
                   </div>
               </b-form>
               <br><br>
           </div>

           <div v-for="d in dialogs">
               <div class="d_inner" v-if="d.topic === selectedDialog.topic">
                   <div class="d_message" v-html="d.message">
                   </div>

                   <div class="d_created">
                       {{$t('competitor.bottom-menu.dialogs.commented-by')}} <span style="font-weight: 600">{{d.author}}</span> {{$t('competitor.bottom-menu.dialogs.on-date')}} {{d.date.toString().substring(0,d.date.toString().lastIndexOf(':')).replace('T', $t('competitor.bottom-menu.dialogs.at-time'))}}
                   </div>
                   <div class="d_comments">
                       <div class="attach" v-if="d.attachmentURL"><img class="close-ico" src="../../../content/icons/attached-b.svg"><a :href="d.attachmentURL.replace('$$$','/')" target="_blank">{{d.attachmentURL.substring(d.attachmentURL.lastIndexOf('$$$')+3)}}</a></div>
                       <div v-if="d.author === user" class="icon-edit close-ico" @click="editMessage(d)"></div><div v-if="d.author === user" class="icon-close close-ico" @click="deleteDialog(d)"></div>
                   </div>

                   <hr class="hr">
                   <br><br>
               </div>


           </div>
       </div>
   </div>
</template>
<script lang="ts" src="./dialogs.component.ts">
</script>
<style scoped>


    .title{
        font-size: 20px;
        font-weight: 600;
        display: inline-block;
    }
    .d_title{
        margin-bottom: 0.5em;
        font-size: 18px;
        font-weight: 600;
        color: #6b48ff;
    }
    .d_title:hover{
        cursor: pointer;
        text-decoration: underline;
    }
    .d_message{
        font-size: 16px;
    }
    .d_created {
        font-size: 12px;
        font-weight: 200;
        display: inline-block;
        width: 40%;
        color: #888888;
        margin-top: 0.8rem;
    }
    .d_comments {
        font-size: 12px;
        font-weight: 600;
        display: inline-block;
        text-align: right;
        width: 59%;
        color: #888888;

    }
    .d_inner {
        padding-left: 3rem;
    }
    .hr {
        margin-top: 0.1rem!important;
        margin-bottom: 0.1rem!important;
    }
    .back {
        color: #6b48ff;
        width: 50%;
        display: inline-block;
    }
    .back:hover {
        text-decoration: underline;
        cursor: pointer;
    }
    .add_message {
        color: #6b48ff;
        width: 49%;
        display: inline-block;
        text-align: right;
    }
    .add_message:hover {
        text-decoration: underline;
        cursor: pointer;
    }


    .edit_main_input {
        height: 48px!important;
        width: 100%;
        display: inline-block!important;
    }
    .details_area {
        display: inline-block;
        width: 100%;
    }

    .itm-txt {
        font-weight: 600;
    }
    .login {
        background-color: #6b48ff;
        color: #ffffff;
        margin-right: 10px;
        height: 38px;
        min-width: 134px;
        border-radius: 3px;
        border-color: #6b48ff;
        font-size: 14px;
        font-weight: 600;
    }
    .login:hover{
         background-color: #6242eb;
    }
    .cancel {
        background-color: #ffffff;
        color: #6b48ff;
        width: 134px;
        margin-right: 10px;
        height: 38px;
        border-radius: 3px;
        border: 2px solid #B6C5D1;
        font-size: 14px;
        font-weight: 600;
    }
    .cancel:hover {
        background-color: rgba(107,72,255,0.08);
    }
    .cancel:active {
        background-color: rgba(107,72,255,0.08)!important;
        border-color: #6b48ff!important;
        color: #6b48ff!important;
    }

.comm_ico {
    width: 16px;
    height: 16px;
    font-size: 16px;
    margin-left: 3px;
}
    .close-ico {
        display: inline-block;
        margin-left: 10px;
        width: 16px;
        height: 16px;
        font-size: 16px;
    }
    .close-ico:hover {
        cursor: pointer;
    }
    .attach {
        display: inline-block;
        font-size: 14px;
        margin-right: 30px;
    }

</style>
