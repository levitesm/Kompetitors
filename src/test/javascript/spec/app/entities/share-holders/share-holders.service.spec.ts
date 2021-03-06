/* tslint:disable max-line-length */
import axios from 'axios';

import * as config from '@/shared/config/config';
import {} from '@/shared/date/filters';
import ShareHoldersService from '@/entities/share-holders/share-holders.service';
import { ShareHolders } from '@/shared/model/share-holders.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('ShareHolders Service', () => {
    let service: ShareHoldersService;
    let elemDefault;
    beforeEach(() => {
      service = new ShareHoldersService();

      elemDefault = new ShareHolders(
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
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
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
      it('should create a ShareHolders', async () => {
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

      it('should update a ShareHolders', async () => {
        const returnedFromService = Object.assign(
          {
            competitorSiren: 'BBBBBB',
            typePersonne: 'BBBBBB',
            denomination: 'BBBBBB',
            civilite: 'BBBBBB',
            nomPatronymique: 'BBBBBB',
            nomUsage: 'BBBBBB',
            prenom: 'BBBBBB',
            libelleFormeJuridique: 'BBBBBB',
            codeFormeJuridique: 'BBBBBB',
            siren: 'BBBBBB',
            codeApe: 'BBBBBB',
            dateNaissance: 'BBBBBB',
            nbrParts: 1,
            pourcentageDetention: 1,
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
      it('should return a list of ShareHolders', async () => {
        const returnedFromService = Object.assign(
          {
            competitorSiren: 'BBBBBB',
            typePersonne: 'BBBBBB',
            denomination: 'BBBBBB',
            civilite: 'BBBBBB',
            nomPatronymique: 'BBBBBB',
            nomUsage: 'BBBBBB',
            prenom: 'BBBBBB',
            libelleFormeJuridique: 'BBBBBB',
            codeFormeJuridique: 'BBBBBB',
            siren: 'BBBBBB',
            codeApe: 'BBBBBB',
            dateNaissance: 'BBBBBB',
            nbrParts: 1,
            pourcentageDetention: 1,
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
      it('should delete a ShareHolders', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
