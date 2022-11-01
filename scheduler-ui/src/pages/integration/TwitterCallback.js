import { useMutation } from "@apollo/client";
import React, { useEffect, useState } from "react";
import { AiOutlineLoading3Quarters } from "react-icons/ai";
import { useSearchParams } from "react-router-dom";
import { useAuth } from "../../auth/AuthContext";
import {
  AUTHORIZE_INSTAGRAM,
  AUTHORIZE_TWITTER,
} from "../../graphql/mutations";

function TwitterCallback() {
  const { currentUser } = useAuth();
  const [searchParams, _] = useSearchParams();
  const [loading, setLoading] = useState(true);
  const [integrated, setIntegrated] = useState();
  const code = searchParams.get("code");
  const verifier = localStorage.getItem("twitter_code_verifier");
  const [authorizeTwitter, { loading: apiLoading, data, error }] =
    useMutation(AUTHORIZE_TWITTER);

  useEffect(() => {
    if (code && verifier) {
      authorizeTwitter({
        variables: {
          authorizeTwitterInput: {
            businessId: currentUser.uid,
            code: code,
            verifier: verifier,
          },
        },
      }).then((res) => {
        console.log(res);
        if (res.data.authorizeTwitter.id !== null) {
          setIntegrated(true);
        }
        setLoading(false);
      });
    } else {
      setLoading(false);
    }
  }, [code, verifier]);

  if (loading) {
    return (
      <div className="w-full h-screen flex flex-col items-center justify-center">
        <h1 className="animate-spin text-blue-600 text-6xl font-poppins font-extrabold text-center">
          <AiOutlineLoading3Quarters />
        </h1>
      </div>
    );
  } else if (integrated) {
    return (
      <div className="w-full h-screen flex flex-col items-center justify-center">
        <h1 className="text-green-600 text-6xl font-poppins font-extrabold text-center">
          Success!
        </h1>
        <p>You've successfully linked your Twitter account to Schedulify!</p>
      </div>
    );
  } else {
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
