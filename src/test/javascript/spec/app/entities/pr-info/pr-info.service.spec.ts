/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import PrInfoService from '@/entities/pr-info/pr-info.service';
import { PrInfo } from '@/shared/model/pr-info.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('PrInfo Service', () => {
    let service: PrInfoService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new PrInfoService();
      currentDate = new Date();

      elemDefault = new PrInfo(0, currentDate, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            date: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a PrInfo', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a PrInfo', async () => {
        const returnedFromService = Object.assign(
          {
            date: format(currentDate, DATE_FORMAT),
            marketingWorkforce: 1,
            marketingBudget: 1,
            experienceFeedback: 1,
            linkedInSubscribers: 1,
            linkedInEngageRate: 1,
            linkedInPostWeek: 1,
            linkedInPostDay: 1,
            twitterFollowers: 1,
            twitterPostWeek: 1,
            twitterPostDay: 1,
            instagramFollowers: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of PrInfo', async () => {
        const returnedFromService = Object.assign(
          {
            date: format(currentDate, DATE_FORMAT),
            marketingWorkforce: 1,
            marketingBudget: 1,
            experienceFeedback: 1,
            linkedInSubscribers: 1,
            linkedInEngageRate: 1,
            linkedInPostWeek: 1,
            linkedInPostDay: 1,
            twitterFollowers: 1,
            twitterPostWeek: 1,
            twitterPostDay: 1,
            instagramFollowers: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a PrInfo', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
