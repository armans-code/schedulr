import { useMutation } from "@apollo/client";
import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { useAuth } from "../../auth/AuthContext";
import { AUTHORIZE_FACEBOOK } from "../../graphql/mutations";

function FacebookCallback() {
  const { currentUser } = useAuth();
  const [searchParams, setSearchParams] = useSearchParams();
  const code = searchParams.get("code");
  const [authorizeFb, { loading, data, error }] = useMutation(AUTHORIZE_FACEBOOK);

  useEffect(() => {
    authorizeFb({
      variables: {
        authorizeFacebookInput: {
          businessId: currentUser.uid,
          code: code,
        },
      },
    }).then((res) => console.log(res));
  }, [code]);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (searchParams.get("error"))
    return (
      <div className="w-full h-screen flex flex-col items-center justify-center">
        <h1 className="text-red-500 text-6xl font-poppins font-extrabold text-center">
          Error!
        </h1>
        <p>
          <b>Error:</b> {searchParams.get("error")}
        </p>
        <p>
          <b>Error code:</b> {searchParams.get("error_code")}{" "}
        </p>
        <p>
          <b>Error description:</b> {searchParams.get("error_description")}{" "}
        </p>
      </div>
    );
  else {
    return (
      <div className="w-full h-screen flex flex-col items-center justify-center">
        <h1 className="text-blue-600 text-6xl font-poppins font-extrabold text-center">
          Success!
        </h1>
        <p>You've successfully linked your Facebook account to Schedulify!</p>
      </div>
    );
  }
}

export default FacebookCallback;
