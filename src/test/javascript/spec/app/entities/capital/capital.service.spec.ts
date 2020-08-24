/* tslint:disable max-line-length */
import axios from 'axios';

import * as config from '@/shared/config/config';
import {} from '@/shared/date/filters';
import CapitalService from '@/entities/capital/capital.service';
import { Capital } from '@/shared/model/capital.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('Capital Service', () => {
    let service: CapitalService;
    let elemDefault;
    beforeEach(() => {
      service = new CapitalService();

      elemDefault = new Capital(0, 'AAAAAAA', 0, 'AAAAAAA', 0, 0, 0, false, false, false, false, false);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a Capital', async () => {
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

      it('should update a Capital', async () => {
        const returnedFromService = Object.assign(
          {
            competitorSiren: 'BBBBBB',
            montant: 1,
            devise: 'BBBBBB',
            nbrParts: 1,
            pourcentageDetentionPP: 1,
            pourcentageDetentionPM: 1,
            listed: true,
            privateCapital: true,
            independentC: true,
            independentE: true,
            old: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of Capital', async () => {
        const returnedFromService = Object.assign(
          {
            competitorSiren: 'BBBBBB',
            montant: 1,
            devise: 'BBBBBB',
            nbrParts: 1,
            pourcentageDetentionPP: 1,
            pourcentageDetentionPM: 1,
            listed: true,
            privateCapital: true,
            independentC: true,
            independentE: true,
            old: true
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a Capital', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
