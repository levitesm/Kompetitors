export interface IShareHolders {
  id?: number;
  competitorSiren?: string;
  typePersonne?: string;
  denomination?: string;
  civilite?: string;
  nomPatronymique?: string;
  nomUsage?: string;
  prenom?: string;
  libelleFormeJuridique?: string;
  codeFormeJuridique?: string;
  siren?: string;
  codeApe?: string;
  dateNaissance?: string;
  nbrParts?: number;
  pourcentageDetention?: number;
  old?: boolean;
}

export class ShareHolders implements IShareHolders {
  constructor(
    public id?: number,
    public competitorSiren?: string,
    public typePersonne?: string,
    public denomination?: string,
    public civilite?: string,
    public nomPatronymique?: string,
    public nomUsage?: string,
    public prenom?: string,
    public libelleFormeJuridique?: string,
    public codeFormeJuridique?: string,
    public siren?: string,
    public codeApe?: string,
    public dateNaissance?: string,
    public nbrParts?: number,
    public pourcentageDetention?: number,
    public old?: boolean
  ) {
    this.old = this.old || false;
  }
}
