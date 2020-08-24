/* tslint:disable max-line-length */
import axios from 'axios';

import * as config from '@/shared/config/config';
import {} from '@/shared/date/filters';
import CompetitiveRatesService from '@/entities/competitive-rates/competitive-rates.service';
import { CompetitiveRates } from '@/shared/model/competitive-rates.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('CompetitiveRates Service', () => {
    let service: CompetitiveRatesService;
    let elemDefault;
    beforeEach(() => {
      service = new CompetitiveRatesService();

      elemDefault = new CompetitiveRates(0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a CompetitiveRates', async () => {
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

      it('should update a CompetitiveRates', async () => {
        const returnedFromService = Object.assign(
          {
            totalRate: 1,
            techRate: 1,
            financeRate: 1,
            clientsRate: 1,
            hrRate: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));
      });
      it('should return a list of CompetitiveRates', async () => {
        const returnedFromService = Object.assign(
          {
            totalRate: 1,
            techRate: 1,
            financeRate: 1,
            clientsRate: 1,
            hrRate: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a CompetitiveRates', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
