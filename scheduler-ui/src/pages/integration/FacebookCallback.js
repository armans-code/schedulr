import { useMutation } from "@apollo/client";
import React, { useEffect } from "react";
import { useState } from "react";
import { AiOutlineLoading3Quarters } from "react-icons/ai";
import { useSearchParams } from "react-router-dom";
import { useAuth } from "../../auth/AuthContext";
import { AUTHORIZE_FACEBOOK } from "../../graphql/mutations";

function FacebookCallback() {
  const { currentUser } = useAuth();
  const [searchParams, setSearchParams] = useSearchParams();
  const [loading, setLoading] = useState(true);
  const [integrated, setIntegrated] = useState(false);
  const code = searchParams.get("code");
  const [authorizeFb, { loading: apiLoading, data, error }] =
    useMutation(AUTHORIZE_FACEBOOK);

  useEffect(() => {
    if (code) {
      authorizeFb({
        variables: {
          authorizeFacebookInput: {
            businessId: currentUser.uid,
            code: code,
          },
        },
      }).then((res) => {
        console.log(res);
        if (res.data.authorizeFacebook != null) {
          setIntegrated(true);
        }
        setLoading(false);
      });
    }
  }, [code]);

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
        <p>You've successfully linked your Facebook account to Schedulify!</p>
      </div>
    );
  } else {
    return (
      <div className="w-full h-screen flex flex-col items-center justify-center">
        <h1 className="text-red-600 text-6xl font-poppins font-extrabold text-center">
          Error!
        </h1>
      </div>
    );
  }
}

export default FacebookCallback;
