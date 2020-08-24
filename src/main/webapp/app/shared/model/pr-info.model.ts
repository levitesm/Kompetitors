export interface IPrInfo {
  id?: number;
  date?: Date;
  marketingWorkforce?: number;
  marketingBudget?: number;
  experienceFeedback?: number;
  linkedInSubscribers?: number;
  linkedInEngageRate?: number;
  linkedInPostWeek?: number;
  linkedInPostDay?: number;
  twitterFollowers?: number;
  twitterPostWeek?: number;
  twitterPostDay?: number;
  instagramFollowers?: number;
  competitorsId?: number;
}

export class PrInfo implements IPrInfo {
  static emptyByCompetitorId(competitorId: number) {
    return new PrInfo(null, new Date(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, competitorId);
  }

  constructor(
    public id?: number,
    public date?: Date,
    public marketingWorkforce?: number,
    public marketingBudget?: number,
    public experienceFeedback?: number,
    public linkedInSubscribers?: number,
    public linkedInEngageRate?: number,
    public linkedInPostWeek?: number,
    public linkedInPostDay?: number,
    public twitterFollowers?: number,
    public twitterPostWeek?: number,
    public twitterPostDay?: number,
    public instagramFollowers?: number,
    public competitorsId?: number
  ) {}
}
