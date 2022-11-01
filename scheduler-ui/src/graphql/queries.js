import { gql } from '@apollo/client';

export const BUSINESS = gql`
    query business($businessId: ID!) {
        business(businessId: $businessId) {
            id
            name
        }
    }
`;

export const FACEBOOK_AUTH_URL = gql`
    query facebookAuthUrl {
        facebookAuthUrl {
            url
        }
    }
`;

export const INSTAGRAM_AUTH_URL = gql`
    query instagramAuthUrl {
        instagramAuthUrl {
            url
        }
    }
`;

export const TWITTER_AUTH_URL = gql`
    query twitterAuthUrl {
        twitterAuthUrl {
            url
            verifier
        }
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

export const FACEBOOK_POSTS = gql`
  query facebookPosts($queryFilter: QueryFilter!) {
    facebookPosts(queryFilter: $queryFilter) {
      id
      message
      tags
      link
      scheduled
      scheduledPublishTime
      createdAt
      updatedAt
    }
  }
`;

export const TWITTER_TWEETS = gql`
  query twitterTweets($queryFilter: QueryFilter!) {
    twitterTweets(queryFilter: $queryFilter) {
      id
      message
      media
      scheduled
      scheduledPublishTime
      createdAt
      updatedAt
    }
  }
`;

export const INSTAGRAM_POSTS = gql`
  query instagramPosts($queryFilter: QueryFilter!) {
    instagramPosts(queryFilter: $queryFilter) {
      id
      caption
      imageUrl
      scheduled
      scheduledPublishTime
      createdAt
      updatedAt
    }
  }
`;