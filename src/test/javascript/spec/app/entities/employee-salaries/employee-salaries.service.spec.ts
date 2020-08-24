/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import EmployeeSalariesService from '@/entities/employee-salaries/employee-salaries.service';
import { EmployeeSalaries } from '@/shared/model/employee-salaries.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('EmployeeSalaries Service', () => {
    let service: EmployeeSalariesService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new EmployeeSalariesService();
      currentDate = new Date();

      elemDefault = new EmployeeSalaries(0, 'AAAAAAA', 0, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            updateDate: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a EmployeeSalaries', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            updateDate: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            updateDate: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a EmployeeSalaries', async () => {
        const returnedFromService = Object.assign(
          {
            seniority: 'BBBBBB',
            salary: 1,
            updateDate: format(currentDate, DATE_FORMAT),
            updatedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            updateDate: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of EmployeeSalaries', async () => {
        const returnedFromService = Object.assign(
          {
            seniority: 'BBBBBB',
            salary: 1,
            updateDate: format(currentDate, DATE_FORMAT),
            updatedBy: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            updateDate: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a EmployeeSalaries', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
