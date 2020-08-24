/* tslint:disable max-line-length */
import axios from 'axios';

import * as config from '@/shared/config/config';
import {} from '@/shared/date/filters';
import InfogreffeService from '@/entities/infogreffe/infogreffe.service';
import { Infogreffe } from '@/shared/model/infogreffe.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('Infogreffe Service', () => {
    let service: InfogreffeService;
    let elemDefault;
    beforeEach(() => {
      service = new InfogreffeService();

      elemDefault = new Infogreffe(
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
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
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
      it('should create a Infogreffe', async () => {
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

      it('should update a Infogreffe', async () => {
        const returnedFromService = Object.assign(
          {
            departement: 'BBBBBB',
            ville: 'BBBBBB',
            numDept: 'BBBBBB',
            codeGreffe: 'BBBBBB',
            dateImmatriculation: 'BBBBBB',
            ca1: 'BBBBBB',
            siren: 'BBBBBB',
            ca2: 'BBBBBB',
            formeJuridique: 'BBBBBB',
            resultat3: 'BBBBBB',
            resultat2: 'BBBBBB',
            resultat1: 'BBBBBB',
            ficheidentite: 'BBBBBB',
            duree1: 'BBBBBB',
            dateDePublication: 'BBBBBB',
            statut: 'BBBBBB',
            nic: 'BBBBBB',
            codeApe: 'BBBBBB',
            adresse: 'BBBBBB',
            trancheCaMillesime3: 'BBBBBB',
            denomination: 'BBBBBB',
            duree2: 'BBBBBB',
            effectif1: 'BBBBBB',
            effectif3: 'BBBBBB',
            effectif2: 'BBBBBB',
            ca3: 'BBBBBB',
            trancheCaMillesime1: 'BBBBBB',
            duree3: 'BBBBBB',
            trancheCaMillesime2: 'BBBBBB',
            codePostal: 'BBBBBB',
            dateDeClotureExercice1: 'BBBBBB',
            dateDeClotureExercice3: 'BBBBBB',
            dateDeClotureExercice2: 'BBBBBB',
            libelleApe: 'BBBBBB',
            greffe: 'BBBBBB',
            millesime3: 'BBBBBB',
            millesime2: 'BBBBBB',
            millesime1: 'BBBBBB',
            region: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of Infogreffe', async () => {
        const returnedFromService = Object.assign(
          {
            departement: 'BBBBBB',
            ville: 'BBBBBB',
            numDept: 'BBBBBB',
            codeGreffe: 'BBBBBB',
            dateImmatriculation: 'BBBBBB',
            ca1: 'BBBBBB',
            siren: 'BBBBBB',
            ca2: 'BBBBBB',
            formeJuridique: 'BBBBBB',
            resultat3: 'BBBBBB',
            resultat2: 'BBBBBB',
            resultat1: 'BBBBBB',
            ficheidentite: 'BBBBBB',
            duree1: 'BBBBBB',
            dateDePublication: 'BBBBBB',
            statut: 'BBBBBB',
            nic: 'BBBBBB',
            codeApe: 'BBBBBB',
            adresse: 'BBBBBB',
            trancheCaMillesime3: 'BBBBBB',
            denomination: 'BBBBBB',
            duree2: 'BBBBBB',
            effectif1: 'BBBBBB',
            effectif3: 'BBBBBB',
            effectif2: 'BBBBBB',
            ca3: 'BBBBBB',
            trancheCaMillesime1: 'BBBBBB',
            duree3: 'BBBBBB',
            trancheCaMillesime2: 'BBBBBB',
            codePostal: 'BBBBBB',
            dateDeClotureExercice1: 'BBBBBB',
            dateDeClotureExercice3: 'BBBBBB',
            dateDeClotureExercice2: 'BBBBBB',
            libelleApe: 'BBBBBB',
            greffe: 'BBBBBB',
            millesime3: 'BBBBBB',
            millesime2: 'BBBBBB',
            millesime1: 'BBBBBB',
            region: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a Infogreffe', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
