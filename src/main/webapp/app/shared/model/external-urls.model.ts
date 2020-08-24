export interface IExternalUrls {
  id?: number;
  facebookUrl?: string;
  twitterUrl?: string;
  instagramUrl?: string;
  youtubeUrl?: string;
  linkedinUrl?: string;
  githubUrl?: string;
  blogFeed?: string;
  googleAlertsFeed?: string;
  competitorId?: number;
}

export class ExternalUrls implements IExternalUrls {
  public static byCompetitorId(id: number): ExternalUrls {
    return new ExternalUrls(null, '', '', '', '', '', '', '', '', id);
  }

  constructor(
    public id?: number,
    public facebookUrl?: string,
    public twitterUrl?: string,
    public instagramUrl?: string,
    public youtubeUrl?: string,
    public linkedinUrl?: string,
    public githubUrl?: string,
    public blogFeed?: string,
    public googleAlertsFeed?: string,
    public competitorId?: number
  ) {}
}
