import { useMutation } from "@apollo/client";
import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { useAuth } from "../../auth/AuthContext";
import { AUTHORIZE_INSTAGRAM } from "../../graphql/mutations";

function InstagramCallback() {
  const { currentUser } = useAuth();
  const [searchParams, _] = useSearchParams();
  const code = searchParams.get("code");
  const [authorizeInsta, { loading, data, error }] = useMutation(AUTHORIZE_INSTAGRAM);

  useEffect(() => {
    authorizeInsta({
      variables: {
        authorizeInstagramInput: {
          businessId: currentUser.uid,
          code: code,
        },
      },
    }).then((res) => console.log(res));
  }, []);

  if(loading) {
    return <div>Loading...</div>;
  }

  if (searchParams.get("code")) {
    return (
      <div className="w-full h-screen flex flex-col items-center justify-center">
        <h1 className="text-blue-600 text-6xl font-poppins font-extrabold text-center">
          Success!
        </h1>
        <p>You've successfully linked your Instagram account to Schedulify!</p>
      </div>
    );
  }
  else {
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
  }
}

export default InstagramCallback;
