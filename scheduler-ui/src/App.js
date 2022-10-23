import { React } from "react";
import { Route, Routes } from "react-router-dom";
import "./App.css";
import {
  ApolloProvider,
  InMemoryCache,
  ApolloClient,
  createHttpLink,
} from "@apollo/client";
import { setContext } from "@apollo/client/link/context";
import { AuthProvider } from "./auth/AuthContext";
import PrivateRoute from "./components/layout/PrivateRoute";
import Integrations from "./pages/integration/Integrations";
import Dashboard from "./pages/Dashboard";
import Login from "./pages/Login";
import NewPost from "./pages/NewPost";
import Register from "./pages/Register";
import TwitterCallback from "./pages/integration/TwitterCallback";
import InstagramCallback from "./pages/integration/InstagramCallback";
import FacebookCallback from "./pages/integration/FacebookCallback";

const createApolloClient = () => {
  const httpLink = createHttpLink({
    uri: "http://localhost:8080/graphql",
    options: {
      reconnect: true,
    },
  });

  const authLink = setContext((_, { headers }) => {
    const token = sessionStorage.getItem("accessToken");
    if (token) {
      return {
        headers: {
          ...headers,
          Authorization: `Bearer ${token}`,
        },
      };
    } else {
      return {
        headers: {
          ...headers,
        },
      };
    }
  });

  return new ApolloClient({
    link: authLink.concat(httpLink),
    cache: new InMemoryCache(),
  });
};

function App() {
  const client = createApolloClient();
  return (
    <ApolloProvider client={client}>
      <AuthProvider>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route
            path="/new-post"
            element={
              <PrivateRoute>
                <NewPost />
              </PrivateRoute>
            }
          />
          <Route
            path="/dashboard"
            element={
              <PrivateRoute>
                <Dashboard />
              </PrivateRoute>
            }
          />
          <Route
            path="/integrations"
            element={
              <PrivateRoute>
                <Integrations />
              </PrivateRoute>
            }
          />
          {/* Cleaner way to include routes below?? */}
          <Route
            path="/twitter-callback"
            element={
              <PrivateRoute>
                <TwitterCallback />
              </PrivateRoute>
            }
          />
          <Route
            path="/instagram-callback"
            element={
              <PrivateRoute>
                <InstagramCallback />
              </PrivateRoute>
            }
          />
          <Route
            path="/facebook-callback"
            element={
              <PrivateRoute>
                <FacebookCallback />
              </PrivateRoute>
            }
          />
        </Routes>
      </AuthProvider>
    </ApolloProvider>
  );
}

export default App;
