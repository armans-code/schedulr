import { useMutation } from "@apollo/client";
import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { useAuth } from "../../auth/AuthContext";
import { AUTHORIZE_INSTAGRAM, AUTHORIZE_TWITTER } from "../../graphql/mutations";

function TwitterCallback() {
  const { currentUser } = useAuth();
  const [searchParams, _] = useSearchParams();
  const token = searchParams.get("oauth_token");
  const verifier = searchParams.get("oauth_verifier");
  const [authorizeTwitter, { loading, data, error }] =
    useMutation(AUTHORIZE_TWITTER);

  useEffect(() => {
    authorizeTwitter({
      variables: {
        authorizeTwitterInput: {
          businessId: currentUser.uid,
          token: token,
          verifier: verifier,
        },
      },
    }).then((res) => console.log(res));
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (searchParams.get("oauth_token") && searchParams.get("oauth_verifier"))
      return (
      <div className="w-full h-screen flex flex-col items-center justify-center">
        <h1 className="text-blue-600 text-6xl font-poppins font-extrabold text-center">
          Success!
        </h1>
        <p>You've successfully linked your Twitter account to Schedulify!</p>
      </div>
    );
  else {
    return (
      <div className="w-full h-screen flex flex-col items-center justify-center">
        <h1 className="text-red-500 text-6xl font-poppins font-extrabold text-center">
          Error!
        </h1>
      </div>
    );
  }
}

export default TwitterCallback;
