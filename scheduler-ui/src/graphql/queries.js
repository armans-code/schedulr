import { gql } from '@apollo/client';

export const BUSINESS = gql`
    query business($businessId: ID!) {
        business(businessId: $businessId) {
            id
            name
        }
    }
`;

export const FACEBOOK_POSTS = gql`
    query facebookPosts($QueryFilter: QueryFilter!) {
        facebookPosts(queryFilter: $QueryFilter) {
            id
            scheduledPublishTime
            published
            message
            business {
                id
            }
            facebookAccount {
                name
            }
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
