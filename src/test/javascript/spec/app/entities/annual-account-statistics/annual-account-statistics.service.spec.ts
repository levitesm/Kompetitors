/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import AnnualAccountStatisticsService from '@/entities/annual-account-statistics/annual-account-statistics.service';
import { AnnualAccountStatistics } from '@/shared/model/annual-account-statistics.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('AnnualAccountStatistics Service', () => {
    let service: AnnualAccountStatisticsService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new AnnualAccountStatisticsService();
      currentDate = new Date();

      elemDefault = new AnnualAccountStatistics(0, 'AAAAAAA', 0, 0, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            modified: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a AnnualAccountStatistics', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            modified: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            modified: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a AnnualAccountStatistics', async () => {
        const returnedFromService = Object.assign(
          {
            siren: 'BBBBBB',
            year: 1,
            code: 1,
            message: 'BBBBBB',
            modified: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            modified: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of AnnualAccountStatistics', async () => {
        const returnedFromService = Object.assign(
          {
            siren: 'BBBBBB',
            year: 1,
            code: 1,
            message: 'BBBBBB',
            modified: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            modified: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a AnnualAccountStatistics', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
