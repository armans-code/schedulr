import { Route, Routes } from 'react-router-dom';
import './App.css';
import { ApolloProvider, InMemoryCache, ApolloClient } from '@apollo/client';
import { AuthProvider } from './auth/AuthContext';
import PrivateRoute from './components/layout/PrivateRotue';
import ConnectedApps from './pages/ConnectedApps';
import Dashboard from './pages/Dashboard';
import IntegrationCallback from './pages/IntegrationCallback';
import Login from './pages/Login';
import NewPost from './pages/NewPost';
import Register from './pages/Register';

const client = new ApolloClient({
    uri: 'http://localhost:8080/graphql',
    cache: new InMemoryCache(),
    credentials: false,
});

function App() {
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
                        path="/connected-apps"
                        element={
                            <PrivateRoute>
                                <ConnectedApps />
                            </PrivateRoute>
                        }
                    />
                    <Route
                        path="/integration-success"
                        element={<IntegrationCallback />}
                    />
                </Routes>
            </AuthProvider>
        </ApolloProvider>
    );
}

export default App;
