import { gql } from "@apollo/client";

export const FACEBOOK_AUTH_URL = gql`
  query facebookAuthUrl {
    facebookAuthUrl
  }
`;

export const INSTAGRAM_AUTH_URL = gql`
  query instagramAuthUrl {
    instagramAuthUrl
  }
`;

export const TWITTER_AUTH_URL = gql`
  query twitterAuthUrl {
    twitterAuthUrl
  }
`;

export const FACEBOOK_ACCOUNTS = gql`
  query facebookAccounts($businessId: ID!) {
    facebookAccounts(businessId: $businessId) {
      id
      facebookId
      name
    }
  }
`;

export const INSTAGRAM_ACCOUNTS = gql`
  query instagramAccounts($businessId: ID!) {
    instagramAccounts(businessId: $businessId) {
      id
      instagramId
      facebookAccount {
        id
        facebookId
        name
      }
      name
    }
  }
`;

export const TWITTER_ACCOUNTS = gql`
  query twitterAccounts($businessId: ID!) {
    twitterAccounts(businessId: $businessId) {
      id
      twitterId
      name
    }
  }
`;