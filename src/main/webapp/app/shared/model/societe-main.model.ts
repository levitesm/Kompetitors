import { ICompetitors } from '@/shared/model/competitors.model';

export interface ISocieteMain {
  id?: number;
  siren?: string;
  deno?: string;
  greffe?: string;
  enseigne?: string;
  psiret?: string;
  adresse?: string;
  codepostal?: string;
  normcommune?: string;
  commune?: string;
  ape?: string;
  apetexte?: string;
  dateimmat?: string;
  dcren?: string;
  nationalite?: string;
  formejur?: string;
  capital?: string;
  devisecap?: string;
  typecap?: string;
  url?: string;
  competitor?: ICompetitors;
}

export class SocieteMain implements ISocieteMain {
  constructor(
    public id?: number,
    public siren?: string,
    public deno?: string,
    public greffe?: string,
    public enseigne?: string,
    public psiret?: string,
    public adresse?: string,
    public codepostal?: string,
    public normcommune?: string,
    public commune?: string,
    public ape?: string,
    public apetexte?: string,
    public dateimmat?: string,
    public dcren?: string,
    public nationalite?: string,
    public formejur?: string,
    public capital?: string,
    public devisecap?: string,
    public typecap?: string,
    public url?: string,
    public competitor?: ICompetitors
  ) {}
}
