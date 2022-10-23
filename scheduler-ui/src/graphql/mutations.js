import { gql } from "@apollo/client";

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
  mutation authorizeFacebook($authorizeFacebookInput: AuthorizeFacebookInput!) {
    authorizeFacebook(authorizeFacebookInput: $authorizeFacebookInput) {
      id
      name
      facebookId
    }
  }
`;

export const AUTHORIZE_INSTAGRAM = gql`
  mutation authorizeInstagram($authorizeInstagramInput: AuthorizeInstagramInput!) {
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
