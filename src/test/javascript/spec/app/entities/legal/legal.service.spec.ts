/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import LegalService from '@/entities/legal/legal.service';
import { Legal } from '@/shared/model/legal.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('Legal Service', () => {
    let service: LegalService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new LegalService();
      currentDate = new Date();

      elemDefault = new Legal(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            founded: format(currentDate, DATE_FORMAT),
            updateDate: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a Legal', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            founded: format(currentDate, DATE_FORMAT),
            updateDate: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            founded: currentDate,
            updateDate: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a Legal', async () => {
        const returnedFromService = Object.assign(
          {
            legalAddress: 'BBBBBB',
            siren: 'BBBBBB',
            greffe: 'BBBBBB',
            founded: format(currentDate, DATE_FORMAT),
            updateDate: format(currentDate, DATE_FORMAT),
            legalForm: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            founded: currentDate,
            updateDate: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of Legal', async () => {
        const returnedFromService = Object.assign(
          {
            legalAddress: 'BBBBBB',
            siren: 'BBBBBB',
            greffe: 'BBBBBB',
            founded: format(currentDate, DATE_FORMAT),
            updateDate: format(currentDate, DATE_FORMAT),
            legalForm: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            founded: currentDate,
            updateDate: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a Legal', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
