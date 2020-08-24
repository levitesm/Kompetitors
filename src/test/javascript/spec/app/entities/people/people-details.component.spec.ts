/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PeopleDetailComponent from '@/entities/people/people-details.vue';
import PeopleClass from '@/entities/people/people-details.component';
import PeopleService from '@/entities/people/people.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('People Management Detail Component', () => {
    let wrapper: Wrapper<PeopleClass>;
    let comp: PeopleClass;
    let peopleServiceStub: SinonStubbedInstance<PeopleService>;

    beforeEach(() => {
      peopleServiceStub = sinon.createStubInstance<PeopleService>(PeopleService);

      wrapper = shallowMount<PeopleClass>(PeopleDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { peopleService: () => peopleServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPeople = { id: 123 };
        peopleServiceStub.find.resolves(foundPeople);

        // WHEN
        comp.retrievePeople(123);
        await comp.$nextTick();

        // THEN
        expect(comp.people).toBe(foundPeople);
      });
    });
  });
});
