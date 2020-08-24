/* tslint:disable max-line-length */
import axios from 'axios';

import * as config from '@/shared/config/config';
import {} from '@/shared/date/filters';
import DashboardFinanceService from '@/entities/dashboard-finance/dashboard-finance.service';
import { DashboardFinance } from '@/shared/model/dashboard-finance.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('DashboardFinance Service', () => {
    let service: DashboardFinanceService;
    let elemDefault;
    beforeEach(() => {
      service = new DashboardFinanceService();

      elemDefault = new DashboardFinance(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a DashboardFinance', async () => {
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

      it('should update a DashboardFinance', async () => {
        const returnedFromService = Object.assign(
          {
            grossSales: 1,
            grossSalesPerEmployee: 1,
            ebit: 1,
            netResult: 1,
            workforce: 1,
            year: 1,
            grossSalesPerConsultant: 1,
            averagePay: 1,
            netResultPercent: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of DashboardFinance', async () => {
        const returnedFromService = Object.assign(
          {
            grossSales: 1,
            grossSalesPerEmployee: 1,
            ebit: 1,
            netResult: 1,
            workforce: 1,
            year: 1,
            grossSalesPerConsultant: 1,
            averagePay: 1,
            netResultPercent: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a DashboardFinance', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
