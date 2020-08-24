export interface ICapital {
  id?: number;
  competitorSiren?: string;
  montant?: number;
  devise?: string;
  nbrParts?: number;
  pourcentageDetentionPP?: number;
  pourcentageDetentionPM?: number;
  listed?: boolean;
  privateCapital?: boolean;
  independentC?: boolean;
  independentE?: boolean;
  old?: boolean;
}

export class Capital implements ICapital {
  constructor(
    public id?: number,
    public competitorSiren?: string,
    public montant?: number,
    public devise?: string,
    public nbrParts?: number,
    public pourcentageDetentionPP?: number,
    public pourcentageDetentionPM?: number,
    public listed?: boolean,
    public privateCapital?: boolean,
    public independentC?: boolean,
    public independentE?: boolean,
    public old?: boolean
  ) {
    this.listed = this.listed || false;
    this.privateCapital = this.privateCapital || false;
    this.independentC = this.independentC || false;
    this.independentE = this.independentE || false;
    this.old = this.old || false;
  }
}
