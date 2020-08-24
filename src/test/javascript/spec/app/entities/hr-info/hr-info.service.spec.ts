/* tslint:disable max-line-length */
import axios from 'axios';

import * as config from '@/shared/config/config';
import {} from '@/shared/date/filters';
import HrInfoService from '@/entities/hr-info/hr-info.service';
import { HrInfo } from '@/shared/model/hr-info.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('HrInfo Service', () => {
    let service: HrInfoService;
    let elemDefault;
    beforeEach(() => {
      service = new HrInfoService();

      elemDefault = new HrInfo(0, 0, 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a HrInfo', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a HrInfo', async () => {
        const returnedFromService = Object.assign(
          {
            interviewsNumber: 1,
            recrutmentTime: 'BBBBBB',
            reviewedCvPercent: 1,
            hrDetails: 'BBBBBB',
            vacanciesUrl: 'BBBBBB',
            hrSpecialistsNumber: 1,
            glassdoorRate: 1,
            viadeoRate: 1,
            glassdoorUrl: 'BBBBBB',
            viadeoUrl: 'BBBBBB',
            cooptationPremiumAmount: 1,
            juniorSalary: 1,
            averageSalary: 1,
            signingIncentives: 'BBBBBB',
            averageContractDuration: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of HrInfo', async () => {
        const returnedFromService = Object.assign(
          {
            interviewsNumber: 1,
            recrutmentTime: 'BBBBBB',
            reviewedCvPercent: 1,
            hrDetails: 'BBBBBB',
            vacanciesUrl: 'BBBBBB',
            hrSpecialistsNumber: 1,
            glassdoorRate: 1,
            viadeoRate: 1,
            glassdoorUrl: 'BBBBBB',
            viadeoUrl: 'BBBBBB',
            cooptationPremiumAmount: 1,
            juniorSalary: 1,
            averageSalary: 1,
            signingIncentives: 'BBBBBB',
            averageContractDuration: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a HrInfo', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
