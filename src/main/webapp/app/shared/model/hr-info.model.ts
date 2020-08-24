import { Competitors, ICompetitors } from '@/shared/model/competitors.model';

export interface IHrInfo {
  id?: number;
  interviewsNumber?: number;
  recrutmentTime?: string;
  reviewedCvPercent?: number;
  hrDetails?: string;
  vacanciesUrl?: string;
  hrSpecialistsNumber?: number;
  recruitersNumber?: number;
  glassdoorRate?: number;
  viadeoRate?: number;
  glassdoorUrl?: string;
  viadeoUrl?: string;
  cooptationPremiumAmount?: number;
  juniorSalary?: number;
  averageSalary?: number;
  signingIncentives?: string;
  averageContractDuration?: number;
  technicalTest?: boolean;
  personalityTest?: boolean;
  englishTest?: boolean;
  competitor?: ICompetitors;
}

export class HrInfo implements IHrInfo {
  constructor(
    public id?: number,
    public interviewsNumber?: number,
    public recrutmentTime?: string,
    public reviewedCvPercent?: number,
    public hrDetails?: string,
    public vacanciesUrl?: string,
    public hrSpecialistsNumber?: number,
    public recruitersNumber?: number,
    public glassdoorRate?: number,
    public viadeoRate?: number,
    public glassdoorUrl?: string,
    public viadeoUrl?: string,
    public cooptationPremiumAmount?: number,
    public juniorSalary?: number,
    public averageSalary?: number,
    public signingIncentives?: string,
    public averageContractDuration?: number,
    public technicalTest?: boolean,
    public personalityTest?: boolean,
    public englishTest?: boolean,
    public competitor?: ICompetitors
  ) {
    this.interviewsNumber = this.interviewsNumber || 0;
    this.recrutmentTime = this.recrutmentTime || '0';
    this.reviewedCvPercent = this.reviewedCvPercent || 0;
    this.hrDetails = this.hrDetails || '';
    this.vacanciesUrl = this.vacanciesUrl || '';
    this.hrSpecialistsNumber = this.hrSpecialistsNumber || 0;
    this.recruitersNumber = this.recruitersNumber || 0;
    this.glassdoorRate = this.glassdoorRate || 0;
    this.viadeoRate = this.viadeoRate || 0;
    this.glassdoorUrl = this.glassdoorUrl || '';
    this.viadeoUrl = this.viadeoUrl || '';
    this.cooptationPremiumAmount = this.cooptationPremiumAmount || 0;
    this.juniorSalary = this.juniorSalary || 0;
    this.averageSalary = this.averageSalary || 0;
    this.signingIncentives = this.signingIncentives || '';
    this.averageContractDuration = this.averageContractDuration || 0;
    this.technicalTest = this.technicalTest || null;
    this.personalityTest = this.personalityTest || null;
    this.englishTest = this.englishTest || null;
    this.competitor = this.competitor || new Competitors();
  }
}
