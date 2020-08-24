/* tslint:disable max-line-length */
import axios from 'axios';

import * as config from '@/shared/config/config';
import {} from '@/shared/date/filters';
import RepresentativesService from '@/entities/representatives/representatives.service';
import { Representatives } from '@/shared/model/representatives.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('Representatives Service', () => {
    let service: RepresentativesService;
    let elemDefault;
    beforeEach(() => {
      service = new RepresentativesService();

      elemDefault = new Representatives(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a Representatives', async () => {
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

      it('should update a Representatives', async () => {
        const returnedFromService = Object.assign(
          {
            competitorSiren: 'BBBBBB',
            qualite: 'BBBBBB',
            type: 'BBBBBB',
            nom: 'BBBBBB',
            prenom: 'BBBBBB',
            nomUsage: 'BBBBBB',
            dateNaissance: 'BBBBBB',
            denominationPM: 'BBBBBB',
            sirenPM: 'BBBBBB',
            linkedInUrl: 'BBBBBB',
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
      it('should return a list of Representatives', async () => {
        const returnedFromService = Object.assign(
          {
            competitorSiren: 'BBBBBB',
            qualite: 'BBBBBB',
            type: 'BBBBBB',
            nom: 'BBBBBB',
            prenom: 'BBBBBB',
            nomUsage: 'BBBBBB',
            dateNaissance: 'BBBBBB',
            denominationPM: 'BBBBBB',
            sirenPM: 'BBBBBB',
            linkedInUrl: 'BBBBBB',
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
      it('should delete a Representatives', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
