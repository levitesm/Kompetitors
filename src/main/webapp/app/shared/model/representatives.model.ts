export interface IRepresentatives {
  id?: number;
  competitorSiren?: string;
  qualite?: string;
  type?: string;
  nom?: string;
  prenom?: string;
  nomUsage?: string;
  dateNaissance?: string;
  denominationPM?: string;
  sirenPM?: string;
  linkedInUrl?: string;
  old?: boolean;
}

export class Representatives implements IRepresentatives {
  constructor(
    public id?: number,
    public competitorSiren?: string,
    public qualite?: string,
    public type?: string,
    public nom?: string,
    public prenom?: string,
    public nomUsage?: string,
    public dateNaissance?: string,
    public denominationPM?: string,
    public sirenPM?: string,
    public linkedInUrl?: string,
    public old?: boolean
  ) {
    this.old = this.old || false;
  }
}
