import { useMutation } from "@apollo/client";
import React, { useEffect } from "react";
import { useState } from "react";
import { AiOutlineLoading3Quarters } from "react-icons/ai";
import { useSearchParams } from "react-router-dom";
import { useAuth } from "../../auth/AuthContext";
import { AUTHORIZE_INSTAGRAM } from "../../graphql/mutations";

function InstagramCallback() {
  const { currentUser } = useAuth();
  const [searchParams, _] = useSearchParams();
  const [loading, setLoading] = useState(true);
  const [integrated, setIntegrated] = useState(false);
  const code = searchParams.get("code");
  const [authorizeInsta, { loading: apiLoading, data, error }] =
    useMutation(AUTHORIZE_INSTAGRAM);

  useEffect(() => {
    if (code) {
      authorizeInsta({
        variables: {
          authorizeInstagramInput: {
            businessId: currentUser.uid,
            code: code,
          },
        },
      }).then((res) => {
        console.log(res);
        if (res.data.authorizeInstagram.id !== null) {
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
        <p>You've successfully linked your Instagram account to Schedulify!</p>
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

export default InstagramCallback;
