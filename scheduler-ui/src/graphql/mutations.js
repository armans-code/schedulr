import { gql } from '@apollo/client';

export const REGISTER_BUSINESS = gql`
    mutation registerBusiness($RegisterBusinessInput: RegisterBusinessInput!) {
        registerBusiness(registerBusinessInput: $RegisterBusinessInput) {
            id
            name
            email
        }
    }
`;
