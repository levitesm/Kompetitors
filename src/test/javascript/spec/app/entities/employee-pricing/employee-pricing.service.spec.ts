/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import EmployeePricingService from '@/entities/employee-pricing/employee-pricing.service';
import { EmployeePricing, EmployeeLevel, Currency, PaymentType } from '@/shared/model/employee-pricing.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('EmployeePricing Service', () => {
    let service: EmployeePricingService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new EmployeePricingService();
      currentDate = new Date();

      elemDefault = new EmployeePricing(0, EmployeeLevel.JUNIOR, 0, Currency.EUR, PaymentType.PER_HOUR, currentDate);
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
      it('should create a EmployeePricing', async () => {
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

      it('should update a EmployeePricing', async () => {
        const returnedFromService = Object.assign(
          {
            level: 'BBBBBB',
            price: 1,
            currency: 'BBBBBB',
            paymentType: 'BBBBBB',
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
      it('should return a list of EmployeePricing', async () => {
        const returnedFromService = Object.assign(
          {
            level: 'BBBBBB',
            price: 1,
            currency: 'BBBBBB',
            paymentType: 'BBBBBB',
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
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a EmployeePricing', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
