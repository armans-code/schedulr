import { useLazyQuery, useQuery } from "@apollo/client";
import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../auth/AuthContext";
import IntegrationCard from "../../components/integrations/IntegrationCard";
import {
  FACEBOOK_ACCOUNTS,
  FACEBOOK_AUTH_URL,
  INSTAGRAM_ACCOUNTS,
  INSTAGRAM_AUTH_URL,
  TWITTER_ACCOUNTS,
  TWITTER_AUTH_URL,
} from "../../graphql/queries";

function Integrations() {
  const { currentUser } = useAuth();

  const {
    loading: fbAccountsLoading,
    data: fbAccountsData,
    error: fbAccountsError,
  } = useQuery(FACEBOOK_ACCOUNTS, {
    variables: {
      businessId: currentUser.uid,
    },
  });

  const {
    loading: twitterAccountsLoading,
    data: twitterAccountsData,
    error: twitterAccountsError,
  } = useQuery(TWITTER_ACCOUNTS, {
    variables: {
      businessId: currentUser.uid,
    },
  });

  const {
    loading: instaAccountsLoading,
    data: instaAccountsData,
    error: instaAccountsError,
  } = useQuery(INSTAGRAM_ACCOUNTS, {
    variables: {
      businessId: currentUser.uid,
    },
  });

  const [
    getFbAuthUrl,
    { loading: fbAuthLoading, data: fbAuthData, error: fbAuthError },
  ] = useLazyQuery(FACEBOOK_AUTH_URL);

  const [
    getInstaAuthUrl,
    { loading: instaAuthLoading, data: instaAuthData, error: instaAuthError },
  ] = useLazyQuery(INSTAGRAM_AUTH_URL);

  const [
    getTwitterAuthUrl,
    {
      loading: twitterAuthLoading,
      data: twitterAuthData,
      error: twitterAuthError,
    },
  ] = useLazyQuery(TWITTER_AUTH_URL);

  useEffect(() => {
    console.log(fbAccountsData, twitterAccountsData, instaAccountsData);
  }, [fbAccountsData, twitterAccountsData, instaAccountsData]);

  return (
    <div className="flex flex-col px-24">
      <div className="flex flex-col gap-3 mt-8">
        <h1 className="text-3xl font-medium">Integrations</h1>
        <p className="text-gray-500">
          Integrate your favorite apps in order to boost your efficiency with
          scheduled posts and optimal hashtags!
        </p>
      </div>
      <div className="flex gap-9 mt-8">
        <IntegrationCard
          name="Twitter"
          desc="Find optimal hashtags and schedule posts for Twitter."
          image="https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/Twitter-logo.svg/440px-Twitter-logo.svg.png"
          connected={twitterAccountsData?.twitterAccounts.length !== 0}
          onClick={getTwitterAuthUrl}
        />
        <IntegrationCard
          name="Instagram"
          desc="Find optimal hashtags and related terms for Instagram."
          image="https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Instagram_logo_2022.svg/300px-Instagram_logo_2022.svg.png"
          connected={instaAccountsData?.instagramAccounts.length !== 0}
          onClick={getInstaAuthUrl}
        />
        <IntegrationCard
          name="Facebook"
          desc="Find optimal hashtags and schedule posts for Facebook"
          image="https://upload.wikimedia.org/wikipedia/en/thumb/0/04/Facebook_f_logo_%282021%29.svg/300px-Facebook_f_logo_%282021%29.svg.png"
          connected={fbAccountsData?.facebookAccounts.length !== 0}
          onClick={getFbAuthUrl}
        />
      </div>
    </div>
  );
}

export default Integrations;
