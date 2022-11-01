import { gql } from '@apollo/client';

export const CREATE_INSTAGRAM_POST = gql`
    mutation CreateInstagramPost(
        $CreateInstagramPostInput: CreateInstagramPostInput!
    ) {
        createInstagramPost(
            createInstagramPostInput: $CreateInstagramPostInput
        ) {
            id
        }
    }
`;

export const CREATE_FACEBOOK_POST = gql`
    mutation CreateFacebookPost(
        $CreateFacebookPostInput: CreateFacebookPostInput!
    ) {
        createFacebookPost(createFacebookPostInput: $CreateFacebookPostInput) {
            id
            published
            scheduledPublishTime
            message
            business {
                id
            }
        }
    }
`;

export const CREATE_TWITTER_POST = gql`
    mutation CreateTwitterTweet(
        $CreateTwitterTweetInput: CreateTwitterTweetInput!
    ) {
        createTwitterTweet(createTwitterTweetInput: $CreateTwitterTweetInput) {
            id
            business {
                id
                name
            }
            twitterAccount {
                id
                name
            }
            twitterId
            message
            scheduledPublishTime
            media
        }
    }
`;

export const REGISTER_BUSINESS = gql`
    mutation registerBusiness($RegisterBusinessInput: RegisterBusinessInput!) {
        registerBusiness(registerBusinessInput: $RegisterBusinessInput) {
            id
            name
            email
        }
    }
`;

export const AUTHORIZE_FACEBOOK = gql`
    mutation authorizeFacebook(
        $authorizeFacebookInput: AuthorizeFacebookInput!
    ) {
        authorizeFacebook(authorizeFacebookInput: $authorizeFacebookInput) {
            id
            name
            facebookId
        }
    }
`;

export const AUTHORIZE_INSTAGRAM = gql`
    mutation authorizeInstagram(
        $authorizeInstagramInput: AuthorizeInstagramInput!
    ) {
        authorizeInstagram(authorizeInstagramInput: $authorizeInstagramInput) {
            id
            name
            instagramId
        }
    }
`;

export const AUTHORIZE_TWITTER = gql`
    mutation authorizeTwitter($authorizeTwitterInput: AuthorizeTwitterInput!) {
        authorizeTwitter(authorizeTwitterInput: $authorizeTwitterInput) {
            id
            name
            twitterId
        }
    }
`;
