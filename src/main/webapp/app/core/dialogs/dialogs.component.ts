import { Component, Inject, Vue } from 'vue-property-decorator';
import DialogsService from '@/entities/dialogs/dialogs.service';
import { DialogsModel } from '@/shared/model/dialogs.model';
import { Competitors } from '@/shared/model/competitors.model';
import CKEditor from '@ckeditor/ckeditor5-vue';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

@Component({
  data() {
    return {
      editor: ClassicEditor,
      editorData: '<p>Content of the editor.</p>',
      editorConfig: {
        removePlugins: ['BlockQuote', 'MediaEmbed', 'ImageUpload']
      }
    };
  },

  components: {
    ckeditor: CKEditor.component
  }
})
export default class DialogsComponent extends Vue {
  @Inject('dialogsService') private dialogsService: () => DialogsService;

  public dialogs = [];
  public shownDialogs = [];
  public topics = [];
  public selectedDialog = null;
  public showAddDialog = false;
  public showAddMessage = false;
  public newTopic = '';
  public newMessage = '';
  public savingError = null;
  public user = '';
  public dialogToDelete = null;
  public attachedFile = null;
  public attachURL = null;
  public attachPlaceHolder = 'Choose attachment';

  async mounted() {
    this.$root.$on('change_in_dia', this.refill);
    this.user = this.$store.getters.account.login;
    await this.refill();
  }

  public async refill(): Promise<void> {
    this.dialogs = await this.dialogsService().retrieveByCompIdAndSection(this.$attrs.compid, 'General');
    this.$store.commit('setDialogs', this.dialogs);
    this.dialogs = this.dialogs.sort((a, b) => {
      return Date.parse(b.date) - Date.parse(a.date);
    });
    this.topics = new Array();
    this.shownDialogs = new Array();
    for (let i = 0; i < this.dialogs.length; i++) {
      if (!this.topics.includes(this.dialogs[i].topic)) {
        this.topics.push(this.dialogs[i].topic);
        this.shownDialogs.push(this.dialogs[i]);
      }
    }
  }

  public showEditReps(): void {
    localStorage.setItem('siren', this.$attrs.siren);
    this.$root.$emit('bv::show::modal', 'edit-reps-page');
  }

  public numberComments(topic: string): number {
    let sum = 0;
    for (let i = 0; i < this.dialogs.length; i++) {
      if (this.dialogs[i].topic === topic) {
        sum++;
      }
    }
    return sum;
  }

  public numberAttachments(topic: string): number {
    let sum = 0;
    for (let i = 0; i < this.dialogs.length; i++) {
      if (this.dialogs[i].topic === topic && this.dialogs[i].attachmentURL) {
        sum++;
      }
    }
    return sum;
  }

  public selectDialog(d: number): void {
    this.selectedDialog = d;
  }

  public back(): void {
    this.selectedDialog = null;
    this.cancel();
  }

  public showAddMess(): void {
    this.showAddMessage = true;
  }

  public showAddTopic(): void {
    this.showAddDialog = true;
  }

  public cancel(): void {
    this.showAddMessage = false;
    this.showAddDialog = false;
    this.newMessage = '';
    this.newTopic = '';
    this.savingError = null;
    this.dialogToDelete = null;
    this.attachURL = null;
    this.attachPlaceHolder = 'Choose attachment';
    this.attachedFile = null;
  }

  public async saveMessage(): Promise<void> {
    if (this.newMessage === '') {
      this.savingError = 'Please enter a Comment';
      return;
    }

    this.newTopic = this.selectedDialog.topic;
    await this.saveTopic();
  }

  public async saveTopic(): Promise<void> {
    if (this.newTopic === '') {
      this.savingError = 'Please enter a Topic';
      return;
    }
    if (this.newMessage === '') {
      this.savingError = 'Please enter a Comment';
      return;
    }

    const newDialog = new DialogsModel();
    newDialog.topic = this.newTopic;
    newDialog.message = this.newMessage;
    newDialog.author = this.user;
    newDialog.section = 'General';
    const comp = new Competitors();
    comp.id = Number(this.$route.params.id);
    newDialog.competitors = comp;
    newDialog.date = new Date();
    newDialog.attachmentURL = this.attachURL;

    await this.dialogsService().create(newDialog);
    this.newMessage = '';
    this.newTopic = '';
    this.savingError = null;
    if (this.dialogToDelete) {
      await this.deleteDialog(this.dialogToDelete);
      this.dialogToDelete = null;
    }
    this.cancel();
    this.refill();
  }

  public async deleteDialog(d: DialogsModel): Promise<void> {
    await this.dialogsService().delete(d.id);
    this.refill();
  }

  public async editMessage(d: DialogsModel): Promise<void> {
    this.newMessage = d.message;
    this.dialogToDelete = d;
    this.attachURL = d.attachmentURL;
    this.attachPlaceHolder = d.attachmentURL ? d.attachmentURL.substring(d.attachmentURL.lastIndexOf('$$$') + 3) : 'Choose attachment';
    this.showAddMess();
    window.scrollTo(0, 1200);
  }

  public attach() {
    if (this.attachedFile.size > 30000000) {
      this.attachURL = null;
      setTimeout(() => {
        this.attachedFile = null;
      }, 1);
      this.attachPlaceHolder = 'Choose file smaller than 30 MB!';
      return;
    }
    const p = new Date().getTime() + '$$$' + this.attachedFile.name;
    this.attachURL = '/attachments/' + p;
    const formData = new FormData();
    formData.append('file', this.attachedFile);
    this.dialogsService().createAttachment(formData, p);
  }
}
