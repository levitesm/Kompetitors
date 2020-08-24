import { IClients } from '@/shared/model/clients.model';
import { ITechCompetancies } from '@/shared/model/tech-competancies.model';
import { ITechPartners } from '@/shared/model/tech-partners.model';
import { ITechProjects } from '@/shared/model/tech-projects.model';
import { ITechServices } from '@/shared/model/tech-services.model';
import { ITechTools } from '@/shared/model/tech-tools.model';
import { IListCities } from '@/shared/model/list-cities.model';
import { ICompetitors } from '@/shared/model/competitors.model';

export interface IOffices {
  id?: number;
  name?: string;
  address?: string;
  phone?: string;
  post?: string;
  cityAsText?: string;
  numberEmployees?: number;
  numberConsultants?: number;
  numberTechnicals?: number;
  numberHR?: number;
  numberClients?: number;
  established?: Date;
  isMainOffice?: boolean;
  clients?: IClients[];
  techCompetancies?: ITechCompetancies[];
  techPartners?: ITechPartners[];
  techProjects?: ITechProjects[];
  techServices?: ITechServices[];
  techTools?: ITechTools[];
  city?: IListCities;
  competitors?: ICompetitors;
}

export class Offices implements IOffices {
  constructor(
    public id?: number,
    public name?: string,
    public address?: string,
    public phone?: string,
    public post?: string,
    public cityAsText?: string,
    public numberEmployees?: number,
    public numberConsultants?: number,
    public numberTechnicals?: number,
    public numberHR?: number,
    public numberClients?: number,
    public established?: Date,
    public isMainOffice?: boolean,
    public clients?: IClients[],
    public techCompetancies?: ITechCompetancies[],
    public techPartners?: ITechPartners[],
    public techProjects?: ITechProjects[],
    public techServices?: ITechServices[],
    public techTools?: ITechTools[],
    public city?: IListCities,
    public competitors?: ICompetitors
  ) {
    this.isMainOffice = this.isMainOffice || false;
  }
}
