/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DialogsDetailComponent from '@/entities/dialogs/dialogs-details.vue';
import DialogsClass from '@/entities/dialogs/dialogs-details.component';
import DialogsService from '@/entities/dialogs/dialogs.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Dialogs Management Detail Component', () => {
    let wrapper: Wrapper<DialogsClass>;
    let comp: DialogsClass;
    let dialogsServiceStub: SinonStubbedInstance<DialogsService>;

    beforeEach(() => {
      dialogsServiceStub = sinon.createStubInstance<DialogsService>(DialogsService);

      wrapper = shallowMount<DialogsClass>(DialogsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { dialogsService: () => dialogsServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDialogs = { id: 123 };
        dialogsServiceStub.find.resolves(foundDialogs);

        // WHEN
        comp.retrieveDialogs(123);
        await comp.$nextTick();

        // THEN
        expect(comp.dialogs).toBe(foundDialogs);
      });
    });
  });
});
