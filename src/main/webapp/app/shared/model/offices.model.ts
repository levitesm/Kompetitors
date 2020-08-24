import { Clients, IClients } from '@/shared/model/clients.model';
import { ITechCompetancies } from '@/shared/model/tech-competancies.model';
import { ITechPartners } from '@/shared/model/tech-partners.model';
import { ITechProjects } from '@/shared/model/tech-projects.model';
import { ITechServices } from '@/shared/model/tech-services.model';
import { ITechTools } from '@/shared/model/tech-tools.model';
import { IListCities, ListCities } from '@/shared/model/list-cities.model';
import { Competitors, ICompetitors } from '@/shared/model/competitors.model';

export interface IOffices {
  id?: number;
  name?: string;
  post?: string;
  address?: string;
  phone?: string;
  numberEmployees?: number;
  numberConsultants?: number;
  numberTechnicals?: number;
  numberHR?: number;
  numberClients?: number;
  established?: Date;
  numberRecruiters?: number;
  recruitersUpdate?: Date;
  mainOffice?: boolean;
  latitude?: number;
  longitude?: number;
  clients?: IClients[];
  city?: IListCities;
  cityAsText?: string;
  competitors?: ICompetitors;
}

export class Offices implements IOffices {
  constructor(
    public id?: number,
    public name?: string,
    public post?: string,
    public address?: string,
    public phone?: string,
    public numberEmployees?: number,
    public numberConsultants?: number,
    public numberTechnicals?: number,
    public numberHR?: number,
    public numberClients?: number,
    public established?: Date,
    public numberRecruiters?: number,
    public recruitersUpdate?: Date,
    public mainOffice?: boolean,
    public latitude?: number,
    public longitude?: number,
    public clients?: IClients[],
    public city?: IListCities,
    public cityAsText?: string,
    public competitors?: ICompetitors
  ) {
    this.name = this.name || '';
    this.post = this.post || '';
    this.address = this.address || '';
    this.phone = this.phone || '';
    this.numberEmployees = this.numberEmployees || 0;
    this.numberConsultants = this.numberConsultants || 0;
    this.numberTechnicals = this.numberTechnicals || 0;
    this.numberHR = this.numberHR || 0;
    this.numberClients = this.numberClients || 0;
    this.established = this.established || new Date();
    this.numberRecruiters = this.numberRecruiters || 0;
    this.recruitersUpdate = this.recruitersUpdate || new Date();
    this.mainOffice = this.mainOffice || false;
    this.clients = this.clients || [];
    this.cityAsText = this.cityAsText || '';
  }
}
