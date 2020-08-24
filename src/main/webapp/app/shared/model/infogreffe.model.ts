import { ICompetitors } from '@/shared/model/competitors.model';

export interface IInfogreffe {
  id?: number;
  departement?: string;
  ville?: string;
  numDept?: string;
  codeGreffe?: string;
  dateImmatriculation?: string;
  ca1?: string;
  siren?: string;
  ca2?: string;
  formeJuridique?: string;
  resultat3?: string;
  resultat2?: string;
  resultat1?: string;
  ficheidentite?: string;
  duree1?: string;
  dateDePublication?: string;
  statut?: string;
  nic?: string;
  codeApe?: string;
  adresse?: string;
  trancheCaMillesime3?: string;
  denomination?: string;
  duree2?: string;
  effectif1?: string;
  effectif3?: string;
  effectif2?: string;
  ca3?: string;
  trancheCaMillesime1?: string;
  duree3?: string;
  trancheCaMillesime2?: string;
  codePostal?: string;
  dateDeClotureExercice1?: string;
  dateDeClotureExercice3?: string;
  dateDeClotureExercice2?: string;
  libelleApe?: string;
  greffe?: string;
  millesime3?: string;
  millesime2?: string;
  millesime1?: string;
  region?: string;
  competitor?: ICompetitors;
}

export class Infogreffe implements IInfogreffe {
  constructor(
    public id?: number,
    public departement?: string,
    public ville?: string,
    public numDept?: string,
    public codeGreffe?: string,
    public dateImmatriculation?: string,
    public ca1?: string,
    public siren?: string,
    public ca2?: string,
    public formeJuridique?: string,
    public resultat3?: string,
    public resultat2?: string,
    public resultat1?: string,
    public ficheidentite?: string,
    public duree1?: string,
    public dateDePublication?: string,
    public statut?: string,
    public nic?: string,
    public codeApe?: string,
    public adresse?: string,
    public trancheCaMillesime3?: string,
    public denomination?: string,
    public duree2?: string,
    public effectif1?: string,
    public effectif3?: string,
    public effectif2?: string,
    public ca3?: string,
    public trancheCaMillesime1?: string,
    public duree3?: string,
    public trancheCaMillesime2?: string,
    public codePostal?: string,
    public dateDeClotureExercice1?: string,
    public dateDeClotureExercice3?: string,
    public dateDeClotureExercice2?: string,
    public libelleApe?: string,
    public greffe?: string,
    public millesime3?: string,
    public millesime2?: string,
    public millesime1?: string,
    public region?: string,
    public competitor?: ICompetitors
  ) {}
}
