export interface IDashboardHR {
  glassdoor?: number;
  viadeo?: number;
  recruitersefficiency?: number;
  interviewsnumber?: number;
  recruitersnumber?: number;
  competitorid?: number;
  competitorname?: string;
  grouplogo?: any;
  grouplogocontenttype?: string;
  cooptationpremiumamount?: number;
  juniorsalary?: number;
}

export class DashboardHR implements IDashboardHR {
  constructor(
    public glassdoor?: number,
    public viadeo?: number,
    public recruitersefficiency?: number,
    public interviewsnumber?: number,
    public recruitersnumber?: number,
    public competitorid?: number,
    public competitorname?: string,
    public grouplogo?: any,
    public grouplogocontenttype?: string,
    public cooptationpremiumamount?: number,
    public juniorsalary?: number
  ) {}
}
